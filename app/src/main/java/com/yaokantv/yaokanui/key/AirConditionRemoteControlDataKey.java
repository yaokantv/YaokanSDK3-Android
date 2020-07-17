package com.yaokantv.yaokanui.key;

/**
 * 存储遥控器面板上键对应的Key
 */
public enum AirConditionRemoteControlDataKey {
	
	// 枚举所有键值,F1,F2,F3,F4,温度加，温度减，风量，模式，风向，扫风
	AIRCON("aircon"), F1("F1"),F2("F2"),F3("F3"),F4("F4"),T_UP("t_up"),T_DOWN("t_down"),SPEED("speed"),MODE("mode"),SWING_DIRECTION("swing_direction"),SWING_SWTICH("swing_switch"),ON("on"),OFF("off");
	
	private String key;
	// 构造方法
	private AirConditionRemoteControlDataKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
