package ru.qrushtabs.app;

import java.util.Locale;

 

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InfoActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	 RadioButton[] rb;
	private OnPageChangeListener listener = new OnPageChangeListener()
	{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			 
			rb[arg0].setChecked(true);
			
			Button btn = (Button)findViewById(R.id.slide_reg_button);
			switch(arg0)
			{
				case 0:
					btn.setVisibility(View.INVISIBLE);
 					break;
				
				case 1:
					btn.setVisibility(View.INVISIBLE);
 					break;
				case 2:
					btn.setVisibility(View.INVISIBLE);
 					break;
				case 3:
					btn.setVisibility(View.INVISIBLE);
 					break;
				case 4:
 					btn.setVisibility(View.VISIBLE);
 					break;
			}
		}
		
	};

	private OnClickListener onNextClick = new OnClickListener()
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.info_slides);
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
//				R.layout.custom_title);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(listener );
		
		
		createRadioButton();
		
		Button btn = (Button)findViewById(R.id.slide_reg_button);
	 
		btn.setVisibility(View.INVISIBLE);
		btn.setOnClickListener(onNextClick );

	}
	
	private void createRadioButton() {
	   rb = new RadioButton[5];
	    RadioGroup rg = (RadioGroup)findViewById(R.id.views_rbg); //create the RadioGroup
	    rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
	    for(int i=0; i<5; i++){
	        rb[i]  = new RadioButton(this);
	        rb[i].setButtonDrawable(this.getResources().getDrawable(R.drawable.swipe_view_selector));
	      
	        rg.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
	        
	    }
	    rb[0].setChecked(true);
  
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position);
			
		 
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 5;
		}

		 
		@Override
		public CharSequence getPageTitle(int position) {

			

			return "";
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
	 

	 

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
			RelativeLayout rl = (RelativeLayout)rootView.findViewById(R.id.info_slide_rl);
			int pos = getArguments().getInt(ARG_SECTION_NUMBER);
 			switch(pos)
			{
				case 0:
 					rl.setBackgroundDrawable(rootView.getResources().getDrawable(R.drawable.one_info));
					break;
				
				case 1:
 					rl.setBackgroundDrawable(rootView.getResources().getDrawable(R.drawable.two_info));
					break;
				case 2:
 					rl.setBackgroundDrawable(rootView.getResources().getDrawable(R.drawable.three_info));
					break;
				case 3:
 					rl.setBackgroundDrawable(rootView.getResources().getDrawable(R.drawable.four_info));
					break;
				case 4:
 					rl.setBackgroundDrawable(rootView.getResources().getDrawable(R.drawable.five_info));
					break;
			}
			//TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			//dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

}
