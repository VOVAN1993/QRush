package ru.qrushtabs.app;

import ru.qrushtabs.app.dialogs.BlackAlertDialog;
import ru.qrushtabs.app.profile.ProfileActivity;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GetMoneyActivity extends MyVungleActivity {
	public static final String PHONE = "toPhone";
	public static final String YM = "toYandexMoney";

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
  		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.get_money);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_back);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Button backButton = (Button)this.findViewById(R.id.header_back_btn);
		backButton.setOnClickListener(new OnClickListener() {
		  @Override
		  public void onClick(View v) {
		    finish();
		  }
		});
		TextView tv = (TextView)findViewById(R.id.money_place_tv);
		tv.setText(String.valueOf(ProfileInfo.getMoneyCount()));
		Button toPhoneButton = (Button)this.findViewById(R.id.get_money_to_phone_btn);
		toPhoneButton.setOnClickListener(new OnClickListener() {
		  @Override
		  public void onClick(View v) {
		    if(ServerAPI.isOnline() && !ServerAPI.offlineMod)
		    {
		    	RelativeLayout rl = (RelativeLayout)GetMoneyActivity.this.findViewById(R.id.to_ym_rl);
		    	rl.setVisibility(View.GONE);
		    	  rl = (RelativeLayout)GetMoneyActivity.this.findViewById(R.id.to_phone_rl);
		    	rl.setVisibility(View.VISIBLE);
		    	
		    	Button tombtn = (Button)findViewById(R.id.get_money_to_phone_btn_ok);
		    	tombtn.setOnClickListener(new OnClickListener()
		    	{

					@Override
					public void onClick(View arg0) {
						EditText codeet = (EditText)GetMoneyActivity.this.findViewById(R.id.phone_code_et);
						EditText numet = (EditText)GetMoneyActivity.this.findViewById(R.id.phone_et);
						EditText countet = (EditText)GetMoneyActivity.this.findViewById(R.id.phone_money_tv);
						int c = Integer.valueOf(countet.getText().toString());
						if(c<=200 && c>=100)
						{
						if(ServerAPI.takeOutMoney(GetMoneyActivity.PHONE,"7"+codeet.getText().toString() + numet.getText().toString(),c).equals("true"))
						{
					    	BlackAlertDialog newFragment;
							newFragment = new BlackAlertDialog();
							 
							newFragment.setLabelText("Вывод денег удался.");
							ProfileInfo.profileChanged = true;
							ProfileInfo.addMoneyCount(-c);
							TextView tv = (TextView)findViewById(R.id.money_place_tv);
							tv.setText(String.valueOf(ProfileInfo.getMoneyCount()));
							newFragment.show(GetMoneyActivity.this.getSupportFragmentManager(),
									"missiles");
							newFragment.setDrawableBackground(GetMoneyActivity.this.getResources().getDrawable(R.drawable.black_alert_ok));
						}
						else
						{
					    	BlackAlertDialog newFragment;
							newFragment = new BlackAlertDialog();
							 
							newFragment.setLabelText("Вывод денег не удался.");
							newFragment.show(GetMoneyActivity.this.getSupportFragmentManager(),
									"missiles");
							newFragment.setDrawableBackground(GetMoneyActivity.this.getResources().getDrawable(R.drawable.black_alert_error));
						}	
						}
						else
						{
							BlackAlertDialog newFragment;
							newFragment = new BlackAlertDialog();
							 
							newFragment.setLabelText("Некорректная сумма, должна быть в промежутке 100..200.");
							newFragment.show(GetMoneyActivity.this.getSupportFragmentManager(),
									"missiles");
							newFragment.setDrawableBackground(GetMoneyActivity.this.getResources().getDrawable(R.drawable.black_alert_error));
						}
					}		    	
		    	});
//		    	BlackAlertDialog newFragment;
//				newFragment = new BlackAlertDialog();
//				 
//				newFragment.setLabelText("Вывод денег на данный момент недоступен.");
//				newFragment.show(GetMoneyActivity.this.getSupportFragmentManager(),
//						"missiles");
//				newFragment.setDrawableBackground(GetMoneyActivity.this.getResources().getDrawable(R.drawable.black_alert_error));
		    }
		    else
		    {
		    	
//		    	BlackAlertDialog newFragment;
//				newFragment = new BlackAlertDialog();
//				 
//				newFragment.setLabelText("В оффлайн режиме недоступен вывод денег!");
//				newFragment.show(GetMoneyActivity.this.getSupportFragmentManager(),
//						"missiles");
//				newFragment.setDrawableBackground(GetMoneyActivity.this.getResources().getDrawable(R.drawable.black_alert_error));
		    }
		  }
		});
		
		Button toYmButton = (Button)this.findViewById(R.id.get_money_to_ym_btn);
		toYmButton.setOnClickListener(new OnClickListener() {
		  @Override
		  public void onClick(View v) {
			  if(ServerAPI.isOnline() && !ServerAPI.offlineMod)
			    {
				  RelativeLayout rl = (RelativeLayout)GetMoneyActivity.this.findViewById(R.id.to_phone_rl);
			    	rl.setVisibility(View.GONE);
			    	rl = (RelativeLayout)GetMoneyActivity.this.findViewById(R.id.to_ym_rl);
			    	rl.setVisibility(View.VISIBLE);
//				  BlackAlertDialog newFragment;
//					newFragment = new BlackAlertDialog();
//					 
//					newFragment.setLabelText("Вывод денег на данный момент недоступен.");
//					newFragment.show(GetMoneyActivity.this.getSupportFragmentManager(),
//							"missiles");
//					newFragment.setDrawableBackground(GetMoneyActivity.this.getResources().getDrawable(R.drawable.black_alert_error));
			    }
			    else
			    {
			    	BlackAlertDialog newFragment;
					newFragment = new BlackAlertDialog();
					 
					newFragment.setLabelText("В оффлайн режиме недоступен вывод денег!");
					newFragment.show(GetMoneyActivity.this.getSupportFragmentManager(),
							"missiles");
					newFragment.setDrawableBackground(GetMoneyActivity.this.getResources().getDrawable(R.drawable.black_alert_error));
			    }
		  }
		});
	}
}
