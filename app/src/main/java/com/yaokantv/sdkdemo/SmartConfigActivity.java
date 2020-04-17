package com.yaokantv.sdkdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.SmartConfigResult;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.DlgUtils;

public class SmartConfigActivity extends BaseActivity implements View.OnClickListener, YaokanSDKListener {
    TextView tvSsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_config);
        initToolbar(R.string.t_smart_config);
        Yaokan.instance().addSdkListener(this);
        tvSsid = findViewById(R.id.tv_ssid);
        String ssid = Yaokan.instance().getSsid(this);
        if (!TextUtils.isEmpty(ssid)) {
            tvSsid.setText(ssid);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Yaokan.instance().removeSdkListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_smart_config:
                EditText editText = findViewById(R.id.et_psw);
                String psw = editText.getText().toString();
                if (!TextUtils.isEmpty(psw)) {
                    Yaokan.instance().smartConfig(this, psw, this);
                }
                break;
        }
    }

    @Override
    public void onReceiveMsg(MsgType msgType, final YkMessage ykMessage) {
        if (isFinishing()) {
            return;
        }
        if (ykMessage != null) {
            log(msgType.name() + ": " + ykMessage.getMsg());
        }
        switch (msgType) {
            case StartSmartConfig:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage(getString( R.string.smart_config));
                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                Yaokan.instance().stopSmartConfig();
                            }
                        });
                        dialog.show();
                    }
                });
                break;
            case SmartConfigResult:
                if (dialog != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            DlgUtils.createDefDlg(activity, "", ykMessage.getMsg(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof SmartConfigResult) {
                                        SmartConfigResult result = (SmartConfigResult) ykMessage.getData();
                                        if (result.isResult()) {
                                            //result = true 配网成功
                                            finish();
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
                break;
        }
    }
}
