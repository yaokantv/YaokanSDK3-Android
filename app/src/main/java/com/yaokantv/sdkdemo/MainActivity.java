package com.yaokantv.sdkdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.YkDevice;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, YaokanSDKListener {
    CommonAdapter<YkDevice> adapter;
    List<YkDevice> mList = new ArrayList<>();
    ListView listView;
    String appId = "";
    String appSecret = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showDlg();
        Yaokan.instance().init(getApplication(), appId, appSecret).addSdkListener(this);
        initView();
        initToolbar(R.string.app_name);
    }

    private void initView() {
        listView = findViewById(R.id.lv_device);
        adapter = new CommonAdapter<YkDevice>(this, mList, R.layout.lv_item) {
            @Override
            public void convert(ViewHolder helper, final YkDevice item, int position) {
                String status = item.isOnline() ? (item.isLan() ? "局域网" : "远程") : "离线";
                helper.setText(R.id.tv_item, " Mac:" + item.getMac() + "\n did:" + item.getDid() + " " + status);
                helper.setOnclickListener(R.id.btn_test, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Yaokan.instance().test(item.getMac(), item.getDid());
                    }
                });
                helper.setOnclickListener(R.id.btn_del, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Yaokan.instance().deleteDevice(item.getMac());
                        mList.remove(item);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, BrandActivity.class);
                App.curMac = mList.get(position).getMac();
                App.curDid = mList.get(position).getDid();
                startActivity(intent);
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
            case R.id.btn_smart_config:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(this, SmartConfigActivity.class));
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 99);
                }
                break;
            case R.id.btn_input_device_list:
                //保存设备方式一
//                if (mList.size() > 0) {
//                    Yaokan.instance().saveYkDevicesToDB(mList);
//                }
                //保存设备方式二
//                Yaokan.instance().saveYkDevicesToDB("[{\"did\":\"A64D184C35EAB091\",\"mac\":\"DC4F22529F13\",\"name\":\"YKK_1.0\"}]");
                break;
            case R.id.btn_device_list:
                //提取设备方式一
//                String s = Yaokan.instance().getDeviceListStringFromDB();

                //提取设备方式二
//                List<YkDevice> mList = Yaokan.instance().getDeviceListFromDB();
//                if (mList != null && mList.size() > 0) {
//                    for (YkDevice device : mList) {
//                        Logger.e(device.toString());
//                    }
//                }
                break;
            case R.id.btn_rc_list:
                String s2 = Yaokan.instance().exportRcString();
                log(s2);
                break;
            case R.id.btn_input_rc_list:
                String json = "[{\"name\":\"格力风扇 FSL-40B\",\"rid\":\"20160517151758\",\"rmodel\":\"FSL-40B\",\"be_rmodel\":\"FSL-40B\",\"be_rc_type\":6,\"bid\":103,\"study_id\":\"\",\"rc_command\":{\"Timer\":{\"name\":\"定时\",\"value\":\"Timer\"},\"Mode\":{\"name\":\"模式\",\"value\":\"Mode\"},\"Sway\":{\"name\":\"摇头 \",\"value\":\"Sway\"},\"Power\":{\"name\":\"电源\",\"value\":\"Power\"},\"TurnOff\":{\"name\":\"关机 \",\"value\":\"TurnOff\"}}},{\"name\":\"格力空调 3(V3)\",\"rid\":\"2016093013074565\",\"rmodel\":\"3(V3)\",\"be_rmodel\":\"3(V3)\",\"be_rc_type\":7,\"bid\":104,\"study_id\":\"\",\"rc_command\":{\"mode\":[\"auto\",\"cold\",\"dry\",\"hot\",\"wind\"],\"attributes\":{\"verticalIndependent\":0,\"horizontalIndependent\":0,\"auto\":{\"speed\":[0,1,2,3],\"swing\":[\"horizontalOff\",\"horizontalOn\",\"verticalOff\",\"verticalOn\"],\"temperature\":[]},\"dry\":{\"speed\":[1],\"swing\":[\"horizontalOff\",\"horizontalOn\",\"verticalOff\",\"verticalOn\"],\"temperature\":[]},\"hot\":{\"speed\":[0,1,2,3],\"swing\":[\"horizontalOff\",\"horizontalOn\",\"verticalOff\",\"verticalOn\"],\"temperature\":[16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]},\"cold\":{\"speed\":[0,1,2,3],\"swing\":[\"horizontalOff\",\"horizontalOn\",\"verticalOff\",\"verticalOn\"],\"temperature\":[16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]},\"wind\":{\"speed\":[1,2,3],\"swing\":[\"horizontalOff\",\"horizontalOn\",\"verticalOff\",\"verticalOn\"],\"temperature\":[16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]}}}}]";
                Yaokan.instance().inputRcString(json);
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 99 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(this, SmartConfigActivity.class));
        }
    }

    @Override
    public void onReceiveMsg(MsgType msgType, YkMessage ykMessage) {
        switch (msgType) {
            case Init:
                dismiss();
                if (ykMessage != null && ykMessage.getCode() == 0) {
                    //初始化成功
                } else {
                    //初始化失败
                    log(ykMessage.toString());
                }
                break;
            case DeviceOnline:
                if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof YkDevice) {
                    YkDevice device = (YkDevice) ykMessage.getData();
                    Yaokan.instance().saveYkDeviceToDB(device);
                    if (mList.contains(device)) {
                        mList.remove(device);
                    }
                    if (!TextUtils.isEmpty(device.getDid())) {
                        mList.add(device);
                        notifyList();
                    }
                }
                break;
            case DeviceOffline:
                if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof YkDevice) {
                    YkDevice device = (YkDevice) ykMessage.getData();
                    Yaokan.instance().saveYkDeviceToDB(device);
                    if (mList.contains(device)) {
                        mList.remove(device);
                    }
                    if (!TextUtils.isEmpty(device.getDid())) {
                        mList.add(device);
                        notifyList();
                    }
                }
                break;
        }
    }

    void notifyList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
