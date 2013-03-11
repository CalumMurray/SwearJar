package com.hacku.swearjar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

        deserializeMaps();
        
        
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

		try {
			// Update hashmap
			String newWord = prefs.getString("blacklistWord", "default");
			float newCharge = Float.valueOf(prefs.getString("blacklistCharge", "0.5"));
			
			if (newWord == null || newWord.equals("") || newCharge < 0f)
				return;
				
			blacklist.put(newWord, newCharge);
			serializeMaps();
		}
		catch (ClassCastException cce)
		{
			
			return;
		}
		catch (NumberFormatException nfe)
		{
			
		}
		
	}
	
	
    public void onDestroy()
    {
		serializeMaps();
    }

	private void serializeMaps() {
		
		try 
		{
			FileOutputStream blackListFileOut = new FileOutputStream(ROOTPATH + "/blacklist.sj");
			ObjectOutputStream blackListOut = new ObjectOutputStream(blackListFileOut);
			blackListOut.writeObject(blacklist);
			
			blackListOut.close();
	        blackListFileOut.close();
	        
	        
	        FileOutputStream occurrenceMapFileOut = new FileOutputStream(ROOTPATH + "/occurrences.sj");
			ObjectOutputStream occurrenceMapOut = new ObjectOutputStream(occurrenceMapFileOut);
			occurrenceMapOut.writeObject(swearOccurrences);
			
			occurrenceMapOut.close();
			occurrenceMapFileOut.close();
		}
		catch (IOException ioe)
		{
			System.err.println("Problem saving lists");
		}
		
		
	}
	
	private void deserializeMaps() {
        
		try
        {
           FileInputStream blackListFileIn = new FileInputStream(ROOTPATH + "/blacklist.sj");
           ObjectInputStream blacklistIn = new ObjectInputStream(blackListFileIn);
           blacklist = (HashMap<String, Float>) blacklistIn.readObject();
           blacklistIn.close();
           blackListFileIn.close();
           
           
           FileInputStream occurrenceFileIn = new FileInputStream(ROOTPATH + "/occurrences.sj");
           ObjectInputStream occurrencesIn = new ObjectInputStream(occurrenceFileIn);
           swearOccurrences = (HashMap<String, Integer>) occurrencesIn.readObject();
           occurrencesIn.close();
           occurrenceFileIn.close();
           
        }
		catch(IOException i)
        {
			System.err.println("Problem loading lists");
			return;
        } 
		catch (ClassNotFoundException e) 
        {
			System.err.println("Problem loading lists");
			return;
		}
	}
	

}
