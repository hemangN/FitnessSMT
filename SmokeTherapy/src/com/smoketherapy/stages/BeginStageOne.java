package com.smoketherapy.stages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smoketherapy.Application;
import com.smoketherapy.LongPress_Detector;
import com.smoketherapy.R;
import com.smoketherapy.alarmmanager.AlarmSetter;
import com.smoketherapy.db.PersonalInfo;
import com.smoketherapy.help.Help;
import com.smoketherapy.settings.Settings;
import com.smoketherapy.timeserver.SntpClient;

public class BeginStageOne extends Activity implements OnClickListener {

	//=================== TO Fetch Time From Time Server ===========================//
	SntpClient client;
	
	//==================== Static Notification Variables ============================//
	public static final String NOTIFY_STAGE_ONE_START = "start_stage_one";
	public static final String NOTIFY_STAGE_ONE_COMP  = "complete_stage_one";
	public static final int    DAYS_TO_START_S1		  = 1;
	public static final int	   DAYS_TO_COMP_S1	      = 6;
	
	LinearLayout lyt_appback;
	LinearLayout lyt_tap;
	TextView txt_stage1, txt_begin_stage1, txt_hold_on_inst, stage_one_inst;
	Button btn_settings, btn_help;
	ImageView img_prog;
	
	//================= Time And Date ========================//
	Calendar calendar;
	SimpleDateFormat sdf;
	
	//================ Persoanl Info Obj ======================//
	PersonalInfo personalInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_one_begin_screen);

		txt_stage1 = (TextView) findViewById(R.id.txt_stage1);
		txt_begin_stage1 = (TextView) findViewById(R.id.txt_begin_stage1);
		txt_hold_on_inst = (TextView) findViewById(R.id.txt_hold_on_inst);
		stage_one_inst = (TextView) findViewById(R.id.stage_one_inst);

		btn_settings = (Button) findViewById(R.id.btn_settings);
		btn_help = (Button) findViewById(R.id.btn_help);

		img_prog = (ImageView) findViewById(R.id.img_prog);

		lyt_tap = (LinearLayout) findViewById(R.id.lyt_tap);

		btn_settings.setOnClickListener(this);
		btn_help.setOnClickListener(this);
		lyt_tap.setOnClickListener(this);
		lyt_tap.setOnTouchListener(new LongPress_Detector() {

			@Override
			public void onLongPressConfirm() {
				// TODO Auto-generated method stub
				super.onLongPressConfirm();
				
				Application.prefrences.Store_Stage_One_Start_State(true);
				personalInfo = Application.dbHelper.getPersonalInfo();
				
				setNotifications(DAYS_TO_START_S1,NOTIFY_STAGE_ONE_START);
				setNotifications(DAYS_TO_COMP_S1,NOTIFY_STAGE_ONE_COMP);
				
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                
				// Vibrate for 300 milliseconds
				v.vibrate(300);
				Intent intent = new Intent(BeginStageOne.this, StageOne.class);
				startActivity(intent);
				//overridePendingTransition(R.anim.right_in, R.anim.left_out);
				BeginStageOne.this.finish();
				
			}

		});
		
		setFontTypeFace();
	}
	
	
	public void setFontTypeFace()
	{
				
		txt_stage1.setTypeface(Application.tf);
		txt_begin_stage1.setTypeface(Application.tf);
		txt_hold_on_inst.setTypeface(Application.tf);
		stage_one_inst.setTypeface(Application.tf);
		btn_settings.setTypeface(Application.tf);		
		btn_help.setTypeface(Application.tf);	
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lyt_appback = (LinearLayout)findViewById(R.id.lyt_appback);		
		lyt_appback.setBackgroundResource(Application.appDrawableId);
		
		//============ To Fetch time from time server ================//
		client = new SntpClient();
		if(client.requestTime("time.foo.com"))
		{
			    System.out.println("======== Reach In client request ===============");
				Long now = client.getNtpTime() + SystemClock.elapsedRealtime() - client.getNtpTimeReference();
				Date nowDate = new Date(now);
				System.out.println("============= Time From Time Server ============="+nowDate);
		}
	}
	
	public void setNotifications(int num_of_days, String message)
	{
		 	calendar = Calendar.getInstance();
		 	

			calendar.add(Calendar.DATE,num_of_days);
			
			sdf      = new SimpleDateFormat("dd-MM-yyyy");
			
			
			String nextDate = sdf.format(calendar.getTime());
				
			System.out.println("============ Wake Up Time ============="+Application.prefrences.Get_Wake_Up_Time());
			nextDate= nextDate+" "+Application.prefrences.Get_Wake_Up_Time();
			System.out.println("========= Date To Start Stage 1/Conp Stage1============"+nextDate);
			
			//will Overrite
			if(num_of_days == DAYS_TO_COMP_S1)
			{
				Application.prefrences.Store_Date_To_Comp_Stage1(nextDate);
			}
			else if(num_of_days == DAYS_TO_START_S1)
			{
				Application.prefrences.Store_Date_To_Start_Stage1(nextDate);
			}
		    System.out.println("========= Start Date to begin stage two / to comp stage 1 ====="+nextDate);
		    
			sdf      = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			
			Date start_Date = null;
			
		
			try {
				start_Date = sdf.parse(nextDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			System.out.println("======Alarm Date For BeginStage2 ================"+start_Date.toString());
			AlarmSetter.setAlarm(getApplicationContext(),start_Date.getTime(),message);
					
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i;

		switch (v.getId()) {

		case R.id.btn_settings:
			i = new Intent(BeginStageOne.this, Settings.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_help:
			i = new Intent(BeginStageOne.this, Help.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;

		default:
			break;
		}
	}

}
