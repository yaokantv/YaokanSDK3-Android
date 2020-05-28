package com.yaokantv.sdkdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yaokantv.yaokansdk.Contants;
import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.SoftApConfigResult;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.Logger;

/**
 * SoftAp配网
 */
public class SoftApConfigActivity extends BaseActivity implements View.OnClickListener, YaokanSDKListener {
    TextView tvSsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_ap_config);
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
                    Yaokan.instance().softApConfig(this, psw, Contants.YKK_MODEL_1013_RF);
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
            //开始配网
            case SoftApConfigStart:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage(getString(com.yaokantv.yaokansdk.R.string.smart_config));
                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                Yaokan.instance().stopSoftApConfig();
                            }
                        });
                        dialog.show();
                    }
                });
                break;
            //配网结束
            case SoftApConfigResult:
                if (dialog != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            DlgUtils.createDefDlg(activity, "", ykMessage.getMsg(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof SoftApConfigResult) {
                                        SoftApConfigResult result = (SoftApConfigResult) ykMessage.getData();
                                        Logger.e(result.toString());
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
