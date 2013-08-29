package ru.qrushtabs.app.dialogs;

import android.support.v4.app.DialogFragment;

public class MyDialog extends DialogFragment {

	protected OnDialogClickListener onDialogClick;
	protected String text = "Вы проиграли"; 
	public void setOnDialogClickListener(OnDialogClickListener l)
	{
		this.onDialogClick = l;
	}
	public void setLabelText(String text)
	{
		this.text = text;
	}
 
}
