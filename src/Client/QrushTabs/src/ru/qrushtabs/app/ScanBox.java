package ru.qrushtabs.app;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ru.qrushtabs.app.dialogs.BlackAlertDialog;
import ru.qrushtabs.app.dialogs.OnDialogClickListener;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.quests.QuestObject;
import ru.qrushtabs.app.utils.SQLiteAPI;
import ru.qrushtabs.app.utils.ServerAPI;

public class ScanBox {

	private static ArrayList<ScanObject> scansInfo;
	private static SQLiteAPI sqLiteAPI;
	private static SQLiteDatabase sqdb;

	public static void addScan(ScanObject scanInfo,CameraActivity context) {
		
		int scanStatus = 0;
		if (!ServerAPI.offlineMod && ServerAPI.isOnline()) {
			PrizeObject po = ServerAPI.tryAddScanForMoney(scanInfo.code,"scan");
			if (po!=null) {
				scanStatus =  2;
				ProfileInfo.addScan(scanInfo.code);
				ServerAPI.saveProfileInfo();
				Intent intent = new Intent(context,
						GamesActivity.class);
				intent.putExtra("prize", po.prize);
				intent.putExtra("currentScan", scanInfo.code);
				intent.putExtra("isTwice", po.isTwice);
				intent.putExtra("maxPrize", po.maxPrize);
				QuestObject.checkScanOnActiveQuests(scanInfo.code);
				context.startActivity(intent);
 			} else {
				scanStatus =  0;
			}
		}

		else if (scansInfo.indexOf(scanInfo) == -1) {
			scansInfo.add(scanInfo);
			scanStatus =  1;
		} else {
			scanStatus =  0;
		}
		
		if (scanStatus == 2) {

			 
			
		} else {
			BlackAlertDialog newFragment;
			newFragment = new BlackAlertDialog();
			if (scanStatus == 1) {
				newFragment.setLabelText("Сохранен в сундук");
  				newFragment.show(context.getSupportFragmentManager(),
						"missiles");
			} else {
				newFragment.setLabelText("Такой скан уже был");
				newFragment.show(context.getSupportFragmentManager(),
						"missiles");
				newFragment.setDrawableBackground(context.getResources().getDrawable(R.drawable.black_alert_rescan));

			}

			newFragment.setOnDialogClickListener(context.onDialogClick);
		}
		 

	}

	public static void removeScan(ScanObject so)
	{
		scansInfo.remove(so);
	}
	public static ArrayList<ScanObject> getScansInfo() {
		return scansInfo;
	}

	public static void loadScans(Context context) {

		sqLiteAPI = new SQLiteAPI(context);
		sqdb = sqLiteAPI.getWritableDatabase();
		scansInfo = new ArrayList<ScanObject>();
		String query = "SELECT " + SQLiteAPI._ID + ", " + SQLiteAPI.SCAN_INFO
				+ " FROM " + SQLiteAPI.TABLE_NAME;
		Cursor cursor = sqdb.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex(SQLiteAPI._ID));
			String name = cursor.getString(cursor
					.getColumnIndex(SQLiteAPI.SCAN_INFO));
			ScanObject so = new ScanObject();
			so.code = name;
			scansInfo.add(so);
			Log.i("LOG_TAG", "ROW " + id + " HAS NAME " + name);
		}
		sqdb.delete(SQLiteAPI.TABLE_NAME, SQLiteAPI._ID + " > 0", null);
	}

	public static void saveScans() {
		int l = scansInfo.size();
		for (int i = 0; i < l; i++) {
			String insertQuery = "INSERT INTO " + sqLiteAPI.TABLE_NAME + " ("
					+ sqLiteAPI.SCAN_INFO + ") VALUES ('" + scansInfo.get(i).code
					+ "')";
			sqdb.execSQL(insertQuery);
		}
		sqdb.close();
		sqLiteAPI.close();
	}
}
