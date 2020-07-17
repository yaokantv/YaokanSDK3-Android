package com.yaokantv.yaokanui.key;

/**
 * DVD类型存储遥控器面板上键对应的Key
 */
public enum DVDRemoteControlDataKey {	
	// 枚举所有键值	
	 UP("up"), DOWN("down"), OK("ok"),LEFT("left"), RIGHT("right"),POWER("power"),MUTE("mute"),
	REW("rew"), PLAY("play"),FF("ff"), PRE("previous"),STOP("stop"), NEXT("next"),MODE("mode"), PAUSE("pause"), 
	TITLE("title"), SWICTH("switch"),MENU("menu"),BACK("back"),TVPOWER("tvpower"),VOLUME_ADD("vol+"),VOLUME_SUB("vol-"),
	ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"),SIGNAL("signal"),BOOT("boot");
	
	private String key;
	// 构造方法
	private DVDRemoteControlDataKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
