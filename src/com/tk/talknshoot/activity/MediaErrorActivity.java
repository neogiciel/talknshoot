package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MediaErrorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mediaerror);
		
		
		final Button btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {
	     public void onClick(View actuelView) {
	    	 Intent intent = new Intent(MediaErrorActivity.this, MediaActivity.class);
	    	 startActivity(intent);
	    	 MediaErrorActivity.this.finish();
	     }
		});	
		
		
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(MediaErrorActivity.this, MediaActivity.class);
   	 	startActivity(intent);
   	 	MediaErrorActivity.this.finish();
	}

}
