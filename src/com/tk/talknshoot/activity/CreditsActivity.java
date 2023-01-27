package com.tk.talknshoot.activity;

import com.tk.talknshoot.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CreditsActivity extends Activity {

	String htmlText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);
		
		TextView htmlTextView = (TextView)findViewById(R.id.credits_txt_1);
		htmlText = (String)this.getString(R.string.credits_txt_1);	
		htmlTextView.setText(Html.fromHtml(htmlText, new ImageGetter(), null));
	      
	}
	
	private class ImageGetter implements Html.ImageGetter {

		public Drawable getDrawable(String source) {
		    int id;
		    if (source.equals("pix_credits1.png")) {
		           id = R.drawable.pix_credits1;
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

