package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RemoteCtrl;

import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.WaterHeaterRemoteControlDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;

public class RcHeaterFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {


    private ImageButton power, tempUp, tempDown;

    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_heater, null);
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
        power = view.findViewById(R.id.power);
        tempUp = view.findViewById(R.id.temp_up);
        tempDown = view.findViewById(R.id.temp_down);
        expandGridView = view.findViewById(R.id.gv);

        power.setTag(StringUtils.DRA_BG_MATCHING);
        tempUp.setTag(StringUtils.DRA_BTN_CIRCLE);
        tempDown.setTag(StringUtils.DRA_BTN_CIRCLE);
    }

    private void binderEvent() {
        power.setOnClickListener(this);
        tempUp.setOnClickListener(this);
        tempDown.setOnClickListener(this);
        power.setOnLongClickListener(this);
        tempUp.setOnLongClickListener(this);
        tempDown.setOnLongClickListener(this);

        bindGvEvent();
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(power, WaterHeaterRemoteControlDataKey.POWER.getKey(), map);
        KeyBackground(tempUp, WaterHeaterRemoteControlDataKey.TEMP_UP.getKey(), map);
        KeyBackground(tempDown, WaterHeaterRemoteControlDataKey.TEMP_DOWN.getKey(), map);
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
            key = WaterHeaterRemoteControlDataKey.POWER.getKey();
        } else if (id == R.id.temp_up) {
            key = WaterHeaterRemoteControlDataKey.TEMP_UP.getKey();
        } else if (id == R.id.temp_down) {
            key = WaterHeaterRemoteControlDataKey.TEMP_DOWN.getKey();
        }
    }


}
