package com.example.webtoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    WebView mwebview;
    ProgressBar progressBar;
    String sb="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mwebview = findViewById(R.id.web);
        progressBar = findViewById(R.id.prb);

        if(savedInstanceState == null)

            mwebview.loadUrl("https://www.fiverr.com/");


        mwebview.getSettings().setJavaScriptEnabled(true);
        mwebview.setWebViewClient(new MyWebViewClient());
//to fetch WebViewClient class
        mwebview.setWebChromeClient(new MyWebCromeClient());
    }

    class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,
                                                WebResourceRequest request) {
            String url = request.getUrl().toString();
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            getSupportActionBar().setTitle(view.getTitle());
        }
    }

    class MyWebCromeClient extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
//super.onReceivedTitle(view, title);
            sb= title;
            getSupportActionBar().setTitle(title);

        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);

        }}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int k = item.getItemId();
        switch (k)
        {
            case R.id.back:
                if(mwebview.canGoBack())
                    mwebview.goBack();

                break;
            case R.id.forward:
                if(mwebview.canGoForward())
                    mwebview.goForward();
                break;
            case R.id.reload:
                mwebview.reload();
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed() {

        if(mwebview.canGoBack())
        {
            mwebview.goBack();
        }
        else
            super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mwebview.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mwebview.restoreState(savedInstanceState);
    }
}
