package com.yaokantv.yaokanui.key;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiayu on 2018/5/21.
 */

public enum SpDeviceCurtainDataKey {
    ON("open"), OFF("close"), STOP("pause");

    private String key;

    // 构造方法
    SpDeviceCurtainDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static List<SpDeviceCurtainDataKey> getKeys() {
        List<SpDeviceCurtainDataKey> keys = new ArrayList<>();
        keys.add(SpDeviceCurtainDataKey.OFF);
        keys.add(SpDeviceCurtainDataKey.ON);
        keys.add(SpDeviceCurtainDataKey.STOP);
        return keys;
    }
}
