package com.yaokantv.sdkdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.AirPowerResult;
import com.yaokantv.yaokansdk.model.Brand;
import com.yaokantv.yaokansdk.model.BrandResult;
import com.yaokantv.yaokansdk.model.CheckVersionResult;
import com.yaokantv.yaokansdk.model.DeviceType;
import com.yaokantv.yaokansdk.model.DeviceTypeResult;
import com.yaokantv.yaokansdk.model.HardInfo;
import com.yaokantv.yaokansdk.model.ProgressResult;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.Logger;
import com.yaokantv.yaokanui.widget.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class BrandActivity extends BaseActivity implements View.OnClickListener, YaokanSDKListener {
    DeviceTypeResult mTypeResult;
    BrandResult mBrandResult;
    Spinner spType, spBrands;
    List<String> nameType = new ArrayList<>();
    List<String> nameBrands = new ArrayList<>();
    ArrayAdapter<String> typeAdapter, brandAdapter;
    DeviceType currDeviceType = null; // 当前设备类型
    Brand currBrand = null; // 当前品牌
    boolean isFirst = true;
    TextView tvVoice;
    RangeSeekBar<Integer> seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
        initView();
        if (getIntent() != null && !TextUtils.isEmpty(getIntent().getStringExtra("from"))) {
            findViewById(R.id.ll_1).setVisibility(View.GONE);
        }
        Yaokan.instance().addSdkListener(this);
        initToolbar(App.curMac);
        //检测更新
//        Yaokan.instance().checkDeviceVersion(App.curDid);
        Yaokan.instance().deviceInfo(App.curDid);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Yaokan.instance().removeSdkListener(this);
    }

    private void initView() {
        seekBar = findViewById(R.id.sb_vol);
        tvVoice = findViewById(R.id.tv_voice);
        spType = findViewById(R.id.spType);
        spBrands = findViewById(R.id.spBrand);
        typeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nameType);
        brandAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nameBrands);
        typeAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(typeAdapter);
        spBrands.setAdapter(brandAdapter);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                for (DeviceType deviceType : mTypeResult.getResult()) {
                    if (nameType.get(pos).equals(deviceType.getName())) {
                        currDeviceType = deviceType;
                        App.curTName = currDeviceType.getName();
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spBrands.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                currBrand = mBrandResult.getResult()[pos];
                App.curBName = currBrand.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                int vol = maxValue;
                dialog.show();
                String voice = "0";
                if (vol < 10) {
                    voice = voice + vol;
                } else {
                    voice = String.valueOf(vol);
                }
                tvVoice.setText("" + vol);
                Yaokan.instance().setVoice(App.curDid, voice);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_types:
                Yaokan.instance().getDeviceType();
                showDlg();
                break;
            case R.id.btn_brands:
                if (currDeviceType != null) {
                    if (currDeviceType.getTid() == 1) {
                        startActivity(new Intent(this, SelectProviderActivity.class));
                        return;
                    }
                    showDlg();
                    Yaokan.instance().getBrandsByType(currDeviceType.getTid());
                }
                break;
            case R.id.btn_match:
                if (currDeviceType != null && currBrand != null) {
                    App.curTid = currDeviceType.getTid();
                    App.curBid = currBrand.getBid();
                    if (App.curTid == 7) {
                        startActivity(new Intent(this, SecondMatchActivity.class));
                    } else if (currDeviceType.getRf() == 1) {
                        startActivity(new Intent(this, RfMatchActivity.class));
                    } else {
                        startActivity(new Intent(this, MatchActivity.class));
                    }
                }
                break;
            case R.id.btn_light_on:
                Yaokan.instance().lightOn(App.curDid);
                break;
            case R.id.btn_light_off:
                Yaokan.instance().lightOff(App.curDid);
                break;
            case R.id.btn_update_device:
                Yaokan.instance().updateDevice(App.curDid);
                break;
            case R.id.btn_voice_update_device:
                Yaokan.instance().updateVoice(App.curDid);
                break;
            case R.id.btn_reset_apple:
                Yaokan.instance().apReset(App.curMac, App.curDid);
                break;
            case R.id.btn_check_version:
                Yaokan.instance().checkDeviceVersion(App.curDid);
                break;
            case R.id.btn_device_info:
                Yaokan.instance().deviceInfo(App.curDid);
                break;
            case R.id.btn_ctrl_list:
                startActivity(new Intent(activity, AppleCtrlListActivity.class));
                break;
            case R.id.btn_power_query:
                long endTime = System.currentTimeMillis() / 1000;
                long startTime = endTime - 24 * 60 * 60;
                Yaokan.instance().powerQuery(App.curDid, "day", startTime, endTime);
                break;
        }
    }

    final int OTHER_FAIL = -1;
    final int HAND_TYPES_SUC = 0;
    final int HAND_BRANDS_SUC = 1;

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismiss();
                switch (msgType) {
                    case setVoiceResult:
                        dismiss();
                        break;
                    case Types:
                        if (ykMessage != null && ykMessage.getCode() == 0) {
                            DeviceTypeResult typeResult = (DeviceTypeResult) ykMessage.getData();
                            if (typeResult != null) {
                                mTypeResult = typeResult;
                                mHandler.sendEmptyMessage(HAND_TYPES_SUC);
                            }
                        } else if (ykMessage != null) {
                            Log.e(TAG, ykMessage.toString());
                        }
                        break;
                    case Brands:
                        if (ykMessage != null && ykMessage.getCode() == 0) {
                            BrandResult brandResult = (BrandResult) ykMessage.getData();
                            if (brandResult != null) {
                                mBrandResult = brandResult;
                                mHandler.sendEmptyMessage(HAND_BRANDS_SUC);
                            }
                        } else if (ykMessage != null) {
                            Log.e(TAG, ykMessage.toString());
                        }
                        break;
                    case DeviceInfo:
                        if (!TextUtils.isEmpty(ykMessage.getMsg())) {
                            if (isFirst) {
                                isFirst = false;
                                HardInfo info = (HardInfo) ykMessage.getData();
                                if("1".equals(info.getLed())){
                                }
                                if (info != null && !TextUtils.isEmpty(info.getVoice_num())) {
                                    tvVoice.setText(info.getVoice_num());
                                    seekBar.setSelectedMaxValue(Integer.valueOf(info.getVoice_num()));
                                }
                            } else {
                                DlgUtils.createDefDlg(activity, ykMessage.getMsg());
                            }
                        }
                        break;
                    case getOtaVersionFail:
                        DlgUtils.createDefDlg(activity, "OTA版本获取失败");
                        break;
                    case otaVersion:
                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof CheckVersionResult) {
                            CheckVersionResult result = (CheckVersionResult) ykMessage.getData();
                            DlgUtils.createDefDlg(activity, "", "当前固件版本：" + result.getVersion()
                                    + "\n最新版本：" + result.getOtaversion()
                                    + "\n当前语音固件版本：" + result.getVoice_version()
                                    + "\n最新固件版本：" + result.getVoice_otaversion(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
//                            if (!TextUtils.isEmpty(result.getOtaversion()) && !TextUtils.isEmpty(result.getVersion())) {
//                                int newV = Integer.valueOf(result.getOtaversion().replace('.', '0'));
//                                int curV = Integer.valueOf(result.getVersion().replace('.', '0'));
//                                if (newV > curV) {
//                                    DlgUtils.createDefDlg(activity, "版本更新", "硬件检测到新版本，是否更新？" + "\n当前版本：" + result.getVersion()
//                                            + "\n最新版本：" + result.getOtaversion(), new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                            Yaokan.instance().updateDevice(App.curDid);
//                                        }
//                                    }, new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//                                } else {
//                                    if (!isFirst) {
//                                        DlgUtils.createDefDlg(activity, "当前已是最新版本：" + result.getVersion());
//                                    }
//                                }
//                            }
                        }
                        break;
                    case UpdateStart:
                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof ProgressResult) {
                            if (App.curMac.equals(((ProgressResult) ykMessage.getData()).getMac())) {
                                showProDlg("固件升级中...", 0);
                            }
                        }
                        break;
                    case UpdateProgress:
                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof ProgressResult) {
                            if (App.curMac.equals(((ProgressResult) ykMessage.getData()).getMac())) {
                                showProDlg("固件升级中...", Integer.valueOf(((ProgressResult) ykMessage.getData()).getProgress().replaceAll("%", "")));
                            }
                        }
                        break;
                    case UpdateSuc:
                        dismissPro();
                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof ProgressResult) {
                            if (App.curMac.equals(((ProgressResult) ykMessage.getData()).getMac())) {
                                DlgUtils.createDefDlg(activity, "升级成功!");
                            }
                        }
                        break;
                    case UpdateFail:
                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof ProgressResult) {
                            if (App.curMac.equals(((ProgressResult) ykMessage.getData()).getMac())) {
                                dismissPro();
                                DlgUtils.createDefDlg(activity, "升级失败");
                            }
                        }
                        break;
                    case Other:
                        if (ykMessage != null) {
                            Message message = mHandler.obtainMessage();
                            message.what = OTHER_FAIL;
                            message.obj = ykMessage.getMsg();
                            mHandler.sendMessage(message);
                        }
                        break;
                    case AirPowerResult:
                        List<AirPowerResult> list = (List<AirPowerResult>) ykMessage.getData();
                        //返回的Model说明 tag:第几天或月；value：电量统计值，保留4位小数；createAt：电量上报的时间戳，用于tag显示的排序
                        DlgUtils.createDefDlg(activity,new Gson().toJson(list));
                        break;
                }
            }
        });
    }


    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HAND_TYPES_SUC:
                    if (mTypeResult != null && mTypeResult.getResult() != null) {
                        nameType.clear();
                        for (DeviceType deviceType : mTypeResult.getResult()) {
                            if (deviceType.getRf() == 1 && Integer.valueOf(App.curRf) != 1) {
                                //设备不支持射频遥控则跳过
                                continue;
                            }
                            nameType.add(deviceType.getName() + (deviceType.getRf() == 1 ? "(射频)" : ""));
                        }
                    }
                    typeAdapter.notifyDataSetInvalidated();
                    spType.setAdapter(typeAdapter);
                    break;
                case HAND_BRANDS_SUC:
                    if (mBrandResult != null && mBrandResult.getResult() != null) {
                        nameBrands.clear();
                        for (Brand brand : mBrandResult.getResult()) {
                            nameBrands.add(brand.getName());
                        }
                    }
                    brandAdapter.notifyDataSetInvalidated();
                    spBrands.setAdapter(brandAdapter);
                    break;
                case OTHER_FAIL:
                    DlgUtils.createDefDlg(activity, (String) msg.obj);
                    log((String) msg.obj);
                default:
                    break;
            }
        }
    };

}
