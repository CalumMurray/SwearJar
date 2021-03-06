package com.hacku.swearjar;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.util.Log;

import com.hacku.swearjar.speechapi.GoogleSpeechAPI;
import com.hacku.swearjar.speechapi.SpeechResponse;

/**
 * Service to run as a thread in the background. Performs actual recording of a
 * phone call to a file.
 * 
 * @author Calum
 */
public class RecordingService extends Service implements Runnable {

	private MediaRecorder recorder;
	private static String filePath = SwearJarApplication.ROOTPATH + "/swearjar";
	private long timeStamp;

	private SwearJarApplication application;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		application = (SwearJarApplication) this.getApplication();
		new Thread(this).start(); // Starts "run()" as a background thread.

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
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_UPLINK);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
		recorder.setAudioEncodingBitRate(16000);
		timeStamp = System.currentTimeMillis(); // Make unique filename
		recorder.setOutputFile(filePath + timeStamp + ".3gp");
		try {
			recorder.prepare();
		} catch (Exception e) {
			System.out.println("Problem preparing Recorder.");
			e.printStackTrace();
		}
		recorder.start(); // RECORD!

	}

	private void stopRecording() {
		recorder.stop();
		recorder.release();

		addOccurrences();

	}

	private void addOccurrences() {
		File rawSpeechFile = new File(filePath + timeStamp + ".3gp");

		int retryAttempts = 2;

		SpeechResponse response = new SpeechResponse();
		while (retryAttempts >= 0 && response.getStatus() != 0) {
			retryAttempts--;
			response = GoogleSpeechAPI.getSpeechResponse(rawSpeechFile);
		}

		String utterance = response.getBestUtterance();
		// TODO: Possible multiple hypotheses?

		Log.e("SPEECH RESPONSE", utterance);

		if (utterance != null && !utterance.equals(""))
			application.addOccurrences(utterance);
		rawSpeechFile.delete(); // Delete file
	}

}
