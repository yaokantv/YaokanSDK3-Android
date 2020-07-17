package com.yaokantv.yaokanui.key;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiayu on 2018/5/21.
 */

public enum SpDeviceCurtainDataKeyCH {
    ON("开"), OFF("关"), STOP("停止");

    private String key;

    // 构造方法
    SpDeviceCurtainDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static List<SpDeviceCurtainDataKeyCH> getKeys() {
        List<SpDeviceCurtainDataKeyCH> keys = new ArrayList<>();
        keys.add(SpDeviceCurtainDataKeyCH.OFF);
        keys.add(SpDeviceCurtainDataKeyCH.ON);
        keys.add(SpDeviceCurtainDataKeyCH.STOP);
        return keys;
    }
}
