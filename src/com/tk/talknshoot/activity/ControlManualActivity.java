package com.tk.talknshoot.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkCameraInfo;
import com.tk.talknshoot.util.TkGoproCommand;
import com.tk.talknshoot.util.TkTrace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ControlManualActivity extends Activity {

	/*
	 * params
	 */
	Context context;
	TextToSpeech tts;
	Locale ttsLanguage = Locale.FRANCE;
	TkCameraInfo tkCameraInfo; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controlmanual);
		
		/*
		 * initialisation application
		 */
		context = this;
        TkGoproCommand.getInstance().setContext(this);
        tkCameraInfo = TkAppManager.getTkCameraInfo();
        
        /*
		 * Par defaut mode video
		 */
   	 	new Thread(new Runnable() {public void run() {//debut threard
   	 		String command = TkGoproCommand.getInstance().executeCommand("MODE_VIDEO");
   	 		tkCameraInfo.setCurrentMode("MODE_VIDEO");
	 	};}).start();//fin threard
		
		/*
		 * initialisation son
		 */
		tts=new TextToSpeech(ControlManualActivity.this,new TextToSpeech.OnInitListener() {
	            @Override
	            public void onInit(int status) {
	                if(status!=TextToSpeech.ERROR) {
	                    tts.setLanguage(ttsLanguage);
	                }
	            }
	        });
	    
		/*
		 * bouton start
		 */
		 final Button btnStart = (Button) findViewById(R.id.btn_manual_start);
		 btnStart.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 new Thread(new Runnable() {public void run() {//debut threard
			     //Exectution de la commande
			     if(tkCameraInfo.getCurrentMode().equals("MODE_VIDEO")){
					 String command = TkGoproCommand.getInstance().executeCommand("START_RECORD_VIDEO");
					 if(command != null){
						//tts.speak("START MOVIE",TextToSpeech.QUEUE_FLUSH,null,null);
						String speak = (String)context.getString(R.string.START_RECORD_VIDEO);
	   					//tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null,null);
	   					tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
	   					
						 //Mise a jour des actions
						tkCameraInfo.setCurrentAction("START_RECORD_VIDEO");
					 	tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
					 }
				 }else{
					 String command = TkGoproCommand.getInstance().executeCommand("MODE_VIDEO");
					 if(command != null){
						 tkCameraInfo.setCurrentMode("MODE_VIDEO");
						 command = TkGoproCommand.getInstance().executeCommand("START_RECORD_VIDEO");
						 if(command != null){
							//tts.speak("START MOVIE",TextToSpeech.QUEUE_FLUSH,null,null);
							String speak = (String)context.getString(R.string.START_RECORD_VIDEO);
			   				tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
			   				//Mise a jour des actions
						 	tkCameraInfo.setCurrentAction("START_RECORD_VIDEO");
					 		tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
						 }
					 }
				 }
		     };}).start();//fin threard
		     }
		});

		/*
		 * bouton stop
		 */
		 final Button btnStop = (Button) findViewById(R.id.btn_manual_stop);
		 	btnStop.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 new Thread(new Runnable() {public void run() {//debut threard
		    	 String command = TkGoproCommand.getInstance().executeCommand("STOP_RECORD_VIDEO");
				 if(command != null){
					//tts.speak("MOVIE STOP",TextToSpeech.QUEUE_FLUSH,null,null);
					String speak = (String)context.getString(R.string.STOP_RECORD_VIDEO);
	   				tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
	   			 	//Mise a jour des actions
				 	tkCameraInfo.setCurrentAction("STOP_RECORD_VIDEO");
			 		tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
				 }
		     	};}).start();//fin threard
		     }
		});

	 	/*
		 * bouton prendre photo
		 */
		 final Button btnTakePhoto = (Button) findViewById(R.id.btn_manual_take_photo);
		 btnTakePhoto.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 new Thread(new Runnable() {public void run() {//debut threard
		    	 if(tkCameraInfo.getCurrentMode().equals("MODE_PHOTO")){
					 String command = TkGoproCommand.getInstance().executeCommand("TAKE_PICTURE");
					 if(command != null){
						//tts.speak("START PHOTO",TextToSpeech.QUEUE_FLUSH,null,null);
						String speak = (String)context.getString(R.string.TAKE_PICTURE);
			   			tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
			   			//Mise a jour des actions
						tkCameraInfo.setCurrentAction("TAKE_PICTURE");
					 	tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
					 }
					 
		    	 }else if(tkCameraInfo.getCurrentMode().equals("MODE_BURST")){
		    		 String command = TkGoproCommand.getInstance().executeCommand("RESET_MODE_BURST");
					 if(command != null){
						 tkCameraInfo.setCurrentMode("MODE_PHOTO");
						 command = TkGoproCommand.getInstance().executeCommand("TAKE_PICTURE");
						 if(command != null){
							//tts.speak("START PHOTO",TextToSpeech.QUEUE_FLUSH,null,null);
							String speak = (String)context.getString(R.string.TAKE_PICTURE);
					   		tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
						 	//Mise a jour des actions
						 	tkCameraInfo.setCurrentAction("TAKE_PICTURE");
					 		tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
						 }
					 }
				 }else{
					 String command = TkGoproCommand.getInstance().executeCommand("MODE_PHOTO");
					 if(command != null){
						 tkCameraInfo.setCurrentMode("MODE_PHOTO");
						 command = TkGoproCommand.getInstance().executeCommand("TAKE_PICTURE");
						 if(command != null){
							//tts.speak("START PHOTO",TextToSpeech.QUEUE_FLUSH,null,null);
							String speak = (String)context.getString(R.string.TAKE_PICTURE);
					   		tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
						 	//Mise a jour des actions
						 	tkCameraInfo.setCurrentAction("TAKE_PICTURE");
					 		tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
						 }
					 }
				 }
		     	};}).start();//fin threard
			  }
		});
		 
		/*
		 * bouton burst photo
		 */
		 final Button btnBurstPhoto = (Button) findViewById(R.id.btn_manual_burst_photo);
		 btnBurstPhoto.setOnClickListener(new OnClickListener() {
			     public void onClick(View actuelView) {
			    	 
			    new Thread(new Runnable() {public void run() {//debut threard
			    	 if(tkCameraInfo.getCurrentMode().equals("MODE_BURST")){
						 String command = TkGoproCommand.getInstance().executeCommand("BURST_PICTURE");
						 if(command != null){
							//tts.speak("START PHOTO",TextToSpeech.QUEUE_FLUSH,null,null);
							String speak = (String)context.getString(R.string.BURST_PICTURE);
				   			tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
				   			//Mise a jour des actions
							tkCameraInfo.setCurrentAction("BURST_PICTURE");
						 	tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
						 }
					 }else{
						 String command = TkGoproCommand.getInstance().executeCommand("MODE_BURST");
						 if(command != null){
							 tkCameraInfo.setCurrentMode("MODE_BURST");
							 command = TkGoproCommand.getInstance().executeCommand("BURST_PICTURE");
							 if(command != null){
								//tts.speak("START PHOTO",TextToSpeech.QUEUE_FLUSH,null,null);
								String speak = (String)context.getString(R.string.BURST_PICTURE);
						   		tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
							 	//Mise a jour des actions
							 	tkCameraInfo.setCurrentAction("BURST_PICTURE");
						 		tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
							 }
						 }
					 }
			    	 
			     };}).start();//fin threard
		 
				    }
			});
		
		 /*
		 * bouton camera allumer
		 */
		 final Button btnCameraOn = (Button) findViewById(R.id.btn_manual_camera_on);
		 btnCameraOn.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 
		    	  new Thread(new Runnable() {
		    		    public void run() {
		    		    	String command = TkGoproCommand.getInstance().executeCommand("CAMERA_ON");
							if(command != null){
								//tts.speak("MOVIE STOP",TextToSpeech.QUEUE_FLUSH,null,null);
								String speak = (String)context.getString(R.string.CAMERA_ON);
				   				tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
				   			 	//Mise a jour des actions
							 	tkCameraInfo.setCurrentAction("CAMERA_ON");
						 		tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
							}
		    		      };
		    		    }
		    		  ).start();
			     }
			});
		 
		 btnCameraOn.setEnabled(false);
		 btnCameraOn.setAlpha(.5f);
		 btnCameraOn.setClickable(false);
		 
		 /*
		 * bouton camera allumer
		 */
		 final Button btnCameraOff = (Button) findViewById(R.id.btn_manual_camera_off);
		 	btnCameraOff.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	  		new Thread(new Runnable() {public void run() {
		    		    	String command = TkGoproCommand.getInstance().executeCommand("CAMERA_OFF");
		    		    	if(command != null){
		    		    		//tts.speak("MOVIE STOP",TextToSpeech.QUEUE_FLUSH,null,null);
		    		    		String speak = (String)context.getString(R.string.CAMERA_OFF);
		    		    		tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
			   			 		//Mise a jour des actions
		    		    		tkCameraInfo.setCurrentAction("CAMERA_OFF");
		    		    		tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
		    		    	}
		    		    };}).start();
				     }
				});
		 	
	}
	
}
