package jp.mothule.locksample.infra;

import android.util.Log;

public class LogUtil {

	public static void d(Class<?> class1, String string) {
		Log.d(class1.getSimpleName(), string);
	}

	public static void w(Class<?> class1, String string) {
		Log.w(class1.getSimpleName(), string);
	}

	public static void i(Class<?> class1, String string) {
		Log.i(class1.getSimpleName(), string);
	}

}
