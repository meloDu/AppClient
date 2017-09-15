package com.android.rmtd.appclient;

import android.app.Application;

/**
 * Created by melo on 2017/9/15.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SmartControl.initSDK(getApplicationContext());
    }
}
