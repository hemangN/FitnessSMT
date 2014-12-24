package com.smoketherapy.alarmmanager;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.sax.StartElementListener;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.smoketherapy.Application;
import com.smoketherapy.R;
import com.smoketherapy.db.ScheduleNotificationService;
import com.smoketherapy.stages.BeginStageTwo;
import com.smoketherapy.stages.StageFour;
import com.smoketherapy.stages.StageOne;
import com.smoketherapy.stages.StageThree;
import com.smoketherapy.stages.StageTwo;

public class AlarmReceiver extends BroadcastReceiver {

    Context context;
    SharedPreferences pref;
	
	Editor editor;
 

	@Override
	public void onReceive(Context k1, Intent intent) {
		// TODO Auto-generated method stub
		context=k1;
		
		pref = k1.getSharedPreferences("SmokeTherapy",Context.MODE_PRIVATE);
		editor = pref.edit();
		
		String Mode=intent.getStringExtra("Mode");
		Log.d("Mode",""+Mode);
		System.out.println("======Mode=========="+Mode);
		
		if(Mode.equalsIgnoreCase("start_stage_one"))
		{
			//Toast.makeText(context, "Broad Cast Received to start stage one ",Toast.LENGTH_LONG).show();
			Application.prefrences.Store_Cigarette_Reg_Flag(true);
			
			/*Intent i = new Intent(context, StageOne.class);
			
			PendingIntent pIntent = PendingIntent.getActivity(context, 0, i,0);
			
			NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			
			NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(context)
														  .setContentTitle("SmokeTherapy Notification")
														  .setContentText("You have now actually entered in \"Stage 1\".Now you can start registering your each smoke.")
														  .setAutoCancel(true)
					                                      .setSmallIcon(R.drawable.smoke_icon)
														  .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
														  .setContentIntent(pIntent);
			Notification notification = notificationbuilder.build();
			
			notificationManager.notify(0, notification);*/
		}
		
		if(Mode.equalsIgnoreCase("complete_stage_one"))
		{
			Toast.makeText(context, "You can proceed to \"Stage 2\"",Toast.LENGTH_LONG).show();
		    
			editor.putBoolean("IS_STAGE1_COMP",true);
			editor.commit();
			
			
			if(Application.prefrences.Get_App_State())
			{
			
				Intent i = new Intent(context, BeginStageTwo.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
				/*Intent i = new Intent();
		        i.setClassName("com.smoketherapy.stages", "com.smoketherapy.stages.StageTwo");
		        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        context.startActivity(i);*/
			}
			else
			{
				
			
			Intent i = new Intent(context, BeginStageTwo.class);
			
			PendingIntent pIntent = PendingIntent.getActivity(context, 0, i,0);
			
			NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			
			NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(context)
										.setContentTitle("Smoke Therapy Notification")
										.setContentText("You can proceed to Stage 2")
										.setAutoCancel(true)
										.setSmallIcon(R.drawable.smoke_icon)									
										.setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
										.setContentIntent(pIntent);
									
			
			Notification notification = notificationbuilder.build();
			
			notificationManager.notify(0, notification);
		}	
		 /*AudioManager audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		 audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		 Toast.makeText(k1, "Silent Mode Activated", Toast.LENGTH_LONG).show();*/
		}
		if(Mode.equalsIgnoreCase("ALLOW_CIGGE_STAGE2"))
		{
			//=================== Update Notyfied Cigge In DB using update service ================================================//
			Intent serviIntent = new Intent(context, SmokeTrackerUpdateService.class);
			context.startService(serviIntent);
			
			Toast.makeText(context, "cigarette allowance in \"Stage 2\"",Toast.LENGTH_LONG).show();
			int allow_count = Application.prefrences.Get_Allow_Cigge_Count();
			System.out.println("======Allow Cigge Count brfore======"+allow_count);
			allow_count++;
			System.out.println("======Allow Cigge Count after increment======"+allow_count);
			
			Application.prefrences.Store_Allow_Cigge_Count(allow_count);
			
			
			Intent i = new Intent(context, StageTwo.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if(Application.prefrences.Get_App_State())
			{
				context.startActivity(i);
			}
			PendingIntent pIntent = PendingIntent.getActivity(context, 0, i,0);
			
			NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			
			NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(context)
										.setContentTitle("Smoke Therapy Notification")
										.setContentText("Cigarette Allowence in Stage 2")
										.setAutoCancel(true)
										.setSmallIcon(R.drawable.smoke_icon)									
										.setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
										.setContentIntent(pIntent);
									
			
			Notification notification = notificationbuilder.build();
			
			notificationManager.notify(0, notification);
		}
		if(Mode.equalsIgnoreCase("ALLOW_CIGGE_STAGE3"))
		{
			//=================== Update Notyfied Cigge In DB using update service ================================================//
			Intent serviIntent = new Intent(context, SmokeTrackerUpdateService.class);
			context.startService(serviIntent);
			
			System.out.println("======Stage Two State Before====in Allow Ciige 3===="+Application.prefrences.Get_stage_two_state());
			/*if(Application.prefrences.Get_stage_two_state() == false)
			{
				Application.prefrences.Store_stage_two_state(true);
			}*/
			System.out.println("======Stage Two State After update====in Allow Ciige 3===="+Application.prefrences.Get_stage_two_state());
			
			Toast.makeText(context, "cigarette allowance in \"Stage 3\"",Toast.LENGTH_LONG).show();
			
			int allow_count = Application.prefrences.Get_Allow_Cigge_Count();
			System.out.println("======Allow Cigge Count brfore======"+allow_count);
			allow_count++;
			System.out.println("======Allow Cigge Count after increment======"+allow_count);
			
			Application.prefrences.Store_Allow_Cigge_Count(allow_count);
			
			Intent i = new Intent(context, StageThree.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if(Application.prefrences.Get_App_State())
			{
				context.startActivity(i);
			}
			
			PendingIntent pIntent = PendingIntent.getActivity(context, 0, i,0);
			
			NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			
			NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(context)
										.setContentTitle("Smoke Therapy Notification")
										.setContentText("Cigarette Allowence in Stage 3")
										.setAutoCancel(true)
										.setSmallIcon(R.drawable.smoke_icon)									
										.setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
										.setContentIntent(pIntent);
									
			
			Notification notification = notificationbuilder.build();
			
			notificationManager.notify(0, notification);
		}
		if(Mode.equalsIgnoreCase("ALLOW_CIGGE_STAGE4"))
		{
			//=================== Update Notyfied Cigge In DB using update service ================================================//
			Intent serviIntent = new Intent(context, SmokeTrackerUpdateService.class);
			context.startService(serviIntent);
			
			System.out.println("======Stage Tthree State Before====in Allow Ciige 3===="+Application.prefrences.Get_stage_three_state());
			/*if(Application.prefrences.Get_stage_three_state() == false)
			{
				Application.prefrences.Store_stage_three_state(true);
			}*/
			System.out.println("======Stage Three State After update====in Allow Ciige 3===="+Application.prefrences.Get_stage_three_state());
			
			Toast.makeText(context, "cigarette allowance in \"Stage 4\"",Toast.LENGTH_LONG).show();
			
			int allow_count = Application.prefrences.Get_Allow_Cigge_Count();
			System.out.println("======Allow Cigge Count brfore======"+allow_count);
			allow_count++;
			System.out.println("======Allow Cigge Count after increment======"+allow_count);
			
			Application.prefrences.Store_Allow_Cigge_Count(allow_count);
			
			Intent i = new Intent(context, StageFour.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if(Application.prefrences.Get_App_State())
			{
				context.startActivity(i);
			}
			
			PendingIntent pIntent = PendingIntent.getActivity(context, 0, i,0);
			
			NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			
			NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(context)
										.setContentTitle("Smoke Therapy Notification")
										.setContentText("Cigarette Allowence in Stage 4")
										.setAutoCancel(true)
										.setSmallIcon(R.drawable.smoke_icon)									
										.setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
										.setContentIntent(pIntent);
									
			
			Notification notification = notificationbuilder.build();
			
			notificationManager.notify(0, notification);
		}
		if(Mode.equalsIgnoreCase("INVOKE_NOTIFICATION_SERVICE"))
		{
			
			Application.prefrences.Store_Allow_Cigge_Count(0);
			System.out.println("====== Notification Service Started at ============"+Calendar.getInstance().getTime());
			System.out.println("====== Allow Cigge Count ========="+Application.prefrences.Get_Allow_Cigge_Count());
			//============ Start Service To Schedule Notifications ====================//
			Intent serviceIntent = new Intent(context, ScheduleNotificationService.class);
			context.startService(serviceIntent);
		}
		/*else if(Mode.equalsIgnoreCase("Normal"))
		{
			 AudioManager audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
			 audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			 Toast.makeText(k1, "Normal Mode Activated", Toast.LENGTH_LONG).show();
		}*/
		
		
	}

	
	
}
