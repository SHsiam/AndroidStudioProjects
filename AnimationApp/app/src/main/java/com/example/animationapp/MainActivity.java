package com.example.animationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    LinearLayout l;
    AnimationDrawable ad1;
    AnimationDrawable ad;
    Animation a,b,c,d,e,f,g,h,i,j;
    Animation ani;
    Button blink,rotate,zoomIn,zoomOut,fadeIn,fadeOut,move,flip,bounce,sequence;
    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l=findViewById(R.id.lay1);
        ad=(AnimationDrawable) l.getBackground();
        ad.setEnterFadeDuration(4500);
        ad.setExitFadeDuration(4500);
        ad.start();

        blink=findViewById(R.id.btnBlink);
        rotate=findViewById(R.id.btnRotate);
        textView=findViewById(R.id.textview1);
        zoomIn=findViewById(R.id.zoomin);
        zoomOut=findViewById(R.id.zoomout);
        fadeIn=findViewById(R.id.fadeIn);
        fadeOut=findViewById(R.id.fadeOut);
        move=findViewById(R.id.move);
        flip=findViewById(R.id.flip);
        imageView=findViewById(R.id.image1);
        bounce=findViewById(R.id.bounch);
        sequence=findViewById(R.id.sequncitial);

        imageView.setBackgroundResource(R.drawable.calllist);
        ad1=(AnimationDrawable)imageView.getBackground();

        a= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
        blink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.startAnimation(a);
            }
        });

        b= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.startAnimation(b);
            }
        });
        c=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomin);
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.startAnimation(c);
            }
        });
        d=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomout);
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.startAnimation(d);
            }
        });
        e=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
        fadeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.startAnimation(e);
            }
        });
        f=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeout);
        fadeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.startAnimation(f);
            }
        });
        g=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.movet);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.startAnimation(g);
            }
        });
        h=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.flp);
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.startAnimation(h);
            }
        });
        h=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.flp);
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.startAnimation(h);
            }
        });
        i=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounch);
        bounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.startAnimation(i);
            }
        });
        j=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.tryseq);
        sequence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.startAnimation(j);
            }
        });
    }
    public void click(View view){
        ad.start();
        imageView.startAnimation(ani);
    }
}