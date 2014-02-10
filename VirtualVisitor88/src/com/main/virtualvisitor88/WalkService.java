package com.main.virtualvisitor88;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class WalkService extends Service {
	
	Handler mHandler = new Handler();
	SensorManager	sm;
	WalkCount		wc;
	
	@Override
	public void onCreate() {
		Log.i("WalkService", "onCreate");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("WalkService", "onStartCommand");
		sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		wc = new WalkCount(sm);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.i("WalkService", "onDestroy");
		wc.stop();
		Toast.makeText(this, "MyServiceÅ@onDestroy", Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.i("WalkService", "onBind");
		return null;
	}
}