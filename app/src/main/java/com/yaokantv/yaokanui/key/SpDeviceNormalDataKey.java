package com.yaokantv.yaokanui.key;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiayu on 2018/5/21.
 */

public enum SpDeviceNormalDataKey {
    ON("open"), OFF("close"), POWER("power");

    private String key;

    // 构造方法
    SpDeviceNormalDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public static List<SpDeviceNormalDataKey> getKeys() {
        List<SpDeviceNormalDataKey> keys = new ArrayList<>();
        keys.add(SpDeviceNormalDataKey.OFF);
        keys.add(SpDeviceNormalDataKey.ON);
        return keys;
    }
}
