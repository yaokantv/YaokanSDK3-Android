package com.yaokantv.sdkdemo;

import android.app.Application;

import com.yaokantv.yaokansdk.crash.YKSenderfactory;
import com.yaokantv.yaokansdk.manager.Yaokan;

import org.acra.ReportField;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(
        reportSenderFactoryClasses ={YKSenderfactory.class},
        customReportContent = { ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT },
        resToastText = R.string.loading
)
public class App extends Application {
    public static String curMac = "";
    public static String curDid = "";
    public static String curTName = "";
    public static String curBName = "";
    public static int curTid = 0;
    public static int curBid = 0;
    public static int curGid = 0;

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
