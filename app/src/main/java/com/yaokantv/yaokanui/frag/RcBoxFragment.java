package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.BoxRemoteControlDataKey;
import com.yaokantv.yaokanui.key.BoxRemoteControlDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;
import com.yaokantv.yaokanui.widget.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

public class RcBoxFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {

    private ImageButton menuBtn, backBtn, muteBtn;
    private ImageButton leftBtn, rightBtn, upBtn, downBtn;
    private ImageButton bootBtn;
    RelativeLayout okBtn;

    private TextView powerOn, signal;

    private ImageButton volumeAddBtn, volumeReduceBtn;

    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_box, null);
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
        bootBtn = view.findViewById(R.id.btn_boot);

        powerOn = view.findViewById(R.id.pro_power_on);
        signal = view.findViewById(R.id.sigal);

        expandGridView = view.findViewById(R.id.gv);
        volumeAddBtn = view.findViewById(R.id.btn_vol_add);
        volumeReduceBtn = view.findViewById(R.id.btn_vol_sub);


        powerOn.setTag(StringUtils.DRA_SQUARE);
        signal.setTag(StringUtils.DRA_SQUARE);

        volumeAddBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        volumeReduceBtn.setTag(StringUtils.DRA_BTN_CIRCLE);

        menuBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        backBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        muteBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        bootBtn.setTag(StringUtils.DRA_BTN_CIRCLE);

        upBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        downBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        leftBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        rightBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        okBtn.setTag(StringUtils.DRA_BTN_CIRCLE);

    }

    private void binderEvent() {
        powerOn.setOnClickListener(this);
        signal.setOnClickListener(this);

        menuBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        upBtn.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        muteBtn.setOnClickListener(this);
        bootBtn.setOnClickListener(this);

        volumeAddBtn.setOnClickListener(this);
        volumeReduceBtn.setOnClickListener(this);

        powerOn.setOnLongClickListener(this);
        signal.setOnLongClickListener(this);

        menuBtn.setOnLongClickListener(this);
        backBtn.setOnLongClickListener(this);
        okBtn.setOnLongClickListener(this);
        upBtn.setOnLongClickListener(this);
        leftBtn.setOnLongClickListener(this);
        downBtn.setOnLongClickListener(this);
        rightBtn.setOnLongClickListener(this);
        muteBtn.setOnLongClickListener(this);
        bootBtn.setOnLongClickListener(this);

        volumeAddBtn.setOnLongClickListener(this);
        volumeReduceBtn.setOnLongClickListener(this);
        bindGvEvent();
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(volumeAddBtn, BoxRemoteControlDataKey.VOLUME_ADD.getKey(), map);
        KeyBackground(volumeReduceBtn, BoxRemoteControlDataKey.VOLUME_SUB.getKey(), map);

        KeyBackground(menuBtn, BoxRemoteControlDataKey.MENU.getKey(), map);
        KeyBackground(muteBtn, BoxRemoteControlDataKey.MUTE.getKey(), map);
        KeyBackground(backBtn, BoxRemoteControlDataKey.BACK.getKey(), map);
        KeyBackground(bootBtn, BoxRemoteControlDataKey.BACK.getKey(), map);

        KeyBackground(powerOn, BoxRemoteControlDataKey.POWER.getKey(), map);
        KeyBackground(signal, BoxRemoteControlDataKey.SIGNAL.getKey(), map);

        KeyBackground(upBtn, BoxRemoteControlDataKey.UP.getKey(), map);
        KeyBackground(leftBtn, BoxRemoteControlDataKey.LEFT.getKey(), map);
        KeyBackground(downBtn, BoxRemoteControlDataKey.DOWN.getKey(), map);
        KeyBackground(rightBtn, BoxRemoteControlDataKey.RIGHT.getKey(), map);
        KeyBackground(okBtn, R.drawable.btn_non, BoxRemoteControlDataKey.OK.getKey(), map);
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
            key = BoxRemoteControlDataKey.POWER.getKey();
        } else if (id == R.id.btn_menu) {
            key = BoxRemoteControlDataKey.MENU.getKey();
        } else if (id == R.id.btn_mute) {
            key = BoxRemoteControlDataKey.MUTE.getKey();
        } else if (id == R.id.btn_back) {
            key = BoxRemoteControlDataKey.BACK.getKey();
        } else if (id == R.id.btn_boot) {
            key = BoxRemoteControlDataKey.BOOT.getKey();
        } else if (id == R.id.btn_up) {
            key = BoxRemoteControlDataKey.UP.getKey();
        } else if (id == R.id.btn_left) {
            key = BoxRemoteControlDataKey.LEFT.getKey();
        } else if (id == R.id.btn_down) {
            key = BoxRemoteControlDataKey.DOWN.getKey();
        } else if (id == R.id.btn_right) {
            key = BoxRemoteControlDataKey.RIGHT.getKey();
        } else if (id == R.id.btn_ok) {
            key = BoxRemoteControlDataKey.OK.getKey();
        } else if (id == R.id.sigal) {
            key = BoxRemoteControlDataKey.SIGNAL.getKey();
        } else if (id == R.id.btn_vol_add) {
            key = BoxRemoteControlDataKey.VOLUME_ADD.getKey();
        } else if (id == R.id.btn_vol_sub) {
            key = BoxRemoteControlDataKey.VOLUME_SUB.getKey();
        }
    }
}