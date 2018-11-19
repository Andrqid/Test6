package com.matthewz.totalpaydemo1;

import android.app.Application;

public class BaseApplication extends Application {

    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}
