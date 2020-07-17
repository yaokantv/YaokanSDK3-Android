package com.yaokantv.yaokanui.key;

/**
 *有线机顶盒存储电视遥控器面板上键对应的Key
 */
public enum MultimediaRemoteControlDataKey {

	
	
	BACK("back"),DOWN("down"),GPS("gps") ,LEFT("left"),NEXT("next"),OK("ok"),UP("up"),RIGHT("right"),PREVIOUS("previous");
	
	

	
	private String key;
	// 构造方法
	private MultimediaRemoteControlDataKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
