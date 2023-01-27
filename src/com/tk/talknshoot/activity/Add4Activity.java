package com.tk.talknshoot.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tk.talknshoot.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class Add4Activity extends Activity {

	/* parameter */
	Context context;
	String camera_selected;
	ListView lv;
	BroadcastReceiver wifiReceiver;
	public static int [] listImages;
    public static String [] listNameList;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add4);
	
		/* context */
		context = this;
		String applicatiion_debug = getResources().getString(R.string.application_debug);
		
		/* get bundle */
		Bundle bundle=getIntent().getExtras();
		camera_selected = bundle.getString("camera_selected");
	    //TkTrace.log(context, "camera_selected = "+camera_selected);
		/* retreive  wifi */
		getWifiNetworksList();
		
		/* retreive  wifi */
		/*if(applicatiion_debug.equals("dev")){
			
			 final Button btnStart = (Button) findViewById(R.id.btn_start);
			 btnStart.setOnClickListener(new OnClickListener() {
			     public void onClick(View actuelView) {
			    	Bundle bundle = new Bundle();
		            bundle.putString("camera_selected", camera_selected);
		            bundle.putString("wifi_selected", "neogopro");
		            Intent intent = new Intent(Add4Activity.this, Add5Activity.class);
				    intent.putExtras(bundle);
				    startActivity(intent);
				    unregisterReceiver(wifiReceiver);
				    finish();
			     }
			});
		}else{
			
			
		}*/
		
	}
	
	/* getWifiNetworksList */
	private void getWifiNetworksList(){      
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        final WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        
        wifiReceiver = new BroadcastReceiver(){
 
                  @Override
                    public void onReceive(Context p_context, Intent p_intent) {
                	  
                	  /* getlist of wifi*/
                	  List<ScanResult> scanList = wifiManager.getScanResults();
                	  //ArrayList<String> list = new ArrayList<String>();
                	  listNameList = new String[scanList.size()];
                	  listImages = new int[scanList.size()];
                      for(int i = 0; i < scanList.size(); i++){
                        	listNameList[i] = scanList.get(i).SSID;
                        	listImages[i] = R.drawable.wifi_list;
                      }
                      //TkTrace.log(context, "nbwifi = "+ scanList.size());
                      	lv=(ListView) findViewById(R.id.add4_list);
                        final CustomListAdaptater  adapter = new CustomListAdaptater("Add4Activity",context,listNameList,listImages);
                     	lv.setAdapter(adapter);
                     	// TkTrace.log(context, "apres setAdapter");
                	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                	    	 
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                            	
                            	Bundle bundle = new Bundle();
            		            bundle.putString("camera_selected", camera_selected);
            		            bundle.putString("wifi_selected", listNameList[position]);
            		            bundle.putString("wifi_password", "none");
            		            
            		            // set wifipassword
            		            //"HERO6 BLACK","HERO5 BLACK","HERO5 SESSION","HERO SESSION","HERO+","HERO4","HERO3+/3/2"};
            		            Intent intent;
           			    	 	if(!camera_selected.equals("HERO6 BLACK")){
           			    	 		intent = new Intent(Add4Activity.this, Add4PasswordActivity.class);
            				    }else if(!camera_selected.equals("HERO5 BLACK")){
            				    	intent = new Intent(Add4Activity.this, Add4PasswordActivity.class);
           			    	 	}else {
           			    	 		intent = new Intent(Add4Activity.this, Add5Activity.class);
           			    	 	}
            		            
            		            intent.putExtras(bundle);
            				    startActivity(intent);
            				    unregisterReceiver(wifiReceiver);
            				    finish();
                 		    	
                            }
                        });
                	    
                  } 
                };      
        
        registerReceiver(wifiReceiver,filter);
        wifiManager.startScan();
    }
}