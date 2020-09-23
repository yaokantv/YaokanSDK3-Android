package com.yaokantv.yaokanui.frag;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.MpeStatusReport;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.Logger;
import com.yaokantv.yaokanui.RcActivity;
import com.yaokantv.yaokanui.bean.BedMode;
import com.yaokantv.yaokanui.bean.BedStatus;
import com.yaokantv.yaokanui.key.MpeDataKey;
import com.yaokantv.yaokanui.widget.RecyclerViewNoBugLinearLayoutManager;
import com.yaokantv.yaokanui.widget.SpaceItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RcChairFragment extends BaseRcFragment implements View.OnClickListener {

    RecyclerView rvMode;
    RcActivity activity;
    CommonAdapter<BedMode> adapter;
    List<BedMode> list = new ArrayList<>();
    LinearLayout llHeadUp, llHeadDown, llFootUp, llFootDown, llHafUp, llHafDown, llHeadSock, llLight, llFootSock, llMode1, llMode2, llMode3, llMin1, llMin2, llMin3;
    BedStatus bedStatus;
    ImageView ivHeadSock, ivLight, ivFootSock, ivMode1, ivMode2, ivMode3, ivMin1, ivMin2, ivMin3;
    TextView tvHeadSock, tvLight, tvFootSock, tvMode1, tvMode2, tvMode3, tvMin1, tvMin2, tvMin3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_rc_chair, null);
        activity = (RcActivity) getActivity();
        initView(v);
        return v;
    }

    private void setView() {
        ivHeadSock.setImageResource(bedStatus.isHead_sock() ? R.mipmap.ic_mpe_headsock_press : R.mipmap.ic_mpe_headsock);
        ivLight.setImageResource(bedStatus.isLight() ? R.mipmap.ic_mpe_larm_press : R.mipmap.ic_mpe_larm);
        ivFootSock.setImageResource(bedStatus.isFoot_sock() ? R.mipmap.ic_mpe_footsock_press : R.mipmap.ic_mpe_footsock);
        ivMode1.setImageResource(R.mipmap.ic_mpe_bed_sm1);
        ivMode2.setImageResource(R.mipmap.ic_mpe_bed_sm2);
        ivMode3.setImageResource(R.mipmap.ic_mpe_bed_sm3);
        ivMin1.setImageResource(R.mipmap.ic_mpe_time10);
        ivMin2.setImageResource(R.mipmap.ic_mpe_time20);
        ivMin3.setImageResource(R.mipmap.ic_mpe_time30);

        tvHeadSock.setTextColor(getResources().getColor(bedStatus.isHead_sock() ? R.color.black : R.color.text_gray));
        tvLight.setTextColor(getResources().getColor(bedStatus.isLight() ? R.color.black : R.color.text_gray));
        tvFootSock.setTextColor(getResources().getColor(bedStatus.isFoot_sock() ? R.color.black : R.color.text_gray));

        switch (bedStatus.getMode()) {
            case 1:
                ivMode1.setImageResource(R.mipmap.ic_mpe_bed_sm1_press);
                tvMode1.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2:
                ivMode2.setImageResource(R.mipmap.ic_mpe_bed_sm2_press);
                tvMode2.setTextColor(getResources().getColor(R.color.black));
                break;
            case 3:
                ivMode3.setImageResource(R.mipmap.ic_mpe_bed_sm3_press);
                tvMode3.setTextColor(getResources().getColor(R.color.black));
                break;
        }
        switch (bedStatus.getTime()) {
            case 1:
                ivMin1.setImageResource(R.mipmap.ic_mpe_time10_press);
                tvMin1.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2:
                ivMin2.setImageResource(R.mipmap.ic_mpe_time20_press);
                tvMin2.setTextColor(getResources().getColor(R.color.black));
                break;
            case 3:
                ivMin3.setImageResource(R.mipmap.ic_mpe_time30_press);
                tvMin3.setTextColor(getResources().getColor(R.color.black));
                break;
        }
    }

    private void setStatus() {
        if (!TextUtils.isEmpty(rc.getUuid()) && bedStatus != null) {
            Hawk.put(rc.getUuid(), bedStatus.getJson());
            setView();
        }
    }

    @Override
    public void refreshRcData(RemoteCtrl rc) {
        this.rc = rc;
        super.refreshRcData(rc);
        map.clear();
        map.addAll(rc.getRcCmdAll());
        setKeyBackground();
        binderEvent();
        m(rc);
        if (bedStatus == null) {
            bedStatus = new BedStatus();
        }
        setView();
    }

    private void m(RemoteCtrl rc) {
        if (rc != null && !TextUtils.isEmpty(rc.getUuid())) {
            String json = Hawk.get(rc.getUuid());
            if (!TextUtils.isEmpty(json)) {
                bedStatus = new Gson().fromJson(json, BedStatus.class);
                setView();
            }
            if (!TextUtils.isEmpty(rc.getDevice_id()) && !TextUtils.isEmpty(rc.getMac())) {
                Yaokan.instance().queryMpeStatus(rc.getMac(), rc.getDevice_id(), Yaokan.instance().getDid(rc.getMac()), rc.getBe_rc_type());
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        m(rc);
    }

    protected void initView(View view) {
        rvMode = view.findViewById(R.id.rv_mode);
        llHeadUp = view.findViewById(R.id.ll_head_up);
        llHeadDown = view.findViewById(R.id.ll_head_down);
        llFootUp = view.findViewById(R.id.ll_foot_up);
        llFootDown = view.findViewById(R.id.ll_foot_down);
        llHafUp = view.findViewById(R.id.ll_haf_up);
        llHafDown = view.findViewById(R.id.ll_haf_down);

        llHeadUp.setOnTouchListener(onTouchListener);
        llHeadDown.setOnTouchListener(onTouchListener);
        llFootUp.setOnTouchListener(onTouchListener);
        llFootDown.setOnTouchListener(onTouchListener);
        llHafUp.setOnTouchListener(onTouchListener);
        llHafDown.setOnTouchListener(onTouchListener);

        llHeadSock = view.findViewById(R.id.ll_head_sock);
        llLight = view.findViewById(R.id.ll_light);
        llFootSock = view.findViewById(R.id.ll_foot_sock);
        llMode1 = view.findViewById(R.id.ll_mode1);
        llMode2 = view.findViewById(R.id.ll_mode2);
        llMode3 = view.findViewById(R.id.ll_mode3);
        llMin1 = view.findViewById(R.id.ll_min1);
        llMin2 = view.findViewById(R.id.ll_min2);
        llMin3 = view.findViewById(R.id.ll_min3);

        ivHeadSock = view.findViewById(R.id.iv_head_sock);
        ivLight = view.findViewById(R.id.iv_light);
        ivFootSock = view.findViewById(R.id.iv_foot_sock);
        ivMode1 = view.findViewById(R.id.iv_mode1);
        ivMode2 = view.findViewById(R.id.iv_mode2);
        ivMode3 = view.findViewById(R.id.iv_mode3);
        ivMin1 = view.findViewById(R.id.iv_min1);
        ivMin2 = view.findViewById(R.id.iv_min2);
        ivMin3 = view.findViewById(R.id.iv_min3);

        tvHeadSock = view.findViewById(R.id.tv_head_sock);
        tvLight = view.findViewById(R.id.tv_light);
        tvFootSock = view.findViewById(R.id.tv_foot_sock);
        tvMode1 = view.findViewById(R.id.tv_mode1);
        tvMode2 = view.findViewById(R.id.tv_mode2);
        tvMode3 = view.findViewById(R.id.tv_mode3);
        tvMin1 = view.findViewById(R.id.tv_min1);
        tvMin2 = view.findViewById(R.id.tv_min2);
        tvMin3 = view.findViewById(R.id.tv_min3);

        llHeadSock.setOnClickListener(this);
        llLight.setOnClickListener(this);
        llFootSock.setOnClickListener(this);
        llMode1.setOnClickListener(this);
        llMode2.setOnClickListener(this);
        llMode3.setOnClickListener(this);
        llMin1.setOnClickListener(this);
        llMin2.setOnClickListener(this);
        llMin3.setOnClickListener(this);

        list.add(new BedMode(BedMode.MODE_LIE, "平躺模式", MpeDataKey.FLAT.getKey()));
        list.add(new BedMode(BedMode.MODE_DEEP_SLEEP, "深睡模式", MpeDataKey.DEEPSLEEP.getKey()));
        list.add(new BedMode(BedMode.MODE_SLEEP, "助眠模式", MpeDataKey.SLEEPAID.getKey()));
        list.add(new BedMode(BedMode.MODE_YOGA, "瑜伽模式", MpeDataKey.YOGA.getKey()));
        list.add(new BedMode(BedMode.MODE_RELAX, "放松模式", MpeDataKey.RELAX.getKey()));
        list.add(new BedMode(BedMode.MODE_ARDER, "休闲模式", MpeDataKey.ARDER.getKey()));
        list.add(new BedMode(BedMode.MODE_OFFICE, "办公模式", MpeDataKey.OFFICE.getKey()));
        rvMode.setLayoutManager(new RecyclerViewNoBugLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvMode.addItemDecoration(new SpaceItemDecoration(0, 0, 0, 20));

        adapter = new CommonAdapter<BedMode>(getActivity(), R.layout.item_bed_lv, list) {

            @Override
            protected void convert(ViewHolder holder, BedMode bedMode, int position) {
                holder.setText(R.id.tv, bedMode.getName());
                switch (bedMode.getMode()) {
                    case BedMode.MODE_LIE:
                        holder.setImageResource(R.id.iv_tag, R.mipmap.ic_mpe_lie);
                        break;
                    case BedMode.MODE_DEEP_SLEEP:
                        holder.setImageResource(R.id.iv_tag, R.mipmap.ic_mpe_deep_sleep);
                        break;
                    case BedMode.MODE_SLEEP:
                        holder.setImageResource(R.id.iv_tag, R.mipmap.ic_mpe_sleep);
                        break;
                    case BedMode.MODE_YOGA:
                        holder.setImageResource(R.id.iv_tag, R.mipmap.ic_mpe_yoga);
                        break;
                    case BedMode.MODE_RELAX:
                        holder.setImageResource(R.id.iv_tag, R.mipmap.ic_mpe_relax);
                        break;
                    case BedMode.MODE_ARDER:
                        holder.setImageResource(R.id.iv_tag, R.mipmap.ic_mpe_casual);
                        break;
                    case BedMode.MODE_OFFICE:
                        holder.setImageResource(R.id.iv_tag, R.mipmap.ic_mpe_offic);
                        break;
                }
                holder.setOnClickListener(R.id.ll_item_bg, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isCanSend) {
                            return;
                        }
                        sendCmd(bedMode.getKey());
                    }
                });
                holder.setOnLongClickListener(R.id.ll_item_bg, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!isCanSend) {
                            return false;
                        }
                        switch (bedMode.getMode()) {
                            case BedMode.MODE_LIE:
                                sendCmd(MpeDataKey.FLAT_SAVE.getKey());
                                break;
                            case BedMode.MODE_DEEP_SLEEP:
                                sendCmd(MpeDataKey.DEEPSLEEP_SAVE.getKey());
                                break;
                            case BedMode.MODE_SLEEP:
                                sendCmd(MpeDataKey.SLEEPAID_SAVE.getKey());
                                break;
                            case BedMode.MODE_RELAX:
                                sendCmd(MpeDataKey.RELAX_SAVE.getKey());
                                break;
                            case BedMode.MODE_ARDER:
                                sendCmd(MpeDataKey.ARDER_SAVE.getKey());
                                break;
                            case BedMode.MODE_OFFICE:
                                sendCmd(MpeDataKey.WORK_SAVE.getKey());
                                break;
                        }
                        return true;
                    }
                });
            }
        };
        rvMode.setAdapter(adapter);
    }


    private void binderEvent() {

    }

    String curKey = "";
    long startTime = 0;
    long endTime = 0;
    boolean isCanSend = true;

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            String m = "";
            switch (v.getId()) {
                case R.id.ll_head_up:
                    m = MpeDataKey.HEADRISE.getKey();
                    break;
                case R.id.ll_head_down:
                    m = MpeDataKey.HEADDROP.getKey();
                    break;
                case R.id.ll_foot_up:
                    m = MpeDataKey.LEGHRISE.getKey();
                    break;
                case R.id.ll_foot_down:
                    m = MpeDataKey.LEGDROP.getKey();
                    break;
                case R.id.ll_haf_up:
                    m = MpeDataKey.FEETRISE.getKey();
                    break;
                case R.id.ll_haf_down:
                    m = MpeDataKey.FEETDROP.getKey();
                    break;
            }
            if (!TextUtils.isEmpty(curKey) && !curKey.equals(m) || !isCanSend) {
                return true;
            }
            curKey = m;
            Logger.d("TTTTT", m + "  " + event.getAction());
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startTime = System.currentTimeMillis();
                    sendCmd(curKey);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    endTime = System.currentTimeMillis();
                    if (endTime - startTime < 150) {
                        isCanSend = false;
                        handler.sendEmptyMessageDelayed(1, (150 - (endTime - startTime)));
                    } else {
                        sendCmd(MpeDataKey.STOP.getKey());
                    }
                    curKey = "";
                    break;
            }
            return false;
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    isCanSend = true;
                    sendCmd(MpeDataKey.STOP.getKey());
                    break;
            }
        }
    };

    @Override
    public void onReceiveMsg(MsgType msgType, YkMessage ykMessage) {
        super.onReceiveMsg(msgType, ykMessage);
        if (getActivity() != null && !getActivity().isFinishing()) {
            switch (msgType) {
                case MpeStatusReport:
                    MpeStatusReport report = (MpeStatusReport) ykMessage.getData();
                    if (report != null) {
                        bedStatus.setHead_sock(report.getStatus().getS_head() == 1);
                        bedStatus.setLight(report.getStatus().getLight() == 1);
                        bedStatus.setFoot_sock(report.getStatus().getS_leg() == 1);
                        bedStatus.setMode(report.getStatus().getModel());
                        bedStatus.setTime(report.getStatus().getS_duration());
                        setStatus();
                    }
                    break;
            }
        }
    }

    public void setVisibility(int type) {
    }

    @Override
    public void setKeyBackground() {
    }

    @Override
    public void onClick(View v) {
        if (!isCanSend) {
            return;
        }
        switch (v.getId()) {
            case R.id.ll_head_sock:
                bedStatus.setHead_sock(!bedStatus.isHead_sock());
                if (bedStatus.isHead_sock()) {
                    sendCmd(MpeDataKey.HEADRELAXON.getKey());
                } else {
                    sendCmd(MpeDataKey.HEADRELAXOFF.getKey());
                }
                setStatus();
                break;
            case R.id.ll_light:
                bedStatus.setLight(!bedStatus.isLight());
                if (bedStatus.isLight()) {
                    sendCmd(MpeDataKey.LIGHTON.getKey());
                } else {
                    sendCmd(MpeDataKey.LIGHTOFF.getKey());
                }
                setStatus();
                break;
            case R.id.ll_foot_sock:
                bedStatus.setFoot_sock(!bedStatus.isFoot_sock());
                if (bedStatus.isFoot_sock()) {
                    sendCmd(MpeDataKey.FEETRELAXON.getKey());
                } else {
                    sendCmd(MpeDataKey.FEETRELAXOFF.getKey());
                }
                setStatus();
                break;
            case R.id.ll_mode1:
                sendCmd(MpeDataKey.MODE1.getKey());
                bedStatus.setMode(1);
                setStatus();
                break;
            case R.id.ll_mode2:
                sendCmd(MpeDataKey.MODE2.getKey());
                bedStatus.setMode(2);
                setStatus();
                break;
            case R.id.ll_mode3:
                sendCmd(MpeDataKey.MODE3.getKey());
                bedStatus.setMode(3);
                setStatus();
                break;
            case R.id.ll_min1:
                sendCmd(MpeDataKey.MIN10.getKey());
                bedStatus.setTime(1);
                setStatus();
                break;
            case R.id.ll_min2:
                sendCmd(MpeDataKey.MIN20.getKey());
                bedStatus.setTime(2);
                setStatus();
                break;
            case R.id.ll_min3:
                sendCmd(MpeDataKey.MIN30.getKey());
                bedStatus.setTime(3);
                setStatus();
                break;
        }
    }
}
