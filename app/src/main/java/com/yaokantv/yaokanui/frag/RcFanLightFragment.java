package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RemoteCtrl;

import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.JackRFDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;

public class RcFanLightFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {

    private TextView stop, low, mid, high, h1, h2, h4, h8;
    private ImageButton light;

    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_fan_light, null);
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

        h1 = view.findViewById(R.id.h1);
        h2 = view.findViewById(R.id.h2);
        h4 = view.findViewById(R.id.h4);
        h8 = view.findViewById(R.id.h8);

        light = view.findViewById(R.id.light);

        stop.setTag(StringUtils.DRA_BTN_CIRCLE);
        low.setTag(StringUtils.DRA_BTN_CIRCLE);
        mid.setTag(StringUtils.DRA_BTN_CIRCLE);
        high.setTag(StringUtils.DRA_BTN_CIRCLE);
        h1.setTag(StringUtils.DRA_BTN_CIRCLE);
        h2.setTag(StringUtils.DRA_BTN_CIRCLE);
        h4.setTag(StringUtils.DRA_BTN_CIRCLE);
        h8.setTag(StringUtils.DRA_BTN_CIRCLE);

        light.setTag(StringUtils.DRA_BG_MATCHING);

        expandGridView = view.findViewById(R.id.gv);
    }

    private void binderEvent() {
        stop.setOnClickListener(this);
        low.setOnClickListener(this);
        mid.setOnClickListener(this);
        high.setOnClickListener(this);
        h1.setOnClickListener(this);
        h2.setOnClickListener(this);
        h4.setOnClickListener(this);
        h8.setOnClickListener(this);

        light.setOnClickListener(this);

        stop.setOnLongClickListener(this);
        low.setOnLongClickListener(this);
        mid.setOnLongClickListener(this);
        high.setOnLongClickListener(this);
        h1.setOnLongClickListener(this);
        h2.setOnLongClickListener(this);
        h4.setOnLongClickListener(this);
        h8.setOnLongClickListener(this);

        light.setOnLongClickListener(this);

        bindGvEventRf();
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(stop, R.drawable.btn_circle, R.drawable.shape_circle_white, JackRFDataKey.STOP.getKey(), map);
        KeyBackground(low, R.drawable.btn_circle, R.drawable.shape_circle_white, JackRFDataKey.LOW.getKey(), map);
        KeyBackground(mid, R.drawable.btn_circle, R.drawable.shape_circle_white, JackRFDataKey.MID.getKey(), map);
        KeyBackground(high, R.drawable.btn_circle, R.drawable.shape_circle_white, JackRFDataKey.HIGH.getKey(), map);
        KeyBackground(h1, R.drawable.btn_circle, R.drawable.shape_circle_white, JackRFDataKey.H1.getKey(), map);
        KeyBackground(h2, R.drawable.btn_circle, R.drawable.shape_circle_white, JackRFDataKey.H2.getKey(), map);
        KeyBackground(h4, R.drawable.btn_circle, R.drawable.shape_circle_white, JackRFDataKey.H4.getKey(), map);
        KeyBackground(h8, R.drawable.btn_circle, R.drawable.shape_circle_white, JackRFDataKey.H8.getKey(), map);

        KeyBackground(light, R.drawable.bg_matching, JackRFDataKey.LIGHT.getKey(), map);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.stop) {
            key = JackRFDataKey.STOP.getKey();
        } else if (id == R.id.low) {
            key = JackRFDataKey.LOW.getKey();
        } else if (id == R.id.mid) {
            key = JackRFDataKey.MID.getKey();
        } else if (id == R.id.high) {
            key = JackRFDataKey.HIGH.getKey();
        } else if (id == R.id.h1) {
            key = JackRFDataKey.H1.getKey();
        } else if (id == R.id.h2) {
            key = JackRFDataKey.H2.getKey();
        } else if (id == R.id.h4) {
            key = JackRFDataKey.H4.getKey();
        } else if (id == R.id.h8) {
            key = JackRFDataKey.H8.getKey();
        } else if (id == R.id.light) {
            key = JackRFDataKey.LIGHT.getKey();
        }
        sendCmd(key);
    }


    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        if (isStudyMode()) {
            if (id == R.id.stop) {
                key = JackRFDataKey.STOP.getKey();
            } else if (id == R.id.low) {
                key = JackRFDataKey.LOW.getKey();
            } else if (id == R.id.mid) {
                key = JackRFDataKey.MID.getKey();
            } else if (id == R.id.high) {
                key = JackRFDataKey.HIGH.getKey();
            } else if (id == R.id.h1) {
                key = JackRFDataKey.H1.getKey();
            } else if (id == R.id.h2) {
                key = JackRFDataKey.H2.getKey();
            } else if (id == R.id.h4) {
                key = JackRFDataKey.H4.getKey();
            } else if (id == R.id.h8) {
                key = JackRFDataKey.H8.getKey();
            } else if (id == R.id.light) {
                key = JackRFDataKey.LIGHT.getKey();
            }
            study(true, key, v);
        }
        return false;
    }
}
