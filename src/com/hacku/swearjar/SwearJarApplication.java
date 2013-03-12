package com.hacku.swearjar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

//	private HashMap<String, Float> blacklist =  new HashMap<String, Float>();  
//	private HashMap<String, Integer> swearOccurrences =  new HashMap<String, Integer>();  
	private List<BlackListItem> blackListItems = new ArrayList<BlackListItem>();
	
    @Override
    public void onCreate()
    {
        super.onCreate();
        
            //Set up preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        deserializeMaps();
        
        
    }

	

	public List<BlackListItem> getBlacklist() {
		return blackListItems;
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
			BigDecimal newCharge = new BigDecimal(prefs.getString("blacklistCharge", "0.5"));
			
			if (newWord == null || newWord.equals("") || newCharge.compareTo(BigDecimal.ZERO) < 0)
				return;
				
			blackListItems.add(new BlackListItem(newWord, newCharge, 0));
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
			blackListOut.writeObject(blackListItems);
			
			blackListOut.close();
	        blackListFileOut.close();

		}
		catch (IOException ioe)
		{
			System.err.println("Problem saving lists");
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void deserializeMaps() {
        
		try
        {
           FileInputStream blackListFileIn = new FileInputStream(ROOTPATH + "/blacklist.sj");
           ObjectInputStream blacklistIn = new ObjectInputStream(blackListFileIn);
           blackListItems = (ArrayList<BlackListItem>) blacklistIn.readObject();
           blacklistIn.close();
           blackListFileIn.close();

           
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



	public BigDecimal getTotalCostDue() {
		BigDecimal total = BigDecimal.ZERO;
		for (BlackListItem item : blackListItems)
		{
			String word = item.getWord();
			BigDecimal charge = item.getCharge();
			int occurrences = item.getOccurrences();
			
			//add to running total
			charge.multiply(new BigDecimal(occurrences));
			total.add(charge);
			
		}
		return total;
	}
	

}
