package com.main.virtualvisitor88;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;

public class MainActivity extends Activity{

	private DrawerLayout mDrawerLayout;
	SensorManager	sm;
	WalkCount		wc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		startService( new Intent( MainActivity.this, WalkService.class ) );
		stopService();
	}
	
	void stopService(){
		stopService( new Intent( MainActivity.this, WalkService.class ) );
	}
}
