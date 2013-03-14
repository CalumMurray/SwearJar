package com.hacku.swearjar.justgiving;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JustGivingAPI {	

	private static HashMap<String, String> getCharitySearchResults(String charitySearch) {
		
		String url = "http://query.yahooapis.com/v1/public/yql?q=SELECT%20charityId%2C%20name%20FROM%20json%20WHERE%20url%3D%22https%3A%2F%2Fapi-staging.justgiving.com%2Fe336c8aa%2Fv1%2Fcharity%2Fsearch%3Fq%3D"
				+ charitySearch 
				+ "%26format%3Djson%22%20AND%20itemPath%3D%22json.charitySearchResults%22%20LIMIT%205&format=json&callback=";
		
			//Make connection
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			HttpParams params = request.getParams();
			HttpConnectionParams.setSoTimeout(params, 30000);
			request.setParams(params);
			
			try {
				HttpResponse response = client.execute(request);
				InputStreamReader inputStream = new InputStreamReader(response.getEntity().getContent());
				response = client.execute(request);
				
				StringWriter sw = null;
				IOUtils.copy(inputStream, sw);
				
				return parseJson(sw.toString());
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return new HashMap<String, String>();
	}

	/**
	 * @return 
	 * @throws JSONException
	 */
	private static HashMap<String, String> parseJson(String json) throws JSONException {
		HashMap<String, String> charityResults = new HashMap<String, String>();
		JSONObject jsonObject = new JSONObject(json.toString());
		JSONArray jsonArray = jsonObject.getJSONArray("charitySearchResults");
		
		for(int i=0; i<jsonArray.length(); i++){
				JSONObject charityResult = jsonArray.getJSONObject(i);
				String name = charityResult.getString("name");
				String id = charityResult.getString("charityId");
				charityResults.put(id, name);
		}
		
		return charityResults;
	}
}
