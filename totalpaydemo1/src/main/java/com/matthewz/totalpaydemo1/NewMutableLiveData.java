package com.matthewz.totalpaydemo1;

import android.arch.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class NewMutableLiveData<T> extends MutableLiveData<T> implements MultiItemEntity{

    @Override
    public int getItemType() {
        Object value = getValue();
        if(null == value || !(value instanceof MultiItemEntity)) {
            return -1;
        }
        return ((MultiItemEntity)value).getItemType();
    }
}
