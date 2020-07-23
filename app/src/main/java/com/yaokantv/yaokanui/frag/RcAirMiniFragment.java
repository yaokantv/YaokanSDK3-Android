package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.AirMiniDataKey;
import com.yaokantv.yaokanui.key.AirMiniDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;


public class RcAirMiniFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {

    private ImageButton power, tempDown, tempUp;
    private RelativeLayout rlMode, rlWind, rlU, rlL;
    RcActivity activity;
    TextView tvMode,tvSpeed,tvU,tvL;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_air_mini, null);
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
        tvMode = view.findViewById(R.id.tv_mode);
        tvSpeed = view.findViewById(R.id.tv_wind);
        tvU = view.findViewById(R.id.tv_u);
        tvL = view.findViewById(R.id.tv_l);
        power = view.findViewById(R.id.power);
        tempDown = view.findViewById(R.id.temp_down);
        tempUp = view.findViewById(R.id.temp_up);
        rlMode = view.findViewById(R.id.rl_mode);
        rlWind = view.findViewById(R.id.rl_wind);
        rlU = view.findViewById(R.id.rl_u);
        rlL = view.findViewById(R.id.rl_l);
        expandGridView = view.findViewById(R.id.gv);

        power.setTag(StringUtils.DRA_BG_MATCHING);
        tempDown.setTag(StringUtils.DRA_BTN_CIRCLE);
        tempUp.setTag(StringUtils.DRA_BTN_CIRCLE);
        rlMode.setTag(StringUtils.DRA_BTN_CIRCLE);
        rlWind.setTag(StringUtils.DRA_BTN_CIRCLE);
        rlU.setTag(StringUtils.DRA_BTN_CIRCLE);
        rlL.setTag(StringUtils.DRA_BTN_CIRCLE);

    }

    private void binderEvent() {
        power.setOnClickListener(this);
        power.setOnLongClickListener(this);
        tempDown.setOnClickListener(this);
        tempDown.setOnLongClickListener(this);
        tempUp.setOnClickListener(this);
        tempUp.setOnLongClickListener(this);
        rlMode.setOnClickListener(this);
        rlMode.setOnLongClickListener(this);
        rlWind.setOnClickListener(this);
        rlWind.setOnLongClickListener(this);
        rlU.setOnClickListener(this);
        rlU.setOnLongClickListener(this);
        rlL.setOnClickListener(this);
        rlL.setOnLongClickListener(this);
        bindGvEvent();
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(power, AirMiniDataKey.POWER.getKey(), map);
        KeyBackground(tempDown, AirMiniDataKey.TEMP_DOWN.getKey(), map);
        KeyBackground(tempUp, AirMiniDataKey.TEMP_UP.getKey(), map);
        KeyBackground(rlMode, tvMode, AirMiniDataKey.MODE.getKey(), map);
        KeyBackground(rlWind, tvSpeed, AirMiniDataKey.FANSPEED.getKey(), map);
        KeyBackground(rlU, tvU, AirMiniDataKey.UDWIND.getKey(), map);
        KeyBackground(rlL, tvL, AirMiniDataKey.LRWIND.getKey(), map);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.power) {
            key = AirMiniDataKey.POWER.getKey();
            sendCmd(key);
        } else if (id == R.id.temp_down) {
            key = AirMiniDataKey.TEMP_DOWN.getKey();
            sendCmd(key);
        } else if (id == R.id.temp_up) {
            key = AirMiniDataKey.TEMP_UP.getKey();
            sendCmd(key);
        } else if (id == R.id.rl_mode) {
            key = AirMiniDataKey.MODE.getKey();
            sendCmd(key);
        } else if (id == R.id.rl_wind) {
            key = AirMiniDataKey.FANSPEED.getKey();
            sendCmd(key);
        } else if (id == R.id.rl_l) {
            key = AirMiniDataKey.LRWIND.getKey();
            sendCmd(key);
        } else if (id == R.id.rl_u) {
            key = AirMiniDataKey.UDWIND.getKey();
            sendCmd(key);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        if (isStudyMode()) {
            if (id == R.id.power) {
                key = AirMiniDataKey.POWER.getKey();
            }else if (id == R.id.temp_down) {
                key = AirMiniDataKey.TEMP_DOWN.getKey();
            } else if (id == R.id.temp_up) {
                key = AirMiniDataKey.TEMP_UP.getKey();
            } else if (id == R.id.rl_mode) {
                key = AirMiniDataKey.MODE.getKey();
            } else if (id == R.id.rl_wind) {
                key = AirMiniDataKey.FANSPEED.getKey();
            } else if (id == R.id.rl_l) {
                key = AirMiniDataKey.LRWIND.getKey();
            } else if (id == R.id.rl_u) {
                key = AirMiniDataKey.UDWIND.getKey();
            }
            study(false, key, v);
        }
        return false;
    }
}
