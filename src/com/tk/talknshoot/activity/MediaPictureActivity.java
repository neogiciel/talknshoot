package com.tk.talknshoot.activity;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;
import android.widget.VideoView;

public class MediaPictureActivity extends Activity {

	/* parameter */
	Context context;
    private String url;
    private ImageView imageView;
	AsyncTask<Void, Void, Bitmap> mediaTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_mediapicture);
		
		// context 
		context = this;
		
		//Recuperation du bunlde
		Bundle bundle=getIntent().getExtras();
		//String mediaName = bundle.getString("medianame");
		//String mediaType = bundle.getString("mediatype");
		url = bundle.getString("url");
				
		imageView = (ImageView) findViewById(R.id.pictureView1);
		
		//---------------------
        //Initialisation task
        //---------------------	
		mediaTask = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                	URL urlConnection = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) urlConnection
                            .openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    return myBitmap;
                    
                } catch (RuntimeException e) {
                	TkTrace.log(context,"[MediaPictureActivity/ doInBackground ] RuntimeException="+e.getMessage()); 
                } catch (Exception e) {
                	TkTrace.log(context,"[MediaPictureActivity/ doInBackground ] Exception="+e.getMessage()); 
                }
                return null;
            }
        
            @Override
            protected void onPostExecute(Bitmap result) {
            	try {
            		super.onPostExecute(result);
                    imageView.setImageBitmap(result);
            	} catch (RuntimeException e) {
                	TkTrace.log(context,"[MediaPictureActivity/ onPostExecute ] RuntimeException="+e.getMessage()); 
                } catch (Exception e) {
                	TkTrace.log(context,"[MediaPictureActivity/ onPostExecute ] Exception="+e.getMessage()); 
                }
            }
        }.execute();
		
		
   }
	
}
