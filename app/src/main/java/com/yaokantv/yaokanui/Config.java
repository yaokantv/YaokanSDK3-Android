package com.yaokantv.yaokanui;

import com.yaokantv.yaokansdk.model.Operators;

public class Config {
    public static final String ACTIVITY_TYPE = "ACTIVITY_TYPE";
    public static final String S_IS_STB = "isStb";
    public static final String S_RC_TYPE = "rcType";
    public static final int TYPE_MATCHING = 3;
    public static final int TYPE_RC = 4;
    public static final int TYPE_RC_STUDY = 5;
    public static final int TYPE_RC_RF_MATCH_STUDY = 6;

    public static final String S_IS_RF = "isRf";
    public static final String S_TID = "tid";
    public static final String S_BID = "bid";
    public static final String S_GID = "gid";
    public static final String S_OPE = "ope";
    public static final String S_TAG = "tag";
    public static String MAC = "";
    public static String DID = "";
    public static String curTName = "";//设备类型
    public static String curBName = "";//品牌名称
    public static boolean IS_MATCH = false;
    public static boolean IS_SHOW_SETTING = true;
    public static Operators operators = null;//运营商对象


    public static void isShowSetting(boolean b) {
        IS_SHOW_SETTING = b;
    }

    public static void setMac(String mac) {
        MAC = mac;
    }

    public static void setDid(String mac) {
        DID = mac;
    }
}
