package com.yaokantv.yaokanui.key;

/**
 * Created by liujiayu on 2018/1/30.
 */

public enum SwitchRFDataKeyCH {
    ON("开"), OFF("关"), POWER("电源"),
    ON1("1开"), OFF1("1关"), ON2("2开"), OFF2("2关"),ON3("3开"), OFF3("3关");
    private String key;

    // 构造方法
    private SwitchRFDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
