package com.yaokantv.yaokanui.key;

/**
 * 存储遥控器面板上键对应的Key
 */
public enum AirConditionRemoteControlDataKeyCH {
	
	// 枚举所有键值
	AIRCON("空调电源开关"), F1("F1"),F2("F2"),F3("F3"),F4("F4"),T_UP("温度加"),T_DOWN("温度减"),SPEED("风量"),MODE("模式"),SWING_DIRECTION("风向"),SWING_SWTICH("扫风"),ON("电源开"),OFF("电源关");
	
	private String key;
	// 构造方法
	private AirConditionRemoteControlDataKeyCH(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
