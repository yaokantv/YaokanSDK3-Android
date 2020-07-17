package com.yaokantv.yaokanui.key;

/**
 * 电视机类型存储电视遥控器面板上键对应的Key
 */
public enum TVRemoteControlDataKeyCH {
	
	// 枚举所有键值
	ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), 
	
	NINE("9"), MENU("菜单"), MUTE("静音"), POWER("电源"), OK("确定"),
	
	VOLUME_ADD("音量+"),VOLUME_SUB("音量-") ,CHANNEL_ADD("频道+"),CHANNEL_SUB("频道-"),
	
	DIGIT("-/--"), SIGNAL("信号源"), BACK("返回"),
	
	REW("快退"), PLAY("播放"),FF("快进"), PRE("上一曲"),STOP("停止"), NEXT("下一曲"), PAUSE("暂停"), 
	
	
    CHILDLOCK("童锁"), BROWSE("浏览"),TVSYS("SYS-制式"), TVSWAP("SWAP-交换"),TIMER("定时/睡眠"), SOUND("音质/立体声"),
	
  NICAM("丽音"), SPIN("旋转"),AUDIOMODEL("声音模式"), SLEEP("睡眠"),INFO("屏显"), ZOOM("缩放"),
	
	IMAGEMODEL("图像模式"), FAVOURITE("喜爱频道"),SCREENSHOT("快照"), DEL("删除"),BOOT("主页"),SHARP("#"),

	

	
	F1("F1"),F2("F2"),F3("F3"),F4("F4"),EXIT("退出") ,UP("上"), DOWN("下"), LEFT("左"), RIGHT("右");
		private String key;

	// 构造方法
		private TVRemoteControlDataKeyCH(String key) {
			this.key = key;
		}
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
