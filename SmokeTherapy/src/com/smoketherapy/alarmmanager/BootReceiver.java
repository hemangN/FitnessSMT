package com.smoketherapy.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
		{
			//Intent serviceIntent = new Intent("com.smoketherapy.alarmmanager.ScheduleCheckService");
			Intent serviceIntent = new Intent(context, ScheduleCheckService.class);
			context.startService(serviceIntent);
			
		}
		
	}

}
