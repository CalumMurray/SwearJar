package com.hacku.swearjar;

import java.math.BigDecimal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddWordActivity extends Activity {
	
	private EditText wordText;
	private EditText chargeText;
	private CheckBox checkBox;
	private Button submitButton;
	
	private SwearJarApplication application;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_word);
		
		application  = (SwearJarApplication) getApplication();
		
		submitButton = (Button) findViewById(R.id.submit);
		wordText = (EditText) findViewById(R.id.add_word);
		chargeText = (EditText) findViewById(R.id.add_charge);
		checkBox = (CheckBox)findViewById(R.id.exact_match);
				
		//OnLongClickListener for button
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				
				String wordInput = wordText.getText().toString();
				String chargeInput = chargeText.getText().toString();
				
				//Validate the charge
				if (!BlackListItem.validateCurrency(chargeText.getText().toString())){
					Toast.makeText(AddWordActivity.this, "Invalid charge amount", Toast.LENGTH_LONG).show();
					return;
				}
				else if (wordInput.isEmpty() || chargeInput.isEmpty())
				{
					Toast.makeText(AddWordActivity.this, "Please enter both values in both fields.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//Create the new item and add to the list
				BlackListItem blackListItem = new BlackListItem(wordInput, new BigDecimal(chargeInput), 0, checkBox.isChecked());
								
				application.addBlackListItem(blackListItem);
				Toast.makeText(AddWordActivity.this, "Word added to blacklist...", Toast.LENGTH_LONG).show();
				
				setResult(RESULT_OK);
				finish();
				
			}
		});
		
	}

}
