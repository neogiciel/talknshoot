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

public class TkHttpJson {
	
	/** elements */
	//static protected Context context;
	
	/** Constructor */
	public TkHttpJson()
	{}
	
	/** httpReadUrl */
    public HashMap getAdminValue(Context p_context,String p_stringparam){
    		TkTrace.log(p_context,"[TkHttpJson / getAdminValue} debut");
			String url = p_context.getString(R.string.application_url_admin);
			url = url +"get.php?"+p_stringparam;
			TkTrace.log(p_context,"[TkHttpJson / getAdminValue} url = "+url);
			return readJson(p_context, url);
	 }
	
	/** httpReadUrl */
	public String httpReadUrl(Context p_context,String p_url)  {
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
	            TkTrace.log(p_context,"[TkHttpJson/httpReadUrl] read =  "+builder.toString());
	            return builder.toString();
    		} catch (Exception e) {
				TkTrace.log(p_context,"[TkHttpJson/httpReadUrl] Exception: Commande "+e.getMessage());
				return null;
			}
    	}
        return null;		
	}
	
	
	/** readJson */
	 public HashMap readJson(Context p_context,String p_url){
		 try {
			
			 //TkTrace.log(p_context,"[TkHttpJson / readJson] tabElement");
			 int nbElement = 0;
			 HashMap tabElement = new HashMap();
			 //TkTrace.log(p_context,"[TkHttpJson / readJson] new tabElement");
			 tabElement.put("nbelement",String.valueOf(0));
			 //TkTrace.log(p_context,"[TkHttpJson / readJson] lecture url");
			 String stringUrl = httpReadUrl(p_context,p_url);
			 if(stringUrl == null) {
				 return tabElement;
			 }	 
			 //TkTrace.log(p_context,"[TkHttpJson / readJson] creation du json 1");
			 JSONObject jsonObj1 = new JSONObject(stringUrl);
			 //Json pere
			 JSONArray jsonArray1 =  jsonObj1.optJSONArray("result");
			 //TkTrace.log(p_context,"[TkHttpJson / readJson} json result");
			 int len1 = jsonArray1.length();
			 //TkTrace.log(p_context,"[TkHttpJson / readJson] len1 ="+len1);
			 tabElement.put("nbelement",String.valueOf(len1));
			 //TkTrace.log(p_context,"[TkHttpJson / readJson] for ");
			 for(int i=0; i < len1; i++) {
				 	JSONObject jsonObj2 = jsonArray1.getJSONObject(i);
					JSONArray jsonArray2 =  jsonObj2.optJSONArray("element");
					 //TkTrace.log(p_context,"[TkHttpJson / readJson] json element");
				 	int len2 = jsonArray2.length();
				 	 //TkTrace.log(p_context,"[TkHttpJson / readJson] len2= "+len2);
				 	for(int j=0; j < len2; j++) {
				 		JSONObject jsonObj3 = jsonArray2.getJSONObject(j);
				 		String key = jsonObj3.optString("key");
	          	   		String val = jsonObj3.optString("val");
	          	   		tabElement.put(key+"_"+i,val);
	          	   		TkTrace.log(p_context,"[TkHttpJson / readJson] "+key+"_"+i+" = "+val);
	          	   		
				 	}
			
			 }
			 return tabElement;
			
		 } catch (Exception e) {
		      TkTrace.log(p_context,"[TkHttpJson/readJson] Exception : "+e.getMessage());
		      return null;
		}
	   }
	   
}
