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
import com.yaokantv.yaokanui.key.DVDRemoteControlDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;

public class RcDVDFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {


    private ImageButton menuBtn, backBtn, muteBtn;
    private ImageButton leftBtn, rightBtn, upBtn, downBtn;

    private ImageButton switchBtn, stopBtn, playBtn, pauseBtn, ffBtn, preBtn, nextBtn, frBtn;

    RelativeLayout okBtn;

    private TextView stbPower;

    private ImageButton volumeAddBtn, volumeReduceBtn;

    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_dvd, null);
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

        upBtn = view.findViewById(R.id.btn_up);
        leftBtn = view.findViewById(R.id.btn_left);
        downBtn = view.findViewById(R.id.btn_down);
        rightBtn = view.findViewById(R.id.btn_right);
        okBtn = view.findViewById(R.id.btn_ok);
        muteBtn = view.findViewById(R.id.btn_mute);

        switchBtn = view.findViewById(R.id.btn_switch);
        stopBtn = view.findViewById(R.id.btn_stop);
        playBtn = view.findViewById(R.id.btn_play);
        pauseBtn = view.findViewById(R.id.btn_pause);
        ffBtn = view.findViewById(R.id.btn_ff);
        preBtn = view.findViewById(R.id.btn_pre);
        nextBtn = view.findViewById(R.id.btn_next);
        frBtn = view.findViewById(R.id.btn_fr);

        expandGridView = view.findViewById(R.id.gv);
        stbPower = view.findViewById(R.id.dvd_power);
        volumeAddBtn = view.findViewById(R.id.btn_vol_add);
        volumeReduceBtn = view.findViewById(R.id.btn_vol_sub);


        stbPower.setTag(StringUtils.DRA_SQUARE);

        switchBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        muteBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        volumeAddBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        volumeReduceBtn.setTag(StringUtils.DRA_BTN_CIRCLE);

        stopBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        pauseBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        playBtn.setTag(StringUtils.DRA_PLAY);

        preBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        ffBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        nextBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        frBtn.setTag(StringUtils.DRA_BTN_CIRCLE);

        menuBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        backBtn.setTag(StringUtils.DRA_BTN_CIRCLE);

        upBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        downBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        leftBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        rightBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        okBtn.setTag(StringUtils.DRA_BTN_CIRCLE);

    }

    private void binderEvent() {
        stbPower.setOnClickListener(this);
        menuBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        upBtn.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        muteBtn.setOnClickListener(this);

        switchBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        ffBtn.setOnClickListener(this);
        preBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        frBtn.setOnClickListener(this);

        volumeAddBtn.setOnClickListener(this);
        volumeReduceBtn.setOnClickListener(this);

        stbPower.setOnLongClickListener(this);
        menuBtn.setOnLongClickListener(this);
        backBtn.setOnLongClickListener(this);
        okBtn.setOnLongClickListener(this);
        upBtn.setOnLongClickListener(this);
        leftBtn.setOnLongClickListener(this);
        downBtn.setOnLongClickListener(this);
        rightBtn.setOnLongClickListener(this);
        muteBtn.setOnLongClickListener(this);

        switchBtn.setOnLongClickListener(this);
        stopBtn.setOnLongClickListener(this);
        playBtn.setOnLongClickListener(this);
        pauseBtn.setOnLongClickListener(this);
        ffBtn.setOnLongClickListener(this);
        preBtn.setOnLongClickListener(this);
        nextBtn.setOnLongClickListener(this);
        frBtn.setOnLongClickListener(this);

        volumeAddBtn.setOnLongClickListener(this);
        volumeReduceBtn.setOnLongClickListener(this);
        bindGvEvent();
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(volumeAddBtn, DVDRemoteControlDataKey.VOLUME_ADD.getKey(), map);
        KeyBackground(volumeReduceBtn, DVDRemoteControlDataKey.VOLUME_SUB.getKey(), map);

        KeyBackground(menuBtn, DVDRemoteControlDataKey.MENU.getKey(), map);
        KeyBackground(muteBtn, DVDRemoteControlDataKey.MUTE.getKey(), map);
        KeyBackground(backBtn, DVDRemoteControlDataKey.BACK.getKey(), map);

        KeyBackground(switchBtn, DVDRemoteControlDataKey.SWICTH.getKey(), map);
        KeyBackground(stopBtn, DVDRemoteControlDataKey.STOP.getKey(), map);
        KeyBackground(playBtn, DVDRemoteControlDataKey.PLAY.getKey(), map);
        KeyBackground(pauseBtn, DVDRemoteControlDataKey.PAUSE.getKey(), map);
        KeyBackground(ffBtn, DVDRemoteControlDataKey.FF.getKey(), map);
        KeyBackground(preBtn, DVDRemoteControlDataKey.PRE.getKey(), map);
        KeyBackground(nextBtn, DVDRemoteControlDataKey.NEXT.getKey(), map);
        KeyBackground(frBtn, DVDRemoteControlDataKey.REW.getKey(), map);

        KeyBackground(stbPower, DVDRemoteControlDataKey.POWER.getKey(), map);

        KeyBackground(upBtn, DVDRemoteControlDataKey.UP.getKey(), map);
        KeyBackground(leftBtn, DVDRemoteControlDataKey.LEFT.getKey(), map);
        KeyBackground(downBtn, DVDRemoteControlDataKey.DOWN.getKey(), map);
        KeyBackground(rightBtn, DVDRemoteControlDataKey.RIGHT.getKey(), map);
        KeyBackground(okBtn, R.drawable.btn_non, DVDRemoteControlDataKey.OK.getKey(), map);
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
        if (id == R.id.dvd_power) {
            key = DVDRemoteControlDataKey.POWER.getKey();
        } else if (id == R.id.btn_menu) {
            key = DVDRemoteControlDataKey.MENU.getKey();
        } else if (id == R.id.btn_mute) {
            key = DVDRemoteControlDataKey.MUTE.getKey();
        } else if (id == R.id.btn_back) {
            key = DVDRemoteControlDataKey.BACK.getKey();
        } else if (id == R.id.btn_up) {
            key = DVDRemoteControlDataKey.UP.getKey();
        } else if (id == R.id.btn_left) {
            key = DVDRemoteControlDataKey.LEFT.getKey();
        } else if (id == R.id.btn_down) {
            key = DVDRemoteControlDataKey.DOWN.getKey();
        } else if (id == R.id.btn_right) {
            key = DVDRemoteControlDataKey.RIGHT.getKey();
        } else if (id == R.id.btn_ok) {
            key = DVDRemoteControlDataKey.OK.getKey();
        } else if (id == R.id.dvd_power) {
            key = DVDRemoteControlDataKey.POWER.getKey();
        } else if (id == R.id.btn_vol_add) {
            key = DVDRemoteControlDataKey.VOLUME_ADD.getKey();
        } else if (id == R.id.btn_vol_sub) {
            key = DVDRemoteControlDataKey.VOLUME_SUB.getKey();
        } else if (id == R.id.btn_play) {
            key = DVDRemoteControlDataKey.PLAY.getKey();
        } else if (id == R.id.btn_fr) {
            key = DVDRemoteControlDataKey.REW.getKey();
        } else if (id == R.id.btn_switch) {
            key = DVDRemoteControlDataKey.SWICTH.getKey();
        } else if (id == R.id.btn_stop) {
            key = DVDRemoteControlDataKey.STOP.getKey();
        } else if (id == R.id.btn_pause) {
            key = DVDRemoteControlDataKey.PAUSE.getKey();
        } else if (id == R.id.btn_pre) {
            key = DVDRemoteControlDataKey.PRE.getKey();
        } else if (id == R.id.btn_next) {
            key = DVDRemoteControlDataKey.NEXT.getKey();
        } else if (id == R.id.btn_ff) {
            key = DVDRemoteControlDataKey.FF.getKey();
        }
    }
}