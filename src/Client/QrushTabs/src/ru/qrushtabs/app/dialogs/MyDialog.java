package ru.qrushtabs.app.dialogs;

import android.support.v4.app.DialogFragment;

public class MyDialog extends DialogFragment {

	protected OnDialogClickListener onDialogClick;
	 
	public void setOnDialogClickListener(OnDialogClickListener l)
	{
		this.onDialogClick = l;
	}
}
