package com.yaokantv.sdkdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.DeviceResult;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.Logger;
import com.yaokantv.yaokansdk.utils.ViewHolder;

public class RfMatchActivity extends BaseActivity implements YaokanSDKListener {
    RemoteCtrl rc;

    GridView gridView;
    CommonAdapter<RcCmd> adapter;
    CountDownTimer countDownTime;
    boolean isStudy = false;

    boolean isDownloadToDevice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rf_match);
        initToolbar(R.string.t_rf);
        Yaokan.instance().addSdkListener(this);
        Yaokan.instance().getRfMatchingResult(App.curMac, App.curTid, App.curBid);
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rc.getId() == 0) {
                    rc.setMac(App.curMac);
                    //射频按键没有学习直接创建时，调用此接口
                    showDlg();
                    rc = Yaokan.instance().uploadRfAndSave(App.curMac, rc);

                } else {
                    showDlg();
                    Yaokan.instance().downloadRFCodeToDevice(App.curDid, rc.getRid(), rc.getBe_rc_type());
                }
            }
        });
    }

    private void initNormal() {
        rc.setName(App.curBName + App.curTName);
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
                    Yaokan.instance().sendCmd(App.curDid, rc.getRid(), rc.getRcCmd().get(position).getValue(), rc.getBe_rc_type(), rc.getStudy_id(), rc.getRf());
                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isStudy) {
                    return false;
                }
                if (rc.getId() == 0) {
                    rc.setMac(App.curMac);
                    rc = Yaokan.instance().saveRc(rc);
                }
                Yaokan.instance().studyRf(  App.curDid, rc, rc.getRcCmd().get(position).getValue());
                showDlg("请对准小苹果发码...", new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        isStudy = false;
                        if (countDownTime != null) {
                            countDownTime.cancel();
                        }
                        Yaokan.instance().stopStudyRf(  App.curDid, rc);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isDownloadToDevice && rc.getId() != 0) {//如果没有将码库下载到设备 则删除本地的遥控器
            Yaokan.instance().deleteRcByUUID(rc.getUuid());
        }
        Yaokan.instance().removeSdkListener(this);
    }

    @Override
    public void onReceiveMsg(MsgType msgType, YkMessage ykMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (msgType) {
                    case RemoteInfo:
                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof RemoteCtrl) {
                            rc = (RemoteCtrl) ykMessage.getData();
                            initNormal();
                        }
                        break;
                    case StudyError:
                        isStudy = false;
                        dismiss();
                        if (countDownTime != null) {
                            countDownTime.cancel();
                        }
                        DlgUtils.createDefDlg(activity, ykMessage.toString());
//                        log(ykMessage.toString());
                        break;
                    case StudySuccess:
                        isStudy = false;
                        dismiss();
                        if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof RemoteCtrl) {
                            RemoteCtrl ctrl = (RemoteCtrl) ykMessage.getData();
                            rc = ctrl;
                            Yaokan.instance().updateRc(ctrl);
                            if (countDownTime != null) {
                                countDownTime.cancel();
                            }
                            DlgUtils.createDefDlg(activity, getString(R.string.study_suc));
                        }
                        break;
                    case RfUploadSuccess:
                        Yaokan.instance().downloadRFCodeToDevice(App.curDid, rc.getRid(), rc.getBe_rc_type());
                        break;
                    case RfUploadFail:
                        dismiss();
                        DlgUtils.createDefDlg(activity, "", "上传失败：" + ykMessage.getMsg(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
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
                                                        isDownloadToDevice = true;
                                                        AppManager.getAppManager().finishActivities(SelectProviderActivity.class,
                                                                BrandActivity.class, MatchActivity.class);
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
