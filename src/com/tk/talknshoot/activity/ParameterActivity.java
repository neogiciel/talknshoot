package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;

import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ParameterActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parameter);
		
		// bouton 
		final Button btnCamera = (Button) findViewById(R.id.btn_parameter_camera);
			btnCamera.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 Intent intent = new Intent(ParameterActivity.this, ParameterCameraActivity.class);
		      	 startActivity(intent);    	 
		     }
		});	
		// bouton 
		final Button btnAbo = (Button) findViewById(R.id.btn_parameter_abo);
		btnAbo.setOnClickListener(new OnClickListener() {
			     public void onClick(View actuelView) {
			    	 Intent intent = new Intent(ParameterActivity.this, ParameterAboActivity.class);
			      	 startActivity(intent);    	 
			     }
			});	
		// bouton 
		final Button btnPersonnal = (Button) findViewById(R.id.btn_parameter_personnal);
		btnPersonnal.setOnClickListener(new OnClickListener() {
			     public void onClick(View actuelView) {
			    	 Intent intent = new Intent(ParameterActivity.this, ParameterPersonnalActivity.class);
			      	 startActivity(intent);    	 
			     }
			});	
		
	}
}
