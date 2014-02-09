package com.main.virtualvisitor88;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

public class WalkCount  implements SensorEventListener, Runnable{
    
    
   private SensorManager   mSensorManager; 	// �Z���T�[�}�l�[�W���[
   private Sensor mAccelerometer;  			// �����x�Z���T�[
   private Handler mHander = new Handler(); // ����I�ɕ\����؂�ւ���
   
   // �����x�Z���T�[�̒l
   private float mAcceX;
   private float mAcceY;
   private float mAcceZ;
   
   WalkCount(SensorManager sm){
	   Log.i("test","test");

       this.mSensorManager = sm;        
       this.mAccelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
       
      // �Z���T�[���X�i�[��o�^����
      this.mSensorManager.registerListener(
              this, this.mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
      // �\����؂�ւ��邽�߂̃n���h���[
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
           this.mAcceX = event.values[0];
           this.mAcceY = event.values[1];
           this.mAcceZ = event.values[2];
           Log.i("test"," " + this.mAcceX);
       }
   }

   public void run() {
       // EditText�ɕ\������
       this.mHander.postDelayed(this, 50);
   }
}