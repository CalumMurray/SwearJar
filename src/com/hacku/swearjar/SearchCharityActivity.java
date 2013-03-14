package com.hacku.swearjar;

import java.util.HashMap;

import com.hacku.swearjar.justgiving.JustGivingAPI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

public class SearchCharityActivity extends Activity {

	private HashMap<String, String> charityResults;
	
	private ProgressDialog progressBar;
//	private EditText searchField; 
//	private Button searchButton; 
	private SearchView searchField;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.charity_layout);
		
		progressBar = ProgressDialog.show(this, "Please Wait...", "Searching Charities...", true);
	
		search
		
		AsyncTask charitySearch = new AsyncTask<String, Integer, String>( ) {
			@Override
			protected String doInBackground(String... charitySearch) {
		
				charityResults = JustGivingAPI.getCharitySearchResults(charitySearch[0]);
				
				return "Done";
			}
			
			@Override
			protected void onProgressUpdate(Integer... progress) {
				super.onProgressUpdate(progress);
			};
			
			@Override
			protected void onPostExecute(String result) {
				progressBar.dismiss();	//Stop progress bar
				//sendtoui(charitySearchResults);
			};
		}.execute("");
		
	}
}
