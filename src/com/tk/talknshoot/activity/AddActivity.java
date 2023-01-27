package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AddActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		/*
		 * bouton start
		 */
		 final Button btnStart = (Button) findViewById(R.id.btn_start);
		 btnStart.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 Intent intent = new Intent(AddActivity.this, Add2Activity.class);
				 startActivity(intent);
				 finish();
		     }
		});
	}
}
