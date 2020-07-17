package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.utils.Utility;
import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.CameraRemoteControlDataKey;
import com.yaokantv.yaokanui.key.FannerRemoteControlDataKey;
import com.yaokantv.yaokanui.utils.DataUtils;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.ExpandAdapter;
import com.yaokantv.yaokanui.widget.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

public class RcCameraFragment extends BaseRcFragment {


    private ImageButton power, subBtn, addBtn;

    private TextView singleBtn, timeDelayBtn, continuousBtn, tvTime, longPrompt;
    private LinearLayout timeLl;
    private int type;

    private final int single = 1, timeDelay = 2, conyinuous = 3;
    private int timer;

    private CameraThread mCameraThread;

    private boolean timing = true;
    protected boolean longStoped = false;
    private boolean isstop = true;
    RcActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_camera, null);
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
        binderEvent();
    }

    protected void initView(View view) {
        power = view.findViewById(R.id.power);
        subBtn = view.findViewById(R.id.time_down);
        addBtn = view.findViewById(R.id.time_up);
        singleBtn = view.findViewById(R.id.single);
        timeDelayBtn = view.findViewById(R.id.delay);
        continuousBtn = view.findViewById(R.id.series);
        tvTime = view.findViewById(R.id.tv_time);
        tvTime.setText("" + dataUtils.getKeyIntValue(DataUtils.CAMERA_TIME));
        timeLl = view.findViewById(R.id.ll_time);
        longPrompt = view.findViewById(R.id.long_prompt);
        type = single;
        timer = Integer.parseInt(tvTime.getText().toString());
        setVisibility(type);

        power.setTag(StringUtils.DRA_CAMERA);
    }

    private void binderEvent() {
        power.setOnClickListener(mOnClickListener);
        singleBtn.setOnClickListener(mOnClickListener);
        timeDelayBtn.setOnClickListener(mOnClickListener);
        continuousBtn.setOnClickListener(mOnClickListener);

        subBtn.setOnClickListener(mOnClickListener);
        addBtn.setOnClickListener(mOnClickListener);

        power.setOnLongClickListener(longClickListener);
        power.setOnTouchListener(mCameraOnTouchListener);

    }

    private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            int id = v.getId();
            if (id == R.id.power) {
                key = CameraRemoteControlDataKey.POWER.getKey();
                if (isStudyMode()) {
                    study(false, key, v);
                } else {
                    if (type == conyinuous) {
                        mCameraThread = new CameraThread(conyinuous, v);
                        mCameraThread.start();
                    }
                }
            }
            return true;
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.power) {
                key = CameraRemoteControlDataKey.POWER.getKey();
                if (type == single) {
                    if (!Utility.isEmpty(key)) {
                        sendCmd(key);
                    }
                } else if (type == timeDelay) {
                    if (timing) {
                        timing = false;
                        isstop = true;
                        subBtn.setVisibility(View.GONE);
                        addBtn.setVisibility(View.GONE);
                        mCameraThread = new CameraThread(timeDelay, v);
                        mCameraThread.start();
                    } else {
                        timing = true;
                        timer = dataUtils.getKeyIntValue(DataUtils.CAMERA_TIME);
                        isstop = false;
                        tvTime.setText("" + timer);
                        subBtn.setVisibility(View.VISIBLE);
                        addBtn.setVisibility(View.VISIBLE);
                        mHandler.removeCallbacks(mCameraThread);
                        mCameraThread = null;
                        mHandler.removeMessages(timeDelay);
                    }
                }
            } else if (id == R.id.single) {
                type = single;
                setVisibility(type);
            } else if (id == R.id.delay) {
                type = timeDelay;
                setVisibility(type);
            } else if (id == R.id.series) {
                type = conyinuous;
                setVisibility(type);
            } else if (id == R.id.time_down) {
                if (timer > 0) {
                    timer = timer - 1;
                    dataUtils.setKeyValue(DataUtils.CAMERA_TIME, timer);
                }
                tvTime.setText("" + timer);
            } else if (id == R.id.time_up) {
                if (timer < 20) {
                    timer = timer + 1;
                    dataUtils.setKeyValue(DataUtils.CAMERA_TIME, timer);
                }
                tvTime.setText("" + timer);
            }

        }
    };

    public void setVisibility(int type) {
        singleBtn.setTextColor(getResources().getColor(R.color.black));
        timeDelayBtn.setTextColor(getResources().getColor(R.color.black));
        continuousBtn.setTextColor(getResources().getColor(R.color.black));
        switch (type) {
            case single:
                timeLl.setVisibility(View.GONE);
                longPrompt.setVisibility(View.GONE);
                singleBtn.setBackgroundResource(R.drawable.shape_cir_main_25);
                singleBtn.setTextColor(getResources().getColor(R.color.white));
                timeDelayBtn.setBackgroundResource(R.drawable.shape_cir_white_25);
                continuousBtn.setBackgroundResource(R.drawable.shape_cir_white_25);
                break;
            case timeDelay:
                timing = true;
                timeLl.setVisibility(View.VISIBLE);
                longPrompt.setVisibility(View.GONE);
                singleBtn.setBackgroundResource(R.drawable.shape_cir_white_25);
                timeDelayBtn.setBackgroundResource(R.drawable.shape_cir_main_25);
                timeDelayBtn.setTextColor(getResources().getColor(R.color.white));
                continuousBtn.setBackgroundResource(R.drawable.shape_cir_white_25);
                break;
            case conyinuous:
                timeLl.setVisibility(View.GONE);
                longPrompt.setVisibility(View.VISIBLE);
                singleBtn.setBackgroundResource(R.drawable.shape_cir_white_25);
                timeDelayBtn.setBackgroundResource(R.drawable.shape_cir_white_25);
                continuousBtn.setBackgroundResource(R.drawable.shape_cir_main_25);
                continuousBtn.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    @Override
    public void setKeyBackground() {
        KeyBackground(power, R.color.transparent, CameraRemoteControlDataKey.POWER.getKey(), map);
    }

    class CameraThread extends Thread {

        int type;
        View view;

        public CameraThread(int type, View view) {
            this.type = type;
            this.view = view;
        }

        @Override
        public void run() {
            super.run();
            if (type == timeDelay) {
                int size = timer - 1;
                for (int i = size; i >= 0 && isstop; i--) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = timeDelay;
                    msg.arg1 = size - i;
                    msg.obj = view;
                    mHandler.sendMessageDelayed(msg, i * 1000);
                }
            } else {
                for (int j = 0; !longStoped; j++) {
                    if (!Utility.isEmpty(key)) {
                        sendCmd(key);
                    }
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case timeDelay:
                    int time = msg.arg1;
                    tvTime.setText("" + time);
                    if (time == 0) {
                        if (!Utility.isEmpty(key)) {
                            sendCmd(key);
                            //发完码后复原。
                            timer = dataUtils.getKeyIntValue(DataUtils.CAMERA_TIME);
                            tvTime.setText("" + timer);
                            subBtn.setVisibility(View.VISIBLE);
                            addBtn.setVisibility(View.VISIBLE);
                            timing = true;
                        }
                    }
                    break;
            }
        }

    };
    /**
     * 触摸事件
     */
    protected View.OnTouchListener mCameraOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    longStoped = false;
                    break;
                case MotionEvent.ACTION_UP:
                    longStoped = true;
                    if (!Utility.isEmpty(mCameraThread)) {
                        mHandler.removeCallbacks(mCameraThread);
                    }
                    break;
            }

            return false;
        }

    };

//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.power) {
//            key = FannerRemoteControlDataKey.POWER.getKey();
//            sendCmd(key);
//        }
//    }


}
