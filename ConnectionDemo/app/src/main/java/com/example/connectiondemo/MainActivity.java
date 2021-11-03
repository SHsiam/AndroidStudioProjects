package com.example.connectiondemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    //create a layout where u ll be having button then image view then text view scroll view
    ConnectivityManager cm;
    ImageView iv;
    TextView tv;
    Boolean flag=false;
    ProgressDialog pd;
    MediaPlayer mp;
    String filepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cm=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        iv=findViewById(R.id.iv1);
        tv=findViewById(R.id.tv1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkWritePermission();
        mp=new MediaPlayer();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mp.stop();

    }

    void checkWritePermission()
    {
        if((ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                PackageManager.PERMISSION_GRANTED))
        {
            flag=true;
            Toast.makeText(this,"permission Already granted ",Toast.LENGTH_LONG).show();
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                checkWritePermission();
            }
            else
            {
                Toast.makeText(this,"Write permission denied by user ",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void checkNetworkStatus(View v)
    {
        NetworkInfo info=cm.getActiveNetworkInfo();
        if(info!=null)
        {
            if(info.getType()==ConnectivityManager.TYPE_WIFI)
            {
               // String textUrl="https://drive.google.com/uc?export=download&id=1yGVhPBOftF1YH0xpiO8bTXOqrC64zRiQ";
                //new MyTextFile().execute(textUrl);
               //String imageUrl="https://wallpapersite.com/images/pages/pic_w/18929.jpg";
               // new ImageClass().execute(imageUrl);
                String musicUrl = "https://drive.google.com/uc?export=download&id=1SIIOas1JY34xi1xwvKLK4_XG4no8OMXV";
                new music().execute(musicUrl);
                Toast.makeText(this,"Connected with WIFI",Toast.LENGTH_LONG).show();
            }
            else if(info.getType()==ConnectivityManager.TYPE_MOBILE)

            {

                Toast.makeText(this,"Connected with Mobile",Toast.LENGTH_LONG).show();
            }
            else
            {
//toast
                Toast.makeText(this,"No network found",Toast.LENGTH_LONG).show();
            }

        }
    }
    class MyTextFile extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            return downloadText(strings[0]);
        }
        String downloadText(String path)
        {
            String s=null;
            try
            {
                URL myurl=new URL(path);
                HttpURLConnection httpURLConnection=(HttpURLConnection)myurl.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                int code=httpURLConnection.getResponseCode();
                if(code==httpURLConnection.HTTP_OK)
                {
                    InputStream stream=httpURLConnection.getInputStream();
                    if(stream!=null)
                    {
                        BufferedReader reader=new BufferedReader((new InputStreamReader(stream)));
                        String line="";
                        String text="";
                        while((line=reader.readLine())!=null)
                        {
                            text=text+line+"\n";
                        }
                        s=text;
                        if(flag)
                        {
                            String state= Environment.getExternalStorageState();
                            if(state.equals(Environment.MEDIA_MOUNTED))
                            {
                                File root=Environment.getExternalStorageDirectory();
                                File f=new File(root,"datatoread.txt");
                                FileOutputStream ff=new FileOutputStream(f);
                                byte[] b=s.getBytes();
                                ff.write(b);
                                ff.close();
                            }
                        }
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
            return s;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {
                tv.setText(s);
            }
        }
    }
    class ImageClass extends AsyncTask<String,Void, Bitmap>{
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
        Bitmap fetchImage(String s){
            Bitmap bitmap=null;
            try{
                URL myImageUrl=new URL(s);
                HttpURLConnection httpURLConnection=(HttpURLConnection)myImageUrl.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setRequestMethod("get");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                int code=httpURLConnection.getResponseCode();
                if(code==HttpURLConnection.HTTP_OK){
                    InputStream inputStream=httpURLConnection.getInputStream();
                    if(inputStream!=null){
                        bitmap= BitmapFactory.decodeStream(inputStream);
                    }
                }
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap!=null){
                iv.setImageBitmap(bitmap);
                pd.dismiss();
            }else{
                Toast.makeText(getApplicationContext(),"Bitmap Null",Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }
    }
    class music extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return fetchMusicFile(strings[0]);
        }
        String fetchMusicFile(String path)
        {
            String s=null;
            try
            {
                URL myurl=new URL(path);
                HttpURLConnection httpURLConnection=(HttpURLConnection)myurl.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                int code=httpURLConnection.getResponseCode();
                if(code==HttpURLConnection.HTTP_OK)
                {
                    InputStream stream=httpURLConnection.getInputStream();
                    if(stream!=null)
                    {
                        if(flag)
                        {
                            String state=Environment.getExternalStorageState();
                            if(state.equals((Environment.MEDIA_MOUNTED)))
                            {
                                File root=Environment.getExternalStorageDirectory();
                                File file1=new File(root,"mymusic.mp3");
                                FileOutputStream fos=new FileOutputStream(file1);
                                int i=0;
                                while((i=stream.read())!=-1)
                                {
                                    fos.write(i);
                                }
                                s="DONE";
                                filepath=file1.getAbsolutePath();
                                fos.close();;

                            }
                        }

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

            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                try {
                    mp.setDataSource(filepath);
                    mp.prepare();
                    mp.start();
                } catch (Exception e) {

                }
            }

        }
    }
}
