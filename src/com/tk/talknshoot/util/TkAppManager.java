package com.tk.talknshoot.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import com.tk.talknshoot.R;

import android.content.Context;

public class TkAppManager {
	
	/** elements */
	static protected Context context;
	static protected TkUser tkUser;
	static protected TkCameraInfo tkCameraInfo;
	//static protected TkWifi tkWifi;
	//static protected TkIniFile tkIniFile;
	
	/** Constructor */
	private TkAppManager()
	{}
 
	/** Instance unique non préinitialisée */
	private static TkAppManager INSTANCE = null;
			
	/** Point d'accès pour l'instance unique du singleton */
	public static synchronized TkAppManager getInstance()
	{			
		if (INSTANCE == null){
			INSTANCE = new TkAppManager();	
		}
		return INSTANCE;
	}
	
	/** init manager */
	static public boolean initManager(){
		try{
			/* load inifile*/
			tkUser =new TkUser();
			tkCameraInfo = new TkCameraInfo();
			TkIniFile tkIniFile = new TkIniFile();
			//Internet actif
			TkWifi tkWifi = new TkWifi();
			boolean internetIsActiv = tkWifi.isNetworkConnected(context);
			if(internetIsActiv==true) {
				TkTrace.log(context,"[TkAppManager/iniManager()] : Internet ACTIF");
			}else {
				TkTrace.log(context,"[TkAppManager/iniManager()] : Internet NON ACTIF");
			}
			
			
			//Temporaire
			//tkIniFile.delete();
			boolean isOpen = tkIniFile.load();
			if(isOpen == true){
				TkTrace.log(context,"[TkAppManager/iniManager()] : Fichier init Ouvert");
				/** user */
				tkUser.id = tkIniFile.get("id");
				tkUser.firstname = tkIniFile.get("firstname");
				tkUser.lastname = tkIniFile.get("lastname");
				tkUser.email = tkIniFile.get("email");
				tkUser.adress = tkIniFile.get("adress");
				tkUser.city = tkIniFile.get("city");
				tkUser.country = tkIniFile.get("country");
				tkUser.phone = tkIniFile.get("phone");
				tkUser.mobile = tkIniFile.get("mobile");
				tkUser.lang = tkIniFile.get("lang");
				
				/** version */
				tkUser.firstvisit = tkIniFile.get("firstvisit");
				tkUser.lastvisit = tkIniFile.get("lastvisit");
				tkUser.versionid = tkIniFile.get("versionid");
				tkUser.versiontype = tkIniFile.get("versiontype");
				tkUser.versionabo = tkIniFile.get("versionabo");
				tkUser.versionduration = tkIniFile.get("versionduration");
				tkUser.versionindex = tkIniFile.get("versionindex");
			
				/** camera */
				tkUser.camerabrand = tkIniFile.get("camerabrand");
				tkUser.cameratype = tkIniFile.get("cameratype");
				tkUser.camerawifiname = tkIniFile.get("camerawifiname");
				tkUser.camerawifipassword = tkIniFile.get("camerawifipassword");
			
				TkTrace.log(context,"[TkAppManager/iniManager()] :camerawifiname("+tkUser.camerawifiname+")");
				TkTrace.log(context,"[TkAppManager/iniManager()] :internetIsActiv("+tkWifi.getWifiConnected(context)+")");
				
				if(tkWifi.getWifiConnected(context).equals(tkUser.camerawifiname)){
					internetIsActiv = false;
					TkTrace.log(context,"[TkAppManager/iniManager()] : Internet NON ACTIF (meme que camera)");
				}else {
					
				}
				
				/** version internet*/
				if(internetIsActiv==true) {
					TkHttpJson httpJson = new TkHttpJson();
					TkTrace.log(context,"[TkAppManager/iniManager()] : Recupere la version admin");
					HashMap map = httpJson.getAdminValue(context, "name=getversion&param1="+context.getString(R.string.versionid));
					int nb = Integer.parseInt((String)map.get("nbelement"));
					if(nb>0){
						tkUser.versionid = (String)map.get("IDVERSION_0");
						tkUser.versiontype = (String)map.get("TYPE_0"); 
						tkUser.versionabo = (String)map.get("ABO_0");
						tkUser.versionduration = (String)map.get("DURATION_0");
						tkUser.versionindex = (String)map.get("INDEXVERSION_0");
						tkIniFile.set("versionid",tkUser.versionid);
						tkIniFile.set("versiontype",tkUser.versiontype);
						tkIniFile.set("versionabo",tkUser.versionabo);
						tkIniFile.set("versionduration",tkUser.versionduration);
						tkIniFile.set("versionindex",tkUser.versionindex);
					}
				}
				
				
				/* current date*/
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
				String currentdate = dateformat.format(Calendar.getInstance().getTime());
				tkUser.lastvisit = currentdate;
				tkIniFile.set("lastvisit",tkUser.lastvisit);
				//tkIniFile.set("firstname","patrice");
				//tkIniFile.set("lastname","radin");
				//tkIniFile.set("wifipassword","romane88");
				tkIniFile.store();
			
				/* stat application */
				if(internetIsActiv==true) {
					TkHttpStat httpStat = new TkHttpStat();
					httpStat.stat(context,"session","1");
				}
				
		    	/** version valide*/
		    	TkTrace.log(context,"[TkAppManager/iniManager()] : Test de la validite de la version");
		    	TkTrace.log(context,"[TkAppManager/iniManager()] : firstVisit = "+tkUser.firstvisit);
		    	TkTrace.log(context,"[TkAppManager/iniManager()] : lastvisit = "+tkUser.lastvisit);
		    	
				TkTool tktool = new TkTool();
				if(tktool.isGoodAppVersion(context,tkUser.firstvisit,tkUser.versiontype,tkUser.versionduration)== false){ 
		    		//Mise a jour application
			    	TkTrace.log(context,"[TkAppManager/iniManager()] : Version A METTRE A JOUR");
		    		return false;	
		    	}else{
			    	TkTrace.log(context,"[TkAppManager/iniManager()] : Version OK");
		    	}
				
							
			}else{
				
				TkTrace.log(context,"[TkAppManager/iniManager()] : Fichier nexiste pas : 1ere instalation");
				
				/* current date*/
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
				String currentdate = dateformat.format(Calendar.getInstance().getTime());
				
				/** user */
				tkUser.id = "guest";
				tkUser.firstname = "guest";
				tkUser.lastname = "guest";
				tkUser.email = "empty";
				tkUser.adress = "empty";
				tkUser.city = "empty";
				tkUser.country = "empty";
				tkUser.phone = "empty";
				tkUser.mobile = "empty";
				tkUser.lang = "empty";
				
				tkIniFile.set("id",tkUser.id);
				tkIniFile.set("firstname",tkUser.firstname);
				tkIniFile.set("lastname",tkUser.lastname);
				tkIniFile.set("email",tkUser.email);
				tkIniFile.set("adress",tkUser.adress);
				tkIniFile.set("city",tkUser.city);
				tkIniFile.set("country",tkUser.country);
				tkIniFile.set("phone",tkUser.phone);
				tkIniFile.set("mobile",tkUser.mobile);
				tkIniFile.set("lang",tkUser.lang);
				
				/** version */
				tkUser.firstvisit = currentdate;
				tkUser.lastvisit = currentdate;
				tkUser.versionid = context.getString(R.string.versionid);
				tkUser.versiontype = context.getString(R.string.versiontype); 
				tkUser.versionabo = context.getString(R.string.versionabo);
				tkUser.versionduration = context.getString(R.string.versionduration);
				tkUser.versionindex = context.getString(R.string.versionindex);
				TkTrace.log(context,"[TkAppManager/iniManager()] versionduration :"+ tkUser.versionduration);
				
				/** version internet*/
				if(internetIsActiv==true) {
					TkHttpJson httpJson1 = new TkHttpJson();
					HashMap map1 = httpJson1.getAdminValue(context, "name=getversion&param1="+context.getString(R.string.versionid));
					int nb1 = Integer.parseInt((String)map1.get("nbelement"));
					TkTrace.log(context,"[TkAppManager/iniManager()] nb1 :"+ nb1);
					if(nb1>0){
						tkUser.versionid = (String)map1.get("IDVERSION_0");
						tkUser.versiontype = (String)map1.get("TYPE_0"); 
						tkUser.versionabo = (String)map1.get("ABO_0");
						tkUser.versionduration = (String)map1.get("DURATION_0");
						tkUser.versionindex = (String)map1.get("INDEXVERSION_0");
					}
				}
				
		    	/** user internet*/
				if(internetIsActiv==true) {
					TkHttpJson httpJson2 = new TkHttpJson();
					HashMap map2 = httpJson2.getAdminValue(context, "name=newuser&param1="+context.getString(R.string.versionid));
					int nb2 = Integer.parseInt((String)map2.get("nbelement"));
					if(nb2>0){
						tkUser.id = (String)map2.get("IDUSER_0");
						tkIniFile.set("id",tkUser.id);
						TkTrace.log(context,"[TkAppManager/iniManager()] : iduser = "+tkUser.id);
					}
				}
		    	
		    	/* sauvegarde */
		    	tkIniFile.set("firstvisit",tkUser.firstvisit);
				tkIniFile.set("lastvisit",tkUser.lastvisit);
				tkIniFile.set("versionid",tkUser.versionid);
				tkIniFile.set("versiontype",tkUser.versiontype);
				tkIniFile.set("versionabo",tkUser.versionabo);
				tkIniFile.set("versionduration",tkUser.versionduration);
				tkIniFile.set("versionindex",tkUser.versionindex);
							
				/** camera */
				tkUser.camerabrand = "empty";
				tkUser.cameratype = "empty";
				tkUser.camerawifiname = "empty";
				tkUser.camerawifipassword = "empty";
				
				tkIniFile.set("camerabrand",tkUser.camerabrand);
				tkIniFile.set("cameratype",tkUser.cameratype);
				tkIniFile.set("camerawifiname",tkUser.camerawifiname);
				tkIniFile.set("camerawifipassword",tkUser.camerawifipassword);
				
				TkTrace.log(context,"[TkAppManager/iniManager()] firstvisit :"+ tkUser.firstvisit);
				TkTrace.log(context,"[TkAppManager/iniManager()] lastvisit :"+ tkUser.lastvisit);
				TkTrace.log(context,"[TkAppManager/iniManager()] : Enregitrement du fichier ini");
				tkIniFile.store();
				
				/* stat application */
				if(internetIsActiv==true) {
					TkHttpStat httpStat = new TkHttpStat();
					httpStat.stat(context,"install","1");
					httpStat.stat(context,"session","1");
				}
			}
			} catch (Exception e) {
			TkTrace.log(context,"[TkAppManager/iniManager()] : Exception="+e);
		}
		
		return true;
	}
	/** setContext */
	static public void setContext(Context p_context){
		context = p_context;
	}
	/** getContext */
	static public Context getContext(){
		return context ;
	}

	/** getTkUser */
	static public TkUser getTkUser(){
		return tkUser ;
	}
	/** getTkCameraInfo */
	static public TkCameraInfo getTkCameraInfo(){
		return tkCameraInfo;
	}

}
