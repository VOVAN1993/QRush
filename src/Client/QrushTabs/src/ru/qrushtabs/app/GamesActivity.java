package ru.qrushtabs.app;

import com.google.analytics.tracking.android.MapBuilder;

import ru.qrushtabs.app.ads.VideoAdActivity;
import ru.qrushtabs.app.ads.VideoAdObject;
import ru.qrushtabs.app.ads.VideoLoader;
import ru.qrushtabs.app.dialogs.LoseDialog;
import ru.qrushtabs.app.dialogs.MyDialog;
import ru.qrushtabs.app.dialogs.OnDialogClickListener;
import ru.qrushtabs.app.dialogs.ToTwiceDialog;
import ru.qrushtabs.app.games.ChestsRenderer;
import ru.qrushtabs.app.games.GameRenderer;
import ru.qrushtabs.app.games.MatchesRenderer;
import ru.qrushtabs.app.games.OnGameEndListener;
import ru.qrushtabs.app.games.RouletteRenderer;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.quests.QuestObject;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.SharedPrefsAPI;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GamesActivity extends MyVungleActivity {

	GameRenderer game;
	LinearLayout gamesLayout;
	int currentPrize = 0;
	String currentScan = "0";
	OnDialogClickListener onDialogAfterMatchClick = new OnDialogClickListener() {

		@Override
		public void onOkClick() {

			QrushTabsApp.getGaTracker().send(
					MapBuilder.createEvent(GoogleConsts.GAME_ACTION, // Event
							// category
							// (required)
							GoogleConsts.TRY_TO_DOUBLE, // Event action (required)
							null, // Event label
							0l) // Event value
							.build());
			game.pause();
			GamesActivity g = GamesActivity.this;
			g.gamesLayout.removeView(game);
			game = RouletteRenderer.getInstance(g);
			g.gamesLayout.addView(game);
			prizeTV.setText(String.valueOf(Integer.valueOf(GamesActivity.this
					.getIntent().getStringExtra("maxPrize")) * 2));
			g.gamesLayout.invalidate();
			game.setOnGameEndListener(onRouletteEnd);
			String isWin = GamesActivity.this.getIntent().getStringExtra(
					"isTwice");
			Log.d("isTwice", isWin);
			if (isWin.equals("True"))
				game.isWin = true;
			else
				game.isWin = false;
			game.resume();

		}

		@Override
		public void onCancelClick() {
			GamesActivity.this.finish();
			if (ServerAPI.tryAddMoneyForScan(
					GamesActivity.this.getIntent()
							.getStringExtra("currentScan"),
					String.valueOf(currentPrize)).equals("true")) {
				PrizeActivity.currentPrize = currentPrize;
				
				QrushTabsApp.getGaTracker().send(
						MapBuilder.createEvent(GoogleConsts.GAME_ACTION, // Event
								// category
								// (required)
								GoogleConsts.WIN, // Event action (required)
								null, // Event label
								(long)currentPrize) // Event value
								.build());
			} else {
				PrizeActivity.currentPrize = 0;
			}
			ProfileInfo.addMoneyCount(PrizeActivity.currentPrize);
			SharedPrefsAPI.saveProfileInfo();
			Intent intent = new Intent(GamesActivity.this, PrizeActivity.class);
			startActivity(intent);

		}

	};

	OnDialogClickListener onDialogAfterLoseClick = new OnDialogClickListener() {

		@Override
		public void onOkClick() {
			GamesActivity.this.finish();
			currentPrize = 0;
			PrizeActivity.currentPrize = 0;

			QrushTabsApp.getGaTracker().send(
					MapBuilder.createEvent(GoogleConsts.GAME_ACTION, // Event
							// category
							// (required)
							GoogleConsts.LOSE, // Event action (required)
							null, // Event label
							0l) // Event value
							.build());
			Intent intent = new Intent(GamesActivity.this, PrizeActivity.class);
			startActivity(intent);

		}

		@Override
		public void onCancelClick() {

		}

	};
	OnDialogClickListener onDialogAfterRouletteClick = new OnDialogClickListener() {

		@Override
		public void onOkClick() {
			GamesActivity.this.finish();
			if (ServerAPI.tryAddMoneyForScan(
					GamesActivity.this.getIntent()
							.getStringExtra("currentScan"),
					String.valueOf(currentPrize)).equals("true")) {
				PrizeActivity.currentPrize = currentPrize;
				
				QrushTabsApp.getGaTracker().send(
						MapBuilder.createEvent(GoogleConsts.GAME_ACTION, // Event
								// category
								// (required)
								GoogleConsts.DOUBLE_WIN, // Event action (required)
								null, // Event label
								(long)currentPrize) // Event value
								.build());
				
			} else {
				PrizeActivity.currentPrize = 0;
			}
			ProfileInfo.addMoneyCount(PrizeActivity.currentPrize);
			SharedPrefsAPI.saveProfileInfo();
			Intent intent = new Intent(GamesActivity.this, PrizeActivity.class);
			startActivity(intent);

		}

		@Override
		public void onCancelClick() {
		}

	};
	OnGameEndListener onMatchEnd = new OnGameEndListener() {

		@Override
		public void onGameEnd(boolean isWin) {

			game.pause();
			// gamesLayout.removeView(gameRenderer);
			MyDialog newFragment;
			if (isWin) {
				currentPrize += Integer.valueOf(GamesActivity.this.getIntent()
						.getStringExtra("prize"));

				newFragment = new ToTwiceDialog();
				newFragment.setLabelText(currentPrize + "");
				newFragment.setOnDialogClickListener(onDialogAfterMatchClick);
				newFragment.show(getSupportFragmentManager(), "missiles");

			} else {
				currentPrize = 0;
				newFragment = new LoseDialog();
				newFragment.setOnDialogClickListener(onDialogAfterLoseClick);
				newFragment.show(getSupportFragmentManager(), "missiles");
			}

		}

	};
	OnGameEndListener onRouletteEnd = new OnGameEndListener() {

		@Override
		public void onGameEnd(boolean isWin) {

			game.pause();
			// gamesLayout.removeView(gameRenderer);
			MyDialog newFragment;
			if (isWin) {
				currentPrize *= 2;
				newFragment = new LoseDialog();
				newFragment
						.setOnDialogClickListener(onDialogAfterRouletteClick);
				newFragment.setLabelText("Вы выйграли");
				newFragment.show(getSupportFragmentManager(), "missiles");

			} else {
				currentPrize = 0;
				newFragment = new LoseDialog();
				newFragment.setOnDialogClickListener(onDialogAfterLoseClick);
				newFragment.show(getSupportFragmentManager(), "missiles");
			}

		}

	};

	TextView prizeTV;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		game = ChestsRenderer.getInstance(this);
		// game.prize = GamesActivity.this.getIntent().getStringExtra("prize");
		setContentView(R.layout.games);
		gamesLayout = (LinearLayout) findViewById(R.id.games_layout);

		prizeTV = (TextView) findViewById(R.id.games_prize_tv);
		prizeTV.setText(GamesActivity.this.getIntent().getStringExtra(
				"maxPrize"));
		int prize = Integer.valueOf(GamesActivity.this.getIntent()
				.getStringExtra("prize"));
		if (prize > 0)
			game.isWin = true;
		else
			game.isWin = false;
		game.setOnGameEndListener(onMatchEnd);

	}

	@Override
	public void onResume() {
		super.onResume();

		prizeTV.setText(GamesActivity.this.getIntent().getStringExtra(
				"maxPrize"));
		// if(((ProfileInfo.getScansCount() +
		// ProfileInfo.getRescansCount())%5)==0)
		// {
		//
		// Intent intent = new Intent(this,VideoAdActivity.class);
		// startActivityForResult(intent,VIDEO_CODE);
		// //watched = true;
		//
		// }
		// else
		// {
		// execute
		gamesLayout.addView(game);
		game.resume();
		// }
	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode,
	// Intent imageReturnedIntent) {
	// super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
	// if(resultCode == VideoAdObject.WATCHED)
	// {
	// //execute
	// }
	// else
	// finish();
	//
	// }
	@Override
	public void onPause() {
		Log.d("games", "gamesactivity pause");
		super.onPause();
		game.pause();
		if (game.getParent() != null)
			gamesLayout.removeView(game);

	}

	// private class AddScanTask extends AsyncTask<String, String, PrizeObject>
	// {
	//
	// protected PrizeObject doInBackground(String... args) {
	// return ServerAPI.tryAddScanForMoney(args[0],"rescan");
	// }
	//
	// protected void onPostExecute(PrizeObject objResult) {
	//
	//
	// AnimationDrawable anim = (AnimationDrawable)
	// NewsContentView.this.arrowsImg
	// .getBackground();
	//
	// anim.stop();
	// NewsContentView.this.arrowsImg.setVisibility(View.INVISIBLE);
	// if (objResult!=null) {
	//
	//
	// ProfileInfo.addScan(NewsContentView.this.scanObject.code,ScanObject.RESCAN);
	// Intent intent = new Intent(NewsContentView.this.context,
	// GamesActivity.class);
	// intent.putExtra("prize", objResult.prize);
	// intent.putExtra("currentScan", NewsContentView.this.scanObject.code);
	// intent.putExtra("isTwice", objResult.isTwice);
	// intent.putExtra("maxPrize", objResult.maxPrize);
	// QuestObject.checkRescanOnActiveQuests();
	// //rowView.setVisibility(View.GONE);
	// NewsContentView.this.scanObject.scanned = true;
	// NewsContentView.this.context.startActivity(intent);
	// } else {
	// // Intent intent = new Intent(NewsContentView.this.context,
	// // EnterActivity.class);
	// // NewsContentView.this.context.startActivity(intent);
	// }
	// }
	// }

}
