package com.smoketherapy.stages;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smoketherapy.Application;
import com.smoketherapy.LongPress_Detector;
import com.smoketherapy.R;
import com.smoketherapy.Splash;
import com.smoketherapy.db.StageOneInfo;
import com.smoketherapy.help.Help;
import com.smoketherapy.settings.Settings;

public class StageOne extends Activity implements OnClickListener{

	LinearLayout lyt_appback;
	Handler mHandler;
/*	//=================== Date objs ===================================//
	SimpleDateFormat sdf;
	
	
	//===================Widgets ===========================================//
	LinearLayout smokeprog;
	FrameLayout frm_swipe;
	TextView txt_stage1;
	ImageView img_cigge;
	ImageView img_anim,img_stc;
	LinearLayout btn_option,btn_info;
	ProgressBar therapy_progress;
	AnimationDrawable swipe;
	Handler hand;
	AlertDialog.Builder builder;
	AlertDialog alert;
	ImageView img_smokeprog;*/
	
	LinearLayout lyt_tap,lyt_before_confirm,lyt_after_confirm;
	TextView txt_stage1,txt_tap_inst,stage_one_inst;
	Button btn_settings,btn_help;
	ImageView img_prog;
	
	//==========================Get Display X AND Y SIZE =====================//
	int heightPixels;
	int widthPixels;
	int minSwipeDistance;
	
	//========================= Move Animation ============================//
	Animation animMove;
	
	
	@Override
	protected void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();
		//========== Check If Begin Stage Two started=============
		 if(Application.prefrences.Get_stage_one_state())
		    {
		    	Intent intent = new Intent(StageOne.this,BeginStageTwo.class);
		    	startActivity(intent);
		    	StageOne.this.finish();
		    }
		//===========Set App State =================================//
		Application.prefrences.Store_App_State(true);
		
		//===========Set App BackGround ============================//
		lyt_appback = (LinearLayout)findViewById(R.id.lyt_appback);		
		lyt_appback.setBackgroundResource(Application.appDrawableId);
	}
	@Override
	protected void onPause() 
	{
		super.onPause();
		Application.prefrences.Store_App_State(false);
		System.out.println("=======On Pause Called ==============");
		//StageOne.this.finish();
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_one_screen);
		
		mHandler = new Handler();
		
		lyt_tap = (LinearLayout)findViewById(R.id.lyt_tap);
		lyt_before_confirm = (LinearLayout)findViewById(R.id.lyt_before_confirm);
		lyt_after_confirm  = (LinearLayout)findViewById(R.id.lyt_after_confirm);
		
		img_prog = (ImageView)findViewById(R.id.img_prog);
		
		btn_settings = (Button)findViewById(R.id.btn_settings);
		btn_help = (Button)findViewById(R.id.btn_help);
		
		txt_stage1 = (TextView)findViewById(R.id.txt_stage1);
		txt_tap_inst = (TextView)findViewById(R.id.txt_tap_inst);
		stage_one_inst = (TextView)findViewById(R.id.stage_one_inst);
		
		img_prog.setOnClickListener(this);
		btn_settings.setOnClickListener(this);
		btn_help.setOnClickListener(this);
		
		lyt_tap.setOnTouchListener(new LongPress_Detector()
		{
			@Override
			public void onLongPressConfirm() {
				
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						lyt_after_confirm.setVisibility(View.GONE);
						lyt_before_confirm.setVisibility(View.VISIBLE);
						lyt_tap.setEnabled(true);
					}
				}, 2000);
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				 
				// Vibrate for 300 milliseconds
				v.vibrate(300);
				lyt_after_confirm.setVisibility(View.VISIBLE);
				lyt_before_confirm.setVisibility(View.GONE);
				lyt_tap.setBackgroundResource(R.drawable.stage_back);
				lyt_tap.setEnabled(false);
				
				
				//=========== Insert Date in StageOne table ======================//
				
				SimpleDateFormat sdf;
				String currentDate;
				String currentTime;
				Calendar calendar = Calendar.getInstance();
				
				sdf = new SimpleDateFormat("dd-MM-yyyy");
				
			    currentDate = sdf.format(calendar.getTime());
			    
			    sdf = new SimpleDateFormat("HH:mm");
			    
			    currentTime = sdf.format(calendar.getTime());
				
				StageOneInfo mStageOneInfo = new StageOneInfo(currentDate, currentTime,1);
				
				long rawId = Application.dbHelper.insertStage1Info(mStageOneInfo);
				
				//System.out.println("======== DB entry Result =========="+rawId);
				
				if(Application.prefrences.Get_Cigarette_Reg_Flag() == true)
				{
					//============ Save total no of cigge in prefrences===================//
					int no_of_cigge = Application.prefrences.Get_Stage_One_Cigge() + 1 ;
				
					Application.prefrences.Store_Stage_One_Total_Cigge(no_of_cigge);
				
					System.out.println("========Total no Of Cigge Stage 1==========="+no_of_cigge);
				}
			
			};
		}
		);
		
/*		hand = new Handler(); 
        // Get Display Size ===================================//
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		//====================== Load Move NAimation ================================/
		animMove = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.move);

		heightPixels = metrics.heightPixels;
		widthPixels =  metrics.widthPixels;
		minSwipeDistance = (int) (widthPixels * 0.60);
		System.out.println("=============Display Height is : "+heightPixels);
		System.out.println("=============Display Width Is : "+widthPixels);
		System.out.println("=============Display min swipe distance Is : "+minSwipeDistance);

		//===============ends ===================================//
		
		smokeprog  = (LinearLayout)findViewById(R.id.smokeprog);
		txt_stage1 = (TextView)findViewById(R.id.txt_stage1);
		img_cigge  = (ImageView)findViewById(R.id.img_cigge);
		frm_swipe  = (FrameLayout)findViewById(R.id.frm_swipe);
		//img_cigge_anim  = (ImageView)findViewById(R.id.img_cigge_anim);
		img_smokeprog  = (ImageView)findViewById(R.id.img_smokeprog);
		img_anim	   = (ImageView)findViewById(R.id.img_anim);
		img_stc		   = (ImageView)findViewById(R.id.img_stc);

		btn_option = (LinearLayout)findViewById(R.id.btn_option);
		btn_info   = (LinearLayout)findViewById(R.id.btn_info);
		
		//therapy_progress = (ProgressBar)findViewById(R.id.therapy_progress);
		
		btn_option.setOnClickListener(this);
		btn_info.setOnClickListener(this);
		img_cigge.setOnClickListener(this);
		frm_swipe.setOnClickListener(this);
		//img_cigge_anim.setOnClickListener(this);
		
		//set swipe anim
		
		
		//swipe = (AnimationDrawable) img_cigge_anim.getBackground();
		
		Cursor cursor = Application.dbHelper.getBaseCalc();
		Log.d("======= Returned Data ================",""+cursor);
		System.out.println("======= Returned Data ================"+cursor);
	
		if(Application.prefrences.Get_stage_one_flag())
		{
			Application.prefrences.Store_stage_one_flag(false);
			builder = new AlertDialog.Builder(this);
			builder.setTitle("Stage Two Will Start At "+""+Application.prefrences.Get_agree_date_time()+"");
			builder.setMessage("You will be under observation for the next five days and to smoke cigarette you must have to tap and then confirm your each smoke to get better result");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			alert = builder.create();
			alert.show();
			
		}
		
		frm_swipe.setOnTouchListener(new SwipeAnimation(frm_swipe,minSwipeDistance){
		@Override
		  public void onSwipeRight() 
		  {
			  Calendar c = Calendar.getInstance();
			
			  sdf = new SimpleDateFormat("dd-MM-yyyy");
			  String CurrentDate = sdf.format(c.getTime());
			  
			  sdf = new SimpleDateFormat("kk:mm");
			  String CurrentTime  = sdf.format(c.getTime());
			  
			  System.out.println("========= Current Date ================"+CurrentDate);
			  System.out.println("========= Current Time ================"+CurrentTime);
			  
			  Application.dbHelper.insertStage1Info(CurrentDate, CurrentTime);
			  
			  Application.dbHelper.updateTotalCiggeInStage1();
			  hand.removeCallbacksAndMessages(null); 
			  frm_swipe.setVisibility(View.GONE);
			  img_cigge.setVisibility(View.VISIBLE);
			  Toast.makeText(StageOne.this, "You have confirmed your smoke", Toast.LENGTH_SHORT).show();
		  }
		});

		img_cigge_anim.setOnTouchListener(new OnSwipeTouchListener(this) 
		{
			  @Override
			  public void onSwipeDown() {
			  }
			  
			  @Override
			  public void onSwipeLeft() {
				  
			  }
			  
			  @Override
			  public void onSwipeUp() {
			  }
			  
			  @Override
			  public void onSwipeRight() 
			  {
				  hand.removeCallbacksAndMessages(null); 
				  swipe.stop();
				  img_cigge_anim.setVisibility(View.GONE);
				  img_cigge.setVisibility(View.VISIBLE);
				  Toast.makeText(StageOne.this, "You have confirmed your smoke", Toast.LENGTH_SHORT).show();
			  }
			});
		
		setProgress();*/
		setFontTypeFace();
	}

	public void setFontTypeFace()
	{
		
		txt_stage1.setTypeface(Application.tf);
		txt_tap_inst.setTypeface(Application.tf);
		stage_one_inst.setTypeface(Application.tf);
		btn_settings.setTypeface(Application.tf);
		btn_help.setTypeface(Application.tf);		
		
	}
	
	/*Runnable run = new Runnable() { 
	    @Override 

	    public void run() { 

			img_cigge.setVisibility(View.VISIBLE);
			frm_swipe.setVisibility(View.GONE);
			hand.removeCallbacksAndMessages(null); 
	    } 

	}; 
	
	Runnable runMoveAnimation = new Runnable() { 
	    @Override 

	    public void run() { 

	    	img_anim.startAnimation(animMove);
	    	hand.postDelayed(runMoveAnimation,3000);
	    } 

	}; */
	
/*	public void setProgress()
	{
		Animation animation = new TranslateAnimation(0,100,0,0);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		smokeprog.startAnimation(animation);
		
	}*/
	
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		Intent i;
		switch (v.getId()) 
		{
		case R.id.img_prog:
			/*i = new Intent(StageOne.this,BeginStageTwo.class);
			startActivity(i);*/
			//this.finish();
			break;
		
		case R.id.btn_settings:
			i = new Intent(StageOne.this,Settings.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_help:
			i = new Intent(StageOne.this,Help.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
	/*	case R.id.btn_option:
			i = new Intent(getApplicationContext(),Options.class);
			startActivity(i);
			break;
			
		case R.id.btn_info:
			i = new Intent(getApplicationContext(),Info.class);
			startActivity(i);
			break;
		case R.id.img_cigge:
			img_cigge.setVisibility(View.GONE);
			frm_swipe.setVisibility(View.VISIBLE);
			hand.post(runMoveAnimation);
	        hand.postDelayed(run,30000);//run runnable after five sec
	        break;
		case R.id.img_cigge_anim:
			swipe.stop();
			img_cigge_anim.setVisibility(View.GONE);
			img_cigge.setVisibility(View.VISIBLE);
			break;*/
		default:
			break;
		}
	}
}

