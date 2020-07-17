package com.yaokantv.yaokanui.key;

/**
 * Created by liujiayu open 2018/1/30.
 */

public enum CommonRFDataKey {
    KEY1("key1"), KEY2("key2"), KEY3("key3"),
    KEY4("key4"),
    KEY5("key5"), KEY6("key6"), KEY7("key7"),
    KEY8("key8");
    private String key;

    // 构造方法
    private CommonRFDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
