package com.yaokantv.sdkdemo;

import android.app.Application;

import com.yaokantv.yaokansdk.crash.YKSenderfactory;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.Operators;

import org.acra.ReportField;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(
        reportSenderFactoryClasses = {YKSenderfactory.class},
        customReportContent = {ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        resToastText = R.string.loading
)
public class App extends Application {
    public static String curMac = "";//设备MAC
    public static String curDid = "";//设备DID
    public static String curRf = "0";//设备是否支持射频
    public static String curTName = "";//设备类型
    public static String curBName = "";//品牌名称
    public static int curTid = 0;//设备类型ID
    public static int curBid = 0;//品牌ID
    public static int curGid = 0;//组ID
    public static Operators operators = null;//运营商对象

    @Override
    public void onCreate() {
        super.onCreate();
        Yaokan.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Yaokan.instance().onTerminate(this);
    }
}
