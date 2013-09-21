package ru.qrushtabs.app;

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

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.mycamera.AvatarCameraActivity;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.BitmapCropper;
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
import android.graphics.Typeface;
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

public class ChangeProfileActivity extends Activity {
	EditText birthdateET;
	AutoCompleteTextView cityTextView;
	int firstYear;
	TreeMap<String, String> citiesMap;
	Bitmap avatar = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.change_profile);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_empty);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		ImageView iv = (ImageView)findViewById(R.id.reg_avatar_iv);
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
				TextView reportView = (TextView)ChangeProfileActivity.this.findViewById(R.id.second_form_report_view);

				List<NameValuePair> nvps = new LinkedList<NameValuePair>();
				String birthdate = birthdateET
						.getText().toString();
				if(birthdate.equals(""))
				{
					 
				}
				else
				{
					nvps.add(new BasicNameValuePair("birthday", birthdate));
				}
				
				

				Iterator<Entry<String, String>> it = citiesMap.entrySet()
						.iterator();

				String cityName = cityTextView.getText().toString();
				 
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
				RadioButton rb = (RadioButton)ChangeProfileActivity.this.findViewById(R.id.radioMale);
				String sex;
				if(rb.isChecked())
					sex = "M";
				else
					sex = "F";
				
				nvps.add(new BasicNameValuePair("sex", sex));
				Log.d("second form", "cityKey " + cityKey);
				if (cityKey.equals("0"))
				{
					 
				}
				else
				{
					nvps.add(new BasicNameValuePair("cityid", cityKey));
				}
  				
				if(!ServerAPI.addInfo(nvps).equals("true"))
				{
					reportView.setText("Не удалось сохранить!");
					return;
				}
				if(!cityKey.equals("0"))
				{
					ProfileInfo.city = cityName;
					ProfileInfo.cityId = cityKey;
				}
				if(!birthdate.equals(""))
					ProfileInfo.birthday = birthdate;
				
				ProfileInfo.sex = sex;
				if (avatar != null) {
					UserPhotosMap.saveAvatar(avatar);
					SampleFileUpload fileUpload = new SampleFileUpload();
					String response = fileUpload
							.executeMultiPartRequest(
									"http://188.120.235.179/uploadProfilePhoto/",
									ProfileInfo.avatarFile,
									ProfileInfo.avatarFile.getName(),
									"File Upload test Hydrangeas.jpg description");
					Log.d("upload avatar", response);
					
				}
				ServerAPI.saveProfileInfo();
				reportView.setText("Данные сохранены!");

			}

		});

	}
	private OnClickListener onAvatarClick = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(ChangeProfileActivity.this,AvatarCameraActivity.class);
			startActivityForResult(intent,1);	
		}
		
	};
	@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode==0)
	    	return;
    	else
    	{
    		ImageView avatarView = (ImageView)findViewById(R.id.reg_avatar_iv);
    		byte bytes[] = data.getByteArrayExtra("photo");
    		avatar = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    		
    		//final float scale = getBaseContext().getResources().getDisplayMetrics().density;
     		avatarView.setImageBitmap(BitmapCropper.pxcrop( avatar, avatarView.getWidth(), avatarView.getWidth()));
    		 
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
