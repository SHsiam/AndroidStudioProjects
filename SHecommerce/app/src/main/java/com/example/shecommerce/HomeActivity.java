package com.example.shecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class HomeActivity extends AppCompatActivity {
ViewFlipper viewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewFlipper=findViewById(R.id.vFlipper);
        int images[]={R.drawable.naima,R.drawable.chnise,R.drawable.japani,R.drawable.mihir};

        for (int image:images){
            flipperImages(image);
        }
    }

    public void flipperImages(int image){
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }
}