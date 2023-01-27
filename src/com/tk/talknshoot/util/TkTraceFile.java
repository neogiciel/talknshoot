package com.tk.talknshoot.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import android.os.Environment;

public class TkTraceFile {

	private InputStream file;
	private String configurationFile = "talknshoot-log.txt";
   //private Properties configuration;
   //private String configurationFile = "talknshoot-log.txt";
   
   public TkTraceFile() {
       
   }
   
   public boolean write(String message){
   try {

		//String fpath = "/sdcard/"+fname+".txt";
	   String fpath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/"+configurationFile;
		
		File file = new File(fpath);

		// If file does not exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		//bw.write(message);
		bw.append(message+"\n");
		bw.close();
		return true;

	} catch (IOException e) {
		e.printStackTrace();
		return false;
	}
   }

}
