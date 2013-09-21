package ru.qrushtabs.app;
import com.vungle.sdk.VunglePub;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;

public class MyVungleActivity extends FragmentActivity 
{
	@Override
	public void onResume()
	{
		super.onResume();
		VunglePub.onResume();
	}
	public void onPause()
	{
		super.onPause();
		VunglePub.onPause();
	}
}
