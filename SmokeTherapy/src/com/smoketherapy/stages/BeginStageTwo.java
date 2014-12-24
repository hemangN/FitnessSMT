package com.smoketherapy.stages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.smoketherapy.Splash;
import com.smoketherapy.alarmmanager.AlarmSetter;
import com.smoketherapy.db.BaseCalc;
import com.smoketherapy.help.Help;
import com.smoketherapy.settings.Settings;
import com.smoketherapy.stats.Statistics;

public class BeginStageTwo extends Activity implements OnClickListener {

	LinearLayout lyt_appback;
	LinearLayout lyt_tap;
	TextView txt_stage2, txt_begin_stage2, txt_hold_on_inst, stage_two_inst;
	Button btn_stat, btn_settings, btn_help;
	ImageView img_prog;

	int avg_cigge;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_two_begin_screen);
		
		//=================== Check Start State ========================//
		if(Application.prefrences.Get_Stage_Two_Start_State())
	    {
	    	Intent intent = new Intent(BeginStageTwo.this,StageTwo.class);
	    	startActivity(intent);
	    	BeginStageTwo.this.finish();
	    }
		//====================End ======================================//
		
		txt_stage2 = (TextView) findViewById(R.id.txt_stage2);
		txt_begin_stage2 = (TextView) findViewById(R.id.txt_begin_stage2);
		txt_hold_on_inst = (TextView) findViewById(R.id.txt_hold_on_inst);
		stage_two_inst = (TextView) findViewById(R.id.stage_two_inst);

		btn_stat = (Button) findViewById(R.id.btn_stat);
		btn_settings = (Button) findViewById(R.id.btn_settings);
		btn_help = (Button) findViewById(R.id.btn_help);

		img_prog = (ImageView) findViewById(R.id.img_prog);

		lyt_tap = (LinearLayout) findViewById(R.id.lyt_tap);

		btn_stat.setOnClickListener(this);
		btn_settings.setOnClickListener(this);
		btn_help.setOnClickListener(this);

		lyt_tap.setOnTouchListener(new LongPress_Detector() {

			@Override
			public void onLongPressConfirm() {
				// TODO Auto-generated method stub
				super.onLongPressConfirm();

				
				
				//============ Store Prefrences =======================
				Application.prefrences.Store_Stage_Two_Start_State(true);
				
				
				//============ Store user therapy time in prefrence ===========
				
			    avg_cigge = Application.prefrences.Get_Avg_No_Of_Cigge();
			    System.out.println("=======Avg No Of Ciggie ============"+avg_cigge);
			    
				BaseCalc baseCalc = Application.dbHelper.getBaseCalcInfo(avg_cigge);
				
				Application.prefrences.Store_Day_Req_In_STAGE2(baseCalc.get_S2_Time());
				Application.prefrences.Store_Day_Req_In_STAGE3(baseCalc.get_S3_Time());
				Application.prefrences.Store_Day_Req_In_STAGE4(baseCalc.get_S4_Time());
				Application.prefrences.Store_Total_Therapy_Time(baseCalc.get_Total_Therapy_Time());
				
		
				
				System.out.println("=====Day Req In Stage2 ============="+Application.prefrences.Get_Day_In_STAGE2());
				System.out.println("======Day req In Stage3============="+Application.prefrences.Get_Day_In_STAGE3());
				System.out.println("======Day req In Stage4============="+Application.prefrences.Get_Day_In_STAGE4());
				
				
				//===========Dummy testing for interval ========================//
				float notificationInterval_in_Minute =(((float)Application.prefrences.Get_Wake_Hours() /(float) avg_cigge)) * 60 ;
				//================ Dummy Interval Counted by dividing actual by 40 =============================
				float dummy_interval = notificationInterval_in_Minute/40f;
				
				System.out.println("========notificationInterval_in_Minute==========="+notificationInterval_in_Minute);
				System.out.println("========dummy_interval==========="+dummy_interval);
				
				//========= Set Alarm To Invoke Notification Service ===============//
				setNotificationServiceAlarm();
				
			    
				// Vibrate for 300 milliseconds
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);	
				v.vibrate(300);
				
				Intent intent = new Intent(BeginStageTwo.this, StageTwo.class);
				startActivity(intent);
				BeginStageTwo.this.finish();
			}

		});
		
		setFontTypeFace();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent a = new Intent(Intent.ACTION_MAIN);
		a.addCategory(Intent.CATEGORY_HOME);
		a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(a);

	};
	
	public void setNotificationServiceAlarm()
	{
		Calendar c = Calendar.getInstance();
		
		c.add(Calendar.DATE, 1);
		
		String nextDate = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
		
		nextDate = nextDate+" "+"00:00";
		
		//================== Store This date for furthur use ===============//
		Application.prefrences.Store_Date_To_Invoke_Notification_Service(nextDate);
		
	    Date alarmDate=null;
	
		try {
			alarmDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(nextDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	    System.out.println("=======Alarm Date For invoke sevice======="+alarmDate.toString());
	    
	    String message = "INVOKE_NOTIFICATION_SERVICE";
		
		AlarmSetter.setAlarm(getApplicationContext(),alarmDate.getTime(), message);
		System.out.println("====Alarm Schedule to Start Notification Service at========"+alarmDate.toString());
	}
	
	public void setFontTypeFace()
	{
		
		txt_stage2.setTypeface(Application.tf);
		txt_begin_stage2.setTypeface(Application.tf);
		txt_hold_on_inst.setTypeface(Application.tf);
		stage_two_inst.setTypeface(Application.tf);
		btn_stat.setTypeface(Application.tf);
		btn_settings.setTypeface(Application.tf);		
		btn_help.setTypeface(Application.tf);	
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lyt_appback = (LinearLayout)findViewById(R.id.lyt_appback);		
		lyt_appback.setBackgroundResource(Application.appDrawableId);
		
		//============= Check Start State ======================//
		if(Application.prefrences.Get_Stage_Two_Start_State())
	    {
	    	Intent intent = new Intent(BeginStageTwo.this,StageTwo.class);
	    	startActivity(intent);
	    	BeginStageTwo.this.finish();
	    }
		//=================== End ==============================//
		
		//============Count Avg No Of Ciige ========================== min 4 ANd Max 20
				avg_cigge = Application.prefrences.Get_Stage_One_Cigge()/ 5;
				Application.prefrences.Store_Avg_No_Of_Cigge(avg_cigge);
				
				avg_cigge = Application.prefrences.Get_Avg_No_Of_Cigge();
				System.out.println("==Avg Cigge In Onresume brfore update============="+avg_cigge);
				
				if(avg_cigge <= 4)
				{
					Application.prefrences.Store_Avg_No_Of_Cigge(4);
				}
				else if(avg_cigge >= 20)
				{
					Application.prefrences.Store_Avg_No_Of_Cigge(20);
				}
				
				System.out.println("==Avg Cigge In Onresume /frm prefrences after update============="+Application.prefrences.Get_Avg_No_Of_Cigge());
			    //=========== e      n       d =================================//
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent i;

		switch (v.getId()) {

		case R.id.btn_stat:
			i = new Intent(BeginStageTwo.this, Statistics.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;

		case R.id.btn_settings:
			i = new Intent(BeginStageTwo.this, Settings.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_help:
			i = new Intent(BeginStageTwo.this, Help.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;

		default:
			break;
		}
	}

}
