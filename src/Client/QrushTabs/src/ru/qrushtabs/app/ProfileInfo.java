package ru.qrushtabs.app;

import ru.qrushtabs.app.utils.OnInfoLoadListener;

public class ProfileInfo {

	private static int scansCount = 4;
	private static float moneyCount = 34.50f ; 
	private static boolean loaded = true;
	public static int getScansCount()
	{
		if(loaded)
		    return scansCount;
		else
			return 0;
	}
	public static float getMoneyCount()
	{
		if(loaded)
		    return moneyCount;
		else
			return 0;
	}
	public static void addScan(String scanInfo)
	{
		ProfileInfo.scansCount++;
		//���������� �� ������ � � ������
	}
	public static void setScansCount(int scansCount)
	{
		ProfileInfo.scansCount = scansCount;
		//���������� �� ������ � � ������
	}
	public static void setMoneyCount(int moneyCount)
	{
		ProfileInfo.moneyCount = moneyCount;
		//���������� �� ������ � � ������
	}
	
	public static void saveInfo(OnInfoLoadListener l)
	{
		
	}
	public static void loadInfo(OnInfoLoadListener l)
	{
		
	}
}
