package com.yaokantv.yaokanui.frag;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.AirOrder;
import com.yaokantv.yaokansdk.model.AirStatus;
import com.yaokantv.yaokansdk.model.Mode;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.Swing;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.Logger;
import com.yaokantv.yaokanui.Config;
import com.yaokantv.yaokanui.RcActivity;

import java.util.ArrayList;
import java.util.List;

public class RcAirFragment extends BaseRcFragment implements View.OnClickListener {

    RcActivity activity;
    TextView tvTemp;
    ImageView ivMode, ivSpeed, ivU, ivL;
    RelativeLayout mode, speed, u, l;
    ImageButton tempDown, tempUp, power;
    View mView;

    List<String> modeList = new ArrayList<>();
    List<String> speedList = new ArrayList<>();
    List<String> tempList = new ArrayList<>();
    List<String> verList = new ArrayList<>();
    List<String> horList = new ArrayList<>();
    String curMode, curSpeed, curTemp, curVer, curHor;
    int modeIndex, speedIndex, tempIndex, verIndex, horIndex;
    boolean isOpen = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_air, null);
        mView = v;
        activity = (RcActivity) getActivity();
        initView(v);
        return v;
    }

    private void saveAirStatus() {
        if (rc == null || TextUtils.isEmpty(rc.getUuid())) {
            return;
        }
        int open = isOpen ? 1 : 0;
        String s = open + "_" + modeIndex + "_" + speedIndex + "_" + tempIndex + "_" + verIndex + "_" + horIndex;
        rc.setAir_status_index(s);
        Yaokan.instance().updateRc(rc);
        dataUtils.setKeyValue(rc.getUuid(), s);
    }

    @Override
    public void refreshRcData(RemoteCtrl rc) {
        this.rc = rc;
        modeList.clear();
        for (String mode : rc.getAirCmd().getMode()) {
            modeList.add(mode);
        }
        if (modeList.size() > 0) {
            curMode = modeList.get(0);
        }
        if (rc != null && !TextUtils.isEmpty(rc.getUuid())) {
            String status = dataUtils.getKeyStringValue(rc.getUuid());
            if (!TextUtils.isEmpty(status)) {
                String arr[] = status.split("_");
                if (arr != null && arr.length == 6) {
                    isOpen = (Integer.valueOf(arr[0]) == 1);
                    modeIndex = Integer.valueOf(arr[1]);
                    speedIndex = Integer.valueOf(arr[2]);
                    tempIndex = Integer.valueOf(arr[3]);
                    verIndex = Integer.valueOf(arr[4]);
                    horIndex = Integer.valueOf(arr[5]);

                    if (modeIndex >= modeList.size()) {
                        modeIndex = 0;
                    } else {
                        curMode = modeList.get(modeIndex);
                    }
                }
            }
        }
        refreshView();
        mView.findViewById(R.id.ll_op).setVisibility(isOpen ? View.VISIBLE : View.GONE);
        mView.findViewById(R.id.tv_temp).setVisibility(isOpen ? View.VISIBLE : View.GONE);
        binderEvent();
    }

    @Override
    public void setKeyBackground() {

    }

    protected void initView(View view) {
        power = view.findViewById(R.id.power);
        tempDown = view.findViewById(R.id.temp_down);
        tempUp = view.findViewById(R.id.temp_up);
        mode = view.findViewById(R.id.rl_mode);
        speed = view.findViewById(R.id.rl_wind);
        u = view.findViewById(R.id.rl_u);
        l = view.findViewById(R.id.rl_l);
        tvTemp = view.findViewById(R.id.tv_temp);

        ivMode = view.findViewById(R.id.iv_mode);
        ivSpeed = view.findViewById(R.id.iv_wind);
        ivU = view.findViewById(R.id.iv_u);
        ivL = view.findViewById(R.id.iv_l);

    }

    private void binderEvent() {
        power.setOnClickListener(this);
        tempDown.setOnClickListener(this);
        tempUp.setOnClickListener(this);
        mode.setOnClickListener(this);
        speed.setOnClickListener(this);
        u.setOnClickListener(this);
        l.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.power) {
            isOpen = !isOpen;
            mView.findViewById(R.id.ll_op).setVisibility(isOpen ? View.VISIBLE : View.GONE);
            mView.findViewById(R.id.tv_temp).setVisibility(isOpen ? View.VISIBLE : View.GONE);
            Yaokan.instance().sendCmd(Config.DID, rc.getRid(), isOpen ? "on" : "off", rc.getBe_rc_type(), null, null);
        } else if (id == R.id.temp_down) {
            if (!isOpen) {
                toast(R.string.open_air_first);
                return;
            }
            tempIndex--;
            if (tempIndex < 0) {
                tempIndex = 0;
            }
            curTemp = tempList.get(tempIndex);
            sendCmd();
        } else if (id == R.id.temp_up) {
            if (!isOpen) {
                toast(R.string.open_air_first);
                return;
            }
            tempIndex++;
            if (tempIndex >= tempList.size()) {
                tempIndex = tempList.size() - 1;
            }
            curTemp = tempList.get(tempIndex);
            sendCmd();
        } else if (id == R.id.rl_mode) {
            if (!isOpen) {
                toast(R.string.open_air_first);
                return;
            }
            modeIndex++;
            if (modeIndex >= modeList.size()) {
                modeIndex = 0;
            }
            curMode = modeList.get(modeIndex);
            refreshView();
            sendCmd();
        } else if (id == R.id.rl_wind) {
            if (!isOpen) {
                toast(R.string.open_air_first);
                return;
            }
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
        } else if (id == R.id.rl_u) {
            if (!isOpen) {
                toast(R.string.open_air_first);
                return;
            }
            if (rc.getAirCmd().getAttributes().getVerticalIndependent() == 1) {
                verIndex++;
                boolean isOpen = (verIndex % 2 == 1);
                Yaokan.instance().sendAirCmd(Config.DID, rc.getRid(), Swing.Ver, isOpen);
                getShowText(isOpen ? "verOn" : "verOff");
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
        } else if (id == R.id.rl_l) {
            if (!isOpen) {
                toast(R.string.open_air_first);
                return;
            }
            if (rc.getAirCmd().getAttributes().getVerticalIndependent() == 1) {
                horIndex++;
                boolean isOpen = (horIndex % 2 == 1);
                Yaokan.instance().sendAirCmd(Config.DID, rc.getRid(), Swing.Hor, isOpen);
                getShowText(isOpen ? "horOn" : "horOff");
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
        }
        saveAirStatus();
    }

    private void sendCmd() {
        Yaokan.instance().sendAirCmd(Config.DID, rc.getRid(), new AirOrder(curMode, curSpeed, curTemp, curVer, curHor));
        setText();
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
                    setViewStatus(ivSpeed, true);
                } else {
                    setViewStatus(ivSpeed, false);
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
                    setViewStatus(tvTemp, true);
                    setViewStatus(tempDown, true);
                    setViewStatus(tempUp, true);
                } else {
                    curTemp = "";
                    setViewStatus(tvTemp, false);
                    setViewStatus(tempDown, false);
                    setViewStatus(tempUp, false);
                }
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
                    setViewStatus(ivU, true);
                } else {
                    if (rc.getAirCmd().getAttributes().getVerticalIndependent() == 1) {
                        setViewStatus(ivU, true);
                    } else {
                        setViewStatus(ivU, false);
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
                    setViewStatus(ivL, true);
                } else {
                    if (rc.getAirCmd().getAttributes().getHorizontalIndependent() == 1) {
                        setViewStatus(ivL, true);
                    } else {
                        setViewStatus(ivL, false);
                    }
                    curHor = "";
                }
            }
            setText();
        }
    }

    void setText() {
        getShowText(curMode);
        getShowText(curSpeed);
        ((TextView) mView.findViewById(R.id.tv_temp)).setText(curTemp);
        if (rc.getAirCmd().getAttributes().getVerticalIndependent() == 0) {
            getShowText(curVer);
            getShowText(curHor);
        }
    }

    String getShowText(String s) {
        String text = "";
        switch (s) {
            case "cold":
                text = "制冷";
                ivMode.setImageResource(R.mipmap.mode_c);
                break;
            case "hot":
                text = "制热";
                ivMode.setImageResource(R.mipmap.mode_h);
                break;
            case "auto":
                text = "自动";
                ivMode.setImageResource(R.mipmap.mode_a);
                break;
            case "dry":
                text = "抽湿";
                ivMode.setImageResource(R.mipmap.mode_d);
                break;
            case "wind":
                text = "送风";
                ivMode.setImageResource(R.mipmap.mode_w);
                break;
            case "0":
                text = "风速自动";
                ivSpeed.setImageResource(R.mipmap.wind_a);
                break;
            case "1":
                text = "风速1";
                ivSpeed.setImageResource(R.mipmap.wind1);
                break;
            case "2":
                text = "风速2";
                ivSpeed.setImageResource(R.mipmap.wind2);
                break;
            case "3":
                text = "风速3";
                ivSpeed.setImageResource(R.mipmap.wind3);
                break;
            case "verticalOn":
            case "verOn":
                text = "上下扫风开";
                ivU.setImageResource(R.mipmap.sweep_u_on);
                break;
            case "verticalOff":
            case "verOff":
                text = "上下扫风关";
                ivU.setImageResource(R.mipmap.sweep_u_off);
                break;
            case "horizontalOn":
            case "horOn":
                text = "左右扫风开";
                ivL.setImageResource(R.mipmap.sweep_l_on);
                break;
            case "horizontalOff":
            case "horOff":
                text = "左右扫风关";
                ivL.setImageResource(R.mipmap.sweep_l_off);
                break;
        }
        return text;
    }

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        super.onReceiveMsg(msgType, ykMessage);
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (msgType) {
                        case CtrlStatus:
                            try {
                                AirStatus airStatus = (AirStatus) ykMessage.getData();
                                if (airStatus != null) {
                                    isOpen = airStatus.isOpen();
                                    mView.findViewById(R.id.ll_op).setVisibility(isOpen ? View.VISIBLE : View.GONE);
                                    mView.findViewById(R.id.tv_temp).setVisibility(isOpen ? View.VISIBLE : View.GONE);
                                    if (isOpen) {
                                        if (modeList.contains(airStatus.getStatus()[0])) {
                                            modeIndex = modeList.indexOf(airStatus.getStatus()[0]);
                                            curMode = modeList.get(modeIndex);
                                        }
                                        if (speedList.contains(airStatus.getStatus()[1])) {
                                            speedIndex = speedList.indexOf(airStatus.getStatus()[1]);
                                            curSpeed = speedList.get(speedIndex);
                                        }
                                        if (tempList.contains(airStatus.getStatus()[2])) {
                                            tempIndex = tempList.indexOf(airStatus.getStatus()[2]);
                                            curTemp = tempList.get(tempIndex);
                                        }
                                        if (!TextUtils.isEmpty(airStatus.getStatus()[3]) && !"*".equals(airStatus.getStatus()[3])) {
                                            if (verList.contains(airStatus.getStatus()[3])) {
                                                verIndex = verList.indexOf(airStatus.getStatus()[3]);
                                                curVer = verList.get(verIndex);
                                            }
                                            if (rc.getAirCmd().getAttributes().getVerticalIndependent() == 1) {
                                                boolean isOpen = airStatus.getStatus()[3].contains("On");
                                                verIndex = (isOpen ? 1 : 0);
                                                getShowText(isOpen ? "verOn" : "verOff");
                                            }
                                        }
                                        if (!TextUtils.isEmpty(airStatus.getStatus()[4]) && !"*".equals(airStatus.getStatus()[4])) {
                                            if (horList.contains(airStatus.getStatus()[4])) {
                                                horIndex = horList.indexOf(airStatus.getStatus()[4]);
                                                curHor = horList.get(horIndex);
                                            }
                                            if (rc.getAirCmd().getAttributes().getHorizontalIndependent() == 1) {
                                                boolean isOpen = airStatus.getStatus()[4].contains("On");
                                                horIndex = (isOpen ? 1 : 0);
                                                getShowText(isOpen ? "horOn" : "horOff");
                                            }
                                        }
                                        refreshView();
                                        saveAirStatus();
                                    }
                                }
                            } catch (Exception e) {
                                Logger.e(e.getMessage());
                            }
                            break;
                    }
                }
            });
        }
    }

    protected void setViewStatus(View v, boolean isEnable) {
        if (v != null) {
            if (v instanceof ImageButton) {
                v.setEnabled(isEnable);
                v.setFocusable(isEnable);
                v.setClickable(isEnable);
                if (Build.VERSION.SDK_INT >= 21) {
                    ((ImageButton) v).setImageTintList(isEnable ? null : getResources().getColorStateList(android.R.color.darker_gray));
                }
                return;
            }

            if (v.getId() == R.id.iv_wind) {
                ((TextView) mView.findViewById(R.id.tv_wind)).setTextColor(getResources().getColor(isEnable ? R.color.black : R.color.top_gray));
            } else if (v.getId() == R.id.iv_u) {
                ((TextView) mView.findViewById(R.id.tv_u)).setTextColor(getResources().getColor(isEnable ? R.color.black : R.color.top_gray));
            } else if (v.getId() == R.id.iv_l) {
                ((TextView) mView.findViewById(R.id.tv_l)).setTextColor(getResources().getColor(isEnable ? R.color.black : R.color.top_gray));
            }
            if (v instanceof Button) {

            } else if (v instanceof TextView) {
                if (!isEnable) {
                    ((TextView) v).setText("--");
                }
            }
            v.setVisibility(isEnable ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
