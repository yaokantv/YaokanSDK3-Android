package com.yaokantv.yaokanui.key;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiayu on 2018/5/21.
 */

public enum SpDeviceNormalDataKeyCH {
    ON("开"), OFF("关"), POWER("电源");

    private String key;

    // 构造方法
    SpDeviceNormalDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public static List<SpDeviceNormalDataKeyCH> getKeys() {
        List<SpDeviceNormalDataKeyCH> keys = new ArrayList<>();
        keys.add(SpDeviceNormalDataKeyCH.OFF);
        keys.add(SpDeviceNormalDataKeyCH.ON);
        return keys;
    }
}
