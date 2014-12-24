package com.smoketherapy.alarmmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.smoketherapy.Application;
import com.smoketherapy.db.SmokeTracker;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class ScheduleCheckService extends Service{

	static final long ONE_MINUTE_IN_MILLIS=60000;
	public static final String NOTIFY_STAGE_ONE_START = "start_stage_one";
	public static final String NOTIFY_STAGE_ONE_COMP  = "complete_stage_one";
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(),"=== Check Schedule Service is Fired =====", Toast.LENGTH_LONG).show();
		System.out.println("===== Check Schedule Service is Fired =====");
		String currentDateString;
		Date currentDate = null;
		
		 Calendar c = Calendar.getInstance();
		    
		 currentDateString  = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
		    
		try 
		{
			currentDate = new SimpleDateFormat("dd-MM-yyyy").parse(currentDateString);
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(Application.prefrences.Get_Stage_Two_Start_State())
		{
			if(Application.dbHelper.getSmokeTrackerInfo() != null)
			{
				Date smDate = null;
				Date notification_date = null;
				int allow_cigge;
				int notyfied_cigge;
				int occurs;
				String message;
				
				
				SmokeTracker smTracker = Application.dbHelper.getSmokeTrackerInfo();
				
				String date = smTracker.getDate();
				allow_cigge = smTracker.getAllow();
				notyfied_cigge = smTracker.getNotyfiedCigge();
				message = "ALLOW_CIGGE_STAGE"+smTracker.getStage();
							
				try 
				{
					smDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				if(currentDate.equals(smDate))
				{
					occurs = allow_cigge - notyfied_cigge;
				}
				else
				{
					occurs = allow_cigge;
				}
				
				String todayDate = currentDateString+" "+Application.prefrences.Get_Wake_Up_Time();
				
				
				try {
					notification_date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(todayDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				float notificationInterval_in_Minute = ((float) (Application.prefrences
						.Get_Wake_Hours() / (float) allow_cigge)) * 60;
			
				
				
				System.out.println("============allow_Ciggi ========="+ allow_cigge);
				System.out.println("============ Notification Date ==================="+ notification_date);
				
				System.out.println("======= interval ========"+ notificationInterval_in_Minute);
				
				setNotifications(notification_date, notificationInterval_in_Minute, occurs,message,notyfied_cigge);
			}
			else
			{
				 String exeDate = Application.prefrences.Get_Date_To_Invoke_Notification_Service();
				
				 
				 Date alarmDate=null;
				
					
					try {
						alarmDate = new SimpleDateFormat("dd-MM-yyyy").parse(exeDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				    System.out.println("=======Previuos Alarm Date For invoke sevice  IN Scedule Check======="+alarmDate.toString());
				    
				    
				    if(currentDate.equals(alarmDate) || currentDate.after(alarmDate))
				    {
				    	setNotificationServiceAlarm(false);
				    }
				    else
				    {
				    	setNotificationServiceAlarm(true);
				    }
			 
			}
		}
		else if(!Application.prefrences.Get_stage_one_state())
		{
			String dateToComp = Application.prefrences.Get_Date_To_Comp_Stage1();
			String dateToStart = Application.prefrences.Get_Date_To_Start_Stage1();
			
			System.out.println("== String Date  frm pref To Start Stage One=="+dateToStart);
			System.out.println("== String Date  frm pref To Complete Stage One=="+dateToComp);
			Date alarmDateToCompStage1 = null;
			Date alarmDateToStartStage1 = null;
			
			try {
				alarmDateToCompStage1 = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(dateToComp);
				alarmDateToStartStage1 = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(dateToStart);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("== Alarm Date To Comp Stage One ========"+alarmDateToCompStage1.toString());
			System.out.println("== Alarm Date To Start Stage One ========"+alarmDateToStartStage1.toString());
			
			AlarmSetter.setAlarm(getApplicationContext(),alarmDateToStartStage1.getTime(), NOTIFY_STAGE_ONE_START);
			AlarmSetter.setAlarm(getApplicationContext(),alarmDateToCompStage1.getTime(), NOTIFY_STAGE_ONE_COMP);
		}
		
		return Service.START_NOT_STICKY;
	}
	
	public void setNotifications(Date notifyDate, float minuteInterval,int occurs, String message,int notyfied_cigge) 
	{
		 long mills = notifyDate.getTime();
		
		mills = mills + ((long)(minuteInterval * notyfied_cigge) * ONE_MINUTE_IN_MILLIS);
		for (int i = 0; i < occurs; i++) 
		{
			
			AlarmSetter.setAlarm(getApplicationContext(), mills, message);
			mills = (long) (mills + ((long)minuteInterval * ONE_MINUTE_IN_MILLIS));
			System.out.println("===========Next Notification At============"+new Date(mills));
			
		}

		Calendar c = Calendar.getInstance();
		
		c.add(Calendar.DATE, 1);
		
		String nextDate = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
		
		nextDate = nextDate+" "+Application.prefrences.Get_Wake_Up_Time();
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
	
		AlarmSetter.setAlarm(getApplicationContext(),alarmDate.getTime(), "INVOKE_NOTIFICATION_SERVICE");
		System.out.println("====Alarm Schedule to Start Notification Service at========"+alarmDate.toString());
		
	}
	
	public void setNotificationServiceAlarm(boolean flag)
	{
		Calendar c = Calendar.getInstance();
		
		if(flag)
		{
			c.add(Calendar.DATE, 1);
		}
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
	
	    System.out.println("=======NEw Alarm Date For invoke sevice IN Scedule Check======="+alarmDate.toString());
	    
	    String message = "INVOKE_NOTIFICATION_SERVICE";
		
		AlarmSetter.setAlarm(getApplicationContext(),alarmDate.getTime(), message);
		System.out.println("==== NEw Alarm Schedule to Start Notification Service at  IN Scedule Check ========"+alarmDate.toString());
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
