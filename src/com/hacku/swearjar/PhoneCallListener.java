package com.hacku.swearjar;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Listens for a change in phone call state.  When phone is off the hook
 * it starts the service to record to a file.
 * 
 * @author Calum
 */
public class PhoneCallListener extends PhoneStateListener {
	
	private Context context;
	
	PhoneCallListener(Context context) {
        super();
        this.context = context;
    }
    
    public void onCallStateChanged(int state, String incomingNumber) {
    	Intent service = new Intent(context, RecordingService.class);
        if (state == TelephonyManager.CALL_STATE_OFFHOOK)
        {
        	// If you're on a phone call
        	
        	//Start Recording service on a thread.
            context.startService(service);  
        }
        else
        {
        	
        	if (isMyServiceRunning()) {
	        	//Stop recording (by calling Service.onDestroy())
	        	context.stopService(service);
        	}
      
        }
                 
           
       
        
    }
    

	private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (RecordingService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    
}
