package com.tk.talknshoot.activity;

import java.io.File;
import java.util.List;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkGoproCommand;
import com.tk.talknshoot.util.TkTrace;
import com.tk.talknshoot.util.TkUser;
import com.tk.talknshoot.util.TkWifi;

import edu.cmu.pocketsphinx.Assets;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ControlNoCameraActivity extends Activity {
	
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_controlnocamera);
		
		  /* default context */
        context = this;
        
           /*
		 * bouton btnAdd
		 */
        Button btnAdd = (Button) findViewById(R.id.btn_controlnocamera_add);
        btnAdd.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 Intent intent = new Intent(ControlNoCameraActivity.this, AddActivity.class);
		    	 startActivity(intent);
		    	 finish();
			
			}
		});

        /*
		 * bouton btnBack
		 */
        Button btnBack = (Button) findViewById(R.id.btn_controlnocamera_back);
        btnBack.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 Intent intent = new Intent(ControlNoCameraActivity.this, HomeActivity.class);
		    	 startActivity(intent);
		    	 finish();
			
			}
		});

	}
	
}
