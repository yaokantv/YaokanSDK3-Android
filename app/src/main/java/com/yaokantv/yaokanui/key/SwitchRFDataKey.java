package com.yaokantv.yaokanui.key;

/**
 * Created by liujiayu on 2018/1/30.
 */

public enum SwitchRFDataKey {
    ON("on"), OFF("off"), POWER("power"),
    ON1("on1"), OFF1("off1"), ON2("on2"), OFF2("off2"),ON3("on3"), OFF3("off3");
    private String key;

    // 构造方法
    private SwitchRFDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
