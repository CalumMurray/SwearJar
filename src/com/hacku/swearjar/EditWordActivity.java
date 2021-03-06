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

public class EditWordActivity extends Activity {

	private Button submitButton;
	private EditText wordText;
	private EditText chargeText;
	private CheckBox checkBox;
	
	private BlackListItem blackListItem;
	private SwearJarApplication application;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_word);
		
		application  = (SwearJarApplication) getApplication();
		//Get the list item we need to edit
		int blackListItemIndex = getIntent().getIntExtra("blackListItemIndex", 0);
		
		blackListItem = application.getBlackListItems().get(blackListItemIndex);

		//Init views
		submitButton = (Button) findViewById(R.id.submit);
		wordText = (EditText) findViewById(R.id.add_word);
		chargeText = (EditText) findViewById(R.id.add_charge);
		checkBox = (CheckBox)findViewById(R.id.exact_match);
		
		wordText.setText(blackListItem.getWord());
		chargeText.setText(blackListItem.getCharge().toString());
		
		//Setup onClickListener for Pay button
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String wordInput = wordText.getText().toString();
				String chargeInput = chargeText.getText().toString();
				
				//Validate the charge
				if (!BlackListItem.validateCurrency(chargeText.getText().toString())){
					Toast.makeText(EditWordActivity.this, "Invalid charge amount", Toast.LENGTH_LONG).show();
					return;
				}
				else if (wordInput.isEmpty() || chargeInput.isEmpty())
				{
					Toast.makeText(EditWordActivity.this, "Please enter both values in both fields.", Toast.LENGTH_LONG).show();
					return;
				}
				
				application.editBlackListItem(blackListItem, wordInput, new BigDecimal(chargeInput), checkBox.isChecked());

				setResult(RESULT_OK);
				finish();
			}
		});
		
	}
}
