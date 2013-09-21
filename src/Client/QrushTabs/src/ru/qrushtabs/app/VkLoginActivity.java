package ru.qrushtabs.app;

import java.util.ArrayList;
import java.util.Collection;

import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.ServerAPI;

import com.perm.kate.api.Api;
import com.perm.kate.api.Auth;
import com.perm.kate.api.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class VkLoginActivity extends Activity {
    private static final String TAG = "Kate.LoginActivity";

    WebView webview;
    Api api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.vksignin);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title_empty);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        webview = (WebView) findViewById(R.id.vkontakteview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.clearCache(true);
        //api.getPhotos(uid, aid, offset, count)
      //Чтобы получать уведомления об окончании загрузки страницы
         webview.setWebViewClient(new VkontakteWebViewClient());
                
        //otherwise CookieManager will fall with java.lang.IllegalStateException: CookieSyncManager::createInstance() needs to be called before CookieSyncManager::getInstance()
        CookieSyncManager.createInstance(this);
        
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        
        String url=Auth.getUrl(Constants.API_ID, Auth.getSettings());
        webview.loadUrl(url);
    }
    
    class VkontakteWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            parseUrl(url);
        }
    }
    
    private void parseUrl(String url) {
        try {
            if(url==null)
                return;
            Log.i(TAG, "url="+url);
            if(url.startsWith(Auth.redirect_url))
            {
                if(!url.contains("error=")){
                    String[] auth=Auth.parseRedirectUrl(url);
                    ProfileInfo.userVKID = auth[1];
                    ProfileInfo.userVKToken = auth[0];
                    ProfileInfo.signInType="vk";
                    Log.d("vk", "user_id "+ Long.parseLong(auth[1]));
                    
                    ServerAPI.saveProfileInfo();
    		        
//                  Intent intent = new Intent(this, RegistrationActivity.class);
//        			finish();
//        			startActivity(intent);
                    //(new SignUpTask()).execute();
        			(new SignInTask()).execute();
                }
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private class SignInTask extends AsyncTask<String,String,String> {

		protected String doInBackground(String... args) {
			return ServerAPI.signin();
		}

		protected void onPostExecute(String objResult) 
		{

			if(objResult.equals("true"))
			{
				Log.d("vk", "OnCheck");
		        Intent intent = new Intent(VkLoginActivity.this, MainActivity.class);
				finish();
				startActivity(intent);
				 
			}
			else
			{
				Log.d("vk", "OnNotLogin");
                Intent intent = new Intent(VkLoginActivity.this, RegistrationActivity.class);
    			finish();
    			startActivity(intent);
				///(new SignUpTask()).execute();
			}
		
		}

	}
//	OnClickListener onSignUp = new OnClickListener()
//	{
//
//		@Override
//		public void onClick(View arg0) {
//  			(new SignUpTask()).execute();
//			
//		}
//		
//	};
//	private class SignUpTask extends AsyncTask<String,String,String> {
//
//		protected String doInBackground(String... args) {
//			return ServerAPI.signUp();
//		}
//
//		protected void onPostExecute(String objResult) {
//
//			if(objResult.equals("true"))
//			{
//				Log.d("vk", "OnSignUp");
//		        Intent intent = new Intent(VkLoginActivity.this, MainActivity.class);
//				finish();
//				  try {
//					  ArrayList<Long> uids = new ArrayList<Long>();
//					  ArrayList<User> info;
//					  api=new Api(ProfileInfo.userVKToken, Constants.API_ID);
//					  uids.add(Long.valueOf(ProfileInfo.userVKID));
// 					  info = api.getProfiles(uids, null, null, null, null, null);
// 					  
// 					  User u = info.get(0);
// 					  Log.d("vk", u.first_name);
// 					 Log.d("vk", u.last_name);
// 					 Log.d("vk", ""+u.city);
// 					 Log.d("vk", ""+u.country);
// 					 Log.d("vk", u.mobile_phone);
//  	                    //Показать сообщение в UI потоке 
// 					 Intent formIntent = new Intent(VkLoginActivity.this,DateActivity.class);
// 					formIntent.putExtra("nickname", u.nickname);
// 					formIntent.putExtra("birthdate", u.birthdate);
// 					formIntent.putExtra("phone_number", u.mobile_phone);
// 					startActivity(formIntent);
// 	                } catch (Exception e) {
//	                    e.printStackTrace();
//	                }
//				//startActivity(intent);
//				
//			}
//			else
//			{
//				Log.d("vk", "OnNotSignUp");
//			}
//		 
//		}

	}
