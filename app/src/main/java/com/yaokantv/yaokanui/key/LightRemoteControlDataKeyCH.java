package com.yaokantv.yaokanui.key;

import java.util.ArrayList;
import java.util.List;

/**
 *有线机顶盒存储电视遥控器面板上键对应的Key
 */
public enum LightRemoteControlDataKeyCH {

	POWER("开"), POWEROFF("关"), H4("4h"), H8("8h"), MODE("mode"), MULTICOLOR("multicolor"), 
	
	C822216("c_822216"), C126F32("c_126f32"),C212F73("c_212f73"), C7A4429("c_7a4429"), C288E21("c_288e21"), C170C4C("c_170c4c"), CC9933E("c_c9933e"),
	C035098("c_035098"),C732741("c_732741"),CB7A2A8("c_b7a2a8"),C03436C("c_03436c") ,C3C1F39("c_3c1f39");
	
	
	private String key;
	// 构造方法
	private LightRemoteControlDataKeyCH(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public static List<LightRemoteControlDataKeyCH> getKeys() {
		List<LightRemoteControlDataKeyCH> keys = new ArrayList<>();
		keys.add(LightRemoteControlDataKeyCH.POWER);
		keys.add(LightRemoteControlDataKeyCH.POWEROFF);
		return keys;
	}
}
