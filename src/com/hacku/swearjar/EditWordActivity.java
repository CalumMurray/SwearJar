package com.hacku.swearjar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EditWordActivity extends Activity {

	private Button submitButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_word);

		//Setup onClickListener for Pay button
		submitButton = (Button) findViewById(R.id.addWord);
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				return;
			}
		});
	}
}
