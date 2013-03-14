package com.hacku.swearjar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.Application;
import android.os.Environment;
import android.os.Handler;


/**
* Facilitates global application state - SharedPreferences (the word blacklist)
*
* @author Calum 
* */
public class SwearJarApplication extends Application /*implements OnSharedPreferenceChangeListener*/ {

	public static final String ROOTPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	
	//private SharedPreferences prefs;
    //private SharedPreferences.Editor editor;


	private ArrayList<BlackListItem> blackListItems = new ArrayList<BlackListItem>();
	private Handler uiCallback;
	
    @Override
    public void onCreate()
    {
        super.onCreate();
        
            //Set up preferences
//        prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        prefs.registerOnSharedPreferenceChangeListener(this);

        deserializeBlackList();
        
        //Test data in blacklist TODO: Remove
       // blackListItems.add(new BlackListItem("fuck", BigDecimal.valueOf(1.00), 3));
    }

	public void subscribe(Handler uiCallback){
		this.uiCallback = uiCallback;
	}

	public ArrayList<BlackListItem> getBlackListItems() {
		return blackListItems;
	}
	
	public void addBlackListItem(BlackListItem itemToAdd) {
		blackListItems.add(itemToAdd);
		serializeBlackList();
	}
	
	public void removeBlackListItem(int blackListItemIndex) {
		blackListItems.remove(blackListItemIndex);
		serializeBlackList();
	}
	
	public void editBlackListItem(BlackListItem itemToEdit, String word, BigDecimal charge) {
		
		itemToEdit.setWord(word);
		itemToEdit.setCharge(charge);
		serializeBlackList();
	}
	
	//Adds to occurrences the number of times that the word it appears in the utterance.  Not case sensitive
	public void addOccurrences(String utterance) {
		
		//Add occurrences from last fetch to application's blacklist
        for (BlackListItem item : getBlackListItems())
        	item.addOccurrences(utterance); 
		
		//Tell the UI we're done updating
		if(uiCallback != null)
			uiCallback.sendEmptyMessage(0);
	}
	

	/**
	* Called when word blacklist preference changed.
	* SharedPreferences here updated to reflect change and remain accessible globally
	*/
//	@Override
//	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//
//		try {
//			// Update hashmap
//			String newWord = prefs.getString("blacklistWord", "default");
//			BigDecimal newCharge = new BigDecimal(prefs.getString("blacklistCharge", "0.5"));
//			
//			if (newWord == null || newWord.equals("") || newCharge.compareTo(BigDecimal.ZERO) < 0)
//				return;
//				
//			blackListItems.add(new BlackListItem(newWord, newCharge, 0));
//			serializeBlackList();
//		}
//		catch (ClassCastException cce)
//		{
//			
//			return;
//		}
//		catch (NumberFormatException nfe)
//		{
//			
//		}
//		
//	}
	

	private void serializeBlackList() {
		
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
	private void deserializeBlackList() {
        
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
		BigDecimal total = new BigDecimal(0);
		for (BlackListItem item : blackListItems)
		{
			//add words total charge to running total
			total = total.add(item.getTotalCharge());
		}
		return total;
	}



	
	

}
