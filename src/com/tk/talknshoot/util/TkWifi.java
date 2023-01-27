package com.tk.talknshoot.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;


public class TkWifi extends BroadcastReceiver{
	
	/** parameter */
	public String wifiname;
	public String wifiip;
	private List<ScanResult> listWifi;
	private boolean processEnd;
	/** constructor */
	public TkWifi(){
		
	}

	//getWifiStatus
	public String getWifiStatus(Context p_context){
		WifiManager wifiManager = (WifiManager) p_context.getSystemService(p_context.WIFI_SERVICE);
	    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	    wifiname = wifiInfo.getSSID();
	    wifiip = Formatter.formatIpAddress(wifiInfo.getIpAddress()); 
	    //TkTrace.log(p_context,"WIFI:("+wifiname+") IP:"+wifiip);
	    
	    if(wifiname == null){
	    	wifiname = "none";
	    	return "none";
	    }
	    
		if(wifiname.length()>3) {
			wifiname = wifiname.substring(1,wifiname.length()-1);
		}
	    return wifiname;
	}
	
	//getWifiStatus
	public String getWifiConnected(Context p_context){
		SupplicantState supState;
		WifiManager wifiManager = (WifiManager) p_context.getSystemService(p_context.WIFI_SERVICE);
	    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	    wifiname = wifiInfo.getSSID();
	    supState = wifiInfo.getSupplicantState();
	    
	    if(wifiname == null){
	    	wifiname = "none";
	    	return "none";
	    }else if(wifiname.equals("unknow ssid")){
	    	wifiname = "none";
	    	return "none";
	    }else {
	    	
	        //ASSOCIATED - Association completed.
	    	//ASSOCIATING - Trying to associate with an access point.
	    	//COMPLETED - All authentication completed.
	    	//DISCONNECTED - This state indicates that client is not associated, but is likely to start looking for an access point.
	    	//DORMANT - An Android-added state that is reported when a client issues an explicit DISCONNECT command.
	    	//FOUR_WAY_HANDSHAKE - WPA 4-Way Key Handshake in progress.
	    	//GROUP_HANDSHAKE - WPA Group Key Handshake in progress.
	    	//INACTIVE - Inactive state.
	    	//INVALID - A pseudo-state that should normally never be seen.
	    	//SCANNING - Scanning for a network.
	    	//UNINITIALIZED - No connection.

	    	if(SupplicantState.COMPLETED.equals(supState)) {
	            // completed state, connected to access point
	    		TkTrace.log(p_context,"[TkWifi/getWifiConnected] : getWifiConnected  = COMPLETED");
	    		
	    		if(wifiname.length()>3) {
	    			wifiname = wifiname.substring(1,wifiname.length()-1);
	    		}
	    		return wifiname;
	        }else {
	        	wifiname = "none";
		    	return "none";
	        }
	    }
	    //return wifiname;
	}
	
	
	//getWifiStatus
	public boolean getWifiConnected(Context p_context,String p_wifiname){
			SupplicantState supState;
			WifiManager wifiManager = (WifiManager) p_context.getSystemService(p_context.WIFI_SERVICE);
		    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		    wifiname = wifiInfo.getSSID();
		    supState = wifiInfo.getSupplicantState();
		    
		    if(wifiname == null){
		    	return false;
		    }else {
		    	if(SupplicantState.COMPLETED.equals(supState)) {
		            // completed state, connected to access point
		    		if(wifiname.equals(p_wifiname)) {
		    			return true;
		    		}else {
		    			return false;
		    		}
		        }else {
		        	wifiname = "none";
		        	return false;
		        }
		    }
		    //return wifiname;
		}
		
 //If 3G, 4g 	
 public String getNetworkType(Context p_context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)p_context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2g";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                //Log.d("Type", "3g");
                //For 3g HSDPA , HSPAP(HSPA+) are main  networktype which are under 3g Network
                //But from other constants also it will 3g like HSPA,HSDPA etc which are in 3g case.
                //Some cases are added after  testing(real) in device with 3g enable data
                //and speed also matters to decide 3g network type
                //https://en.wikipedia.org/wiki/4G#Data_rate_comparison
                return "3g";
            case TelephonyManager.NETWORK_TYPE_LTE:
                //No specification for the 4g but from wiki
                //I found(LTE (Long-Term Evolution, commonly marketed as 4G LTE))
                //https://en.wikipedia.org/wiki/LTE_(telecommunication)
                return "4g";
            default:
                return "none";
        }
    }
	
 	//isNetworkConnected
 	public boolean isNetworkConnected(Context p_context) {
 		ConnectivityManager cm = (ConnectivityManager)p_context.getSystemService(Context.CONNECTIVITY_SERVICE);
 		NetworkInfo netInfo = cm.getActiveNetworkInfo();
 		return netInfo != null && netInfo.isConnected();
 	}
	
	// connectWifi 
	public boolean connectWifi(Context p_context, String p_wifiName,String p_wifiPassword) {
		
		WifiConfiguration conf = new WifiConfiguration();
		//conf.SSID = "\"" + p_wifiName + "\"";   
		//conf.wepKeys[0] = "\"" + p_wifiPassword + "\""; 
		conf.SSID = "\"".concat(p_wifiName).concat("\""); 
		conf.preSharedKey  = "\"".concat(p_wifiPassword).concat("\"");
		conf.hiddenSSID = false;
		conf.status = WifiConfiguration.Status.ENABLED;
		
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP); 
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP); 
		conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK); 
		conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP); 
		conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP); 
		conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
	    
		
		/*conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
		conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		conf.allowedAuthAlgorithms.clear();
		conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
		conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		*/
		WifiManager wifiManager = (WifiManager)p_context.getSystemService(Context.WIFI_SERVICE); 
		//TkTrace.log(p_context,"[TkWifi/connectWifi] : addNetwork ");
		int res = wifiManager.addNetwork(conf);
		//TkTrace.log(p_context,"[TkWifi/connectWifi] : add network = "+res);
		//TkTrace.log(p_context,"[TkWifi/connectWifi] : List ");
		List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
		for( WifiConfiguration i : list ) {
			//TkTrace.log(p_context,"[TkWifi/connectWifi] : i.SSID = "+i.SSID);
		    if(i.SSID != null && i.SSID.equals("\"" + p_wifiName + "\"")) {
		    	//TkTrace.log(p_context,"[TkWifi/connectWifi] : i.SSID = "+i.SSID);
		    	//TkTrace.log(p_context,"[TkWifi/connectWifi] : disconnect ");
				wifiManager.disconnect();
				//TkTrace.log(p_context,"[TkWifi/connectWifi] : enableNetwork ");
		        wifiManager.enableNetwork(i.networkId, true);
		        //TkTrace.log(p_context,"[TkWifi/connectWifi] : reconnect ");
		        //wifiManager.reassociate();
		        wifiManager.reconnect();               
		         return true;
		    }           
		 }
		
		return false;

	}
	
	/*
	// connectWifi 
	public boolean connectWifi(Context p_context, String p_wifiName,String p_wifiPassword) {
		
		WifiConfiguration conf = new WifiConfiguration();
		conf.SSID = "\"" + p_wifiName + "\"";   
		conf.wepKeys[0] = "\"" + p_wifiPassword + "\""; 
		//conf.SSID = p_wifiName;   
		//conf.wepKeys[0] = p_wifiPassword; 
		conf.wepTxKeyIndex = 0;
		conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
		conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		conf.allowedAuthAlgorithms.clear();
		conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
		conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		//conf.preSharedKey = p_wifiPassword;
		conf.preSharedKey = "\"".concat(p_wifiPassword).concat("\"");
		//conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

		WifiManager wifiManager = (WifiManager)p_context.getSystemService(Context.WIFI_SERVICE); 
		TkTrace.log(p_context,"[TkWifi/connectWifi] : addNetwork ");
		wifiManager.addNetwork(conf);
		TkTrace.log(p_context,"[TkWifi/connectWifi] : List ");
		List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
		for( WifiConfiguration i : list ) {
			//TkTrace.log(p_context,"[TkWifi/connectWifi] : i.SSID = "+i.SSID);
		    if(i.SSID != null && i.SSID.equals("\"" + p_wifiName + "\"")) {
		    	TkTrace.log(p_context,"[TkWifi/connectWifi] : i.SSID = "+i.SSID);
		    	TkTrace.log(p_context,"[TkWifi/connectWifi] : disconnect ");
				wifiManager.disconnect();
				TkTrace.log(p_context,"[TkWifi/connectWifi] : enableNetwork ");
		        wifiManager.enableNetwork(i.networkId, true);
		        TkTrace.log(p_context,"[TkWifi/connectWifi] : reconnect ");
		        //wifiManager.reassociate();
		        wifiManager.reconnect();               
		         return true;
		    }           
		 }
		
		return false;

	}*/
    
	/** scanWifi */
	/*public List<ScanResult> scanWifi(Context p_context) {
		TkTrace.log(p_context,"[TkAppManager/scanWifi()] : entree");
		processEnd = false;
		WifiManager wifiManager = (WifiManager) p_context.getSystemService(Context.WIFI_SERVICE);
		BroadcastReceiver wifiReceiver = new BroadcastReceiver() { // init your Receiver
            @Override
            public void onReceive(Context p_context, Intent p_intent) {
            	TkTrace.log(p_context,"[TkAppManager/scanWifi onReceive()] : entree");
            	WifiManager wifiManager =(WifiManager) p_context.getSystemService(Context.WIFI_SERVICE);
            	listWifi = wifiManager.getScanResults();
            	TkTrace.log(p_context,"[TkAppManager/scanWifi onReceive()] : nb wifi = "+listWifi.size());
            	
            	for(int i=0; i < listWifi.size(); ++i) {
            		//TkTrace.log(p_context, "  BSSID       =" + listWifi.get(i).BSSID);
            		//TkTrace.log(p_context, "  SSID        =" + listWifi.get(i).SSID);
            		//TkTrace.log(p_context, "  Capabilities=" + listWifi.get(i).capabilities);
            		//TkTrace.log(p_context, "  Frequency   =" + listWifi.get(i).frequency);
            		//TkTrace.log(p_context, "  Level       =" + listWifi.get(i).level);
            		//TkTrace.log(p_context, "---------------");
            	}
            	
            	processEnd = true;
            	return;
            }
        };
        p_context.registerReceiver(wifiReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        int processIndex=0;
        for(int i=0; i < 20; ++i) {
        	 try {
        		 Thread.sleep(10);
        		 processIndex++;
        		 TkTrace.log(p_context,"[TkAppManager/scanWifi()] : sleep ("+processIndex+")");
        	 } catch (InterruptedException e) {
        		 TkTrace.log(p_context,"[TkAppManager/scanWifi()] : sleep "+e.getMessage());
             }
        }
        
        TkTrace.log(p_context,"[TkAppManager/scanWifi()] : return ");
        return listWifi;
	}*/
	
	 @Override
     public void onReceive(Context p_context, Intent p_intent) {
		 //TkTrace.log(p_context,"[TkAppManager/onReceive()] : inherit ");
     }
	

}
