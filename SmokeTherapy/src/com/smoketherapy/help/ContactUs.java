package com.smoketherapy.help;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smoketherapy.Application;
import com.smoketherapy.R;

public class ContactUs extends Activity implements OnClickListener {

	LinearLayout lyt_appback;
	TextView txt_contact_us;
	Button btn_visit_wesite, btn_find_on_fb, btn_email_us, btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactus_screen);

		txt_contact_us = (TextView) findViewById(R.id.txt_contact_us);

		btn_visit_wesite = (Button) findViewById(R.id.btn_visit_wesite);
		btn_find_on_fb = (Button) findViewById(R.id.btn_find_on_fb);
		btn_email_us = (Button) findViewById(R.id.btn_email_us);
		btn_back = (Button) findViewById(R.id.btn_back);

		btn_visit_wesite.setOnClickListener(this);
		btn_find_on_fb.setOnClickListener(this);
		btn_email_us.setOnClickListener(this);
		btn_back.setOnClickListener(this);

		setFontTypeFace();
	}

	public void setFontTypeFace()
	{
		txt_contact_us.setTypeface(Application.tf);
		btn_visit_wesite.setTypeface(Application.tf);
		btn_find_on_fb.setTypeface(Application.tf);
		btn_email_us.setTypeface(Application.tf);
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
		switch (v.getId()) {
		case R.id.btn_visit_wesite:

			break;

		case R.id.btn_find_on_fb:

			break;
		case R.id.btn_email_us:

			break;
		case R.id.btn_back:
			ContactUs.this.finish();
			overridePendingTransition( R.anim.left_in,R.anim.right_out);
			break;
		default:
			break;
		}

	}
}
