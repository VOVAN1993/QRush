package ru.qrushtabs.app;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ru.qrushtabs.app.utils.SQLiteAPI;

public class ScanBox 
{

	private static ArrayList<String> scansInfo;
	private static SQLiteAPI sqLiteAPI; 
 	private static SQLiteDatabase sqdb;
	public static void addScan(String scanInfo)
	{
		 scansInfo.add(scanInfo);
    }
	public static ArrayList<String> getScansInfo()
	{
		 return scansInfo;
	}
	public static void loadScans(Context context)
	{
		
		sqLiteAPI = new SQLiteAPI(context);
		sqdb = sqLiteAPI.getWritableDatabase();
		scansInfo = new ArrayList<String>();
		String query = "SELECT " + SQLiteAPI._ID + ", "
				+ SQLiteAPI.SCAN_INFO + " FROM " + SQLiteAPI.TABLE_NAME;
		Cursor cursor = sqdb.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor
					.getColumnIndex(SQLiteAPI._ID));
			String name = cursor.getString(cursor
					.getColumnIndex(SQLiteAPI.SCAN_INFO));
			scansInfo.add(name);
			Log.i("LOG_TAG", "ROW " + id + " HAS NAME " + name);
		}
		sqdb.delete(SQLiteAPI.TABLE_NAME, SQLiteAPI._ID+" > 0", null);
	}
	public static void saveScans()
	{
		int l = scansInfo.size();
		for(int i = 0;i < l;i++)
		{
			String insertQuery = "INSERT INTO " + 
					sqLiteAPI.TABLE_NAME + 
				     " (" + sqLiteAPI.SCAN_INFO + ") VALUES ('" + scansInfo.get(i)+ "')";
				    sqdb.execSQL(insertQuery);
		}
		sqdb.close();
		sqLiteAPI.close();
	}
}
