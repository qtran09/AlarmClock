package Clocks;

import java.util.Calendar;
import java.util.Date;

public class Reminder{
	//Use multithreading to keep track of reminders
	
	public Reminder(String message,int hour, int minute, int second){
		this.message = message;
		hourRemind = hour;
		minuteRemind = minute;
		secondRemind = second;
		isActive = true;
	}
	@SuppressWarnings("deprecation")
	public String getTime(){
		Date day = Calendar.getInstance().getTime();
		day.setHours(hourRemind);
		day.setMinutes(minuteRemind);
		day.setSeconds(secondRemind);
		return day.toString();
	}
	public String getInfo(){
		return "Event: " + message + 
				"\n Time: " + hourRemind.toString() + ":" + minuteRemind.toString() + ":" + secondRemind.toString();
	}
	public void setInfo(String message, int hour, int minute, int second){
		this.message = message;
		hourRemind = hour;
		minuteRemind = minute;
		secondRemind = second;
	}
	public boolean getActive(){
		return isActive;
	}
	public void setActive(boolean running){
		isActive = running;
	}
	//Variable declarations
	String message;
	Integer hourRemind;
	Integer minuteRemind;
	Integer secondRemind;
	boolean isActive;
}
