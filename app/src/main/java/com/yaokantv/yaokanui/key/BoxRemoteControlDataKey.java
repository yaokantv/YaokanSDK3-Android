package com.yaokantv.yaokanui.key;

public enum BoxRemoteControlDataKey {
	
     TVPOWER("tvpower"),POWER("power"),MENU("menu"),SIGNAL("signal"),
	
     VOLUME_ADD("vol+"),VOLUME_SUB("vol-") ,BACK("back"),BOOT("boot"),
	
	 OK("ok"), UP("up"),DOWN("down"), LEFT("left"), RIGHT("right"),EXIT("exit"),MUTE("mute") ;//退出
	 
	 
	private String key;
	// 构造方法
	private BoxRemoteControlDataKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
