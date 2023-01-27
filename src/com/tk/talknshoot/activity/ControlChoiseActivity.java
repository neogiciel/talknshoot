package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkTrace;
import com.tk.talknshoot.util.TkUser;
import com.tk.talknshoot.util.TkWifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ControlChoiseActivity extends Activity {
	
	private Context context;
	//private String wifiCameraConfigurated;
	//private String wifiCameraActual;
	//private boolean isWifiCameraConfigurated=true;
	//private boolean isWifiCameraEqual=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controlchoise);
		
		  /* default context */
        context = this;
        
		/* init */
		//init();
        displayInit();
		
		/*
		 * bouton btn_choise_manual
		 */
		 final Button btnManual = (Button) findViewById(R.id.btn_choise_manual);
		 	btnManual.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 Intent intent = new Intent(ControlChoiseActivity.this, ControlManualActivity.class);
		    	 startActivity(intent);
			
			}
		});
		/*if((isWifiCameraConfigurated==false)||(isWifiCameraEqual==false)){
			btnManual.setEnabled(false);
			btnManual.setAlpha(.5f);
			btnManual.setClickable(false);
		 }*/
			
		/*
		 * bouton btn_choise_manual
		 */
		 final Button btnVoice = (Button) findViewById(R.id.btn_choise_voice);
		 	btnVoice.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 Intent intent = new Intent(ControlChoiseActivity.this, ControlVoiceActivity.class);
		    	startActivity(intent);
		    
			}
		});
		
		/* 	
		if((isWifiCameraConfigurated==false)||(isWifiCameraEqual==false)){
			 btnVoice.setEnabled(false);
			 btnVoice.setAlpha(.5f);
			 btnVoice.setClickable(false);
		 }*/
		 	
	 	/*
		 * bouton btn_choise_refresh
		 */
		 /*final Button btnRefresh = (Button) findViewById(R.id.btn_choise_refresh);
		 btnRefresh.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 init();
			}
		});*/

	
	}
	/* init */
	/*void init(){
		try{
			TkUser tkUser = TkAppManager.getTkUser();
			TkWifi tkWifi = new TkWifi();
			wifiCameraConfigurated = "\""+tkUser.camerawifiname+"\"";
			wifiCameraActual = tkWifi.getWifiStatus(this);
			TextView txtCamera = (TextView) findViewById(R.id.controlchoise_txt_camera);
			if(tkUser.camerawifiname.equals("empty")){
				txtCamera.setText((String)this.getString(R.string.controlchoise_txt_camera_empty));
				isWifiCameraConfigurated = false;
			}else{
				txtCamera.setText(wifiCameraConfigurated);
			}
			
			TextView txtActualCamera = (TextView) findViewById(R.id.controlchoise_txt_actualcamera);
			txtActualCamera.setText(wifiCameraActual);
			
			// test if wifi ok
			if(wifiCameraActual.equals(wifiCameraConfigurated) == true){
				isWifiCameraEqual = true;
				ImageView img= (ImageView) findViewById(R.id.controlchoise_img_wifi);
				img.setImageResource(R.drawable.pix_control_ok);
				
			}else{
				isWifiCameraEqual = false;
				ImageView img= (ImageView) findViewById(R.id.controlchoise_img_wifi);
				img.setImageResource(R.drawable.pix_control_alert);
			}
			
		} catch (Exception e) {
			TkTrace.log(context,"[ControlChoiseActivity/init()] : Exception="+e);
		}
	}*/
	
	
	void displayInit(){
		TkUser tkUser = TkAppManager.getTkUser();
		String wifiCameraConfigurated = tkUser.camerawifiname;
		String cameraTypeConfigurated = tkUser.cameratype;
		//Set name
		TextView txtCamera = (TextView) findViewById(R.id.controlchoise_txt_camera);
		txtCamera.setText(wifiCameraConfigurated);
		TextView txtActualCamera = (TextView) findViewById(R.id.controlchoise_txt_actualcamera);
		txtActualCamera.setText(wifiCameraConfigurated);
		
		//Set camera Type
		TextView txtCameraType = (TextView) findViewById(R.id.controlchoise_txt_typecamera);
		txtCameraType.setText(cameraTypeConfigurated);
		
		//Display		
		String camera = tkUser.cameratype;
		ImageView img= (ImageView) findViewById(R.id.controlchoise_img_wifi);
		
		if(camera.equals("HERO6 BLACK")){
			//HERO6 BLACK
			img.setImageResource(R.drawable.camera_list_hero6black);	
		}else if(camera.equals("HERO5 BLACK")){
			//HERO5 BLACK
			img.setImageResource(R.drawable.camera_list_hero5black);	
		}else if(camera.equals("HERO5 SESSION")){
			//HERO5 SESSION
			img.setImageResource(R.drawable.camera_list_hero5session);	
		}else if(camera.equals("HERO SESSION")){
			//HERO SESSION
			img.setImageResource(R.drawable.camera_list_herosession);
		}else if(camera.equals("HERO+")){
			//HERO+
			img.setImageResource(R.drawable.camera_list_heroplus);	
		}else if(camera.equals("HERO4")){
			//HERO4
			img.setImageResource(R.drawable.camera_list_hero4);
		}else{
			//HERO3+/3/2
			img.setImageResource(R.drawable.camera_list_hero2);
		}

	}
}
