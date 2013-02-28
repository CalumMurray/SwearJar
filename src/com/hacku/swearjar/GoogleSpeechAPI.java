package com.hacku.swearjar;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * Facilitates access to Google Speech API
 * 
 * @author Neil
 */
public class GoogleSpeechAPI {
	
	/**
	 * Gets a JSON object with a hypothesis from Google Speech API 
	 * about the speech in the file
	 * 
	 * @param speechFile to recognise
	 * @return JSON with hypothesis
	 */
	public JSONObject getJson(String speechFile){
		try {
			InputStream inputStream = new FileInputStream(speechFile);

			HttpClient client = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(
					"https://www.google.com/speech-api/v1/recognize" +
					"?xjerr=1&pfilter=0&client=chromium&lang=en-US&maxresults=1");

			ByteArrayInputStream data = new ByteArrayInputStream(
					IOUtils.toByteArray(inputStream));

			InputStreamBody isb = new InputStreamBody(data, "Content");

			MultipartEntity entity = new MultipartEntity();
			entity.addPart("Content", isb);
			entity.addPart("Content_Type", new StringBody(
					"audio/x-flac; rate=16000"));

			postRequest.setEntity(entity);
			HttpResponse response = client.execute(postRequest);

			//TODO Parse JSON, go direct, cut out middle string
			String responseText = getResponseText(response);
			
			response.getEntity().getContent().close();
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @param response
	 * @throws IOException
	 */
	private String getResponseText(HttpResponse response) throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
		return writer.toString();
	}
}
