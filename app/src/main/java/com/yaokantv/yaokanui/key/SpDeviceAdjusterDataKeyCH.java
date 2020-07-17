package com.yaokantv.yaokanui.key;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiayu on 2018/5/21.
 */

public enum SpDeviceAdjusterDataKeyCH {
    K0("亮度0"), K1("亮度1"), K2("亮度2"), K3("亮度3"), K4("亮度4"), K5("亮度5"), K6("亮度6"), K7("亮度7"), K8("亮度8"), K9("亮度9"), K10("亮度10"),
    K11("亮度11"), K12("亮度12"), K13("亮度13"), K14("亮度14"), K15("亮度15"), K16("亮度16"), K17("亮度17"), K18("亮度18"), K19("亮度19"), K20("亮度20"),
    K21("亮度21"), K22("亮度22"), K23("亮度23"), K24("亮度24"), K25("亮度25"), K26("亮度26"), K27("亮度27"), K28("亮度28"), K29("亮度29"), K30("亮度30"),
    K31("亮度31"), K32("亮度32"), K33("亮度33"), K34("亮度34"), K35("亮度35"), K36("亮度36"), K37("亮度37"), K38("亮度38"), K39("亮度39"), K40("亮度40"),
    K41("亮度41"), K42("亮度42"), K43("亮度43"), K44("亮度44"), K45("亮度45"), K46("亮度46"), K47("亮度47"), K48("亮度48"), K49("亮度49"), K50("亮度50"),
    K51("亮度51"), K52("亮度52"), K53("亮度53"), K54("亮度54"), K55("亮度55"), K56("亮度56"), K57("亮度57"), K58("亮度58"), K59("亮度59"), K60("亮度60"),
    K61("亮度61"), K62("亮度62"), K63("亮度63"), K64("亮度64"), K65("亮度65"), K66("亮度66"), K67("亮度67"), K68("亮度68"), K69("亮度69"), K70("亮度70"),
    K71("亮度71"), K72("亮度72"), K73("亮度73"), K74("亮度74"), K75("亮度75"), K76("亮度76"), K77("亮度77"), K78("亮度78"), K79("亮度79"), K80("亮度80"),
    K81("亮度81"), K82("亮度82"), K83("亮度83"), K84("亮度84"), K85("亮度85"), K86("亮度86"), K87("亮度87"), K88("亮度88"), K89("亮度89"), K90("亮度90"),
    K91("亮度91"), K92("亮度92"), K93("亮度93"), K94("亮度94"), K95("亮度95"), K96("亮度96"), K97("亮度97"), K98("亮度98"), K99("亮度99"), K100("亮度100");

    private String key;

    // 构造方法
    SpDeviceAdjusterDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public static List<SpDeviceAdjusterDataKeyCH> getKeys() {
        List<SpDeviceAdjusterDataKeyCH> keys = new ArrayList<>();
        for (SpDeviceAdjusterDataKeyCH key : SpDeviceAdjusterDataKeyCH.values()) {
            keys.add(key);
        }
        return keys;
    }
}
