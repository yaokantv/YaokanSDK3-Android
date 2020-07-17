package com.yaokantv.yaokanui.key;

/**
 * DVD类型存储遥控器面板上键对应的Key
 */
public enum DVDRemoteControlDataKeyCH {	
	// 枚举所有键值	
	 UP("上"), DOWN("下"), OK("确定"),LEFT("左"), RIGHT("右"),POWER("电源"),MUTE("静音"),
	REW("快退"), PLAY("播放"),FF("快进"), PRE("上一曲"),STOP("停止"), NEXT("下一曲"),MODE("模式, 制式"), PAUSE("暂停"), 
	TITLE("电视机信号输入源选择"), SWICTH("开关仓"),MENU("菜单"),BACK("返回"),TVPOWER("电视电源"),VOLUME_ADD("音量+"), VOLUME_SUB("音量-"),
	ZERO("频道数字键0"), ONE("频道数字键1"), TWO("频道数字键2"), THREE("频道数字键3"), FOUR("频道数字键4"), FIVE("频道数字键5"), SIX("频道数字键6"),
	SEVEN("频道数字键7"), EIGHT("频道数字键8"), NINE("频道数字键9"),SIGNAL("信号源选择"),BOOT("导视");
	
	private String key;
	// 构造方法
	private DVDRemoteControlDataKeyCH(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
