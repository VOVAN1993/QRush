package ru.qrushtabs.app;

import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Logger.LogLevel;
import com.google.analytics.tracking.android.Tracker;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class QrushTabsApp extends Application {
	  private static Context context;

	  
	  private static GoogleAnalytics mGa;
	  private static Tracker mTracker;

	  /*
	   * Google Analytics configuration values.
	   */
	  // Placeholder property ID.
	  private static final String GA_PROPERTY_ID = "UA-44683951-1";

	  // Dispatch period in seconds.
	  private static final int GA_DISPATCH_PERIOD = 30;

	  // Prevent hits from being sent to reports, i.e. during testing.
	  private static final boolean GA_IS_DRY_RUN = false;

	  // GA Logger verbosity.
	  private static final LogLevel GA_LOG_VERBOSITY = LogLevel.VERBOSE;

	  // Key used to store a user's tracking preferences in SharedPreferences.
	  private static final String TRACKING_PREF_KEY = "trackingPreference";
	    public void onCreate(){
	        super.onCreate();
	        QrushTabsApp.context = getApplicationContext();
	        
	        initializeGa();
	         
	    }

	    private void initializeGa() {
	        mGa = GoogleAnalytics.getInstance(this);
	        
	        mTracker = mGa.getTracker(GA_PROPERTY_ID);

	        // Set dispatch period.
	        GAServiceManager.getInstance().setLocalDispatchPeriod(GA_DISPATCH_PERIOD);
 	        // Set dryRun flag.
	       // mGa.setDryRun(GA_IS_DRY_RUN);

	        // Set Logger verbosity.
	        mGa.getLogger().setLogLevel(GA_LOG_VERBOSITY);

	        // Set the opt out flag when user updates a tracking preference.
	        SharedPreferences userPrefs = PreferenceManager.getDefaultSharedPreferences(this);
	        userPrefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener () {
	          @Override
	          public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
	              String key) {
	            if (key.equals(TRACKING_PREF_KEY)) {
	              GoogleAnalytics.getInstance(getApplicationContext()).setAppOptOut(sharedPreferences.getBoolean(key, false));
	            }
	          }
	        });
	      }
	    
	   

	    /*
	     * Returns the Google Analytics tracker.
	     */
	    public static Tracker getGaTracker() {
	      return mTracker;
	    }

	    public static GoogleAnalytics getGaInstance() {
	        return mGa;
	      }
	    public static Context getAppContext() {
	        return QrushTabsApp.context;
	    }
}
