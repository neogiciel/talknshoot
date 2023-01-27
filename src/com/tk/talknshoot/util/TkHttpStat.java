package com.tk.talknshoot.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tk.talknshoot.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class TkHttpStat {
	
	/** elements */
	//static protected Context context;
	
	/** Constructor */
	public TkHttpStat()
	{}
	
	/** httpReadUrl */
    public void stat(Context p_context,String p_type,String p_nb){
			String url = p_context.getString(R.string.application_url_admin);
			url = url +"stat-android.php?type="+p_type+"&nb="+p_nb;
			TkTrace.log(p_context,"[TkHttpJson / getAdminValue} url = "+url);
			httpReadUrl(p_context, url);
	 }
	
	/** httpReadUrl */
	private String httpReadUrl(Context p_context,String p_url)  {
		URLConnection connection; 
    	ConnectivityManager connMgr = (ConnectivityManager)p_context.getSystemService(p_context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

    	if (networkInfo != null && networkInfo.isConnected()) {
    		try{
    			URL url=new URL(p_url);
	            connection = url.openConnection();
	            connection.setConnectTimeout(2 * 1000);
    			connection.setReadTimeout(2 * 1000);
	            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            int cl = connection.getContentLength();
	            StringBuilder builder = cl > 0 ? new StringBuilder(cl) : new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                builder.append(line);
	            }
	            return builder.toString();
    		} catch (Exception e) {
				TkTrace.log(p_context,"[TkMedia/httpReadUrl] Exception: Commande "+e.getMessage());
				return null;
			}
    	}
        return null;		
	}
		   
}
