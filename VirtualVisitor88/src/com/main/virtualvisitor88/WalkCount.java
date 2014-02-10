package com.main.virtualvisitor88;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class WalkCount  implements SensorEventListener, Runnable{
 
	private SensorManager	mSensorManager; 	// ÉZÉìÉTÅ[É}ÉlÅ[ÉWÉÉÅ[
	private Sensor 			mAccelerometer;  			// â¡ë¨ìxÉZÉìÉTÅ[
	private Handler 		mHander = new Handler(); // íËä˙ìIÇ…ï\é¶ÇêÿÇËë÷Ç¶ÇÈ
	private int 			flg 	= 0;
	private int				walkNum = 0;
	// â¡ë¨ìxÉZÉìÉTÅ[ÇÃíl
	private float mAcceX;
	private float mAcceY;
	private float mAcceZ;
	RemoteCallbackList<walkCallback> walkCallBack;
	
	WalkCount(SensorManager sm,RemoteCallbackList<walkCallback> walkCallBack){
		Log.i("test","test");
		this.walkCallBack 	= walkCallBack;
	 	this.mSensorManager = sm;  
	 	this.mAccelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	 
	 	// ÉZÉìÉTÅ[ÉäÉXÉiÅ[Çìoò^Ç∑ÇÈ
	 	this.mSensorManager.registerListener(
		this, this.mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		// ï\é¶ÇêÿÇËë÷Ç¶ÇÈÇΩÇﬂÇÃÉnÉìÉhÉâ
		this.mHander.postDelayed(this, 50);
	}

	void stop() {  
	 // ÉäÉXÉiÅ[ÇäOÇ∑
	 this.mSensorManager.unregisterListener(this);
	 // ï\é¶ÇêÿÇËë÷Ç¶ÇÈÇΩÇﬂÇÃÉnÉìÉhÉâÅ[
	 this.mHander.removeCallbacks(this);  
	}
	 
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	 // TODO é©ìÆê∂ê¨Ç≥ÇÍÇΩÉÅÉ\ÉbÉhÅEÉXÉ^Éu
	  
	}
	
	public void onSensorChanged(SensorEvent event) {
	  
	 // â¡ë¨ìxÉZÉìÉTÇæÇ¡ÇΩéû
	 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	
	  // äeé≤ï˚å¸ÇÃílÇéÊÇËèoÇ∑
	  this.mAcceX = event.values[0] * event.values[0];
	  this.mAcceY = event.values[1] * event.values[1];
	  this.mAcceZ = event.values[2] * event.values[2];
	  float value = (float)Math.sqrt(this.mAcceX + this.mAcceY + this.mAcceZ);
	  if(flg == 0){
	  	if(value < 9.0){
	  		flg = 1;
	  		walkNum++;
//	  	  Log.i("test",walkNum + " : " + flg + " : " + value);
			int observerNum = walkCallBack.beginBroadcast();
//			Log.i("WalkService", "beginBroadcast" + observerNum);
			for(int i = 0; i < observerNum; i++){
//				Log.i("WalkService", "Count:"+i);
				try {
					//RemoteCallbackList#getBroadcastItem()Ç≈Callback interfaceÇéÊÇËèoÇµ
					//Callback methodÇåƒÇ—èoÇ∑
					walkCallBack.getBroadcastItem(i).updateWalkCount(walkNum);
				} catch (RemoteException e) {
				}
			}	
			walkCallBack.finishBroadcast();	  		
	  	}
	  }else{
	  	if(value > 12.0){
	  		flg = 0;
	  	}  	
	  }
	 }
	}

	public void run() {
	 // EditTextÇ…ï\é¶Ç∑ÇÈ
	 this.mHander.postDelayed(this, 50);
	}
}