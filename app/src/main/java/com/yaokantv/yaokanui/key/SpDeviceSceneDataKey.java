package com.yaokantv.yaokanui.key;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiayu on 2018/5/21.
 */

public enum SpDeviceSceneDataKey {
    SCENE("scene");

    private String key;

    // 构造方法
    SpDeviceSceneDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public static List<SpDeviceSceneDataKey> getKeys() {
        List<SpDeviceSceneDataKey> keys = new ArrayList<>();
        keys.add(SpDeviceSceneDataKey.SCENE);
        return keys;
    }
}
