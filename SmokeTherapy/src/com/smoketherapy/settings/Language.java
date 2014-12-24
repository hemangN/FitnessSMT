package com.smoketherapy.settings;

import com.smoketherapy.Application;
import com.smoketherapy.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Language extends Activity implements OnClickListener {

	LinearLayout lyt_appback;
	TextView txt_language,txt_by;
	Button btn_english, btn_espanol, btn_francais, btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.language_screen);

		txt_language = (TextView) findViewById(R.id.txt_language);
		txt_by		 = (TextView)findViewById(R.id.txt_by);
		
		btn_english = (Button) findViewById(R.id.btn_english);
		btn_espanol = (Button) findViewById(R.id.btn_espanol);
		btn_francais = (Button) findViewById(R.id.btn_francais);
		btn_back = (Button) findViewById(R.id.btn_back);

		btn_english.setOnClickListener(this);
		btn_espanol.setOnClickListener(this);
		btn_francais.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		
		setFontTypeFace();
	}

	public void setFontTypeFace()
	{
		txt_language.setTypeface(Application.tf);
		txt_by.setTypeface(Application.tf);
		btn_english.setTypeface(Application.tf);
		btn_espanol.setTypeface(Application.tf);
		btn_francais.setTypeface(Application.tf);		
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
		case R.id.btn_english:
			btn_english.setEnabled(false);
			btn_espanol.setEnabled(true);
			btn_francais.setEnabled(true);
			break;

		case R.id.btn_espanol:
			btn_espanol.setEnabled(false);
			btn_english.setEnabled(true);
			btn_francais.setEnabled(true);
			break;
		case R.id.btn_francais:
			btn_francais.setEnabled(false);
			btn_english.setEnabled(true);
			btn_espanol.setEnabled(true);
			break;
		case R.id.btn_back:
			Language.this.finish();
			overridePendingTransition( R.anim.left_in,R.anim.right_out);
			break;
		default:
			break;
		}

	}
}
