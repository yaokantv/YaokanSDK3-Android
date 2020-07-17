package com.yaokantv.yaokanui.key;

/**
 * 风扇类型存储遥控器面板上键对应的Key
 */
public enum CameraRemoteControlDataKeyCH {
	
	// 枚举所有键值
	 POWER("电源");
	
	private String key;
	// 构造方法
	private CameraRemoteControlDataKeyCH(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
