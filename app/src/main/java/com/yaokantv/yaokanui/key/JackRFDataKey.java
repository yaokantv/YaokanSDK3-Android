package com.yaokantv.yaokanui.key;

/**
 * Created by liujiayu on 2018/1/30.
 */

public enum JackRFDataKey {
    ON("on"), OFF("off"), POWER("power"),
    STOP("stop"), LOW("low"), MID("mid"), HIGH("high"), LIGHT("light"),
    H1("1h"), H2("2h"), H4("4h"), H8("8h");
    private String key;

    // 构造方法
    private JackRFDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
