package com.hacku.swearjar;

import java.util.ArrayList;

import com.hacku.swearjar.justgiving.JustGivingAPI;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class SearchCharityActivity extends ListActivity {
	
	private static final String JUST_GIVING_URI = "http://www.justgiving.com/donation/direct/charity/#1?frequency=single&amount=#2";
	
	private ArrayList<Charity> charityResults;
	
	private ProgressDialog progressBar;
	private EditText searchField; 
	private Button searchButton; 

	private SwearJarApplication application;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_charity);
		
		searchField = (EditText) findViewById(R.id.charity_search_text);
		searchButton = (Button)  findViewById(R.id.submit);
		application = (SwearJarApplication) getApplication();
		
		searchButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				//Get user's search input
				String charityToSearch = searchField.getText().toString();

				progressBar = ProgressDialog.show(SearchCharityActivity.this, "Please Wait...", "Searching Charities...", true);
				
				new AsyncTask<String, Integer, String>() 
				{
					@Override
					protected String doInBackground(String... charitySearch) 
					{
				
						SearchCharityActivity.this.charityResults = JustGivingAPI.getCharitySearchResults(charitySearch[0]);
						
						return "Done";
					}
					
					@Override
					protected void onProgressUpdate(Integer... progress) 
					{
						super.onProgressUpdate(progress);
					};
					
					@Override
					protected void onPostExecute(String result) 
					{
						progressBar.dismiss();	//Stop progress bar
						
						if (charityResults.size() == 0)
						{
							Toast.makeText(SearchCharityActivity.this, "No charities found. Try Again.", Toast.LENGTH_LONG).show();
							return;
						}
						
						//Populate list with results
						setListAdapter(charityResults);
						ListView lv = getListView();
		                lv.setTextFilterEnabled(true);
		                
		                lv.setOnItemClickListener(new OnItemClickListener()
		                {
		                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		                    {
		                            //Check for internet access
		                        if (!application.hasInternetConnectivity())
		                        {
		                            Toast.makeText(SearchCharityActivity.this, "Internet Access Required...", Toast.LENGTH_LONG).show();
		                            return;
		                        }

		                        String webPage = DEFINITION_PAGE.replace("WORD", ((TextView) view).getText());
		                        
		                             //Start a web browser to pull dictionary definition
		                        Intent intent = new Intent(Intent.ACTION_VIEW);
		                        intent.setData(Uri.parse(webPage));
		                        startActivity(intent);
		                    }
		                });
		                
					};
				}.execute(charityToSearch);
				
				
			}
		});
		

	}
}
