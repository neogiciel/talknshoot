package com.tk.talknshoot.activity;

import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkCameraInfo;
import com.tk.talknshoot.util.TkGoproCommand;
import com.tk.talknshoot.util.TkTrace;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.os.Vibrator;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ControlVoiceActivity extends Activity implements RecognitionListener{

	/* Parameter */
	Context context;
	TkCameraInfo tkCameraInfo; 
	SpeechRecognizer recognizer;
	TextToSpeech tts;
	Handler a = new Handler();
	AsyncTask<Void, Void, Exception> voiceTask;
	int startingTask;
	boolean isAction;
	String  typeAction;  
	
	
	/* models-us */
	//Locale ttsLanguage = Locale.US;
	//String accousticModel = "hmm/en-us-semi";
	//String dictionaryModel = "dict/en.dic";		
	//String grammarModel = "grammar/en.gram";
	/* models-fr */
	String lang;
	Locale ttsLanguage = Locale.FRANCE;
	String accousticModel = "hmm/fr";
	String dictionaryModel = "dict/fr.dic";		
	String grammarModel = "grammar/fr.gram";
	/* models-es */
	//Locale ttsLanguage = Locale.SPANISH;
	//String accousticModel = "hmm/es";
	//String dictionaryModel = "dict/es.dic";		
	//String grammarModel = "grammar/es.gram";
	
	
	//Speak
	String speakEnregistrerVideo;
	String speakArreterVideo;
	String speakPrendrePhoto;
	String speakBurstPhoto;
	String speak…teindreCamera;
	
	
	/* Named searches allow to quickly reconfigure the decoder */
	private static final int HEADSET_ENABLE_TIMEOUT = 5000;
    private static final String KWS_SEARCH = "wakeup";
    private static final String FORECAST_SEARCH = "forecast";
    private static final String DIGITS_SEARCH = "digits";
    private static final String PHONE_SEARCH = "phones";
    private static final String MENU_SEARCH = "menu";
    private static final String KEYPHRASE = "start computer";

	/* chaine de texte  */
    TextView recognizer_state, recognized_word, recognized_partialword, recognized_action;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controlvoice);
		
		//Mise en place du context
        context = this;
        TkGoproCommand.getInstance().setContext(this);
        tkCameraInfo = TkAppManager.getTkCameraInfo();
        startingTask = 1;
        
		//TextView
		recognizer_state = (TextView) findViewById(R.id.txt_process);
        recognized_word = (TextView) findViewById(R.id.txt_result);
        recognized_partialword = (TextView) findViewById(R.id.txt_partial_result);
        recognized_action = (TextView) findViewById(R.id.txt_action);
        
        //Initialisation language
    	lang = Locale.getDefault().getLanguage();
    	TkTrace.log(context,"[ControlVoiceActivity/ onCreate ] lang="+lang);
    	
    	if(lang.equals("en")) {
    		ttsLanguage = Locale.US;
        	accousticModel = "hmm/en-us-semi";
        	dictionaryModel = "dict/en.dic";		
        	grammarModel = "grammar/en.gram";
    	}else if(lang.equals("es")) {
        	ttsLanguage = new Locale("es", "ES");
        	accousticModel = "hmm/es";
        	dictionaryModel = "dict/es.dic";		
        	grammarModel = "grammar/es.gram";
    	}else {
    		ttsLanguage = Locale.FRANCE;
        	accousticModel = "hmm/fr";
        	dictionaryModel = "dict/fr.dic";		
        	grammarModel = "grammar/fr.gram";
    	}
        
    	//-------------------------------------------------
    	// Recording Video >> Recording starting
        // Recording Stop >> Recording stopping
    	// Take Photo >> Picture taking
    	// Burst Photo >> Burst taking
    	// Camera Turn Off >> Camera shutdown
    	//
    	// Enregistrer video >> Enregistrement demarrÈ
    	// Arreter  video >> Enregistrement arrete
    	// Prendre Photo >> Photo Prise
    	// Burst Photo >> Photo Prise
    	// …teindre Camera >> Camera Eteinte
    	//-------------------------------------------------
    	/*if(lang.equals("en")) {
    		speakEnregistrerVideo = "Recording starting";
    		speakArreterVideo = "Recording stopping";
        	speakPrendrePhoto = "Picture taking";
        	speakBurstPhoto = "Burst taking";
        	speak…teindreCamera = "Camera shutdown";
    	}else {
    		speakEnregistrerVideo = "Recording starting";
    		speakArreterVideo = "Recording stopping";
        	speakPrendrePhoto = "Picture taking";
        	speakBurstPhoto = "Burst taking";
        	speak…teindreCamera = "Camera shutdown";
    	}*/
    	
        //---------------------
        //Initialisation task
        //---------------------	
		voiceTask = new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(getApplicationContext());
                	File assetDir = assets.syncAssets();
                	setupRecognizer(assetDir);
                } catch (Exception e) {
                	TkTrace.log(context,"[ControlVoiceActivity/ doInBackground ] Exception="+e.getMessage()); 
                    return e;
                }
                return null;
            }
        
            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                	//TkTrace.log(context,"switchSearch : Exception="+e); 
                } else {
                	try{
                	switchSearch(KWS_SEARCH,"onPostExecute");
                	 } catch (Exception e) {
                		 TkTrace.log(context,"[ControlVoiceActivity/ switchSearch ] Exception="+e); 
                	 }
                }
            }
        }.execute();
        //---------------------
        //Initialisation son
        //---------------------
        tts=new TextToSpeech(ControlVoiceActivity.this,new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR) {
                    tts.setLanguage(ttsLanguage);
                }
            }
        });
        //---------------------
		//Bouton Start
        //---------------------
		final Button btnStart = (Button) findViewById(R.id.btn_start);
			btnStart.setOnClickListener(new OnClickListener() {
			public void onClick(View actuelView) {
				startingTask = 1;
				switchSearch(KWS_SEARCH,"onClick");
			}
		});
	
	    //---------------------
		//Bouton Stop
        //---------------------
		final Button btnStop = (Button) findViewById(R.id.btn_stop);
		btnStop.setOnClickListener(new OnClickListener() {
			public void onClick(View actuelView) {
				startingTask = 0;
				recognizer.stop();
			    voiceTask.cancel(false);
			}
		});
			
		//---------------------	        
	    //Bouton BluetoothStart
	    //----------------------
	   final Button btnBluetoothStart = (Button) findViewById(R.id.btn_bluetooth_start);
	   			btnBluetoothStart.setOnClickListener(new OnClickListener() {
		        
				public void onClick(View actuelView) {
					
					//Mise en place du bluetooth
					AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
					//audioManager.setBluetoothScoOn(true);
				    //audioManager.startBluetoothSco();
				    audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
					//audioManager.setMode(AudioManager.MODE_IN_CALL);
					audioManager.startBluetoothSco();
			        //Log du texte
					recognized_action.setText("BLUETOOTH CONNECT");
					//Son sound
					String speak = (String)context.getString(R.string.CONNECT_BLUETOOTH);
					tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
					tts.speak("CONNECT BLUETOOTH",TextToSpeech.QUEUE_FLUSH,null);
					
					//Ouvrir dialog box
					openDialog();
				}
		});
	   			
	   	//---------------------	        
	    //Bouton BluetoothStart
	    //----------------------
	    final Button btnBluetoothStop = (Button) findViewById(R.id.btn_bluetooth_stop);
	    	btnBluetoothStop.setOnClickListener(new OnClickListener() {
	   			        
	   			public void onClick(View actuelView) {
	   				//Mise en place du bluetooth
	   						AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
	   						//audioManager.setBluetoothScoOn(false);
	   						//audioManager.stopBluetoothSco();
	   						audioManager.setMode(AudioManager.MODE_NORMAL);
	   						audioManager.stopBluetoothSco();
	   						
	   					    //Log du texte
	   						recognized_action.setText("BLUETOOTH DISCONNECT");
	   						//Son sound
	   						String speak = (String)context.getString(R.string.DISCCONNECT_BLUETOOTH);
	   						tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
	   					}
	   			});
		
	}
	
	/** Dialog box */
	public void openDialog() {
	    final Dialog dialog = new Dialog(context,R.style.dialogstyle); // Context, this, etc.
	    dialog.setContentView(R.layout.dialog_bluetooth);
	    //dialog.setTitle(R.string.dialog_bluetooth_txt_titre);
	    //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog_bluetooth);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                
                //Son sound
                //String speak = (String)context.getString(R.string.CONNECT_BLUETOOTH);
                //tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null,null);
            }
        });
	    dialog.show();
	}
	
	@Override
	public void onBackPressed()
	{
		try{
			
		//Mise a normal du bluetooth
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		audioManager.setMode(AudioManager.MODE_NORMAL);
		audioManager.stopBluetoothSco();

		//Arret du process	
		 startingTask = 0;
		 recognizer.stop();
		 recognizer.cancel();
		 voiceTask.cancel(false);
	     // code here to show dialog
	     super.onBackPressed(); 
		 TkTrace.log(context,"[ControlVoiceActivity/onBackPressed ()");
		 		 
	    } catch (Exception e) {
			 TkTrace.log(context,"[ControlVoiceActivity/onBackPressed : Exception="+e);
	     }
		     
	}

	
	@Override
	public void onPause(){
	try{
		super.onPause();
		TkTrace.log(context,"[ControlVoiceActivity/onPause ()");
			 
	 } catch (Exception e) {
		 TkTrace.log(context,"[ControlVoiceActivity/onStop : Exception="+e);
     }
	}

	@Override
	public void onStop(){
	try{
		//startingTask = 0;
		//recognizer.stop();
		//voiceTask.cancel(false);
		super.onStop();
		TkTrace.log(context,"[ControlVoiceActivity/onStop ()");
			 
	 } catch (Exception e) {
		 TkTrace.log(context,"[ControlVoiceActivity/onStop : Exception="+e);
     }
	}

	@Override
	public void onDestroy(){
	try{
		super.onPause();
		TkTrace.log(context,"[ControlVoiceActivity/onDestroy ()");
		//Mise a normal du bluetooth
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		audioManager.setMode(AudioManager.MODE_NORMAL);
		audioManager.stopBluetoothSco();
			 
	 } catch (Exception e) {
		 TkTrace.log(context,"[ControlVoiceActivity/onStop : Exception="+e);
     }
	}

	@Override
	public void onBeginningOfSpeech() {
		// TODO Auto-generated method stub
		recognizer_state.setText("onBeginningOfSpeech()");		
	}

	@Override
	public void onEndOfSpeech() {
		// TODO Auto-generated method stub
		try{
			TkTrace.log(context,"[ControlVoiceActivity/onEndOfSpeech ");
			recognizer_state.setText("onEndOfSpeech()");
			if (!recognizer.getSearchName().equals(KWS_SEARCH))
	            switchSearch(KWS_SEARCH,"onEndOfSpeech");
			} catch (Throwable  t) {
				TkTrace.log(context,"[ControlVoiceActivity/onEndOfSpeech : Exception="+t.toString());
			}
	}	
	
	private void setupRecognizer(File assetsDir) {
		try{
	
			
			
			
		//Mise en place de l ecoute
		File modelsDir = new File(assetsDir, "models");
		recognizer = defaultSetup()
	    .setAcousticModel(new File(modelsDir, accousticModel))
        //.setDictionary(new File(modelsDir, "dict/cmu07a.dic"))
        .setDictionary(new File(modelsDir, dictionaryModel))
        //.setBoolean("-remove_noise", true)
        //.setRawLogDir(assetsDir)
        //.setKeywordThreshold(1e-45f)
        //.setKeywordThreshold(1e-80f)
        //.setBoolean("-allphone_ci", true)
        .getRecognizer();

	    recognizer.addListener(this);
	    // Create grammar-based search for digit recognition
        //http://passwordsgenerator.net/md5-hash-generator/
	    File digitsGrammar = new File(modelsDir, grammarModel);
        recognizer.addGrammarSearch(DIGITS_SEARCH, digitsGrammar);
	    //Fin
        recognizer_state.setText("setupRecognizer()");
	    //Par defaut du mode 
        String command = TkGoproCommand.getInstance().executeCommand("MODE_VIDEO");
 	    if(command != null){
	    	 tkCameraInfo.setCurrentMode("MODE_VIDEO"); 
 	    }
 		} catch (Exception e) {
			TkTrace.log(context,"[ControlVoiceActivity/setupRecognizer] Exception="+e.getMessage());
		} 
	 }
	
	 private void switchSearch(String searchName,String fromProcess) {
	    try{
	    	recognizer.stop();
	    	// If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
	    	//TkTrace.log(context,"switchSearch = startListening DIGIT");
	    	recognizer_state.setText("switchSearch from : "+fromProcess);
	    	recognizer.startListening(DIGITS_SEARCH);
	        
	 	} catch (Throwable  t) {
			TkTrace.log(context,"switchSearch : Exception="+t.toString());
		}
	}
	 
	@Override
	public void onResult(Hypothesis hup) {
		recognized_word.setText("");
	    if (hup != null)
	    {
	    	String text = hup.getHypstr();
	    	recognized_word.setText(text);
	    }
		
	}

	@Override
	public void onPartialResult(Hypothesis arg0) {
		try{
		     if (arg0 == null)
		    	    return;
		     if (startingTask == 0)
		    	 return;
		     
		     String text = arg0.getHypstr();
		     recognized_partialword.setText("onPartialResult="+text);
		     //TkTrace.log(context,"onPartialResult="+text);

		     //Effectuer l action
		     if(onAction(text)==true){
		    	 arg0.delete();
		     };
		     
		     //if(startingTask==false){
		    	 if (text.equals(KEYPHRASE))
		           switchSearch(MENU_SEARCH,"onPartialResult");
		     	else if (text.equals(DIGITS_SEARCH))
		          switchSearch(DIGITS_SEARCH,"onPartialResult");
		     	else{
		    	 recognized_partialword.setText("onPartialResult="+text);
		     	}
		    //}
		} catch (Exception e) {
			TkTrace.log(context,"onPartialResult : Exception="+e);
		}
		
	}
	
	/** Action */
	public boolean onAction(String text) {
		isAction = false;
    	typeAction = "NONE";  
    	
    	//display text
    	String displayText = text.toLowerCase();
    	if(displayText.length()> 40){
    		displayText = text.toLowerCase().substring(text.length()-40, text.length());
    	}
    	
		
    	if(text.toLowerCase().contains("recording video")||text.toLowerCase().contains("enregistrer video")){
    		//recognized_action.setText("ACTION_START("+text.toLowerCase()+")");
    		recognized_action.setText("ACTION_START("+displayText+")");
			isAction = true;
			typeAction = "START_RECORD_VIDEO";
		}
        if(text.toLowerCase().contains("recording stop")||text.toLowerCase().contains("arreter video")){
			//recognized_action.setText("ACTION_STOP("+text.toLowerCase()+")");
			recognized_action.setText("ACTION_STOP("+displayText+")");
			isAction = true;
			typeAction = "STOP_RECORD_VIDEO";
		}
	    if(text.toLowerCase().contains("photo shoot")||text.toLowerCase().contains("take photo")||text.toLowerCase().contains("prendre photo")){
	    	//recognized_action.setText("TAKE_PICTURE ("+text.toLowerCase()+")");
	    	recognized_action.setText("TAKE_PICTURE("+displayText+")");
			isAction = true;
			typeAction = "TAKE_PICTURE";
		}
	    if(text.toLowerCase().contains("camera info")){
			//recognized_action.setText("CAMERA_STATE ("+text.toLowerCase()+")");
			recognized_action.setText("CAMERA_INFO("+displayText+")");
			isAction = true;
			typeAction = "CAMERA_STATE";
		}
		
		
		if(isAction == true){
			//Stop
			recognizer.stop();
			//Traitement en fonction des commandes
		    String currentAction = tkCameraInfo.getCurrentAction(); 
		      
			if(isAction == true){
				new Thread(new Runnable() {public void run() {//debut threard
					executeAction(typeAction);
				};}).start();//fin threard
			}
		    return true;
		}
		
		return false;
		
	}
	
	/** Action */
	public boolean executeAction(String typeAction) {
		TkTrace.log(context,"Action="+typeAction);
		   
		//Vibration
		Vibrator va = (Vibrator)context.getSystemService(VIBRATOR_SERVICE);
		va.vibrate(600);
			    
		if(typeAction.contains("START_RECORD_VIDEO")){
			 if(tkCameraInfo.getCurrentMode().equals("MODE_VIDEO")){
				 String command = TkGoproCommand.getInstance().executeCommand("START_RECORD_VIDEO");
				 if(command != null){
					//tts.speak("START MOVIE",TextToSpeech.QUEUE_FLUSH,null,null);
					String speak = (String)context.getString(R.string.START_RECORD_VIDEO);
   					tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
					 //Mise a jour des actions
					tkCameraInfo.setCurrentAction(typeAction);
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
					 	tkCameraInfo.setCurrentAction(typeAction);
				 		tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
					 }
				 }
				 
				 
			 }
		}
		
		if(typeAction.contains("STOP_RECORD_VIDEO")){
			 String command = TkGoproCommand.getInstance().executeCommand("STOP_RECORD_VIDEO");
			 if(command != null){
				//tts.speak("MOVIE STOP",TextToSpeech.QUEUE_FLUSH,null,null);
				String speak = (String)context.getString(R.string.STOP_RECORD_VIDEO);
   				tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
   			 	//Mise a jour des actions
			 	tkCameraInfo.setCurrentAction(typeAction);
		 		tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
			 }
			 
		}
		if(typeAction.contains("TAKE_PICTURE")){
			 if(tkCameraInfo.getCurrentMode().equals("MODE_PHOTO")){
				 String command = TkGoproCommand.getInstance().executeCommand("TAKE_PICTURE");
				 if(command != null){
					//tts.speak("START PHOTO",TextToSpeech.QUEUE_FLUSH,null,null);
					String speak = (String)context.getString(R.string.TAKE_PICTURE);
		   			tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
		   			//Mise a jour des actions
					tkCameraInfo.setCurrentAction(typeAction);
				 	tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
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
					 	tkCameraInfo.setCurrentAction(typeAction);
				 		tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
					 }
				
				 }
		
			 }
			 
		}

		if(!typeAction.equals("CAMERA_STATE")){
		 	tkCameraInfo.setCurrentAction(typeAction);
		 	tkCameraInfo.setLastAction(tkCameraInfo.getLastAction());
		}else{
			if(tkCameraInfo.getCurrentAction().contains("START_RECORD_VIDEO")){
				//tts.speak("RECORDING VIDEO",TextToSpeech.QUEUE_FLUSH,null,null);	
				String speak = (String)context.getString(R.string.START_RECORD_VIDEO);
		   		tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
			}else{
		   	 	//tts.speak(tkCameraInfo.getCurrentMode(),TextToSpeech.QUEUE_FLUSH,null,null);
				String speak = (String)context.getString(R.string.NO_ACTION);
		   		tts.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
			}
		}
		
	     //Relance le reconizer
	     recognizer.startListening(DIGITS_SEARCH);
	     	
	     return true;
	}
	
}




