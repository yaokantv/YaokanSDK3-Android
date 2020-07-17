package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RemoteCtrl;

import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.FannerRemoteControlDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;


public class RcFanFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {

    private ImageButton power;

    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_fan, null);
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
        expandGridView = view.findViewById(R.id.gv);

        power.setTag(StringUtils.DRA_BG_MATCHING);

    }

    private void binderEvent() {
        power.setOnClickListener(this);
        power.setOnLongClickListener(this);
        bindGvEvent();
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(power, FannerRemoteControlDataKey.POWER.getKey(), map);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.power) {
            key = FannerRemoteControlDataKey.POWER.getKey();
            sendCmd(key);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        if (isStudyMode()) {
            if (id == R.id.power) {
                key = FannerRemoteControlDataKey.POWER.getKey();
            }
            study(false, key, v);
        }
        return false;
    }
}
