package com.yaokantv.yaokanui.key;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiayu on 2018/5/21.
 */

public enum SpDeviceJackDataKeyCH {
    ON("开"), OFF("关");

    private String key;

    // 构造方法
    SpDeviceJackDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static List<SpDeviceJackDataKeyCH> getKeys() {
        List<SpDeviceJackDataKeyCH> keys = new ArrayList<>();
        keys.add(SpDeviceJackDataKeyCH.OFF);
        keys.add(SpDeviceJackDataKeyCH.ON);
        return keys;
    }
}
