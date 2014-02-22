package jp.mothule.locksample.app;

import android.app.Activity;
import android.content.Intent;

public class BootFlowUtils {

	public static Intent[] proceedToNextFlow(Activity activity, boolean isLaunchAction) {
		Intent i = new Intent(activity, MainActivity.class);
		return new Intent[] { i };
	}

	public static void startLoginActivityIfNeedFromExternal(Activity activity, Intent[] nextFlowIntents) {
		// TODO Auto-generated method stub

	}

}
