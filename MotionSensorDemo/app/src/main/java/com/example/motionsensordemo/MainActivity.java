package com.example.motionsensordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button btnacceler,btngeroscope;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnacceler=findViewById(R.id.btnAce);
        btngeroscope=findViewById(R.id.btnGero);
btnacceler.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),aceldemo.class);
        startActivity(intent);
    }
});
        btngeroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GeroRun.class);
                startActivity(intent);
            }
        });
    }
}