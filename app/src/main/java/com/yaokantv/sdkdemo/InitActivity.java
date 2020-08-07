package com.yaokantv.sdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokanui.DeviceListActivity;

public class InitActivity extends BaseActivity implements YaokanSDKListener, View.OnClickListener {
    String appId = "";
    String appSecret = "";
    boolean isUi = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        showDlg();
        Yaokan.instance().init(getApplication(), appId, appSecret).addSdkListener(this);
        initToolbar(R.string.t_init);
        if (!TextUtils.isEmpty(appId)) {
            ((EditText) findViewById(R.id.et_appid)).setText(appId);
            ((EditText) findViewById(R.id.et_app_secret)).setText(appSecret);
        }
//        String s = "[{\"name\":\"空调\",\"rid\":\"2016093013210706\",\"place\":\"阳台\",\"rmodel\":\"1(V3)\",\"be_rmodel\":\"1(V3)\",\"be_rc_type\":7,\"bid\":1236,\"mac\":\"A4CF12A9334E\",\"rf\":\"0\",\"rf_body\":\"\",\"rc_command_type\":1,\"study_id\":\"0\",\"rc_command\":{\"mode\":[\"auto\",\"cold\",\"dry\",\"hot\",\"wind\"],\"attributes\":{\"verticalIndependent\":0,\"horizontalIndependent\":0,\"auto\":{\"speed\":[0,1,2,3],\"swing\":[\"horizontalOff\",\"horizontalOn\",\"verticalOff\",\"verticalOn\"],\"temperature\":[]},\"dry\":{\"speed\":[1],\"swing\":[\"horizontalOff\",\"horizontalOn\",\"verticalOff\",\"verticalOn\"],\"temperature\":[]},\"hot\":{\"speed\":[0,1,2,3],\"swing\":[\"horizontalOff\",\"horizontalOn\",\"verticalOff\",\"verticalOn\"],\"temperature\":[16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]},\"cold\":{\"speed\":[0,1,2,3],\"swing\":[\"horizontalOff\",\"horizontalOn\",\"verticalOff\",\"verticalOn\"],\"temperature\":[16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]},\"wind\":{\"speed\":[1,2,3],\"swing\":[\"horizontalOff\",\"horizontalOn\",\"verticalOff\",\"verticalOn\"],\"temperature\":[16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]}}}}]";
//        String d = "[{\"did\":\"BE2D38BD3F1BD7E5\",\"mac\":\"A4CF12A9334E\",\"name\":\"YKK-1013-RF\",\"rf\":\"0\"}]";
//        Yaokan.instance().inputRcString(s);
//        Yaokan.instance().inputYkDevicesToDB(d);
    }

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (msgType) {
                    case Init:
                        dismiss();
                        if (ykMessage != null && ykMessage.getCode() == 0) {
                            startActivity(new Intent(InitActivity.this, (isUi ? DeviceListActivity.class : MainActivity.class)));
                            finish();
                            //初始化成功
                        } else {
                            //初始化失败
                            DlgUtils.createDefDlg(InitActivity.this, ykMessage.getMsg());
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_init:
                if (!Yaokan.instance().isInit()) {
                    showDlg();
                    Yaokan.instance().init(getApplication(), appId, appSecret);
                }
                break;
        }
    }
}
