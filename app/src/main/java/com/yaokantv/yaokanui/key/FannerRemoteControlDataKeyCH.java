package com.yaokantv.yaokanui.key;

/**
 * 风扇类型存储遥控器面板上键对应的Key
 */
public enum FannerRemoteControlDataKeyCH {
	
	// 枚举所有键值
	POWER("电源开"),   POWEROFF("电源关"),   FANSPEED("风速"), 
	
	MODE("风类"),      TIMER("定时"),        OSCILLATION("摇头"),
	
	LAMP("灯光"), ANION("负离子"), MUTE("静音"),
	
	COOL("制冷"),FANSPEED_ADD("风速加"),FANSPEED_SUB("风速减");	
	
	private String key;
	// 构造方法
	private FannerRemoteControlDataKeyCH(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
