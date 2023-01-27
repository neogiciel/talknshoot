package com.tk.talknshoot.activity;

import java.io.File;
import java.util.List;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkGoproCommand;
import com.tk.talknshoot.util.TkTrace;
import com.tk.talknshoot.util.TkUser;
import com.tk.talknshoot.util.TkWifi;

import edu.cmu.pocketsphinx.Assets;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ControlConnectWifiActivity extends Activity {
	
	private Context context;
	private boolean isCameraConfigurated;
	private String wifiNameCameraConfigurated;
	private String wifiPasswordCameraConfigurated;
	private String wifiCameraActual;
	private boolean isWifiCameraConfigurated=false;
	private boolean isWifiCameraEqual=false;
	private Button btnNext;
	private Button btnRefresh;
	
	private BroadcastReceiver wifiReceiver;
	private ProgressDialog progress;
	//AsyncTask<Void, Void, Exception> initTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* default context */
        context = this;
        
		//Teste si Camera est configure
		TkUser tkUser = TkAppManager.getTkUser();
		if(tkUser.camerawifiname.equals("empty")) {
			//Aucune camera active  
			isCameraConfigurated = false;
			//Acces page erreur
			Intent intent = new Intent(ControlConnectWifiActivity.this, ControlNoCameraActivity.class);
	    	startActivity(intent);
	    	finish();
		}else {
			//camera active
			isCameraConfigurated = true; 
		}
		
		
		if(isCameraConfigurated==true) {
			
		
			//initialisation camera
			try {
				//Progress bar
				progress = new ProgressDialog(this);
				progress.setTitle(null);
				progress.setCancelable(false);
				progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progress.setMessage((String)this.getString(R.string.controlconnectwifi_txt_progress));
				progress.show();
			}catch (Exception e) {
				TkTrace.log(context,"[ControlConnectWifiActivity/onCreate()] : Exception="+e.getMessage());
			};    	
		        
				//progress = new ProgressDialog(context);
				//progress.setTitle(null);
				//progress.setContentView(R.layout.custom_progress);
				//progress.setCancelable(false);
				//progress.show();
		    	
				setContentView(R.layout.activity_controlconnectwifi);
				
				
		        /* initialisation des boutons*/
		        btnNext = (Button) findViewById(R.id.btn_controlconnectwifi_next);
		        btnRefresh = (Button) findViewById(R.id.btn_controlconnectwifi_refresh);
		        
		        
				/* init */
		        new Thread(new Runnable() {public void run() {//debut threard
		        	initConnectCamera();
		        	//progress.dismiss();
			 	};}).start();//fin threard
				
		        
		        
				//bouton btn_next
				btnNext = (Button) findViewById(R.id.btn_controlconnectwifi_next);
				btnNext.setOnClickListener(new OnClickListener() {
				     public void onClick(View actuelView) {
				    	 Intent intent = new Intent(ControlConnectWifiActivity.this, ControlChoiseActivity.class);
				    	 startActivity(intent);
				    	 finish();
					
					}
				});
				
				/*if((isWifiCameraConfigurated==false)||(isWifiCameraEqual==false)){
					btnNext.setEnabled(false);
					btnNext.setAlpha(.5f);
					btnNext.setClickable(false);
				 }*/
				
				 //bouton btn_choise_refresh
				 btnRefresh = (Button) findViewById(R.id.btn_controlconnectwifi_refresh);
				 btnRefresh.setOnClickListener(new OnClickListener() {
				     public void onClick(View actuelView) {
				    	 
				    	 /* init */
					     new Thread(new Runnable() {public void run() {//debut threard
					    	TkTrace.log(context,"[ControlConnectWifiActivity/btnRefresh()] : Debut");
				    	 	progress.show();
				    	 	initConnectCamera();
				    	 	progress.dismiss();
				    	 	TkTrace.log(context,"[ControlConnectWifiActivity/btnRefresh()] : fin");
						 };}).start();//fin threard
						
					}
				});
			
			}
	}
	
	
	
	/* getWifiNetworksList */
	void initConnectCamera(){
		try {
			TkTrace.log(context,"[ControlConnectWifiActivity/initConnectCamera()] : Debut");
		
			/* test if connected */
			TkUser tkUser = TkAppManager.getTkUser();
			
			//initialisation camera
			wifiNameCameraConfigurated = "\""+tkUser.camerawifiname+"\"";
			wifiPasswordCameraConfigurated = "\""+tkUser.camerawifipassword+"\"";
			TkTrace.log(context,"[ControlConnectWifiActivity/initConnectCamera()] : wifiNameCameraConfigurated = "+wifiNameCameraConfigurated);
			TkTrace.log(context,"[ControlConnectWifiActivity/initConnectCamera()] : wifiNameCameraConfigurated = "+wifiPasswordCameraConfigurated);
			
			//temp
			wifiNameCameraConfigurated = tkUser.camerawifiname;
			wifiPasswordCameraConfigurated = tkUser.camerawifipassword;
			//wifiNameCameraConfigurated = "neogiciel";
			//wifiPasswordCameraConfigurated = "matthias88";
	
			//Recuperation du wifi actuel
			TkWifi tkWifi = new TkWifi();
			//wifiCameraActual = tkWifi.getWifiStatus(this);
			wifiCameraActual = tkWifi.getWifiConnected(context);
			
			TkTrace.log(context,"[ControlConnectWifiActivity/initConnectCamera()] wifiNameCameraConfigurated =("+wifiNameCameraConfigurated+")");
			TkTrace.log(context,"[ControlConnectWifiActivity/initConnectCamera()] wifiCameraActual =("+wifiCameraActual+")");
			
			if(tkUser.camerawifiname.equals("empty")){
				TkTrace.log(context,"[ControlConnectWifiActivity/initConnectCamera()] : aucune camera connecte");
				isWifiCameraConfigurated = false;
				setNoCameraInstall();
			}else{
				isWifiCameraConfigurated = true;
				/* test if wifi ok*/
				if(wifiCameraActual.equals(wifiNameCameraConfigurated) == true){
					TkTrace.log(context,"[ControlConnectWifiActivity/initConnectCamera()] : camera correctement connecte");
					isWifiCameraEqual = true;
					setCameraOk();
				}else{
					isWifiCameraEqual = false;
					TkTrace.log(context,"[ControlConnectWifiActivity/initConnectCamera()] : connectWifiNetworksList");
					connectWifiNetworksList();
				}
			}
			TkTrace.log(context,"[ControlConnectWifiActivity/initConnectCamera()] : fin");
			
		}catch(RuntimeException ex) {
    		TkTrace.log(context,"[ControlConnectWifiActivity/initConnectCamera()] : RuntimeException="+ex.getMessage());
    		setCameraFalse();
    	}catch (Exception e) {
			TkTrace.log(context,"[ControlConnectWifiActivity/initConnectCamera()] : Exception="+e.getMessage());
			setCameraFalse();
		}  		
	}
	
	/* getWifiNetworksList */
	private void connectWifiNetworksList(){      
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        final WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        
        wifiReceiver = new BroadcastReceiver(){
 
                  @Override
                    public void onReceive(Context p_context, Intent p_intent) {
                   	try{
                   	 boolean findWifi = false;	
                   		
					  /* getlist of wifi*/
                	  List<ScanResult> scanList = wifiManager.getScanResults();
                	  //ArrayList<String> list = new ArrayList<String>();
                	  //TkTrace.log(context,"[ControlConnectWifiActivity/connectWifiNetworksList] : Nb wifi actif ="+ scanList.size());
                	  for(int i = 0; i < scanList.size(); i++){
                		  //TkTrace.log(context,"[ControlConnectWifiActivity/connectWifiNetworksList] : ************** i ="+ i);
                        	String listWifiName = scanList.get(i).SSID;
                        	//TkTrace.log(context,"[ControlConnectWifiActivity/connectWifiNetworksList] : wifi scanne actif ="+listWifiName);
                        	
                        	if(listWifiName.equals(wifiNameCameraConfigurated) == true){
                        		TkTrace.log(context,"[ControlConnectWifiActivity/connectWifiNetworksList] : Esseye de se connecter ");
                        		TkWifi tkWifi = new TkWifi();
                        		tkWifi.connectWifi(context,wifiNameCameraConfigurated,wifiPasswordCameraConfigurated);
                        		
                        		for(int j=0;j<10;j++){
									//wifiCameraActual = tkWifi.getWifiStatus(context);
                        			wifiCameraActual = tkWifi.getWifiConnected(context);
									//TkTrace.log(context,"[ControlConnectWifiActivity/connectWifiNetworksList()] : wifiNameCameraConfigurated ="+wifiNameCameraConfigurated);
									//TkTrace.log(context,"[ControlConnectWifiActivity/connectWifiNetworksList()] : getWifiStatus ="+wifiCameraActual);
									if(wifiCameraActual.equals(wifiNameCameraConfigurated) == true){
										//TkTrace.log(context,"[ControlConnectWifiActivity/connectWifiNetworksList()] : setCameraOk");
										//camera False
										setCameraOk();
										findWifi = true;
										break;
									}
									
									if(findWifi == true)
										break;
									Thread.sleep(1000);   
								}
							}
                      }
                	  
                	/* if(findWifi == false){
                	  //TkTrace.log(context,"[ControlConnectWifiActivity/endwifi] : connect auti ");
                      TkWifi tkWifi = new TkWifi();
                      tkWifi.connectWifi(context,wifiNameCameraConfigurated,wifiPasswordCameraConfigurated);
                    		
                      	for(int j=0;j<10;j++){
							//wifiCameraActual = tkWifi.getWifiStatus(context);
							wifiCameraActual = tkWifi.getWifiConnected(context);
							if(wifiCameraActual.length()>3);
								wifiCameraActual = wifiCameraActual.substring(1,wifiCameraActual.length()-1);
								//TkTrace.log(context,"[ControlConnectWifiActivity/init()] : si false wifiNameCameraConfigurated ="+wifiNameCameraConfigurated);
								//TkTrace.log(context,"[ControlConnectWifiActivity/init()] : si false Waiting wifi ="+wifiCameraActual);
								if(wifiCameraActual.equals(wifiNameCameraConfigurated) == true){
									//TkTrace.log(context,"[ControlConnectWifiActivity/connectWifiNetworksList()] : si false setCameraOk");
									//camera False
									setCameraOk();
									findWifi = true;
									break;
							}
							Thread.sleep(1000);   
                      	}
                      	//camera False
                      	if(findWifi == false)
                      		setCameraFalse();
                	 }*/	
                    
                	  if(findWifi == false){
                			setCameraFalse();
                	  }
                	}catch(RuntimeException ex) {
                		TkTrace.log(context,"[ControlConnectWifiActivity/connectWifiNetworksList()] : RuntimeException="+ex.getMessage());
                		setCameraFalse();
                	}catch (Exception e) {
						TkTrace.log(context,"[ControlConnectWifiActivity/connectWifiNetworksList()] : Exception="+e.getMessage());
						setCameraFalse();
					}  		
                  } 
                };      
        
        registerReceiver(wifiReceiver,filter);
        wifiManager.startScan();
    }
	
	
	/* setNoCameraInstall */
	void setNoCameraInstall(){
		//camera configurated
		TextView txtCamera = (TextView) findViewById(R.id.controlconnectwifi_txt_camera);
		txtCamera.setText((String)this.getString(R.string.controlconnectwifi_txt_camera_empty));
		//camera
		TextView txtActualCamera = (TextView) findViewById(R.id.controlconnectwifi_txt_actualcamera);
		txtActualCamera.setText(wifiCameraActual);
		//img
		ImageView img= (ImageView) findViewById(R.id.controlconnectwifi_img_wifi);
		img.setImageResource(R.drawable.pix_control_alert);
		//bouton
		btnNext.setEnabled(false);
		btnNext.setAlpha(.5f);
		btnNext.setClickable(false);
		//progress
		progress.dismiss();
	}
	
	/* setCameraOk */
	void setCameraOk(){
		
		//camera configurated
		/*TextView txtCamera = (TextView) findViewById(R.id.controlconnectwifi_txt_camera);
		txtCamera.setText(wifiNameCameraConfigurated);
		//camera
		TextView txtActualCamera = (TextView) findViewById(R.id.controlconnectwifi_txt_actualcamera);
		txtActualCamera.setText(wifiCameraActual);
		//img
		ImageView img= (ImageView) findViewById(R.id.controlconnectwifi_img_wifi);
		img.setImageResource(R.drawable.pix_control_ok);
		//bouton
		btnNext.setEnabled(true);
		btnNext.setAlpha(1f);
		btnNext.setClickable(true);*/
		
		
		//Bascule vers l ecran suivant
		Intent intent = new Intent(ControlConnectWifiActivity.this, ControlChoiseActivity.class);
		startActivity(intent);
   	 	
		//progress
		progress.dismiss();
				
		finish();
	}
	
	/* setCameraFalse */
	void setCameraFalse(){
		//camera configurated
		TextView txtCamera = (TextView) findViewById(R.id.controlconnectwifi_txt_camera);
		txtCamera.setText(wifiNameCameraConfigurated);
		//camera
		TextView txtActualCamera = (TextView) findViewById(R.id.controlconnectwifi_txt_actualcamera);
		if(wifiCameraActual.equals("on")) {
			txtActualCamera.setText((String)context.getString(R.string.controlconnectwifi_txt_no_wifi));
		}else {
			txtActualCamera.setText(wifiCameraActual);
		}
		
		//img
		ImageView img= (ImageView) findViewById(R.id.controlconnectwifi_img_wifi);
		img.setImageResource(R.drawable.pix_control_alert);
		//bouton
		btnNext.setEnabled(false);
		btnNext.setAlpha(.5f);
		btnNext.setClickable(false);
		//progress
		progress.dismiss();
	}
	
	
}
