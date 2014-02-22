package jp.mothule.locksample.app;

import jp.mothule.locksample.R;
import jp.mothule.locksample.infra.LogUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.main_sub_button) {
			startActivity(new Intent(this, SubActivity.class));
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		Intent launchIntent = new Intent(this, ApplicationLaunchActivity.class);
		launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		launchIntent.putExtra(ApplicationLaunchActivity.EXTRA_EXIT_APPLICATION, true);
		startActivity(launchIntent);
		finish();
		LogUtil.d(getClass(), "メイン画面でバックボタン押下");

	}

}
