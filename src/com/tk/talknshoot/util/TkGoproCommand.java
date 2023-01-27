package com.tk.talknshoot.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

public class TkGoproCommand {
	
	/** Constructeur privé */
	private TkGoproCommand()
	{}
 
	/** Instance unique non préinitialisée */
	private static TkGoproCommand INSTANCE = null;
	private static Context context;
	private static String nameWifi;
	private static String ipAddressWifi;
	private static String passwordWifi;
	private static String typeCamera;

	//String gopro = "10.5.5.100";
	//String goprotwo = "10.5.5.109";
    		
	/** Point d'accès pour l'instance unique du singleton */
	public static synchronized TkGoproCommand getInstance()
	{			
		if (INSTANCE == null){
			INSTANCE = new TkGoproCommand();	
		}
		return INSTANCE;
	}
	
	/** set context */
	public void setContext(Context p_context){
		context = p_context;
	}
	
	/** set context */
	public void setTypeCamera(String p_typeCamera){
		typeCamera = p_typeCamera;
	}
	
	
	/** set context */
	public boolean getWifiStatus(){
		WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        nameWifi = wifiInfo.getSSID();
        int ip = wifiInfo.getIpAddress();
        ipAddressWifi = Formatter.formatIpAddress(ip);
        TkTrace.log(context,"WIFI:("+nameWifi+") IP:"+ipAddressWifi);
    	
    	//http://10.5.5.9/param1/PARAM2?t=PASSWORD&p=%OPTION
        if (ipAddressWifi.equals("10.5.5.100")){
        	typeCamera = "OLD";
        	
        }
        
        //http://10.5.5.9/gp/gpControl/command/shutter?p=1"
        if (ipAddressWifi.equals("10.5.5.109")){
        	typeCamera = "NEW";
        }
        return true;
	}
	
	/** executeCommand */
	public String executeCommand(String p_typeCommand){
		//TkTrace.log(context,"Commande Action = "+p_typeCommand);
		String url = "";
		
		
		TkCameraInfo tkCameraInfo = TkAppManager.getTkCameraInfo();
		String camerabrand = tkCameraInfo.getCameraBrand();
		String cameratype = tkCameraInfo.getCameraType();
		String wifiPassword = tkCameraInfo.getWifiPassword();
		//TkTrace.log(context,"cameratype = "+cameratype);
		//TkTrace.log(context,"wifiPassword = "+wifiPassword);
		
		if(cameratype.equals("HERO3+/3/2")){
			if(p_typeCommand.equals("MODE_VIDEO")){
				url = "http://10.5.5.9/camera/CM?t="+wifiPassword+"&p=%00";
			}
			if(p_typeCommand.equals("MODE_PHOTO")){
				url = "http://10.5.5.9/camera/CM?t="+wifiPassword+"&p=%01";
			}
			if(p_typeCommand.equals("MODE_BURST")){
				url = "http://10.5.5.9/bacpac/CM?t="+wifiPassword+"&p=%02";
			}
			if(p_typeCommand.equals("RESET_MODE_BURST")){
				url = "http://10.5.5.9/bacpac/CM?t="+wifiPassword+"&p=%01";
			}
			if(p_typeCommand.equals("START_RECORD_VIDEO")){
				url = "http://10.5.5.9/bacpac/SH?t="+wifiPassword+"&p=%01";
			}
			if(p_typeCommand.equals("TAKE_PICTURE")){
				url = "http://10.5.5.9/bacpac/SH?t="+wifiPassword+"&p=%01";
			}
			if(p_typeCommand.equals("STOP_RECORD_VIDEO")){
				url = "http://10.5.5.9/bacpac/SH?t="+wifiPassword+"&p=%00";
			}
			if(p_typeCommand.equals("BURST_PICTURE")){
				url = "http://10.5.5.9/bacpac/SH?t="+wifiPassword+"&p=%01";
			}
			if(p_typeCommand.equals("CAMERA_ON")){
				url = "http://10.5.5.9/bacpac/PW?t="+wifiPassword+"&p=%00";			}
			
			if(p_typeCommand.equals("CAMERA_OFF")){
				url = "http://10.5.5.9/bacpac/PW?t="+wifiPassword+"&p=%01";			}
			
		}else{
			if(p_typeCommand.equals("MODE_VIDEO")){
				url = "http://10.5.5.9/gp/gpControl/command/mode?p=0";
			}
			if(p_typeCommand.equals("MODE_PHOTO")){
				url = "http://10.5.5.9/gp/gpControl/command/mode?p=1";
			}
			if(p_typeCommand.equals("MODE_BURST")){
				url = "http://10.5.5.9/gp/gpControl/command/sub_mode?mode=2&sub_mode=0";
			}
			
			if(p_typeCommand.equals("RESET_MODE_BURST")){
				url = "http://10.5.5.9/gp/gpControl/command/sub_mode?mode=1&sub_mode=0";
			}
			
			if(p_typeCommand.equals("START_RECORD_VIDEO")){
				url = "http://10.5.5.9/gp/gpControl/command/shutter?p=1";
			}
			if(p_typeCommand.equals("TAKE_PICTURE")){
				url = "http://10.5.5.9/gp/gpControl/command/shutter?p=1";
			}
			if(p_typeCommand.equals("STOP_RECORD_VIDEO")){
				url = "http://10.5.5.9/gp/gpControl/command/shutter?p=0";
			}
			if(p_typeCommand.equals("BURST_PICTURE")){
				url = "http://10.5.5.9/gp/gpControl/command/shutter?p=1";
			}
			
			if(p_typeCommand.equals("CAMERA_ON")){
				url = "http://10.5.5.9/gp/gpControl/command/system/sleep";
			}
			
			if(p_typeCommand.equals("CAMERA_OFF")){
				url = "http://10.5.5.9/gp/gpControl/command/system/sleep";
			}
		}
		/** Execution de la commande */
		TkTrace.log(context,"[TkGoproCommand/executeCommand] url = "+ url);
		this.httpCommand(url);
		return "OK";
		//return this.httpCommand(url);
	
	}
	/** httpCommand */
	/*public final boolean httpCommand(String p_url)  {
		URLConnection con; 
		URL url;
		try{
				TkTrace.log(context,"[TkGoproCommand/httpCommand1] url = "+p_url);
				url = new URL(p_url);
				TkTrace.log(context,"[TkGoproCommand/httpCommand1] getSystemService");
				ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
				TkTrace.log(context,"[TkGoproCommand/httpCommand1] getActiveNetworkInfo");
		    	NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		    	if (networkInfo != null && networkInfo.isConnected()) {
		    		TkTrace.log(context,"[TkGoproCommand/httpCommand1] openConnection");
		    		con = url.openConnection();
		    		TkTrace.log(context,"[TkGoproCommand/httpCommand1] fileReader");
		    		BufferedReader fileReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		    		TkTrace.log(context,"[TkGoproCommand/httpCommand1] StringBuilder");
					StringBuilder data = new StringBuilder();
		    		String line;
		    		TkTrace.log(context,"[TkGoproCommand/httpCommand1] line");
		    		while ((line = fileReader.readLine()) != null) {
		    			 data.append(line).append('\n');
		    		}
		    		TkTrace.log(context,"[TkGoproCommand/httpCommand1] data = "+data.toString());
		    		return true;
		    	} else {
		    		TkTrace.log(context,"[TkGoproCommand/httpCommand1] networkInfo : Aucune connection reseau");
		    		return false;
		    	}
		} catch (Exception e) {
			TkTrace.log(context,"[TkGoproCommand/httpCommand1] Exception: Commande "+e.getMessage());
			return false;
		}
	}*/
	
	/** httpCommand */
	public String httpCommand(String p_url)  {
		URLConnection connection; 
    	ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

    	if (networkInfo != null && networkInfo.isConnected()) {
    		try{
    			TkTrace.log(context,"[TkGoproCommand/httpReadUrl] p_url = "+p_url);
    			URL url=new URL(p_url);
    			//TkTrace.log(context,"[TkMedia/httpReadUrl] openConnection");
    			connection = url.openConnection();
    			//TkTrace.log(context,"[TkMedia/httpReadUrl] setConnectTimeout");
	            connection.setConnectTimeout(2000);
	            //TkTrace.log(context,"[TkMedia/httpReadUrl] BufferedReader");
	            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            //TkTrace.log(context,"[TkMedia/httpReadUrl] getContentLength");
	            int cl = connection.getContentLength();
	            StringBuilder builder = cl > 0 ? new StringBuilder(cl) : new StringBuilder();
	            String line;
	            //TkTrace.log(context,"[TkMedia/httpReadUrl] while");
	            while ((line = reader.readLine()) != null) {
	                builder.append(line);
	            }
	            TkTrace.log(context,"[TkMedia/httpReadUrl] return = "+ builder.toString());
	            return builder.toString();
    		} catch (Exception e) {
				TkTrace.log(context,"[TkGoproCommand/httpReadUrl] Exception: Commande "+e.getMessage());
				return null;
			}
    	}
        return null;		
	}
	

	/** httpCommand */
	/*public String httpCommand(String p_url)  {

		try{
			TkTrace.log(context,"[TkGoproCommand/httpCommand] url "+p_url);
			URL url = new URL(p_url);
			TkTrace.log(context,"[TkGoproCommand/httpCommand] openConnection ");
	    	HttpURLConnection c = (HttpURLConnection) url.openConnection();
	    	TkTrace.log(context,"[TkGoproCommand/httpCommand] setRequestMethod ");
	    	c.setRequestMethod("GET");
	    	TkTrace.log(context,"[TkGoproCommand/httpCommand] connect ");
	    	c.connect();//connect the URL Connection
	    	if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
	    		TkTrace.log(context,"[TkMedia/httpReadUrl] Server returned HTTP " + c.getResponseCode()+ " " + c.getResponseMessage());
	    	}
	    	TkTrace.log(context,"[TkGoproCommand/httpCommand] return ");	    	
	    	return "OK";
		} catch (Exception e) {
			TkTrace.log(context,"[TkGoproCommand/httpCommand] Exception: Commande "+e.getMessage());
			return null;
		}
	}*/
}
