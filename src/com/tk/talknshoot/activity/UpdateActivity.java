package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UpdateActivity extends Activity {
	
	/* default context */
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		
        /* default context */
        context = this;

		
		/*final Button btnStart = (Button) findViewById(R.id.btn_media_premium);
		btnStart.setOnClickListener(new OnClickListener() {
	     public void onClick(View actuelView) {
	    	 //Intent intent = new Intent(MediaActivity.this, Media1Activity.class);
	    	 //startActivity(intent);
	     }
		});*/	
		
		
		
		
	}
}
