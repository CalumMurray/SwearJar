package com.hacku.swearjar;

import java.math.BigDecimal;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
		
		//Bundle extras = getIntent().getSerializableExtra("blackListItem")
		blackListItem = (BlackListItem) getIntent().getSerializableExtra("blackListItem");

		//Init views
		submitButton = (Button) findViewById(R.id.submit);
		wordText = (TextView) findViewById(R.id.add_word);
		chargeText = (TextView) findViewById(R.id.add_charge);
		
		//Setup onClickListener for Pay button
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!validateCurrency(chargeText.getText().toString())){
					Toast.makeText(EditWordActivity.this,
							"Invalid charge amount",
							Toast.LENGTH_LONG).show();
					return;
				}
					
					
				
				blackListItem.setWord(wordText.getText().toString());
				blackListItem.setCharge(new BigDecimal(chargeText.getText().toString()));
			}

			private boolean validateCurrency(String string) {
				return string.matches("^[ ]*[\\d]+[.][\\d][\\d]$");
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
