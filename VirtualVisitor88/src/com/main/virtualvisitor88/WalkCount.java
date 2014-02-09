package com.main.virtualvisitor88;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class WalkCount  implements SensorEventListener, Runnable{
 
 
private SensorManager	mSensorManager; 	// センサーマネージャー
private Sensor 			mAccelerometer;  			// 加速度センサー
private Handler 		mHander = new Handler(); // 定期的に表示を切り替える
private int 			flg 	= 0;
private int				walkNum = 0;
// 加速度センサーの値
private float mAcceX;
private float mAcceY;
private float mAcceZ;

String KEY = "walkValue";
SharedPreferences pref;  
SharedPreferences.Editor editor;

WalkCount(SensorManager sm){
	Log.i("test","test");

	 this.mSensorManager = sm;  
	 this.mAccelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	 
	// センサーリスナーを登録する
	this.mSensorManager.registerListener(
	  this, this.mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	// 表示を切り替えるためのハンドラー
	this.mHander.postDelayed(this, 50);
	}

	void stop() {  
	 // リスナーを外す
	 this.mSensorManager.unregisterListener(this);
	 // 表示を切り替えるためのハンドラー
	 this.mHander.removeCallbacks(this);  
	}
	 
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	 // TODO 自動生成されたメソッド・スタブ
	  
	}
	
	public void onSensorChanged(SensorEvent event) {
	  
	 // 加速度センサだった時
	 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	
	  // 各軸方向の値を取り出す
	  this.mAcceX = event.values[0] * event.values[0];
	  this.mAcceY = event.values[1] * event.values[1];
	  this.mAcceZ = event.values[2] * event.values[2];
	  float value = (float)Math.sqrt(this.mAcceX + this.mAcceY + this.mAcceZ);
	  if(flg == 0){
	  	if(value < 9.0){
	  		flg = 1;
	  		walkNum++;
	  	}
	  }else{
	  	if(value > 10.0){
	  		flg = 0;
	  	}  	
	  }
	  Log.i("test",walkNum + " : " + flg + " : " + value);
	 }
	}

	public void run() {
	 // EditTextに表示する
	 this.mHander.postDelayed(this, 50);
	}

	public void save(String value){
		editor = pref.edit();  
		// Editor に値を代入  
		editor.putString(  
			KEY,value
		);  
		// データの保存  
		editor.commit();    
	}  
}