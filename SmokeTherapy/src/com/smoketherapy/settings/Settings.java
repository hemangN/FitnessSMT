package com.smoketherapy.settings;

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

public class Settings extends Activity implements OnClickListener {

	LinearLayout lyt_appback;
	TextView txt_settings,txt_by;
	Button btn_lang, btn_back_color, btn_remove_ad, btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_screen);

		txt_settings = (TextView) findViewById(R.id.txt_settings);
		txt_by = (TextView) findViewById(R.id.txt_by);
		
		btn_lang = (Button) findViewById(R.id.btn_lang);
		btn_back_color = (Button) findViewById(R.id.btn_back_color);
		btn_remove_ad = (Button) findViewById(R.id.btn_remove_ad);
		btn_back = (Button) findViewById(R.id.btn_back);

		btn_lang.setOnClickListener(this);
		btn_back_color.setOnClickListener(this);
		btn_remove_ad.setOnClickListener(this);
		btn_back.setOnClickListener(this);

		setFontTypeFace();
	}

	public void setFontTypeFace()
	{
		txt_settings.setTypeface(Application.tf);
		txt_by.setTypeface(Application.tf);
		btn_lang.setTypeface(Application.tf);
		btn_back_color.setTypeface(Application.tf);
		btn_remove_ad.setTypeface(Application.tf);		
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
		case R.id.btn_lang:
			Intent intent = new Intent(Intent.ACTION_MAIN);
			/*intent.setClassName("com.android.settings", "com.android.settings.LanguageSettings");*/
			/*intent.setClassName("com.android.settings", "com.android.settings.LocalePicker");*/
		    intent = new Intent( android.provider.Settings.ACTION_LOCALE_SETTINGS );
			startActivity(intent);
			/*i = new Intent(Settings.this, Language.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);*/
			break;

		case R.id.btn_back_color:
			i = new Intent(Settings.this, Background.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_remove_ad:

			break;
		case R.id.btn_back:

			Settings.this.finish();
			overridePendingTransition( R.anim.left_in,R.anim.right_out);
			break;
		default:
			break;
		}

	}
}
