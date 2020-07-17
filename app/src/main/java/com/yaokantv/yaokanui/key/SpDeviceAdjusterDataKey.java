package com.yaokantv.yaokanui.key;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiayu on 2018/5/21.
 */

public enum SpDeviceAdjusterDataKey {
    K0("power"), K1("poweroff"), K2("k2"), K3("k3"), K4("k4"), K5("k5"), K6("k6"), K7("k7"), K8("k8"), K9("k9"), K10("k10"),
    K11("k11"), K12("k12"), K13("k13"), K14("k14"), K15("k15"), K16("k16"), K17("k17"), K18("k18"), K19("k19"), K20("k20"),
    K21("k21"), K22("k22"), K23("k23"), K24("k24"), K25("k25"), K26("k26"), K27("k27"), K28("k28"), K29("k29"), K30("k30"),
    K31("k31"), K32("k32"), K33("k33"), K34("k34"), K35("k35"), K36("k36"), K37("k37"), K38("k38"), K39("k39"), K40("k40"),
    K41("k41"), K42("k42"), K43("k43"), K44("k44"), K45("k45"), K46("k46"), K47("k47"), K48("k48"), K49("k49"), K50("k50"),
    K51("k51"), K52("k52"), K53("k53"), K54("k54"), K55("k55"), K56("k56"), K57("k57"), K58("k58"), K59("k59"), K60("k60"),
    K61("k61"), K62("k62"), K63("k63"), K64("k64"), K65("k65"), K66("k66"), K67("k67"), K68("k68"), K69("k69"), K70("k70"),
    K71("k71"), K72("k72"), K73("k73"), K74("k74"), K75("k75"), K76("k76"), K77("k77"), K78("k78"), K79("k79"), K80("k80"),
    K81("k81"), K82("k82"), K83("k83"), K84("k84"), K85("k85"), K86("k86"), K87("k87"), K88("k88"), K89("k89"), K90("k90"),
    K91("k91"), K92("k92"), K93("k93"), K94("k94"), K95("k95"), K96("k96"), K97("k97"), K98("k98"), K99("k99"), K100("k100");

    private String key;

    // 构造方法
    SpDeviceAdjusterDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public static List<SpDeviceAdjusterDataKey> getKeys() {
        List<SpDeviceAdjusterDataKey> keys = new ArrayList<>();
        for (SpDeviceAdjusterDataKey key : SpDeviceAdjusterDataKey.values()) {
            keys.add(key);
        }
        return keys;
    }
}
