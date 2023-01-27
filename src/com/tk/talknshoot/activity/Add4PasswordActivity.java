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

public class Add4PasswordActivity extends Activity {

	/* parameter */
	Context context;
	String camera_selected;
	String camera_brand;
	String wifi_selected;
	String wifi_password;
	EditText edit_txt_wifi_password;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add4_password);
		
		/* context */
		context = this;
		
		
		// get bundle 
		Bundle bundle=getIntent().getExtras();
		camera_selected = bundle.getString("camera_selected");
		camera_brand = "gopro";
		wifi_selected = bundle.getString("wifi_selected");
		
	    
	    // disable texview
    	edit_txt_wifi_password = (EditText) findViewById(R.id.add4_password_edittxt);

		 //bouton start
		 final Button btnStart = (Button) findViewById(R.id.btn_add4_password_start);
		 btnStart.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	
		    	 //Remplit bundle
		    	Bundle bundle = new Bundle();
		        bundle.putString("camera_selected", camera_selected);
		        bundle.putString("wifi_selected", wifi_selected);
		        
		        //Recuperation du texte
		        wifi_password = edit_txt_wifi_password.getText().toString();
		        TkTrace.log(context, "wifi_password = "+wifi_password);
		        bundle.putString("wifi_password", wifi_password);
		        
		        //Page suivante
		        Intent intent = new Intent(Add4PasswordActivity.this, Add5Activity.class);    
		        intent.putExtras(bundle);
			    startActivity(intent);
			    finish();
		     }
		});
    	
	}
	
	
}
