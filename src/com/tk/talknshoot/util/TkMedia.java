package com.tk.talknshoot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class TkMedia {

	public TkMedia() {
       
   }

  
   /** httpCommand */
	public String httpReadUrl(Context p_context,String p_url)  {
		URLConnection connection; 
    	ConnectivityManager connMgr = (ConnectivityManager)p_context.getSystemService(p_context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

    	if (networkInfo != null && networkInfo.isConnected()) {
    		try{
    			URL url=new URL(p_url);
	            connection = url.openConnection();
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
	
	public HashMap readJson(Context p_context,String p_urlListMedia,String p_urlDcim){
	 try {
		TkTrace.log(p_context,"[TkMedia / readJson} p_url = "+p_urlListMedia);
		int nbMedia = 0;
		HashMap tabMedia = new HashMap();
		String urlMedia = p_urlListMedia;
		//TkTrace.log(p_context,"[TkMedia / readJson} set nb media 0");
		tabMedia.put("nbmedia",String.valueOf(0));
		//TkTrace.log(p_context,"[TkMedia / readJson} read url");
		String stringUrl = httpReadUrl(p_context,p_urlListMedia);
		//TkTrace.log(p_context,"[TkMedia / readJson} stringUrl = "+stringUrl);
	 	JSONObject json = new JSONObject(stringUrl);
	 	JSONArray jsonMediaArray = json.optJSONArray("media");
	 	int len = jsonMediaArray.length();
	 	//TkTrace.log(p_context,"[TkMedia / readJson} nb = "+len);
	    
	    for(int i=0; i < len; i++) {
               JSONObject obj = jsonMediaArray.getJSONObject(i);
               String folderName = obj.optString("d");
               urlMedia = p_urlDcim+folderName.trim();
               //TkTrace.log(p_context,"[TkMedia / readJson} urlMedia = "+urlMedia);
               tabMedia.put("folder",folderName);
               //TkTrace.log(p_context,"[TkMedia / readJson} folderName = "+folderName);
               //folder
               JSONArray folderArray = obj.getJSONArray("fs");
               int lenFolder = folderArray.length();
               nbMedia = lenFolder;
               //TkTrace.log(p_context,"[TkMedia / readJson} nbMedia = "+nbMedia);
               tabMedia.put("nbmedia",String.valueOf(nbMedia));
               for(int j=0; j < lenFolder; j++) {
               	JSONObject objFolder = folderArray.getJSONObject(j);
               	String mediaName = objFolder.optString("n");
               	tabMedia.put("media_name_"+j,mediaName);
               	
               	if(mediaName.toUpperCase().contains("JPG")){
               		tabMedia.put("media_type_"+j,"picture");
               	}else{
               		tabMedia.put("media_type_"+j,"movie");	                		
               	}
               	String mediaUrl = urlMedia+"/"+mediaName;
               	tabMedia.put("media_url_"+j,mediaUrl);
               	//TkTrace.log(p_context,"[TkMedia / readJson} mediaName("+j+")="+mediaName);
               	//TkTrace.log(p_context,"[TkMedia / readJson} mediaUrl("+j+")="+mediaUrl);
               }
               
               //TkTrace.log(p_context,"[TkMedia / readJson} fin boucle i");
       }
	    TkTrace.log(p_context,"[TkMedia / readJson} return");
	    return tabMedia;
	 	
	 } catch (Exception e) {
	      TkTrace.log(p_context,"[TkMedia/readJson] Exception : "+e.getMessage());
	      return null;
	}
  }
	
	/* public HashMap readJson(Context p_context,String p_url,String p_urlroot1){
		 try {
			TkTrace.log(p_context,"[TkMedia / readJson} p_url = "+p_url);
			TkTrace.log(p_context,"[TkMedia / readJson} p_urlroot = "+p_urlroot1);
			int nbMedia = 0;
			HashMap tabMedia = new HashMap();
			//String urlMedia1 = p_urlroot1;
			String urlMedia = "";
			
			tabMedia.put("nbmedia",String.valueOf(0));
			String stringUrl = httpReadUrl(p_context,p_url);
		 	JSONObject json = new JSONObject(stringUrl);
		 	JSONArray jsonMediaArray = json.optJSONArray("media");
		 	int len = jsonMediaArray.length();
		 	//TkTrace.log(p_context,"[TkMedia / readJson} nb = "+len);
		    
		    for(int i=0; i < len; i++) {
	                JSONObject obj = jsonMediaArray.getJSONObject(i);
	                String folderName = obj.optString("d");
	                urlMedia = urlMedia+folderName.trim();
	                TkTrace.log(p_context,"[TkMedia / readJson} urlMedia = "+urlMedia);
	                //tabMedia.put("folder",folderName);
	                TkTrace.log(p_context,"[TkMedia / readJson} folderName = "+folderName);
	                //folder
	                JSONArray folderArray = obj.getJSONArray("fs");
	                int lenFolder = folderArray.length();
	                nbMedia = lenFolder;
	                //TkTrace.log(p_context,"[TkMedia / readJson} nbMedia = "+nbMedia);
	                tabMedia.put("nbmedia",String.valueOf(nbMedia));
	                for(int j=0; j < lenFolder; j++) {
	                	JSONObject objFolder = folderArray.getJSONObject(j);
	                	String mediaName = objFolder.optString("n");
	                	tabMedia.put("media_name_"+j,mediaName);
	                	
	                	if(mediaName.toUpperCase().contains("JPG")){
	                		tabMedia.put("media_type_"+j,"picture");
	                	}else{
	                		tabMedia.put("media_type_"+j,"movie");	                		
	                	}
	                	String mediaUrl = urlMedia+"/"+mediaName;
	                	tabMedia.put("media_url_"+j,mediaUrl);
	                	TkTrace.log(p_context,"[TkMedia / readJson} mediaName("+j+")="+mediaName);
	                	TkTrace.log(p_context,"[TkMedia / readJson} mediaUrl("+j+")="+mediaUrl);
	                }
	                
	                TkTrace.log(p_context,"[TkMedia / readJson} fin boucle i");
	        }
		    TkTrace.log(p_context,"[TkMedia / readJson} return");
		    return tabMedia;
		 	
		 } catch (Exception e) {
		      TkTrace.log(p_context,"[TkMedia/readJson] Exception : "+e.getMessage());
		      return null;
		}
	   }*/
	   
	
	 
	 
	 
}
