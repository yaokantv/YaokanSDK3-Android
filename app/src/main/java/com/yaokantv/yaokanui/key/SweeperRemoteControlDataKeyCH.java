package com.yaokantv.yaokanui.key;

public enum SweeperRemoteControlDataKeyCH {
	
	POWER("电源"), UP("上"), DOWN("下"), LEFT("左"), RIGHT("右"), PAUSE("暂停"),CHARGE("返回充电");
	
	
	 
	private String key;
	// 构造方法
	private SweeperRemoteControlDataKeyCH(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
