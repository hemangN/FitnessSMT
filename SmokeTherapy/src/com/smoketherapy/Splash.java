package com.smoketherapy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smoketherapy.personalinfo.PersonalInfo_gender;
import com.smoketherapy.stages.BeginStageOne;
import com.smoketherapy.stages.BeginStageTwo;
import com.smoketherapy.stages.StageFour;
import com.smoketherapy.stages.StageOne;
import com.smoketherapy.stages.StageThree;
import com.smoketherapy.stages.StageTwo;

public class Splash extends Activity
{
	Handler hand;
	Intent intent;
	
	LinearLayout lyt_appback;
	TextView txt_by;
	
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
		setContentView(R.layout.splash_screen);
		
		txt_by = (TextView)findViewById(R.id.txt_by);
		
		setFontTypeFace();
		
		hand = new Handler(); 
		hand.postDelayed(run,5000);
			
	}
	
	public void setFontTypeFace()
	{
		txt_by.setTypeface(Application.tf);
	}
	
	Runnable run = new Runnable() { 
	    @Override 

	    public void run() { 
	    	
	    hand.removeCallbacksAndMessages(null);
	    if(Application.prefrences.Get_stage_three_state())
	    {
	    	intent = new Intent(Splash.this,StageFour.class);
	    	startActivity(intent);
	    	Splash.this.finish();
	    }
	    else if(Application.prefrences.Get_stage_two_state())
	    {
	    	intent = new Intent(Splash.this,StageThree.class);
	    	startActivity(intent);
	    	Splash.this.finish();
	    }
	    else if(Application.prefrences.Get_Stage_Two_Start_State())
	    {
	    	intent = new Intent(Splash.this,StageTwo.class);
	    	startActivity(intent);
	    	Splash.this.finish();
	    }
	    else if(Application.prefrences.Get_stage_one_state())
	    {
	    	intent = new Intent(Splash.this,BeginStageTwo.class);
	    	startActivity(intent);
	    	Splash.this.finish();
	    }
	    else if(Application.prefrences.Get_Stage_One_Start_State())
	    {
	    	intent = new Intent(Splash.this,StageOne.class);
	    	startActivity(intent);
	    	Splash.this.finish();
	    }
	    //here goes to stage one entry pref
	    else if(Application.prefrences.Get_personalinfo_state())
	    {
	    	intent = new Intent(Splash.this,BeginStageOne.class);
	    	startActivity(intent);
	    	Splash.this.finish(); 	
	    }
	    
	    else if(Application.prefrences.Get_agree_state())
	    {
	    	intent = new Intent(Splash.this,PersonalInfo_gender.class);
	    	startActivity(intent);
	    	Splash.this.finish();
	    }
	    else if(Application.prefrences.Get_welcome_state())
	    {
	    	intent = new Intent(Splash.this,Agreement.class);
	    	startActivity(intent);
	    	Splash.this.finish();
	    }
	    else
	    {
	    	intent = new Intent(Splash.this, Welcome.class);
	    	startActivity(intent);
	    	Splash.this.finish();
	    }
	    

	    } 

	}; 
	
}
