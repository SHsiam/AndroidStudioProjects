package com.example.proximetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
SensorManager sm;
SensorEventListener sel;
Sensor proximity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        proximity=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sel=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.values[0]<proximity.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }else{
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sm.registerListener(sel,proximity,2*1000*1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(sel);
    }
}