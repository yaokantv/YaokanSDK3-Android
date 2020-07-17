package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;

import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.FannerRemoteControlDataKey;
import com.yaokantv.yaokanui.key.STBRemoteControlDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;
import com.yaokantv.yaokanui.widget.NoScrollGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RcStbFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {

    /**
     * 数字1,2,3按钮
     */
    private TextView oneBtn, twoBtn, threeBtn, starBtn;
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
    private ImageButton menuBtn, backBtn, bootBtn, muteBtn;
    /**
     * 上,下,左,右,ok按钮
     */
    private ImageButton leftBtn, rightBtn, btnRew, btnFF, btnPlay;

    private ImageButton upBtn, downBtn;

    RelativeLayout okBtn;
    Button exitBtn;

    private TextView stbPower;

    private ImageButton volumeAddBtn, volumeReduceBtn, channelAddBtn, channelReduceBtn;

    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_stb, null);
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
        starBtn = view.findViewById(R.id.tv_start);
        sharpBtn = view.findViewById(R.id.tv_sharp);

        // 上,左,下,右,ok按鈕的使初始化
        upBtn = view.findViewById(R.id.btn_up);
        leftBtn = view.findViewById(R.id.btn_left);
        downBtn = view.findViewById(R.id.btn_down);
        rightBtn = view.findViewById(R.id.btn_right);
        okBtn = view.findViewById(R.id.btn_ok);
        muteBtn = view.findViewById(R.id.btn_mute);
        bootBtn = view.findViewById(R.id.btn_home);
        exitBtn = view.findViewById(R.id.btn_exit);

        btnRew = view.findViewById(R.id.btn_rew);
        btnFF = view.findViewById(R.id.btn_ff);
        btnPlay = view.findViewById(R.id.btn_play);

        expandGridView = view.findViewById(R.id.gv);
        stbPower = view.findViewById(R.id.stb_power);
        volumeAddBtn = view.findViewById(R.id.btn_vol_add);
        volumeReduceBtn = view.findViewById(R.id.btn_vol_sub);
        channelAddBtn = view.findViewById(R.id.btn_channel_add);
        channelReduceBtn = view.findViewById(R.id.btn_channel_sub);

        stbPower.setTag(StringUtils.DRA_SQUARE);
        channelAddBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        channelReduceBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        muteBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        bootBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        volumeAddBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        volumeReduceBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        menuBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        backBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        exitBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        upBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        downBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        leftBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        rightBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        okBtn.setTag(StringUtils.DRA_BTN_CIRCLE);
        btnRew.setTag(StringUtils.DRA_BTN_CIRCLE);
        btnFF.setTag(StringUtils.DRA_BTN_CIRCLE);
        btnPlay.setTag(StringUtils.DRA_PLAY);
        oneBtn.setTag(StringUtils.DRA_BTN_NUM);
        twoBtn.setTag(StringUtils.DRA_BTN_NUM);
        threeBtn.setTag(StringUtils.DRA_BTN_NUM);
        fourBtn.setTag(StringUtils.DRA_BTN_NUM);
        fiveBtn.setTag(StringUtils.DRA_BTN_NUM);
        sixBtn.setTag(StringUtils.DRA_BTN_NUM);
        sevenBtn.setTag(StringUtils.DRA_BTN_NUM);
        eightBtn.setTag(StringUtils.DRA_BTN_NUM);
        nineBtn.setTag(StringUtils.DRA_BTN_NUM);
        zeroBtn.setTag(StringUtils.DRA_BTN_NUM);
        sharpBtn.setTag(StringUtils.DRA_BTN_NUM);
        starBtn.setTag(StringUtils.DRA_BTN_NUM);
    }

    private void binderEvent() {
        stbPower.setOnClickListener(this);
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
        bootBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        starBtn.setOnClickListener(this);
        sharpBtn.setOnClickListener(this);

        btnPlay.setOnClickListener(this);
        btnFF.setOnClickListener(this);
        btnRew.setOnClickListener(this);

        volumeAddBtn.setOnClickListener(this);
        volumeReduceBtn.setOnClickListener(this);
        channelAddBtn.setOnClickListener(this);
        channelReduceBtn.setOnClickListener(this);

        stbPower.setOnLongClickListener(this);
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
        bootBtn.setOnLongClickListener(this);
        exitBtn.setOnLongClickListener(this);
        starBtn.setOnLongClickListener(this);
        sharpBtn.setOnLongClickListener(this);

        btnPlay.setOnLongClickListener(this);
        btnFF.setOnLongClickListener(this);
        btnRew.setOnLongClickListener(this);

        volumeAddBtn.setOnLongClickListener(this);
        volumeReduceBtn.setOnLongClickListener(this);
        channelAddBtn.setOnLongClickListener(this);
        channelReduceBtn.setOnLongClickListener(this);

        bindGvEvent();
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(zeroBtn, R.drawable.btn_num, R.drawable.shape_cir_white_25, STBRemoteControlDataKey.ZERO.getKey(), map);
        KeyBackground(oneBtn, R.drawable.btn_num, R.drawable.shape_cir_white_25, STBRemoteControlDataKey.ONE.getKey(), map);
        KeyBackground(twoBtn, R.drawable.btn_num, R.drawable.shape_cir_white_25, STBRemoteControlDataKey.TWO.getKey(), map);
        KeyBackground(threeBtn, R.drawable.btn_num, R.drawable.shape_cir_white_25, STBRemoteControlDataKey.THREE.getKey(), map);
        KeyBackground(fourBtn, R.drawable.btn_num, R.drawable.shape_cir_white_25, STBRemoteControlDataKey.FOUR.getKey(), map);
        KeyBackground(fiveBtn, R.drawable.btn_num, R.drawable.shape_cir_white_25, STBRemoteControlDataKey.FIVE.getKey(), map);
        KeyBackground(sixBtn, R.drawable.btn_num, R.drawable.shape_cir_white_25, STBRemoteControlDataKey.SIX.getKey(), map);
        KeyBackground(sevenBtn, R.drawable.btn_num, R.drawable.shape_cir_white_25, STBRemoteControlDataKey.SEVEN.getKey(), map);
        KeyBackground(eightBtn, R.drawable.btn_num, R.drawable.shape_cir_white_25, STBRemoteControlDataKey.EIGHT.getKey(), map);
        KeyBackground(nineBtn, R.drawable.btn_num, R.drawable.shape_cir_white_25, STBRemoteControlDataKey.NINE.getKey(), map);
        KeyBackground(eightBtn, R.drawable.btn_num, R.drawable.shape_cir_white_25, STBRemoteControlDataKey.EIGHT.getKey(), map);
        KeyBackground(nineBtn, R.drawable.btn_num, R.drawable.shape_cir_white_25, STBRemoteControlDataKey.NINE.getKey(), map);

        KeyBackground(channelAddBtn, STBRemoteControlDataKey.CHANNEL_ADD.getKey(), map);
        KeyBackground(channelReduceBtn, STBRemoteControlDataKey.CHANNEL_SUB.getKey(), map);
        KeyBackground(volumeAddBtn, STBRemoteControlDataKey.VOLUME_ADD.getKey(), map);
        KeyBackground(volumeReduceBtn, STBRemoteControlDataKey.VOLUME_SUB.getKey(), map);

        KeyBackground(btnPlay, STBRemoteControlDataKey.PLAY.getKey(), map);
        KeyBackground(btnFF, STBRemoteControlDataKey.FF.getKey(), map);
        KeyBackground(btnRew, STBRemoteControlDataKey.REW.getKey(), map);

        KeyBackground(menuBtn, STBRemoteControlDataKey.MENU.getKey(), map);
        KeyBackground(muteBtn, STBRemoteControlDataKey.MUTE.getKey(), map);
        KeyBackground(backBtn, STBRemoteControlDataKey.BACK.getKey(), map);
        KeyBackground(bootBtn, STBRemoteControlDataKey.BOOT.getKey(), map);
        KeyBackground(exitBtn, STBRemoteControlDataKey.EXIT.getKey(), map);

        KeyBackground(stbPower, STBRemoteControlDataKey.POWER.getKey(), map);

        KeyBackground(upBtn, STBRemoteControlDataKey.UP.getKey(), map);
        KeyBackground(leftBtn, STBRemoteControlDataKey.LEFT.getKey(), map);
        KeyBackground(downBtn, STBRemoteControlDataKey.DOWN.getKey(), map);
        KeyBackground(rightBtn, STBRemoteControlDataKey.RIGHT.getKey(), map);
        KeyBackground(okBtn, R.drawable.btn_non, STBRemoteControlDataKey.OK.getKey(), map);
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

    @Override
    public void onReceiveMsg(MsgType msgType, YkMessage ykMessage) {
        super.onReceiveMsg(msgType, ykMessage);
        if (activity != null && !activity.isFinishing()) {
            if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof RemoteCtrl) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setKeyBackground();
                    }
                });
            }
        }
    }

    private void setKey(int id) {
        if (id == R.id.stb_power) {
            key = STBRemoteControlDataKey.POWER.getKey();
        } else if (id == R.id.tv_power) {
            key = STBRemoteControlDataKey.TVPOWER.getKey();
        } else if (id == R.id.tv_1) {
            key = STBRemoteControlDataKey.ONE.getKey();
        } else if (id == R.id.tv_2) {
            key = STBRemoteControlDataKey.TWO.getKey();
        } else if (id == R.id.tv_3) {
            key = STBRemoteControlDataKey.THREE.getKey();
        } else if (id == R.id.tv_4) {
            key = STBRemoteControlDataKey.FOUR.getKey();
        } else if (id == R.id.tv_5) {
            key = STBRemoteControlDataKey.FIVE.getKey();
        } else if (id == R.id.tv_6) {
            key = STBRemoteControlDataKey.SIX.getKey();
        } else if (id == R.id.tv_7) {
            key = STBRemoteControlDataKey.SEVEN.getKey();
        } else if (id == R.id.tv_8) {
            key = STBRemoteControlDataKey.EIGHT.getKey();
        } else if (id == R.id.tv_9) {
            key = STBRemoteControlDataKey.NINE.getKey();
        } else if (id == R.id.tv_0) {
            key = STBRemoteControlDataKey.ZERO.getKey();
        } else if (id == R.id.btn_menu) {
            key = STBRemoteControlDataKey.MENU.getKey();
        } else if (id == R.id.btn_mute) {
            key = STBRemoteControlDataKey.MUTE.getKey();
        } else if (id == R.id.btn_home) {
            key = STBRemoteControlDataKey.BOOT.getKey();
        } else if (id == R.id.btn_exit) {
            key = STBRemoteControlDataKey.EXIT.getKey();
        } else if (id == R.id.btn_back) {
            key = STBRemoteControlDataKey.BACK.getKey();
        } else if (id == R.id.btn_up) {
            key = STBRemoteControlDataKey.UP.getKey();
        } else if (id == R.id.btn_left) {
            key = STBRemoteControlDataKey.LEFT.getKey();
        } else if (id == R.id.btn_down) {
            key = STBRemoteControlDataKey.DOWN.getKey();
        } else if (id == R.id.btn_right) {
            key = STBRemoteControlDataKey.RIGHT.getKey();
        } else if (id == R.id.btn_ok) {
            key = STBRemoteControlDataKey.OK.getKey();
        } else if (id == R.id.tv_power) {
            key = STBRemoteControlDataKey.TVPOWER.getKey();
        } else if (id == R.id.sigal) {
            key = STBRemoteControlDataKey.SIGNAL.getKey();
        } else if (id == R.id.stb_power) {
            key = STBRemoteControlDataKey.POWER.getKey();
        } else if (id == R.id.btn_vol_add) {
            key = STBRemoteControlDataKey.VOLUME_ADD.getKey();
        } else if (id == R.id.btn_vol_sub) {
            key = STBRemoteControlDataKey.VOLUME_SUB.getKey();
        } else if (id == R.id.btn_channel_add) {
            key = STBRemoteControlDataKey.CHANNEL_ADD.getKey();
        } else if (id == R.id.btn_channel_sub) {
            key = STBRemoteControlDataKey.CHANNEL_SUB.getKey();
        } else if (id == R.id.tv_sharp) {
            key = STBRemoteControlDataKey.SHARP.getKey();
        } else if (id == R.id.tv_start) {
            key = STBRemoteControlDataKey.START.getKey();
        } else if (id == R.id.btn_play) {
            key = STBRemoteControlDataKey.PLAY.getKey();
        } else if (id == R.id.btn_rew) {
            key = STBRemoteControlDataKey.REW.getKey();
        } else if (id == R.id.btn_ff) {
            key = STBRemoteControlDataKey.FF.getKey();
        }
    }

}
