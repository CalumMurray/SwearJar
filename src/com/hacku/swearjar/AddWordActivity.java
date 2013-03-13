package com.hacku.swearjar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AddWordActivity extends Activity {
	
	private Button submitButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_word);
		
		submitButton = (Button) findViewById(R.id.submit);
		
		//OnLongClickListener for button
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(AddWordActivity.this, "Word added to blacklist...", Toast.LENGTH_LONG).show();
				//TODO: Actually do what I just told the user I did.
				
				
			}
		});
		
	}

}
