package ru.qrushtabs.app;

import android.app.Application;
import android.content.Context;

public class QrushTabsApp extends Application {
	  private static Context context;

	    public void onCreate(){
	        super.onCreate();
	        QrushTabsApp.context = getApplicationContext();
	    }

	    public static Context getAppContext() {
	        return QrushTabsApp.context;
	    }
}
