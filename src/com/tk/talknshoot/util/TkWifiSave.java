package com.tk.talknshoot.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;


public class TkWifiSave extends BroadcastReceiver{
	
	/** parameter */
	public String wifiname;
	public String wifiip;
	private List<ScanResult> listWifi;
	private boolean processEnd;
	/** constructor */
	public TkWifiSave(){
		
	}

	/** constructor */
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
	    return wifiname;
	}
	
	
	/** connectWifi */
	public boolean connectWifi(Context p_context, String p_wifiName,String p_wifiPassword) {
		
		WifiManager wifiManager = (WifiManager)p_context.getSystemService(Context.WIFI_SERVICE);
		WifiConfiguration conf = new WifiConfiguration();
		//conf.SSID = p_wifiName;   
		//conf.wepKeys[0] = p_wifiPassword;
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
	    int res = wifiManager.addNetwork(conf); 
	    wifiManager.disconnect();
	    TkTrace.log(p_context,"[TkWifi/connectWifi] : add network = "+res); 
	    boolean b = wifiManager.enableNetwork(res, true); 
	    TkTrace.log(p_context,"[TkWifi/connectWifi] : enableNetwork = "+b);
	    boolean c = wifiManager.reconnect();
	    TkTrace.log(p_context,"[TkWifi/connectWifi] : reconnect = "+c);
		return c;
		
		/*List<ScanResult> list = wifiManager.getScanResults();
		for(int i = 0; i < list.size(); i++){
			//TkTrace.log(p_context,"[TkWifi/connectWifi] : i.SSID = "+i.SSID);
		    if(list.get(i).SSID != null && list.get(i).equals("\"" + p_wifiName + "\"")) {
		    	TkTrace.log(p_context,"[TkWifi/connectWifi] : i.SSID = "+list.get(i).SSID);
		    	TkTrace.log(p_context,"[TkWifi/connectWifi] : Return true");
		    	return true;
		    }           
		}
		return false;*/
		
		/*TkTrace.log(p_context,"[TkWifi/connectWifi] : List ");
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
		        wifiManager.reconnect();               
		        return true;
		    }           
		}*/
		
		//return false;

	}
    
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
