package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpActivity extends Activity {

	String htmlText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		TextView htmlTextView = (TextView)findViewById(R.id.help_txt_1);
		htmlText = (String)this.getString(R.string.help_txt_1);	
		htmlTextView.setText(Html.fromHtml(htmlText, new ImageGetter(), null));
	      
	}
	
	private class ImageGetter implements Html.ImageGetter {

		public Drawable getDrawable(String source) {
		    int id;
		    if (source.equals("pix_help1.png")) {
		           id = R.drawable.pix_help1;
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

