package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RemoteCtrl;

import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.ProjectorRemoteControlDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;

public class RcProjectorFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {

    private ImageButton menuBtn, backBtn, muteBtn;
    /**
     * 上,下,左,右,ok按钮
     */
    private ImageButton leftBtn, rightBtn, upBtn, downBtn;

    RelativeLayout okBtn;

    private TextView powerOn, signal, powerOff;

    private ImageButton volumeAddBtn, volumeReduceBtn, focusAddBtn, focusReduceBtn;

    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_projector, null);
        activity = (RcActivity) getActivity();
        initView(v);
        return v;
    }

    @Override
    public void refreshRcData(RemoteCtrl rc) {
        this.rc = rc;
        super.refreshRcData(rc);
        map.clear();
        map.addAll(rc.getRcCmdAll());
        setKeyBackground();
        mExpandAdapter = new ExpandAdapter(getActivity(), rc, map);
        expandGridView.setAdapter(mExpandAdapter);
        binderEvent();
    }

    protected void initView(View view) {
        menuBtn = view.findViewById(R.id.btn_menu);
        backBtn = view.findViewById(R.id.btn_back);

        // 上,左,下,右,ok按鈕的使初始化
        upBtn = view.findViewById(R.id.btn_up);
        leftBtn = view.findViewById(R.id.btn_left);
        downBtn = view.findViewById(R.id.btn_down);
        rightBtn = view.findViewById(R.id.btn_right);
        okBtn = view.findViewById(R.id.btn_ok);

        muteBtn = view.findViewById(R.id.btn_mute);

        powerOff = view.findViewById(R.id.pro_poweroff);
        powerOn = view.findViewById(R.id.pro_power_on);
        signal = view.findViewById(R.id.sigal);

        expandGridView = view.findViewById(R.id.gv);
        volumeAddBtn = view.findViewById(R.id.btn_vol_add);
        volumeReduceBtn = view.findViewById(R.id.btn_vol_sub);
        focusAddBtn = view.findViewById(R.id.btn_focus_add);
        focusReduceBtn = view.findViewById(R.id.btn_focus_sub);

        powerOn.setTag(StringUtils.DRA_SQUARE);
        signal.setTag(StringUtils.DRA_SQUARE);
        powerOff.setTag(StringUtils.DRA_SQUARE);

        focusAddBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        focusReduceBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        muteBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        volumeAddBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        volumeReduceBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        menuBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        backBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        upBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        downBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        leftBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        rightBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        okBtn.setTag(StringUtils.DRA_BTN_CIRCLE);

    }

    private void binderEvent() {
        powerOn.setOnClickListener(this);
        signal.setOnClickListener(this);
        powerOff.setOnClickListener(this);

        menuBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        upBtn.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        muteBtn.setOnClickListener(this);

        volumeAddBtn.setOnClickListener(this);
        volumeReduceBtn.setOnClickListener(this);
        focusAddBtn.setOnClickListener(this);
        focusReduceBtn.setOnClickListener(this);

        powerOn.setOnLongClickListener(this);
        signal.setOnLongClickListener(this);
        powerOff.setOnLongClickListener(this);

        menuBtn.setOnLongClickListener(this);
        backBtn.setOnLongClickListener(this);
        okBtn.setOnLongClickListener(this);
        upBtn.setOnLongClickListener(this);
        leftBtn.setOnLongClickListener(this);
        downBtn.setOnLongClickListener(this);
        rightBtn.setOnLongClickListener(this);
        muteBtn.setOnLongClickListener(this);

        volumeAddBtn.setOnLongClickListener(this);
        volumeReduceBtn.setOnLongClickListener(this);
        focusAddBtn.setOnLongClickListener(this);
        focusReduceBtn.setOnLongClickListener(this);
        bindGvEvent();
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(focusAddBtn, ProjectorRemoteControlDataKey.FOCUS_ADD.getKey(), map);
        KeyBackground(focusReduceBtn, ProjectorRemoteControlDataKey.FOCUS_SUB.getKey(), map);
        KeyBackground(volumeAddBtn, ProjectorRemoteControlDataKey.VOL_ADD.getKey(), map);
        KeyBackground(volumeReduceBtn, ProjectorRemoteControlDataKey.VOL_SUB.getKey(), map);

        KeyBackground(menuBtn, ProjectorRemoteControlDataKey.MENU.getKey(), map);
        KeyBackground(muteBtn, ProjectorRemoteControlDataKey.MUTE.getKey(), map);
        KeyBackground(backBtn, ProjectorRemoteControlDataKey.BACK.getKey(), map);

        KeyBackground(powerOff, ProjectorRemoteControlDataKey.POWEROFF.getKey(), map);
        KeyBackground(powerOn, ProjectorRemoteControlDataKey.POWER.getKey(), map);
        KeyBackground(signal, ProjectorRemoteControlDataKey.SIGNAL.getKey(), map);

        KeyBackground(upBtn, ProjectorRemoteControlDataKey.UP.getKey(), map);
        KeyBackground(leftBtn, ProjectorRemoteControlDataKey.LEFT.getKey(), map);
        KeyBackground(downBtn, ProjectorRemoteControlDataKey.DOWN.getKey(), map);
        KeyBackground(rightBtn, ProjectorRemoteControlDataKey.RIGHT.getKey(), map);
        KeyBackground(okBtn, R.drawable.btn_non, ProjectorRemoteControlDataKey.OK.getKey(), map);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        setKey(id);
        if (!TextUtils.isEmpty(key)) {
            sendCmd(key);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        if (isStudyMode()) {
            setKey(id);
            study(false, key, v);
        }
        return false;
    }

    private void setKey(int id) {
        if (id == R.id.pro_power_on) {
            key = ProjectorRemoteControlDataKey.POWER.getKey();
        } else if (id == R.id.btn_menu) {
            key = ProjectorRemoteControlDataKey.MENU.getKey();
        } else if (id == R.id.btn_mute) {
            key = ProjectorRemoteControlDataKey.MUTE.getKey();
        } else if (id == R.id.btn_home) {
            key = ProjectorRemoteControlDataKey.BOOT.getKey();
        } else if (id == R.id.btn_exit) {
            key = ProjectorRemoteControlDataKey.EXIT.getKey();
        } else if (id == R.id.btn_back) {
            key = ProjectorRemoteControlDataKey.BACK.getKey();
        } else if (id == R.id.btn_up) {
            key = ProjectorRemoteControlDataKey.UP.getKey();
        } else if (id == R.id.btn_left) {
            key = ProjectorRemoteControlDataKey.LEFT.getKey();
        } else if (id == R.id.btn_down) {
            key = ProjectorRemoteControlDataKey.DOWN.getKey();
        } else if (id == R.id.btn_right) {
            key = ProjectorRemoteControlDataKey.RIGHT.getKey();
        } else if (id == R.id.btn_ok) {
            key = ProjectorRemoteControlDataKey.OK.getKey();
        } else if (id == R.id.sigal) {
            key = ProjectorRemoteControlDataKey.SIGNAL.getKey();
        } else if (id == R.id.btn_vol_add) {
            key = ProjectorRemoteControlDataKey.VOL_ADD.getKey();
        } else if (id == R.id.btn_vol_sub) {
            key = ProjectorRemoteControlDataKey.VOL_SUB.getKey();
        } else if (id == R.id.btn_focus_add) {
            key = ProjectorRemoteControlDataKey.FOCUS_ADD.getKey();
        } else if (id == R.id.btn_focus_sub) {
            key = ProjectorRemoteControlDataKey.FOCUS_SUB.getKey();
        } else if (id == R.id.pro_poweroff) {
            key = ProjectorRemoteControlDataKey.POWEROFF.getKey();
        }
    }
}