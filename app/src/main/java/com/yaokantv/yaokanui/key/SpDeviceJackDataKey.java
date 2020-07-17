package com.yaokantv.yaokanui.key;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiayu on 2018/5/21.
 */

public enum SpDeviceJackDataKey {
    ON("on"), OFF("off") ;

    private String key;

    // 构造方法
    SpDeviceJackDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public static List<SpDeviceJackDataKey> getKeys() {
        List<SpDeviceJackDataKey> keys = new ArrayList<>();
        keys.add(SpDeviceJackDataKey.OFF);
        keys.add(SpDeviceJackDataKey.ON);
        return keys;
    }
}
