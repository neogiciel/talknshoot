package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpWifiActivity extends Activity {

	String htmlText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helpwifi);
		
		TextView htmlTextView = (TextView)findViewById(R.id.help_txt_1);
		/* type de camera */
		Bundle bundle=getIntent().getExtras();
		String camera_selected = bundle.getString("camera_selected");
		String camera_brand = bundle.getString("camera_brand");
		
		//"HERO6 BLACK","HERO5 BLACK","HERO5 SESSION","HERO SESSION","HERO+","HERO4","HERO3+/3/2"};
		if(camera_selected.equals("HERO6 BLACK")){
			//HERO6 BLACK
			htmlText = (String)this.getString(R.string.helpwifi_txt_7);	
		}else if(camera_selected.equals("HERO5 BLACK")){
			//HERO5 BLACK
			htmlText = (String)this.getString(R.string.helpwifi_txt_6);	
		}else if(camera_selected.equals("HERO5 SESSION")){
			//HERO5 SESSION
			htmlText = (String)this.getString(R.string.helpwifi_txt_5);	
		}else if(camera_selected.equals("HERO SESSION")){
			//HERO SESSION
			htmlText = (String)this.getString(R.string.helpwifi_txt_4);
		}else if(camera_selected.equals("HERO+")){
			//HERO+
			htmlText = (String)this.getString(R.string.helpwifi_txt_3);	
		}else if(camera_selected.equals("HERO4")){
			//HERO4
			htmlText = (String)this.getString(R.string.helpwifi_txt_2);
		}else{
			//HERO3+/3/2
			htmlText = (String)this.getString(R.string.helpwifi_txt_1);
		}

		htmlTextView.setText(Html.fromHtml(htmlText, new ImageGetter(), null));
	      
	}
	
	private class ImageGetter implements Html.ImageGetter {

		public Drawable getDrawable(String source) {
		    int id;
		    if (source.equals("camera_list_hero6black.png")) {
		           id = R.drawable.camera_list_hero6black;
		    }else if (source.equals("help_screen_gopro6_1.png")) {
		           id = R.drawable.help_screen_gopro6_1;
		    }else if (source.equals("help_screen_gopro6_2.png")) {
		           id = R.drawable.help_screen_gopro6_2;
		    }else if (source.equals("camera_list_hero5black.png")) {
		           id = R.drawable.camera_list_hero5black;
		    }else if(source.equals("camera_list_hero5session.png")) {
		           id = R.drawable.camera_list_hero5session;
		    }else if(source.equals("camera_list_herosession.png")) {
		           id = R.drawable.camera_list_herosession;
		    }else if(source.equals("camera_list_heroplus.png")) {
		           id = R.drawable.camera_list_heroplus;
		    }else if(source.equals("camera_list_hero4.png")) {
		           id = R.drawable.camera_list_hero4;
		    }else if(source.equals("camera_list_hero2.png")) {
		           id = R.drawable.camera_list_hero2;
		    }else if(source.equals("help_hero4_screen1.png")) {
		           id = R.drawable.help_hero4_screen1;
		    }else if(source.equals("help_app_capture.png")) {
		           id = R.drawable.help_app_capture;
		    }
		    else {
		        return null;
		    }

		   Drawable d = getResources().getDrawable(id);
		   d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
		   return d;
		 }
		};
	
}

