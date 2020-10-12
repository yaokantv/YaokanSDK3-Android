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


public class RcOtherFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {


    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_other, null);
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
        expandGridView = view.findViewById(R.id.gv);
    }

    private void binderEvent() {
        bindGvEvent();
    }

    @Override
    public void setKeyBackground() {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }

    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        if (isStudyMode()) {
        }
        return false;
    }
}
