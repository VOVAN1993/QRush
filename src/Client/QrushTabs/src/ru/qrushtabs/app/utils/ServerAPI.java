package ru.qrushtabs.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class ServerAPI 
{
	public static void loadProfileInfo()
	{
		
	}
	public static void loadFriendsInfo()
	{
		
	}
	public static void loadRatingsInfo()
	{
		
	}
	public static boolean isOnline(Context c) {
	    ConnectivityManager cm =
	        (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

	    return cm.getActiveNetworkInfo() != null && 
	       cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}
}
