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
import android.widget.RelativeLayout;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class MainActivity extends AppCompatActivity  {
Animation seq,nextButton;
AnimationDrawable animationDrawable;
RelativeLayout relativeLayout;
LinearLayout layout;
Button btnSub,btnMove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout=findViewById(R.id.relative);
        layout = (LinearLayout) findViewById(R.id.linearLayout_ani);
        btnMove=findViewById(R.id.btnMove);
        btnSub=findViewById(R.id.btnNext);

        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seq = AnimationUtils.loadAnimation(MainActivity.this,R.anim.tsequence);
                seq.reset();
                seq.setFillAfter(true);
                seq.setRepeatCount(4);
                seq.setRepeatMode(4);
                layout.startAnimation(seq);
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButton=AnimationUtils.loadAnimation(MainActivity.this,R.anim.bounch);
                btnSub.startAnimation(nextButton);
                Intent intent=new Intent(MainActivity.this,Zoom_Rotated.class);
                startActivity(intent);
                overridePendingTransition(R.anim.side_right, R.anim.side_out_left);
            }
        });
    }

}