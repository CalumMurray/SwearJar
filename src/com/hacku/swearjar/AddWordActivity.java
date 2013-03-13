package com.hacku.swearjar;

import java.math.BigDecimal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddWordActivity extends Activity {
	
	private TextView wordText;
	private TextView chargeText;
	private Button submitButton;
	private SwearJarApplication application;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_word);
		
		application  = (SwearJarApplication) getApplication();
		
		submitButton = (Button) findViewById(R.id.submit);
		wordText = (TextView) findViewById(R.id.add_word);
		chargeText = (TextView) findViewById(R.id.add_charge);
		
		//OnLongClickListener for button
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				//Validate the charge
				if(!BlackListItem.validateCurrency(chargeText.getText().toString())){
					Toast.makeText(AddWordActivity.this, "Invalid charge amount", Toast.LENGTH_LONG).show();
					return;
				}
				
				//Create the new item and add to the list
				BlackListItem blackListItem = new BlackListItem(wordText.getText().toString(), new BigDecimal(chargeText.getText().toString()));
								
				application.addBlackListItem(blackListItem);
				Toast.makeText(AddWordActivity.this, "Word added to blacklist...", Toast.LENGTH_LONG).show();
				
				setResult(RESULT_OK);
				finish();
				
			}
		});
		
	}

}
