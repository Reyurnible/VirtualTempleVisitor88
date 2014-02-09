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
 
	private SensorManager	mSensorManager; 	// �Z���T�[�}�l�[�W���[
	private Sensor 			mAccelerometer;  			// �����x�Z���T�[
	private Handler 		mHander = new Handler(); // ����I�ɕ\����؂�ւ���
	private int 			flg 	= 0;
	private int				walkNum = 0;
	// �����x�Z���T�[�̒l
	private float mAcceX;
	private float mAcceY;
	private float mAcceZ;
	RemoteCallbackList<walkCallback> walkCallBack;
	
	WalkCount(SensorManager sm,RemoteCallbackList<walkCallback> walkCallBack){
		Log.i("test","test");
		this.walkCallBack 	= walkCallBack;
	 	this.mSensorManager = sm;  
	 	this.mAccelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	 
	 	// �Z���T�[���X�i�[��o�^����
	 	this.mSensorManager.registerListener(
		this, this.mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		// �\����؂�ւ��邽�߂̃n���h��
		this.mHander.postDelayed(this, 50);
	}

	void stop() {  
	 // ���X�i�[���O��
	 this.mSensorManager.unregisterListener(this);
	 // �\����؂�ւ��邽�߂̃n���h���[
	 this.mHander.removeCallbacks(this);  
	}
	 
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	 // TODO �����������ꂽ���\�b�h�E�X�^�u
	  
	}
	
	public void onSensorChanged(SensorEvent event) {
	  
	 // �����x�Z���T��������
	 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	
	  // �e�������̒l�����o��
	  this.mAcceX = event.values[0] * event.values[0];
	  this.mAcceY = event.values[1] * event.values[1];
	  this.mAcceZ = event.values[2] * event.values[2];
	  float value = (float)Math.sqrt(this.mAcceX + this.mAcceY + this.mAcceZ);
	  if(flg == 0){
	  	if(value < 9.0){
	  		flg = 1;
	  		walkNum++;
	  	  Log.i("test",walkNum + " : " + flg + " : " + value);
			int observerNum = walkCallBack.beginBroadcast();
			Log.i("WalkService", "beginBroadcast" + observerNum);
			for(int i = 0; i < observerNum; i++){
				Log.i("WalkService", "Count:"+i);
				try {
					//RemoteCallbackList#getBroadcastItem()��Callback interface�����o��
					//Callback method���Ăяo��
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
	 // EditText�ɕ\������
	 this.mHander.postDelayed(this, 50);
	}
}