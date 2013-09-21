package ru.qrushtabs.app.utils;

import java.util.ArrayList;

import android.app.Activity;


public class ActivitiesStack {
	
	private static ArrayList<Activity> activities = new ArrayList<Activity>();
	public static void addActivity(Activity a)
	{
		activities.add(a);
	}
	
	public static void finishAll()
	{
		for(int i = 0;i<activities.size();i++)
		activities.get(i).finish();
		activities.clear();
	}

}
