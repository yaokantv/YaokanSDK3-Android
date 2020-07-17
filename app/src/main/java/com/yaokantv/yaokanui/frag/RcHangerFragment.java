package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;

import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.HangerRFDataKey;
import com.yaokantv.yaokanui.key.HangerRFDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;

import java.util.ArrayList;
import java.util.List;

public class RcHangerFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {

    private ImageButton pause, rise, drop,light;
    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_hanger, null);
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
        pause = view.findViewById(R.id.btn_pause);
        rise = view.findViewById(R.id.btn_rise);
        drop = view.findViewById(R.id.btn_drop);
        light = view.findViewById(R.id.btn_light);
        expandGridView = view.findViewById(R.id.gv);

        rise.setTag(StringUtils.DRA_BTN_CIRCLE);
        drop.setTag(StringUtils.DRA_BTN_CIRCLE);
        pause.setTag(StringUtils.DRA_BTN_CIRCLE);
        light.setTag(StringUtils.DRA_BTN_CIRCLE);
    }

    private void binderEvent() {
        pause.setOnClickListener(this);
        rise.setOnClickListener(this);
        drop.setOnClickListener(this);
        light.setOnClickListener(this);
        
        pause.setOnLongClickListener(this);
        rise.setOnLongClickListener(this);
        drop.setOnLongClickListener(this);
        light.setOnLongClickListener(this);
        bindGvEventRf();
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(pause, R.drawable.btn_circle, HangerRFDataKey.STOP.getKey(), map);
        KeyBackground(drop, R.drawable.btn_circle, HangerRFDataKey.DROP.getKey(), map);
        KeyBackground(rise, R.drawable.btn_circle, HangerRFDataKey.RISE.getKey(), map);
        KeyBackground(light, R.drawable.btn_circle, HangerRFDataKey.LIGHT.getKey(), map);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_pause) {
            key = HangerRFDataKey.STOP.getKey();
        } else if (id == R.id.btn_rise) {
            key = HangerRFDataKey.RISE.getKey();
        } else if (id == R.id.btn_drop) {
            key = HangerRFDataKey.DROP.getKey();
        } else if (id == R.id.btn_light) {
            key = HangerRFDataKey.LIGHT.getKey();
        }
        sendCmd(key);
    }


    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        if (isStudyMode()) {
            if (id == R.id.btn_pause) {
                key = HangerRFDataKey.STOP.getKey();
            } else if (id == R.id.btn_rise) {
                key = HangerRFDataKey.RISE.getKey();
            } else if (id == R.id.btn_drop) {
                key = HangerRFDataKey.DROP.getKey();
            } else if (id == R.id.btn_light) {
                key = HangerRFDataKey.LIGHT.getKey();
            }
            study(true, key, v);
        }
        return false;
    }
}
