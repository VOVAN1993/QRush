package ru.qrushtabs.app.dialogs;

import ru.qrushtabs.app.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BlackAlertDialog extends MyDialog {


	private View v;
	private Drawable bkg;
	
	private OnClickListener onOk = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			BlackAlertDialog.this.dismiss();
			if(onDialogClick!=null)
			onDialogClick.onOkClick();
  		}
		
	};
	

	TextView tv;
	Button btn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		v = inflater.inflate(R.layout.black_alert_dialog, container);
		
		tv = (TextView)v.findViewById(R.id.black_alert_tv);
		tv.setText(text);
		btn = (Button)v.findViewById(R.id.black_alert_okbtn);
		
		btn.setOnClickListener(onOk);
		
		if(bkg!=null)
		{
		LinearLayout ll = (LinearLayout)v.findViewById(R.id.black_alert_main_layout);
		ll.setBackgroundDrawable(bkg);
		}
		return v;
		
	}
	
	public void setDrawableBackground(Drawable bkg)
	{
		this.bkg = bkg;
	}
 
	@Override
	public void onResume()
    {

        super.onResume();
        this.getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
    }

}
