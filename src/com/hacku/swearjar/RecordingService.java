package com.hacku.swearjar;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;

public class RecordingService extends Service implements Runnable {

	private static int fileNameIncrementer = 0;
	private MediaRecorder recorder;
	private boolean recording = false;
	
	
	
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

	public void startRecording() {
		//Set up recorder TODO: Do once in onCreate??
		recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);	//TODO: Record or Convert to 'Speex' or 'FLAC' format?
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/swearjar" + System.currentTimeMillis() + ".3gp");
        try {
            recorder.prepare();
        } catch (Exception e) {
        	System.out.println("Problem preparing Recorder.");
            e.printStackTrace();
        }
        recording = true;
        recorder.start();	//RECORD!

	}
	
	public void stopRecording() {
        recorder.stop();
        recorder.release();
        recording = false;
    }
	
}
