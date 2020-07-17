package com.yaokantv.yaokanui.key;

public enum LiangbaRFDataKeyCH {
    PTWIND("摆风"), LOW("低风"), MID("中风"),
    HIGH("高风"), TIME_DN("定时-"), TIME_UP("定时+"),

    POWER("电源"), LIGHT("照明"),

    SWING("吹风");
    private String key;

    // 构造方法
    private LiangbaRFDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
