package com.yaokantv.sdkdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.ViewHolder;

public class RfMatchActivity extends BaseActivity implements YaokanSDKListener {
    RemoteCtrl rc;

    GridView gridView;
    CommonAdapter<RcCmd> adapter;
    CountDownTimer countDownTime;
    boolean isStudy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rf_match);
        initToolbar(R.string.t_rf);
//        initNormal();
        Yaokan.instance().addSdkListener(this);
        Yaokan.instance().getRfMatchingResult(App.curMac, App.curTid, App.curBid);
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rc.getId() == 0) {
                    rc.setMac(App.curMac);
                    Yaokan.instance().saveRc(rc);
                }
                AppManager.getAppManager().finishActivities(BrandActivity.class);
                finish();
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
                    Yaokan.instance().sendCmd(App.curDid, rc.getRid(), rc.getRcCmd().get(position).getValue(), rc.getBe_rc_type(), rc.getStudyId(), rc.getRf());
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
                    Yaokan.instance().saveRc(rc);
                }
                Yaokan.instance().studyRf(App.curMac, App.curDid, rc, rc.getRcCmd().get(position).getValue());
                showDlg("请对准小苹果发码...", new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        isStudy = false;
                        if (countDownTime != null) {
                            countDownTime.cancel();
                        }
                        Yaokan.instance().stopStudyRf(App.curMac, App.curDid, rc);
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
                            Yaokan.instance().updateRc(ctrl);
                            if (countDownTime != null) {
                                countDownTime.cancel();
                            }
                            DlgUtils.createDefDlg(activity, getString(R.string.study_suc));
                        }
                        break;
                }
            }
        });
    }
}
