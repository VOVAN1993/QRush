package ru.qrushtabs.app;

import ru.qrushtabs.app.dialogs.BlackAlertDialog;
import ru.qrushtabs.app.profile.ProfileActivity;
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GetMoneyActivity extends MyVungleActivity {
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
		
		Button toPhoneButton = (Button)this.findViewById(R.id.get_money_to_phone_btn);
		toPhoneButton.setOnClickListener(new OnClickListener() {
		  @Override
		  public void onClick(View v) {
		    if(ServerAPI.isOnline() && !ServerAPI.offlineMod)
		    {
		    	BlackAlertDialog newFragment;
				newFragment = new BlackAlertDialog();
				 
				newFragment.setLabelText("Вывод денег на данный момент недоступен.");
				newFragment.show(GetMoneyActivity.this.getSupportFragmentManager(),
						"missiles");
				newFragment.setDrawableBackground(GetMoneyActivity.this.getResources().getDrawable(R.drawable.black_alert_error));
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
		
		Button toYmButton = (Button)this.findViewById(R.id.get_money_to_ym_btn);
		toYmButton.setOnClickListener(new OnClickListener() {
		  @Override
		  public void onClick(View v) {
			  if(ServerAPI.isOnline() && !ServerAPI.offlineMod)
			    {
				  BlackAlertDialog newFragment;
					newFragment = new BlackAlertDialog();
					 
					newFragment.setLabelText("Вывод денег на данный момент недоступен.");
					newFragment.show(GetMoneyActivity.this.getSupportFragmentManager(),
							"missiles");
					newFragment.setDrawableBackground(GetMoneyActivity.this.getResources().getDrawable(R.drawable.black_alert_error));
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
