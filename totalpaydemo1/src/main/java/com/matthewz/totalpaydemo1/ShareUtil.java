package com.matthewz.totalpaydemo1;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ShareUtil {

    private static final String SEPERATOR = "------------seperator------------";

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

    public List<String> getGoods() {
        String all_goods = sp.getString("all_goods", null);
        if(TextUtils.isEmpty(all_goods))
            return null;
        String[] strings = all_goods.split(SEPERATOR);
        return new ArrayList<>(Arrays.asList(strings));
    }

    public void saveGoods(List<String> goods) {
        String saveStr = null;
        if(null != goods && !goods.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < goods.size(); i++) {
                builder.append(goods.get(i));
                if(i < goods.size() - 1) {
                    builder.append(SEPERATOR);
                }
            }
            saveStr = builder.toString();
        }
        sp.edit().putString("all_goods", saveStr).apply();
    }
}
