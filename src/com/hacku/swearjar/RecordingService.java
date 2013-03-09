package com.hacku.swearjar;

import org.apache.commons.lang3.StringUtils;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;

import com.hacku.swearjar.speechapi.GoogleSpeechAPI;
import com.hacku.swearjar.speechapi.SpeechResponse;

/**
 * Service to run as a thread in the background.
 * Performs actual recording of a phone call to a file.
 * 
 * @author Calum
 */
public class RecordingService extends Service implements Runnable {
	
	private MediaRecorder recorder;
	private boolean recording = false;
	private static String filePath = SwearJarApplication.ROOTPATH + "/swearjar";
	private long timeStamp;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		new Thread(this).start();	//Starts "run()" as a background thread.

	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		
	}

	@Override
	public void onDestroy() {
		stopRecording();
	}


	@Override
	public void run() {
		startRecording();
		
	}

	private void startRecording() {
		//Set up recorder TODO: Do once in onCreate??
		recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_UPLINK);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);		//TODO: Record or Convert to 'Speex' or 'FLAC' format?
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        timeStamp = System.currentTimeMillis();						//Make unique filename
        recorder.setOutputFile(filePath + timeStamp + ".3gp");
        try {
            recorder.prepare();
        } catch (Exception e) {
        	System.out.println("Problem preparing Recorder.");
            e.printStackTrace();
        }
        recording = true;
        recorder.start();	//RECORD!

	}
	
	private void stopRecording() {
        recorder.stop();
        recorder.release();
        recording = false;
        
        
        addOccurrences();
       
    }

	private void addOccurrences() {
		SpeechResponse response = GoogleSpeechAPI.getSpeechResponse(filePath + timeStamp + ".3gp");
	        
	        
        String utterance = response.getBestUtterance();		//TODO: Possible multiple hypotheses?
        
        SwearJarApplication application = (SwearJarApplication) this.getApplication();
        
        //Add occurrences from last fetch to application's HashMap
        for (String word : application.getBlacklist().keySet())
        {
        	int occurrences = StringUtils.countMatches(utterance, word);	
        	application.getOccurrenceMap().put(word, occurrences);	//TODO: INCREMENT not overwrite occurrences
        }       
	 
		
	}
	
	
}
