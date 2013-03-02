package com.hacku.swearjar;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;


/**
* Facilitates global application state - SharedPreferences (the word blacklist)
*
* @author Calum 
* */
public class SwearJarApplication extends Application implements OnSharedPreferenceChangeListener {

	
	private SharedPreferences prefs;
    //private SharedPreferences.Editor editor;

	//TODO: Hold word blacklist here?:  private HashMap<String, float> blacklist;  
	
    @Override
    public void onCreate()
    {
        super.onCreate();
        
            //Set up preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

    }

    
	/**
	* Called when word blacklist preference changed.
	* SharedPreferences here updated to reflect change and remain accessible globally
	*/
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals("blacklistWord"))
		{
			//TODO: Update hashmap
		}
		else if (key.equals("blacklistCharge"))
		{
			//TODO: Update hashmap
		}
		
	}

}
