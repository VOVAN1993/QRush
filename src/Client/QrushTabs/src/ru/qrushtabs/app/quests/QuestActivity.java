package ru.qrushtabs.app.quests;

import java.io.IOException;
import java.util.TreeMap;

import org.apache.http.client.ClientProtocolException;

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

public class QuestActivity extends Activity {
	private WebView webView;
	private static TreeMap<String, WebView> webMap = new TreeMap<String, WebView>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.quest);

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

		if (QuestObject.currentQuestObject.state == QuestObject.ACTIVE) {

			Button dit = (Button) this.findViewById(R.id.quest_dobtn);
			dit.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.reg_greenbtn));
			dit.setText("����� �����");
			dit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (ServerAPI.changeQuestStatus(QuestObject.currentQuestObject.name, QuestObject.currentQuestObject.isDaily, "completed").equals("true")) {
						QuestObject qo = QuestObject.currentQuestObject;
						//qo.state = QuestObject.COMPLETED;
						//qo.getQuestContentView().refreshProgress();
						
						QuestObject.removeQuest(qo);

					}
				}
			});
		} else {
			Button dit = (Button) this.findViewById(R.id.quest_dobtn);
			dit.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.quest_doitbtn));
			dit.setText("���������");
			dit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (ServerAPI.subscribeQuest(
							QuestObject.currentQuestObject.name).equals("true")) {
						QuestObject qo = QuestObject.currentQuestObject;
						if (QuestObject.getActiveQuest(qo.questId) == null) {
							QuestObject.addQuest(qo);
							QuestObject.checkTasks();
						}

					}
				}
			});
		}

		webView = (WebView) findViewById(R.id.quest_wv);
		webView.getSettings().setJavaScriptEnabled(true);
		// //webView.loadUrl("http://www.google.com");
		//
		// // String customHtml = "<html><body><h1>Hello, WebView</h1> " +
		// //
		// "<img src=\"http://htmlbook.ru/themes/hb/img/logo.png\" width=\"189\" height=\"255\" alt=\"lorem\"></body></html>";
		// // webView.loadData(customHtml, "text/html", "UTF-8");
		//
		webView.clearCache(true);
		webView.clearHistory();
		String result = "";
		try {
			result = ServerAPI.getHtml(ServerAPI.getServerURL()
					+ QuestObject.currentQuestObject.url + "index.html");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webView.loadDataWithBaseURL(null, result, "text/html", "utf-8", null);
		// webView.loadUrl();

	}

}
