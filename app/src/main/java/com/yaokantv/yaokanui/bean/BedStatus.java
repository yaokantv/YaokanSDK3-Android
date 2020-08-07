package com.yaokantv.yaokanui.bean;

import com.google.gson.Gson;

public class BedStatus {
    private boolean head_sock = false;
    private boolean light = false;
    private boolean foot_sock = false;
    private int mode;
    private int time;

    public String getJson() {
        return new Gson().toJson(this);
    }

    public boolean isHead_sock() {
        return head_sock;
    }

    public void setHead_sock(boolean head_sock) {
        this.head_sock = head_sock;
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public boolean isFoot_sock() {
        return foot_sock;
    }

    public void setFoot_sock(boolean foot_sock) {
        this.foot_sock = foot_sock;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
