package jp.mothule.locksample.app;

import jp.mothule.locksample.R;
import jp.mothule.locksample.domain.LockService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LockActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lock);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		Intent launchIntent = new Intent(this, ApplicationLaunchActivity.class);
		launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		launchIntent.putExtra(ApplicationLaunchActivity.EXTRA_EXIT_APPLICATION, true);
		startActivity(launchIntent);

		finish();
	}

	@Override
	public void onClick(View v) {
		LockService.unlock(this);
		LockService.forceUnlock(this);
		finish();
	}

}
