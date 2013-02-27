package com.hacku.swearjar;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class PhoneCallListener extends PhoneStateListener {
	
	private Context context;
	
	PhoneCallListener(Context context) {
        super();
        this.context = context;
    }
    
    public void onCallStateChanged(int state, String incomingNumber) {
    	
        if (state == TelephonyManager.CALL_STATE_OFFHOOK)
        {
        	// If you're on a phone call
        	
        	//TODO: Start Recording service on a thread.
            Intent serv = new Intent(context, RecordingService.class);
            context.startService(serv);  
        }
        else
        {
        	//TODO: Stop recording
        }
                 
           
       
        
    }
    
}
