package com.hacku.swearjar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;


/**
* Facilitates global application state access - the word blacklist
*
* @author Calum 
*/
public class SwearJarApplication extends Application {

	public static final String ROOTPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	private static int notificationID = 0;
	
	private ArrayList<BlackListItem> blackListItems = new ArrayList<BlackListItem>();
	private Handler uiCallback;
	
    @Override
    public void onCreate()
    {
        super.onCreate();
        deserializeBlackList();
        
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
		
        sendNotification();

		//Tell the UI we're done updating
		if(uiCallback != null)
			uiCallback.sendEmptyMessage(0);
		
		serializeBlackList();
	}

	//Sends a notification to the notification bar when new word occurrences detected
	private void sendNotification() {
        NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.appicon)
		        .setContentTitle("Words Detected")
		        .setContentText("SwearJar has detected more occurrences of blacklisted words.");
		
        Intent resultIntent = new Intent(this, MainLayoutActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(MainLayoutActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager notificationMan = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationMan.notify(++notificationID, mBuilder.build());
	}
	

	//After user has paid, word occurrences reset to zero
	public void resetOccurrences() {
		for (BlackListItem item : getBlackListItems())
			item.setOccurrences(0);
		
		serializeBlackList();
		
	}

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



	// Check for Internet access
	public boolean hasInternetConnectivity() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo().isConnectedOrConnecting() == false)
			return false;
		return true;
	}

	
	

}
