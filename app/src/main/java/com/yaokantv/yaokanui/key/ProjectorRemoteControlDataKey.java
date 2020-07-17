package com.yaokantv.yaokanui.key;

/**
 * 存储遥控器面板上键对应的Key
 */
public enum ProjectorRemoteControlDataKey {
	
	// 枚举所有键值
	 POWER("power"),  POWEROFF("poweroff"), SIGNAL("signal"), FOCUS_ADD("pic+"), FOCUS_SUB("pic-"),
	 MENU("menu"),OK("ok"), UP("up"), DOWN("down"), LEFT("left"), RIGHT("right"),
	EXIT("exit"),VOL_ADD("vol+"),VOL_SUB("vol-"),MUTE("mute"),PAUSE("pause"),LIGHTNESS("lightness"),
	PRE("previous"),NEXT("next"),KEYSTONE_ADD("keystone+"),KEYSTONE_SUB("keystone-"),BOOT("boot"),BACK("back");
	
	private String key;
	// 构造方法
	private ProjectorRemoteControlDataKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
