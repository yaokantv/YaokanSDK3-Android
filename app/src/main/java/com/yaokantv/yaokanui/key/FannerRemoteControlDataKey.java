package com.yaokantv.yaokanui.key;

/**
 * 风扇类型存储遥控器面板上键对应的Key
 */
public enum FannerRemoteControlDataKey  {
	
	// 枚举所有键值
	 POWER("power"),    POWEROFF("poweroff"), FANSPEED("fanspeed"),
	 TIMER("timer"),    MODE("mode"),         OSCILLATION("oscillation"), 	
	 LAMP("lamp"),    ANION("anion"),       MUTE("mute"),
	 COOL("cool"),          	
	 FANSPEED_ADD("fanspeed+"),FANSPEED_SUB("fanspeed-");
	
	private String key;
	// 构造方法
	private FannerRemoteControlDataKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
