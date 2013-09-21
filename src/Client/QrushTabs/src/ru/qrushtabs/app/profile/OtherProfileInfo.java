package ru.qrushtabs.app.profile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import ru.qrushtabs.app.friends.FriendField;
import ru.qrushtabs.app.utils.OnInfoLoadListener;

public class OtherProfileInfo {

	public static final int maxPhotoWidth = 200;
	public static final int maxPhotoHeight = 200;
	
	private   int scansCount = 0;
	private   int rescansCount = 0;
	private   int moneyCount = 0 ; 
	
 	public   String sex = "M";
 	public   String username = "0";
  	public   Bitmap avatarBitmap = null;
 	public   String avatarPath = "0";
 	public   File avatarFile = null;
 	
 	public   List<String> scansList = new ArrayList<String>();
 	public   FriendField[] friendsList;;
 	public   boolean friendsChanged = true;

  	
 	public   String city = "ђепрежевальск";
 	public   String cityId = "0";
 	
 	public   boolean haveScan(String scan)
 	{
 		for(int i = 0;i<scansList.size();i++)
 			if(scansList.get(i).equals(scan))
 				return true;
 		return false;
 	}
 	
  
 	
  
	public   int getScansCount()
	{
		 
		    return scansCount;
		 
	}
	public   int getRescansCount()
	{
		 
		    return rescansCount;
		 
	}
	public   int getMoneyCount()
	{
		 
		    return moneyCount;
		 
	}
 
	public   void setScansCount(int scansCount)
	{
		this.scansCount = scansCount;
		//добавление на сервер и в склайт
	}
	public   void setRescansCount(int rescansCount)
	{
		this.rescansCount = rescansCount;
		//добавление на сервер и в склайт
	}
	public   void setMoneyCount(int moneyCount)
	{
		this.moneyCount = moneyCount;
		//добавление на сервер и в склайт
	}
 
	
 
}
