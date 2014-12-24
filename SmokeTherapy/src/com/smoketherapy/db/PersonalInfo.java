package com.smoketherapy.db;

public class PersonalInfo {

	String gender;
	String wake_up_time;
	String sleep_hours;
	
	public PersonalInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public PersonalInfo(String gender,String wakeTime,String sleepHours)
	{
		this.gender = gender;
		this.wake_up_time = wakeTime;
		this.sleep_hours  = sleepHours;
	}
	
	public String getGender()
	{
		return this.gender;
	}
	
	public String getWake_Up_Time()
	{
		return this.wake_up_time;
	}
	
	public String getSleep_Hours()
	{
		return this.sleep_hours;
	}
}
