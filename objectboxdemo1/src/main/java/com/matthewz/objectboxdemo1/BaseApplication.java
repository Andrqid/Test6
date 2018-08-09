package com.matthewz.objectboxdemo1;

import android.app.Application;

import com.matthewz.objectboxdemo1.entity.MyObjectBox;

import io.objectbox.BoxStore;

public class BaseApplication extends Application {

    public static BaseApplication sApplication;
    public BoxStore mBoxStore;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;
        mBoxStore = MyObjectBox.builder().androidContext(this).build();
    }
}
