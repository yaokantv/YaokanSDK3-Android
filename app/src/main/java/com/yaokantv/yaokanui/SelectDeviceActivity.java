package com.yaokantv.yaokanui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.DeviceType;
import com.yaokantv.yaokansdk.model.DeviceTypeResult;
import com.yaokantv.yaokansdk.model.MpeBindResult;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SelectDeviceActivity extends BaseActivity {
    private GridView gvDeviceType, gvRfDeviceType;
    private List<DeviceType> arrayDeviceType = new ArrayList<>(), arrayRfDeviceType = new ArrayList<>();
    boolean isRf;
    CommonAdapter<DeviceType> gvAdapter, gvRfAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRf = getIntent().getBooleanExtra(Config.S_IS_RF, false);
        setContentView(R.layout.activity_select_device);
        setMTitle(R.string.title_select_device, TITLE_LOCATION_LEFT);
        reload();
    }

    @Override
    protected void initView() {
        gvDeviceType = findViewById(R.id.gv_device_type);
        gvRfDeviceType = findViewById(R.id.gv_rf_device_type);
        if (isRf) {
            findViewById(R.id.tv_rf).setVisibility(View.VISIBLE);
            gvRfDeviceType.setVisibility(View.VISIBLE);
        }
        gvAdapter = new CommonAdapter<DeviceType>(activity, arrayDeviceType, R.layout.item_rc_type) {
            @Override
            public void convert(ViewHolder helper, DeviceType item, int position) {
                setGridView(helper, item, position);

            }
        };
        gvRfAdapter = new CommonAdapter<DeviceType>(activity, arrayRfDeviceType, R.layout.item_rc_type) {
            @Override
            public void convert(ViewHolder helper, DeviceType item, int position) {
                setGridView(helper, item, position);
            }
        };
        gvDeviceType.setAdapter(gvAdapter);
        gvRfDeviceType.setAdapter(gvRfAdapter);
    }

    private int tid = 0;

    private void setGridView(ViewHolder helper, final DeviceType item, int position) {
        helper.setText(R.id.tv_device_type_name, item.getName());
        helper.setOnclickListener(R.id.sr_content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getTid() == -1) {
                    return;
                }
                Intent intent = new Intent();
                Config.curTName = item.getName();
                if (item.getTid() == 1) {
                    intent.setClass(activity, SelectProviderActivity.class);
                } else if (item.getTid() == 45 || item.getTid() == 46) {

                    intent.setClass(activity, BrandListActivity.class);
                    intent.putExtra(Config.S_IS_RF, item.getRf() == 1);
                    intent.putExtra(Config.S_TID, item.getTid());
                } else {
                    intent.setClass(activity, BrandListActivity.class);
                    intent.putExtra(Config.S_IS_RF, item.getRf() == 1);
                    intent.putExtra(Config.S_TID, item.getTid());
                }
                startActivity(intent);
            }
        });
        TextView tag = helper.getView(R.id.tv_tag);
        if (item.getTid() == -1) {
            tag.setVisibility(View.GONE);
            helper.setVisibility(R.id.iv_type, View.GONE);
        } else {
            tag.setVisibility(View.VISIBLE);
            helper.setVisibility(R.id.iv_type, View.VISIBLE);
            if (item.getRf() == 0) {
                tag.setText("IR");
                tag.setBackgroundColor(getResources().getColor(R.color.white));
                tag.setTextColor(getResources().getColor(R.color.tag_color));
            } else if (item.getRf() == 1) {
                tag.setText("RF");
                tag.setBackgroundColor(getResources().getColor(R.color.tag_blue_bg));
                tag.setTextColor(getResources().getColor(R.color.white));
            }
            switch (item.getTid()) {
                case 1:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_stb);
                    break;
                case 2:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_tv);
                    break;
                case 7:
                case 44:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_air);
                    break;
                case 10:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_box);
                    break;
                case 6:
                case 42:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_fan);
                    break;
                case 8:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_light);
                    break;
                case 3:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_dvd);
                    break;
                case 5:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_projector);
                    break;
                case 11:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_satv);
                    break;
                case 13:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_audio);
                    break;
                case 15:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_air_p);
                    break;
                case 40:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_water_h);
                    break;
                case 12:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_sweeper);
                    break;
                case 14:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_camera);
                    break;
                case 24:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_hanger);
                    break;
                case 23:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_curtain);
                    break;
                case 21:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_switch);
                    break;
                case 22:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_jack);
                    break;
                case 25:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_light_ctrl);
                    break;
                case 38:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_fan_light);
                    break;
                case 41:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_liangba);
                    break;
                case 45:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_bed);
                    break;
                case 46:
                    helper.setImageResource(R.id.iv_type, R.mipmap.ctrl_chair);
                    break;
            }
        }
    }

    @Override
    protected void reload() {
        showDlg();
        Yaokan.instance().getDeviceType();
    }

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismiss();
                switch (msgType) {

                    case Types:
                        if (ykMessage != null && ykMessage.getCode() == 0) {
                            showContent(true);
                            DeviceTypeResult typeResult = (DeviceTypeResult) ykMessage.getData();
                            if (typeResult != null) {
                                for (DeviceType type : typeResult.getResult()) {
                                    if (type.getRf() == 0) {
                                        arrayDeviceType.add(type);
                                    } else if (type.getRf() == 1) {
                                        arrayRfDeviceType.add(type);
                                    }
                                }
                                while (arrayDeviceType.size() % 3 != 0) {
                                    DeviceType type = new DeviceType();
                                    type.setName("");
                                    type.setRf(0);
                                    type.setTid(-1);
                                    arrayDeviceType.add(type);
                                }
                                while (arrayRfDeviceType.size() % 3 != 0) {
                                    DeviceType type = new DeviceType();
                                    type.setName("");
                                    type.setRf(1);
                                    type.setTid(-1);
                                    arrayRfDeviceType.add(type);
                                }
                                gvAdapter.notifyDataSetChanged();
                                gvRfAdapter.notifyDataSetChanged();
                            }
                        } else if (ykMessage != null) {
                            showContent(false);
                            Log.e(TAG, ykMessage.toString());
                        }
                        break;
                    case Other:
                        if (ykMessage != null) {
                            showContent(false);
                        }
                        break;
                }
            }
        });
    }
}