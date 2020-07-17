package com.yaokantv.yaokanui.key;

public enum BoxRemoteControlDataKeyCH {
	
	 TVPOWER("电视机电源开关"), POWER("电源"),MENU("菜单"),SIGNAL("信号源选择"),
	
	 VOLUME_ADD("音量+"), VOLUME_SUB("音量-") ,BACK("返回"),	BOOT("导视"),
	
	 OK("确定"), UP("上"), DOWN("下"), LEFT("左"), RIGHT("右"),EXIT("exit") , MUTE("静音");//退出
	 
	 
	private String key;
	// 构造方法
	private BoxRemoteControlDataKeyCH(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
