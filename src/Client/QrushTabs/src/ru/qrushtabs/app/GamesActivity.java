package ru.qrushtabs.app;

import ru.qrushtabs.app.dialogs.LoseDialog;
import ru.qrushtabs.app.dialogs.MyDialog;
import ru.qrushtabs.app.dialogs.OnDialogClickListener;
import ru.qrushtabs.app.dialogs.ToTwiceDialog;
import ru.qrushtabs.app.games.GameRenderer;
import ru.qrushtabs.app.games.MatchesRenderer;
import ru.qrushtabs.app.games.OnGameEndListener;
import ru.qrushtabs.app.games.RouletteRenderer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GamesActivity extends FragmentActivity {

	GameRenderer game;
	LinearLayout gamesLayout;
	OnDialogClickListener onDialogClick = new OnDialogClickListener()
	{

		@Override
		public void onOkClick() 
		{
			//GamesActivity.this.finish();
			game.pause();
			GamesActivity g = GamesActivity.this;
			g.gamesLayout.removeAllViews();
			game = RouletteRenderer.getInstance(g);
			g.gamesLayout.addView(game);
			game.resume();
			
		}

		@Override
		public void onCancelClick() {
			GamesActivity.this.finish();
			Intent intent = new Intent(GamesActivity.this,PrizeActivity.class);
			startActivity(intent);
			
		}
		
	};
 	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//		
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
//				R.layout.custom_title_back);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		game = MatchesRenderer.getInstance(this);
		setContentView(R.layout.games);
		gamesLayout = (LinearLayout)findViewById(R.id.games_layout);
			
		OnGameEndListener l = new OnGameEndListener()
		{

			@Override
			public void onGameEnd(boolean isWin) {
				
				
 				game.pause();
				//gamesLayout.removeView(gameRenderer);
 				 MyDialog newFragment;
				 if(isWin)
					 newFragment = new ToTwiceDialog();
				 else
					 newFragment = new LoseDialog();
				 
				 newFragment.setOnDialogClickListener(onDialogClick);
 				 newFragment.show(getSupportFragmentManager(), "missiles");
				
			}
			
		};
		game.setOnGameEndListener(l);
 		//gamesLayout.addView(gameRenderer);
		
		
//		if(gameRenderer.getParent()!=null)
//			gamesLayout.removeView(gameRenderer);
//		setContentView(gameRenderer);
		//gamesLayout.removeAllViews();
		//
		// addView(gameRenderer);
	}

	protected void onResume() {
		super.onResume();
		gamesLayout.addView(game);
		game.resume();
	}

	protected void onPause() {
		super.onPause();
		game.pause();
		gamesLayout.removeView(game);
		
	}

}
