package com.xiaya.core.enums;

public enum CacheTimes {

	FIFTEEN(15),
	THIRTY(30),
	ONEMINS(60),
	TWOMINS(120),
	FIFMINS(300),
	TENMINS(600),
	FIFTEENMINS(900),
	HALFHOUR(1800),
	ONEHOUR(3600),
	SIXHOUR(21600),
	ONEDAY(86400),
	WEEKLY(604800),
	THIRTYDAY(2592000),
	FOREVER(-2);
	
	private int time;
	
	private CacheTimes(int time){
		this.time = time;
	}
	
	public int getTime(){
		return time;
	}
	
	public void setTime(int time){
		this.time = time;
	}
	
}
