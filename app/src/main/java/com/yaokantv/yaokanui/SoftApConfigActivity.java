package com.yaokantv.yaokanui;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.Contants;
import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.ConfigType;
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
    TextView tvSsid, tvTips;
    String ssid;
    String psw;
    EditText editText;
    RadioGroup rgConfigType, rgDeviceType;
    String deviceType = Contants.YKK_MODEL_1011;
    ConfigType configType = ConfigType.SoftAp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_ap_config);
        setMTitle(R.string.t_smart_config, TITLE_LOCATION_CENTER);
    }

    @Override
    protected void initView() {
        tvSsid = findViewById(R.id.tv_ssid);
        tvTips = findViewById(R.id.tv_cfg_tips);
        editText = findViewById(R.id.et_psw);
        rgConfigType = findViewById(R.id.rg_config);
        rgDeviceType = findViewById(R.id.rg_device);
        ssid = Yaokan.instance().getSsid(this);
        if (!TextUtils.isEmpty(ssid)) {
            tvSsid.setText(ssid);
            if (Hawk.get(ssid) != null) {
                editText.setText((String) Hawk.get(ssid));
            }
        }
        rgDeviceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cb_1101:
                        deviceType = Contants.YKK_MODEL_1011;
                        break;
                    case R.id.cb_1103:
                        deviceType = Contants.YKK_MODEL_1013;
                        break;
                    case R.id.cb_ds16:
                        deviceType = Contants.YKK_MODEL_DS16A;
                        break;
                }
            }
        });
        rgConfigType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cb_soft:
                        configType = ConfigType.SoftAp;
                        findViewById(R.id.ll_psw).setVisibility(View.VISIBLE);
                        break;
                    case R.id.cb_smart:
                        configType = ConfigType.SmartConfig;
                        findViewById(R.id.ll_psw).setVisibility(View.VISIBLE);
                        break;
                    case R.id.cb_hot_point:
                        configType = ConfigType.HotPoint;
                        findViewById(R.id.ll_psw).setVisibility(View.GONE);
                        break;
                    case R.id.cb_param:
                        configType = ConfigType.PARAM;
                        findViewById(R.id.ll_psw).setVisibility(View.GONE);
                        break;
                }
                setTips();
            }
        });
        setTips();
    }

    private void setTips() {
        String txt = "";
        switch (configType) {
            case SoftAp:
                txt = getString(R.string.net_config_ap_tips);
                break;
            case SmartConfig:
                txt = getString(R.string.net_config_smart_tips);
                break;
            case HotPoint:
                txt = getString(R.string.net_config_hot_point_tips);
                break;
            case PARAM:
                break;
        }
        tvTips.setText(txt);
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
            case R.id.btn_smart_config:
                psw = editText.getText().toString();
                if (!TextUtils.isEmpty(ssid) && !TextUtils.isEmpty(psw)) {
                    Hawk.put(ssid, psw);//用于保存数据
                }
                switch (configType) {
                    case SoftAp:
                        Yaokan.instance().softApConfigSwitch(SoftApConfigActivity.this, ssid, psw, deviceType);
                        break;
                    case SmartConfig:
                        Yaokan.instance().smartConfig(this, psw);
                        break;
                    case HotPoint:
                        Yaokan.instance().hotPointConfig();
                        break;
                    case PARAM:
                        Yaokan.instance().paramConfig("","");
                        break;
                }
                break;
        }
    }


    @Override
    public void onReceiveMsg(MsgType msgType, final YkMessage ykMessage) {
        if (isFinishing()) {
            return;
        }

        switch (msgType) {
            //开始配网
            case SendToDevice:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage("向设备发送信息成功");
                    }
                });
                break;
            case GetConfigParam:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage("参数获取成功，请用以下参数进行配网：\n" + ykMessage.getData());
                    }
                });
                break;
            case SwitchWifi:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage("如果WI-FI未自动切换\n至:" + deviceType + "，请手动连接。");
                    }
                });
                break;
            case HotPointStart:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage(getString(com.yaokantv.yaokansdk.R.string.smart_config));
                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                Yaokan.instance().stopHotPointConfig();
                            }
                        });
                        dialog.show();
                    }
                });
                break;
            case ParamConfigStart:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage(getString(com.yaokantv.yaokansdk.R.string.smart_config));
                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                Yaokan.instance().stopParamConfig();
                            }
                        });
                        dialog.show();
                    }
                });
                break;
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
            case HotPointConfigResult:
            case ParamConfigResult:
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
                                            Yaokan.instance().deleteRemoteByMac(result.getMac());//清除旧的本地遥控器，可以不清除
                                            //result = true 配网成功
                                            if (msgType == MsgType.SoftApConfigResult) {
                                                Hawk.put(ssid, psw);
                                            }
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
                                            Yaokan.instance().deleteRemoteByMac(result.getMac());//清除旧的本地遥控器，可以不清除
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

    @Override
    protected void reload() {

    }
}
