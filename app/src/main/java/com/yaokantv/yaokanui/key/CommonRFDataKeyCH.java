package com.yaokantv.yaokanui.key;

/**
 * Created by liujiayu open 2018/1/30.
 */

public enum CommonRFDataKeyCH {
    KEY1("按键1"), KEY2("按键2"), KEY3("按键3"),
    KEY4("按键4"),
    KEY5("按键5"), KEY6("按键6"), KEY7("按键7"),
    KEY8("按键8");
    private String key;

    // 构造方法
    private CommonRFDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
