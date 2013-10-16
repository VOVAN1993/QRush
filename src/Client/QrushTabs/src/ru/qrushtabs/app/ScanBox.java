package ru.qrushtabs.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.analytics.tracking.android.MapBuilder;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import ru.qrushtabs.app.ads.VideoAdActivity;
import ru.qrushtabs.app.ads.VideoAdObject;
import ru.qrushtabs.app.dialogs.BlackAlertDialog;
import ru.qrushtabs.app.dialogs.OnDialogClickListener;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.quests.QuestObject;
import ru.qrushtabs.app.utils.SQLiteAPI;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.SharedPrefsAPI;

public class ScanBox {

	private static ArrayList<ScanObject> scansInfo;
	private static SQLiteAPI sqLiteAPI;
	private static SQLiteDatabase sqdb;

	public static void addScanToBox(ScanObject scanInfo,
			MyVungleActivity context) {
		scansInfo.add(scanInfo);
		BlackAlertDialog newFragment;
		newFragment = new BlackAlertDialog();
		newFragment.setLabelText("Сохранен в сундук");
		newFragment.show(context.getSupportFragmentManager(), "missiles");
		MainActivity.getInstance().refreshBoxButton();
	}

	private static ScanObject savedScanObjectBeforeWatch;

	public static void addScanAfterWatch(MyVungleActivity context) {
		if (savedScanObjectBeforeWatch != null) {

			addScan(savedScanObjectBeforeWatch, context);
			savedScanObjectBeforeWatch = null;
		}
	}

	public static void addScan(ScanObject scanInfo, MyVungleActivity context) {

		if (ProfileInfo.haveScan(scanInfo.code)) {
			QrushTabsApp.getGaTracker().send(
					MapBuilder.createEvent(ScanObject.SCAN_ACTION, // Event
																	// category
																	// (required)
							ScanObject.FAILED_SCAN, // Event action (required)
							scanInfo.code, // Event label
							null) // Event value
							.build());
			BlackAlertDialog newFragment;
			newFragment = new BlackAlertDialog();

			ScanBox.removeScan(scanInfo);
			newFragment.setLabelText("Такой скан уже отправляли");
			newFragment.show(context.getSupportFragmentManager(), "missiles");
			newFragment.setDrawableBackground(context.getResources()
					.getDrawable(R.drawable.black_alert_rescan));

			newFragment.setOnDialogClickListener(context.onDialogClick);
			return;
		}
		if (!ServerAPI.offlineMod && ServerAPI.isOnline()) {
			if (((ProfileInfo.getScansCount() + 1 + ProfileInfo
					.getRescansCount()) % 5) == 0
					&& !VideoAdObject.lastIsWatched) {
				savedScanObjectBeforeWatch = scanInfo;
				Intent intent = new Intent(context, VideoAdActivity.class);
				context.startActivity(intent);
				return;

			}
			VideoAdObject.lastIsWatched = false;
			PrizeObject po = ServerAPI.tryAddScanForMoney(scanInfo.code,
					scanInfo.scantype);
			if (po != null) {

				QrushTabsApp.getGaTracker().send(
						MapBuilder.createEvent(GoogleConsts.SCAN_ACTION, // Event
																		// category
																		// (required)
								scanInfo.scantype, // Event action (required)
								scanInfo.code, // Event label
								null) // Event value
								.build());

				ProfileInfo.addScan(scanInfo.code, scanInfo.scantype);
				SharedPrefsAPI.saveProfileInfo();
				Intent intent = new Intent(context, GamesActivity.class);
				intent.putExtra("prize", po.prize);
				intent.putExtra("currentScan", scanInfo.code);
				intent.putExtra("isTwice", po.isTwice);
				intent.putExtra("maxPrize", po.maxPrize);
				if(scanInfo.scantype.equals(ScanObject.SCAN))
					QuestObject.checkScanOnActiveQuests(scanInfo.code);
				else
					QuestObject.checkRescanOnActiveQuests();

				context.startActivity(intent);
			} else {

				BlackAlertDialog newFragment;
				newFragment = new BlackAlertDialog();

				newFragment.setLabelText("Такой скан уже отправляли");
				newFragment.show(context.getSupportFragmentManager(),
						"missiles");
				newFragment.setDrawableBackground(context.getResources()
						.getDrawable(R.drawable.black_alert_rescan));

				newFragment.setOnDialogClickListener(context.onDialogClick);
			}
			ScanBox.removeScan(scanInfo);
		}

		else if (scansInfo.indexOf(scanInfo) == -1) {
			addScanToBox(scanInfo, context);

		} else {
			BlackAlertDialog newFragment;
			newFragment = new BlackAlertDialog();

			newFragment.setLabelText("Такой скан уже есть в сундуке");
			newFragment.show(context.getSupportFragmentManager(), "missiles");
			newFragment.setDrawableBackground(context.getResources()
					.getDrawable(R.drawable.black_alert_rescan));

			newFragment.setOnDialogClickListener(context.onDialogClick);
		}

	}

	public static void removeScan(ScanObject so) {
		scansInfo.remove(so);
	}

	public static ArrayList<ScanObject> getScansInfo() {
		return scansInfo;
	}

	public static void loadScans(Context context) {

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(QrushTabsApp.getAppContext());
		JSONArray scans = null;

		scansInfo = new ArrayList<ScanObject>();
		try {
			scans = new JSONArray(prefs.getString("scanBox_"
					+ ProfileInfo.username, "[]"));
			for (int i = 0; i < scans.length(); i++) {
				ScanObject so = new ScanObject();
				so.code = (String) scans.get(i);
				scansInfo.add(so);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveScans() {

		JSONArray editor = new JSONArray();

		for (int i = 0; i < scansInfo.size(); i++)
			editor.put(scansInfo.get(i).code);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(QrushTabsApp.getAppContext());
		Editor peditor = prefs.edit();
		peditor.putString("scanBox_" + ProfileInfo.username, editor.toString());
		peditor.commit();

	}
}
