package com.tk.talknshoot.util;

import java.sql.Date;

public class TkCameraInfo {
	
	public String camerabrand;
	public String cameratype;
	public String lastaction;
	public String currentaction;
	public String currentmode;
	public String wifiname;
	public String wifipassword;
	
	/** constructor */
	public TkCameraInfo(){
		camerabrand = "none";
		cameratype = "none";
		lastaction = "none";
		currentaction = "none";
		currentmode = "none";
		wifiname = "none";
		wifipassword = "none";
	}
	
	/** setCameraBrand */
	public void setCameraBrand(String p_camerabrand){
		camerabrand = p_camerabrand;		
	}
	/** getCameraBrand */
	public String getCameraBrand(){
		return camerabrand;		
	}
	/** setCameraType */
	public void setCameraType(String p_cameratype){
		cameratype = p_cameratype;		
	}
	/** getCameraType */
	public String getCameraType(){
		return cameratype;		
	}
	/** setLastAction */
	public void setLastAction(String p_lastaction){
		lastaction = p_lastaction;		
	}
	/** getLastAction */
	public String getLastAction(){
		return camerabrand;		
	}
	/** setCurrentAction */
	public void setCurrentAction(String p_currentaction){
		currentaction = p_currentaction;		
	}
	/** getCurrentAction */
	public String getCurrentAction(){
		return currentaction;		
	}
	/** setCurrentMode */
	public void setCurrentMode(String p_currentmode){
		currentmode = p_currentmode;		
	}
	/** getCurrentMode */
	public String getCurrentMode(){
		return currentmode;		
	}
	/** setWifiName */
	public void setWifiName(String p_wifiname){
		wifiname = p_wifiname;		
	}
	/** getWifiName */
	public String getWifiName(){
		return wifiname;		
	}
	/** setWifiPassword */
	public void setWifiPassword(String p_wifipassword){
		wifipassword = p_wifipassword;		
	}
	/** getWifiPassword */
	public String getWifiPassword(){
		return wifipassword;		
	}
}
