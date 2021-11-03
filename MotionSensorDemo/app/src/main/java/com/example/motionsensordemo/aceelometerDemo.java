package com.example.motionsensordemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class aceelometerDemo {
    interface call{
        void tranaition(float x,float y,float z);
    }
    call l;
    void setListener(call l){
        this.l=l;
    }
    SensorManager sm;
    Sensor s;
    SensorEventListener sel;

    aceelometerDemo(Context c){
        sm=(SensorManager)c.getSystemService(Context.SENSOR_SERVICE);
        s=sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sel=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(l!=null){
                    l.tranaition(event.values[0],event.values[1],event.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
    void register(){
        sm.registerListener(sel,s,SensorManager.SENSOR_DELAY_NORMAL);
    }
    void unregister(){
        sm.unregisterListener(sel);
    }
}
