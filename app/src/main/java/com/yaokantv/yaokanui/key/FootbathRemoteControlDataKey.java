package com.yaokantv.yaokanui.key;

/**
 *有线机顶盒存储电视遥控器面板上键对应的Key
 */
public enum FootbathRemoteControlDataKey {

	
	
	POWER("power"),HEAT("heat"),SHOCK("shock") ,TEMP("temp"),TIME("time"),WAVE("wave");
	
	

	
	private String key;
	// 构造方法
	private FootbathRemoteControlDataKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
