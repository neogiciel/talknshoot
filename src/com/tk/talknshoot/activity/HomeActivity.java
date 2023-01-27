package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity {

	private TkUser tkUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		
		/* retreive data */
		tkUser = TkAppManager.getTkUser();
		//TextView txtLog = (TextView) findViewById(R.id.txt_log);
		//txtLog.setText(tkUser.firstname);

		
		final Button btnControl = (Button) findViewById(R.id.btn_control);
	 	btnControl.setOnClickListener(new OnClickListener() {
	     public void onClick(View actuelView) {
	    	 //Intent intent = new Intent(HomeActivity.this, ControlChoiseActivity.class);
	    	Intent intent = new Intent(HomeActivity.this, ControlConnectWifiActivity.class);
	      	startActivity(intent);
		}
	 	});

		final Button btnAdd = (Button) findViewById(R.id.btn_add);
	 	btnAdd.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 Intent intent = new Intent(HomeActivity.this, AddActivity.class);
		    	 startActivity(intent);
			}
	 	});

	 	 final Button btnMedia = (Button) findViewById(R.id.btn_media);
		 	btnMedia.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 //Intent intent = new Intent(HomeActivity.this, TestActivity.class);
		    	 //startActivity(intent);
		    	 Intent intent = new Intent(HomeActivity.this, MediaActivity.class);
		    	 startActivity(intent);
			}
	 	});

		final Button btnHelp = (Button) findViewById(R.id.btn_help);
		     btnHelp.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 Intent intent = new Intent(HomeActivity.this, HelpActivity.class);
		    	 startActivity(intent);
			}
		});

	 	 final Button btnParameter = (Button) findViewById(R.id.btn_parameter);
		 btnParameter.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 Intent intent = new Intent(HomeActivity.this, ParameterActivity.class);
		    	 startActivity(intent);
		     }
		});
		     
		 final Button btnPlus = (Button) findViewById(R.id.btn_plus);
			btnPlus.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 Intent intent = new Intent(HomeActivity.this, CreditsActivity.class);
		    	 //Intent intent = new Intent(HomeActivity.this, TestActivity.class);
		    	 startActivity(intent);
			}
		});
	     
		
	}
}
