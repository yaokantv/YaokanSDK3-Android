package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.Contants;
import com.yaokantv.yaokansdk.model.GfskStatus;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.key.FannerRemoteControlDataKey;
import com.yaokantv.yaokanui.utils.StringUtils;
import com.yaokantv.yaokanui.widget.AdjRangeSeekBar;
import com.yaokantv.yaokanui.widget.Croller;
import com.yaokantv.yaokanui.widget.ExpandAdapter;
import com.yaokantv.yaokanui.widget.OnCrollerChangeListener;


public class RcAdjusterFragment extends BaseRcFragment implements View.OnClickListener, View.OnLongClickListener {


    RcActivity activity;
    Croller lightBar;
    TextView textView;
    AdjRangeSeekBar<Integer> colorBar;
    Button btn25, btn50, btn75, btn100;
    View tempView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_adjuster, null);
        activity = (RcActivity) getActivity();
        initView(v);
        return v;
    }

    @Override
    public void refreshRcData(RemoteCtrl rc) {
        this.rc = rc;
        super.refreshRcData(rc);
        int color = Hawk.get(rc.getRid() + Contants.S_COLOR_TEMP, 0);
        int light = Hawk.get(rc.getRid() + Contants.S_LIGHTNESS, 0);

        colorBar.setSelectedMaxValue(color);
        lightBar.setProgress(light);
    }

    protected void initView(View view) {
        lightBar = view.findViewById(R.id.croller);
        textView = view.findViewById(R.id.tv_progress);
        colorBar = view.findViewById(R.id.sb_vol);
        btn25 = view.findViewById(R.id.k25);
        btn50 = view.findViewById(R.id.k50);
        btn75 = view.findViewById(R.id.k75);
        btn100 = view.findViewById(R.id.k100);
        tempView = view.findViewById(R.id.ll_temp);
        view.findViewById(R.id.power).setOnClickListener(this);
        view.findViewById(R.id.off).setOnClickListener(this);
        view.findViewById(R.id.on).setOnClickListener(this);
        view.findViewById(R.id.k25).setOnClickListener(this);
        view.findViewById(R.id.k50).setOnClickListener(this);
        view.findViewById(R.id.k75).setOnClickListener(this);
        view.findViewById(R.id.k100).setOnClickListener(this);

        colorBar.setOnRangeSeekBarChangeListener(new AdjRangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(AdjRangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                String h = maxValue + "";
                sendAdjCmd("colortemp:" + h);
                Hawk.put(rc.getRid() + Contants.S_COLOR_TEMP, maxValue);
            }
        });
        lightBar.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                textView.setText(progress + "%");
                resetLightBtn();
                if (progress % 25 == 0) {
                    switch (progress) {
                        case 25:
                            btn25.setBackgroundResource(R.drawable.shape_blue_circle);
                            break;
                        case 50:
                            btn50.setBackgroundResource(R.drawable.shape_blue_circle);
                            break;
                        case 75:
                            btn75.setBackgroundResource(R.drawable.shape_blue_circle);
                            break;
                        case 100:
                            btn100.setBackgroundResource(R.drawable.shape_blue_circle);
                            break;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {
            }

            @Override
            public void onStopTrackingTouch(Croller croller) {
                int pro = croller.getProgress();
                String h = pro + "";
                sendAdjCmd("lightness:" + h);
                Hawk.put(rc.getRid() + Contants.S_LIGHTNESS, pro);
            }
        });

    }

    void resetLightBtn() {
        btn25.setBackgroundResource(R.drawable.yk_ctrl_camera_power);
        btn50.setBackgroundResource(R.drawable.yk_ctrl_camera_power);
        btn75.setBackgroundResource(R.drawable.yk_ctrl_camera_power);
        btn100.setBackgroundResource(R.drawable.yk_ctrl_camera_power);
    }

    private void binderEvent() {
    }

    @Override
    public void setKeyBackground() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.power:
                sendAdjCmd("power");
                break;
            case R.id.on:
                sendAdjCmd("on");
                break;
            case R.id.off:
                sendAdjCmd("off");
                break;
            case R.id.k25:
                sendAdjCmd("lightness:25");
                Hawk.put(rc.getRid() + Contants.S_LIGHTNESS, 25);
                lightBar.setProgress(25);
                break;
            case R.id.k50:
                sendAdjCmd("lightness:50");
                Hawk.put(rc.getRid() + Contants.S_LIGHTNESS, 50);
                lightBar.setProgress(50);
                break;
            case R.id.k75:
                sendAdjCmd("lightness:75");
                Hawk.put(rc.getRid() + Contants.S_LIGHTNESS, 75);
                lightBar.setProgress(75);
                break;
            case R.id.k100:
                sendAdjCmd(  "lightness:100");
                Hawk.put(rc.getRid() + Contants.S_LIGHTNESS, 100);
                lightBar.setProgress(100);
                break;
        }
    }
    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        super.onReceiveMsg(msgType, ykMessage);
        if (msgType == MsgType.GfskStatus) {
            if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof GfskStatus) {
                if (getActivity() != null && !getActivity().isFinishing())
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GfskStatus device = (GfskStatus) ykMessage.getData();
                            String rfModel = device.getData().getMac() + "100" + device.getData().getNum_no();
                            if (rfModel.equals(rc.getRmodel())) {
                                toast(device.getData().toString());
                                boolean isOpen = device.getData().getPower() > 0;
                                Hawk.put(rc.getRid() + Contants.S_COLOR_TEMP, device.getData().getColortemp());
                                Hawk.put(rc.getRid() + Contants.S_LIGHTNESS, device.getData().getLightness());
                                colorBar.setSelectedMaxValue(device.getData().getColortemp());
                                lightBar.setProgress(device.getData().getLightness());
                            }
                        }
                    });
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
