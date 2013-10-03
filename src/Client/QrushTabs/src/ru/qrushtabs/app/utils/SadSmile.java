package ru.qrushtabs.app.utils;

import ru.qrushtabs.app.R;
import android.app.Activity;
import android.widget.TextView;

public class SadSmile {

	public static void setSadSmile(Activity a,String text)
	{
		a.setContentView(R.layout.sad_view);
		
		TextView tv = (TextView)a.findViewById(R.id.sad_tv);
		tv.setText(text);
	}
}
