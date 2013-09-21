package ru.qrushtabs.app;

import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingsActivity extends Activity {
	private OnClickListener onChangePassClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SettingsActivity.this,ChangePassActivity.class);
			startActivity(intent);
		}
	};
	@Override
	public void onCreate(Bundle savedBundleInstance) {
		super.onCreate(savedBundleInstance);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.settings);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_back);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Button changePassBtn = (Button)findViewById(R.id.set_change_pass_btn);
		if(!ProfileInfo.signInType.equals("def"))
		{
			changePassBtn.setVisibility(View.GONE);
			
		}
		else
		{
			changePassBtn.setOnClickListener(onChangePassClick);
		}
		Button backButton = (Button) this.findViewById(R.id.header_back_btn);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Button changeProfBtn = (Button)findViewById(R.id.set_change_prof_btn);
		changeProfBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this,ChangeProfileActivity.class);
				startActivity(intent);
			}
		});
		

		Button exitButton = (Button) this.findViewById(R.id.exit_btn);
		exitButton.setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View v) {
				MainActivity.getInstance().finish();
				Intent intent = new Intent(SettingsActivity.this,EnterActivity.class);
				
				ServerAPI.flushProfile();
				startActivity(intent);
				finish();
				
//				Intent intent = new Intent(Intent.ACTION_MAIN);
//				intent.addCategory(Intent.CATEGORY_HOME);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent);
			}
		});

	}
}
