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

import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.Brand;
import com.yaokantv.yaokansdk.model.BrandResult;
import com.yaokantv.yaokansdk.model.CheckVersionResult;
import com.yaokantv.yaokansdk.model.DeviceType;
import com.yaokantv.yaokansdk.model.DeviceTypeResult;
import com.yaokantv.yaokansdk.model.ProgressResult;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.DlgUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
        initView();
        Yaokan.instance().addSdkListener(this);
        initToolbar(App.curMac);
        //检测更新
        Yaokan.instance().checkDeviceVersion(App.curMac, App.curDid);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Yaokan.instance().removeSdkListener(this);
    }

    private void initView() {
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
                currDeviceType = mTypeResult.getResult()[pos];
                App.curTName = currDeviceType.getName();
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
                    } else if (currDeviceType.getRf()==1) {
                        startActivity(new Intent(this, RfMatchActivity.class));
                    } else {
                        startActivity(new Intent(this, MatchActivity.class));
                    }
                }
                break;
            case R.id.btn_light_on:
                Yaokan.instance().lightOn(App.curMac, App.curDid);
                break;
            case R.id.btn_light_off:
                Yaokan.instance().lightOff(App.curMac, App.curDid);
                break;
            case R.id.btn_update_device:
                Yaokan.instance().updateDevice(App.curMac, App.curDid);
                break;
            case R.id.btn_reset_apple:
                Yaokan.instance().resetApple(App.curMac, App.curDid);
                break;
            case R.id.btn_check_version:
                Yaokan.instance().checkDeviceVersion(App.curMac, App.curDid);
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
                    case otaVersion:
                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof CheckVersionResult) {
                            CheckVersionResult result = (CheckVersionResult) ykMessage.getData();
                            if (!TextUtils.isEmpty(result.getOtaversion()) && !TextUtils.isEmpty(result.getVersion())) {
                                if (!result.getOtaversion().equals(result.getVersion())) {
                                    DlgUtils.createDefDlg(activity, "版本更新", "硬件检测到新版本，是否更新？" + "\n当前版本：" + result.getVersion()
                                            + "\n最新版本：" + result.getOtaversion(), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Yaokan.instance().updateDevice(App.curMac, App.curDid);
                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                } else {
                                    if (!isFirst) {
                                        DlgUtils.createDefDlg(activity, "当前已是最新版本：" + result.getOtaversion());
                                    }
                                }
                            }
                        }
                        isFirst = false;
                        break;
                    case UpdateStart:
                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof ProgressResult) {
                            if (App.curMac.equals(((ProgressResult) ykMessage.getData()).getMac())) {
                                showProDlg("硬件升级中...", 0);
                            }
                        }
                        break;
                    case UpdateProgress:
                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof ProgressResult) {
                            if (App.curMac.equals(((ProgressResult) ykMessage.getData()).getMac())) {
                                showProDlg("硬件升级中...", Integer.valueOf(((ProgressResult) ykMessage.getData()).getProgress().replaceAll("%", "")));
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
                            nameType.add(deviceType.getName()+(deviceType.getRf()==1?"(射频)":""));
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
