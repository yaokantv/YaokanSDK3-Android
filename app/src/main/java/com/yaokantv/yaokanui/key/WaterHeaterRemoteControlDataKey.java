package com.yaokantv.yaokanui.key;

/**
 * 风扇类型存储遥控器面板上键对应的Key
 */
public enum WaterHeaterRemoteControlDataKey {

    // 枚举所有键值
    POWER("power"), TEMP_UP("temp+"), TEMP_DOWN("temp-");

    private String key;

    // 构造方法
    private WaterHeaterRemoteControlDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
