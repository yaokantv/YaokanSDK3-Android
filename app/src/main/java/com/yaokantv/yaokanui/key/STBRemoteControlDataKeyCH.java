package com.yaokantv.yaokanui.key;

/**
 * 有线机顶盒存储电视遥控器面板上键对应的Key
 */
public enum STBRemoteControlDataKeyCH {
	
	ZERO("频道数字键0"), ONE("频道数字键1"), TWO("频道数字键2"), THREE("频道数字键3"), FOUR("频道数字键4"), FIVE("频道数字键5"), SIX("频道数字键6"),

	SEVEN("频道数字键7"), EIGHT("频道数字键8"), NINE("频道数字键9"), 
	
	OK("确定"), UP("上"), DOWN("下"), LEFT("左"), RIGHT("右"),MENU("菜单"), MUTE("静音"), TVPOWER("电视机电源"), 
	
	POWER("电源"),VOLUME_ADD("音量+"), VOLUME_SUB("音量-") ,CHANNEL_ADD("频道+"),CHANNEL_SUB("频道-"),
	
	BACK("返回"),	BOOT("主页"), SIGNAL("输入源"),
	
	 F1("F1"),F2("F2"),F3("F3"),F4("F4"),EXIT("退出"),
	
    GUIDE("导视"),EPG("节目指南"),SERVICE("信息服务"),TRACK("声道"),INFO("信息"),VOD("院线"),PRE("上页"),NEXT("下页"),
	
	FAVOURITE("喜爱频道"),INTERACTIVE("互动"),RED("红"),GREEN("绿"),YELLOW("黄"),BLUE("蓝"),INPUTMETHOD("输入法"),
	
	LOOPPLAY(" 轮播"),RECALL("回看"),LIVE("直播"),SET("设置"),LOCATION("定位"),START("*"),SHARP("#"),REW("快退"),PLAY("播放"),FF("快进"), STAR("*"), POUND("#");
	
//	导视: guide 
//	节目指南: epg     (卫星机顶盒)
//	信息服务: service (卫星机顶盒)
//	声道: track
//	信息: info     (屏显, 卫星机顶盒-节目信息)
//	院线: vod      (点播:cinemas) 
//	上页: previous
//	下页: next
//	喜爱频道(收藏): favourite
//  互动: interactive
//  红: red, 绿: green, 黄:yellow, 蓝:blue
//  输入法: input_method
//  轮播: loopplay
//  回看: recall
//  直播: live
//  设置: set
//  定位: location
  
	private String key;
	// 构造方法
	private STBRemoteControlDataKeyCH(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
