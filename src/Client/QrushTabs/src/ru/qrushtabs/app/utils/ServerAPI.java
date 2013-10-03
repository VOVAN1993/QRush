package ru.qrushtabs.app.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.*;

import ru.qrushtabs.app.PrizeActivity;
import ru.qrushtabs.app.PrizeObject;
import ru.qrushtabs.app.QrushTabsApp;
import ru.qrushtabs.app.RatingField;
import ru.qrushtabs.app.RegistrationActivity;
import ru.qrushtabs.app.ScanObject;
import ru.qrushtabs.app.profile.OtherProfileInfo;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.quests.QuestObject;
import ru.qrushtabs.app.quests.TasksObject;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class ServerAPI {
	
	public static boolean offlineMod = true;

	// private static String executeHttpResponse(String query,
	// List<NameValuePair> values) {
	// HttpClient client = new DefaultHttpClient();
	// HttpGet request = new HttpGet("http://188.120.235.179/" + query + "/");
	// HttpResponse response = null;
	// try {
	// response = client.execute(request);
	// } catch (ClientProtocolException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// BufferedReader rd = null;
	// StringBuilder sb = new StringBuilder("");
	// try {
	// rd = new BufferedReader(new InputStreamReader(response.getEntity()
	// .getContent()));
	//
	// String line = "";
	// while ((line = rd.readLine()) != null) {
	// sb.append(line);
	//
	// }
	// } catch (IllegalStateException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// return sb.toString();
	// }

	public static String getServerURL()
	{
		return "http://188.120.235.179";
	}
	 public static String getHtml(String s) throws ClientProtocolException, IOException
	 {
	     HttpClient httpClient = new DefaultHttpClient();
	     HttpContext localContext = new BasicHttpContext();
	     HttpGet httpGet = new HttpGet(s);
	     HttpResponse response = httpClient.execute(httpGet, localContext);
	     String result = "";

	     BufferedReader reader = new BufferedReader(
	         new InputStreamReader(
	           response.getEntity().getContent()
	         )
	       );

	     String line = null;
	     while ((line = reader.readLine()) != null){
	       result += line + "\n";
	        

	     }
	     return result;

	 }
	 

	private static String executeHttpPostResponse(String query,
			List<NameValuePair> values) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost("http://188.120.235.179/" + query + "/");
		HttpResponse response = null;
		
		values.add(new BasicNameValuePair("username", ProfileInfo.username));
		values.add(new BasicNameValuePair("atoken", ProfileInfo.userToken));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(values, HTTP.ASCII));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d("http", "Login form get: " + response.getStatusLine());

		BufferedReader rd = null;
		StringBuilder sb = new StringBuilder("");
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				Log.d("http", line);
				sb.append(line);
			}
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		httpclient.getConnectionManager().shutdown();

		String resp = sb.toString();
		String report = "";
		try {
			Log.d("http", resp);
			
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) 
			{
				if (jsonObj.has("atoken"))
					ProfileInfo.userToken = (String) jsonObj.get("atoken");

				
			} else {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	public static String signUp() {

		String req = "add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("what", "user"));
		nvps.add(new BasicNameValuePair("email", ProfileInfo.mail));
		nvps.add(new BasicNameValuePair("sex", ProfileInfo.sex));
		nvps.add(new BasicNameValuePair("cityid", ProfileInfo.cityId));
		nvps.add(new BasicNameValuePair("birthday", ProfileInfo.birthday));
		nvps.add(new BasicNameValuePair("deviceid", ProfileInfo.deviceID));
		if (ProfileInfo.signInType.equals("vk")) {
			nvps.add(new BasicNameValuePair("userid", ProfileInfo.userVKID));
			nvps.add(new BasicNameValuePair("vktoken", ProfileInfo.userVKToken));
		} else {
			nvps.add(new BasicNameValuePair("password", ProfileInfo.userPass));
		}

		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		try {
			Log.d("http", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http on regist", success);
		if (success.equals("success")) {
			return "true";
		} else
			return "false";
	}

	public static String signin() {

		String req = "signin";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (ProfileInfo.signInType.equals("vk")) {
			nvps.add(new BasicNameValuePair("userid", ProfileInfo.userVKID));
			nvps.add(new BasicNameValuePair("vktoken", ProfileInfo.userVKToken));

		} else {
			nvps.add(new BasicNameValuePair("email", ProfileInfo.mail));
			nvps.add(new BasicNameValuePair("password", ProfileInfo.userPass));

		}
		nvps.add(new BasicNameValuePair("signintype", ProfileInfo.signInType));

		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		try {
			Log.d("http", "signin");
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			ProfileInfo.username = (String) jsonObj.get("username");

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (success.equals("success")) {
			loadProfileInfo(ProfileInfo.username);
			return "true";
		} else
			return "false";
	}

	public static String loadProfileInfo(String user) {

		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "status"));
		nvps.add(new BasicNameValuePair("otheruername", user));
		String resp = executeHttpPostResponse(req, nvps);
		String report = "";
		String money = "";
		String scans = "";
		String rescans = "";

		try {
			Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				money = (String) jsonObj.get("balance");
				scans = (String) jsonObj.get("count_scan");
				rescans = (String) jsonObj.get("count_rescan");
				ProfileInfo.setScansCount(Integer.valueOf(scans));
				ProfileInfo.setMoneyCount(Integer.valueOf(money));
				ProfileInfo.setRescansCount(Integer.valueOf(rescans));
				ServerAPI.saveProfileInfo();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http loadInfo", report);
		if (report.equals("success")) {
			Log.d("http money", money);
			return "true";
		} else
			return "false";
	}

	public static OtherProfileInfo loadOtherProfileInfo(String user) {

		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "status"));
		nvps.add(new BasicNameValuePair("otherUserName", user));
		String resp = executeHttpPostResponse(req, nvps);
		String report = "";
		String money = "";
		String scans = "";
		String rescans = "";

		try {
			Log.d("http load other profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			Log.d("http loadOtherInfo", report);

			if (report.equals("success")) {
				money = (String) jsonObj.get("balance");
				scans = (String) jsonObj.get("count_scan");
				rescans = (String) jsonObj.get("count_rescan");
				OtherProfileInfo opi = new OtherProfileInfo();
				opi.setScansCount(Integer.valueOf(scans));
				opi.setMoneyCount(Integer.valueOf(money));
				opi.setRescansCount(Integer.valueOf(rescans));
				
				return opi;
				//ServerAPI.saveProfileInfo();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		 
			return null;
	}
	public static String tryAddScan(String scan,String scantype) {
		String req = "add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "scan"));
		nvps.add(new BasicNameValuePair("scan", scan));
		nvps.add(new BasicNameValuePair("scantype", scantype));

		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		try {
			Log.d("http load resp", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http loadScan", success);
		if (success.equals("success")) {
			return "true";
		} else
			return "false";
	}
	
	public static Bitmap loadBitmap(String url) {
		String urldisplay = url;
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
 	}
	
	public static String tryAddMoneyForScan(String scan) {
		String req = "add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "moneyForScan"));
		nvps.add(new BasicNameValuePair("scan", scan));
 
		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		try {
			Log.d("http load resp", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http loadScan", success);
		if (success.equals("success")) {
			return "true";
		} else
			return "false";
	}

	public static PrizeObject tryAddScanForMoney(String scan,String scantype) {
		String req = "add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "scanForMoney"));
		nvps.add(new BasicNameValuePair("scan", scan));
		nvps.add(new BasicNameValuePair("scantype", scantype));
 
		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		try {
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			if(success.equals("success"))
			{
				Log.d("http load resp", resp);
				
				
				PrizeObject po = PrizeObject.parse(jsonObj);
				return po;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
 		return null;
	}

//	public static String addMoney(int count) {
//
//		String req = "add";
//		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//		nvps.add(new BasicNameValuePair("what", "money"));
//		nvps.add(new BasicNameValuePair("count", "" + count));
//
//		String resp = executeHttpPostResponse(req, nvps);
//		String success = "";
//		try {
//			Log.d("http   addmoney resp", resp);
//			JSONObject jsonObj = new JSONObject(resp);
//			success = (String) jsonObj.get("report");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Log.d("http http addmony", success);
//		if (success.equals("success")) {
//			return "true";
//		} else
//			return "false";
//	}

	public static String getCountries() {
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "cities"));
		String resp = executeHttpPostResponse(req, nvps);
		String report = "";
		try {
			Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {

				ServerAPI.saveProfileInfo();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static TreeMap<String, String> getCities(String countryID) {
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "cities"));
		nvps.add(new BasicNameValuePair("countryid", "" + countryID));

		String resp = executeHttpPostResponse(req, nvps);
		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {

				String citiesJson = jsonObj.get("cities").toString();
				citiesJson = citiesJson.replace("{", "");
				citiesJson = citiesJson.replace("}", "");
				citiesJson = citiesJson.replace("\"", "");

				// Log.d("cities", citiesJson);
				TreeMap<String, String> citiesMap = new TreeMap<String, String>();

				String pairs[] = citiesJson.split(",");
				for (int i = 0; i < pairs.length; i++) {
					String pair[] = pairs[i].split(":");
					citiesMap.put(pair[0], pair[1]);
				}

				ServerAPI.saveProfileInfo();

				return citiesMap;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static RatingField[] getTopUsers() {
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "topUsers"));

		String resp = executeHttpPostResponse(req, nvps);
		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				JSONArray array = jsonObj.optJSONArray("topUsers");

				RatingField[] ratings = new RatingField[array.length()];
				for (int i = 0; i < array.length(); i++) {
					ratings[i] = RatingField.parse((JSONObject)array.get(array.length() - i - 1));
					 
					 
				}
				ServerAPI.saveProfileInfo();

				return ratings;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static RatingField[] getTopFriends() {
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "topFriends"));

		String resp = executeHttpPostResponse(req, nvps);
		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				JSONArray array = jsonObj.optJSONArray("topFriends");

				RatingField[] ratings = new RatingField[array.length()];
				for (int i = 0; i < array.length(); i++) {
					ratings[i] = RatingField.parse((JSONObject)array.get(array.length() - i - 1));
					 
					 
				}
				ServerAPI.saveProfileInfo();

				return ratings;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String[] searchFriend(String friendName) {
		
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "users"));
		nvps.add(new BasicNameValuePair("pattern", friendName));
		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				JSONArray array = jsonObj.optJSONArray("users");

				String[] users = new String[array.length()];
				for (int i = 0; i < array.length(); i++) {
					users[i] = array.get(i).toString();
				}
				ServerAPI.saveProfileInfo();

				return users;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String addFriend(String friendName) {
		if(friendName.equals(ProfileInfo.username))
			return "false";
		String req = "add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "friendshipRequest"));
		nvps.add(new BasicNameValuePair("tousername", friendName));
		String resp = executeHttpPostResponse(req, nvps);
		String report = "";
		try {
			JSONObject jsonObj = new JSONObject(resp);
			report = jsonObj.getString("report");
			if(report.equals("success"))
				return "true";
			else
				return "false";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}

	public static String[] getReqFriends() {
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "friendshipRequests"));
		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			JSONArray array = jsonObj.optJSONArray("friendshipRequests");
			report = (String) jsonObj.get("report");

			String[] users = new String[array.length()];
			for (int i = 0; i < array.length(); i++) {
				users[i] = array.get(i).toString();
			}
			ServerAPI.saveProfileInfo();
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] getMyFriends(String myName,String friendName, int firstPosition,
			int lastPosition) {
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("otherUserName", myName));
		nvps.add(new BasicNameValuePair("what", "friends"));
		nvps.add(new BasicNameValuePair("firstPosition", "0"));
		nvps.add(new BasicNameValuePair("lastPosition", "0"));
		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				JSONArray array = jsonObj.optJSONArray("friends");

				String[] friends = new String[array.length()];
				for (int i = 0; i < array.length(); i++) {
					friends[i] = array.get(i).toString();
				}
				ServerAPI.saveProfileInfo();

				return friends;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<ScanObject> getScans(String username) {
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "scans"));
		nvps.add(new BasicNameValuePair("otherUserName", username));
		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				JSONArray array = jsonObj.optJSONArray("scans");

 
				ArrayList<ScanObject> scans = new ArrayList<ScanObject>();

				for (int i = 0; i < array.length(); i++) {

					 
						ScanObject so = ScanObject.parse((JSONObject) array
								.get(i));
						so.username = ProfileInfo.username;
						 
						scans.add(so);
						ProfileInfo.scansList.add(so.code);
				 
				}
				ServerAPI.saveProfileInfo();

				return scans;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<ScanObject> getFriendsScans() {
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "friendsScans"));
		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				JSONArray array = jsonObj.optJSONArray("scans");

				ArrayList<ScanObject> friendsScans = new ArrayList<ScanObject>();

				for (int i = 0; i < array.length(); i++) {

					JSONObject user = (JSONObject) array.get(i);
					JSONArray userScans = user.optJSONArray("scans");
					for (int j = 0; j < userScans.length(); j++) {
						ScanObject so = ScanObject.parse((JSONObject) userScans
								.get(j));
						so.username = user.get("username").toString();
						so.city = user.get("city").toString();
						friendsScans.add(so);
					}
				}
				ServerAPI.saveProfileInfo();

				return friendsScans;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String changeQuestStatus(String questName,boolean isDaily,String newStatus) {
		String req = "quest";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "changeStatus"));
		nvps.add(new BasicNameValuePair("questname", questName));
		if(isDaily)
			nvps.add(new BasicNameValuePair("questtype", "daily"));
		else
			nvps.add(new BasicNameValuePair("questtype", "some"));
		nvps.add(new BasicNameValuePair("newstatus", newStatus));
 		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				 

				return "true";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}
 
	public static ArrayList<QuestObject> getQuests() {
		String req = "quest";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "getAllQuests"));
		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				JSONArray all = jsonObj.optJSONArray("allSomeQuests");
				JSONArray active = jsonObj.optJSONArray("activeSomeQuests");
				JSONArray completed = jsonObj.optJSONArray("completedSomeQuests");
				
				ArrayList<QuestObject> quests = new ArrayList<QuestObject>();

				for (int i = 0; i < all.length(); i++) 
				{

					JSONObject quest = (JSONObject) all.get(i);
 					QuestObject qo = QuestObject.parse(quest);
 					quests.add(qo);
				}
				for (int i = 0; i < active.length(); i++) {

					JSONObject quest = (JSONObject) active.get(i);
 					QuestObject qo = QuestObject.parse(quest);
 					for (int j = 0; j < all.length(); j++) 
 					{
 						if(quests.get(j).name.equals(qo.name))
 						{
 							quests.get(j).state = QuestObject.ACTIVE;	
 							//QuestObject.questsProgress.add(quests.get(j));
 							
 						}
 					}
 					 
				}
				for (int i = 0; i < completed.length(); i++) {

					JSONObject quest = (JSONObject) completed.get(i);
 					QuestObject qo = QuestObject.parse(quest);
 					for (int j = 0; j < all.length(); j++) 
 					{
 						if(quests.get(j).name.equals(qo.name))
 						quests.get(j).state = QuestObject.COMPLETED;	
 					}
 					 
				}
				ServerAPI.saveProfileInfo();

				return quests;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<ScanObject> getCompletedQuestsForNews(String username) {
		String req = "quest";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "getCompletedQuests"));
		nvps.add(new BasicNameValuePair("otherUserName", username));
		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				 
				JSONArray completed = jsonObj.optJSONArray("quests");
				
				ArrayList<ScanObject> quests = new ArrayList<ScanObject>();

				
				for (int i = 0; i < completed.length(); i++) {

					JSONObject quest = (JSONObject) completed.get(i);
 					//QuestObject qo = QuestObject.parse(quest);
 					 ScanObject qo  = ScanObject.parseQuest(quest);
 					quests.add(qo);
				}
				ServerAPI.saveProfileInfo();

				return quests;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static QuestObject getDailyQuest() {
		String req = "quest";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "getCurrentDailyQuest"));
		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				JSONObject quest = (JSONObject) jsonObj.get("quest");
					QuestObject qo = QuestObject.parse(quest);
 					//QuestObject.addActiveQuest(qo);
					qo.state = QuestObject.ACTIVE;
					qo.isDaily = true;
					return qo;
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	 public static TasksObject getQuestTasks(String s) throws ClientProtocolException, IOException
	 {
	     HttpClient httpClient = new DefaultHttpClient();
	     HttpContext localContext = new BasicHttpContext();
	     HttpGet httpGet = new HttpGet(s);
	     HttpResponse response = httpClient.execute(httpGet, localContext);
	     String result = "";

	     BufferedReader reader = new BufferedReader(
	         new InputStreamReader(
	           response.getEntity().getContent()
	         )
	       );

	     String line = null;
	     while ((line = reader.readLine()) != null){
	       result += line + "\n";
	        

	     }
	     Log.d("tasks ", result);
	     TasksObject to = null;
		try {
			
			JSONObject obj = new JSONObject(result);
			to = TasksObject.parse(obj.getJSONObject("tasks"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     return to;

	 }
	public static String friendshipRequestRefuse(String userName) {
		String req = "friendshipRequestRefuse";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("refuseUser", userName));
		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");

			if (report.equals("success"))
				return "true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}
	public static String subscribeQuest(String questName) {
		String req = "quest";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "addSubscribeSomeQuest"));
		nvps.add(new BasicNameValuePair("questname", questName));

		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				 
					return "true";
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}
	public static String removeFriend(String userName) {
		String req = "remove";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "friend"));
		nvps.add(new BasicNameValuePair("friendName", userName));
		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");

			if (report.equals("success"))
				return "true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}

	public static String addInfo(List<NameValuePair> nvps) {
		String req = "add";
		nvps.add(new BasicNameValuePair("what", "profileInfo"));
		String resp = executeHttpPostResponse(req, nvps);
		String report = "";
		try {
			Log.d("http add profileInfo", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				ServerAPI.saveProfileInfo();
				return "true";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}

	public static String loadFriendsInfo() {
		return null;
	}

	public static String loadRatingsInfo() {
		return null;
	}

	public static byte[] downloadProfilePhoto(String who) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(
				"http://188.120.235.179/downloadProfilePhoto/");
		HttpResponse response = null;

		List<NameValuePair> values = new ArrayList<NameValuePair>();
		values.add(new BasicNameValuePair("who", who));
		// values.add(new BasicNameValuePair("username", ProfileInfo.username));
		// values.add(new BasicNameValuePair("atoken", ProfileInfo.userToken));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(values, HTTP.ASCII));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d("http", "Login form get: " + response.getStatusLine());

		BufferedReader rd = null;
		InputStream is = null;
		try {
			is = response.getEntity().getContent();
		} catch (IllegalStateException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int contentSize = (int) response.getEntity().getContentLength();
		Log.d("http", "content size " + contentSize);
		if (contentSize < 100)
			return null;
		// System.out.println("Content size ["+contentSize+"]");
		BufferedInputStream bis = new BufferedInputStream(is, 512);
		byte[] data = null;
		data = new byte[contentSize];
		int bytesRead = 0;
		int offset = 0;

		while (bytesRead != -1 && offset < contentSize) {
			try {
				bytesRead = bis.read(data, offset, contentSize - offset);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			offset += bytesRead;
		}

		httpclient.getConnectionManager().shutdown();

		return data;
	}

	
 
	public static void saveProfileInfo() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(QrushTabsApp.getAppContext());
		Editor editor = prefs.edit();
		editor.putString("userPass", ProfileInfo.userPass);
		editor.putString("userID", ProfileInfo.username);
		editor.putString("userToken", ProfileInfo.userToken);
		editor.putString("userVKID", ProfileInfo.userVKID);
		editor.putString("userVKToken", ProfileInfo.userVKToken);
		editor.putString("loginType", ProfileInfo.signInType);

		editor.putInt("scansCount", ProfileInfo.getScansCount());
		editor.putInt("rescansCount", ProfileInfo.getRescansCount());
		editor.putInt("moneyCount", ProfileInfo.getMoneyCount());

		editor.putString("avatarPath", ProfileInfo.avatarPath);
		editor.putString("sex", ProfileInfo.sex);
		editor.putString("email", ProfileInfo.mail);

		editor.putString("city", ProfileInfo.city);
		editor.commit();
	}
	
	public static void loadProgress() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(QrushTabsApp.getAppContext());
		String questsProgress = prefs.getString("questsProgress", "[]");
		Log.d("quest","load "+ questsProgress);
		JSONArray array;
		try {
			array = new JSONArray(questsProgress);
			 
			for(int i = 0;i<array.length();i++)
			{
				JSONObject jo = (JSONObject)array.get(i);
			    QuestObject qo = QuestObject.getActiveQuest(jo.getString("questId"));
			    if(qo!=null)
			    	qo.tasksProgress = TasksObject.parse((JSONObject)jo.get("tasks"));;
 					 
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void saveProgress() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(QrushTabsApp.getAppContext());
		Editor editor = prefs.edit();
		JSONArray array = new JSONArray();
		ArrayList<QuestObject> al = QuestObject.getActiveQuests();
		for(int i = 0; i < al.size();i++)
		{
			 
			try {
				
				if(al.get(i).tasksProgress==null)
				{
					 
					try {
						String s1 = ServerAPI.getServerURL();
						String s2 = s1+al.get(i).url;
						al.get(i).tasksProgress = ServerAPI.getQuestTasks( s2+"tasks.json");
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
 				}
				array.put(QuestObject.unparseProgress(al.get(i)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		editor.putString("questsProgress", array.toString());
		Log.d("quest","save "+ array.toString());
//		try {
//			JSONArray ar = new JSONArray(array.toString());
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		editor.commit();
	}

	public static boolean isOnline() {
		Context c = QrushTabsApp.getAppContext();
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		
		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	public static void flushProfile() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(QrushTabsApp.getAppContext());
		Editor editor = prefs.edit();
		editor.clear();
		editor.commit();
		// TODO Auto-generated method stub

	}
	public static String addAchievment(String name) 
	{
		String req = "add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "attainment"));
		nvps.add(new BasicNameValuePair("attainmentName", name));
		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");

			if (report.equals("success"))
				return "true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
		
	}
	public static String[] getAchievments(String username) 
	{
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "attainment"));
		nvps.add(new BasicNameValuePair("otherUserName", username));
		
		String resp = executeHttpPostResponse(req, nvps);

		String report = "";
		try {
			// Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			
			JSONArray ar = (JSONArray)jsonObj.get("attainments");
			String[] str = new String[ar.length()];
			for(int i = 0;i<str.length;i++)
			{
				JSONObject jso = (JSONObject)ar.get(i);
				str[i] = jso.optString("name");
			}
			if (report.equals("success"))
				return str;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
		
	}

}
