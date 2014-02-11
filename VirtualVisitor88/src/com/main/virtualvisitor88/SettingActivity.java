package com.main.virtualvisitor88;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	Button resetButton;
	AlertDialog.Builder alertDialog;
	
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetbutton);
		resetButton = (Button) findViewById(R.id.init_button);
		alertDialog = new AlertDialog.Builder(this);
		resetButton.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				 
				// ダイアログの設定
				alertDialog.setTitle("確認");          //タイトル
				alertDialog.setMessage("歩数を初期化しますか？");      //内容
				 
				// OKボタンの設定
				alertDialog.setPositiveButton("はい", new DialogInterface.OnClickListener() {
				 
				    public void onClick(DialogInterface dialog, int which) {
				    	MainActivity.walkCount = 0;

				    }
				});
				// NGボタンの設定
				alertDialog.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int which) {
				        Log.d("AlertDialog", "Negative which :" + which);
				 
				    }
				});
				 
				// ダイアログの作成と表示
				alertDialog.create();
				alertDialog.show();

			}
		}); 
        addPreferencesFromResource(R.layout.activity_setting);
		setPreferences();
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	 
	@Override
	protected void onPause() {
	    super.onPause();
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,  String key) {
		setPreferences();
	}
	
	void setPreferences(){
		EditTextPreference name_preference 		= (EditTextPreference)getPreferenceScreen().findPreference("name");
		EditTextPreference tall_preference 		= (EditTextPreference)getPreferenceScreen().findPreference("tall");
		EditTextPreference weight_preference 	= (EditTextPreference)getPreferenceScreen().findPreference("weight");
		ListPreference sex_preference 			= (ListPreference)getPreferenceScreen().findPreference("sex");
		name_preference.setSummary(name_preference.getText());
		tall_preference.setSummary(tall_preference.getText());
		weight_preference.setSummary(weight_preference.getText());
		sex_preference.setSummary(sex_preference.getValue());
	}
}
