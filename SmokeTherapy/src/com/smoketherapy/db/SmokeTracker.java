package com.smoketherapy.db;

public class SmokeTracker {

	int id;
	int stage;
	String date;
	int day_req;
	int allow_cigge;
	int extra_cigge;
	int day_in_stage;
	int notyfied_cigge;
	
	public SmokeTracker()
	{
		
	}
	
	public SmokeTracker(int stage,String date,int day_req,int allow,int extra,int days,int notyfied_cigge)
	{
		this.stage = stage;
		this.date  = date;
		this.day_req = day_req;
		this.allow_cigge = allow;
		this.extra_cigge = extra;
		this.day_in_stage = days;
		this.notyfied_cigge = notyfied_cigge;
	}
	public SmokeTracker(int id,int stage,String date,int day_req,int allow,int extra,int days,int notyfied_cigge)
	{
		this.id = id;
		this.stage = stage;
		this.date  = date;
		this.day_req = day_req;
		this.allow_cigge = allow;
		this.extra_cigge = extra;
		this.day_in_stage= days;
		this.notyfied_cigge = notyfied_cigge;
	}
	
	public int getRawID()
	{
		return this.id;
	}
	
	public int getStage()
	{
		return this.stage;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public int getReq_Day()
	{
		return this.day_req;
	}
	
	public int getAllow()
	{
		return this.allow_cigge;
	}
	public int getExtra()
	{
		return this.extra_cigge;
	}
	
	public int getDaysInStage()
	{
		return this.day_in_stage;
	}
	
	public int getNotyfiedCigge()
	{
		return this.notyfied_cigge;
	}
}

