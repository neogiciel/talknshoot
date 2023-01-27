package com.tk.talknshoot.activity;

import java.io.File;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tk.talknshoot.R;
import com.tk.talknshoot.util.TkAppManager;
import com.tk.talknshoot.util.TkMedia;
import com.tk.talknshoot.util.TkTrace;
import com.tk.talknshoot.util.TkUser;
import com.tk.talknshoot.util.TkWifi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import edu.cmu.pocketsphinx.Assets;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MediaVideoActivity extends Activity {

	/* parameter */
	Context context;
	ProgressDialog progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// context 
		context = this;
		//progress
		//Progress bar
		progress = new ProgressDialog(this);
		progress.setTitle(null);
		progress.setCancelable(false);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setMessage("Loading Video wait...");
		progress.show();
		 //set content
		 setContentView(R.layout.activity_mediavideo);
		 
		//Recuperation du bunlde
		Bundle bundle=getIntent().getExtras();
		String mediaName = bundle.getString("medianame");
		String mediaType = bundle.getString("mediatype");
		String url = bundle.getString("url");
		
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);
        Uri vidUri = Uri.parse(url);
        videoView.setVideoURI(vidUri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);        
        videoView.start();
        
        progress.dismiss();
        
   }
	
}
