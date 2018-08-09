package com.matthewz.mvvmdemo1;

import android.arch.lifecycle.LiveData;

public class LocationLiveData extends LiveData<Location> {
    public LocationLiveData() {
        setValue(new Location());
    }

}
