package com.yaokantv.yaokanui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yaokantv.sdkdemo.App;
import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.Contants;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.BigAppleTreaty;
import com.yaokantv.yaokansdk.model.CreateGfskResult;
import com.yaokantv.yaokansdk.model.DeviceResult;
import com.yaokantv.yaokansdk.model.GfskMacResult;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.WhereBean;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.utils.YKAppManager;
import com.yaokantv.yk.YKTools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GfskMatchActivity extends BaseActivity implements View.OnClickListener {

    TextView tvRoad;
    String subMac, code;
    int road, tid, bid;
    CreateGfskResult createGfskResult;
    LinkedList<String> linkedList = new LinkedList<>();
    List<GfskMacResult.RfCodesBean> rfCodes;
    GfskMacResult gfskMacResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gfsk_match);
        setMTitle("设备配码", TITLE_LOCATION_CENTER);
        Yaokan.instance().addSdkListener(this);
    }

    @Override
    protected void initView() {
        tvRoad = findViewById(R.id.tv_road);
        road = getIntent().getIntExtra("road", 0);
        tid = getIntent().getIntExtra("tid", 0);
        bid = getIntent().getIntExtra("bid", 0);
        gfskMacResult = getIntent().getExtras().getParcelable("p");
        subMac = gfskMacResult.getMac();
        rfCodes = gfskMacResult.getRfCodes();
        String t = Integer.toHexString(tid);
        if (t.length() == 1) {
            t = "0" + t;
        }
        code = Contants.YK_PRO + "15FFAA010100" + App.curMac + subMac + "300" + road + "00" + t;
        switch (road) {
            case 4:
                findViewById(R.id.test4).setVisibility(View.VISIBLE);
            case 3:
                findViewById(R.id.test3).setVisibility(View.VISIBLE);
            case 2:
                findViewById(R.id.test2).setVisibility(View.VISIBLE);
                break;
        }
        tvRoad.setText(getIntent().getIntExtra("road", 0) + "");
        findViewById(R.id.rl_match).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Yaokan.instance().publishDown(App.curDid, 10 + new YKTools().encode(5, code));
            }
        });
        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(subMac)) {
                    return;
                }
                showDlg(1 * 60, "创建中", new OnDownloadTimerOutListener() {
                    @Override
                    public void onTimeOut() {

                    }
                });
                Yaokan.instance().rfOsmV2(App.curMac, tid, bid, road, subMac);
            }
        });
    }

    boolean isUploadFinish = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Yaokan.instance().removeSdkListener(this);
    }

    protected void setRoom(String rid, String type) {
        WhereBean treaty = new WhereBean();
        treaty.setRid(rid);
        treaty.setDefaultType(type);
        Yaokan.instance().publishDown(App.curDid, "58" + new Gson().toJson(treaty));
    }

    protected void bigAppleDownloadCode(String rid, String type) {
        BigAppleTreaty treaty = new BigAppleTreaty();
        treaty.setRid(rid);
        treaty.setType(type);
        Yaokan.instance().publishDown(App.curDid, treaty.getDownloadCode());
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (linkedList.size() > 0) {
                        String rid = linkedList.getFirst();
                        bigAppleDownloadCode(rid, createGfskResult.getTid() + "");
                    } else {
                        dismiss();
                        handler.sendEmptyMessageDelayed(2, 1000);
                    }
                    break;
                case 1:
                    break;
                case 2:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DlgUtils.createDefDlg(activity, "", "下载完成", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!TextUtils.isEmpty(uuid) && road == 1) {
                                        Intent intent = new Intent(activity, RoomMsgActivity.class);
                                        intent.putExtra("uuid", uuid);
                                        intent.putExtra("create", true);
                                        startActivity(intent);
                                    }
                                    YKAppManager.getAppManager().finishActivities(SelectProviderActivity.class, RoadListActivity.class,
                                            NewMatchActivity.class, SelectDeviceActivity.class, BrandListActivity.class, SelectGfskModeActivity.class);
                                    finish();
                                }
                            }, false);
                        }
                    });
                    break;
            }
        }
    };

    String uuid = "";
    String rmodel = "";

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (msgType == MsgType.CreateGfskResult) {
                    if (ykMessage != null && ykMessage.getData() != null) {
                        createGfskResult = (CreateGfskResult) ykMessage.getData();
                        for (CreateGfskResult.RidsBean bean : createGfskResult.getRids()) {
                            String rid = bean.getRid();
                            rmodel = bean.getRfModel();
                            linkedList.add(rid);
                        }
                        isUploadFinish = true;
                        handler.sendEmptyMessage(0);
                    }
                } else if (msgType == MsgType.DownloadCode) {
                    final DeviceResult result = (DeviceResult) ykMessage.getData();
                    if (result != null && !TextUtils.isEmpty(App.curMac) && App.curMac.equals(result.getMac())) {
                        switch (result.getCode()) {
                            case "00":
                            case "04":
                                handler.sendEmptyMessage(0);
                                break;
                            case "03":
                                String rid = linkedList.getFirst();
                                setRoom(rid, createGfskResult.getTid() + "");
                                RemoteCtrl ctrl = new RemoteCtrl();
                                ctrl.setRid(rid);
                                ctrl.setStudyId(rid);
                                ctrl.setBe_rc_type(tid);
                                ctrl.setName(StringUtils.typeString(activity, tid));
                                ctrl.setBid(bid);
                                ctrl.setMac(App.curMac);
                                ctrl.setRmodel(rmodel);
                                ctrl.setRf("1");
                                ctrl.setRcCmd(createKeysModel(rid));
                                ctrl = Yaokan.instance().saveRc(ctrl);
                                if (ctrl != null) {
                                    uuid = ctrl.getUuid();
                                }
                                linkedList.removeFirst();
                                handler.sendEmptyMessage(0);
                                break;
                            case "07":
                                dismiss();
                                DlgUtils.createDefDlg(GfskMatchActivity.this, "", "遥控器数量已到达上限", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }, false);
                                break;
                        }
                    }
                }
            }
        });
    }

    private List<RcCmd> createKeysModel(String rid) {
        List<RcCmd> rcCmds = new ArrayList<>();
        rcCmds.add(new RcCmd(rid, "开", "on"));
        rcCmds.add(new RcCmd(rid, "关", "off"));
        rcCmds.add(new RcCmd(rid, "电源", "power"));
        return rcCmds;
    }

    @Override
    protected void reload() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.test1:
                Yaokan.instance().publishDown(App.curDid, rfCodes.get(0).getFunCode() + new YKTools().encode(5, rfCodes.get(0).getRfCode()));
                break;
            case R.id.test2:
                Yaokan.instance().publishDown(App.curDid, rfCodes.get(0).getFunCode() + new YKTools().encode(5, rfCodes.get(1).getRfCode()));
                break;
            case R.id.test3:
                Yaokan.instance().publishDown(App.curDid, rfCodes.get(0).getFunCode() + new YKTools().encode(5, rfCodes.get(2).getRfCode()));
                break;
            case R.id.test4:
                Yaokan.instance().publishDown(App.curDid, rfCodes.get(0).getFunCode() + new YKTools().encode(5, rfCodes.get(3).getRfCode()));
                break;
        }
    }
}