package com.yaokantv.yaokanui.utils;

import android.content.Context;

import com.yaokantv.sdkdemo.R;

public class StringUtils {
    public static final String DRA_BTN_CIRCLE = "btn_circle";
    public static final String DRA_BTN_NUM = "btn_num";
    public static final String DRA_BG_MATCHING = "bg_matching";
    public static final String DRA_SQUARE = "bg_square";
    public static final String DRA_PLAY = "bg_play";
    public static final String DRA_CAMERA = "bg_camera";

    public static String getTypeString(Context context, int type) {
        if (type == 1) {
            return context.getString(R.string.stb);
        } else if (type == 2) {
            return context.getString(R.string.tv);
        } else if (type == 3) {
            return context.getString(R.string.dvd);
        } else if (type == 5) {
            return context.getString(R.string.projector);
        } else if (type == 6) {
            return context.getString(R.string.fan);
        } else if (type == 7) {
            return context.getString(R.string.air);
        } else if (type == 8) {
            return context.getString(R.string.light);
        } else if (type == 10) {
            return context.getString(R.string.box);
        } else if (type == 12) {
            return context.getString(R.string.sweeper);
        } else if (type == 13) {
            return context.getString(R.string.audio);
        } else if (type == 14) {
            return context.getString(R.string.camera);
        } else if (type == 16) {
            return context.getString(R.string.sweeper);
        } else if (type == 21) {
            return context.getString(R.string.switch_);
        } else if (type == 22) {
            return context.getString(R.string.jack);
        } else if (type == 23) {
            return context.getString(R.string.curtain);
        } else if (type == 24) {
            return context.getString(R.string.hanger);
        } else if (type == 25) {
            return context.getString(R.string.light_ctrl);
        } else if (type == 38) {
            return context.getString(R.string.fan_light);
        } else if (type == 40) {
            return context.getString(R.string.water_heater);
        } else if (type == 41) {
            return context.getString(R.string.liangba);
        }
        return "";
    }
}
