package com.yaokantv.sdkdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.AirOrder;
import com.yaokantv.yaokansdk.model.Mode;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.Swing;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class RcActivity extends BaseActivity implements View.OnClickListener, YaokanSDKListener {
    public static final String RID = "RID";
    RemoteCtrl rc;

    GridView gridView;
    CommonAdapter<RcCmd> adapter;

    List<String> modeList = new ArrayList<>();
    List<String> speedList = new ArrayList<>();
    List<String> tempList = new ArrayList<>();
    List<String> verList = new ArrayList<>();
    List<String> horList = new ArrayList<>();
    String curMode, curSpeed, curTemp, curVer, curHor;
    int modeIndex, speedIndex, tempIndex, verIndex, horIndex;
    CountDownTimer countDownTime;
    EditText etName;
    boolean isStudy = false;
    private String did;//小苹果重置后Did会改变！

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rc);
        initToolbar(R.string.rc_detail);
        Yaokan.instance().addSdkListener(this);
        String rid = getIntent().getStringExtra(RID);
        etName = findViewById(R.id.et_name);
        if (!TextUtils.isEmpty(rid)) {
            rc = Yaokan.instance().getRcData(rid);
            if (rc != null && rc.getRcCmd() != null && rc.getRcCmd().size() > 0) {
                initNormal();
            } else if (rc != null && !TextUtils.isEmpty(rc.getAirCmdString())) {
                initAir();
            }
        }
        if (rc != null) {
            etName.setText(rc.getName());
            did = Yaokan.instance().getDid(rc.getMac());
        }
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete_rc:
                        //用Rid删除遥控器的方法已过期，建议使用deleteRcByUUID
//                        Yaokan.instance().deleteRc(rc.getRid());
                        Yaokan.instance().deleteRcByUUID(rc.getUuid());
                        finish();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Yaokan.instance().removeSdkListener(this);
        if (countDownTime != null) {
            countDownTime.cancel();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private void initNormal() {

        gridView = findViewById(R.id.gv);
        gridView.setVisibility(View.VISIBLE);
        adapter = new CommonAdapter<RcCmd>(this, rc.getRcCmd(), android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder helper, RcCmd item, int position) {
                helper.setText(android.R.id.text1, item.getName());
                helper.setBgResource(android.R.id.text1, item.getStand_key() == 1 ? R.drawable.shape_blue_btn : R.drawable.shape_btn);
            }
        };
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isStudy) {
                    Yaokan.instance().sendCmd(did, rc.getRid(), rc.getRcCmd().get(position).getValue(), rc.getBe_rc_type(), rc.getStudyId());
                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isStudy) {
                    return false;
                }
                if (rc.getBe_rc_type() >= 21 && rc.getBe_rc_type() <= 24) {
                    Yaokan.instance().studyRf(rc.getMac(), did, rc, rc.getRcCmd().get(position).getValue());
                } else {
                    Yaokan.instance().study(rc.getMac(), did, rc, rc.getRcCmd().get(position).getValue());
                }
                showDlg("请对准小苹果发码...", new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        isStudy = false;
                        if (countDownTime != null) {
                            countDownTime.cancel();
                        }
                        Yaokan.instance().stopStudy(rc.getMac(), did);
                    }
                });
                newCountDownTime();
                isStudy = true;
                return false;
            }
        });

    }

    private void newCountDownTime() {
        if (countDownTime != null) {
            countDownTime.cancel();
        }
        countDownTime = new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                isStudy = false;
                dismiss();
                DlgUtils.createDefDlg(activity, "学习超时");
            }
        };
        countDownTime.start();
    }

    private void initAir() {
        findViewById(R.id.ll_air).setVisibility(View.VISIBLE);
        modeList.clear();
        for (String mode : rc.getAirCmd().getMode()) {
            modeList.add(mode);
        }
        if (modeList.size() > 0) {
            curMode = modeList.get(0);
        }
        refreshView();
    }

    private void sendCmd() {
        Yaokan.instance().sendAirCmd(did, rc.getRid(), new AirOrder(curMode, curSpeed, curTemp, curVer, curHor));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rename:
                if (etName.getText().toString().length() > 0) {
                    rc.setName(etName.getText().toString());
                    Yaokan.instance().updateRc(rc);
                }
                break;
            case R.id.btn_on:
                Yaokan.instance().sendCmd(did, rc.getRid(), "on", rc.getBe_rc_type());
                break;
            case R.id.btn_off:
                Yaokan.instance().sendCmd(did, rc.getRid(), "off", rc.getBe_rc_type());
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
                    Yaokan.instance().sendAirCmd(did, rc.getRid(), Swing.Ver, isOpen);
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
                    Yaokan.instance().sendAirCmd(did, rc.getRid(), Swing.Hor, isOpen);
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

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (msgType) {
                    case StudyError:
                        isStudy = false;
                        dismiss();
                        if (countDownTime != null) {
                            countDownTime.cancel();
                        }
                        DlgUtils.createDefDlg(activity, ykMessage.toString());
                        break;
                    case StudySuccess:
                        isStudy = false;
                        dismiss();
                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof RemoteCtrl) {
                            RemoteCtrl ctrl = (RemoteCtrl) ykMessage.getData();
                            Yaokan.instance().updateRc(ctrl);
                            if (countDownTime != null) {
                                countDownTime.cancel();
                            }
                            DlgUtils.createDefDlg(activity, getString(R.string.study_suc));
                        }
                        break;
                    case SendCodeResponse:
                        log(ykMessage.toString());
                        break;
                }
            }
        });
    }
}
