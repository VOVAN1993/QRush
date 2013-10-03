package ru.qrushtabs.app.quests;

import ru.qrushtabs.app.MyVungleActivity;
import ru.qrushtabs.app.R;
import ru.qrushtabs.app.R.id;
import ru.qrushtabs.app.R.layout;
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class DailyQuestActivity extends MyVungleActivity 
{
	//private WebView webView;
	 
	@Override
	public void onCreate(Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.daily_quest);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_back);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Button backButton = (Button) this.findViewById(R.id.header_back_btn);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
	 
		 
	 
		int currentDay = QuestObject.currentQuestObject.currentDay;
		
//		   webView = (WebView) findViewById(R.id.quest_wv);
//		   webView.getSettings().setJavaScriptEnabled(true);
		   //webView.loadUrl("http://www.google.com");
	 
//		   String customHtml = "<html><body><h1>Hello, WebView</h1> " +
//		   		"<img src=\"http://htmlbook.ru/themes/hb/img/logo.png\" width=\"189\" height=\"255\" alt=\"lorem\"></body></html>";
//		   webView.loadData(customHtml, "text/html", "UTF-8");
		   
	//	   webView.loadUrl(ServerAPI.getServerURL() + QuestObject.currentQuestObject.url+"index.html"); 
	 
	 
	}

}
