package com.main.virtualvisitor88;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

public class WalkCount  implements SensorEventListener, Runnable{
    
    
   private SensorManager   mSensorManager; 	// センサーマネージャー
   private Sensor mAccelerometer;  			// 加速度センサー
   private Handler mHander = new Handler(); // 定期的に表示を切り替える
   
   // 加速度センサーの値
   private float mAcceX;
   private float mAcceY;
   private float mAcceZ;
   
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
           this.mAcceX = event.values[0];
           this.mAcceY = event.values[1];
           this.mAcceZ = event.values[2];
           Log.i("test"," " + this.mAcceX);
       }
   }

   public void run() {
       // EditTextに表示する
       this.mHander.postDelayed(this, 50);
   }
}