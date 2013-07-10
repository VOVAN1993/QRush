package ru.qrushtabs.app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class SQLiteAPI extends SQLiteOpenHelper implements BaseColumns {

	private static final String DATABASE_NAME = "qrush.db";
	public static final String TABLE_NAME = "scans_table";
	private static final int DATABASE_VERSION = 1;
 	public static final String SCAN_INFO = "scan_info";
	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ TABLE_NAME + " (" + SQLiteAPI._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ SCAN_INFO + " VARCHAR(255));";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;
	
	
	public SQLiteAPI(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
 

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("LOG_TAG", "Обновление базы данных с версии " + oldVersion
				+ " до версии " + newVersion + ", которое удалит все старые данные");
		// Удаляем предыдущую таблицу при апгрейде
		db.execSQL(SQL_DELETE_ENTRIES);
		// Создаём новый экземпляр таблицы
		onCreate(db);

	}

}
