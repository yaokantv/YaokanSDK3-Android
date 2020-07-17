package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;

import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.TVRemoteControlDataKey;
import com.yaokantv.yaokanui.key.TVRemoteControlDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;
import com.yaokantv.yaokanui.widget.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

public class RcTvFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {

    /**
     * 数字1,2,3按钮
     */
    private TextView oneBtn, twoBtn, threeBtn, tvDigit;
    /**
     * 数字4,5,6按钮
     */
    private TextView fourBtn, fiveBtn, sixBtn, sharpBtn;
    /**
     * 数字7,8,9按钮
     */
    private TextView sevenBtn, eightBtn, nineBtn, zeroBtn;
    /**
     * 菜单，数字0，返回按钮
     */
    private ImageButton menuBtn, backBtn, muteBtn;
    /**
     * 上,下,左,右,ok按钮
     */
    private ImageButton leftBtn, rightBtn;

    private ImageButton upBtn, downBtn;

    RelativeLayout okBtn;
    Button exitBtn;

    private TextView stbPower, signal, boot;

    private ImageButton volumeAddBtn, volumeReduceBtn, channelAddBtn, channelReduceBtn;

    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_tv, null);
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
        /* 数字鍵1至9,*，0，#按钮的初始化 */
        oneBtn = view.findViewById(R.id.tv_1);
        twoBtn = view.findViewById(R.id.tv_2);
        threeBtn = view.findViewById(R.id.tv_3);
        fourBtn = view.findViewById(R.id.tv_4);
        fiveBtn = view.findViewById(R.id.tv_5);
        sixBtn = view.findViewById(R.id.tv_6);
        sevenBtn = view.findViewById(R.id.tv_7);
        eightBtn = view.findViewById(R.id.tv_8);
        nineBtn = view.findViewById(R.id.tv_9);
        menuBtn = view.findViewById(R.id.btn_menu);
        zeroBtn = view.findViewById(R.id.tv_0);
        backBtn = view.findViewById(R.id.btn_back);
        tvDigit = view.findViewById(R.id.tv_digit);
        sharpBtn = view.findViewById(R.id.tv_sharp);

        // 上,左,下,右,ok按鈕的使初始化
        upBtn = view.findViewById(R.id.btn_up);
        leftBtn = view.findViewById(R.id.btn_left);
        downBtn = view.findViewById(R.id.btn_down);
        rightBtn = view.findViewById(R.id.btn_right);
        okBtn = view.findViewById(R.id.btn_ok);
        muteBtn = view.findViewById(R.id.btn_mute);
        boot = view.findViewById(R.id.boot);
        exitBtn = view.findViewById(R.id.btn_exit);


        expandGridView = view.findViewById(R.id.gv);
        stbPower = view.findViewById(R.id.stb_power);
        signal = view.findViewById(R.id.sigal);
        volumeAddBtn = view.findViewById(R.id.btn_vol_add);
        volumeReduceBtn = view.findViewById(R.id.btn_vol_sub);
        channelAddBtn = view.findViewById(R.id.btn_channel_add);
        channelReduceBtn = view.findViewById(R.id.btn_channel_sub);

        stbPower.setTag(StringUtils.DRA_SQUARE);
        signal.setTag(StringUtils.DRA_SQUARE);
        boot.setTag(StringUtils.DRA_SQUARE);
        channelAddBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        channelReduceBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        muteBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        exitBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        volumeAddBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        volumeReduceBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        menuBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        backBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        upBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        downBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        leftBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        rightBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        okBtn.setTag(StringUtils.DRA_BTN_CIRCLE);

        oneBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        twoBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        threeBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        fourBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        fiveBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        sixBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        sevenBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        eightBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        nineBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        zeroBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        sharpBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        tvDigit.setTag(StringUtils.DRA_BTN_CIRCLE);
    }

    private void binderEvent() {
        stbPower.setOnClickListener(this);
        signal.setOnClickListener(this);
        boot.setOnClickListener(this);

        oneBtn.setOnClickListener(this);
        twoBtn.setOnClickListener(this);
        threeBtn.setOnClickListener(this);
        fourBtn.setOnClickListener(this);
        fiveBtn.setOnClickListener(this);
        sixBtn.setOnClickListener(this);
        sevenBtn.setOnClickListener(this);
        eightBtn.setOnClickListener(this);
        nineBtn.setOnClickListener(this);
        zeroBtn.setOnClickListener(this);
        menuBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        upBtn.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        muteBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        tvDigit.setOnClickListener(this);
        sharpBtn.setOnClickListener(this);

        volumeAddBtn.setOnClickListener(this);
        volumeReduceBtn.setOnClickListener(this);
        channelAddBtn.setOnClickListener(this);
        channelReduceBtn.setOnClickListener(this);

        stbPower.setOnLongClickListener(this);
        signal.setOnLongClickListener(this);
        boot.setOnLongClickListener(this);

        oneBtn.setOnLongClickListener(this);
        twoBtn.setOnLongClickListener(this);
        threeBtn.setOnLongClickListener(this);
        fourBtn.setOnLongClickListener(this);
        fiveBtn.setOnLongClickListener(this);
        sixBtn.setOnLongClickListener(this);
        sevenBtn.setOnLongClickListener(this);
        eightBtn.setOnLongClickListener(this);
        nineBtn.setOnLongClickListener(this);
        zeroBtn.setOnLongClickListener(this);
        menuBtn.setOnLongClickListener(this);
        backBtn.setOnLongClickListener(this);
        okBtn.setOnLongClickListener(this);
        upBtn.setOnLongClickListener(this);
        leftBtn.setOnLongClickListener(this);
        downBtn.setOnLongClickListener(this);
        rightBtn.setOnLongClickListener(this);
        muteBtn.setOnLongClickListener(this);
        exitBtn.setOnLongClickListener(this);
        tvDigit.setOnLongClickListener(this);
        sharpBtn.setOnLongClickListener(this);

        volumeAddBtn.setOnLongClickListener(this);
        volumeReduceBtn.setOnLongClickListener(this);
        channelAddBtn.setOnLongClickListener(this);
        channelReduceBtn.setOnLongClickListener(this);

        bindGvEvent();
    }


    @Override
    public void setKeyBackground() {
        KeyBackground(zeroBtn, R.drawable.btn_circle, R.drawable.shape_circle_white, TVRemoteControlDataKey.ZERO.getKey(), map);
        KeyBackground(oneBtn, R.drawable.btn_circle, R.drawable.shape_circle_white, TVRemoteControlDataKey.ONE.getKey(), map);
        KeyBackground(twoBtn, R.drawable.btn_circle, R.drawable.shape_circle_white, TVRemoteControlDataKey.TWO.getKey(), map);
        KeyBackground(threeBtn, R.drawable.btn_circle, R.drawable.shape_circle_white, TVRemoteControlDataKey.THREE.getKey(), map);
        KeyBackground(fourBtn, R.drawable.btn_circle, R.drawable.shape_circle_white, TVRemoteControlDataKey.FOUR.getKey(), map);
        KeyBackground(fiveBtn, R.drawable.btn_circle, R.drawable.shape_circle_white, TVRemoteControlDataKey.FIVE.getKey(), map);
        KeyBackground(sixBtn, R.drawable.btn_circle, R.drawable.shape_circle_white, TVRemoteControlDataKey.SIX.getKey(), map);
        KeyBackground(sevenBtn, R.drawable.btn_circle, R.drawable.shape_circle_white, TVRemoteControlDataKey.SEVEN.getKey(), map);
        KeyBackground(eightBtn, R.drawable.btn_circle, R.drawable.shape_circle_white, TVRemoteControlDataKey.EIGHT.getKey(), map);
        KeyBackground(nineBtn, R.drawable.btn_circle, R.drawable.shape_circle_white, TVRemoteControlDataKey.NINE.getKey(), map);

        KeyBackground(tvDigit, R.drawable.btn_circle, R.drawable.shape_circle_white, TVRemoteControlDataKey.DIGIT.getKey(), map);
        KeyBackground(sharpBtn, R.drawable.btn_circle, R.drawable.shape_circle_white, TVRemoteControlDataKey.SHARP.getKey(), map);

        KeyBackground(channelAddBtn, TVRemoteControlDataKey.CHANNEL_ADD.getKey(), map);
        KeyBackground(channelReduceBtn, TVRemoteControlDataKey.CHANNEL_SUB.getKey(), map);
        KeyBackground(volumeAddBtn, TVRemoteControlDataKey.VOLUME_ADD.getKey(), map);
        KeyBackground(volumeReduceBtn, TVRemoteControlDataKey.VOLUME_SUB.getKey(), map);

        KeyBackground(menuBtn, TVRemoteControlDataKey.MENU.getKey(), map);
        KeyBackground(muteBtn, TVRemoteControlDataKey.MUTE.getKey(), map);
        KeyBackground(backBtn, TVRemoteControlDataKey.BACK.getKey(), map);
        KeyBackground(exitBtn, TVRemoteControlDataKey.EXIT.getKey(), map);

        KeyBackground(boot, TVRemoteControlDataKey.BOOT.getKey(), map);
        KeyBackground(stbPower, TVRemoteControlDataKey.POWER.getKey(), map);
        KeyBackground(signal, TVRemoteControlDataKey.SIGNAL.getKey(), map);

        KeyBackground(upBtn, TVRemoteControlDataKey.UP.getKey(), map);
        KeyBackground(leftBtn, TVRemoteControlDataKey.LEFT.getKey(), map);
        KeyBackground(downBtn, TVRemoteControlDataKey.DOWN.getKey(), map);
        KeyBackground(rightBtn, TVRemoteControlDataKey.RIGHT.getKey(), map);
        KeyBackground(okBtn, R.drawable.btn_non, TVRemoteControlDataKey.OK.getKey(), map);
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
        if (id == R.id.stb_power) {
            key = TVRemoteControlDataKey.POWER.getKey();
        } else if (id == R.id.tv_1) {
            key = TVRemoteControlDataKey.ONE.getKey();
        } else if (id == R.id.tv_2) {
            key = TVRemoteControlDataKey.TWO.getKey();
        } else if (id == R.id.tv_3) {
            key = TVRemoteControlDataKey.THREE.getKey();
        } else if (id == R.id.tv_4) {
            key = TVRemoteControlDataKey.FOUR.getKey();
        } else if (id == R.id.tv_5) {
            key = TVRemoteControlDataKey.FIVE.getKey();
        } else if (id == R.id.tv_6) {
            key = TVRemoteControlDataKey.SIX.getKey();
        } else if (id == R.id.tv_7) {
            key = TVRemoteControlDataKey.SEVEN.getKey();
        } else if (id == R.id.tv_8) {
            key = TVRemoteControlDataKey.EIGHT.getKey();
        } else if (id == R.id.tv_9) {
            key = TVRemoteControlDataKey.NINE.getKey();
        } else if (id == R.id.tv_0) {
            key = TVRemoteControlDataKey.ZERO.getKey();
        } else if (id == R.id.btn_menu) {
            key = TVRemoteControlDataKey.MENU.getKey();
        } else if (id == R.id.btn_mute) {
            key = TVRemoteControlDataKey.MUTE.getKey();
        } else if (id == R.id.btn_home) {
            key = TVRemoteControlDataKey.BOOT.getKey();
        } else if (id == R.id.btn_exit) {
            key = TVRemoteControlDataKey.EXIT.getKey();
        } else if (id == R.id.btn_back) {
            key = TVRemoteControlDataKey.BACK.getKey();
        } else if (id == R.id.btn_up) {
            key = TVRemoteControlDataKey.UP.getKey();
        } else if (id == R.id.btn_left) {
            key = TVRemoteControlDataKey.LEFT.getKey();
        } else if (id == R.id.btn_down) {
            key = TVRemoteControlDataKey.DOWN.getKey();
        } else if (id == R.id.btn_right) {
            key = TVRemoteControlDataKey.RIGHT.getKey();
        } else if (id == R.id.btn_ok) {
            key = TVRemoteControlDataKey.OK.getKey();
        } else if (id == R.id.sigal) {
            key = TVRemoteControlDataKey.SIGNAL.getKey();
        } else if (id == R.id.stb_power) {
            key = TVRemoteControlDataKey.POWER.getKey();
        } else if (id == R.id.btn_vol_add) {
            key = TVRemoteControlDataKey.VOLUME_ADD.getKey();
        } else if (id == R.id.btn_vol_sub) {
            key = TVRemoteControlDataKey.VOLUME_SUB.getKey();
        } else if (id == R.id.btn_channel_add) {
            key = TVRemoteControlDataKey.CHANNEL_ADD.getKey();
        } else if (id == R.id.btn_channel_sub) {
            key = TVRemoteControlDataKey.CHANNEL_SUB.getKey();
        } else if (id == R.id.tv_sharp) {
            key = TVRemoteControlDataKey.SHARP.getKey();
        } else if (id == R.id.tv_digit) {
            key = TVRemoteControlDataKey.DIGIT.getKey();
        } else if (id == R.id.btn_play) {
            key = TVRemoteControlDataKey.PLAY.getKey();
        } else if (id == R.id.btn_rew) {
            key = TVRemoteControlDataKey.REW.getKey();
        } else if (id == R.id.boot) {
            key = TVRemoteControlDataKey.BOOT.getKey();
        }
    }
}