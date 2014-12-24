package com.smoketherapy.stages;

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
import com.smoketherapy.staticdata.StaticData;

public class Warning extends Activity implements OnClickListener {

	LinearLayout lyt_appback;
	TextView txt_warning;
	WebView txt_terms;
	Button btn_cont, btn_cancel;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lyt_appback = (LinearLayout)findViewById(R.id.lyt_appback);		
		lyt_appback.setBackgroundResource(Application.appDrawableId);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.warning_screen);


		txt_terms = (WebView) findViewById(R.id.txt_terms);
		txt_terms.setBackgroundColor(0x00000000);
		
		WebSettings settWebView = txt_terms.getSettings();
		settWebView.setJavaScriptEnabled(true);
		settWebView.setDefaultTextEncodingName("utf-8");

		txt_terms.loadData(getResources().getString(R.string.warningtxt).toString(),"text/html", "utf-8");

		txt_warning = (TextView) findViewById(R.id.txt_warning);

		btn_cont = (Button) findViewById(R.id.btn_cont);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);

		btn_cont.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

		setFontTypeFace();
	}

	public void setFontTypeFace()
	{		
		txt_warning.setTypeface(Application.tf);
		btn_cont.setTypeface(Application.tf);
		btn_cancel.setTypeface(Application.tf);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btn_cont:
            StaticData.EXTRA_CIGGE_FLAG = true;
            //Application.prefrences.Store_Extra_Cigge_Taken_Flag(true);
			Warning.this.finish();
			break;

		case R.id.btn_cancel:
			Warning.this.finish();
			break;

		default:
			break;
		}
	}
}
