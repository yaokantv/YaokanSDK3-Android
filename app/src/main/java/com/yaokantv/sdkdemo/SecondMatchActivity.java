package com.yaokantv.sdkdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.AirOrder;
import com.yaokantv.yaokansdk.model.MatchingData;
import com.yaokantv.yaokansdk.model.Mode;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.Swing;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SecondMatchActivity extends BaseActivity implements View.OnClickListener, YaokanSDKListener {
    int index = 0;
    List<MatchingData> dataList;
    MatchingData mMatch;
    TextView textView;
    RemoteCtrl rc;
    CommonAdapter<RcCmd> adapter;
    GridView gv;
    List<RcCmd> codeKeys = new ArrayList<>();


    List<String> modeList = new ArrayList<>();
    List<String> speedList = new ArrayList<>();
    List<String> tempList = new ArrayList<>();
    List<String> verList = new ArrayList<>();
    List<String> horList = new ArrayList<>();
    String curMode, curSpeed, curTemp, curVer, curHor;
    int modeIndex, speedIndex, tempIndex, verIndex, horIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_match);
        initToolbar(R.string.second_match);

        initView();
        Yaokan.instance().addSdkListener(this);
        showDlg();
        Yaokan.instance().getMatchingResult(App.curTid, App.curBid, App.curGid);
    }

    private void initView() {
        textView = findViewById(R.id.tips);
        if (App.curTid == 7) {
            findViewById(R.id.ll_air).setVisibility(View.VISIBLE);

        } else {
            gv = findViewById(R.id.gv);
            gv.setVisibility(View.VISIBLE);
            adapter = new CommonAdapter<RcCmd>(this, codeKeys, android.R.layout.simple_list_item_1) {
                @Override
                public void convert(ViewHolder helper, RcCmd item, int position) {
                    helper.setText(android.R.id.text1, item.getName());
                    helper.setBgResource(android.R.id.text1, R.drawable.shape_btn);
                }
            };
            gv.setAdapter(adapter);
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String key = codeKeys.get(position).getValue();
                    Yaokan.instance().sendCmd(App.curDid, rc.getRid(), key, rc.getBe_rc_type());
                }
            });
        }
    }

    private void sendCmd() {
        Yaokan.instance().sendAirCmd(App.curDid, rc.getRid(), new AirOrder(curMode, curSpeed, curTemp, curVer, curHor));
        setText();
    }

    void setText() {
        ((TextView) findViewById(R.id.tv_mode)).setText(getShowText(curMode));
        ((TextView) findViewById(R.id.tv_speed)).setText(getShowText(curSpeed));
        ((TextView) findViewById(R.id.tv_temp)).setText(curTemp + "℃");
        if (rc.getAirCmd().getAttributes().getVerticalIndependent() == 0) {
            ((TextView) findViewById(R.id.tv_ver)).setText(getShowText(curVer));
            ((TextView) findViewById(R.id.tv_hor)).setText(getShowText(curHor));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Yaokan.instance().removeSdkListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pre:
                index--;
                if (index < 0) {
                    index = dataList.size() - 1;
                }
                setMatchMsg();
                break;
            case R.id.btn_next:
                index++;
                if (index >= dataList.size()) {
                    index = 0;
                }
                setMatchMsg();
                break;
            case R.id.btn_save:
                rc.setName(App.curBName + App.curTName + " " + rc.getRmodel());
                Yaokan.instance().saveRc(rc);
                AppManager.getAppManager().finishActivities(SelectProviderActivity.class,
                        BrandActivity.class, MatchActivity.class);
                finish();
                break;
            case R.id.btn_get_again:
                showDlg();
                match();
                break;
            case R.id.btn_second:
                if (mMatch != null) {
                    App.curGid = mMatch.getGid();
                }
                break;
            case R.id.btn_on:
                Yaokan.instance().sendCmd(App.curDid, rc.getRid(), "on", rc.getBe_rc_type());
                break;
            case R.id.btn_off:
                Yaokan.instance().sendCmd(App.curDid, rc.getRid(), "off", rc.getBe_rc_type());
                break;
            case R.id.btn_mode:
                modeIndex++;
                if (modeIndex >= modeList.size()) {
                    modeIndex = 0;
                }
                curMode = modeList.get(modeIndex);
                refreshView();
                sendCmd();
                break;
            case R.id.btn_speed:
                speedIndex++;
                if (speedIndex >= speedList.size()) {
                    speedIndex = 0;
                }
                if (speedList.size() > 0) {
                    curSpeed = speedList.get(speedIndex);
                } else {
                    curSpeed = "";
                }
                sendCmd();
                break;
            case R.id.btn_ver:
                if (rc.getAirCmd().getAttributes().getVerticalIndependent() == 1) {
                    verIndex++;
                    boolean isOpen = (verIndex % 2 == 1);
                    Yaokan.instance().sendAirCmd(App.curDid, rc.getRid(), Swing.Ver, isOpen);
                    ((TextView) findViewById(R.id.tv_ver)).setText(getShowText(isOpen ? "verOn" : "verOff"));
                } else {
                    verIndex++;
                    if (verIndex >= verList.size()) {
                        verIndex = 0;
                    }
                    if (verList.size() > 0) {
                        curVer = verList.get(verIndex);
                    } else {
                        curVer = "";
                    }
                    sendCmd();
                }
                break;
            case R.id.btn_hor:
                if (rc.getAirCmd().getAttributes().getVerticalIndependent() == 1) {
                    horIndex++;
                    boolean isOpen = (horIndex % 2 == 1);
                    Yaokan.instance().sendAirCmd(App.curDid, rc.getRid(), Swing.Hor, isOpen);
                    ((TextView) findViewById(R.id.tv_ver)).setText(getShowText(isOpen ? "horOn" : "horOff"));
                } else {
                    horIndex++;
                    if (horIndex >= horList.size()) {
                        horIndex = 0;
                    }
                    if (horList.size() > 0) {
                        curHor = horList.get(horIndex);
                    } else {
                        curHor = "";
                    }
                    sendCmd();
                }
                break;
            case R.id.btn_sub:
                tempIndex--;
                if (tempIndex < 0) {
                    tempIndex = 0;
                }
                curTemp = tempList.get(tempIndex);
                sendCmd();
                break;
            case R.id.btn_add:
                tempIndex++;
                if (tempIndex >= tempList.size()) {
                    tempIndex = tempList.size() - 1;
                }
                curTemp = tempList.get(tempIndex);
                sendCmd();
                break;
        }
    }

    private void match() {
        if (mMatch != null) {
            Yaokan.instance().remoteInfo(mMatch.getRid(), App.curTid);
        } else {
            dismiss();
        }

    }

    private void setRcData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismiss();
                if (rc != null && rc.getRcCmd() != null && 7 != rc.getBe_rc_type() && rc.getRcCmd().size() > 0) {
                    codeKeys.clear();
                    codeKeys.addAll(rc.getRcCmd());
                    adapter.notifyDataSetChanged();
                } else if (rc != null && 7 == rc.getBe_rc_type() && rc.getAirCmd() != null) {
                    modeList.clear();
                    for (String mode : rc.getAirCmd().getMode()) {
                        modeList.add(mode);
                    }
                    if (modeList.size() > 0) {
                        curMode = modeList.get(0);
                    }
                    refreshView();
                }
            }
        });
    }

    private void refreshView() {
        if (rc.getAirCmd() != null && !TextUtils.isEmpty(curMode)) {
            Mode mode = null;
            switch (curMode) {
                case "auto"://自动模式
                    mode = rc.getAirCmd().getAttributes().getAuto();
                    break;
                case "cold"://制冷模式
                    mode = rc.getAirCmd().getAttributes().getCold();
                    break;
                case "hot"://制热模式
                    mode = rc.getAirCmd().getAttributes().getHot();
                    break;
                case "wind"://送风模式
                    mode = rc.getAirCmd().getAttributes().getWind();
                    break;
                case "dry"://抽湿模式
                    mode = rc.getAirCmd().getAttributes().getDry();
                    break;
            }
            if (mode != null) {
                speedList.clear();
                for (int speed : mode.getSpeed()) {
                    speedList.add(speed + "");
                }
                if (speedList.size() > 0) {
                    if (speedList.size() > speedIndex) {
                        curSpeed = speedList.get(speedIndex);
                    } else {
                        speedIndex = 0;
                        curSpeed = speedList.get(speedIndex);
                    }
                    setViewStatus(findViewById(R.id.btn_speed), true);
                } else {
                    setViewStatus(findViewById(R.id.btn_speed), false);
                    curSpeed = "";
                }

                tempList.clear();
                for (int temp : mode.getTemperature()) {
                    tempList.add(temp + "");
                }

                if (tempList.size() > 0) {
                    if (tempList.size() > tempIndex) {
                        curTemp = tempList.get(tempIndex);
                    } else {
                        tempIndex = 0;
                        curTemp = tempList.get(0);
                    }
                    setViewStatus(findViewById(R.id.tv_temp), true);
                    setViewStatus(findViewById(R.id.btn_sub), true);
                    setViewStatus(findViewById(R.id.btn_add), true);
                } else {
                    curTemp = "";
                    setViewStatus(findViewById(R.id.tv_temp), false);
                    setViewStatus(findViewById(R.id.btn_sub), false);
                    setViewStatus(findViewById(R.id.btn_add), false);
                }


//
                verList.clear();
                horList.clear();
                for (String swing : mode.getSwing()) {
                    if (swing.contains("horizontal")) {
                        horList.add(swing);
                    } else if (swing.contains("vertical")) {
                        verList.add(swing);
                    }
                }
                if (verList.size() > 0) {//有上下掃風
                    if (verList.size() > verIndex) {
                        curVer = verList.get(verIndex);
                    } else {
                        verIndex = 0;
                        curVer = verList.get(verIndex);
                    }
                    setViewStatus(findViewById(R.id.btn_ver), true);
                } else {
                    if (rc.getAirCmd().getAttributes().getVerticalIndependent() == 1) {
                        setViewStatus(findViewById(R.id.btn_ver), true);
                    } else {
                        setViewStatus(findViewById(R.id.btn_ver), false);
                    }
                    curVer = "";
                }
                if (horList.size() > 0) {
                    if (horList.size() > horIndex) {
                        curHor = horList.get(horIndex);
                    } else {
                        horIndex = 0;
                        curHor = horList.get(horIndex);
                    }
                    setViewStatus(findViewById(R.id.btn_hor), true);
                } else {
                    if (rc.getAirCmd().getAttributes().getHorizontalIndependent() == 1) {
                        setViewStatus(findViewById(R.id.btn_hor), true);
                    } else {
                        setViewStatus(findViewById(R.id.btn_hor), false);
                    }
                    curHor = "";
                }
            }
            setText();
        }
    }

    private void setMatchMsg() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.ll_match).setVisibility(View.VISIBLE);
                mMatch = dataList.get(index);
                String msg = App.curBName + " " + App.curTName + (index + 1);
                msg += "\n" + (index + 1) + "/" + dataList.size();
                textView.setText(msg);
                match();
            }
        });
    }

    @Override
    public void onReceiveMsg(MsgType msgType, YkMessage ykMessage) {
        switch (msgType) {
            case SecondMatching:
                if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof List) {
                    dataList = (List) ykMessage.getData();
                    if (dataList != null && dataList.size() > 0) {
                        setMatchMsg();
                    }
                } else {
                    dismiss();
                }
                break;
            case RemoteInfo:
                if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof RemoteCtrl) {
                    rc = (RemoteCtrl) ykMessage.getData();
                    setRcData();
                }
                break;
        }
    }

}
