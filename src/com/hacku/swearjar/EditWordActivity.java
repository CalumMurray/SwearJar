package com.hacku.swearjar;

import java.math.BigDecimal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditWordActivity extends Activity {

	private Button submitButton;
	private TextView wordText;
	private TextView chargeText;
	private BlackListItem blackListItem;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_word);
		
		//Get the list item we need to edit
		int blackListItemIndex = getIntent().getIntExtra("blackListItemIndex", 0);
		SwearJarApplication application = (SwearJarApplication) getApplication();
		blackListItem = application.getBlackListItems().get(blackListItemIndex);

		//Init views
		submitButton = (Button) findViewById(R.id.submit);
		wordText = (TextView) findViewById(R.id.add_word);
		chargeText = (TextView) findViewById(R.id.add_charge);
		
		wordText.setText(blackListItem.getWord());
		chargeText.setText(blackListItem.getCharge().toString());
		
		
		//Setup onClickListener for Pay button
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Check charge is valid currency
				if(!BlackListItem.validateCurrency(chargeText.getText().toString())){
					Toast.makeText(EditWordActivity.this, "Invalid charge amount", Toast.LENGTH_LONG).show();
					return;
				}
				
				blackListItem.setWord(wordText.getText().toString());
				blackListItem.setCharge(new BigDecimal(chargeText.getText().toString()));

				setResult(RESULT_OK);
				finish();
			}
		});
		
		/* Text Change Listener
		 * 
		 * chargeText.addTextChangedListener(new TextWatcher(){
		 *
			
			@Override
			public void onTextChanged(CharSequence s, int start,
	                int before, int count){
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});*/
	}
}
