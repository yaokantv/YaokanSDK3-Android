package com.yaokantv.yaokanui.key;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiayu on 2018/5/21.
 */

public enum SpDeviceRadioDataKey {
    ON("on"), OFF("off"), RADIO("radio"),
    BLUE("blue"), TF("tf"), CLOCK("clock"),
    PLAY("play"), PAUSE("pause"), VOLUME_ADD("volume_add"),
    VOLUME_SUB("volume_sub"), SONG_ADD("song_add"), SONG_SUB("song_sub"),
    CHANEL_ADD("chanel_add"), CHANEL_SUB("chanel_sub");

    private String key;

    // 构造方法
    SpDeviceRadioDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static List<SpDeviceRadioDataKey> getKeys() {
        List<SpDeviceRadioDataKey> keys = new ArrayList<>();
        keys.add(SpDeviceRadioDataKey.OFF);
        keys.add(SpDeviceRadioDataKey.ON);
        keys.add(SpDeviceRadioDataKey.RADIO);
        keys.add(SpDeviceRadioDataKey.BLUE);
        keys.add(SpDeviceRadioDataKey.TF);
        keys.add(SpDeviceRadioDataKey.CLOCK);
        keys.add(SpDeviceRadioDataKey.PLAY);
        keys.add(SpDeviceRadioDataKey.PAUSE);
        keys.add(SpDeviceRadioDataKey.VOLUME_ADD);
        keys.add(SpDeviceRadioDataKey.VOLUME_SUB);
        keys.add(SpDeviceRadioDataKey.SONG_ADD);
        keys.add(SpDeviceRadioDataKey.SONG_SUB);
        keys.add(SpDeviceRadioDataKey.CHANEL_ADD);
        keys.add(SpDeviceRadioDataKey.CHANEL_SUB);
        return keys;
    }
}
