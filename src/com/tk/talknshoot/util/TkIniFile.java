package com.tk.talknshoot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import android.os.Environment;

public class TkIniFile {

   private Properties configuration;
   private String configurationFile = "talknshoot.ini";
   
   public TkIniFile() {
       configuration = new Properties();
   }

   public boolean load() {
       boolean retval = false;

       try {
           //configuration.load(new FileInputStream(this.configurationFile));
    	   //configuration.load(new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.txt"));
    	   configuration.load(new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/"+configurationFile));
           retval = true;
       } catch (IOException e) {
           System.out.println("Configuration error: " + e.getMessage());
       }

       return retval;
   }

   public boolean store() {
       boolean retval = false;
       try {
           //configuration.store(new FileOutputStream(this.configurationFile), null);
    	   configuration.store(new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/"+configurationFile), null);
           retval = true;
       } catch (IOException e) {
           System.out.println("Configuration error: " + e.getMessage());
       }

       return retval;
   }

   public void set(String key, String value) {
       configuration.setProperty(key, value);
   }

   public String get(String key) {
       return configuration.getProperty(key);
   }
   
   public boolean delete() {
       boolean retval = false;

       try {
    	   File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/"+configurationFile);
           f.delete();
           retval = true;
       } catch (Exception e) {
           System.out.println("Configuration error: " + e.getMessage());
       }

       return retval;
   }

}
