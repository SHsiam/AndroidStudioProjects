package com.example.backendlesssetup;

import android.app.Application;

import com.backendless.Backendless;

public class BackendlessApplication extends Application {
    public static final String APPLICATION_ID="D1D36DFA-2551-26AF-FFBD-827ADC0A0F00";
    public static final String API_KEY="1FFE39CB-AA11-4BD4-9D2F-9C81552051A8";
    public static final String SERVER_URL="https://api.backendless.com";
    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(getApplicationContext(),APPLICATION_ID,API_KEY);
    }
}
