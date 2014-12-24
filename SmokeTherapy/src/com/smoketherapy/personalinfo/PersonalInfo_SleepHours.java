package com.smoketherapy.personalinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smoketherapy.Application;
import com.smoketherapy.R;
import com.smoketherapy.staticdata.StaticData;

public class PersonalInfo_SleepHours extends Activity implements
		OnClickListener {

	RelativeLayout lyt_appback;
	TextView txt_personal_info, txt_avg_sleep_hours, txt_sleep_inst;
	EditText edt_avg_sleep_time;
	Button btn_cont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info_screen_two_relate);

		txt_personal_info = (TextView) findViewById(R.id.txt_personal_info);
		txt_avg_sleep_hours = (TextView) findViewById(R.id.txt_avg_sleep_hours);
		txt_sleep_inst = (TextView) findViewById(R.id.txt_sleep_inst);

		edt_avg_sleep_time = (EditText) findViewById(R.id.edt_avg_sleep_time);

		btn_cont = (Button) findViewById(R.id.btn_cont);

		btn_cont.setOnClickListener(this);
		edt_avg_sleep_time.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(s.length() != 0 )
				{
					if(Integer.valueOf(edt_avg_sleep_time.getText().toString()) > 24 || Integer.valueOf(edt_avg_sleep_time.getText().toString()) <= 0)
					{
						edt_avg_sleep_time.setText("");
						btn_cont.setVisibility(View.GONE);
						edt_avg_sleep_time.setBackgroundResource(R.drawable.agree_button);
						
						Toast.makeText(PersonalInfo_SleepHours.this,"Enter Valid hours",Toast.LENGTH_LONG).show();
					}
					else
					{
						btn_cont.setVisibility(View.VISIBLE);
						edt_avg_sleep_time.setBackgroundResource(R.drawable.agree_button_clicked);
					}
				
				}
				else
				{
					btn_cont.setVisibility(View.GONE);
					edt_avg_sleep_time.setBackgroundResource(R.drawable.agree_button);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});

		setFontTypeFace();
	}

	public void setFontTypeFace()
	{
		txt_personal_info.setTypeface(Application.tf);
		txt_avg_sleep_hours.setTypeface(Application.tf);
		txt_sleep_inst.setTypeface(Application.tf);
		edt_avg_sleep_time.setTypeface(Application.tf);
		btn_cont.setTypeface(Application.tf);	
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lyt_appback = (RelativeLayout)findViewById(R.id.lyt_appback);		
		lyt_appback.setBackgroundResource(Application.appDrawableId);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btn_cont:

			StaticData.SLEEP_HOURS = edt_avg_sleep_time.getText().toString().trim();
			Intent intent = new Intent(PersonalInfo_SleepHours.this,
					PersonalInfo_WakeUpTime.class);
			startActivity(intent);
			this.finish();
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			

			break;

		case R.id.edt_avg_sleep_time:
			edt_avg_sleep_time.setFocusable(true);
			btn_cont.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

	}
}
