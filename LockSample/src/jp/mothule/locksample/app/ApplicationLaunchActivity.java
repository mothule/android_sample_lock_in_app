package jp.mothule.locksample.app;

import java.lang.Thread.UncaughtExceptionHandler;

import jp.mothule.locksample.domain.LockService;
import jp.mothule.locksample.infra.LogUtil;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * アプリケーションランチャー
 */
public class ApplicationLaunchActivity extends BaseActivity {
	/** * アプリ外部から起動要求 */
	private enum FromExternalLaunchRequestType {
		/** なし */
		NULL,
		/** ログイン画面 */
		LOGIN;
	}

	/** キー：初回起動フローであるかどうか */
	public static final String EXTRA_IS_INIT_BOOT_FLOW = "isInitBootFlow";

	/** キー：アプリケーション起動による呼び出しであるかどうか */
	public static final String EXTRA_IS_LAUNCH_ACTION = "isLaunchAction";

	/** キー：アプリケーションを終了するかどうか */
	public static final String EXTRA_EXIT_APPLICATION = "finishApplication";

	/** onCreateかonNewIntent経由で呼び出されたかどうか. バックキーでアクティビティスタックから取り出された場合はfalseになる */
	private boolean resumeViaOnCreateOrNewIntent;

	/** アプリ外部から起動要求 */
	private FromExternalLaunchRequestType fromExternalLaunchRequestType = FromExternalLaunchRequestType.NULL;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 非キャッチ例外ハンドラの初期化
		initUncaughtExceptionHandeler();

		resumeViaOnCreateOrNewIntent = true;
		resolveFromExternalLaunchRequestType(getIntent());

		LockService.forceLock(this);

		Intent intent = new Intent(this, MainActivity.class);
		this.startActivity(intent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		boolean exitApplication = intent.getBooleanExtra(EXTRA_EXIT_APPLICATION, false);
		if (!exitApplication) {
			resumeViaOnCreateOrNewIntent = true;
			resolveFromExternalLaunchRequestType(intent);
			launchNextFlow(intent.getBooleanExtra(EXTRA_IS_LAUNCH_ACTION, false));

		} else {
			resumeViaOnCreateOrNewIntent = false;
		}

		LogUtil.i(getClass(), "onNewIntent " + exitApplication);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onResume() {
		super.onResume();

		if (resumeViaOnCreateOrNewIntent) {
			resumeViaOnCreateOrNewIntent = false;
			LogUtil.i(getClass(), "onResume");

		} else {
			// バックキーでアクティビティスタックから取り出された場合は、他のすべての画面が終了しているため自分も終了
			finish();
			LogUtil.i(getClass(), "onResume 他の画面が終了しているため終了する");
		}
	}

	private void launchNextFlow(boolean isLaunchAction) {
		Intent[] nextFlowIntents = BootFlowUtils.proceedToNextFlow(this, isLaunchAction);
		for (Intent nextFlowIntent : nextFlowIntents) {
			this.startActivity(nextFlowIntent);
		}
		// this.finish(); ApplicationLaunchActivityはアクティビティスタックの底に残す

		// アプリ外部からの起動要求
		switch (fromExternalLaunchRequestType) {
		case LOGIN:
			// ログイン
			BootFlowUtils.startLoginActivityIfNeedFromExternal(this, nextFlowIntents);
			break;
		case NULL:
		default:
			break;
		}
		fromExternalLaunchRequestType = FromExternalLaunchRequestType.NULL;
	}

	/**
	 * アプリ外部からの起動要求を解決します<br>
	 * 
	 * @param intent
	 *            起動インテント
	 */
	private void resolveFromExternalLaunchRequestType(Intent intent) {
		if (!Intent.ACTION_VIEW.equalsIgnoreCase(intent.getAction()) || null == intent.getData()) {
			// アプリ外部からの起動ではないため、何もしない
			return;

		} else {
			String typeName = intent.getData().getQueryParameter("type");
			for (FromExternalLaunchRequestType type : FromExternalLaunchRequestType.values()) {
				if (type.name().equalsIgnoreCase(typeName)) {
					fromExternalLaunchRequestType = type;
					return;
				}
			}
			LogUtil.w(getClass(), "指定されたtypeパラメータを解決できません. type=[" + typeName + "]");
			return;
		}
	}

	/**
	 * 拾いきれていない例外に対するハンドラ
	 */
	private void initUncaughtExceptionHandeler() {
		// 拾い切れない例外を拾う
		final UncaughtExceptionHandler savedUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				Log.e("fatal", "予期しないエラーが発生", ex);
				// 退避しておいた UncaughtExceptionHandler を実行
				savedUncaughtExceptionHandler.uncaughtException(thread, ex);
			}
		});
	}
}
