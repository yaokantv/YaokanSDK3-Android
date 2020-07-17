package com.yaokantv.yaokanui.key;

/**
 * 风扇类型存储遥控器面板上键对应的Key
 */
public enum CameraRemoteControlDataKey {
	
	// 枚举所有键值
	 POWER("power");
	
	private String key;
	// 构造方法
	private CameraRemoteControlDataKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
