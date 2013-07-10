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

public class ToTwiceDialog extends MyDialog {

	
 	 
	private OnClickListener onCancel = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			ToTwiceDialog.this.dismiss();
			if(onDialogClick!=null)
				onDialogClick.onCancelClick();
			
		}
		
	};
	
	private OnClickListener onOk = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			ToTwiceDialog.this.dismiss();
			if(onDialogClick!=null)
				onDialogClick.onOkClick();
			
		}
		
	};
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		
//		
//		 Dialog m_dialog = new Dialog(getActivity());
//	     // LayoutInflater m_inflater = LayoutInflater.from(CustomDialogActivity.this);
//	     // View v = LayoutInflater.from(mContext).inflate(R.layout.view_quick_action, null, false);
//	        // SET ALL THE VIEWS
//	        m_dialog.setTitle(null);
//	        
//	        m_dialog.setContentView(R.layout.to_twice_dialog);
//	       // m_dialog.show();
////	   // AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
////	    // Get the layout inflater
////	    LayoutInflater inflater = getActivity().getLayoutInflater();
////
////	    // Inflate and set the layout for the dialog
////	    // Pass null as the parent view because its going in the dialog layout
////	    builder.setView(inflater.inflate(R.layout.to_twice_dialog, null));
////	    // Add action buttons
////	    Dialog d = builder.create();
//// 	   // d.getWindow().setBackgroundDrawable(null);	
////	    Window window = d.getWindow();
////	    window.setBackgroundDrawable(null);
//	   // window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//
//	    
////	    Dialog m_dialog = new Dialog(getActivity(), false, null);
////	    // Inflate and set the layout for the dialog
////	    // Pass null as the parent view because its going in the dialog layout
////	    View v = inflater.inflate(R.layout.to_twice_dialog,null);
////	   // builder.setView(inflater.inflate(R.layout.to_twice_dialog, null));
////	    // Add action buttons
////	    Dialog d = builder.create();
//	    
//  	    return m_dialog;
//	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.to_twice_dialog, container);
		
		Button cancel = (Button)v.findViewById(R.id.not_twice_btn);
		
		cancel.setOnClickListener(onCancel);
		
		Button ok = (Button)v.findViewById(R.id.to_twice_btn);
		
		ok.setOnClickListener(onOk);
			
		
		return v;
		
	}
	
	@Override
	public void onResume()
    {
        // Auto size the dialog based on it's contents
        //Dialog.Window.SetLayout(LinearLayout.LayoutParams.WrapContent, LinearLayout.LayoutParams.WrapContent);

        // Make sure there is no background behind our view
        //Dialog.Window.SetBackgroundDrawable(new ColorDrawable(Color.Transparent));

        // Disable standard dialog styling/frame/theme: our custom view should create full UI
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
