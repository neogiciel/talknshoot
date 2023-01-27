package com.tk.talknshoot.activity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkHttpJson;
import com.tk.talknshoot.util.TkHttpStat;
import com.tk.talknshoot.util.TkTrace;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity {

		   private Context context;
		   private MediaRecorder myRecorder;
		   private MediaPlayer myPlayer;
		   private String outputFile = null;
		   private Button startBtn;
		   private Button stopBtn;
		   private Button playBtn;
		   private Button stopPlayBtn;
		   private Button testBtn;
		   private TextView text;
		   private AudioManager amanager;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_test);

		    context = this;
		    
		    amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		    text = (TextView) findViewById(R.id.txt_1);
		      // store it to sd card
		      outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/talkandshootRecording.3gpp";

		      myRecorder = new MediaRecorder();
		      myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		      myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		      myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		      myRecorder.setOutputFile(outputFile);

		      startBtn = (Button)findViewById(R.id.btn_test);
		      startBtn.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            // TODO Auto-generated method stub
		            start(v);
		        }
		      });

		      stopBtn = (Button)findViewById(R.id.btn_test1);
		      stopBtn.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            // TODO Auto-generated method stub
		            stop(v);
		        }
		      });

		      playBtn = (Button)findViewById(R.id.btn_test2);
		      playBtn.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            // TODO Auto-generated method stub
		                play(v);    
		        }
		      });

		      stopPlayBtn = (Button)findViewById(R.id.btn_test3);
		      stopPlayBtn.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            // TODO Auto-generated method stub
		            stopPlay(v);
		        }
		      });
		      
		      testBtn = (Button)findViewById(R.id.btn_test4);
		      testBtn.setOnClickListener(new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		            // TODO Auto-generated method stub
		            test(v);
		        }
		      });

		}
		public void start(View view){
		       try {
		          
		    	  //amanager.setMode(AudioManager.MODE_IN_CALL);
		    	  amanager.setMode(AudioManager.MODE_IN_COMMUNICATION);
		    	  amanager.startBluetoothSco();
		          myRecorder.prepare();
		          myRecorder.start();
		       } catch (IllegalStateException e) {
		          // start:it is called before prepare()
		          // prepare: it is called after start() or before setOutputFormat() 
		          e.printStackTrace();
		       } catch (IOException e) {
		           // prepare() fails
		           e.printStackTrace();
		        }

		       text.setText("Recording Point: Recording");
		       startBtn.setEnabled(false);
		       stopBtn.setEnabled(true);

		   }

		   public void stop(View view){
		       try {
		          myRecorder.stop();
		          myRecorder.release();
		          myRecorder  = null;

		          stopBtn.setEnabled(false);
		          playBtn.setEnabled(true);
		          text.setText("Recording Point: Stop recording");

		       } catch (IllegalStateException e) {
		            //  it is called before start()
		            e.printStackTrace();
		       } catch (RuntimeException e) {
		            // no valid audio/video data has been received
		            e.printStackTrace();
		       }
		   }

		   public void play(View view) {
		       try{
		           myPlayer = new MediaPlayer();
		           myPlayer.setDataSource(outputFile);
		           myPlayer.prepare();
		           myPlayer.start();

		           playBtn.setEnabled(false);
		           stopPlayBtn.setEnabled(true);
		           text.setText("Recording Point: Playing");

		       } catch (Exception e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		   }

		   public void stopPlay(View view) {
		       try {
		           if (myPlayer != null) {
		               myPlayer.stop();
		               myPlayer.release();
		               myPlayer = null;
		               playBtn.setEnabled(true);
		               stopPlayBtn.setEnabled(false);
		               text.setText("Recording Point: Stop playing");

		           }
		       } catch (Exception e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		   }
		   
		   public void test(View view){
		       try {
		    	   TkHttpJson httpJson = new TkHttpJson();
		    	   HashMap map = httpJson.getAdminValue(context, "name=getversion&param1="+context.getString(R.string.versionid));
		    	   
		    	   TkHttpStat httpStat = new TkHttpStat();
		    	   httpStat.stat(context,"hit","1");
		    		
		    	   
		       } catch (Exception e) {
			      TkTrace.log(context,"[TkHttpJson/readJson] Exception : "+e.getMessage());
				 
				}
		   }
		}