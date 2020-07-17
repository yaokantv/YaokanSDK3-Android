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
import com.yaokantv.yaokanui.key.CurtainRFDataKey;
import com.yaokantv.yaokanui.key.CurtainRFDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;

import java.util.ArrayList;
import java.util.List;

public class RcCurtainFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {

    private ImageButton pause, open, close;
    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_curtain, null);
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
        pause = view.findViewById(R.id.btn_cur_pause);
        open = view.findViewById(R.id.curtain_open);
        close = view.findViewById(R.id.curtain_close);
        expandGridView = view.findViewById(R.id.gv);

        open.setTag(StringUtils.DRA_BTN_CIRCLE);
        close.setTag(StringUtils.DRA_BTN_CIRCLE);
        pause.setTag(StringUtils.DRA_BTN_CIRCLE);
    }

    private void binderEvent() {
        pause.setOnClickListener(this);
        open.setOnClickListener(this);
        close.setOnClickListener(this);
        pause.setOnLongClickListener(this);
        open.setOnLongClickListener(this);
        close.setOnLongClickListener(this);
        bindGvEventRf();
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(pause, R.drawable.btn_circle, CurtainRFDataKey.PAUSE.getKey(), map);
        KeyBackground(close, R.drawable.btn_circle, CurtainRFDataKey.CLOSE.getKey(), map);
        KeyBackground(open, R.drawable.btn_circle, CurtainRFDataKey.OPEN.getKey(), map);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_cur_pause) {
            key = CurtainRFDataKey.PAUSE.getKey();
        } else if (id == R.id.curtain_open) {
            key = CurtainRFDataKey.OPEN.getKey();
        } else if (id == R.id.curtain_close) {
            key = CurtainRFDataKey.CLOSE.getKey();
        }
        sendCmd(key);
    }


    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        if (isStudyMode()) {
            if (id == R.id.btn_cur_pause) {
                key = CurtainRFDataKey.PAUSE.getKey();
            } else if (id == R.id.curtain_open) {
                key = CurtainRFDataKey.OPEN.getKey();
            } else if (id == R.id.curtain_close) {
                key = CurtainRFDataKey.CLOSE.getKey();
            }
            study(true, key, v);
        }
        return false;
    }
}
