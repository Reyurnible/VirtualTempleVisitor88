package com.main.virtualvisitor88;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
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
	
	//RemoteCallbackListÇ∆Ç¢Ç§ClassÇópà”ÇµCallback InterfaceÇï€éùÇ∑ÇÈ
		private RemoteCallbackList<walkCallback> walkCallBack = new RemoteCallbackList<walkCallback>();
		
		//ServiceÇ≈AIDL fileÇ…íËã`ÇµÇΩinterfaceÇÃé¿ëï
		//ÇΩÇæÇµÅAê∂ê¨Ç≥ÇÍÇΩjava fileÇÃStub classÇé¿ëïÇ∑ÇÈ
		private final activityCallback.Stub activityCallBack = new activityCallback.Stub() {
			
			//Observerìoò^methodì‡Ç≈RemoteCallbackList#register() methodÇ≈à¯êîÇ…ìnÇ≥ÇÍÇΩCallback interfaceÇìoò^Ç∑ÇÈ
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

	//ê∂ê¨ÇµÇΩStub classÇService#onBind()Ç≈returnÇ∑ÇÈ
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
<<<<<<< HEAD
		Toast.makeText(this, "MyService onDestroy", Toast.LENGTH_SHORT).show();
=======
		Toast.makeText(this, "MyServiceÅ@onDestroy", Toast.LENGTH_SHORT).show();
>>>>>>> f50a694374ade68b83a68835a519637eacbb5b5c
	}

}