package com.yaokantv.yaokanui.key;

/**
 * Created by liujiayu open 2018/1/30.
 */

public enum HangerRFDataKey {
    RISE("rise"), STOP("stop"), DROP("drop"),
    LIGHT("light"),
    POWER("power"), DISINFECT("disinfect"), DRYING("drying"),
    AIRDRY("airdry");
    private String key;

    // 构造方法
    private HangerRFDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
