package com.smoketherapy.help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smoketherapy.Application;
import com.smoketherapy.R;
import com.smoketherapy.settings.Settings;

public class ShareWithFriends extends Activity implements OnClickListener{

	LinearLayout lyt_appback;
	TextView txt_livesmokefree;
	Button btn_stat,btn_settings,btn_help,btn_complete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.live_smoke_free_screen);
		
		txt_livesmokefree = (TextView)findViewById(R.id.txt_livesmokefree);
		
		
		btn_stat = (Button)findViewById(R.id.btn_stat);
		btn_settings = (Button)findViewById(R.id.btn_settings);
		btn_help = (Button)findViewById(R.id.btn_help);
		btn_complete = (Button)findViewById(R.id.btn_complete);
	
		btn_stat.setOnClickListener(this);
		btn_settings.setOnClickListener(this);
		btn_help.setOnClickListener(this);
		btn_complete.setOnClickListener(this);
		
		setFontTypeFace();
		
	}

	public void setFontTypeFace()
	{
		txt_livesmokefree.setTypeface(Application.tf);
		btn_stat.setTypeface(Application.tf);
		btn_settings.setTypeface(Application.tf);
		btn_help.setTypeface(Application.tf);
		btn_complete.setTypeface(Application.tf);
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lyt_appback = (LinearLayout)findViewById(R.id.lyt_appback);		
		lyt_appback.setBackgroundResource(Application.appDrawableId);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Intent i;
		
		switch (v.getId()) {
		case R.id.btn_stat:
			
			break;

		case R.id.btn_settings:
			i = new Intent(ShareWithFriends.this,Settings.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_help:
			i = new Intent(ShareWithFriends.this,Help.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_complete:
			ShareWithFriends.this.finish();
			overridePendingTransition( R.anim.left_in,R.anim.right_out);
			break;
		default:
			break;
		}
		
	}
}

