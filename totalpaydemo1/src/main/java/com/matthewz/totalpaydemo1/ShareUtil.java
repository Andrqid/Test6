package com.matthewz.totalpaydemo1;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class ShareUtil {

    private SharedPreferences sp;

    private ShareUtil() {
        sp = BaseApplication.getInstance().getSharedPreferences("testtest", Context.MODE_PRIVATE);
    }

    public static ShareUtil getInstance() {
        return InnerHolder.SHARE_UTIL;
    }

    private static class InnerHolder {
        private static final ShareUtil SHARE_UTIL = new ShareUtil();
    }

    public Set<String> getGoods() {
        return sp.getStringSet("all_goods", null);
    }

    public void saveGoods(Set<String> set) {
        sp.edit().putStringSet("all_goods", set).apply();
    }
}
