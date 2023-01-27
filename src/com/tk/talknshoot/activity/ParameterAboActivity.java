package com.tk.talknshoot.activity;

import java.util.HashMap;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkHttpJson;
import com.tk.talknshoot.util.TkTool;
import com.tk.talknshoot.util.TkTrace;
import com.tk.talknshoot.util.TkUser;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ParameterAboActivity extends Activity {
	
	Context context;
	String urlapk;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parameterabo);
		
		/* context */
		context = this;
		 /* user data*/
		TkUser tkUser = TkAppManager.getTkUser();
		TkTool tktool = new TkTool();
		
		//Recuperation de la version de l application
		String version = tkUser.versionabo; 
		version = "PREMIUM";
		TkTrace.log(context,"[TkAppManager/onCreate()] : version ="+version);
		
		//Mise a jour de la date
		TkTrace.log(context,"[ParameterAboActivity/onCreate()] : firstvisit ="+tkUser.firstvisit);
		String dateFirstInstall= tktool.formatDate(context,tkUser.firstvisit);
		TkTrace.log(context,"[ParameterAboActivity/onCreate()] : dateFirstInstall ="+dateFirstInstall);
		TextView txt_date_firstinstall = (TextView) findViewById(R.id.parameterabo_txt_install);
		txt_date_firstinstall.setText(dateFirstInstall);
		
		//Mise a jour de la date
		int nbday = tktool.nbDayAppVersion(context,tkUser.firstvisit,tkUser.versiontype,tkUser.versionduration);
		TkTrace.log(context,"[ParameterAboActivity/onCreate()] : nbday ="+nbday);
		TextView txt_date_nbday = (TextView) findViewById(R.id.parameterabo_txt_date);
		txt_date_nbday.setText(Integer.toString(nbday)+" "+(String)this.getString(R.string.parameterabo_txt_jour));
		
		//Mise a jour du texte si abonnement
		if(!version.equalsIgnoreCase("FREE")){
			TkTrace.log(context,"[ParameterAboActivity/onCreate()] : premium");
			TextView parameterabo_txt_4 = (TextView) findViewById(R.id.parameterabo_txt_4);
			parameterabo_txt_4.setText((String)this.getString(R.string.parameterabo_txt_6));
			TextView parameterabo_txt_5 = (TextView) findViewById(R.id.parameterabo_txt_5);
			parameterabo_txt_5.setText((String)this.getString(R.string.parameterabo_txt_7));
		}else {
			TkTrace.log(context,"[ParameterAboActivity/onCreate()] : free");
			
		}
		
	
		//Url de la version premium
		new Thread(new Runnable() {public void run() {//debut threard
			TkHttpJson httpJson = new TkHttpJson();
			TkTrace.log(context,"[ParameterAboActivity/onCreate()] : Recupere la version premium");
			HashMap map = httpJson.getAdminValue(context, "name=getlastversion&param1=android&param2=premium");
			int nb = Integer.parseInt((String)map.get("nbelement"));
	    	if(nb>0){
	    		urlapk = (String)map.get("URLVERSION_0");
	    		TkTrace.log(context,"[ParameterAboActivity/onCreate()] : urlapk = "+urlapk);
	    	}
	
	    	//Bouton de telechargement
	    	final Button btnPremium = (Button) findViewById(R.id.btn_parameterabo_premium1);
	    	btnPremium.setOnClickListener(new OnClickListener() {
	    				     public void onClick(View actuelView) {
	    				    	 try {
	    				    		 Uri webpage = Uri.parse(urlapk);
	    				             Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
	    				             startActivity(myIntent);
	    				         } catch (ActivityNotFoundException e) {
	    				        	 TkTrace.log(context,"[ParameterAboActivity/onCreate()] No application can handle this request. Please install a web browser or check your URL.");
	    				         }
	    				     }
	    				});
		};}).start();//fin threard
	}
}
