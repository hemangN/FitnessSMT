package com.smoketherapy.alarmmanager;

import com.smoketherapy.Application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmSetter {
	
    public static void setAlarm(Context context,long targetMillis,String extraMessage) 
    {
		//int RQS_ID = (int) System.currentTimeMillis();
    	//@ hn to assign unique request code
    	int RQS_ID = Application.prefrences.Get_REQ_CODE();
    	RQS_ID++;
    	Application.prefrences.Store_REQ_CODE(RQS_ID);
    	
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("Mode",extraMessage);        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RQS_ID, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetMillis,
                pendingIntent);   
    }
}
