package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkCameraInfo;
import com.tk.talknshoot.util.TkIniFile;
import com.tk.talknshoot.util.TkTrace;
import com.tk.talknshoot.util.TkUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Add5Activity extends Activity {

	/* parameter */
	Context context;
	String camera_selected;
	String camera_brand;
	String wifi_selected;
	String wifi_password;
	
	//EditText edit_txt_wifi_password;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add5);
		
		/* context */
		context = this;
		
		/* get bundle */
		Bundle bundle=getIntent().getExtras();
		camera_selected = bundle.getString("camera_selected");
		camera_brand = "gopro";
		wifi_selected = bundle.getString("wifi_selected");
		wifi_password = bundle.getString("wifi_password");
		//TkTrace.log(context, "camera_selected = "+camera_selected);
		//TkTrace.log(context, "camera_brand = "+camera_brand);
		//TkTrace.log(context, "wifi_selected = "+wifi_selected);
		//TkTrace.log(context, "wifi_password = "+wifi_password);
		
		/* update textview */
	    TextView txt_camera = (TextView) findViewById(R.id.add_txt_2);
	    txt_camera.setText(camera_selected);
	    TextView txt_wifi = (TextView) findViewById(R.id.add_txt_3);
	    txt_wifi.setText(wifi_selected);
	    TextView txt_password = (TextView) findViewById(R.id.add_txt_4);
	    txt_password.setText(wifi_password);
	  
	    if(txt_password.equals("none")){
	    	txt_password.setVisibility(View.GONE);
	    }
	    /* disable texview*/
	    /*if(!camera_selected.equals("HERO3+/3/2")){
	    	TextView txt_setwifi = (TextView) findViewById(R.id.add5_txt_subtitre_4);
	    	txt_setwifi.setVisibility(View.GONE);
	    	edit_txt_wifi_password = (EditText) findViewById(R.id.add_edittxt_4);
	    	edit_txt_wifi_password.setVisibility(View.GONE);
	    }else{
	    	edit_txt_wifi_password = (EditText) findViewById(R.id.add_edittxt_4);
	    }*/
	    
		/*
		 * bouton start
		 */
		 final Button btnStart = (Button) findViewById(R.id.btn_start);
		 btnStart.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	try{ 
			    	// user data
			    	TkUser tkUser = TkAppManager.getTkUser();    	 
			    	tkUser.cameratype = camera_selected;
			    	tkUser.camerabrand = camera_brand;
			    	tkUser.camerawifiname = wifi_selected;
			    	tkUser.camerawifipassword = wifi_password;

			    	// user data
			    	TkIniFile tkIniFile = new TkIniFile();
			    	boolean isOpen = tkIniFile.load();
					if(isOpen == true){
						tkIniFile.set("cameratype",tkUser.cameratype);
						tkIniFile.set("camerabrand",tkUser.camerabrand);
						tkIniFile.set("camerawifiname",tkUser.camerawifiname);
						tkIniFile.set("camerawifipassword",tkUser.camerawifipassword);
						tkIniFile.store();
					}
			    	// camera info 
			    	TkCameraInfo tkCameraInfo = TkAppManager.getTkCameraInfo();
			    	tkCameraInfo.setCameraType(tkUser.cameratype);
			    	tkCameraInfo.setCameraBrand(tkUser.camerabrand);
			    	tkCameraInfo.setWifiName(tkUser.camerawifiname);
			    	tkCameraInfo.setWifiPassword(tkUser.camerawifipassword);
			    	
		    	
				} catch (Exception e) {
					TkTrace.log(context,"Exception: Commande"+e.getMessage());
				}
			
		    	finish();
		     }
		});
	}
	
	
}
