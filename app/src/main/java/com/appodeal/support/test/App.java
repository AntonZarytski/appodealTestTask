package com.appodeal.support.test;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
        MultiDex.install(this);
    }
}
