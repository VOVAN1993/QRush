package ru.qrushtabs.app.dialogs;

import ru.qrushtabs.app.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;

public class LoseDialog extends MyDialog {


	View v;
	
	private OnClickListener onCancel = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			LoseDialog.this.dismiss();
			if(onDialogClick!=null)
			onDialogClick.onOkClick();
  		}
		
	};
	

	TextView tv;
	Button btn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		v = inflater.inflate(R.layout.lose_dialog, container);
		
		tv = (TextView)v.findViewById(R.id.okLabel);
		tv.setText(text);
		btn = (Button)v.findViewById(R.id.okBtn);
		
		btn.setOnClickListener(onCancel);
	 
		return v;
		
	}
	

 
	@Override
	public void onResume()
    {

        super.onResume();
        this.getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
    }

}
