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

public class Help extends Activity implements OnClickListener {

	LinearLayout lyt_appback;
	TextView txt_help;
	Button btn_tutorial, btn_about_app, btn_contact_us, btn_share_with_friends,
			btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_screen);

		txt_help = (TextView) findViewById(R.id.txt_help);

		btn_tutorial = (Button) findViewById(R.id.btn_tutorial);
		btn_about_app = (Button) findViewById(R.id.btn_about_app);
		btn_contact_us = (Button) findViewById(R.id.btn_contact_us);
		btn_share_with_friends = (Button) findViewById(R.id.btn_share_with_friends);
		btn_back = (Button) findViewById(R.id.btn_back);

		btn_tutorial.setOnClickListener(this);
		btn_about_app.setOnClickListener(this);
		btn_contact_us.setOnClickListener(this);
		btn_share_with_friends.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		
		setFontTypeFace();
	}
	
	public void setFontTypeFace()
	{
		txt_help.setTypeface(Application.tf);
		btn_tutorial.setTypeface(Application.tf);
		btn_about_app.setTypeface(Application.tf);
		btn_contact_us.setTypeface(Application.tf);
		btn_share_with_friends.setTypeface(Application.tf);
		btn_back.setTypeface(Application.tf);
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
		case R.id.btn_tutorial:
			/*i = new Intent(Help.this, Tutorial.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);*/
			break;

		case R.id.btn_about_app:
			i = new Intent(Help.this, AboutTheApp.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_contact_us:
			i = new Intent(Help.this, ContactUs.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_share_with_friends:
			/*i = new Intent(Help.this, ShareWithFriends.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);*/
			break;
		case R.id.btn_back:
			Help.this.finish();
			overridePendingTransition( R.anim.left_in,R.anim.right_out);
			break;
		default:
			break;
		}
	}
}
