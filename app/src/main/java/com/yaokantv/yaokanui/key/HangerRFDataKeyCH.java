package com.yaokantv.yaokanui.key;

/**
 * Created by liujiayu open 2018/1/30.
 */

public enum HangerRFDataKeyCH {
    RISE("上升"), STOP("停止"), DROP("下降"),
    LIGHT("照明"),
    POWER("电源"), DISINFECT("消毒"), DRYING("烘干"),
    AIRDRY("风干") ;
    private String key;

    // 构造方法
    private HangerRFDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
