package com.hacku.swearjar.speechapi;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import com.google.gson.Gson;

/**
 * Facilitates access to Google Speech API
 * 
 * @author Neil
 */
public class GoogleSpeechAPI {

	/**
	 * Takes the audio at the specified path and sends it off to Google via HTTP
	 * POST. Packages the JSON response from Google into a SpeechResponse
	 * object.
	 * 
	 * @param speechFile path to the audio file
	 * @return SpeechResponse containing recognised speech, null if error occurs
	 */
	public static SpeechResponse getSpeechResponse(String speechFile) {
		try {
			// Read speech file
			InputStream inputStream = new FileInputStream(speechFile);
			ByteArrayInputStream data = new ByteArrayInputStream(
					IOUtils.toByteArray(inputStream));

			// Set up the POST request
			HttpPost postRequest = getPost(data);

			// Do the request
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(postRequest);

			// Package the returned JSON into a SpeechResponse
			SpeechResponse speechResponse = packageResponse(response);

			// Close the stream
			response.getEntity().getContent().close();

			return speechResponse;

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return null;
	}

	/**
	 * Uses GSON library to put the returned JSON into a SpeechResponse object
	 * 
	 * @param response containing JSON to be packaged
	 * @return SpeechResponse containing recognised speech
	 * @throws IOException
	 */
	private static SpeechResponse packageResponse(HttpResponse response)
			throws IOException {
		Gson gson = new Gson();
		InputStreamReader isr = new InputStreamReader(response.getEntity().getContent());
		SpeechResponse speechResponse = gson.fromJson(isr, SpeechResponse.class);
		return speechResponse;
	}

	/**
	 * Sets up the post request
	 * 
	 * @param data audio file
	 * @return HttpPost object with parameters initialised to audio file
	 */
	private static HttpPost getPost(ByteArrayInputStream data) {
		HttpPost postRequest = new HttpPost(
				"https://www.google.com/speech-api/v1/recognize"
						+ "?xjerr=1&pfilter=0&client=chromium&lang=en-US&maxresults=1");

		// Specify Content and Content-Type parameters for POST request
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("Content", new InputStreamBody(data, "Content"));
		postRequest.setHeader("Content-Type", "audio/x-flac; rate=16000");
		postRequest.setEntity(entity);
		return postRequest;
	}
}