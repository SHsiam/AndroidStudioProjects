package com.example.ca2__animation;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;


public class DrawAcitivity extends AppCompatActivity  {
    private SlidrInterface slidr;
DrawClass drawClass;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_draw_acitivity);
        slidr = Slidr.attach(this);
        drawClass=new DrawClass(this);
        setContentView(drawClass);

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.side_left, R.anim.side_out_right);
    }
}
