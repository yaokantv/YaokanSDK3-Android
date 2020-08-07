package com.yaokantv.yaokanui.bean;

public class BedMode {
    public static final int MODE_LIE = 1;
    public static final int MODE_DEEP_SLEEP = 2;
    public static final int MODE_SLEEP = 3;
    public static final int MODE_YOGA = 4;
    public static final int MODE_RELAX = 5;
    public static final int MODE_ARDER = 6;
    public static final int MODE_OFFICE = 7;
    public static final int MODE_WAKE_UP = 8;

    private int mode;
    private String name;
    private String key;

    public BedMode(int mode, String name,String key) {
        this.mode = mode;
        this.name = name;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
