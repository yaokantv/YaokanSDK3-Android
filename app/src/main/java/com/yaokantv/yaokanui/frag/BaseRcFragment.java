package com.yaokantv.yaokanui.frag;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.Logger;
import com.yaokantv.yaokansdk.utils.Utility;
import com.yaokantv.yaokanui.Config;
import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.utils.AnimStudy;
import com.yaokantv.yaokanui.utils.DataUtils;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.utils.ToastUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;
import com.yaokantv.yaokanui.widget.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRcFragment extends Fragment implements YaokanSDKListener {
    protected AnimStudy animStudy;

    protected NoScrollGridView expandGridView;
    protected ExpandAdapter mExpandAdapter;

    public void refreshRcData(RemoteCtrl rc) {
        setConfig();
    }

    public abstract void setKeyBackground();

    protected String key = "";
    RemoteCtrl rc;
    DataUtils dataUtils;
    RcActivity rcActivity;
    boolean isStudy = false;
    CountDownTimer countDownTime;
    List<RcCmd> map = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataUtils = new DataUtils(getActivity());
        rcActivity = (RcActivity) getActivity();
        animStudy = new AnimStudy(getActivity());
        Yaokan.instance().addSdkListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setConfig();
    }

    private void setConfig() {
        if (rc != null && !TextUtils.isEmpty(rc.getMac())) {
            String mac = rc.getMac();
            if (!TextUtils.isEmpty(mac)) {
                String did = Yaokan.instance().getDid(mac);
                if (!TextUtils.isEmpty(did)) {
                    Config.setMac(mac);
                    Config.setDid(did);
                }
            }
        }
    }

    protected void bindGvEvent() {
        expandGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                key = mExpandAdapter.getExpandKeys().get(position).getValue();
                if (!TextUtils.isEmpty(key)) {
                    sendCmd(key);
                }
            }
        });
        expandGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                key = mExpandAdapter.getExpandKeys().get(position).getValue();
                if (!TextUtils.isEmpty(key)) {
                    View v = view.findViewById(R.id.key_btn);
                    if (v != null) {
                        v.setTag(StringUtils.DRA_BTN_NUM);
                        study(false, key, v);
                        return true;
                    }
                    study(false, key, view);
                }
                return true;
            }
        });
    }

    protected void bindGvEventRf() {
        expandGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                key = mExpandAdapter.getExpandKeys().get(position).getValue();
                if (!TextUtils.isEmpty(key)) {
                    sendCmd(key);
                }
            }
        });
        expandGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                key = mExpandAdapter.getExpandKeys().get(position).getValue();
                if (!TextUtils.isEmpty(key)) {
                    View v = view.findViewById(R.id.key_btn);
                    if (v != null) {
                        v.setTag(StringUtils.DRA_BTN_NUM);
                        study(true, key, v);
                        return true;
                    }
                    study(false, key, view);
                }
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Yaokan.instance().removeSdkListener(this);
        if (countDownTime != null) {
            countDownTime.cancel();
        }
    }

    protected void newCountDownTime() {
        if (countDownTime != null) {
            countDownTime.cancel();
        }
        countDownTime = new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    isStudy = false;
                    rcActivity.dismiss();
                    animStudy.stopAnim();
                    rcActivity.setStudyTips(R.string.study_fail);
                }
            }
        };
        countDownTime.start();
    }

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (msgType) {
                        case StudyError:
                            if (!isStudy) {
                                return;
                            }
                            animStudy.stopAnim();
                            isStudy = false;
                            rcActivity.dismiss();
                            if (countDownTime != null) {
                                countDownTime.cancel();
                            }
                            DlgUtils.createDefDlg(rcActivity, getString(R.string.study_fail), ykMessage.toString());
                            break;
                        case StudySuccess:
                            if (!isStudy) {
                                return;
                            }
                            isStudy = false;
                            animStudy.stopAnim();
                            rcActivity.dismiss();
                            if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof RemoteCtrl) {
                                RemoteCtrl ctrl = (RemoteCtrl) ykMessage.getData();
                                Yaokan.instance().updateRc(ctrl);
                                if (!TextUtils.isEmpty(ykMessage.getMsg())) {
                                    RcCmd cmd = new RcCmd();
                                    cmd.setValue(ykMessage.getMsg());
                                    if (!map.contains(cmd)) {
                                        cmd.setUuid(ctrl.getUuid());
                                        cmd.save();
                                        map.add(cmd);
                                        if (rcActivity != null && !rcActivity.isFinishing()) {
                                            if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof RemoteCtrl) {
                                                rcActivity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        setKeyBackground();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                                if (countDownTime != null) {
                                    countDownTime.cancel();
                                }
                                rcActivity.setStudyTips(R.string.study_suc);
                            }
                            break;
                        case SendCodeResponse:
                            Logger.e(ykMessage.toString());
                            break;
                    }
                }
            });
        }
    }

    public void saveRc() {
        if (animStudy != null) {
            animStudy.stopAnim();
            if (countDownTime != null) {
                countDownTime.cancel();
            }
            isStudy = false;
            if ("0".equals(rc.getRf())) {
                Yaokan.instance().stopStudy(Config.DID);
            } else {
                Yaokan.instance().stopStudyRf(Config.DID, rc);
            }
        }
        if (rc.getId() == 0 && "1".equals(rc.getRf())) {
            rc.setMac(Config.MAC);
            rc.setName(Config.curBName + Config.curTName);
            rcActivity.showDlg();
            Yaokan.instance().uploadRfAndSave(Config.MAC, rc);
        }
    }

    protected void study(boolean isRf, String key, View v) {
        if (isStudy) {
            return;
        }
        if (isRf) {
            if (rc.getId() == 0) {
                rc.setMac(Config.MAC);
                rc.setName(Config.curBName + Config.curTName);
                Yaokan.instance().saveRc(rc);
            }
            Yaokan.instance().studyRf(Config.DID, rc, key);
        } else {
            Yaokan.instance().study(Config.DID, rc, key);
        }
        rcActivity.setStudyTips(R.string.study_ing);
        animStudy.startAnim(v);
        newCountDownTime();
        isStudy = true;
    }

    protected boolean isStudyMode() {
        return rcActivity.getActivityType() == Config.TYPE_RC_STUDY || rcActivity.getActivityType() == Config.TYPE_RC_RF_MATCH_STUDY;
    }

    protected void sendCmd(String key) {
        if (isStudy) {
            Logger.e("sendCmd a");
            return;
        }
        Logger.e("sendCmd:"+key);
        RcCmd cmd = new RcCmd();
        cmd.setValue(key);
        if (!map.contains(cmd)) {
            return;
        }
        if (rc != null && !TextUtils.isEmpty(rc.getMac())) {
            String mac = rc.getMac();
            if (!TextUtils.isEmpty(mac)) {
                String did = Yaokan.instance().getDid(mac);
                if (!Yaokan.instance().isDeviceOnline(mac)) {
                    rcActivity.onReceiveMsg(MsgType.DeviceOffline, null);
                    return;
                } else {
                    Config.DID = did;
                }
            }
        }
        if (Config.IS_MATCH) {
            if (TextUtils.isEmpty(rc.getStudyId())) {
                Yaokan.instance().sendCmd(Config.DID, rc.getRid(), key, rc.getBe_rc_type(), null, null);
            } else {
                Yaokan.instance().sendCmd(Config.DID, rc.getRid(), key, rc.getBe_rc_type(), rc.getStudyId(), rc.getRf());
            }
        } else {
            Yaokan.instance().sendCmd(Config.DID, rc.getRid(), key, rc.getBe_rc_type(), rc.getStudyId(), rc.getRf());
        }
    }

    protected void toast(String s) {
        ToastUtils.showToastFree(getActivity(), s);
    }

    protected void toast(int s) {
        toast(getString(s));
    }

    protected void setBtnTypeface(TextView view, String t) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),
                "iconfont.ttf");
        view.setTypeface(typeface);
        view.setText(t);
    }

    protected void KeyBackground(ImageButton btn, int bg, String key, List<RcCmd> map) {
        if (btn == null) {
            return;
        }
        if (!Utility.isEmpty(map)) {
            RcCmd cmd = new RcCmd();
            cmd.setValue(key);
            if (map.contains(cmd)) {
                if (Build.VERSION.SDK_INT >= 21) {
                    btn.setImageTintList(null);
                }
                btn.setBackgroundResource(bg);
            } else {
                if (Build.VERSION.SDK_INT >= 21) {
                    btn.setImageTintList(getResources().getColorStateList(android.R.color.darker_gray));
                }
            }
        } else {
            btn.setBackgroundResource(R.drawable.shape_circle_white);
            if (Build.VERSION.SDK_INT >= 21) {
                btn.setImageTintList(getResources().getColorStateList(android.R.color.darker_gray));
            }
        }
    }

    protected void KeyBackground(ImageButton btn, String key, List<RcCmd> map) {
        if (btn == null) {
            return;
        }
        if (!Utility.isEmpty(map)) {
            RcCmd cmd = new RcCmd();
            cmd.setValue(key);
            if (map.contains(cmd)) {
                if (Build.VERSION.SDK_INT >= 21) {
                    btn.setImageTintList(null);
                }
                btn.setBackgroundResource(R.drawable.btn_circle);
                if ("play".equals(key)) {
                    btn.setBackgroundResource(R.drawable.btn_trac_play);
                } else if ("power".equals(key)) {
                    btn.setBackgroundResource(R.drawable.bg_matching);
                }
            } else {
                btn.setBackgroundResource(R.drawable.shape_circle_white);
                if ("play".equals(key)) {
                    btn.setBackgroundResource(R.drawable.shape_cir_white_30);
                } else if ("power".equals(key)) {
                    btn.setBackgroundResource(R.drawable.shape_matching_btn);
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    btn.setImageTintList(getResources().getColorStateList(android.R.color.darker_gray));
                }
            }
        } else {
            btn.setBackgroundResource(R.drawable.shape_circle_white);
            if ("play".equals(key)) {
                btn.setBackgroundResource(R.drawable.shape_cir_white_30);
            } else if ("power".equals(key)) {
                btn.setBackgroundResource(R.drawable.shape_matching_btn);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                btn.setImageTintList(getResources().getColorStateList(android.R.color.darker_gray));
            }
        }
    }

    protected void KeyBackground(Button btn, String key, List<RcCmd> map) {
        if (btn == null) {
            return;
        }
        if (!Utility.isEmpty(map)) {
            RcCmd cmd = new RcCmd();
            cmd.setValue(key);
            if (map.contains(cmd)) {
                btn.setTextColor(getResources().getColorStateList(R.drawable.rc_btn_text));
                btn.setBackgroundResource(R.drawable.btn_circle);
            } else {
                btn.setTextColor(getResources().getColor(android.R.color.darker_gray));
                btn.setBackgroundResource(R.drawable.shape_circle_white);
            }
        } else {
            btn.setBackgroundResource(R.drawable.shape_circle_white);
        }
    }

    protected void KeyBackground(TextView btn, String key, List<RcCmd> map) {
        if (btn == null) {
            return;
        }
        if (!Utility.isEmpty(map)) {
            RcCmd cmd = new RcCmd();
            cmd.setValue(key);
            if (map.contains(cmd)) {
                btn.setBackgroundResource(R.drawable.app_square);
                btn.setTextColor(getResources().getColorStateList(R.drawable.rc_btn_text));
                if (Build.VERSION.SDK_INT >= 23) {
                    btn.setCompoundDrawableTintList(null);
                }
            } else {
                btn.setBackgroundResource(R.mipmap.yk_ctrl_unselected_app_square);
                btn.setTextColor(getResources().getColor(android.R.color.darker_gray));
                if (Build.VERSION.SDK_INT >= 23) {
                    btn.setCompoundDrawableTintList(getResources().getColorStateList(android.R.color.darker_gray));
                }
            }
        } else {
            btn.setBackgroundResource(R.mipmap.yk_ctrl_unselected_app_square);
            btn.setTextColor(getResources().getColor(android.R.color.darker_gray));
            if (Build.VERSION.SDK_INT >= 23) {
                btn.setCompoundDrawableTintList(getResources().getColorStateList(android.R.color.darker_gray));
            }
        }
    }

    protected void KeyBackground(RelativeLayout btn, int bgresid, String key, List<RcCmd> map) {
        if (!Utility.isEmpty(map)) {
            RcCmd cmd = new RcCmd();
            cmd.setValue(key);
            if (map.contains(cmd)) {
                btn.setVisibility(View.VISIBLE);
            }
        } else {
            btn.setVisibility(View.GONE);
        }
    }

    protected void KeyBackground(RelativeLayout btn, TextView textView, String key, List<RcCmd> map) {
        if (!Utility.isEmpty(map)) {
            RcCmd cmd = new RcCmd();
            cmd.setValue(key);
            if (map.contains(cmd)) {
                btn.setVisibility(View.VISIBLE);
                btn.setClickable(true);
                textView.setTextColor(getResources().getColor(R.color.black));
            } else {
                btn.setClickable(false);
                textView.setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
        } else {
            btn.setVisibility(View.GONE);
        }
    }

    protected void KeyBackground(TextView btn, int bg, int d_bg, String key, List<RcCmd> map) {
        if (btn == null) {
            return;
        }
        if (!Utility.isEmpty(map)) {
            RcCmd cmd = new RcCmd();
            cmd.setValue(key);
            if (map.contains(cmd)) {
                btn.setBackgroundResource(bg);
                btn.setTextColor(getResources().getColorStateList(R.drawable.rc_btn_text));
            } else {
                btn.setBackgroundResource(d_bg);
                btn.setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
        } else {
            btn.setBackgroundResource(d_bg);
            btn.setTextColor(getResources().getColor(android.R.color.darker_gray));
        }
    }
}
