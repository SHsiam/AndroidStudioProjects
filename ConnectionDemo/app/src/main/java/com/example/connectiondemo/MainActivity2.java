package com.example.connectiondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
VideoView v;
Button b;
String st= "https://developers.google.com/training/images/tacoma_narrows.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        b=findViewById(R.id.button);
        v=findViewById(R.id.videoView);
        Uri vedUri=Uri.parse("https://developers.google.com/training/images/tacoma_narrows.mp4");
        v.setVideoURI(vedUri);
        v.start();
        MediaController mc=new MediaController(MainActivity2.this);
        v.setMediaController(mc);
        mc.show();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });

    }
    void call()
    {
        fetchVideo fv=new fetchVideo();
        fv.execute();
    }
    class fetchVideo extends AsyncTask<String,String,String>
    {



        @Override
        protected String doInBackground(String... strings)
        {
            downloadFile(st);
            return null;
        }
        void downloadFile(String url)
        {
            SimpleDateFormat sd=new SimpleDateFormat("yymmhh");
            String date=sd.format(new Date());
            String name="video"+date+".mp4";
            try
            {
                String rooDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS
                )+ File.separator+"Myvid";
                File f=new File(rooDir);
                f.mkdir();
                URL u=new URL(st);
                HttpURLConnection httpURLConnection=(HttpURLConnection)u.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                FileOutputStream fos=new FileOutputStream(new File(rooDir,name));
                InputStream in=httpURLConnection.getInputStream();
                byte[] buffer=new byte[1024];
                int len=0;
                while((len=in.read(buffer))>0)
                {
                    fos.write(buffer,0,len);
                }
                fos.close();

            }
            catch (IOException e)
            {
                Log.d("Error",e.toString());
            }

        }
    }
}
