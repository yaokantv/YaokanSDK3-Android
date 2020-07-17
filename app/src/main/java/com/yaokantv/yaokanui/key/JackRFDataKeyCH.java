package com.yaokantv.yaokanui.key;

/**
 * Created by liujiayu on 2018/1/30.
 */

public enum JackRFDataKeyCH {
    ON("开"), OFF("关"), POWER("电源"),
    STOP("停止"), LOW("低风"), MID("中风"), HIGH("高风"), LIGHT("照明"),
    H1("1h"), H2("2h"), H4("4h"), H8("8h");
    private String key;

    // 构造方法
    private JackRFDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
