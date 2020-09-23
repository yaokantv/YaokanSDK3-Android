package com.yaokantv.sdkdemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.orhanobut.hawk.Hawk;
import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.HotPointConfigResult;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.SoftApConfigResult;
import com.yaokantv.yaokansdk.model.YkDevice;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.Logger;
import com.yaokantv.yaokansdk.utils.ViewHolder;
import com.yaokantv.yaokanui.RcListActivity;
import com.yaokantv.yaokanui.SoftApConfigActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, YaokanSDKListener {
    CommonAdapter<YkDevice> adapter;
    List<YkDevice> mList = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar(R.string.app_name);
        Yaokan.instance().addSdkListener(this);
        initView();
    }

    String getMac(String mac) {
        if (!TextUtils.isEmpty(mac)) {
            StringBuilder newM = new StringBuilder();
            int i = 0;
            for (char c : mac.toCharArray()) {
                newM.append(c);
                if (i % 2 == 1 && i != mac.length() - 1) {
                    newM.append(":");
                }
                i++;
            }
            mac = newM.toString();
        }
        return mac;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Yaokan.instance().onDestroy(getApplication());
    }

    private void initView() {
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.refresh_list:
                        Yaokan.instance().loadDevices(mList);
                        break;
                    case R.id.export_error_log:
                        String log = Yaokan.instance().getErrorLog();
                        log(log);
                        log = Yaokan.instance().getLastErrorLog();
                        log(log);
                        break;
                    case R.id.clear_log:
                        Yaokan.instance().clearCrashLog();
                        break;
                    case R.id.about:
                        String text = "当前版本：" + Yaokan.SDK_VERSION + "\n" + "发布日期：" + Yaokan.RELEASE_DATA;
                        DlgUtils.createDefDlg(activity, text);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        listView = findViewById(R.id.lv_device);
        adapter = new CommonAdapter<YkDevice>(this, mList, R.layout.lv_item) {
            @Override
            public void convert(ViewHolder helper, final YkDevice item, int position) {
                String status = item.isOnline() ? "在线" : "离线";
                helper.setText(R.id.tv_item, " MAC:" + getMac(item.getMac()) + " " + status);
                helper.setOnclickListener(R.id.btn_test, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Yaokan.instance().test(item.getDid());
                    }
                });
                helper.setOnclickListener(R.id.btn_del, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DlgUtils.createDefDlg(activity, "", "是否删除该设备?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Yaokan.instance().deleteRemoteByMac(item.getMac());
                                Yaokan.instance().apReset(item.getMac(), item.getDid());
                                Yaokan.instance().deleteDevice(item.getMac());
                                mList.remove(item);
                                adapter.notifyDataSetChanged();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
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
                App.curRf = mList.get(position).getName().contains("RF") ? "1" : "0";
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hot_point_config:
                Yaokan.instance().hotPointConfig();
                break;
            case R.id.btn_soft_ap_config:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(this, SoftApConfigActivity.class));
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 98);
                }
                break;
            case R.id.btn_input_device_list:
                //导入设备方式一
//                if (mList.size() > 0) {
//                    Yaokan.instance().inputYkDevicesToDB(mList);
//                }
                //导入设备方式二
                Yaokan.instance().inputYkDevicesToDB("[{\"did\":\"CA2772763BBD7FD4\",\"mac\":\"BCDDC2898CD7\",\"name\":\"\",\"rf\":\"0\"}]");
                break;
            case R.id.btn_device_list:
                //导出设备方式一
                String s = Yaokan.instance().exportDeviceListStringFromDB();
                DlgUtils.createDefDlg(activity, s);
                //导出设备方式二
//                List<YkDevice> mList = Yaokan.instance().exportDeviceListFromDB();
//                if (mList != null && mList.size() > 0) {
//                    for (YkDevice device : mList) {
//                        Logger.e(device.toString());
//                    }
//                }
                break;
            case R.id.btn_rc_list:
                startActivity(new Intent(this, RcListActivity.class));
                break;
            case R.id.btn_ex_rc_list:
                String s2 = Yaokan.instance().exportRcString();
                DlgUtils.createDefDlg(activity, s2);
                break;
            case R.id.btn_input_rc_list:
                String data = "[{\"rf_body\":\"\",\"rf\":\"0\",\"be_rmodel\":\"a_2\",\"name\":\" a_2\",\"be_rc_type\":2,\"rmodel\":\"a_2\",\"study_id\":\"0\",\"rid\":\"201907041018161755\",\"bid\":3582,\"rc_command\":{\"Liyin\":{\"order_no\":1,\"stand_key\":0,\"name\":\"丽音\",\"value\":\"Liyin\"},\"back\":{\"order_no\":1,\"stand_key\":1,\"name\":\"返回\",\"value\":\"back\"},\"down\":{\"order_no\":1,\"stand_key\":1,\"name\":\"下\",\"value\":\"down\"},\"vol-\":{\"order_no\":1,\"stand_key\":1,\"name\":\"音量-\",\"value\":\"vol-\"},\"vol+\":{\"order_no\":1,\"stand_key\":1,\"name\":\"音量+\",\"value\":\"vol+\"},\"screendisplay\":{\"order_no\":1,\"stand_key\":0,\"name\":\"场景显示\",\"value\":\"screendisplay\"},\"V\":{\"order_no\":1,\"stand_key\":0,\"name\":\"V\",\"value\":\"V\"},\"power\":{\"order_no\":1,\"stand_key\":1,\"name\":\"电源\",\"value\":\"power\"},\"up\":{\"order_no\":1,\"stand_key\":1,\"name\":\"上\",\"value\":\"up\"},\"ok\":{\"order_no\":1,\"stand_key\":1,\"name\":\"OK\",\"value\":\"ok\"},\"track\":{\"order_no\":1,\"stand_key\":0,\"name\":\"声道\",\"value\":\"track\"},\"--\":{\"order_no\":1,\"stand_key\":0,\"name\":\"--\",\"value\":\"--\"},\"12\":{\"order_no\":1,\"stand_key\":0,\"name\":\"12\",\"value\":\"12\"},\"normal\":{\"order_no\":1,\"stand_key\":0,\"name\":\"正常\",\"value\":\"normal\"},\"ch+\":{\"order_no\":1,\"stand_key\":1,\"name\":\"频道+\",\"value\":\"ch+\"},\"ch-\":{\"order_no\":1,\"stand_key\":1,\"name\":\"频道-\",\"value\":\"ch-\"},\"mute\":{\"order_no\":1,\"stand_key\":1,\"name\":\"静音\",\"value\":\"mute\"},\"right\":{\"order_no\":1,\"stand_key\":1,\"name\":\"右\",\"value\":\"right\"},\"menu\":{\"order_no\":1,\"stand_key\":1,\"name\":\"菜单\",\"value\":\"menu\"},\"0\":{\"order_no\":1,\"stand_key\":1,\"name\":\"0\",\"value\":\"0\"},\"1\":{\"order_no\":1,\"stand_key\":1,\"name\":\"1\",\"value\":\"1\"},\"2\":{\"order_no\":1,\"stand_key\":1,\"name\":\"2\",\"value\":\"2\"},\"3\":{\"order_no\":1,\"stand_key\":1,\"name\":\"3\",\"value\":\"3\"},\"4\":{\"order_no\":1,\"stand_key\":1,\"name\":\"4\",\"value\":\"4\"},\"left\":{\"order_no\":1,\"stand_key\":1,\"name\":\"左\",\"value\":\"left\"},\"5\":{\"order_no\":1,\"stand_key\":1,\"name\":\"5\",\"value\":\"5\"},\"6\":{\"order_no\":1,\"stand_key\":1,\"name\":\"6\",\"value\":\"6\"},\"7\":{\"order_no\":1,\"stand_key\":1,\"name\":\"7\",\"value\":\"7\"},\"8\":{\"order_no\":1,\"stand_key\":1,\"name\":\"8\",\"value\":\"8\"},\"9\":{\"order_no\":1,\"stand_key\":1,\"name\":\"9\",\"value\":\"9\"},\"drop out\":{\"order_no\":1,\"stand_key\":0,\"name\":\"退出\",\"value\":\"drop out\"}},\"mac\":\"123123\",\"rc_commond_type\":1}]";
//                String data = "[{\"name\":\" a_3\",\"rid\":\"201907041018161756\",\"rmodel\":\"a_3\",\"be_rmodel\":\"a_3\",\"be_rc_type\":2,\"bid\":3582,\"mac\":\"DC4F22529E01\",\"rf\":\"0\",\"rc_command_type\":1,\"study_id\":\"0\",\"rc_command\":{\"ch+\":{\"name\":\"频道+\",\"value\":\"ch+\",\"stand_key\":1,\"order_no\":1},\"ch-\":{\"name\":\"频道-\",\"value\":\"ch-\",\"stand_key\":1,\"order_no\":1},\"mute\":{\"name\":\"静音\",\"value\":\"mute\",\"stand_key\":1,\"order_no\":1},\"vol-\":{\"name\":\"音量-\",\"value\":\"vol-\",\"stand_key\":1,\"order_no\":1},\"vol+\":{\"name\":\"音量+\",\"value\":\"vol+\",\"stand_key\":1,\"order_no\":1},\"0\":{\"name\":\"0\",\"value\":\"0\",\"stand_key\":1,\"order_no\":1},\"1\":{\"name\":\"1\",\"value\":\"1\",\"stand_key\":1,\"order_no\":1},\"2\":{\"name\":\"2\",\"value\":\"2\",\"stand_key\":1,\"order_no\":1},\"3\":{\"name\":\"3\",\"value\":\"3\",\"stand_key\":1,\"order_no\":1},\"4\":{\"name\":\"4\",\"value\":\"4\",\"stand_key\":1,\"order_no\":1},\"5\":{\"name\":\"5\",\"value\":\"5\",\"stand_key\":1,\"order_no\":1},\"6\":{\"name\":\"6\",\"value\":\"6\",\"stand_key\":1,\"order_no\":1},\"7\":{\"name\":\"7\",\"value\":\"7\",\"stand_key\":1,\"order_no\":1},\"8\":{\"name\":\"8\",\"value\":\"8\",\"stand_key\":1,\"order_no\":1},\"9\":{\"name\":\"9\",\"value\":\"9\",\"stand_key\":1,\"order_no\":1},\"power\":{\"name\":\"电源\",\"value\":\"power\",\"stand_key\":1,\"order_no\":1},\"normal\":{\"name\":\"正常\",\"value\":\"normal\",\"stand_key\":0,\"order_no\":1},\"screendisplay\":{\"name\":\"场景显示\",\"value\":\"screendisplay\",\"stand_key\":0,\"order_no\":1},\"track\":{\"name\":\"声道\",\"value\":\"track\",\"stand_key\":0,\"order_no\":1},\"drop out\":{\"name\":\"退出\",\"value\":\"drop out\",\"stand_key\":0,\"order_no\":1}}}]";
                List<RemoteCtrl> list = Yaokan.instance().inputRcString(data);
                for (RemoteCtrl ctrl : list) {
                    RemoteCtrl ctrl2 = Yaokan.instance().getRcDataByUUID(ctrl.getUuid());
                    Log.e(TAG, ctrl2.toString());
                }
                break;

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 98 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(this, SoftApConfigActivity.class));
        }
    }

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (msgType) {
                    case HotPointStart:
                        showForceDlg("手机热点配网中");
                        break;
                    case HotPointConfigResult:
                        HotPointConfigResult configResult = (HotPointConfigResult) ykMessage.getData();
                        if(configResult!=null){
                            if(configResult.isResult()){
                                dismiss();
                                showDlg();
                            }
                            DlgUtils.createDefDlg(activity, "", ykMessage.getMsg(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof SoftApConfigResult) {
                                        SoftApConfigResult result = (SoftApConfigResult) ykMessage.getData();
                                        Logger.e(result.toString());
                                    }
                                }
                            });
                        }
                        break;
                    case DeviceOnline:
                    case DeviceOffline:

                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof YkDevice) {
                            YkDevice device = (YkDevice) ykMessage.getData();
                            //设备上线之后要把设备保存进去
                            Yaokan.instance().inputYkDeviceToDB(device);
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
        });
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
