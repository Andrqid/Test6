package com.matthewz.toastutildemo1;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ToastUtil.init(this);
    }
}
