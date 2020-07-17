package com.yaokantv.yaokanui.key;

/**
 * Created by liujiayu open 2018/1/30.
 */

public enum CurtainRFDataKey {
    OPEN("open"), CLOSE("close"), PAUSE("pause"),
    OPEN1("open1"), CLOSE1("close1"),PAUSE1("pause1"),
    OPEN2("open2"), CLOSE2("close2"),PAUSE2("pause2") ;
    private String key;

    // 构造方法
    private CurtainRFDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
