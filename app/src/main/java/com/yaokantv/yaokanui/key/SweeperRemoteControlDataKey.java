package com.yaokantv.yaokanui.key;

public enum SweeperRemoteControlDataKey {
	
     POWER("power"),UP("up"),DOWN("down"), LEFT("left"), RIGHT("right"),PAUSE("pause"),CHARGE("charge");
	 
	private String key;
	// 构造方法
	private SweeperRemoteControlDataKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
