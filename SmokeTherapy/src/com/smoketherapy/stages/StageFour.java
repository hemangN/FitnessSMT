package com.smoketherapy.stages;

import android.app.Activity;
import android.app.NotificationManager;
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
import android.widget.Toast;

import com.smoketherapy.Application;
import com.smoketherapy.LongPress_Detector;
import com.smoketherapy.R;
import com.smoketherapy.db.SmokeTracker;
import com.smoketherapy.help.Help;
import com.smoketherapy.settings.Settings;
import com.smoketherapy.staticdata.StaticData;
import com.smoketherapy.stats.Statistics;

public class StageFour extends Activity implements OnClickListener {

	LinearLayout lyt_appback;
	LinearLayout lyt_tap, lyt_tap_extra, lyt_tap_end_therapy;
	TextView txt_stage4, txt_allow_cig, txt_allow_cig_extra, txt_end_therapy,
			txt_hold_on_inst;
	Button btn_settings, btn_help, btn_withdraw_cigge, btn_stat;
	ImageView img_stop_cigge, img_prog;
	// ================================= To Maintain Double Click
	// ==================//
	int click;
	
	//============= To cancel All Notifications on app open ===================//
		NotificationManager mgr;


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent a = new Intent(Intent.ACTION_MAIN);
		a.addCategory(Intent.CATEGORY_HOME);
		a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(a);

	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_four_screen);

		lyt_tap = (LinearLayout) findViewById(R.id.lyt_tap);
		lyt_tap_extra = (LinearLayout) findViewById(R.id.lyt_tap_extra);
		lyt_tap_end_therapy = (LinearLayout) findViewById(R.id.lyt_tap_end_therapy);

		img_stop_cigge = (ImageView) findViewById(R.id.img_stop_cigge);
		img_prog = (ImageView) findViewById(R.id.img_prog);

		btn_settings = (Button) findViewById(R.id.btn_settings);
		btn_help = (Button) findViewById(R.id.btn_help);
		btn_withdraw_cigge = (Button) findViewById(R.id.btn_withdraw_cigge);
		btn_stat = (Button) findViewById(R.id.btn_stat);

		txt_stage4 = (TextView) findViewById(R.id.txt_stage4);
		txt_allow_cig = (TextView) findViewById(R.id.txt_allow_cig);
		txt_allow_cig_extra = (TextView) findViewById(R.id.txt_allow_cig_extra);
		txt_end_therapy = (TextView) findViewById(R.id.txt_end_therapy);
		txt_hold_on_inst = (TextView) findViewById(R.id.txt_hold_on_inst);

		btn_settings.setOnClickListener(this);
		btn_help.setOnClickListener(this);
		btn_withdraw_cigge.setOnClickListener(this);
		btn_stat.setOnClickListener(this);
		img_prog.setOnClickListener(this);
		//lyt_tap_extra.setOnClickListener(this);
		lyt_tap_extra.setOnTouchListener(new LongPress_Detector()
		{
			@Override
			public void onLongPressConfirm() {
				
				
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				 
				// Vibrate for 300 milliseconds
				v.vibrate(300);
				Application.prefrences.Store_Extra_Cigge_Taken_Flag(true);
				lyt_tap_extra.setVisibility(View.GONE);
				lyt_tap.setVisibility(View.GONE);
				StaticData.EXTRA_CIGGE_FLAG = false;
				img_stop_cigge.setVisibility(View.VISIBLE);

				// ==================Update Smoke Traker With Extra Cigge
				// ===================//
				SmokeTracker smokeTracker = Application.dbHelper.getSmokeTrackerInfo();
				Application.dbHelper.updateSmokeTrackerInfo(smokeTracker);

			
			};
		}
		);
		lyt_tap_end_therapy.setOnTouchListener(new LongPress_Detector() {
			@Override
			public void onLongPressConfirm() 
			{
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

				// Vibrate for 300 milliseconds
				v.vibrate(300);
				
				Intent intent = new Intent(StageFour.this,Congratulation.class);
				startActivity(intent);
				overridePendingTransition(R.anim.right_in,R.anim.left_out);
				StageFour.this.finish();
			}

		});
		lyt_tap.setOnTouchListener(new LongPress_Detector() 
		{
			@Override
			public void onLongPressConfirm() {

				lyt_tap.setBackgroundResource(R.drawable.stage_back);
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

				// Vibrate for 300 milliseconds
				v.vibrate(300);

				int Allow_count = Application.prefrences.Get_Allow_Cigge_Count();
				Allow_count--;
				Application.prefrences.Store_Allow_Cigge_Count(Allow_count);
				txt_allow_cig.setText("cigarette allowance: \n" + Allow_count);
				StaticData.EXTRA_CIGGE_FLAG = false;
				
				if (Application.prefrences.Get_End_Therapy_Flag()) 
				{
					
					lyt_tap_end_therapy.setVisibility(View.VISIBLE);
					img_stop_cigge.setVisibility(View.GONE);
					btn_withdraw_cigge.setVisibility(View.GONE);
					lyt_tap.setVisibility(View.GONE);
					lyt_tap_extra.setVisibility(View.GONE);
					img_prog.setBackgroundResource(R.drawable.progress_4);
					
					Application.prefrences.Store_Complete_Stat_Flag(true);
				}
				else if (Allow_count == 0) 
				{
					lyt_tap_extra.setVisibility(View.GONE);
					lyt_tap.setVisibility(View.GONE);
					img_stop_cigge.setVisibility(View.VISIBLE);
					if(!Application.prefrences.Get_Extra_Cigge_Taken_Flag())
					{
						btn_withdraw_cigge.setVisibility(View.VISIBLE);
					}
				}

			};
		});
		setFontTypeFace();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// ===========Set App State =================================//
		Application.prefrences.Store_App_State(false);
	}

	public void setFontTypeFace() {

		txt_stage4.setTypeface(Application.tf);
		txt_allow_cig.setTypeface(Application.tf);
		txt_allow_cig_extra.setTypeface(Application.tf);
		txt_end_therapy.setTypeface(Application.tf);
		txt_hold_on_inst.setTypeface(Application.tf);
		btn_settings.setTypeface(Application.tf);
		btn_help.setTypeface(Application.tf);
		btn_withdraw_cigge.setTypeface(Application.tf);
		btn_stat.setTypeface(Application.tf);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lyt_appback = (LinearLayout) findViewById(R.id.lyt_appback);
		lyt_appback.setBackgroundResource(Application.appDrawableId);

		//============= To cancel All Notifications on app open ===================//
		mgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mgr.cancel(0);
		
		// ===========Set App State =================================//
		Application.prefrences.Store_App_State(true);

		click = 0;
		btn_withdraw_cigge.setText("WITHDRAW CIGARETTE");

		// ============= Check If Extra Cigge Taken =======================//
		if (Application.prefrences.Get_Extra_Cigge_Taken_Flag()) 
		{
			btn_withdraw_cigge.setVisibility(View.GONE);
		}

		if (Application.prefrences.Get_End_Therapy_Flag()) 
		{
			
			if (Application.prefrences.Get_Allow_Cigge_Count() != 0) 
			{
				StaticData.EXTRA_CIGGE_FLAG = false;
				img_stop_cigge.setVisibility(View.GONE);
				btn_withdraw_cigge.setVisibility(View.GONE);
				lyt_tap_extra.setVisibility(View.GONE);
				lyt_tap.setVisibility(View.VISIBLE);
				txt_allow_cig.setText("cigarette allowance: \n"+ Application.prefrences.Get_Allow_Cigge_Count());
				
			} 
			else
			{
				lyt_tap_end_therapy.setVisibility(View.VISIBLE);
				img_stop_cigge.setVisibility(View.GONE);
				btn_withdraw_cigge.setVisibility(View.GONE);
				lyt_tap.setVisibility(View.GONE);
				lyt_tap_extra.setVisibility(View.GONE);
				img_prog.setBackgroundResource(R.drawable.progress_4);
			}
		}
		else 
		{
			if (StaticData.EXTRA_CIGGE_FLAG) 
			{
				img_stop_cigge.setVisibility(View.GONE);
				btn_withdraw_cigge.setVisibility(View.GONE);
				lyt_tap.setVisibility(View.GONE);
				lyt_tap_extra.setVisibility(View.VISIBLE);
			}

			System.out.println("=======Cigarette Allow Count in Stage 4======"
					+ Application.prefrences.Get_Allow_Cigge_Count());

			if (Application.prefrences.Get_Allow_Cigge_Count() != 0) 
			{
				StaticData.EXTRA_CIGGE_FLAG = false;
				img_stop_cigge.setVisibility(View.GONE);
				btn_withdraw_cigge.setVisibility(View.GONE);
				lyt_tap_extra.setVisibility(View.GONE);
				lyt_tap.setVisibility(View.VISIBLE);
				txt_allow_cig.setText("cigarette allowance: \n"+ Application.prefrences.Get_Allow_Cigge_Count());
				
			} 
			else if (Application.prefrences.Get_Allow_Cigge_Count() == 0 && StaticData.EXTRA_CIGGE_FLAG == false) 
			{
				img_stop_cigge.setVisibility(View.VISIBLE);
				if(!Application.prefrences.Get_Extra_Cigge_Taken_Flag())
				{
					btn_withdraw_cigge.setVisibility(View.VISIBLE);
				}
				lyt_tap.setVisibility(View.GONE);
				lyt_tap_extra.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i;

		switch (v.getId()) {
		case R.id.img_prog:

			break;

		case R.id.btn_settings:

			i = new Intent(StageFour.this, Settings.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_help:
			i = new Intent(StageFour.this, Help.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_withdraw_cigge:
			click++;
			if (click == 1) {
				btn_withdraw_cigge.setText("EXTRA CIGARETTE");
			}
			if (click == 2) {
				i = new Intent(StageFour.this, Warning.class);
				startActivity(i);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
			break;

		case R.id.btn_stat:
			i = new Intent(StageFour.this, Statistics.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;

		case R.id.lyt_tap_extra:
			lyt_tap_extra.setVisibility(View.GONE);
			StaticData.EXTRA_CIGGE_FLAG = false;
			img_stop_cigge.setVisibility(View.VISIBLE);

			// ==================Update Smoke Traker With Extra Cigge
			// ===================//
			SmokeTracker smokeTracker = Application.dbHelper
					.getSmokeTrackerInfo();
			Application.dbHelper.updateSmokeTrackerInfo(smokeTracker);

			break;
		default:
			break;
		}
	}
}
