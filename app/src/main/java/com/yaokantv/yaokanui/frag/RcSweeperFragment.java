package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RemoteCtrl;

import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.SweeperRemoteControlDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;

public class RcSweeperFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {


    private ImageButton leftBtn, rightBtn, btnCharge, btnPause;

    private ImageButton upBtn, downBtn;

    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_sweeper, null);
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
        upBtn = view.findViewById(R.id.btn_up);
        leftBtn = view.findViewById(R.id.btn_left);
        downBtn = view.findViewById(R.id.btn_down);
        rightBtn = view.findViewById(R.id.btn_right);

        btnCharge = view.findViewById(R.id.btn_charge);
        btnPause = view.findViewById(R.id.btn_pause);

        expandGridView = view.findViewById(R.id.gv);

        upBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        leftBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        downBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        rightBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        btnCharge.setTag(StringUtils.DRA_BTN_CIRCLE);
        btnPause.setTag(StringUtils.DRA_BTN_CIRCLE);

    }

    private void binderEvent() {
        upBtn.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);

        btnCharge.setOnClickListener(this);
        btnPause.setOnClickListener(this);

        upBtn.setOnLongClickListener(this);
        leftBtn.setOnLongClickListener(this);
        downBtn.setOnLongClickListener(this);
        rightBtn.setOnLongClickListener(this);

        btnCharge.setOnLongClickListener(this);
        btnPause.setOnLongClickListener(this);
        bindGvEvent();
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(btnCharge, SweeperRemoteControlDataKey.CHARGE.getKey(), map);
        KeyBackground(btnPause, SweeperRemoteControlDataKey.PAUSE.getKey(), map);

        KeyBackground(upBtn, SweeperRemoteControlDataKey.UP.getKey(), map);
        KeyBackground(leftBtn, SweeperRemoteControlDataKey.LEFT.getKey(), map);
        KeyBackground(downBtn, SweeperRemoteControlDataKey.DOWN.getKey(), map);
        KeyBackground(rightBtn, SweeperRemoteControlDataKey.RIGHT.getKey(), map);
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
        if (id == R.id.btn_pause) {
            key = SweeperRemoteControlDataKey.PAUSE.getKey();
        } else if (id == R.id.btn_up) {
            key = SweeperRemoteControlDataKey.UP.getKey();
        } else if (id == R.id.btn_left) {
            key = SweeperRemoteControlDataKey.LEFT.getKey();
        } else if (id == R.id.btn_down) {
            key = SweeperRemoteControlDataKey.DOWN.getKey();
        } else if (id == R.id.btn_right) {
            key = SweeperRemoteControlDataKey.RIGHT.getKey();
        } else if (id == R.id.btn_charge) {
            key = SweeperRemoteControlDataKey.CHARGE.getKey();
        }
    }
}
