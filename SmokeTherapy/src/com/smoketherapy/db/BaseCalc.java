package com.smoketherapy.db;

public class BaseCalc {

	int reduce_in_S2;
	int reduce_in_S3;
	int reduce_in_S4;
	int therapy_time;
	
	public BaseCalc(int r_s2,int r_s3,int r_s4,int t_time)
	{
		this.reduce_in_S2 = r_s2;
		this.reduce_in_S3 = r_s3;
		this.reduce_in_S4 = r_s4;
		this.therapy_time = t_time;
	}
	
	public int get_S2_Time()
	{
		int time_s2 = this.reduce_in_S2 * 2 ;
		return time_s2;
	}
	
	public int get_S3_Time()
	{
		int time_s3 = this.reduce_in_S3 * 2;
		return time_s3;
	}
	
	public int get_S4_Time()
	{
		int time_s4 = (this.reduce_in_S4 - 1) * 3;
		return time_s4;
	}
	
	public int get_Total_Therapy_Time()
	{
		return this.therapy_time;
	}
}
