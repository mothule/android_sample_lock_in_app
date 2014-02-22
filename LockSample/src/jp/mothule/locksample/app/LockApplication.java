package jp.mothule.locksample.app;

import jp.mothule.locksample.infra.LogUtil;
import android.app.Application;

public class LockApplication extends Application {

	@Override
	public void onCreate() {
		LogUtil.i(getClass(), "onCreate");
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		LogUtil.i(getClass(), "onTerminate");
		super.onTerminate();
	}
}
