package com.yaokantv.yaokanui.key;

import android.content.Context;

public class CtrlContants {

    public final static class CodeType {
        public final static String CTRL_CODE_TYPE_BLUETOOTH = "1"; //蓝牙码库

        public final static String CTRL_CODE_TYPE_STANDARD = "3";//标准码库

        public final static String CTRL_CODE_TYPE_HUAWEI = "4";//华为码库

        public final static String CTRL_CODE_TYPE_UEI = "5";//uei码库

    }

    public static final class ConnType {

        public static final String AUDIO = "audio"; //音频外设

        public static final String AUDIOTWO = "audiotwo"; //音频外设2.0

        public static final String BTTWO = "bttwo";//蓝牙2.0

        public static final String BTFOUR = "btfour";//蓝牙4.0

        public static final String USB = "usb";//USB

        public static final String UEI = "uei"; //UEI

        public static final String WIFI = "wifi";//wifi

        public static final String WIFI_433 = "wifi_433";//wifi

        public static final String YK_WIFI = "ykwifi";//wifi

        public static final String HTC = "htc";//HTC

        public static final String OPPO = "oppo";//OPPO

        public static final String MIFOUR = "mifour";// MIFOUR

        public static final String SAMSUNG = "samsung";//SAMSUNG

        public static final String HSIX = "hsix";//HSIX

        public static final String LG = "lg";//LG

        public static final String OTHER = "other";//OTHER

        public static final String HWHN3 = "hwhn3";//HWHN3

        public static final String GIONEEF303 = "gioneef303";//金立303

        public static final String OS_STANDARD = "osstandard";//金立303

        public static final String ABOVE = "above";//above

        public static final String YKAN = "ykan";//ykan

        public static final String LETV = "uei_letv";//uei_letv


    }

    public static final class RemoteControlType {

        public static final int SP = -2;//添加

        public static final int ADD = -1;//添加

        public static final int STB = 1;// 被遥控设备类型为有线机顶盒

        public static final int TV = 2;//被遥控设备类型为电视机

        public static final int DVD = 3;//被遥控设备类型为DVD播放机

        public static final int AUDIO = 13;// UEI 音响

        public static final int PROJECTOR = 5;//被遥控设备类型为投影仪

        public static final int FANNER = 6;//被遥控设备类型为电风扇

        public static final int AIRCONDITION = 7;//被遥控设备类型为空调

        public static final int BOX = 10;//被遥控设备类型为盒子

        public static final int LIGHT = 8; // 被遥控设备类型为智能灯泡

        public static final int IPTV = 4; //被遥控设备类型为IPTV机顶盒

        public static final int SATV = 11; //被遥控设备类型为卫星电视

        public static final int SWEEPER = 12; // 被遥控设备类型为扫地机

        public static final int AIRPURIFIER = 15; // 被遥控设备类型为淨化器

        public static final int CAMERA = 14;// 被遥控设备类型为照相机

        public static final int FOOTHBATH = 16;// 被遥控设备类型为洗脚盆

        public static final int MULTIMEDIA = 17;// 被遥控设备类型为汽车多媒体//multimedia

        public static final int NINHT_LIGHT = 18;// 被遥控设备类型为夜灯//

        public static final int WATER_HEATER = 40;// 被遥控设备类型为热水器

        public static final int YK_433 = 19;// 433/315

        public static final int DIY = 99;// 自定义

        public static final int SWITCH_RF = 21;// 射频遥控开关

        public static final int JACK_RF = 22;// 射频遥控插座

        public static final int CURTAIN_RF = 23;// 射频遥控窗帘

        public static final int HANDGER_RF = 24;// 射频遥控衣架

        public static final int LIGHT_CTRL = 25;// 灯控器

        //        public static final int SP_SWITCH = 25;// 超级开关-开关 normal
//        public static final int SP_CURTAIN = 26;// 超级开关-窗帘
//        public static final int SP_TEMP = 27;// 超级开关-温控器
        public static final int SP_RADIO = 28;// 超级开关-收音机
        //        public static final int SP_INDUCTOR = 29;// 超级开关-感应开关 normal
//        public static final int SP_NIGHT_LIGHT = 30;// 超级开关-小夜灯 normal
//        public static final int SP_ADJUSTER = 31;// 超级开关-调光器
//        public static final int SP_JACK = 32;// 超级开关-智能插座 normal
//        public static final int SP_CAMERA = 33;// 超级开关-摄像头 normal
        public static final int SP_SCENE = 34;// 超级开关-场景
        public static final int FAN_LIGHT = 38;// 风扇灯
        public static final int LIANGE_BA = 41;// 凉霸
        public static final int FAN_RF = 42;// 风扇

        public static final int AIRCONDITION_DIY = 100;//被遥控设备类型为空调

    }

    public static String getTypeName(int t, Context context) {
        String name = null;
        switch (t) {
            case RemoteControlType.SWITCH_RF:
                name = "射频遥控开关";
                break;
            case RemoteControlType.CURTAIN_RF:
                name = "射频遥控窗帘";
                break;
        }
        return name;
    }

    public static String dataByte = null;// 用来临时存储分析处理已学习到数据，用以验证采集的数据是否正确）
    public static int zip = 2;// 用来临时存储分析处理已学习到数据，用以验证采集的数据是否正确）
    public static String dataName = null;// 用来临时存储分析处理已学习到数据，用以验证采集的数据是否正确）

    public static final String DEVICE_NAME_COLLECTION = "blk,kukan,yaokan"; // blk：测试蓝牙;kukan;酷看蓝牙；yaokan,遥看蓝牙

    public static final String DEVICE_NAME = "device_name";//设备名称


    public static final class ArrDriver {
        public static final String DRIVER_TYPE = "driverType";

        public static final int IR = 0, YKKJONE = 1, YKKJTWO = 2, YKWIFI = 3, YKWIFI2 = 5, BLE = 4, YKCLOUD = 6;

        public static final int[] arrDriver = new int[]{IR, YKKJONE, YKKJTWO, YKWIFI, BLE, YKCLOUD};

    }

    public static String getDriverByInt(int d) {
        switch (d) {
            case ArrDriver.YKWIFI:
            case ArrDriver.YKWIFI2:
                return ConnType.WIFI;
            case ArrDriver.BLE:
                return ConnType.BTFOUR;
            case ArrDriver.YKCLOUD:
                return ConnType.YK_WIFI;
        }
        return "";
    }

    public static boolean isOriMatch(String type) {
        int t = Integer.parseInt(type);
        if (t == 7 || t == 12 || t == 16 || t == 17
                || t == 21 || t == 22 || t == 23 || t == 24) {
            return true;
        }
        return false;
    }

    /**
     * 遥控数据加密类型
     *
     * @author wave.li
     */
    public static final class AlgorithmType {

        public static final int NO = -1; // 无算法

        public static final int DEFAULT = 0; //默认 原始加密算法

        public static final int ZIP_ONE = 1;//压缩1算法

        public static final int ZIP_TWO = 2;//压缩2算法

    }

}
