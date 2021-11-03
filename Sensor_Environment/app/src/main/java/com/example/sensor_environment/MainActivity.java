package com.example.sensor_environment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    Button show;
TextView tv1,tv2;
RelativeLayout root1;
List<Sensor>sl;
    Sensor light,ac,mg,proximity;;
SensorManager sm;
    float [] a=new float[3];
    float [] m=new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=findViewById(R.id.tv1);
        root1=findViewById(R.id.root1);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        ac=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mg=sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        light=sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximity=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sl=sm.getSensorList(Sensor.TYPE_ALL);
        tv2=findViewById(R.id.tv2);
        show=findViewById(R.id.button1);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t="";
                for(Sensor s:sl)
                {
                    t=t+ s.getName()+"\n";
                }
                tv2.setText(t);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(light!=null){
            sm.registerListener(this,light,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(proximity!=null){
            sm.registerListener(this,proximity,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(ac!=null){
            sm.registerListener(this,ac,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mg!=null){
            sm.registerListener(this,mg,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
switch (event.sensor.getType()){
    case Sensor.TYPE_LIGHT:
        float max=light.getMaximumRange();
        float v=event.values[0];
        int newval=(int)(255f*v/max);
        root1.setBackgroundColor(Color.rgb(newval,newval,newval));
        break;
    case Sensor.TYPE_PROXIMITY:
        if(event.values[0]<proximity.getMaximumRange())
        {
            tv1.setBackgroundColor(Color.CYAN);
        }
        else
        {
            tv1.setBackgroundColor(Color.WHITE);
        }
        break;
    case Sensor.TYPE_ACCELEROMETER:
        a=event.values;
        break;
    case Sensor.TYPE_MAGNETIC_FIELD:
        m=event.values;
        break;

}
         float [] rMatrix= new float[9];
boolean f=sm.getRotationMatrix(rMatrix,null,a,m);
if(f){
    float []oa=new float[3];
    sm.getOrientation(rMatrix,oa);
    float azimuth=oa[0];
    float pitch=oa[1];
    float roll=oa[2];
    tv1.setText("Azimuth is"+azimuth+"\n Pitch is"+pitch+"\n Roll is"+roll);
}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}