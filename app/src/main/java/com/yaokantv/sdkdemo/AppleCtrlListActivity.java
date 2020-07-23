package com.yaokantv.sdkdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.yaokantv.yaokansdk.Contants;
import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.CtrlStatus;
import com.yaokantv.yaokansdk.model.DeviceResult;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/***
 * 设备遥控器列表
 */
public class AppleCtrlListActivity extends BaseActivity implements YaokanSDKListener {
    ListView lv;
    CommonAdapter<CtrlStatus> adapter;
    List<CtrlStatus> ctrlStatusList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_ctrl_list);
        Yaokan.instance().addSdkListener(this);
        initView();
        initToolbar(App.curMac);
        showDlg(10, "加载中...", new OnDownloadTimerOutListener() {
            @Override
            public void onTimeOut() {
                dismiss();
            }
        });
        Yaokan.instance().deviceCtrlList(App.curDid, "01", 15);
        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDlg();
                Yaokan.instance().deviceCtrlList(App.curDid, "15", 15);
            }
        });
        findViewById(R.id.btn_delete_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Yaokan.instance().deleteAllDevice(App.curDid);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Yaokan.instance().removeSdkListener(this);
    }

    private void initView() {
        lv = findViewById(R.id.lv_device);
        adapter = new CommonAdapter<CtrlStatus>(this, ctrlStatusList, R.layout.lv_rc_item) {
            @Override
            public void convert(ViewHolder helper, final CtrlStatus item, int position) {
                helper.setVisibility(R.id.text2, View.GONE);
                String s = Yaokan.instance().getNameByRid(item.getRid());
                if (TextUtils.isEmpty(s)) {
                    if (Yaokan.instance().isRfType(item.getType())) {
                        s = Yaokan.instance().getNameByStudyId(item.getRid());
                    }
                    if (TextUtils.isEmpty(s)) {
                        s = item.getRid();
                    }
                }
                helper.setText(R.id.text1, s);
                helper.setOnLongClickListener(R.id.rl, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DlgUtils.createDefDlg(AppleCtrlListActivity.this, "", "是否删除改遥控器？",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Yaokan.instance().deleteDeviceCode(App.curDid, item.getRid());
                                        ctrlStatusList.remove(item);
                                        adapter.notifyDataSetChanged();
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        return false;
                    }
                });
            }
        };
        lv.setAdapter(adapter);
    }

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (msgType == MsgType.AppleCtrlList) {
                    List<CtrlStatus> ctrlList = (List) ykMessage.getData();
                    if (ctrlList != null && ctrlList.size() > 0) {
                        for (int i = 0; i < ctrlList.size(); i++) {
                            if (!ctrlStatusList.contains(ctrlList.get(i))) {
                                ctrlStatusList.add(ctrlList.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        dismiss();
                    } else {
                        dismiss();
                    }
                } else if (msgType == MsgType.DelAppleCtrl) {
                    if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof DeviceResult) {
                        DeviceResult result = (DeviceResult) ykMessage.getData();
                        switch (result.getCode()) {
                            case Contants.YK_DELETE_CODE_RESULT_ALL_SUC:
                                DlgUtils.createDefDlg(activity, "", getString(R.string.app_delete_success), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Yaokan.instance().deleteAllRcDevice();
                                        finish();
                                    }
                                }, false);
                                break;
                            case Contants.YK_DELETE_CODE_RESULT_ALL_FAIL:
                                toast(R.string.app_delete_failed);
                                break;
                        }
                    }
                }
            }
        });
    }
}
