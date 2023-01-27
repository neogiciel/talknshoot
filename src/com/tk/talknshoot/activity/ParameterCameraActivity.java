package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkTrace;
import com.tk.talknshoot.util.TkUser;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ParameterCameraActivity extends Activity {
	
	//Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parametercamera);
		
		/* context */
		//context = this;
		 /* user data*/
		TkUser tkUser = TkAppManager.getTkUser();    	 
		TkTrace.log(this, "firstname = "+tkUser.firstname);
		TkTrace.log(this, "lastname = "+tkUser.lastname);
		TkTrace.log(this, "cameratype = "+tkUser.cameratype);
		TkTrace.log(this, "camerabrand = "+tkUser.camerabrand);
		TkTrace.log(this, "camerawifiname = "+tkUser.camerawifiname);
		TkTrace.log(this, "camerawifipassword = "+tkUser.camerawifipassword);
		
		if(tkUser.camerabrand.equals("empty")){
			TkTrace.log(this, "empty ");
			TextView parametercamera_txt_1 = (TextView) findViewById(R.id.parametercamera_txt_1);
			parametercamera_txt_1.setText((String)this.getString(R.string.parametercamera_txt_2));
			
			TextView txt_titre_cameratype = (TextView) findViewById(R.id.parametercamera_titre_txt_cameratype);
			txt_titre_cameratype.setVisibility(View.INVISIBLE);
			TextView txt_cameratype = (TextView) findViewById(R.id.parametercamera_txt_cameratype);
			txt_cameratype.setVisibility(View.INVISIBLE);
			
			TextView txt_titre_camerabrand = (TextView) findViewById(R.id.parametercamera_titre_txt_camerabrand);
			txt_titre_camerabrand.setVisibility(View.INVISIBLE);
			TextView txt_camerabrand = (TextView) findViewById(R.id.parametercamera_txt_camerabrand);
			txt_camerabrand.setVisibility(View.INVISIBLE);
			
			TextView txt_titre_wifiname = (TextView) findViewById(R.id.parametercamera_titre_txt_wifiname);
			txt_titre_wifiname.setVisibility(View.INVISIBLE);
			TextView txt_wifiname = (TextView) findViewById(R.id.parametercamera_txt_wifiname);
			txt_wifiname.setVisibility(View.INVISIBLE);
			
			
			TextView txt_titre_wifipassword = (TextView) findViewById(R.id.parametercamera_titre_txt_wifipassword);
			txt_titre_wifipassword.setVisibility(View.INVISIBLE);
			TextView txt_wifipassword = (TextView) findViewById(R.id.parametercamera_txt_wifipassword);
			txt_wifipassword.setVisibility(View.INVISIBLE);
			
		}else{
			
			TkTrace.log(this, "not empty ");
			TextView txt_cameratype = (TextView) findViewById(R.id.parametercamera_txt_cameratype);
			txt_cameratype.setText(tkUser.cameratype);
			TextView txt_camerabrand = (TextView) findViewById(R.id.parametercamera_txt_camerabrand);
			txt_camerabrand.setText(tkUser.camerabrand);
			TextView txt_wifiname = (TextView) findViewById(R.id.parametercamera_txt_wifiname);
			txt_wifiname.setText(tkUser.camerawifiname);
			
			if(tkUser.camerawifipassword.equals("none")){
				TkTrace.log(this, "camerawifipassword none ");
				TextView txt_titre_wifipassword = (TextView) findViewById(R.id.parametercamera_titre_txt_wifipassword);
				txt_titre_wifipassword.setVisibility(View.INVISIBLE);
				TextView txt_wifipassword = (TextView) findViewById(R.id.parametercamera_txt_wifipassword);
				txt_wifipassword.setVisibility(View.INVISIBLE);
			}else{
				TkTrace.log(this, "camerawifipassword set ");
				TextView txt_wifipassword = (TextView) findViewById(R.id.parametercamera_txt_wifipassword);
				txt_wifipassword.setText(tkUser.camerawifipassword);
			}
			
			
		}
		
		
	}
}
