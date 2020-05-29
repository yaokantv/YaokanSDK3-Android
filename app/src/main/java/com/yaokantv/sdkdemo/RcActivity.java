package com.yaokantv.sdkdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.yaokantv.yaokansdk.Contants;
import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.AirOrder;
import com.yaokantv.yaokansdk.model.DeviceResult;
import com.yaokantv.yaokansdk.model.Mode;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.Swing;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.Logger;
import com.yaokantv.yaokansdk.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class RcActivity extends BaseActivity implements View.OnClickListener, YaokanSDKListener {
    public static final String UUID = "UUID";
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
    boolean isCodeChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rc);
        initToolbar(R.string.rc_detail);
        Yaokan.instance().addSdkListener(this);
        String uuid = getIntent().getStringExtra(UUID);
        etName = findViewById(R.id.et_name);
        if (!TextUtils.isEmpty(uuid)) {
            rc = Yaokan.instance().getRcDataByUUID(uuid);
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
                    case R.id.export_single:
                        String json = Yaokan.instance().exportRcString(rc.getUuid());
                        DlgUtils.createDefDlg(RcActivity.this, json);
                        break;
                    case R.id.action_delete_rc:
                        if (Yaokan.instance().isDeviceOnline(rc.getMac())) {
                            //删除设备端和手机内的的遥控器
                            Yaokan.instance().deleteDeviceRc(did, rc.getRid());
                            Yaokan.instance().deleteRcByUUID(rc.getUuid());
                            finish();
                        } else {
                            //如果不在线则只能删除手机数据库中的遥控器
                            Yaokan.instance().deleteRcByUUID(rc.getUuid());
                            finish();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        //如果射频码库有改变要通知设备去下载
        if (isCodeChange && "1".equals(rc.getRf())) {
            Yaokan.instance().downloadRFCodeToDevice(App.curDid, rc.getStudy_id(), rc.getBe_rc_type());
            return;
        }
        super.onBackPressed();
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
        adapter = new CommonAdapter<RcCmd>(this, rc.getRcCmd(), R.layout.lv_key_item) {
            @Override
            public void convert(ViewHolder helper, RcCmd item, int position) {
                helper.setText(R.id.text1, item.getName());
                helper.setText(R.id.text2, item.getValue());
                helper.setBgResource(R.id.rl, item.getStand_key() == 1 ? R.drawable.shape_blue_btn : R.drawable.shape_btn);
            }
        };
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isStudy) {
                    Yaokan.instance().sendCmd(did, rc.getRid(), rc.getRcCmd().get(position).getValue(), rc.getBe_rc_type(), rc.getStudy_id(), rc.getRf());
                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isStudy) {
                    return false;
                }
                if ("1".equals(rc.getRf())) {
                    Yaokan.instance().studyRf(did, rc, rc.getRcCmd().get(position).getValue());
                } else {
                    Yaokan.instance().study(did, rc, rc.getRcCmd().get(position).getValue());
                }
                showDlg("请对准小苹果发码...", new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        isStudy = false;
                        if (countDownTime != null) {
                            countDownTime.cancel();
                        }
                        Yaokan.instance().stopStudy(did);
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
                Yaokan.instance().sendCmd(did, rc.getRid(), "on", rc.getBe_rc_type(), null, null);
                break;
            case R.id.btn_off:
                Yaokan.instance().sendCmd(did, rc.getRid(), "off", rc.getBe_rc_type(), null, null);
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
                    case EmptyDid:
                        break;
                    case StudyError:
                        if (!isStudy) {
                            return;
                        }
                        isStudy = false;
                        dismiss();
                        if (countDownTime != null) {
                            countDownTime.cancel();
                        }
                        DlgUtils.createDefDlg(activity, ykMessage.toString());
                        break;
                    case StudySuccess:
                        if (!isStudy) {
                            return;
                        }
                        isStudy = false;
                        isCodeChange = true;
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
                    case DelAppleCtrl:
                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof DeviceResult) {
                            DeviceResult result = (DeviceResult) ykMessage.getData();
                            switch (result.getCode()) {
                                case Contants.YK_DELETE_CODE_RESULT_SINGLE_SUC:
                                    toast(R.string.app_delete_success);
                                    break;
                                case Contants.YK_DELETE_CODE_RESULT_SINGLE_FAIL:
                                    //有可能是设备端没有这个遥控器（设备被复位了）
                                    toast(R.string.app_delete_failed);
                                    break;
                            }
                        }
                        break;
                    case DownloadCode:
                        if (!isFinishing()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final DeviceResult result = (DeviceResult) ykMessage.getData();
                                    Logger.e("DownloadCode" + result.toString());
                                    if (result != null && !TextUtils.isEmpty(App.curMac) && App.curMac.equals(result.getMac())) {
                                        String msg = "";
                                        switch (result.getCode()) {
                                            case "00":
                                                dismiss();
                                                msg = "开启下载遥控器失败";
                                                break;
                                            case "01"://开始下载遥控器
                                                Logger.e("开始下载遥控器");
                                                showDlg(120, "正在下载码库到设备...", new OnDownloadTimerOutListener() {
                                                    @Override
                                                    public void onTimeOut() {
                                                        DlgUtils.createDefDlg(activity, "下载超时");
                                                    }
                                                });
                                                break;
                                            case "03"://下载遥控器成功
                                                Logger.e("下载遥控器成功");
                                                dismiss();
                                                DlgUtils.createDefDlg(activity, "", "下载成功", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                    }
                                                }, false);

                                                break;
                                            case "04":
                                                msg = "下载遥控器失败";
                                                break;
                                            case "05":
                                                msg = "遥控器已存在设备中";
                                                break;
                                            case "06":
                                                msg = "空调遥控器达到极限";
                                                break;
                                            case "07":
                                                msg = "非空调遥控器达到极限";
                                                break;
                                            case "08":
                                                msg = "射频遥控器达到极限";
                                                break;
                                            case "09":
                                                msg = "门铃遥控器达到极限";
                                                break;
                                        }
                                        if (!TextUtils.isEmpty(msg)) {
                                            dismiss();
                                            DlgUtils.createDefDlg(activity, "", msg, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            }, false);
                                        }
                                    }
                                }
                            });
                        }
                        break;
                }
            }
        });
    }
}
