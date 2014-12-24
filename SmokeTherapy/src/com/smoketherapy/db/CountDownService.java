package com.smoketherapy.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import com.smoketherapy.Application;

public class CountDownService extends Service{

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String formatStr = "yyyy-MM-dd HH:mm:ss";

		 SimpleDateFormat sdf1 = new SimpleDateFormat(formatStr);
		
		Date date = null;
		try {
			date = sdf1.parse("2014-06-18 12:20:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // here put your time zone
		long millis = date.getTime();

		long currentTimeInMili = new Date().getTime();
		MyCount counter = new MyCount(millis - currentTimeInMili, 1 * 1000);
		  counter.start();
		return Service.START_NOT_STICKY;
	};
	
	
	class MyCount extends CountDownTimer {
	    public MyCount(long millisInFuture, long countDownInterval) {
	        super(millisInFuture, countDownInterval);
	    }// MyCount

	    public void onPause() {
	        onPause();
	    }// finish

	    public void onTick(long millisUntilFinished) {
	    	
	    }// on tick

	    @Override
	    public void onFinish() {
	       
	    	Application.prefrences.Store_Count_Down_State(true);

	    }// finish
	}
	
	public String formatTime(long millis) {

	    String output = "00:00";
	    try {
	        long seconds = millis / 1000;
	        long minutes = seconds / 60;
	        long hours = seconds / 3600;
	        long days = seconds / (3600 * 24);

	        seconds = seconds % 60;
	        minutes = minutes % 60;
	        hours = hours % 24;
	        days = days % 30;

	        String sec = String.valueOf(seconds);
	        String min = String.valueOf(minutes);
	        String hur = String.valueOf(hours);
	        String day = String.valueOf(days);

	        if (seconds < 10)
	            sec = "0" + seconds;
	        if (minutes < 10)
	            min = "0" + minutes;
	        if (hours < 10)
	            hur = "0" + hours;
	        if (days < 10)
	            day = "0" + days;

	        /*output = day + "D " + hur + "H " + min + "M " + sec + "S";*/
	        output =  hur+":"+ min + ":" + sec;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return output;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
