package com.smoketherapy.help;

import android.app.Activity;
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

public class AboutTheApp extends Activity implements OnClickListener{
	
	LinearLayout lyt_appback;
	TextView txt_about_app;
	WebView txt_terms;
	Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_app_screen);
			
		txt_terms = (WebView) findViewById(R.id.txt_terms);
		txt_terms.setBackgroundColor(0x00000000);
		
		txt_about_app = (TextView)findViewById(R.id.txt_about_app);
		
		btn_back 	  = (Button)findViewById(R.id.btn_back);

		WebSettings settWebView = txt_terms.getSettings();
		settWebView.setJavaScriptEnabled(true);
		settWebView.setDefaultTextEncodingName("utf-8");
		
		//txt_terms.loadUrl("file:///android_asset/terms.html");
		txt_terms.loadData(getResources().getString(R.string.abouttheapptxt).toString(),"text/html", "utf-8");
		
		btn_back.setOnClickListener(this);
		
		setFontTypeFace();
		
	}

	public void setFontTypeFace()
	{
		txt_about_app.setTypeface(Application.tf);
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
	
		switch (v.getId()) 
		{
		case R.id.btn_back:
		
			AboutTheApp.this.finish();
			overridePendingTransition( R.anim.left_in,R.anim.right_out);
			break;

			
		default:
			break;
		}
	}
}

