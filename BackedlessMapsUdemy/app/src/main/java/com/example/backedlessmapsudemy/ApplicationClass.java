package com.example.backedlessmapsudemy;

import android.app.Application;

import com.backendless.Backendless;

public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl("https://api.backendless.com");
        Backendless.initApp(getApplicationContext(),"DCE78938-E2EC-A6AC-FF0A-00860B9F1800","ACC055CF-8A2A-41B3-984A-9E83BD97C472");
    }
}
