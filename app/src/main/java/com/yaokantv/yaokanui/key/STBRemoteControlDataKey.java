package com.yaokantv.yaokanui.key;

/**
 * 有线机顶盒存储电视遥控器面板上键对应的Key
 */
public enum STBRemoteControlDataKey {

    ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"),

    OK("ok"), UP("up"), DOWN("down"), LEFT("left"), RIGHT("right"), MENU("menu"), MUTE("mute"), TVPOWER("tvpower"),

    POWER("power"), VOLUME_ADD("vol+"), VOLUME_SUB("vol-"), CHANNEL_ADD("ch+"), CHANNEL_SUB("ch-"),

    BACK("back"), BOOT("boot"), SIGNAL("signal"), F1("F1"), F2("F2"), F3("F3"), F4("F4"),

    GUIDE("guide"), EPG("epg"), SERVICE("service"), TRACK("track"), INFO("info"), VOD("vod"), PRE("pre"), NEXT("next"),

    FAVOURITE("favourite"), INTERACTIVE("interactive"), RED("red"), GREEN("green"), YELLOW("yellow"), BLUE("blue"), INPUTMETHOD("input_method"),

    LOOPPLAY("loopplay"), RECALL("recall"), LIVE("live"), SET("set"), LOCATION("location"), START("*"), SHARP("#"), REW("rew"), FF("ff"), PLAY("play"),

    //	导视: guide
//	节目指南: epg     (卫星机顶盒)
//	信息服务: service (卫星机顶盒)
//	声道: track
//	信息: info     (屏显, 卫星机顶盒-节目信息)
//	院线: vod      (点播:cinemas) 
//	上页: previous
//	下页: next
//	喜爱频道(收藏): favourite
//  互动: interactive
//  红: red, 绿: green, 黄:yellow, 蓝:blue
//  输入法: input_method
//  轮播: loopplay
//  回看: recall
//  直播: live
//  设置: set
//  定位: location
    EXIT("exit"), STAR("*"), POUND("#");//退出


    private String key;

    // 构造方法
    private STBRemoteControlDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
