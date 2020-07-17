package com.yaokantv.yaokanui.key;

/**
 *有线机顶盒存储电视遥控器面板上键对应的Key
 */
public enum YKLightRemoteControlDataKey {

	 POWER("power"), POWEROFF("poweroff"),LIGHTNESS_ADD("lightness+"),LIGHTNESS_SUB("lightness-"),
	 CT_ADD("ct+"),CT_SUB("ct-"),TIMER("timer"),LIGHTNESS("lightness"),LIGHTNESS_SHOW("lightslow"),
	 SN1("sn1"),SN1_S("sn1_s"),SN2("sn2"),SN2_S("sn2_s"),SN3_S("sn3_s"),SN3("sn3"),SN4("sn4"),SN4_S("sn4_s"),WPOWER("wpower"),WPOWEROFF("wpoweroff");
	
	private String key;
	// 构造方法
	private YKLightRemoteControlDataKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
