package com.yaokantv.yaokanui.key;

public enum LiangbaRFDataKey {
    STOP("fanstop"),LOW("low"),

    MID("mid"),

    HIGH("high"),TIME_DN("timer-"),

    TIME_UP("timer+"),

    POWER("power"),LIGHT("light"),

    SWING("blowwind");

    private String key;

    // 构造方法
    private LiangbaRFDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
