package com.yaokantv.yaokanui.key;

public enum MpeDataKey { // 枚举所有键值
    HEADRISE("headrise"), HEADDROP("headdrop"),
    FEETRISE("feetrise"), FEETDROP("feetdrop"),
    BOTHRISE("bothrise"), BOTHDROP("bothdrop"),
    LEGHRISE("leghrise"), LEGDROP("legdrop"),
    LIGHTON("lighton"), LIGHTOFF("lightoff"),
    HEADRELAXON("headrelaxon"), HEADRELAXOFF("headrelaxoff"),
    FEETRELAXON("feetrelaxon"), FEETRELAXOFF("feetrelaxoff"),
    MODE1("mode1"), MODE2("mode2"), MODE3("mode3"),
    MIN10("10m"), MIN20("20m"), MIN30("30m"),
    FLAT("flat"), DEEPSLEEP("deepsleep"), SLEEPAID("sleepaid"), YOGA("yoga"),STOP("stop"),
    RELAX("relax"), ARDER("arder"), WAKEUP("wakeup"), OFFICE("work"),
    RELAX_SAVE("relaxsave"), ARDER_SAVE("ardersave"), DEEPSLEEP_SAVE("deepsleepsave"), WORK_SAVE("worksave"),SLEEPAID_SAVE("sleepaidsave"),FLAT_SAVE("flatsave"),

    ;

    private String key;

    // 构造方法
    private MpeDataKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
