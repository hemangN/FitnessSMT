package com.smoketherapy.db;

public class StageOneInfo {

	String date;
	String time;
	int no_of_cigge;
	
	public StageOneInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public StageOneInfo(String date, String time, int no_of_cigge)
	{
		this.date = date;
		this.time = time;
		this.no_of_cigge = no_of_cigge;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public String getTime()
	{
		return this.time;
	}
	
	public int no_of_cigge()
	{
		return this.no_of_cigge;
	}
}
