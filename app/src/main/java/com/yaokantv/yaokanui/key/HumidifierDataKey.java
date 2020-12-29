package com.yaokantv.yaokanui.key;

public enum HumidifierDataKey { // 枚举所有键值
    POWER("power"), AMOUNTOFFOG("amountoffog"),

    timer("timer"), sleep("sleep"),

    CONSTANT_HUMIDITY("constanthumidity");

    private String key;

    // 构造方法
    private HumidifierDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
