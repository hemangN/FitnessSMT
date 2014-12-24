package com.smoketherapy.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.smoketherapy.Application;
import com.smoketherapy.alarmmanager.AlarmSetter;
import com.smoketherapy.stages.StageFour;

public class ScheduleNotificationService extends Service {

	static final long ONE_MINUTE_IN_MILLIS=60000;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		//==========Vars To compute Stats ============================================//
		float percentage;
		int avg_cigge_at_begining = Application.prefrences.Get_Avg_No_Of_Cigge();
		int day_count;
		
		
		//=========== Set Flag For Extra Cigge Taken to False to reset Every Day ====//
		Application.prefrences.Store_Extra_Cigge_Taken_Flag(false);
		
		// ================Set Flag To Stop Therapy [true for stop]
		// ==================//
		boolean stopFlag = false;

		// ================Get Application Prefrences
		// ============================//
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();

		// ===============To Reset Allow Cigge Count =====================//

		editor.putInt("ALLOW_CIGGE_COUNT", 0);
		editor.commit();

		
		int smStage = 2;
		String message = "ALLOW_CIGGE_STAGE"+smStage;
		String todayDate = "";
		int avg_cigge = Application.prefrences.Get_Avg_No_Of_Cigge();
		int day_req = Application.prefrences.Get_Total_Therapy_Time();
		int extra_cigge = 0;
		int day_in_stage = Application.prefrences.Get_Day_In_STAGE2();

		Calendar c = Calendar.getInstance();

		todayDate = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());

		System.out.println("===Today Date===" + todayDate);

		if (Application.dbHelper.getSmokeTrackerInfo() != null) 
		{

			SmokeTracker smTracker = Application.dbHelper.getSmokeTrackerInfo();

			smStage = smTracker.getStage();
			day_req = smTracker.getReq_Day();
			avg_cigge = smTracker.getAllow();
			extra_cigge = smTracker.getExtra();
			day_in_stage = smTracker.getDaysInStage();

			if (extra_cigge == 1) 
			{
				message = "ALLOW_CIGGE_STAGE"+smStage;
				extra_cigge = 0;
			}
			else
			{	
				//================= Days Elapsed Count ===================//
				int days_elapsed = Application.prefrences.Get_Days_Elapsed();
				days_elapsed++;
				Application.prefrences.Store_Days_Elapsed(days_elapsed);
				
				if(extra_cigge == 0 && day_in_stage == 0)
				{
				
					stopFlag = true;
					Application.prefrences.Store_Complete_Stat_Flag(true);
					//@hn now for reflact End
					Intent i = new Intent(getApplicationContext(), StageFour.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					if(Application.prefrences.Get_App_State())
					{
						getApplicationContext().startActivity(i);
					}
					
				}
				else if (extra_cigge == 0 && day_in_stage == 1) 
				{
					if (smStage == 4) 
					{
						//out
						//stopFlag = true;
						
						day_req--;
						avg_cigge--;
						day_in_stage--;
						message = "ALLOW_CIGGE_STAGE"+smStage;
						
						Application.prefrences.Store_End_Therapy_Flag(true);
						
						//========== Calculate Statistics for stage 4 completion ==============//
						percentage = ((float)((avg_cigge_at_begining - 1)*100))/(float)avg_cigge_at_begining;
						percentage = (Math.round(percentage*100))/100;
						Application.prefrences.Store_Nicotine_After_Stage4(percentage);
						System.out.println("=== Nicotine Persentage at stage 4 comp=="+percentage);
						
						//=========Getting Day Count for stage 4 ======================//
						day_count = Application.dbHelper.getDayCountToCompTherapy();
						Application.prefrences.Store_Days_Taken_Stage4(day_count);
					} else {
						smStage++;
						day_req--;
						
						//@hn_according_sir_logic
						avg_cigge--;
						Application.prefrences.Store_Days_Elapsed(1);
	
						if (smStage == 3) 
						{
							//==========Save pref to register completion of stage 2 ========================//
							Application.prefrences.Store_stage_two_state(true);
							//========== ends ================
							
							day_in_stage = Application.prefrences
									.Get_Day_In_STAGE3();
							message = "ALLOW_CIGGE_STAGE"+smStage;
							Application.prefrences.Store_StageTwo_Stat_Flag(true);
							
							//======== Calculate Statistics for stage 2  completion =========//
							int avg_cigge_at_end_stagetwo = avg_cigge;
							percentage = ((float)((avg_cigge_at_begining - avg_cigge_at_end_stagetwo)*100))/(float)avg_cigge_at_begining;
							percentage = (Math.round(percentage*100))/100;
							Application.prefrences.Store_Nicotine_After_Stage2(percentage);
							System.out.println("=== Nicotine Persentage at stage 2 comp=="+percentage);
							
							//=========Getting Day Count for stage 2 ======================//
							day_count = Application.dbHelper.getDayCountForStage(2);
							Application.prefrences.Store_Days_Taken_Stage2(day_count);
							
						}
	
						if (smStage == 4) 
						{
							//========= Save pref to store completion of stage 3 =======================//
							Application.prefrences.Store_stage_three_state(true);
							//=========== e   n   d===================================
							
							day_in_stage = Application.prefrences
									.Get_Day_In_STAGE4();
							message = "ALLOW_CIGGE_STAGE"+smStage;
							int count = Application.prefrences.Get_Stage4_Count();
							count++;
							Application.prefrences.Store_Stage4_Count(count);
							Application.prefrences.Store_StageThree_Stat_Flag(true);
							
							//========== Calculate Statistics for stage 3 completion ==============//
							int avg_cigge_at_end_stagethree = avg_cigge;
							percentage = ((float)((avg_cigge_at_begining - avg_cigge_at_end_stagethree)*100))/(float)avg_cigge_at_begining;
							percentage = (Math.round(percentage*100))/100;
							Application.prefrences.Store_Nicotine_After_Stage3(percentage);
							System.out.println("=== Nicotine Persentage at stage 3 comp=="+percentage);
							
							//=========Getting Day Count for stage 3 ======================//
							day_count = Application.dbHelper.getDayCountForStage(3);
							Application.prefrences.Store_Days_Taken_Stage3(day_count);
						}
					}
				} 
				else if ((extra_cigge == 0)) 
				{
					if (smStage == 2) {
						message = "ALLOW_CIGGE_STAGE"+smStage;
						day_req--;
						if(days_elapsed == 3)
						{
							Application.prefrences.Store_Days_Elapsed(1);
							avg_cigge--;
						}
						day_in_stage--;
					}
					if (smStage == 3) {
						message = "ALLOW_CIGGE_STAGE"+smStage;
						day_req--;
						if(days_elapsed == 3)
						{
							Application.prefrences.Store_Days_Elapsed(1);
							avg_cigge--;
						}
						day_in_stage--;
					}
					if (smStage == 4) {
						message = "ALLOW_CIGGE_STAGE"+smStage;
						
						if(days_elapsed == 4)
						{
							Application.prefrences.Store_Days_Elapsed(1);
							avg_cigge--;
							day_req--;
							day_in_stage--;
						}
						else
						{
							day_req--;
							day_in_stage--;
						}
					/*	if (Application.prefrences.Get_Stage4_Count() == 2) {
							Application.prefrences.Store_Stage4_Count(0);
							day_req--;
							avg_cigge--;
							day_in_stage--;
						} else {
							int count = Application.prefrences.Get_Stage4_Count();
							count++;
							Application.prefrences.Store_Stage4_Count(count);
							day_req--;
							day_in_stage--;
						}*/
					}
	
				}
			
			}

		}

		System.out.println("===Stage==" + smStage);
		System.out.println("===todayDate==" + todayDate);
		System.out.println("===day_req====" + day_req);
		System.out.println("===Avg_cigge / Allow Cigge : ======" + avg_cigge);
		System.out.println("===extra_cigge==" + extra_cigge);
		System.out.println("===day_in_stage==" + day_in_stage);

		System.out.println("===message==" + message);
		if (!stopFlag) 
		{

			SmokeTracker mSmokeTracker = new SmokeTracker(smStage, todayDate,
					day_req, avg_cigge, extra_cigge, day_in_stage,0);

			long rawId = Application.dbHelper
					.insertSmokeTrackerInfo(mSmokeTracker);
			System.out.println("=== inserted raw Id in Smoke Tracker ==="
					+ rawId);

			todayDate = todayDate + " "
					+ Application.prefrences.Get_Wake_Up_Time();

			Date notificationDate = null;
			try {
				notificationDate = new SimpleDateFormat("dd-MM-yyyy HH:mm")
						.parse(todayDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int allow_Cigge = avg_cigge;

			float notificationInterval_in_Minute = ((float) (Application.prefrences
					.Get_Wake_Hours() / (float) allow_Cigge)) * 60;
			// ================ Dummy Interval Counted by dividing actual by 30
			// =============================
			float dummy_interval = notificationInterval_in_Minute / 30f;

			System.out.println("============allow_Ciggi - Occures ========="
					+ allow_Cigge);
			System.out
					.println("============ Notification Date ==================="
							+ notificationDate);
			System.out.println("=======Dummy Interval =================="
					+ dummy_interval);
			System.out.println("======= interval ========"
					+ notificationInterval_in_Minute);

			setNotifications(notificationDate, notificationInterval_in_Minute, allow_Cigge,
					message);
			setNotificationServiceAlarm();
		}
		return Service.START_NOT_STICKY;
	};

	public void setNotifications(Date notifyDate, float minuteInterval,
			int occurs, String message) {
		 long mills = notifyDate.getTime();
		
		
		for (int i = 0; i < occurs; i++) 
		{
			long preMills = mills;
			AlarmSetter.setAlarm(getApplicationContext(), mills, message);
			mills = (long) (mills + ((long)minuteInterval * ONE_MINUTE_IN_MILLIS));
			System.out.println("**Difference In mills**"+(mills - preMills));
			System.out.println("===========Next Notification At============"+new Date(mills));
		}

		
	}

	public void setNotificationServiceAlarm() {
		
		Calendar c = Calendar.getInstance();
		
		c.add(Calendar.DATE, 1);
		
		String nextDate = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
		
		//nextDate = nextDate+" "+"00:00";
		//for Testing 
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
	    
	    String message = "INVOKE_NOTIFICATION_SERVICE";
		
		AlarmSetter.setAlarm(getApplicationContext(),alarmDate.getTime(), message);
		System.out.println("====Alarm Schedule to Start Notification Service at========"+alarmDate.toString());
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
