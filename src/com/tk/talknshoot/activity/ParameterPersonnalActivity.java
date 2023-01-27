package com.tk.talknshoot.activity;

import java.util.Locale;

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

public class ParameterPersonnalActivity extends Activity {
	
	//Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parameterpersonnal);
		
		// context 
		//context = this;
		
		// user data
		TkUser tkUser = TkAppManager.getTkUser();    	 
		//TkTrace.log(this, "firstname = "+tkUser.firstname);
		//TkTrace.log(this, "lastname = "+tkUser.lastname);
		//TkTrace.log(this, "cameratype = "+tkUser.cameratype);
		//TkTrace.log(this, "camerabrand = "+tkUser.camerabrand);
		//TkTrace.log(this, "wifiname = "+tkUser.wifiname);
		//TkTrace.log(this, "wifipassword = "+tkUser.wifipassword);
		
		//langue
		TextView txt_lang = (TextView) findViewById(R.id.parameterpersonnal_txt_lang);
		//(String)this.getString(R.string.helpwifi_txt_2);
		//String lang = Locale.getDefault().getLanguage();
		//fr
		//francais
		String langDisplay = Locale.getDefault().getDisplayLanguage();
		String lang = Locale.getDefault().getLanguage();
		txt_lang.setText(langDisplay +"("+lang+")");
		
		// bouton 
		/*final Button btnBack = (Button) findViewById(R.id.btn_parameterpersonnal_back);
			btnBack.setOnClickListener(new OnClickListener() {
		     public void onClick(View actuelView) {
		    	 Intent intent = new Intent(ParameterPersonnalActivity.this, ParameterActivity.class);
		      	 startActivity(intent);   
		      	 finish();
		     }
		});*/	
		
		
	}
}
