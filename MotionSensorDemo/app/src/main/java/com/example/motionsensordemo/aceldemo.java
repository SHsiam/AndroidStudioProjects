package com.example.motionsensordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

public class aceldemo extends AppCompatActivity {
    aceelometerDemo obj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aceldemo);
        obj=new aceelometerDemo(this);
        obj.setListener(new aceelometerDemo.call() {
            @Override
            public void tranaition(float x, float y, float z) {
                if(x>1.0f){
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }
                else if(x<-1.0f){
                    getWindow().getDecorView().setBackgroundColor(Color.CYAN);
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        obj.register();
    }
    @Override
    protected void onPause() {
        super.onPause();
        obj.unregister();

    }
}