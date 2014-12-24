package com.smoketherapy.personalinfo;

import java.util.Calendar;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.smoketherapy.Application;
import com.smoketherapy.R;
import com.smoketherapy.staticdata.StaticData;

public class PersonalInfo_WakeUpTime extends Activity implements
		OnClickListener {

	RelativeLayout lyt_appback;
	TextView txt_personal_info, txt_wake_up_time, txt_wakeup_inst;
	//EditText edt_avg_hour, edt_avg_min;
	Button btn_cont,btn_wake_up_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info_screen_three_relate);

		txt_personal_info = (TextView) findViewById(R.id.txt_personal_info);
		txt_wake_up_time = (TextView) findViewById(R.id.txt_wake_up_time);
		txt_wakeup_inst = (TextView) findViewById(R.id.txt_wakeup_inst);

		/*edt_avg_hour = (EditText) findViewById(R.id.edt_avg_hour);
		edt_avg_min = (EditText) findViewById(R.id.edt_avg_min);*/
		
		btn_cont = (Button) findViewById(R.id.btn_cont);
		btn_wake_up_time = (Button) findViewById(R.id.btn_wake_up_time);

		btn_cont.setOnClickListener(this);
		btn_wake_up_time.setOnClickListener(this);
		setFontTypeFace();
		/*edt_avg_hour.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
					if(s.length() != 0)
					{
						
						if(Integer.valueOf(edt_avg_hour.getText().toString()) > 24 || Integer.valueOf(edt_avg_hour.getText().toString()) <= 0 )
						{
						
								btn_cont.setVisibility(View.GONE);
								edt_avg_min.setText("");
								edt_avg_hour.setText("");
								Toast.makeText(PersonalInfo_WakeUpTime.this,"Enter Valid hours",Toast.LENGTH_LONG).show();
						}
						else
						{
							if(Integer.valueOf(edt_avg_hour.getText().toString()) == 24)
							{
								edt_avg_min.setText("");
								//edt_avg_min.setHint("00");
							    edt_avg_min.setEnabled(false);	
							    btn_cont.setVisibility(View.VISIBLE);
							}
							else
							{
								edt_avg_min.setEnabled(true);
								btn_cont.setVisibility(View.VISIBLE);
							}
						}
						if(Integer.valueOf(edt_avg_hour.getText().toString()) >= 24)
						{
							if(Integer.valueOf(edt_avg_hour.getText().toString()) == 24)
							{
								
								edt_avg_min.setHint("00");
							    edt_avg_min.setEnabled(false);	
							}
							else
							{
								if(edt_avg_min.getText().toString().length() == 0)
								{
									btn_cont.setVisibility(View.GONE);
								}
								edt_avg_hour.setText("");
								Toast.makeText(PersonalInfo_WakeUpTime.this,"Enter Valid hours",Toast.LENGTH_LONG).show();
							}
						}
						else if(Integer.valueOf(edt_avg_hour.getText().toString()) == 0)
						{
							edt_avg_hour.setText("");
							btn_cont.setVisibility(View.GONE);
							Toast.makeText(PersonalInfo_WakeUpTime.this,"Enter Valid hours",Toast.LENGTH_LONG).show();
						}
						else
						{
							btn_cont.setVisibility(View.VISIBLE);
							edt_avg_min.setEnabled(true);	
						}
						
					}
					else
					{
						
							btn_cont.setVisibility(View.GONE);
						
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
		
		edt_avg_min.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(s.length() != 0)
				{
					if(Integer.valueOf(edt_avg_min.getText().toString()) > 59)
					{
							edt_avg_min.setText("");
							Toast.makeText(PersonalInfo_WakeUpTime.this,"Enter Valid hours",Toast.LENGTH_LONG).show();
					}
					
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
		});*/
		/* edt_avg_hour.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				
				 // TODO Auto-generated method stub
				 if(keyCode == KeyEvent.FLAG_EDITOR_ACTION)
				 {

					 
                    Selection.setSelection((Editable) edt_avg_min.getText(),edt_avg_min.getSelectionStart());
                    edt_avg_min.requestFocus();
                    
                    return true;
                 }

				return false;
			}
		});*/
		
	}

	public void setFontTypeFace()
	{
		txt_personal_info.setTypeface(Application.tf);
		txt_wake_up_time.setTypeface(Application.tf);
		txt_wakeup_inst.setTypeface(Application.tf);
		/*edt_avg_hour.setTypeface(Application.tf);
		edt_avg_min.setTypeface(Application.tf);*/
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
		
		/*	if(edt_avg_min.getText().toString().equals("") || Integer.valueOf(edt_avg_min.getText().toString()) == 0)
			{
				edt_avg_min.setText("00");
			}
			StaticData.WAKE_UP_TIME = edt_avg_hour.getText().toString()+":"+edt_avg_min.getText().toString();
			System.out.println("===Wake Up Time ==========="+StaticData.WAKE_UP_TIME);*/
			
			Intent i = new Intent(getApplicationContext(),
					Confirm_StageOne_Entry.class);
			startActivity(i);
			this.finish();
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			
			break;

		case R.id.edt_avg_hour:
			btn_cont.setVisibility(View.VISIBLE);
			break;

		case R.id.edt_avg_min:
			btn_cont.setVisibility(View.VISIBLE);
			break;
		case R.id.btn_wake_up_time:
			Calendar c = Calendar.getInstance();
			TimePickerDialog timePickerDialog = new TimePickerDialog(PersonalInfo_WakeUpTime.this, timePickerListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
			timePickerDialog.setCancelable(false);
			timePickerDialog.setTitle("Wake Up Time");
			timePickerDialog.show();
			break;
		default:
			break;
		}
	}
	
	 private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
         

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				String hours = String.valueOf(hourOfDay);
				String min   = String.valueOf(minute);
				
				if(hours.length() == 1)
				{
					hours = "0"+hours;
				}
				if(min.length() == 1)
				{
					min = "0"+min;
				}
				
				btn_wake_up_time.setText(hours+":"+min);
				btn_wake_up_time.setBackgroundResource(R.drawable.agree_button_clicked);
				StaticData.WAKE_UP_TIME = hourOfDay+":"+minute;
				btn_cont.setVisibility(View.VISIBLE);
			}
	 
	    };
}
