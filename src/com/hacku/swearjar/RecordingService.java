package com.hacku.swearjar;

import java.io.File;
import javaFlacEncoder.FLAC_FileEncoder;

import android.app.Service;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.os.Environment;
import android.os.IBinder;


public class RecordingService extends Service implements Runnable {
	
	private ExtAudioRecorder recorder;
	private boolean recording = false;
	private static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/swearjar";
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
		convertToFlac();
	}


	@Override
	public void run() {
		startRecording();
		
	}

	private void startRecording() {
		//Set up recorder TODO: Do once in onCreate??
		/*recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_UPLINK);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);		//TODO: Record or Convert to 'Speex' or 'FLAC' format?
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);*/
		recorder = new ExtAudioRecorder(true, AudioSource.VOICE_UPLINK, 16000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        timeStamp = System.currentTimeMillis();						//Make unique filename
        recorder.setOutputFile(filePath + timeStamp + ".wav");
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
    }
	
	private void convertToFlac() {
		new FLAC_FileEncoder().encode(new File(filePath + timeStamp + ".wav"), new File(filePath + timeStamp + ".flac"));		
	}
	
}
