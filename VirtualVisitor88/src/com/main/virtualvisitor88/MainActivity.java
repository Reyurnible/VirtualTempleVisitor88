package com.main.virtualvisitor88;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity{

	private DrawerLayout mDrawerLayout;
	SensorManager	sm;
	WalkCount		wc;
	TextView 		tv;
	String KEY = "walkValue";
	SharedPreferences pref;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		WCSample();
	}

	
	void WCSample(){
		pref = getSharedPreferences(KEY, Activity.MODE_PRIVATE);  
		tv  = new TextView(this);
//		tv.setText(pref.getString(PREF_KEY, "No Data"));
	    setContentView(tv);
		startService();
//		stopService();		
	}
	
	void startService(){
		startService( new Intent( MainActivity.this, WalkService.class ) );		
	}
	void stopService(){
		stopService( new Intent( MainActivity.this, WalkService.class ) );
	}
}
