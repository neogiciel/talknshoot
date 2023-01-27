package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkTrace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	/** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
	private Context context;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        
        /* default context */
        context = this;
        
        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
            	
            	 new Thread(new Runnable() {public void run() {//debut threard
            		 
            	/* init manager */
            	TkAppManager.setContext(context);
            	if(TkAppManager.initManager() == true){
            		/* Create an Intent that will start the Menu-Activity. */
            		TkTrace.log(context,"[SplashActivity] HomeActivity startActivity ");
            		Intent mainIntent = new Intent(SplashActivity.this,HomeActivity.class);
            		SplashActivity.this.startActivity(mainIntent);
            		SplashActivity.this.finish();
            	}else{
            		/* Create an Intent that will start the Menu-Activity. */
            		TkTrace.log(context,"[SplashActivity] HomeActivity SplashActivity ");
            		Intent mainIntent = new Intent(SplashActivity.this,UpdateActivity.class);
            		SplashActivity.this.startActivity(mainIntent);
            		SplashActivity.this.finish();
            	}
            	
            	};}).start();//fin threard
            	
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
