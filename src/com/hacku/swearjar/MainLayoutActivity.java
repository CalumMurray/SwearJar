package com.hacku.swearjar;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;

import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Initial activity to bootstrap application.
 * 
 * @author Calum
 */
public class MainLayoutActivity extends ListActivity {
	
	private static final String JUST_GIVING_URI = "www.justgiving.com/donation/direct/charity/#1?frequency=single&amount=#2";
	private TelephonyManager teleManager;
	private PhoneStateListener callListener;
	private Button payButton;

	private SwearJarApplication application;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        application = (SwearJarApplication) getApplication();
        
        //Setup ListAdapter
        //TODO: parse blacklist item into 3 different fields defined in list_item.xml
        ArrayList<BlackListItem> blackListWords = (ArrayList<BlackListItem>) application.getBlackListItems();	//Get blacklisted words from SwearJarApplication as an array
        BlackListArrayAdapter adapter = new BlackListArrayAdapter(this, blackListWords);
        setListAdapter(adapter);
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        //Setup onClickListener for Pay button
        payButton = (Button) findViewById(R.id.payButton);
        payButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Pay through JUST GIVING!!!!!
				
				if (!hasInternetConnectivity()){
					Toast.makeText(MainLayoutActivity.this, "Internet Access is required to pay.", Toast.LENGTH_LONG).show();
					return;
				}
                
                //Loop through words getting total cost
                BigDecimal totalCost = application.getTotalCostDue();
                NumberFormat formatter = NumberFormat.getNumberInstance();
                formatter.setMaximumFractionDigits(2);
                String webPage = JUST_GIVING_URI.replace("#2", formatter.format(totalCost));
                
                
                //Start a web browser to go to JustGiving home page
	           Intent intent = new Intent(Intent.ACTION_VIEW);
	           intent.setData(Uri.parse(webPage));
	           startActivity(intent);				
			}
		});
        
        
        
        //Listen for PhoneCalls
        callListener = new PhoneCallListener(this);
        teleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        
        teleManager.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);	//Register Listener to be notified of changes to the phone call state.
        
    }

    

    @Override
    public void onDestroy() {
    	super.onDestroy();
    	
    	//TODO: Unregister listener and clean up resources etc.
    	teleManager.listen(callListener, PhoneStateListener.LISTEN_NONE); //Unregister
    	
    	//Serialise maps
    	((SwearJarApplication) getApplication()).onDestroy();
    }
    
  //Check for internet access
    public boolean hasInternetConnectivity()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo().isConnectedOrConnecting() == false)
        	return false;
        return true;
    }
    
    
    
    //------------MENU STUFF-------------
    
  //What to do when hard 'MENU' button is clicked
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);    //Inflate menu.xml layout
        return true;
    }


    //What to do when 'MENU' options selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuItemPrefs:    startActivity(new Intent(this, WordsActivity.class));   //Open word preferences activity
                                        break;


        }
        return true;
    }
}
