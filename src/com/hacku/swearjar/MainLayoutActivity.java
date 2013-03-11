package com.hacku.swearjar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Initial activity to bootstrap application.
 * 
 * @author Calum
 */
public class MainLayoutActivity extends Activity {
	
	private TelephonyManager teleManager;
	private PhoneStateListener callListener;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        callListener = new PhoneCallListener(this);
        teleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        
        teleManager.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);	//Register Listener to be notified of changes to the phone call state.
        
    }

    

    @Override
    public void onDestroy() {
    	super.onDestroy();
    	
    	//TODO: Unregister listener and clean up resources etc.
    	teleManager.listen(callListener, PhoneStateListener.LISTEN_NONE); //Unregister
    	
    	//Serialize maps
    	((SwearJarApplication) getApplication()).onDestroy();
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
