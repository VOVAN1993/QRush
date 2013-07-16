package ru.qrushtabs.app;

import ru.qrushtabs.app.utils.ServerAPI;

import com.perm.kate.api.Auth;
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
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class LoginActivity extends Activity {
    private static final String TAG = "Kate.LoginActivity";

    WebView webview;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        webview = (WebView) findViewById(R.id.vkontakteview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.clearCache(true);
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
                    //Intent intent=new Intent();
                   // intent.putExtra("token", auth[0]);
                    //intent.putExtra("user_id", Long.parseLong(auth[1]));
                    ProfileInfo.userVKID = auth[1];
                    ProfileInfo.userVKToken = auth[0];
                    ProfileInfo.loginType="vk";
                    Log.d("vk", "user_id "+ Long.parseLong(auth[1]));
                    
                    ServerAPI.saveProfileInfo();
    		        
                    (new CheckTask()).execute();
//    		        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//    				finish();
//    				startActivity(intent);
                    //setResult(Activity.RESULT_OK, intent);
                }
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private class CheckTask extends AsyncTask<String,String,String> {

		protected String doInBackground(String... args) {
			return ServerAPI.loadProfileInfo();
		}

		protected void onPostExecute(String objResult) 
		{

			if(objResult.equals("true"))
			{
				Log.d("vk", "OnCheck");
//			    TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
//			    tv.setText("¬ы успешно вошли");
//			    
//				ProfileInfo.userID = userID;
//		        ProfileInfo.userPass = userPass;
//		        
//
//		        ServerAPI.saveProfileInfo();
//		        
		        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				finish();
				startActivity(intent);
			   // ServerAPI.loadProfileInfo(ProfileInfo.userID,ProfileInfo.userPass);
				 
			}
			else
			{
				Log.d("vk", "OnNotCheck");
				(new SignUpTask()).execute();
//				TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
//			    tv.setText("Не получилось войти");
			}
		
		}

	}
	OnClickListener onSignUp = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
  			(new SignUpTask()).execute();
			
		}
		
	};
	private class SignUpTask extends AsyncTask<String,String,String> {

		protected String doInBackground(String... args) {
			return ServerAPI.signUp();
		}

		protected void onPostExecute(String objResult) {

			if(objResult.equals("true"))
			{
				Log.d("vk", "OnSignUp");
		        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				finish();
				startActivity(intent);
				
			}
			else
			{
				Log.d("vk", "OnNotSignUp");
			}
		 
		}

	}
}