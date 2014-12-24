package com.smoketherapy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smoketherapy.Application;
import com.smoketherapy.R;
import com.smoketherapy.personalinfo.PersonalInfo_gender;

public class Welcome extends Activity implements OnClickListener {

	LinearLayout lyt_appback;
	TextView txt_welcome;
	WebView txt_terms;
	Button btn_proceed_tutorial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_screen);

		txt_terms = (WebView) findViewById(R.id.txt_terms);
		txt_terms.setBackgroundColor(0x00000000);

		txt_welcome = (TextView) findViewById(R.id.txt_welcome);

		btn_proceed_tutorial = (Button) findViewById(R.id.btn_proceed_tutorial);

		WebSettings settWebView = txt_terms.getSettings();
		settWebView.setJavaScriptEnabled(true);
		settWebView.setDefaultTextEncodingName("utf-8");
		
		//txt_terms.loadUrl("file:///android_asset/terms.html");
		txt_terms.loadData(getResources().getString(R.string.tutorialtxt).toString(),"text/html", "utf-8");
		btn_proceed_tutorial.setOnClickListener(this);
		
		setFontTypeFace();
		
		//==================Set Welcome stat =================//
		Application.prefrences.Store_welcome_state(true);

	}

	public void setFontTypeFace()
	{
		txt_welcome.setTypeface(Application.tf);
		btn_proceed_tutorial.setTypeface(Application.tf);
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
		case R.id.btn_proceed_tutorial:

			i = new Intent(getApplicationContext(), Agreement.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			Welcome.this.finish();
			break;

		default:
			break;
		}
	}
}
