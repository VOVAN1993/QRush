package ru.qrushtabs.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import com.perm.kate.api.Api;
import com.perm.kate.api.KException;
import com.perm.kate.api.User;

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.dialogs.BlackPhotoDialog;
import ru.qrushtabs.app.mycamera.AvatarCameraActivity;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.BitmapCropper;
import ru.qrushtabs.app.utils.QRLoading;
import ru.qrushtabs.app.utils.SampleFileUpload;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.UserPhotosMap;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class SecondFormActivity extends MyVungleActivity {
	EditText birthdateET;
	AutoCompleteTextView cityTextView;
	int firstYear;
	TreeMap<String, String> citiesMap;
	Bitmap avatar = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.second_form);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_empty);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		ImageView iv = (ImageView)findViewById(R.id.reg_avatar);
		iv.setOnClickListener(onAvatarClick );
		citiesMap = ServerAPI.getCities("1");
		Iterator<Entry<String, String>> it = citiesMap.entrySet().iterator();

		ArrayList<String> citiesList = new ArrayList<String>();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) it
					.next();
			citiesList.add((String) pairs.getValue());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, citiesList);
		cityTextView = (AutoCompleteTextView) findViewById(R.id.cities_list);
		cityTextView.setAdapter(adapter);

		birthdateET = (EditText) findViewById(R.id.birthdate_et);

		birthdateET.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				LinearLayout birthdateWheels = (LinearLayout) findViewById(R.id.birthdate_wheels);
				if (hasFocus) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(cityTextView.getWindowToken(),
							0);
					birthdateWheels.setVisibility(View.VISIBLE);
				} else {
					birthdateWheels.setVisibility(View.GONE);

				}
			}
		});
		createDateWheel();

		Button signupBtn = (Button) findViewById(R.id.signupBtn);
		signupBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TextView reportView = (TextView)SecondFormActivity.this.findViewById(R.id.second_form_report_view);

				List<NameValuePair> nvps = new LinkedList<NameValuePair>();
				String birthdate = birthdateET
						.getText().toString();
				if(birthdate.equals(""))
				{
					reportView.setText("Введите дату рождения");
					return;
				}
				ProfileInfo.birthday = birthdate;
//				nvps.add(new BasicNameValuePair("birthday", birthdateET
//						.getText().toString()));

				Iterator<Entry<String, String>> it = citiesMap.entrySet()
						.iterator();

				String cityName = cityTextView.getText().toString();
				ProfileInfo.city = cityName;
				// Log.d("second form","cityName " + cityName);
				String cityKey = "0";

				for (Entry<String, String> entry : citiesMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();

					if (cityName.equals(value)) {
						cityKey = key;
					}
					// System.o
					// Log.d("second form","search " + value);
				}
				// while (it.hasNext())
				// {
				// Map.Entry<String, String> pairs = (Map.Entry<String,
				// String>)it.next();
				// String key = pairs.getKey();
				// String value = pairs.getValue();
				// Log.d("second form","search " + value);
				// if(cityName.equals(value))
				// {
				// cityKey = key;
				// }
				// //System.out.println(pairs.getKey() + " = " +
				// pairs.getValue());
				// it.remove(); // avoids a ConcurrentModificationException
				// }
				RadioButton rb = (RadioButton)SecondFormActivity.this.findViewById(R.id.radioMale);
				if(rb.isChecked())
					ProfileInfo.sex = "M";
				else
					ProfileInfo.sex = "F";
				
				//nvps.add(new BasicNameValuePair("sex", ProfileInfo.sex));
				Log.d("second form", "cityKey " + cityKey);
				if (cityKey.equals("0"))
				{
					reportView.setText("Вводите доступный город, используя автозаполнение");
					return;
				}
				ProfileInfo.cityId = cityKey;
				//nvps.add(new BasicNameValuePair("cityid", cityKey));
				if(!ServerAPI.signUp().equals("true"))
				{
					reportView.setText("Не удалось зарегистрироваться!");
					return;
				}
				if (avatar != null) {
					new Thread(new Runnable() {
						public void run() {
							UserPhotosMap.saveAvatar(avatar);
							SampleFileUpload fileUpload = new SampleFileUpload();
							String response = fileUpload
									.executeMultiPartRequest(
											"http://188.120.235.179/uploadProfilePhoto/",
											ProfileInfo.avatarFile,
											ProfileInfo.avatarFile.getName(),
											"File Upload test Hydrangeas.jpg description");
							Log.d("upload avatar", response);
							ServerAPI.saveProfileInfo();
						}
					}).start();
				}
				EnterActivity ea = EnterActivity.getInstance();
				if(ea!=null)
					ea.finish();
				Intent intent = new Intent(SecondFormActivity.this,
						MainActivity.class);
				finish();
				startActivity(intent);
			}

		});

	}
	private static final int SELECT_PHOTO = 100;
	private static final int CAPTURE_PHOTO = 1;
	private BlackPhotoDialog.OnPhotoMethodChoosedListener op = new BlackPhotoDialog.OnPhotoMethodChoosedListener() {

		@Override
		public void onPhotoMethodChoosed(int method) {
			if (method == BlackPhotoDialog.GALLERY) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO);
			}
			if (method == BlackPhotoDialog.CAPTURE) {
				Intent intent = new Intent(SecondFormActivity.this,
						AvatarCameraActivity.class);
				startActivityForResult(intent, CAPTURE_PHOTO);

			}
			
			if (method == BlackPhotoDialog.VK) {
				 
				
				(new DownloadPhotoFromVK()).execute();
			}

		}

	};
	 private class DownloadPhotoFromVK extends AsyncTask<String,String,Bitmap> {

		 @Override
		 protected void onPreExecute()
		 {
			 ImageView avatarView = (ImageView) findViewById(R.id.reg_avatar_iv);
			 QRLoading.setLoading(avatarView);
		 }
			protected Bitmap doInBackground(String... args) {
				 ArrayList<Long> uids = new ArrayList<Long>();
				  ArrayList<User> info = null;
				  Api api = new Api(ProfileInfo.userVKToken, Constants.API_ID);
				  uids.add(Long.valueOf(ProfileInfo.userVKID));
				  try {
					info = api.getProfiles(uids, null, null, null, null, null);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (KException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
				  User u = info.get(0);
 
				  if(u.photo_big!=null)
				  {
					  return ServerAPI.loadBitmap(u.photo_big);
				  }
				  if(u.photo_medium!=null)
				  {
					  return ServerAPI.loadBitmap(u.photo_medium);
				  }
				  if(u.photo_medium_rec!=null)
				  {
					  return ServerAPI.loadBitmap(u.photo_medium_rec);
				  }
				  if(u.photo!=null)
				  {
					  return ServerAPI.loadBitmap(u.photo);
				  }
				  return null;
				
			}

			protected void onPostExecute(Bitmap objResult) 
			{

				ImageView avatarView = (ImageView) findViewById(R.id.reg_avatar_iv);
				 QRLoading.stopLoading(avatarView);
				if(objResult==null)
					return;
				 
				 
				avatar = objResult;
 				avatarView.setImageBitmap(BitmapCropper.pxcrop(avatar,
						avatarView.getWidth(), avatarView.getWidth()));
			}

		}
	
	private OnClickListener onAvatarClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			BlackPhotoDialog newFragment;
			newFragment = new BlackPhotoDialog();
			newFragment.setOnPhotoMethodChoosedListener(op);
				 
				newFragment.show(SecondFormActivity.this
						.getSupportFragmentManager(), "missiles");
			  
		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				Uri selectedImage = imageReturnedIntent.getData();
				InputStream imageStream;
				try {
					imageStream = getContentResolver().openInputStream(
							selectedImage);
					avatar = BitmapFactory.decodeStream(imageStream);
					ImageView avatarView = (ImageView) findViewById(R.id.reg_avatar_iv);
					avatarView.setImageBitmap(BitmapCropper.pxcrop(avatar,
							avatarView.getWidth(), avatarView.getWidth()));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			break;
		case CAPTURE_PHOTO:
			if (resultCode == 0)
				return;
			else {
				ImageView avatarView = (ImageView) findViewById(R.id.reg_avatar_iv);
				byte bytes[] = imageReturnedIntent.getByteArrayExtra("photo");
				avatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

				Matrix matrix = new Matrix();
		    	// rotate the Bitmap 90 degrees (counterclockwise)
		    	matrix.postRotate(90);

		    	// recreate the new Bitmap, swap width and height and apply transform
		    	avatar = Bitmap.createBitmap(avatar, 0, 0,
		    			avatar.getWidth(), avatar.getHeight(), matrix, true);
				 

				avatarView.setImageBitmap(BitmapCropper.pxcrop(avatar,
						avatarView.getWidth(), avatarView.getWidth()));
			}
		}

	}

	private void createDateWheel() {
		Calendar calendar = Calendar.getInstance();
		final WheelView month = (WheelView) findViewById(R.id.month);
		final WheelView year = (WheelView) findViewById(R.id.year);
		final WheelView day = (WheelView) findViewById(R.id.day);

		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(year, month, day);
			}
		};

		// month
		int curMonth = calendar.get(Calendar.MONTH);
		String months[] = new String[] { "Январь", "Февраль", "Март", "Апель",
				"Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь",
				"Ноябрь", "Декабрь" };
		month.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
		month.setCurrentItem(curMonth);
		month.addChangingListener(listener);

		// year
		int curYear = calendar.get(Calendar.YEAR);
		firstYear = curYear - 60;
		int lastYear = curYear - 4;
		year.setViewAdapter(new DateNumericAdapter(this, firstYear, lastYear, 0));
		year.setCurrentItem(lastYear - firstYear - 6);
		year.addChangingListener(listener);

		// day
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar
				.get(Calendar.DAY_OF_MONTH) - 1));
		day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
		day.addChangingListener(listener);
		// updateDays(year, month, day);

		// calendar.set(Calendar.DAY_OF_MONTH, day.getCurrentItem()+1);

	}

	/**
	 * Updates day wheel. Sets max days according to selected month and year
	 */
	void updateDays(WheelView year, WheelView month, WheelView day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, firstYear + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());
		calendar.set(Calendar.DAY_OF_MONTH, day.getCurrentItem() + 1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar
				.get(Calendar.DAY_OF_MONTH) - 1));
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);

		birthdateET.setText(dateFormat.format(calendar.getTime()));
	}

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	/**
	 * Adapter for string based wheel. Highlights the current value.
	 */
	private class DateArrayAdapter extends ArrayWheelAdapter<String> {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}
}
