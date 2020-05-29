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

public class InitActivity extends BaseActivity implements YaokanSDKListener, View.OnClickListener {
    String appId = "";
    String appSecret = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        showDlg();
        initToolbar(R.string.t_init);
        if (!TextUtils.isEmpty(appId)) {
            Yaokan.instance().init(getApplication(), appId, appSecret).addSdkListener(this);
            ((EditText) findViewById(R.id.et_appid)).setText(appId);
            ((EditText) findViewById(R.id.et_app_secret)).setText(appSecret);
        }
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
                            startActivity(new Intent(InitActivity.this, MainActivity.class));
                            finish();
                            //初始化成功
                        } else {
                            //初始化失败
                            DlgUtils.createDefDlg(InitActivity.this,ykMessage.getMsg());
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
