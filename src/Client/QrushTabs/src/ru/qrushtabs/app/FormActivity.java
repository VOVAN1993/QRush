package ru.qrushtabs.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FormActivity extends MyVungleActivity {

	OnItemSelectedListener oisl = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent,
				View view, int pos, long id) {
			Log.d("for", "" + parent.getItemAtPosition(pos));
			// An item was selected. You can retrieve the selected item using
			// parent.getItemAtPosition(pos)
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		int ms[][] = { { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
				{ 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.form);
		Intent intent = this.getIntent();

		Spinner yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);
		for (int i = 2012; i >= 1930; i--)
			adapter.add(String.valueOf(i));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		yearSpinner.setAdapter(adapter);
		String birthdate[] = intent.getStringExtra("birthdate").split("\\.");
		yearSpinner.setSelection(adapter.getPosition(birthdate[2]));

		Spinner monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
		ArrayAdapter<CharSequence> madapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);
		for (int i = 1; i <= 12; i++)
			madapter.add(String.valueOf(i));

		madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		monthSpinner.setAdapter(madapter);
		monthSpinner.setSelection(madapter.getPosition(birthdate[1]));
		monthSpinner.setOnItemSelectedListener(oisl);
		int year = Integer.valueOf(birthdate[2]);
		int month = Integer.valueOf(birthdate[1]);
		int day = Integer.valueOf(birthdate[0]);

		Spinner daySpinner = (Spinner) findViewById(R.id.daySpinner);
		int leap;
		if (year % 4 == 0)
			leap = 1;
		else
			leap = 0;
		ArrayAdapter<Integer> dadapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item);

		for (int i = 1; i <= ms[leap][month - 1]; i++)
			dadapter.add(i);

		dadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		daySpinner.setAdapter(dadapter);

		daySpinner.setSelection(dadapter.getPosition(Integer
				.valueOf(birthdate[0])));
		
		
		Spinner spinner = (Spinner) findViewById(R.id.countrySpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(this,
		        R.array.countries_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(countryAdapter);
		spinner.setSelection(countryAdapter.getPosition("Россия"));
		
		Spinner citySpinner = (Spinner) findViewById(R.id.citySpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
//		Resources res = getResources();
//		String[] planets = res.getStringArray(R.array
		ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(this,
		        R.array.city1, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		citySpinner.setAdapter(cityAdapter);
		citySpinner.setSelection(cityAdapter.getPosition("Санкт-Петербург"));

	}

}
