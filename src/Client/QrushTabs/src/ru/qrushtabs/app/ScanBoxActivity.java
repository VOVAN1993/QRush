package ru.qrushtabs.app;

import java.util.ArrayList;

import ru.qrushtabs.app.utils.SQLiteAPI;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

public class ScanBoxActivity extends Activity {

	private ArrayList<String> scansInfo;
	ScanBoxArrayAdapter scansInfoAdapter;
	private SQLiteAPI sqLiteAPI; 
	private Context context;
	private SQLiteDatabase sqdb;
	private ListView lv;
	private static ScanBoxActivity instance;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.scan_box);
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
		 //loadScans();
 		instance = this;
		lv = (ListView) findViewById(R.id.scan_box_list);
		scansInfo = ScanBox.getScansInfo();
		scansInfoAdapter = new ScanBoxArrayAdapter(this,scansInfo);
		
		lv.setAdapter(scansInfoAdapter);

	}
	
	public void addScan(String scanInfo)
	{
		 
		scansInfoAdapter.add(scanInfo);
	}
	
	public void loadScans()
	{
		sqLiteAPI = new SQLiteAPI(context);
		sqdb = sqLiteAPI.getWritableDatabase();
		
		
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
	}
	public void saveScans()
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
	public static ScanBoxActivity getInstance()
	{
		return instance;
	}
}
