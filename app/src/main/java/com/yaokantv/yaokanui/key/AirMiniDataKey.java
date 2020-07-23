package com.yaokantv.yaokanui.key;

public enum AirMiniDataKey { // 枚举所有键值
    POWER("power"),TEMP_UP("temp+"),

    TEMP_DOWN("temp-"),MODE("mode"),

    FANSPEED("fanspeed"),LRWIND("lrwind"),

    UDWIND("udwind");

    private String key;

    // 构造方法
    private AirMiniDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
