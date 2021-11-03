package com.example.motionsensordemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Gerocopedemo {
    interface callGero{
        void callGeroMethod(float x,float y,float z);
    }
    callGero cg;
    void setListener(callGero cg){
        this.cg=cg;
    }
    SensorManager sm;
    Sensor s;
    SensorEventListener sel;

    Gerocopedemo(Context c) {
        sm = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sel=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(cg!=null){
                    cg.callGeroMethod(event.values[0],event.values[1],event.values[2]);
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

