package com.yaokantv.yaokanui.key;

/**
 * 电视机类型存储电视遥控器面板上键对应的Key
 */
public enum TVRemoteControlDataKey {

	// 枚举所有键值
	ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"),
	
    OK("ok"),UP("up"),DOWN("down"), LEFT("left"), RIGHT("right"),
	
	VOLUME_ADD("vol+"),VOLUME_SUB("vol-") ,CHANNEL_ADD("ch+"),CHANNEL_SUB("ch-"),
	
	
	MENU("menu"), MUTE("mute"), POWER("power"),DIGIT("-/--"), SIGNAL("signal"), BACK("back"),

	
	
	
	REW("rew"), PLAY("play"),FF("ff"), PRE("pre"),STOP("yk_ctrl_stop"), NEXT("next"),PAUSE("pause"),
	
	CHILDLOCK("child_lock"), BROWSE("browse"),TVSYS("tv_sys"), TVSWAP("tv_swap"),TIMER("timer"), SOUND("sound"),
	
	NICAM("nicam"), SPIN("spin"),AUDIOMODEL("audio_model"), SLEEP("sleep"),INFO("info"), ZOOM("zoom"),
	
	IMAGEMODEL("image_model"), FAVOURITE("favourite"),SCREENSHOT("screenshot"), DEL("del"),BOOT("boot"),SHARP("#"),

	EXIT("exit") ;//退出;
		
	private String key;
	// 构造方法
	private TVRemoteControlDataKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
