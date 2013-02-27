package com.hacku.swearjar;

import android.os.Bundle;
import android.app.Activity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;


public class MainLayoutActivity extends Activity {
	
	private TelephonyManager teleManager;
	private PhoneStateListener callListener;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        
       //TODO: Prefs then run service/listener in background?
        
        callListener = new PhoneCallListener(this);
        teleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        
        teleManager.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);	//Register Listener to be notified of changes to the phone call state.
        
    }

    

    @Override
    public void onDestroy() {
    	//TODO: Unregister listener and clean up resources etc.
    	teleManager.listen(callListener, PhoneStateListener.LISTEN_NONE); //Unregister
    }
    
    
    //Do we want a menu?
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_layout, menu);
        return true;
    }
}
