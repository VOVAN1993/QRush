package ru.qrushtabs.app.badges;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import ru.qrushtabs.app.MyVungleActivity;
import ru.qrushtabs.app.R;
import ru.qrushtabs.app.dialogs.BlackAlertDialog;
import ru.qrushtabs.app.dialogs.BlackBadgesDialog;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.ServerAPI;
 
public class BadgesActivity extends MyVungleActivity {

	ImageView badgeViews[];
	ArrayList<Badge> badges;
	private OnClickListener ocl = new OnClickListener()
	{

		@Override
		public void onClick(View v) {
			for(int i = 0; i<BadgesActivity.this.badgeViews.length;i++)
			{
				if(v==badgeViews[i])
				{
					BlackBadgesDialog newFragment;
					newFragment = new BlackBadgesDialog();
					 
						newFragment.setLabelText(BadgesActivity.this.badges.get(i).description);
						newFragment.show(BadgesActivity.this.getSupportFragmentManager(),
								"missiles");
						newFragment.setDrawableBackground(BadgesActivity.this.badges.get(i).dialogIcon);
				}
			}
			
		}
		
	};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.badges);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_back);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Button backButton = (Button)findViewById(R.id.header_back_btn);
		backButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.backbtn));
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				  finish();
			}
		});
		
		TableLayout tl = (TableLayout)findViewById(R.id.badges_tl);
		
		badges = Badges.getBadges();
		String str[] = ServerAPI.getAchievments(this.getIntent().getStringExtra("username"));
		for(int i= 0;i<str.length;i++)
		{
			for(int j = 0;j<badges.size();j++)
			{
				if(badges.get(j).name.equals(str[i]))
				{
					badges.get(j).achieved = true;
				}
			}
		}
		  
		int l = badges.size();
		badgeViews = new ImageView[l];
		if(l%3==0)
			l = l/3;
		else
			l=l/3+1;
		for(int i = 0;i<l;i++)
		{
			TableRow tr = new TableRow(this);
			for(int j = 0;j<3;j++)
			{
				if(i*3+j>=badges.size())
					break;
				badgeViews[i*3+j]= new ImageView(this);
				
				badges.get(i*3+j).draw(badgeViews[i*3+j]);
				tr.addView(badgeViews[i*3+j]);
				badgeViews[i*3+j].setOnClickListener(ocl);
			}
			tl.addView(tr);
		}
	}
}
