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
import android.widget.TextView;

public class Media1Activity extends Activity {

	/* parameter */
	Context context;
	ListView lv;
	TkMedia tkMedia;
	int nbMedia;
	public static HashMap tabMedia; 
	public static int [] listImages;
    public static String [] listNameList;
    AsyncTask<Void, Void, Exception> mediaTask;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media1);
		
		// context 
		context = this;
		
		//init
		TkUser tkUser = TkAppManager.getTkUser();
		TkWifi tkWifi = new TkWifi();
		String wifiCameraConfigurated = "\""+tkUser.camerawifiname+"\"";
		String wifiCameraActual = tkWifi.getWifiStatus(this);
		
		if(wifiCameraActual.equals(wifiCameraConfigurated) == true){
			nbMedia = 1;
		}else {
			nbMedia = 0;
		}
		
		
		//---------------------
        //Initialisation task
        //---------------------	
		mediaTask = new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                	if(nbMedia>0) {
                		TkTrace.log(context,"[Media1Activity /onCreate ] doInBackground");
                		retreiveListMedia();
                	}
                } catch (RuntimeException e) {
                	TkTrace.log(context,"[Media1Activity/ doInBackground ] RuntimeException="+e.getMessage()); 
                    return e;
                } catch (Exception e) {
                	TkTrace.log(context,"[Media1Activity/ doInBackground ] Exception="+e.getMessage()); 
                    return e;
                }
                return null;
            }
        
            @Override
            protected void onPostExecute(Exception result) {
            	try {
            		TkTrace.log(context,"[Media1Activity /onCreate ] onPostExecute");
            		if(nbMedia>0) {
            			displayListMedia();
            		}else {
            			//No media
            			TextView txt1=(TextView) findViewById(R.id.media1_txt_1);
            			txt1.setText(getString(R.string.media1_txt_result2));
            			//List view invisible
            			ListView lv=(ListView) findViewById(R.id.media1_list);
            			lv.setVisibility(View.GONE);
            			
            			LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.layout2);
            		    ImageView image = new ImageView(Media1Activity.this);
            		    
            		    image.setImageResource(R.drawable.media_nomedia);
            		    linearLayout1.addView(image);	
            		}
            			
            	} catch (RuntimeException e) {
                	TkTrace.log(context,"[Media1Activity/ onPostExecute ] RuntimeException="+e.getMessage()); 
                } catch (Exception e) {
                	TkTrace.log(context,"[Media1Activity/ onPostExecute ] Exception="+e.getMessage()); 
                }
            }
        }.execute();
	}
	
	void retreiveListMedia() {
		try {
			TkTrace.log(context,"[Media1Activity /retreiveListMedia ] debut");
			/* lecture du json */
			tkMedia = new TkMedia();
			tabMedia = tkMedia.readJson(context,"http://10.5.5.9:8080/gp/gpMediaList","http://10.5.5.9:8080/videos/DCIM/");
			nbMedia = Integer.parseInt((String)tabMedia.get("nbmedia"));
			TkTrace.log(context,"[Media1Activity /onCreate ] nb = "+(String)tabMedia.get("nbmedia"));
			listNameList = new String[nbMedia];
	   	  	listImages = new int[nbMedia];
	   	  
	   	 	for (int i=0; i<nbMedia; i++) {
				String mediaName = (String)tabMedia.get("media_name_"+i);
				String mediaType = (String)tabMedia.get("media_type_"+i);
				listNameList[i] =  mediaName;
				if(mediaType.equals("picture")){
					listImages[i] = R.drawable.media_picture;	
				}else{
					listImages[i] = R.drawable.media_movie;
				}
			}
	   	 	TkTrace.log(context,"[Media1Activity /retreiveListMedia ] sleep");
	   	 	Thread.sleep(2000);   
	   	 	TkTrace.log(context,"[Media1Activity /retreiveListMedia ] fin");
		} catch (RuntimeException e) {
	      TkTrace.log(context,"[Media1Activity/retreiveListMedia] RuntimeException : "+e.getMessage());
	 	} catch (Exception e) {
		      TkTrace.log(context,"[Media1Activity/retreiveListMedia] Exception : "+e.getMessage());
		}
	}

	void displayListMedia() {
	   	 	try {
	   	 		TkTrace.log(context,"[Media1Activity /displayListMedia ] debut");
		   	 	lv=(ListView) findViewById(R.id.media1_list);
				TkTrace.log(context,"[Media1Activity /displayListMedia ] adapter");
				final CustomListAdaptater  adapter = new CustomListAdaptater("Media1Activity",context,listNameList,listImages);
				TkTrace.log(context,"[Media1Activity /displayListMedia ] setAdapter");
			    lv.setAdapter(adapter);
				TkTrace.log(context,"[Media1Activity /displayListMedia ] CustomListAdaptater");
			    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			    	@Override
			    	public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		        		//Clique sur le media associe
			    		String mediaName = (String)tabMedia.get("media_name_"+position);
						String mediaType = (String)tabMedia.get("media_type_"+position);
			    		String url = (String)tabMedia.get("media_url_"+position);
		        		TkTrace.log(context,"[Media1Activity / onItemClick } url = "+url);
		        		if(mediaType.equals("picture")){
			        		//Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(url));
			        		//startActivity(intent);
		        			Bundle bundle = new Bundle();
		        			bundle.putString("medianame", mediaName);
		                 	bundle.putString("mediatype", mediaType);
		                 	bundle.putString("url", url);
		                 	//TkTrace.log(context, "item = "+listNameList[position]);
		     		    	Intent intent = new Intent(Media1Activity.this, MediaPictureActivity.class);
		     		    	intent.putExtras(bundle);
		     		    	startActivity(intent);
			        		
		        		}else {
		        			
		        			Bundle bundle = new Bundle();
		        			bundle.putString("medianame", mediaName);
		                 	bundle.putString("mediatype", mediaType);
		                 	bundle.putString("url", url);
		                 	//TkTrace.log(context, "item = "+listNameList[position]);
		     		    	Intent intent = new Intent(Media1Activity.this, MediaVideoActivity.class);
		     		    	intent.putExtras(bundle);
		     		    	startActivity(intent);
		        		}
		        		
		            }
			    });
	   	 	} catch (RuntimeException e) {
		      TkTrace.log(context,"[Media1Activity/displayListMedia] RuntimeException : "+e.getMessage());
		 	} catch (Exception e) {
			      TkTrace.log(context,"[Media1Activity/displayListMedia] Exception : "+e.getMessage());
			}
	}
	
	
	
	/*
	void initListMedia() {
		TkUser tkUser = TkAppManager.getTkUser();
		TkWifi tkWifi = new TkWifi();
		String wifiCameraConfigurated = "\""+tkUser.camerawifiname+"\"";
		String wifiCameraActual = tkWifi.getWifiStatus(this);
		
		if(wifiCameraActual.equals(wifiCameraConfigurated) == true){
			
			// lecture du json 
			tkMedia = new TkMedia();
			tabMedia = tkMedia.readJson(context,"http://10.5.5.9:8080/gp/gpMediaList","http://10.5.5.9:8080/videos/DCIM/");
			int nb = Integer.parseInt((String)tabMedia.get("nbmedia"));
			TkTrace.log(context,"[Media1Activity /onCreate ] nb = "+(String)tabMedia.get("nbmedia"));
			listNameList = new String[nb];
	   	  	listImages = new int[nb];
	   	  
	   	 	for (int i=0; i<nb; i++) {
				String mediaName = (String)tabMedia.get("media_name_"+i);
				String mediaType = (String)tabMedia.get("media_type_"+i);
				listNameList[i] =  mediaName;
				if(mediaType.equals("picture")){
					listImages[i] = R.drawable.media_picture;	
				}else{
					listImages[i] = R.drawable.media_movie;
				}
			}


			
	   	 	try {
		   	 	lv=(ListView) findViewById(R.id.media1_list);
				TkTrace.log(context,"[Media1Activity /onCreate ] adapter");
				final CustomListAdaptater  adapter = new CustomListAdaptater("Media1Activity",context,listNameList,listImages);
				TkTrace.log(context,"[Media1Activity /onCreate ] setAdapter");
			    lv.setAdapter(adapter);
				TkTrace.log(context,"[Media1Activity /onCreate ] CustomListAdaptater");
			    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			    	@Override
			    	public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		        		String url = (String)tabMedia.get("media_url_"+position);
		        		TkTrace.log(context,"[Media1Activity / onItemClick } url = "+url);
		        		Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(url));
		        		startActivity(intent);
		            }
			    });
	   	 	} catch (RuntimeException e) {
		      TkTrace.log(context,"[TkHttpJson/Media1Activity] RuntimeException : "+e.getMessage());
		 	} catch (Exception e) {
			      TkTrace.log(context,"[TkHttpJson/Media1Activity] Exception : "+e.getMessage());
			}

		    
		}else{
			//No media
			TextView txt1=(TextView) findViewById(R.id.media1_txt_1);
			txt1.setText(getString(R.string.media1_txt_result2));
			//List view invisible
			ListView lv=(ListView) findViewById(R.id.media1_list);
			lv.setVisibility(View.GONE);
			
			LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.layout2);
		    ImageView image = new ImageView(Media1Activity.this);
		    
		    image.setImageResource(R.drawable.media_nomedia);
		    linearLayout1.addView(image);
		    
		}
	}*/
}
