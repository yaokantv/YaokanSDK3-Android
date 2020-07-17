package com.yaokantv.yaokanui.key;

/**
 * 存储遥控器面板上键对应的Key
 */
public enum ProjectorRemoteControlDataKeyCH {

    //枚举所有键值
    POWER("电源开"), POWEROFF("电源关"), SIGNAL("信号源"), FOCUS_ADD("变焦+"), FOCUS_SUB("变焦-"),
    MENU("菜单"), OK("确认"), UP("上"), DOWN("下"), LEFT("左"), RIGHT("右"),
    EXIT("退出"), VOL_ADD("音量加"), VOL_SUB("音量减"), MUTE("静音"), PAUSE("暂停"), LIGHTNESS("亮度"),
    PRE("上页"), NEXT("下页"), KEYSTONE_ADD("增加梯度调节+"), KEYSTONE_SUB(" 减少梯度调节-"), BOOT("主页"),BACK("返回");


    private String key;

    // 构造方法
    private ProjectorRemoteControlDataKeyCH(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
