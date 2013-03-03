package com.hacku.swearjar;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Interface to edit word blacklist
 * Called from Menu option
 *
 * @author Calum Murray
 */
public class WordsActivity extends PreferenceActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.prefs);	//Displays screen
        
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
