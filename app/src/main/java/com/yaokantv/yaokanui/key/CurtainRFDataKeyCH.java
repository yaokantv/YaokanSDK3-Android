package com.yaokantv.yaokanui.key;

/**
 * Created by liujiayu open 2018/1/30.
 */

public enum CurtainRFDataKeyCH {
    OPEN("打开"), CLOSE("关闭"), PAUSE("暂停"),
    OPEN1("1打开"), CLOSE1("1关闭"),PAUSE1("1暂停"),
    OPEN2("2打开"), CLOSE2("2关闭"),PAUSE2("2暂停") ;
    private String key;

    // 构造方法
    private CurtainRFDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
