package com.main.virtualvisitor88;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class WalkService extends Service{
	
	Handler mHandler = new Handler();
	SensorManager	sm;
	WalkCount		wc;
	
	//RemoteCallbackList‚Æ‚¢‚¤Class‚ğ—pˆÓ‚µCallback Interface‚ğ•Û‚·‚é
		private RemoteCallbackList<walkCallback> walkCallBack = new RemoteCallbackList<walkCallback>();
		
		//Service‚ÅAIDL file‚É’è‹`‚µ‚½interface‚ÌÀ‘•
		//‚½‚¾‚µA¶¬‚³‚ê‚½java file‚ÌStub class‚ğÀ‘•‚·‚é
		private final activityCallback.Stub activityCallBack = new activityCallback.Stub() {
			
			//Observer“o˜^method“à‚ÅRemoteCallbackList#register() method‚Åˆø”‚É“n‚³‚ê‚½Callback interface‚ğ“o˜^‚·‚é
			public void setObserver(walkCallback observer)
					throws RemoteException {
				Log.i("test", "setObserver called by " + Thread.currentThread().getName());
				walkCallBack.register(observer);			
			}
			public void removeObserver(walkCallback observer)
					throws RemoteException {
				walkCallBack.unregister(observer);
			}
		}; 

	//¶¬‚µ‚½Stub class‚ğService#onBind()‚Åreturn‚·‚é
	@Override
	public IBinder onBind(Intent arg0) {
		Toast.makeText(this, "onBind called by "
			  + Thread.currentThread().getName(), Toast.LENGTH_LONG).show();
		return activityCallBack;
	}
		
	@Override
	public void onCreate() {
		Log.i("WalkService", "onCreate");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("WalkService", "onStartCommand");
		sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		wc = new WalkCount(sm,walkCallBack);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.i("WalkService", "onDestroy");
		wc.stop();
		Toast.makeText(this, "MyService@onDestroy", Toast.LENGTH_SHORT).show();
	}

}