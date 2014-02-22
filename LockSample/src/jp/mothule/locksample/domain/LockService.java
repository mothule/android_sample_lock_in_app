package jp.mothule.locksample.domain;

import jp.mothule.locksample.app.LockActivity;
import jp.mothule.locksample.infra.LogUtil;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LockService {

	private static final String APP_CONFIG_NAME = "lockSampleConfig";

	/** ロックをかける */
	public static void lock(Context c) {
		SharedPreferences sp = c.getSharedPreferences(APP_CONFIG_NAME, Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putBoolean("shouldLock", true);
		edit.commit();
		LogUtil.d(LockService.class, "ロックフラグOn");
	}

	public static void forceLock(Context c) {
		SharedPreferences sp = c.getSharedPreferences(APP_CONFIG_NAME, Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putBoolean("shouldForceLock", true);
		edit.commit();
		LogUtil.d(LockService.class, "強制ロックフラグOn");
	}

	public static void lockingIfNeed(Context c) {
		LogUtil.d(LockService.class, "ロック判定");

		SharedPreferences sp = c.getSharedPreferences(APP_CONFIG_NAME, Context.MODE_PRIVATE);
		boolean shouldLock = sp.getBoolean("shouldLock", false);
		boolean shouldForceLock = sp.getBoolean("shouldForceLock", false);
		if (shouldLock || shouldForceLock) {
			LogUtil.d(LockService.class, "ロック画面呼び出し");

			Intent intent = new Intent(c, LockActivity.class);
			c.startActivity(intent);
		}
	}

	public static void unlock(Context c) {
		SharedPreferences sp = c.getSharedPreferences(APP_CONFIG_NAME, Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putBoolean("shouldLock", false);
		edit.commit();
		LogUtil.d(LockService.class, "ロックフラグOff");
	}

	public static void forceUnlock(Context c) {
		SharedPreferences sp = c.getSharedPreferences(APP_CONFIG_NAME, Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putBoolean("shouldForceLock", false);
		edit.commit();
		LogUtil.d(LockService.class, "強制ロックフラグOff");
	}

}
