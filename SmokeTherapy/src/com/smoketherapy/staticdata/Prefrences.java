package com.smoketherapy.staticdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Prefrences {

	SharedPreferences pref;
	
	Editor editor;
	
	Context context;
	
	int PRIVATE_MODE = 0;

	private static final String PREF_NAME = "SmokeTherapy";
	
   //=====================================@hn to achieve reboot recovery 01-07-2014==============================================================================//
	private static final String REQ_CODE = "REQ_CODE";
	private static final String CIGARETTE_REGISTER_FLAG = "CIGARETTE_REGISTER_FLAG";
	private static final String IS_NOTIFICATION_SERVICE_EXECUTED_FIRST_TIME = "IS_NOTIFICATION_SERVICE_EXECUTED_FIRST_TIME";
	private static final String DATE_TO_INVOKE_NOTIFICATIONSERVICE = "DATE_TO_INVOKE_NOTIFICATIONSERVICE";
	
	private static final String DATE_TO_COMP_STAGE1	  = "DATE_TO_COMP_STAGE1";
	private static final String DATE_TO_START_STAGE1	  = "DATE_TO_START_STAGE1";
	private static final String DAY_ELAPSED           = "DAY_ELAPSED";
	private static final String DAY_TAKEN_STAGE2      = "DAY_TAKEN_STAGE2";
	private static final String DAY_TAKEN_STAGE3      = "DAY_TAKEN_STAGE3";
	private static final String DAY_TAKEN_STAGE4      = "DAY_TAKEN_STAGE4";
	
	private static final String NICOTINE_AFTER_STAGE2 = "NICOTINE_AFTER_STAGE2";
	private static final String NICOTINE_AFTER_STAGE3 = "NICOTINE_AFTER_STAGE3";
	private static final String NICOTINE_AFTER_STAGE4 = "NICOTINE_AFTER_STAGE4";
	private static final String WELCOME_STATE    = "WELCOME_STATE";
	private static final String STAGE_TWO_STAT_FLAG    = "STAGE_TWO_STAT_FLAG";
	private static final String STAGE_THREE_STAT_FLAG  = "STAGE_THREE_STAT_FLAG";
	private static final String COMPLETE_STAT_FLAG   = "STAGE_FOUR_STAT_FLAG";
	private static final String EXTRA_CIGGE_TAKEN_FLAG = "EXTRA_CIGGE_TAKEN_FLAG";
	private static final String END_THERAPY		  = "END_THERAPY";
	private static final String ALLOW_CIGGE_COUNT = "ALLOW_CIGGE_COUNT";
	private static final String COUNT_DOWN_DATE   = "COUNT_DOWN_DATE";
	private static final String MILLIS_FOR_TIMER  = "MILLIS_FOR_TIMER";
	private static final String STAGE4_COUNT      = "STAGE4_COUNT";
	private static final String DAY_IN_STAGE2     = "DAY_IN_STAGE2";
	private static final String DAY_IN_STAGE3     = "DAY_IN_STAGE3";
	private static final String DAY_IN_STAGE4	  = "DAY_IN_STAGE4";
	private static final String TOTAL_THERAPY_TIME= "TOTAL_THERAPY_TIME";
	private static final String COUNT_DOWN_STAGE2 = "COUNT_DOWN_STAGE2";
	private static final String WAKE_HOURS	 = "WAKE_HOURS";
	private static final String WAKE_UP_TIME = "WAKE_UP_TIME";
	private static final String SLEEP_HOURS  = "SLEEP_HOURS";
	private static final String GENDER		 = "GENDER";
	private static final String APP_STATE    = "APP_STATE";
	private static final String AVG_NO_OF_CIGGE    = "AVG_NO_OF_CIGGE";
	private static final String TOTAL_STAGE1_CIGGE = "TOTAL_STAGE1_CIGGE";
	private static final String IS_INFO_TAKEN = "IS_INFO_TAKEN";
	private static final String IS_AGREE= "IS_AGREE";
	private static final String IS_STAGE2_START = "IS_STAGE2_START";
	private static final String IS_STAGE1_START = "IS_STAGE1_START";
	private static final String IS_STAGE1_COMP  = "IS_STAGE1_COMP";
	private static final String IS_STAGE2_COMP  = "IS_STAGE2_COMP";
	private static final String IS_STAGE3_COMP  = "IS_STAGE3_COMP";
	private static final String IS_STAGE4_COMP  = "IS_STAGE4_COMP";
	private static final String AGREE_TIME	    = "AGREE_TIME";
	private static final String IS_STAGE1_FST_EX = "IS_STAGE1_FST_EX";
	private static final String APP_BACK_GROUND = "APP_BG";
	
	public Prefrences(Context contx) {
		// TODO Auto-generated constructor stub
		context = contx;
		pref = contx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	public void ClearPrfrences()
	{
		editor.clear();
		editor.commit();
	}
	
	//==================================== Store Requset code ==========================================================//
	public void Store_REQ_CODE(int code)
	{
		editor.putInt(REQ_CODE, code);
		editor.commit();
	}
	
	public int Get_REQ_CODE()
	{
		return pref.getInt(REQ_CODE, 0);
	}
	
	//===================================== Store REgister Cigarette Flag  ==============================================//
		public void Store_Cigarette_Reg_Flag(boolean flag)
		{
			editor.putBoolean(CIGARETTE_REGISTER_FLAG,flag);
			editor.commit();
		}
		
		public boolean Get_Cigarette_Reg_Flag()
		{
			return pref.getBoolean(CIGARETTE_REGISTER_FLAG,false);
		}
		
	
	
	//===================================== Store Date To complete stage 1 ==============================================//
	public void Store_Date_To_Comp_Stage1(String date)
	{
		editor.putString(DATE_TO_COMP_STAGE1,date);
		editor.commit();
	}
	
	public String Get_Date_To_Comp_Stage1()
	{
		return pref.getString(DATE_TO_COMP_STAGE1, "");
	}
	
	//===================================== Store Date To Start stage 1 ==============================================//
		public void Store_Date_To_Start_Stage1(String date)
		{
			editor.putString(DATE_TO_START_STAGE1,date);
			editor.commit();
		}
		
		public String Get_Date_To_Start_Stage1()
		{
			return pref.getString(DATE_TO_START_STAGE1, "");
		}
	 //=====================================@hn to achieve reboot recovery 01-07-2014==============================================================================//
	//============ Store Date to invoke notification service first time======================================//
	public void Store_Date_To_Invoke_Notification_Service(String date)
	{
		editor.putString(DATE_TO_INVOKE_NOTIFICATIONSERVICE, date);
		editor.commit();
	}
	
	public String Get_Date_To_Invoke_Notification_Service()
	{
		return pref.getString(DATE_TO_INVOKE_NOTIFICATIONSERVICE, "");
	}
	
	
	//=============== e                n                          d           =====================================
	
	
	//=============Day elapsed ====================//
		public void Store_Days_Elapsed(int days)
		{
			editor.putInt(DAY_ELAPSED,days);
			editor.commit();
		}
		public int Get_Days_Elapsed()
		{
			return pref.getInt(DAY_ELAPSED, 1);
		}
	
	//=============== Store Actual Days taken in individual Stages ==================================//
	public void Store_Days_Taken_Stage2(int days)
	{
		editor.putInt(DAY_TAKEN_STAGE2,days);
		editor.commit();
	}
	public int Get_Days_Taken_Stage2()
	{
		return pref.getInt(DAY_TAKEN_STAGE2, 0);
	}
	
	public void Store_Days_Taken_Stage3(int days)
	{
		editor.putInt(DAY_TAKEN_STAGE3,days);
		editor.commit();
	}
	public int Get_Days_Taken_Stage3()
	{
		return pref.getInt(DAY_TAKEN_STAGE3, 0);
	}
	
	public void Store_Days_Taken_Stage4(int days)
	{
		editor.putInt(DAY_TAKEN_STAGE4,days);
		editor.commit();
	}
	public int Get_Days_Taken_Stage4()
	{
		return pref.getInt(DAY_TAKEN_STAGE4, 0);
	}
	
	//===============Store Nicotine Level At the end of each stage ======================//
	public void Store_Nicotine_After_Stage2(float val)
	{
		editor.putFloat(NICOTINE_AFTER_STAGE2, val);
		editor.commit();
	}
	public float Get_Nicotine_After_Stage2()
	{
		return pref.getFloat(NICOTINE_AFTER_STAGE2,0);
	}
	
	public void Store_Nicotine_After_Stage3(float val)
	{
		editor.putFloat(NICOTINE_AFTER_STAGE3, val);
		editor.commit();
	}
	public float Get_Nicotine_After_Stage3()
	{
		return pref.getFloat(NICOTINE_AFTER_STAGE3,0);
	}
	
	public void Store_Nicotine_After_Stage4(float val)
	{
		editor.putFloat(NICOTINE_AFTER_STAGE4, val);
		editor.commit();
	}
	public float Get_Nicotine_After_Stage4()
	{
		return pref.getFloat(NICOTINE_AFTER_STAGE4,0);
	}
	//================Store Stage Two State Flag==========================//
	public void Store_StageTwo_Stat_Flag(boolean flag)
	{
		editor.putBoolean(STAGE_TWO_STAT_FLAG,flag);
		editor.commit();
	}
	public boolean Get_StageTwo_Stat_Flag()
	{
		return pref.getBoolean(STAGE_TWO_STAT_FLAG,false);
	}
	
	//==============Store Stage Three Stat Flag=========================//
	public void Store_StageThree_Stat_Flag(boolean flag)
	{
		editor.putBoolean(STAGE_THREE_STAT_FLAG, flag);
		editor.commit();
	}
	public boolean Get_StageThree_Stat_Flag()
	{
		return pref.getBoolean(STAGE_THREE_STAT_FLAG, false);
	}
	//===============Store Complete Stat Flag ==========================//
	public void Store_Complete_Stat_Flag(boolean flag)
	{
		editor.putBoolean(COMPLETE_STAT_FLAG,flag);
		editor.commit();
	}
	public boolean Get_Complete_Stat_Flag()
	{
		return pref.getBoolean(COMPLETE_STAT_FLAG, false);
	}
	//================ Store Extra Cigge Taken Flag =====================//
	public void Store_Extra_Cigge_Taken_Flag(boolean flag)
	{
		editor.putBoolean(EXTRA_CIGGE_TAKEN_FLAG,flag);
		editor.commit();
	}
	public boolean Get_Extra_Cigge_Taken_Flag()
	{
		return pref.getBoolean(EXTRA_CIGGE_TAKEN_FLAG, false);
	}
	//================= Store END THERAPY FLAG ==========================//
	public void Store_End_Therapy_Flag(boolean flag)
	{
		editor.putBoolean(END_THERAPY, flag);
		editor.commit();
	}
	public boolean Get_End_Therapy_Flag()
	{
		return pref.getBoolean(END_THERAPY,false);
	}
	//=================Store Allowed Cigge Count =======================//
	public void Store_Allow_Cigge_Count(int count)
	{
		editor.putInt(ALLOW_CIGGE_COUNT,count);
		editor.commit();
	}
	public int Get_Allow_Cigge_Count()
	{
		return pref.getInt(ALLOW_CIGGE_COUNT,0);
	}
	
	//================== Store count down date ======================//
	
	public void Store_Count_Down_Date(String date)
	{
		editor.putString(COUNT_DOWN_DATE, date);
		editor.commit();
	}
	public String Get_Count_Down_Date()
	{
		return pref.getString(COUNT_DOWN_DATE,"");
	}
	//==================  Store Static Millis ============================================//
	public void Store_Static_Millis(long millis)
	{
		editor.putLong(MILLIS_FOR_TIMER,millis);
		editor.commit();
	}
	public long Get_Static_Millis()
	{
		return pref.getLong(MILLIS_FOR_TIMER,0);
	}
	
	//=================== Store Stage4 Count ===========================================//
	public void Store_Stage4_Count(int count)
	{
		editor.putInt(STAGE4_COUNT, count);
		editor.commit();
	}
	public int Get_Stage4_Count()
	{
		return pref.getInt(STAGE4_COUNT,0);
	}
	//==================== Store Therapy Time for user =================================//
	public void Store_Day_Req_In_STAGE2(int days)
	{
		editor.putInt(DAY_IN_STAGE2,days);
		editor.commit();
	}
	public int Get_Day_In_STAGE2()
	{
		return pref.getInt(DAY_IN_STAGE2,0);
	}
	public void Store_Day_Req_In_STAGE3(int days)
	{
		editor.putInt(DAY_IN_STAGE3,days);
		editor.commit();
	}
	public int Get_Day_In_STAGE3()
	{
		return pref.getInt(DAY_IN_STAGE3,0);
	}
	
	public void Store_Day_Req_In_STAGE4(int days)
	{
		editor.putInt(DAY_IN_STAGE4,days);
		editor.commit();
	}
	public int Get_Day_In_STAGE4()
	{
		return pref.getInt(DAY_IN_STAGE4,0);
	}
	
	public void Store_Total_Therapy_Time(int days)
	{
		editor.putInt(TOTAL_THERAPY_TIME,days);
		editor.commit();
	}
	public int Get_Total_Therapy_Time()
	{
		return pref.getInt(TOTAL_THERAPY_TIME,0);
	}
	//==================== Store Count Down State For Stage two =======================//
	public void Store_Count_Down_State(boolean countDownFlag)
	{
		editor.putBoolean(COUNT_DOWN_STAGE2,countDownFlag);
		editor.commit();
	}
	
	public boolean Get_Count_Down_State()
	{
		return pref.getBoolean(COUNT_DOWN_STAGE2,false);
	}
	
	//==================== Store Gender ==============================================//
	public void Store_Gender(String gen)
	{
		editor.putString(GENDER,gen);
		editor.commit();
	}
	public String Get_Gender()
	{
		return pref.getString(GENDER,"");
	}
	//=================== Store Wake Up Time ===================================//
	public void Store_Wake_Up_Time(String wakeTime)
	{
		editor.putString(WAKE_UP_TIME,wakeTime);
		editor.commit();
	}
	public String Get_Wake_Up_Time()
	{
		return pref.getString(WAKE_UP_TIME,"");
	}
	//==================Store Sleep Hours ================================//
	public void Store_Sleep_Hours(String sleepHours)
	{
		editor.putString(SLEEP_HOURS,sleepHours);
		editor.commit();
	}
	public String Get_Sleep_Hours()
	{
		return pref.getString(SLEEP_HOURS, "");
	}
	//==================== Store Wake hours ==========================================//
	public void Store_Wake_Hours(int wake_hours)
	{
		editor.putInt(WAKE_HOURS,wake_hours);
		editor.commit();
	}
	
	public int Get_Wake_Hours()
	{
		return pref.getInt(WAKE_HOURS,0);
	}
	//===================== Store_App_State ==========================================//
	public void Store_App_State(boolean state)
	{
		editor.putBoolean(APP_STATE, state);
		editor.commit();
	}
	public boolean Get_App_State()
	{
		return pref.getBoolean(APP_STATE,false);
	}
	//===================== Store Avg No of cigge =======================================//
	public void Store_Avg_No_Of_Cigge(int avg_cigge)
	{
		editor.putInt(AVG_NO_OF_CIGGE,avg_cigge);
		editor.commit();
	}
	public int Get_Avg_No_Of_Cigge()
	{
		return pref.getInt(AVG_NO_OF_CIGGE,4);
	}
	
	//===================== To Store Total NO Of Cigge IN Stage One =============================//
	public void Store_Stage_One_Total_Cigge(int no_of_cigge)
	{
		editor.putInt(TOTAL_STAGE1_CIGGE,no_of_cigge);
		editor.commit();
	}
	
	public int Get_Stage_One_Cigge()
	{
		return pref.getInt(TOTAL_STAGE1_CIGGE,0);
	}
	//=========================Ends ========================================================//
	

	//=======  Store stage one start state ======================
	public void Store_Stage_One_Start_State(boolean isStarted)
	{
		editor.putBoolean(IS_STAGE1_START,isStarted);
		editor.commit();
	}
	
	public boolean Get_Stage_One_Start_State()
	{
		return pref.getBoolean(IS_STAGE1_START, false);
	}
	//=======  ends ======================
	
	//======== Store Stage two Start state ===================================
	public void Store_Stage_Two_Start_State(boolean isStarted)
	{
		editor.putBoolean(IS_STAGE2_START, isStarted);
		editor.commit();
	}
	public boolean Get_Stage_Two_Start_State()
	{
		return pref.getBoolean(IS_STAGE2_START,false);
	}
	//============== ends ====================================================
	public void Store_App_Backgroung(String bgcolor)
	{
		editor.putString(APP_BACK_GROUND,bgcolor);
		editor.commit();
	}
	
	public String Get_App_Background()
	{
		return pref.getString(APP_BACK_GROUND, "green_back");
	}
	public void Store_personalinfo_state(boolean is_info_taken)
	{
		editor.putBoolean(IS_INFO_TAKEN, is_info_taken);
		editor.commit();
	}
	
	public boolean Get_personalinfo_state()
	{
		return pref.getBoolean(IS_INFO_TAKEN, false);
	}
	public void Store_stage_one_flag(boolean is_ex)
	{
		editor.putBoolean(IS_STAGE1_FST_EX,is_ex);
		editor.commit();
	}
	
	public boolean Get_stage_one_flag()
	{
		return pref.getBoolean(IS_STAGE1_FST_EX,true);
	}
	
	public void Store_agree_date_time(String dateTime)
	{
		editor.putString(AGREE_TIME, dateTime);
		editor.commit();
	}
	
	public String Get_agree_date_time()
	{
		return pref.getString(AGREE_TIME, "");
	}
	//===============WelCome State=================//
	public void Store_welcome_state(boolean flag)
	{
		editor.putBoolean(WELCOME_STATE,flag);
		editor.commit();
	}
	
	public boolean Get_welcome_state()
	{
		return pref.getBoolean(WELCOME_STATE,false);
	}
	//=====================================
	public void Store_agree_state(boolean isAgree)
	{
		editor.putBoolean(IS_AGREE,isAgree);
		editor.commit();
	}
	
	public boolean Get_agree_state()
	{
		return pref.getBoolean(IS_AGREE,false);
	}
	
	public void Store_stage_one_state(boolean sone)
	{
		editor.putBoolean(IS_STAGE1_COMP, sone);
		editor.commit();
	}
	
	public boolean Get_stage_one_state()
	{
		return pref.getBoolean(IS_STAGE1_COMP, false);
	}
	
	public void Store_stage_two_state(boolean stwo)
	{
		editor.putBoolean(IS_STAGE2_COMP, stwo);
		editor.commit();
	}
	public boolean Get_stage_two_state()
	{
		return pref.getBoolean(IS_STAGE2_COMP, false);
	}
	
	public void Store_stage_three_state(boolean sthree)
	{
		editor.putBoolean(IS_STAGE3_COMP, sthree);
		editor.commit();
	}
	
	public boolean Get_stage_three_state()
	{
		return pref.getBoolean(IS_STAGE3_COMP, false);
	}
	
	public void Store_stage_four_state(boolean sfour)
	{
		editor.putBoolean(IS_STAGE4_COMP, sfour);
		editor.commit();
	}
	
	public boolean Get_stage_four_state()
	{
		return pref.getBoolean(IS_STAGE4_COMP, false);
	}
}
