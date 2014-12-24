package com.smoketherapy.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smoketherapy.Application;
import com.smoketherapy.R;

public class Background extends Activity implements OnClickListener{


	LinearLayout lyt_appback;
	TextView txt_background,txt_by;
	Button btn_aqua,btn_alexa,btn_ameth,btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.background_screen);

		txt_background = (TextView)findViewById(R.id.txt_background);
		txt_by = (TextView)findViewById(R.id.txt_by);
		
		btn_aqua = (Button)findViewById(R.id.btn_aqua);
		btn_alexa = (Button)findViewById(R.id.btn_alexa);
		btn_ameth = (Button)findViewById(R.id.btn_ameth);
		btn_back = (Button)findViewById(R.id.btn_back);
	
		btn_aqua.setOnClickListener(this);
		btn_alexa.setOnClickListener(this);
		btn_ameth.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		
		if(Application.AppDrawableName.equals("green_back"))
		{
			btn_aqua.setEnabled(false);
		}
		else if(Application.AppDrawableName.equals("violet_back"))
		{
			btn_alexa.setEnabled(false);
		}
		else if(Application.AppDrawableName.equals("purpal_back"))
		{
			btn_ameth.setEnabled(false);
		}
			 
		setFontTypeFace();
	}

	public void setFontTypeFace()
	{
		txt_background.setTypeface(Application.tf);
		txt_by.setTypeface(Application.tf);
		btn_aqua.setTypeface(Application.tf);
		btn_alexa.setTypeface(Application.tf);
		btn_ameth.setTypeface(Application.tf);		
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
		case R.id.btn_aqua:
			btn_aqua.setEnabled(false);
			btn_alexa.setEnabled(true);
			btn_ameth.setEnabled(true);
			
			Application.prefrences.Store_App_Backgroung("green_back");
			setAppBackground();
			//Application.
			
			break;

		case R.id.btn_alexa:
			btn_alexa.setEnabled(false);
			btn_aqua.setEnabled(true);
			btn_ameth.setEnabled(true);
			
			Application.prefrences.Store_App_Backgroung("violet_back");
			setAppBackground();
			break;
		case R.id.btn_ameth:
			btn_ameth.setEnabled(false);
			btn_alexa.setEnabled(true);
			btn_aqua.setEnabled(true);
			Application.prefrences.Store_App_Backgroung("purpal_back");
			setAppBackground();
			break;
			
		case R.id.btn_back:
	
			Background.this.finish();
			overridePendingTransition( R.anim.left_in,R.anim.right_out);
			
			break;
		default:
			break;
		}
		
	}
	
	public void setAppBackground()
	{
		Application.AppDrawableName = Application.prefrences.Get_App_Background();
		Application.appDrawableId = getResources().getIdentifier(Application.AppDrawableName, "drawable", getPackageName());
		lyt_appback.setBackgroundResource(Application.appDrawableId);
	}
}

