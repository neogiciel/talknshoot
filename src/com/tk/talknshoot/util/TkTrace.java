package com.tk.talknshoot.util;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class TkTrace {
	
	static String message;
	
	/** constructeur */
	public TkTrace(){
		
	}
	/** log */
	public static void log(Context p_context, String p_message ){
		try{
			//TextView
			//TextView txtView = (TextView) ((HomeActivity)p_context).findViewById(R.id.txtLog);
			//txtView.setText(p_message);
			//Log
							//Log.d("TALKANDSHOOT :",p_message);
			//Toast
			//Toast.makeText(p_context,p_message, Toast.LENGTH_LONG).show();
			/* log file */
			TkTraceFile tkTraceFile = new TkTraceFile();
			tkTraceFile.write(p_message);
			
		}catch(Exception e){
			//Toast.makeText(p_context,"(TraceError) catch:"+e.toString(), Toast.LENGTH_LONG).show();
		}

	}
};
