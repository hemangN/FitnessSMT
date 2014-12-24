package com.smoketherapy.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeZoneChangedReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("Time Zone Changed");
		Intent serviceIntent = new Intent(context, ReScheduleService.class);
		context.startService(serviceIntent);
	}

}
