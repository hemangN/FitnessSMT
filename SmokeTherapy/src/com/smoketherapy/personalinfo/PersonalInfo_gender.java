package com.smoketherapy.personalinfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smoketherapy.Application;
import com.smoketherapy.R;
import com.smoketherapy.staticdata.StaticData;

public class PersonalInfo_gender extends Activity implements OnClickListener{


	LinearLayout lyt_appback;
	TextView txt_personal_info,txt_gen;

	Button btn_male,btn_female,btn_cont;
	
		//==================== DATABASE VARIABLES ==================================//
	String Dob="";
	String Gender="";
	String Sleep_Time="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info_screen_one);
		
		txt_personal_info = (TextView)findViewById(R.id.txt_personal_info);
		txt_gen = (TextView)findViewById(R.id.txt_gen);
		
		btn_male = (Button)findViewById(R.id.btn_male);
		btn_female = (Button)findViewById(R.id.btn_female);
		btn_cont = (Button)findViewById(R.id.btn_cont);
	
		btn_male.setOnClickListener(this);
		btn_female.setOnClickListener(this);
		btn_cont.setOnClickListener(this);
		
		setFontTypeFace();
	}

	public void setFontTypeFace()
	{
		txt_personal_info.setTypeface(Application.tf);
		txt_gen.setTypeface(Application.tf);
		btn_male.setTypeface(Application.tf);
		btn_female.setTypeface(Application.tf);
		btn_cont.setTypeface(Application.tf);	
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lyt_appback = (LinearLayout)findViewById(R.id.lyt_appback);		
		lyt_appback.setBackgroundResource(Application.appDrawableId);
	}
	public void setTextSize()
	{
		
		
	}

/*	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(!StaticData.SLEEP_FROM.equals("") && !StaticData.SLEEP_TO.equals(""))
		{
			Date fDate = null;
        	Date tDate = null;
        	try 
        	{
        			fDate = df.parse(StaticData.SLEEP_FROM);
        			tDate = df.parse(StaticData.SLEEP_TO);
			} 
        	catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			txt_sleep.setText(DateFormat.format("hh:mm aaa",fDate)+" - "+DateFormat.format("hh:mm aaa",tDate));
		}
	}*/
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder;
		AlertDialog alert;
		
		switch (v.getId()) 
		{
		/*case R.id.btn_gender:
			
			builder = new AlertDialog.Builder(this);
			builder.setTitle("Select Gender");
			builder.setItems(StaticData.GENDER, new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int item) 
				{
					if(item == 0)
					{
						txt_gender.setText("Male");
						Gender = "Male";
					}
					else
					{
						txt_gender.setText("Female");
						Gender = "Female";
					}
					System.out.println("=========GENDER IS============="+Gender);
				}
			});
			
			alert = builder.create();
		   	alert.show();
	
			
			break;
		case R.id.btn_dob:
			dtdialog =  new DatePickerDialog(this,new PickDate(), dateAndTime.get(Calendar.YEAR),
		             dateAndTime.get(Calendar.MONTH),dateAndTime.get(Calendar.DAY_OF_MONTH));
			
			dtdialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() 
			 {
		         public void onClick(DialogInterface dialoginf, int which) 
		         {
		            if (which == DialogInterface.BUTTON_NEGATIVE)
		            {                 
		            	dtdialog.hide();
		            }
		         }
		       }); 
			dtdialog.setCancelable(false);
			dtdialog.setCanceledOnTouchOutside(false);
			dtdialog.show();
			break;
			
		case R.id.btn_sleeptime:
	
			Intent intent = new Intent(PersonalInfo.this,SelectSleepTime.class);
			startActivity(intent);
			
			break;*/
		
		case R.id.btn_male:
			StaticData.GENDER = "male";
			btn_male.setEnabled(false);
			btn_female.setEnabled(true);
			btn_cont.setVisibility(View.VISIBLE);
			Gender = "Male";
			
			break;
			
		case R.id.btn_female:
			StaticData.GENDER = "female";
			btn_female.setEnabled(false);
			btn_male.setEnabled(true);
			btn_cont.setVisibility(View.VISIBLE);
			Gender = "Female";
			
			break;
		
		case R.id.btn_cont:
		
			Intent i = new Intent(PersonalInfo_gender.this, PersonalInfo_SleepHours.class);
			startActivity(i);
			this.finish();
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			
			break;
			
/*		case R.id.btn_cont:
			
			if(Gender.equals(""))
			{
				Toast.makeText(getApplicationContext(), "Please select gender",Toast.LENGTH_LONG).show();
				
			}
			else if(Dob.equals(""))
			{
				Toast.makeText(getApplicationContext(), "Please select your birth date",Toast.LENGTH_LONG).show();
			}
			else if(StaticData.SLEEP_FROM.equals("") || StaticData.SLEEP_TO.equals(""))
			{
				Toast.makeText(getApplicationContext(), "Please select your sleep time and wakeup time",Toast.LENGTH_LONG).show();
			}
			else
			{
			
            	Date fDate = null;
            	Date tDate = null;
            	try 
            	{
            			fDate = df.parse(StaticData.SLEEP_FROM);
            			tDate = df.parse(StaticData.SLEEP_TO);
				} 
            	catch (ParseException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
            	System.out.println("=====================From Time ==================="+DateFormat.format("hh:mm aaa",fDate));
                System.out.println("=====================To Time   ==================="+DateFormat.format("hh:mm aaa",tDate));
            	
            	//DateFormat.format("hh:mm aaa",fDate);
                //DateFormat.format("hh:mm aaa",tDate);

            	
            	StaticData.WAKE_UP_TIME = getTimeDifference(fDate, tDate);
            	
            	System.out.println("==============Wakeup Time==================="+StaticData.WAKE_UP_TIME);
            	
            	
				builder = new AlertDialog.Builder(PersonalInfo.this);
				builder.setTitle("");
				builder.setMessage("Above information can not be changed further, Do you want to continue?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						Application.dbHelper.insertPersonalInfo(Gender, Dob, StaticData.SLEEP_FROM,StaticData.SLEEP_TO,StaticData.WAKE_UP_TIME);
						Application.prefrences.Store_personalinfo_state(true);
						Calendar c = Calendar.getInstance();
						
						c.add(Calendar.DATE,5);
					
						
						AlarmSetter.setAlarm(getApplicationContext(),c);
						Log.d("==NExt Date Time====",sdf.format(c.getTime()));
						System.out.println("==Next Date=="+sdf.format(c.getTime()));
						
						// Set Agree time in shared Pref
						Application.prefrences.Store_agree_date_time(sdf.format(c.getTime()));
						
						// Set Agree State in shared Pref
						Application.prefrences.Store_agree_state(true);
						
						Intent i = new Intent(getApplicationContext(), StageOne.class);
						startActivity(i);
						finish();
				
					}
				});
				builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				
				alert = builder.create();
				alert.show();
				
				
				
				
				
			}
			break;*/

		default:
			break;
		}
	}
	
/*	private class PickDate implements  DatePickerDialog.OnDateSetListener
	{

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) 
		{
			// TODO Auto-generated method stub
			view.updateDate(year, monthOfYear, dayOfMonth);
			monthOfYear++;
			
			Dob = dayOfMonth+"-"+monthOfYear+"-"+year;
			txt_dob.setText(Dob);
			System.out.println("======= DOB IS =============="+Dob);
		}
		
	}
	
	
	public String getTimeDifference(Date frmDate , Date toDate)
	{
		long difference = frmDate.getTime() - toDate.getTime(); 
    	System.out.println("===== Long Difference =============="+difference);
    	int days = (int) (difference / (1000*60*60*24));  
    	int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60)); 
    	int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
    	hours = (hours < 0 ? -hours : hours);
    	Log.i("======= Hours"," :: "+hours);
    	System.out.println("============ Difference ================"+hours+":"+min);                	
        
        return hours+":"+min;
	}
	*/


}
