package com.yaokantv.sdkdemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.YkDevice;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.ViewHolder;

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
                        Yaokan.instance().test(item.getMac(), item.getDid());
                    }
                });
                helper.setOnclickListener(R.id.btn_del, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DlgUtils.createDefDlg(activity, "", "是否删除该设备?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Yaokan.instance().resetApple(item.getMac(), item.getDid());
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
                App.curRf = mList.get(position).getRf();
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
            case R.id.btn_smart_config:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(this, SmartConfigActivity.class));
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 99);
                }
                break;
            case R.id.btn_input_device_list:
                //导入设备方式一
//                if (mList.size() > 0) {
//                    Yaokan.instance().inputYkDevicesToDB(mList);
//                }
                //导入设备方式二
                Yaokan.instance().inputYkDevicesToDB("[{\"did\":\"XXXXXXXXXXXXXXXX\",\"mac\":\"XXXXXXXXXXXX\",\"name\":\"YKK_1.0\"}]");
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
                String json = "[{\"name\":\"海尔风扇 1-0-0\",\"rid\":\"20150724144229\",\"rmodel\":\"1-0-0\",\"be_rmodel\":\"1\",\"be_rc_type\":6,\"bid\":71,\"mac\":\"84F3EB30F05D\",\"rf\":\"0\",\"study_id\":\"0\",\"rc_command\":{\"mode\":{\"name\":\"模式\",\"value\":\"mode\",\"stand_key\":1,\"order_no\":1},\"timer\":{\"name\":\"定时\",\"value\":\"timer\",\"stand_key\":1,\"order_no\":1},\"fanspeed\":{\"name\":\"风速\",\"value\":\"fanspeed\",\"stand_key\":1,\"order_no\":1},\"poweroff\":{\"name\":\"关机\",\"value\":\"poweroff\",\"stand_key\":1,\"order_no\":1},\"oscillation\":{\"name\":\"摇头 \",\"value\":\"oscillation\",\"stand_key\":1,\"order_no\":1},\"power\":{\"name\":\"电源\",\"value\":\"power\",\"stand_key\":1,\"order_no\":1}}},{\"name\":\"易百珑开关\",\"rid\":\"4\",\"be_rc_type\":21,\"bid\":3175,\"mac\":\"84F3EB30F05D\",\"rf\":\"1\",\"study_id\":\"0\",\"rc_command\":{\"power\":{\"name\":\"电源\",\"value\":\"power\",\"stand_key\":1,\"order_no\":1},\"on\":{\"name\":\"开\",\"value\":\"on\",\"stand_key\":1,\"order_no\":2},\"off\":{\"name\":\"关\",\"value\":\"off\",\"stand_key\":1,\"order_no\":3},\"key1\":{\"name\":\"自定义1\",\"value\":\"key1\",\"stand_key\":0,\"order_no\":4},\"key2\":{\"name\":\"自定义2\",\"value\":\"key2\",\"stand_key\":0,\"order_no\":5},\"key3\":{\"name\":\"自定义3\",\"value\":\"key3\",\"stand_key\":0,\"order_no\":6},\"key4\":{\"name\":\"自定义4\",\"value\":\"key4\",\"stand_key\":0,\"order_no\":7},\"key5\":{\"name\":\"自定义5\",\"value\":\"key5\",\"stand_key\":0,\"order_no\":8},\"key6\":{\"name\":\"自定义6\",\"value\":\"key6\",\"stand_key\":0,\"order_no\":9},\"key7\":{\"name\":\"自定义7\",\"value\":\"key7\",\"stand_key\":0,\"order_no\":10},\"key8\":{\"name\":\"自定义8\",\"value\":\"key8\",\"stand_key\":0,\"order_no\":11},\"key9\":{\"name\":\"自定义9\",\"value\":\"key9\",\"stand_key\":0,\"order_no\":12}}},{\"name\":\"海尔(小超人)空调 a19\",\"rid\":\"20181120083237\",\"rmodel\":\"a19\",\"be_rmodel\":\"空调\",\"be_rc_type\":7,\"bid\":72,\"mac\":\"DC4F22529E01\",\"rf\":\"0\",\"study_id\":\"0\",\"rc_command\":{\"mode\":[\"auto\",\"cold\",\"dry\",\"hot\",\"wind\"],\"attributes\":{\"verticalIndependent\":0,\"horizontalIndependent\":0,\"auto\":{\"speed\":[0],\"swing\":[\"verticalOff\",\"verticalOn\"],\"temperature\":[]},\"dry\":{\"speed\":[1],\"swing\":[\"verticalOff\",\"verticalOn\"],\"temperature\":[]},\"hot\":{\"speed\":[0,1,2,3],\"swing\":[\"verticalOff\",\"verticalOn\"],\"temperature\":[16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]},\"cold\":{\"speed\":[0,1,2,3],\"swing\":[\"verticalOff\",\"verticalOn\"],\"temperature\":[16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]},\"wind\":{\"speed\":[1,2,3],\"swing\":[\"verticalOff\",\"verticalOn\"],\"temperature\":[16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]}}}}]";
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
            case DeviceOnline:
                if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof YkDevice) {
                    YkDevice device = (YkDevice) ykMessage.getData();
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
            case DeviceOffline:
                if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof YkDevice) {
                    YkDevice device = (YkDevice) ykMessage.getData();
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

    void notifyList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
