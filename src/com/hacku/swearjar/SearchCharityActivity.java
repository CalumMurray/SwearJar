package com.hacku.swearjar;

import java.util.HashMap;

import android.app.Activity;
import android.os.AsyncTask;

public class SearchCharityActivity extends Activity {

	private HashMap<String, String> charityResults;
	
	AsyncTask charitySearch = new AsyncTask<String, Integer, String>(){
	@Override
	protected String doInBackground(String... charitySearch) {

		charityResults = getCharitySearchResults(yql);
		
		return "";
	}
	
	@Override
	protected void onProgressUpdate(Integer[] values) {
		super.onProgressUpdate(values);
	};
	
	@Override
	protected void onPostExecute(String result) {
		
		sendtoui(charitySearchResults);
	};
	}.execute("");
	
}
