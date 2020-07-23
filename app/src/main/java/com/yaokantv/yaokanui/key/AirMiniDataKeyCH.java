package com.yaokantv.yaokanui.key;

public enum AirMiniDataKeyCH { // 枚举所有键值
    // 枚举所有键值
    POWER("电源"), TEMP_UP("温度加"), TEMP_DOWN("温度减"), MODE("模式"), FANSPEED("风速"), LRWIND("左右摆风"), UDWIND("上下摆风");

    private String key;

    // 构造方法
    private AirMiniDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
