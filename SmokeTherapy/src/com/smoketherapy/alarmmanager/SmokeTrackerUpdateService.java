package com.smoketherapy.alarmmanager;

import com.smoketherapy.Application;
import com.smoketherapy.db.SmokeTracker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SmokeTrackerUpdateService extends Service{

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		//==================Update Smoke Traker With Notyfied Cigge ===================//
		SmokeTracker smokeTracker = Application.dbHelper.getSmokeTrackerInfo();
		Application.dbHelper.updateNotifiedCiggeInSmokeTracker(smokeTracker);
		
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
