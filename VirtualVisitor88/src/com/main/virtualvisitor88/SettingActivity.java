package com.main.virtualvisitor88;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingActivity extends PreferenceActivity{
	
	private Preference resetButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.activity_setting);
        CharSequence resetPref = (CharSequence)findViewById(R.id.reset); 
        resetButton = (Preference)findPreference(resetPref);  

	}
}
