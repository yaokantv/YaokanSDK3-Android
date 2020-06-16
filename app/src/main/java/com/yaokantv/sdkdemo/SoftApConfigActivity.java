package com.yaokantv.sdkdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.orhanobut.hawk.Hawk;
import com.yaokantv.yaokansdk.Contants;
import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.SmartConfigResult;
import com.yaokantv.yaokansdk.model.SoftApConfigResult;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.Logger;

/**
 * SoftAp配网
 */
public class SoftApConfigActivity extends BaseActivity implements View.OnClickListener, YaokanSDKListener {
    TextView tvSsid, tvType;
    String ssid;
    String psw;
    EditText editText;
    boolean isAp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_ap_config);
        initToolbar(R.string.t_smart_config);
        Yaokan.instance().addSdkListener(this);
        tvSsid = findViewById(R.id.tv_ssid);
        tvType = findViewById(R.id.tv_type);
        editText = findViewById(R.id.et_psw);
        ssid = Yaokan.instance().getSsid(this);
        if (!TextUtils.isEmpty(ssid)) {
            tvSsid.setText(ssid);
            if (Hawk.get(ssid) != null) {
                editText.setText((String) Hawk.get(ssid));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Yaokan.instance().onConfigResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Yaokan.instance().onConfigPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Yaokan.instance().removeSdkListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_switch:
                isAp = !isAp;
                tvType.setText(isAp ? "SoftAp配网" : "SmartConfig配网");
                break;
            case R.id.btn_smart_config:
                psw = editText.getText().toString();
                Hawk.put(ssid, psw);//用于保存数据
                if (isAp) {
                    final String[] items = {"小苹果", "大苹果", "空调伴侣"};
                    AlertDialog.Builder listDialog =
                            new AlertDialog.Builder(this);
                    listDialog.setTitle("请选择需要配网的设备");
                    listDialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String mode = "";
                            switch (which) {
                                case 0:
                                    mode = Contants.YKK_MODEL_1011;
                                    break;
                                case 1:
                                    mode = Contants.YKK_MODEL_1013;
                                    break;
                                case 2:
                                    mode = Contants.YKK_MODEL_DS16A;
                                    break;
                            }
                            Yaokan.instance().softApConfig(SoftApConfigActivity.this, ssid, psw, mode);
                        }
                    });
                    listDialog.show();
                } else {
                    Yaokan.instance().smartConfig(this, psw);
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
            case StartSmartConfig:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage(getString(com.yaokantv.yaokansdk.R.string.smart_config));
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
            case WifiNotFind:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        DlgUtils.createDefDlg(activity, "扫描不到设备热点");
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
                                            Hawk.put(ssid, psw);
                                            finish();
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
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
