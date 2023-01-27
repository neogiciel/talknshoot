package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;

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
import android.widget.TextView;

public class Add3Activity extends Activity {

	/* parameter */
	Context context;
	String camera_selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add3);
		
		/* context */
		context = this;
		
		/* get bundle */
		Bundle bundle=getIntent().getExtras();
		camera_selected = bundle.getString("camera_selected");
	    //TkTrace.log(context, "item = "+camera_selected);
	    TextView txt_camera = (TextView) findViewById(R.id.add_txt_2);
	    txt_camera.setText(camera_selected);
	    
		/*
		 * bouton start
		 */
		 final Button btnStart = (Button) findViewById(R.id.btn_start);
		 btnStart.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	Bundle bundle = new Bundle();
	            bundle.putString("camera_selected", camera_selected);
			    Intent intent = new Intent(Add3Activity.this, Add4Activity.class);
			    intent.putExtras(bundle);
			    startActivity(intent);
			    finish();
		     }
		});
		 
		 /*
		 * bouton help
		 */
		 final Button btnHelp = (Button) findViewById(R.id.btn_helpwifi);
		 btnHelp.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 	Bundle bundle = new Bundle();
		            bundle.putString("camera_selected", camera_selected);
		            bundle.putString("camera_brand", "gopro");
		            Intent intent = new Intent(Add3Activity.this, HelpWifiActivity.class);
				    intent.putExtras(bundle);
				    startActivity(intent);
		     }
		});
	}
}
