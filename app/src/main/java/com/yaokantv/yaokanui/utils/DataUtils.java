package com.yaokantv.yaokanui.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DataUtils {
    static final String NAME = "ykk";
    public static final String CAMERA_TIME = "CAMERA_TIME";

    private Context ctx;

    public DataUtils() {
    }

    public DataUtils(Context ctx) {
        this.ctx = ctx;
    }

    public void setKeyValue(String key, int value) {
        SharedPreferences.Editor localEditor = ctx.getSharedPreferences(NAME,Context.MODE_PRIVATE).edit();
        localEditor.putInt(key, value).commit();
    }
    public void setKeyValue(String key, String value) {
        SharedPreferences.Editor localEditor = ctx.getSharedPreferences(NAME,Context.MODE_PRIVATE).edit();
        localEditor.putString(key, value).commit();
    }

    public int getKeyIntValue(String key) {
        return  ctx.getSharedPreferences(NAME,Context.MODE_PRIVATE).getInt(key, 10);
    }
    public String getKeyStringValue(String key) {
        return  ctx.getSharedPreferences(NAME,Context.MODE_PRIVATE).getString(key, "");
    }

}
