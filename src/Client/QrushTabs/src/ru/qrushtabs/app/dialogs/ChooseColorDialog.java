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

public class ChooseColorDialog extends MyDialog {

	
 	 
	private OnClickListener onCancel = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			ChooseColorDialog.this.dismiss();
			if(onDialogClick!=null)
				onDialogClick.onCancelClick();
			
		}
		
	};
	
	private OnClickListener onOk = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			ChooseColorDialog.this.dismiss();
			if(onDialogClick!=null)
				onDialogClick.onOkClick();
			
		}
		
	};

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.choose_color_dialog, container);
		
		Button cancel = (Button)v.findViewById(R.id.bluebtn);
		
		cancel.setOnClickListener(onCancel);
		
		Button ok = (Button)v.findViewById(R.id.green_btn);
		
		ok.setOnClickListener(onOk);
			
		
		return v;
		
	}
	
	@Override
	public void onResume()
    {

       //SetStyle(Android.Support.V4.App.DialogFragment.StyleNoFrame, Android.Resource.Style.Theme);

        super.onResume();
        this.getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
    }
//	@Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
//        //this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
//    }
}
