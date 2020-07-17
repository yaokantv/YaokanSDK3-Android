package com.yaokantv.yaokanui.key;

/**
 * 电视机类型存储电视遥控器面板上键对应的Key
 */
public enum AirPurifierRemoteControlDataKeyCH {
	
	     // 枚举所有键值
		 POWER("电源"), ANION("负离子"), ATOMIZE("雾化"),FANSPEED("风速"), 
		 
	    HUMIDIFY("加湿"),LOCK("锁"),SLEEP("睡眠"),STERILIZE("杀菌"),
	    
	    TIMER("定时");        
		
		private String key;
		// 构造方法
		private AirPurifierRemoteControlDataKeyCH(String key) {
			this.key = key;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}


}
