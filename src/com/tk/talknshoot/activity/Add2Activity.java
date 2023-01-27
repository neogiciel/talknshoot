package com.tk.talknshoot.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tk.talknshoot.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Add2Activity extends Activity {

	/* parameter */
	Context context;
	public static int [] listImages={R.drawable.camera_list_hero6black,R.drawable.camera_list_hero5black,R.drawable.camera_list_hero5session,R.drawable.camera_list_herosession,R.drawable.camera_list_heroplus,R.drawable.camera_list_hero4,R.drawable.camera_list_hero5session,R.drawable.camera_list_hero5session,R.drawable.camera_list_hero5session,R.drawable.camera_list_hero2};
    public static String [] listNameList={"HERO6 BLACK","HERO5 BLACK","HERO5 SESSION","HERO SESSION","HERO+","HERO4","HERO3+/3/2"};
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add2);
		
		/* context */
		context = this;

		ListView lv=(ListView) findViewById(R.id.add2_list);
	    lv.setAdapter(new CustomListAdaptater("TestActivity",this,listNameList,listImages));
	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
            	Bundle bundle = new Bundle();
             	bundle.putString("camera_selected", listNameList[position]);
             	//TkTrace.log(context, "item = "+listNameList[position]);
 		    	Intent intent = new Intent(Add2Activity.this, Add3Activity.class);
 		    	intent.putExtras(bundle);
 		    	startActivity(intent);
 		    	finish();
 		    	
            }
        });
	    
		/*
		final ListView listview = (ListView) findViewById(R.id.add2_list);
        String[] camera = getResources().getStringArray(R.array.add2_txt_list);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < camera.length; ++i) {
            list.add(camera[i]);
        }
        
        
        final ArrayAdapter  adapter = new ArrayAdapter (this,android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,int position, long id) {
            
            	  
            	final String item = (String) parent.getItemAtPosition(position);
            	
            	Bundle bundle = new Bundle();
            	bundle.putString("camera_selected", item);
		    	//TkTrace.log(context, "item = "+item);
		    	Intent intent = new Intent(Add2Activity.this, Add3Activity.class);
		    	intent.putExtras(bundle);
		    	startActivity(intent);
		    	finish();
            }

        });*/
	}
}

