package ru.qrushtabs.app.dialogs;

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.profile.ProfileInfo;
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

public class BlackPhotoDialog extends MyDialog {


	public static int VK = 2;
	public static int GALLERY = 1;
	public static int CAPTURE = 0;
	private View v;
	private Drawable bkg;
	public static interface OnPhotoMethodChoosedListener
	{
		public void onPhotoMethodChoosed(int method);
	}
	public void setOnPhotoMethodChoosedListener(OnPhotoMethodChoosedListener o)
	{
		this.l = o;
	}
	public OnPhotoMethodChoosedListener l;
	private OnClickListener onOk = new OnClickListener()
	{

		@Override
		public void onClick(View v) {
			
			 
			if(l!=null)
			{
				if(v==BlackPhotoDialog.this.vkbtn)
					l.onPhotoMethodChoosed(VK);
				if(v==BlackPhotoDialog.this.galbtn)
					l.onPhotoMethodChoosed(GALLERY);
				if(v==BlackPhotoDialog.this.capbtn)
					l.onPhotoMethodChoosed(CAPTURE);
				
				BlackPhotoDialog.this.dismiss();
			}
  		}
		
	};
	

	 
	Button vkbtn;
	Button galbtn;
	Button capbtn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		v = inflater.inflate(R.layout.black_photo_dialog, container);
		
		
		vkbtn = (Button)v.findViewById(R.id.black_photo_vkbtn);
		if(ProfileInfo.signInType.equals("vk"))
		{
			vkbtn.setOnClickListener(onOk);
			vkbtn.setVisibility(View.VISIBLE);
		}
		else
			vkbtn.setVisibility(View.GONE);
		capbtn = (Button)v.findViewById(R.id.black_photo_capbtn);
		capbtn.setOnClickListener(onOk);
		galbtn = (Button)v.findViewById(R.id.black_photo_galbtn);
		galbtn.setOnClickListener(onOk);
 		
		 
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
