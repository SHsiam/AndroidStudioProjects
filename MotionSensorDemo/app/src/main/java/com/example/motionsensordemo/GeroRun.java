package com.example.motionsensordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class GeroRun extends AppCompatActivity {
Gerocopedemo obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gero_run);
        obj = new Gerocopedemo(this);
        obj.setListener(new Gerocopedemo.callGero() {
            @Override
            public void callGeroMethod(float x, float y, float z) {
                if (z > 1.0f) {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                } else if (z < -1.0f) {
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
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
