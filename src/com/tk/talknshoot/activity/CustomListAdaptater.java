package com.tk.talknshoot.activity;

import java.util.ArrayList;


import java.util.HashMap;

import com.tk.talknshoot.R;

import android.app.Activity;
import android.content.Context;
import android.view.View.OnClickListener;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomListAdaptater extends BaseAdapter{

	Context context;
	String nameActivity;
    String [] result;
    int [] imageId;
    private static LayoutInflater inflater=null;
    
    
    public CustomListAdaptater(String p_nameActivity,Context p_context, String[] p_prgmNameList, int[] p_prgmImages) {
        // TODO Auto-generated constructor stub
        result=p_prgmNameList;
        context=p_context;
        imageId=p_prgmImages;
        nameActivity = p_nameActivity;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    /*@Override
    public void onClick(View v) {
          // Log.v("CustomAdapter", "=====Row button clicked=====");
    }*/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;       
        rowView = inflater.inflate(R.layout.custom_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);       
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);         
        
       /* rowView.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });*/   
        return rowView;
    }

} 