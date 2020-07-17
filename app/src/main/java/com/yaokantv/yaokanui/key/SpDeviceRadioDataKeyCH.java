package com.yaokantv.yaokanui.key;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiayu on 2018/5/21.
 */

public enum SpDeviceRadioDataKeyCH {
    ON("开"), OFF("关"), RADIO("收音机模式"),
    BLUE("蓝牙模式"), TF("TF卡模式"), CLOCK("时钟模式"),
    PLAY("播放"), PAUSE("暂停"), VOLUME_ADD("音量加"),
    VOLUME_SUB("音量减"), SONG_ADD("曲目加"), SONG_SUB("曲目减"),
    CHANEL_ADD("频道加"), CHANEL_SUB("频道减");

    private String key;

    // 构造方法
    SpDeviceRadioDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static List<SpDeviceRadioDataKeyCH> getKeys() {
        List<SpDeviceRadioDataKeyCH> keys = new ArrayList<>();
        keys.add(SpDeviceRadioDataKeyCH.OFF);
        keys.add(SpDeviceRadioDataKeyCH.ON);
        keys.add(SpDeviceRadioDataKeyCH.RADIO);
        keys.add(SpDeviceRadioDataKeyCH.BLUE);
        keys.add(SpDeviceRadioDataKeyCH.TF);
        keys.add(SpDeviceRadioDataKeyCH.CLOCK);
        keys.add(SpDeviceRadioDataKeyCH.PLAY);
        keys.add(SpDeviceRadioDataKeyCH.PAUSE);
        keys.add(SpDeviceRadioDataKeyCH.VOLUME_ADD);
        keys.add(SpDeviceRadioDataKeyCH.VOLUME_SUB);
        keys.add(SpDeviceRadioDataKeyCH.SONG_ADD);
        keys.add(SpDeviceRadioDataKeyCH.SONG_SUB);
        keys.add(SpDeviceRadioDataKeyCH.CHANEL_ADD);
        keys.add(SpDeviceRadioDataKeyCH.CHANEL_SUB);
        return keys;
    }
}
