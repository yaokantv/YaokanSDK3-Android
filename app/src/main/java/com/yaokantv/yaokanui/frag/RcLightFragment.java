package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RemoteCtrl;

import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.JackRFDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;

public class RcLightFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {


    private ImageButton power;
    private Button on, off;

    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_light, null);
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
        power = view.findViewById(R.id.c_btn_power);
        on = view.findViewById(R.id.c_btn_open);
        off = view.findViewById(R.id.c_btn_close);
        expandGridView = view.findViewById(R.id.gv);

    }

    private void binderEvent() {
        power.setOnClickListener(this);
        on.setOnClickListener(this);
        off.setOnClickListener(this);
        power.setOnLongClickListener(this);
        on.setOnLongClickListener(this);
        off.setOnLongClickListener(this);
        bindGvEvent();
        power.setTag(StringUtils.DRA_BTN_CIRCLE);
        on.setTag(StringUtils.DRA_BTN_CIRCLE);
        off.setTag(StringUtils.DRA_BTN_CIRCLE);
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(power, R.drawable.btn_circle, JackRFDataKey.POWER.getKey(), map);
        KeyBackground(off, JackRFDataKey.OFF.getKey(), map);
        KeyBackground(on, JackRFDataKey.ON.getKey(), map);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        setKey(id);
        sendCmd(key);
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
        if (id == R.id.c_btn_power) {
            key = JackRFDataKey.POWER.getKey();
        } else if (id == R.id.c_btn_open) {
            key = JackRFDataKey.ON.getKey();
        } else if (id == R.id.c_btn_close) {
            key = JackRFDataKey.OFF.getKey();
        }
    }
}
