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

import com.smoketherapy.personalinfo.PersonalInfo_gender;

public class Agreement extends Activity implements OnClickListener{
	
	
	//================ Widgets =================================// 
	LinearLayout lyt_appback;
	TextView txt_terms_condition;
	WebView txt_terms;
	Button btn_agree;
	//Button btn_not_agree;
	
	
	//================= Font settings ==============================//
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agreement_screen);
			
		txt_terms = (WebView) findViewById(R.id.txt_terms);
		txt_terms.setBackgroundColor(0x00000000);
		
		/*if(Build.VERSION.SDK_INT >= 11)
		{
			txt_terms.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		}*/
		txt_terms_condition = (TextView)findViewById(R.id.txt_terms_condition);
		
		btn_agree 	  = (Button)findViewById(R.id.btn_agree);
/*		btn_not_agree = (Button)findViewById(R.id.btn_not_agree);*/
		
		
		WebSettings settWebView = txt_terms.getSettings();
		settWebView.setJavaScriptEnabled(true);
		settWebView.setDefaultTextEncodingName("utf-8");
		
		//txt_terms.loadUrl("file:///android_asset/terms.html");
		txt_terms.loadData(getResources().getString(R.string.agreementtxt).toString(),"text/html", "utf-8");
		
		btn_agree.setOnClickListener(this);
		//btn_not_agree.setOnClickListener(this);
		
		setFontTypeFace();
	}
	
	public void setFontTypeFace()
	{
		txt_terms_condition.setTypeface(Application.tf);
		btn_agree.setTypeface(Application.tf);
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
		
		switch (v.getId()) 
		{
		case R.id.btn_agree:
			Application.prefrences.Store_agree_state(true);
			i = new Intent(getApplicationContext(), PersonalInfo_gender.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			Agreement.this.finish();
			break;

		/*case R.id.btn_not_agree:
			finish();
			break;*/
			
		default:
			break;
		}
	}
}
