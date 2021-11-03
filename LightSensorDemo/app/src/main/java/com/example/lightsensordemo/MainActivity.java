package com.example.lightsensordemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    SensorManager sm;
    Sensor light;
    SensorEventListener sel;
    View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.root);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sel = new SensorEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSensorChanged(SensorEvent event) {
                float max = light.getMaximumRange();
                float v = event.values[0];
                float newval = (int) (255f * v / max);
                root.setBackgroundColor(Color.rgb(newval, newval, newval));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
        @Override
        protected void onResume () {
            super.onResume();
            sm.registerListener(sel, light, SensorManager.SENSOR_DELAY_NORMAL);
        }
        @Override
        protected void onPause () {
            super.onPause();
            sm.unregisterListener(sel);
        }
    }
