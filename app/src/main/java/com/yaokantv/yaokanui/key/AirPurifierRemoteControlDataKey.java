package com.yaokantv.yaokanui.key;

/**
 * 电视机类型存储电视遥控器面板上键对应的Key
 */
public enum AirPurifierRemoteControlDataKey {
	
	     // 枚举所有键值
		 POWER("power"), ANION("anion"), ATOMIZE("atomize"),FANSPEED("fanspeed"), 
		 
	    HUMIDIFY("humidify"),LOCK("lock"),SLEEP("sleep"),STERILIZE("sterilize"),
	    
	    TIMER("timer");        
		
		private String key;
		// 构造方法
		private AirPurifierRemoteControlDataKey(String key) {
			this.key = key;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}


}
