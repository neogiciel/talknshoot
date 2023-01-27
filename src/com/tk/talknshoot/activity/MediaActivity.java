package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkUser;
import com.tk.talknshoot.util.TkWifi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MediaActivity extends Activity {
	
	String wifiCameraConfigurated;
	String wifiCameraActual;
	boolean isWifiCameraConfigurated=true;
	boolean isWifiCameraEqual=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media);
		
		// regarde si la camera est connecte  
		init();
		
		// bouton 
		final Button btnStart = (Button) findViewById(R.id.btn_media_premium);
		btnStart.setOnClickListener(new OnClickListener() {
	     public void onClick(View actuelView) {
	    	 
	    	 if((isWifiCameraConfigurated==false)||(isWifiCameraEqual==false)){
	    		Intent intent = new Intent(MediaActivity.this, MediaErrorActivity.class);
	    		startActivity(intent);
	    		MediaActivity.this.finish();
	    	 }else{
	    		 Intent intent = new Intent(MediaActivity.this, Media1Activity.class);
		    	 startActivity(intent);
	    	 }
	    	 
	     }
		});	
		
	}
	
	/* init */
	void init(){
		TkUser tkUser = TkAppManager.getTkUser();
		TkWifi tkWifi = new TkWifi();
		wifiCameraConfigurated = "\""+tkUser.camerawifiname+"\"";
		wifiCameraActual = tkWifi.getWifiConnected(this);
		if(tkUser.camerawifiname.equals("empty")){
			isWifiCameraConfigurated = false;
		}
		if(wifiCameraActual.equals(wifiCameraConfigurated) == true){
			isWifiCameraEqual = true;
			
		}else{
			isWifiCameraEqual = false;
		}
	}
}
