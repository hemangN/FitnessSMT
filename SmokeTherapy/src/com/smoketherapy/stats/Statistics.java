package com.smoketherapy.stats;

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

public class Statistics extends Activity implements OnClickListener{

	LinearLayout lyt_appback;
	TextView txt_statistics;
	Button btn_s1,btn_s2,btn_s3,btn_comp_stat,btn_back;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lyt_appback = (LinearLayout)findViewById(R.id.lyt_appback);		
		lyt_appback.setBackgroundResource(Application.appDrawableId);
		
		//============= Check For The Stat Flags ========================//
		if(Application.prefrences.Get_StageTwo_Stat_Flag())
		{
			btn_s2.setEnabled(true);
		}
		if(Application.prefrences.Get_StageThree_Stat_Flag())
		{
			btn_s3.setEnabled(true);
		}
		if(Application.prefrences.Get_Complete_Stat_Flag())
		{
			btn_comp_stat.setEnabled(true);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics_screen);
			
		txt_statistics = (TextView)findViewById(R.id.txt_statistics);
		
		btn_s1 = (Button)findViewById(R.id.btn_s1);
		btn_s2 = (Button)findViewById(R.id.btn_s2);
		btn_s3 = (Button)findViewById(R.id.btn_s3);
		btn_comp_stat = (Button)findViewById(R.id.btn_comp_stat);
		btn_back = (Button)findViewById(R.id.btn_back);
		
		btn_s1.setOnClickListener(this);
		btn_s2.setOnClickListener(this);
		btn_s3.setOnClickListener(this);
		btn_comp_stat.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		
		setFontTypeFace();
	}

	public void setFontTypeFace()
	{		
		txt_statistics.setTypeface(Application.tf);
		btn_s1.setTypeface(Application.tf);
		btn_s2.setTypeface(Application.tf);
		btn_s3.setTypeface(Application.tf);
		btn_comp_stat.setTypeface(Application.tf);
		btn_back.setTypeface(Application.tf);	
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i;
		
		switch (v.getId()) {
		case R.id.btn_s1:
			i = new Intent(Statistics.this,stage_one_statistics.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;

		case R.id.btn_s2:
			i = new Intent(Statistics.this,stage_two_statistic.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_s3:
			i = new Intent(Statistics.this,stage_three_statistics.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_comp_stat:
			//i = new Intent(Statistics.this,CpCompStat.class);
			i = new Intent(Statistics.this,CompleteStatistics.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.btn_back:
			Statistics.this.finish();
			overridePendingTransition( R.anim.left_in,R.anim.right_out);
			break;
		default:
			break;
		}
	}
}

