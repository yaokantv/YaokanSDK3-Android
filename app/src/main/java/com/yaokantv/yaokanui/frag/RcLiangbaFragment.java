package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;

import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.FannerRemoteControlDataKey;
import com.yaokantv.yaokanui.key.LiangbaRFDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;

import java.util.ArrayList;
import java.util.List;

public class RcLiangbaFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {

    private TextView stop, low, mid, high;
    private ImageButton power, timeDown, timeUp, light, fan;

    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_liangba, null);
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
        stop = view.findViewById(R.id.stop);
        low = view.findViewById(R.id.low);
        mid = view.findViewById(R.id.mid);
        high = view.findViewById(R.id.high);

        timeDown = view.findViewById(R.id.time_down);
        timeUp = view.findViewById(R.id.time_up);

        light = view.findViewById(R.id.light);
        power = view.findViewById(R.id.power);
        fan = view.findViewById(R.id.fan);

        stop.setTag(StringUtils.DRA_BTN_CIRCLE);
        low.setTag(StringUtils.DRA_BTN_CIRCLE);
        mid.setTag(StringUtils.DRA_BTN_CIRCLE);
        high.setTag(StringUtils.DRA_BTN_CIRCLE);
        timeDown.setTag(StringUtils.DRA_BTN_CIRCLE);
        timeUp.setTag(StringUtils.DRA_BTN_CIRCLE);

        light.setTag(StringUtils.DRA_BG_MATCHING);
        power.setTag(StringUtils.DRA_BG_MATCHING);
        fan.setTag(StringUtils.DRA_BG_MATCHING);

        expandGridView = view.findViewById(R.id.gv);
    }

    private void binderEvent() {
        stop.setOnClickListener(this);
        low.setOnClickListener(this);
        mid.setOnClickListener(this);
        high.setOnClickListener(this);

        timeDown.setOnClickListener(this);
        timeUp.setOnClickListener(this);

        light.setOnClickListener(this);
        power.setOnClickListener(this);
        fan.setOnClickListener(this);

        stop.setOnLongClickListener(this);
        low.setOnLongClickListener(this);
        mid.setOnLongClickListener(this);
        high.setOnLongClickListener(this);

        timeDown.setOnLongClickListener(this);
        timeUp.setOnLongClickListener(this);

        light.setOnLongClickListener(this);
        power.setOnLongClickListener(this);
        fan.setOnLongClickListener(this);

        bindGvEventRf();
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(stop, R.drawable.btn_circle, R.drawable.shape_circle_white, LiangbaRFDataKey.STOP.getKey(), map);
        KeyBackground(low, R.drawable.btn_circle, R.drawable.shape_circle_white, LiangbaRFDataKey.LOW.getKey(), map);
        KeyBackground(mid, R.drawable.btn_circle, R.drawable.shape_circle_white, LiangbaRFDataKey.MID.getKey(), map);
        KeyBackground(high, R.drawable.btn_circle, R.drawable.shape_circle_white, LiangbaRFDataKey.HIGH.getKey(), map);

        KeyBackground(timeDown, LiangbaRFDataKey.TIME_DN.getKey(), map);
        KeyBackground(timeUp, LiangbaRFDataKey.TIME_UP.getKey(), map);
        KeyBackground(light,R.drawable.bg_matching, LiangbaRFDataKey.LIGHT.getKey(), map);
        KeyBackground(power,R.drawable.bg_matching, LiangbaRFDataKey.POWER.getKey(), map);
        KeyBackground(fan,R.drawable.bg_matching, LiangbaRFDataKey.SWING.getKey(), map);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.stop) {
            key = LiangbaRFDataKey.STOP.getKey();
        } else if (id == R.id.low) {
            key = LiangbaRFDataKey.LOW.getKey();
        } else if (id == R.id.mid) {
            key = LiangbaRFDataKey.MID.getKey();
        } else if (id == R.id.high) {
            key = LiangbaRFDataKey.HIGH.getKey();
        } else if (id == R.id.time_up) {
            key = LiangbaRFDataKey.TIME_UP.getKey();
        } else if (id == R.id.time_down) {
            key = LiangbaRFDataKey.TIME_DN.getKey();
        } else if (id == R.id.light) {
            key = LiangbaRFDataKey.LIGHT.getKey();
        } else if (id == R.id.power) {
            key = LiangbaRFDataKey.POWER.getKey();
        } else if (id == R.id.fan) {
            key = LiangbaRFDataKey.SWING.getKey();
        }
        sendCmd(key);
    }


    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        if (isStudyMode()) {
            if (id == R.id.stop) {
                key = LiangbaRFDataKey.STOP.getKey();
            } else if (id == R.id.low) {
                key = LiangbaRFDataKey.LOW.getKey();
            } else if (id == R.id.mid) {
                key = LiangbaRFDataKey.MID.getKey();
            } else if (id == R.id.high) {
                key = LiangbaRFDataKey.HIGH.getKey();
            } else if (id == R.id.time_up) {
                key = LiangbaRFDataKey.TIME_UP.getKey();
            } else if (id == R.id.time_down) {
                key = LiangbaRFDataKey.TIME_DN.getKey();
            } else if (id == R.id.light) {
                key = LiangbaRFDataKey.LIGHT.getKey();
            } else if (id == R.id.power) {
                key = LiangbaRFDataKey.POWER.getKey();
            } else if (id == R.id.fan) {
                key = LiangbaRFDataKey.SWING.getKey();
            }
            study(true, key, v);
        }
        return false;
    }
}
