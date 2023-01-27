package com.tk.talknshoot.util;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ParseException;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;


public class TkTool{
	
	/** constructor */
	public TkTool(){
		
	}
	
	/** isGoodAppVersion */
	public boolean isGoodAppVersion(Context p_context, String p_firstinstall, String p_type, String p_duration) {
		TkTrace.log(p_context,"[TkTool/isGoodAppVersion()] :  p_firstinstall = "+p_firstinstall);
		TkTrace.log(p_context,"[TkTool/isGoodAppVersion()] :  p_type = "+p_type);
		TkTrace.log(p_context,"[TkTool/isGoodAppVersion()] :  p_duration = "+p_duration);
		//String dateStart = "11/03/14 09:29:58";
		//String dateStop = "11/03/14 09:33:43";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateStart = p_firstinstall;
		//String dateStart = "20170708";
		String dateStop = format.format(Calendar.getInstance().getTime());
		
		Date d1 = null;
	    Date d2 = null;
	    
	    try {
	        d1 = format.parse(dateStart);
	        d2 = format.parse(dateStop);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    int diffInDays = (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	    //TkTrace.log(p_context,"[TkTool/isGoodAppVersion()] :  diff date = "+diffInDays);
	    int duration = Integer.parseInt(p_duration);
	    //TkTrace.log(p_context,"[TkTool/isGoodAppVersion()] :  duration = "+duration);
	
	    if(diffInDays > duration ){
	    	return false;
	    }
	    return true;
		
	}

	/** nbDayAppVersion */
	public int nbDayAppVersion(Context p_context, String p_firstinstall, String p_type, String p_duration) {
		//String dateStart = "11/03/14 09:29:58";
		//String dateStop = "11/03/14 09:33:43";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateStart = p_firstinstall;
		//String dateStart = "20170708";
		String dateStop = format.format(Calendar.getInstance().getTime());
		
		Date d1 = null;
	    Date d2 = null;
	    
	    try {
	        d1 = format.parse(dateStart);
	        d2 = format.parse(dateStop);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    int diffInDays = (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	    TkTrace.log(p_context,"[TkTool/nbDayAppVersion()] :  diffInDays = "+diffInDays);
	    int nbDays = Integer.parseInt(p_duration);  
	    TkTrace.log(p_context,"[TkTool/nbDayAppVersion()] :  nbDays = "+nbDays);
	    nbDays = (int) (nbDays - diffInDays);
	    return nbDays;
		
	}


	/** nbDayAppVersion */
	public String formatDate(Context p_context, String p_date) {
		  try {
			  SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			  Date date = null;
			  date = format.parse(p_date);
			  SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
			  String date1 = dateFormat1.format(date);
			  return date1;        
		    } catch (Exception e) {
		    	TkTrace.log(p_context,"[TkTool/formatDate()] :  Exception = "+e.getMessage());
		    	return null;
		    }
		
	}	
		
}
