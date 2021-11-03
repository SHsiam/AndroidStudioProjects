package com.example.ca2__animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class Zoom_Rotated extends AppCompatActivity {
    private SlidrInterface slidr;
    AnimationDrawable drawable,animationDrawable;
    Animation rotate,zoomIn,zoomOut,draw;
    Button btnRotate,btnZoomIn,btnZoomOut,btnDraw;
    LinearLayout layout;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom__rotated);
        layout=findViewById(R.id.linearlayout);
        slidr = Slidr.attach(this);
        btnRotate=findViewById(R.id.btnRotate);
        btnZoomIn=findViewById(R.id.btnZoomIn);
        btnZoomOut=findViewById(R.id.btnZoomOut);
        btnDraw=findViewById(R.id.btnDraw);
        imageView = (ImageView) findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animationlist);
        drawable= (AnimationDrawable) imageView.getBackground();

        animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        rotate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotated);
        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.startAnimation(rotate);
            }
        });
        zoomIn= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomin);
        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.startAnimation(zoomIn);
            }
        });
        zoomOut= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomout);
        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.startAnimation(zoomOut);
            }
        });
        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draw=AnimationUtils.loadAnimation(Zoom_Rotated.this,R.anim.bounch);
                btnDraw.startAnimation(draw);
                Intent intent=new Intent(Zoom_Rotated.this,DrawAcitivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.side_left, R.anim.side_out_right);
            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        drawable.start();
    }

}