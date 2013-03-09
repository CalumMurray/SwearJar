package com.hacku.swearjar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Environment;
import android.preference.PreferenceManager;


/**
* Facilitates global application state - SharedPreferences (the word blacklist)
*
* @author Calum 
* */
public class SwearJarApplication extends Application implements OnSharedPreferenceChangeListener {

	public static final String ROOTPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	
	private SharedPreferences prefs;
    //private SharedPreferences.Editor editor;

	private HashMap<String, Float> blacklist =  new HashMap<String, Float>();  
	private HashMap<String, Integer> swearOccurrences =  new HashMap<String, Integer>();  

	
    @Override
    public void onCreate()
    {
        super.onCreate();
        
            //Set up preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

    }

	public HashMap<String, Float> getBlacklist() {
		return blacklist;
	}
	
	public HashMap<String, Integer> getOccurrenceMap() {
		return swearOccurrences;
	}


	/**
	* Called when word blacklist preference changed.
	* SharedPreferences here updated to reflect change and remain accessible globally
	*/
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		
		try 
		{
		
			if (key.equals("blacklistWord"))
			{
				//TODO: Update hashmap
		
				//Serialize
				FileOutputStream blackListFileOut = new FileOutputStream(ROOTPATH + "/blacklist.sj");
				ObjectOutputStream blackListOut = new ObjectOutputStream(blackListFileOut);
				blackListOut.writeObject(blacklist);
				
				blackListOut.close();
		        blackListFileOut.close();
			}
			else if (key.equals("blacklistCharge"))
			{
				//TODO: Update hashmap
				
				//Serialize
				FileOutputStream occurrenceMapFileOut = new FileOutputStream(ROOTPATH + "/occurrences.sj");
				ObjectOutputStream occurrenceMapOut = new ObjectOutputStream(occurrenceMapFileOut);
				occurrenceMapOut.writeObject(swearOccurrences);
				
				occurrenceMapOut.close();
				occurrenceMapFileOut.close();
			}
		}
		catch (IOException ioe)
		{
			System.err.println("Problem saving lists");
		}
		
		

	}

}
