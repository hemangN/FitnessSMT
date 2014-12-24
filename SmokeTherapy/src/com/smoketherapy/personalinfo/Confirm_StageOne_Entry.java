package com.smoketherapy.personalinfo;

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
import com.smoketherapy.db.PersonalInfo;
import com.smoketherapy.stages.BeginStageOne;
import com.smoketherapy.staticdata.StaticData;

public class Confirm_StageOne_Entry extends Activity implements OnClickListener{

	LinearLayout lyt_appback;
	TextView txt_almost_there;
	WebView txt_terms;
	Button btn_understand;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_stage1_entry_screen);
		
		
		txt_terms = (WebView) findViewById(R.id.txt_terms);
		txt_terms.setBackgroundColor(0x00000000);
		
		txt_almost_there = (TextView)findViewById(R.id.txt_almost_there);
		
		btn_understand 	  = (Button)findViewById(R.id.btn_understand);
		
		WebSettings settWebView = txt_terms.getSettings();
		settWebView.setJavaScriptEnabled(true);
		settWebView.setDefaultTextEncodingName("utf-8");
		System.out.println("==Almost there String====="+getResources().getString(R.string.almosttheretxt).toString());
		//txt_terms.loadUrl("file:///android_asset/terms.html");
		txt_terms.loadData(getResources().getString(R.string.almosttheretxt).toString(),"text/html", "utf-8");
		
		btn_understand.setOnClickListener(this);
		
		setFontTypeFace();
		
	}
	
	public void setFontTypeFace()
	{
		txt_almost_there.setTypeface(Application.tf);
		btn_understand.setTypeface(Application.tf);
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
		case R.id.btn_understand:
			
			//============ Store In DB =========================================================================
			PersonalInfo mPersonalInfo = new PersonalInfo(StaticData.GENDER,StaticData.WAKE_UP_TIME,StaticData.SLEEP_HOURS);
			Application.dbHelper.insertPersonalInfo(mPersonalInfo);
			
			//============ Store In Preferences ================================================================
			Application.prefrences.Store_Wake_Hours(24 - Integer.parseInt(StaticData.SLEEP_HOURS));
			Application.prefrences.Store_Wake_Up_Time(StaticData.WAKE_UP_TIME);
			Application.prefrences.Store_Gender(StaticData.GENDER);
			Application.prefrences.Store_Sleep_Hours(StaticData.SLEEP_HOURS);
			Application.prefrences.Store_personalinfo_state(true);
			
			Intent intent = new Intent(Confirm_StageOne_Entry.this,BeginStageOne.class);
			startActivity(intent);
			this.finish();
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			
			break;

		default:
			break;
		}
		
	}

}
