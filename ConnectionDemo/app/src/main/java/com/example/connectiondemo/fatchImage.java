package com.example.connectiondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fatchImage extends AppCompatActivity {
    ConnectivityManager cm;
    String url="";
    EditText et1;
    Button b1;
    TextView tv;
    ImageView iv1;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatch_image);
        tv=findViewById(R.id.tv1);
        iv1=findViewById(R.id.iv1);
        et1=findViewById(R.id.et1);
        b1=findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url=et1.getText().toString();
                new imageClass().execute(url);
                Toast.makeText(getApplicationContext(),"Connected with WIFI",Toast.LENGTH_LONG).show();

            }
        });

    }
    public void checkNetworkStatus(View v)
    {
        NetworkInfo info=cm.getActiveNetworkInfo();
        if(info!=null)
        {
            if(info.getType()== ConnectivityManager.TYPE_WIFI)

            {

                url=et1.getText().toString();
                new imageClass().execute(url);
                Toast.makeText(this,"Connected with WIFI",Toast.LENGTH_LONG).show();
            }
            else if(info.getType()==ConnectivityManager.TYPE_MOBILE)

            {
//https://drive.google.com/file/d/1yGVhPBOftF1YH0xpiO8bTXOqrC64zRiQ/view?usp=sharing
//String textUrl="https://drive.google.com/uc?export=download&id=1yGVhPBOftF1YH0xpiO8bTXOqrC64zRiQ";
//add toast

                Toast.makeText(this,"Connected with Mobile",Toast.LENGTH_LONG).show();
            }
            else
            {
//toast
                Toast.makeText(this,"No network found",Toast.LENGTH_LONG).show();
            }

        }
    }
    class imageClass extends AsyncTask<String,Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(getApplicationContext());
            pd.setMessage("Loading Image....");
            pd.show();
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            return fetchImage(strings[0]);
        }
        Bitmap fetchImage(String s)
        {
            Bitmap bitmap=null;
            try
            {
                URL myimageurl=new URL(s);
                HttpURLConnection httpURLConnection=(HttpURLConnection)myimageurl.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                int code=httpURLConnection.getResponseCode();
                if(code==HttpURLConnection.HTTP_OK)
                {
                    InputStream stream=httpURLConnection.getInputStream();
                    if(stream!=null)
                    {
                        bitmap= BitmapFactory.decodeStream(stream);
                    }
                }
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap!=null) {
                iv1.setImageBitmap(bitmap);
                pd.dismiss();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Bitmap is null",
                        Toast.LENGTH_LONG).show();
                pd.dismiss();
            }}
    }
}