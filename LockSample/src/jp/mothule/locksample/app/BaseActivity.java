package jp.mothule.locksample.app;

import jp.mothule.locksample.domain.LockService;
import jp.mothule.locksample.infra.LogUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;

public class BaseActivity extends Activity {

	@Override
	protected void onNewIntent(Intent intent) {
		LogUtil.i(getClass(), "onNewIntent");
		super.onNewIntent(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogUtil.i(getClass(), "onCreate");
		super.onCreate(savedInstanceState);

		LockService.unlock(this);
	}

	@Override
	protected void onResume() {
		LogUtil.i(getClass(), "onResume");
		super.onResume();

		if (isLockTarget()) {
			LockService.lockingIfNeed(this);
		}
	}

	@Override
	protected void onStart() {
		LogUtil.i(getClass(), "onStart");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		LogUtil.i(getClass(), "onRestart");
		super.onRestart();
	}

	@Override
	protected void onPause() {
		LogUtil.i(getClass(), "onPause");
		super.onPause();

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		if (!pm.isScreenOn()) {
			LockService.lock(this);
		}
	}

	@Override
	protected void onStop() {
		LogUtil.i(getClass(), "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		LogUtil.i(getClass(), "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		LogUtil.i(getClass(), "onBackPressed");
		super.onBackPressed();
	}

	@Override
	protected void onUserLeaveHint() {
		LogUtil.i(getClass(), "onUserLeaveHint");
		super.onUserLeaveHint();

		// ロックをかける
		LockService.lock(this);
	}

	private boolean isLockTarget() {
		if (this instanceof ApplicationLaunchActivity) {
			return false;
		}
		return true;
	}
}
