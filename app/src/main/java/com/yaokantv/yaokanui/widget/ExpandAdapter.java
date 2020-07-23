package com.yaokantv.yaokanui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.RcCmd;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.utils.Utility;
import com.yaokantv.yaokanui.key.CtrlContants;
import com.yaokantv.yaokanui.utils.ExpandKeyListUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExpandAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater inflater;

    private int deviceType;

    private List<RcCmd> expandKeyRDatas = new ArrayList<>();

    private RemoteCtrl mRemoteControl;


    public ExpandAdapter(Context mContext, RemoteCtrl rc, List<RcCmd> rcCmds) {
        super();
        this.mContext = mContext;
        mRemoteControl = rc;
        inflater = LayoutInflater.from(mContext);
        expandKeyRDatas.addAll(rcCmds);
        deviceType = mRemoteControl.getBe_rc_type();
        getKey(this.deviceType);

    }

    private void getKey(int deviceType) {
        ExpandKeyListUtil expandKeyList = new ExpandKeyListUtil(deviceType);
        List<String> commonKeys = null;
        switch (deviceType) {
            case CtrlContants.RemoteControlType.STB:
            case CtrlContants.RemoteControlType.IPTV:
            case CtrlContants.RemoteControlType.SATV:
                commonKeys = expandKeyList.getmSTBList();
                break;
            case CtrlContants.RemoteControlType.TV:
                commonKeys = expandKeyList.getmTVList();
                break;
            case CtrlContants.RemoteControlType.SWEEPER:
                commonKeys = expandKeyList.getmSweperList();
                break;
            case CtrlContants.RemoteControlType.FANNER:
                commonKeys = expandKeyList.getmFanList();
                break;
            case CtrlContants.RemoteControlType.AIRPURIFIER:
                commonKeys = expandKeyList.getmAirPurifierList();
                break;
            case CtrlContants.RemoteControlType.PROJECTOR:
                commonKeys = expandKeyList.getmProList();
                break;
            case CtrlContants.RemoteControlType.AUDIO:
                commonKeys = expandKeyList.getmAudioList();
                break;
            case CtrlContants.RemoteControlType.LIGHT:
                commonKeys = expandKeyList.getmLightList();
                break;
            case CtrlContants.RemoteControlType.DVD:
                commonKeys = expandKeyList.getmDVDList();
                break;
            case CtrlContants.RemoteControlType.BOX:
                commonKeys = expandKeyList.getmBoxList();
                break;
            case CtrlContants.RemoteControlType.WATER_HEATER:
                commonKeys = expandKeyList.getmWaterHeaterList();
                break;
            case CtrlContants.RemoteControlType.LIANGE_BA:
                commonKeys = expandKeyList.getmLiangbaList();
                break;
            case CtrlContants.RemoteControlType.JACK_RF:
            case CtrlContants.RemoteControlType.SWITCH_RF:
            case CtrlContants.RemoteControlType.CURTAIN_RF:
            case CtrlContants.RemoteControlType.HANDGER_RF:
            case CtrlContants.RemoteControlType.LIGHT_CTRL:
            case CtrlContants.RemoteControlType.FAN_LIGHT:
                commonKeys = expandKeyList.getmRfList();
                break;
            case CtrlContants.RemoteControlType.AIR_MINI:
                commonKeys = expandKeyList.getAirMiniList();
                break;
        }
        if (commonKeys == null) {
            commonKeys = new ArrayList<>();
        }
        if (!Utility.isEmpty(expandKeyRDatas)) {
            Iterator<RcCmd> iterator = expandKeyRDatas.iterator();
            while (iterator.hasNext()) {
                RcCmd cmd = iterator.next();
                if (cmd.getValue().contains("_r") || commonKeys.contains(cmd.getValue())) {
                    iterator.remove();
                }
            }
        }

    }

    public List<RcCmd> getExpandKeys() {
        return expandKeyRDatas;
    }

    @Override
    public int getCount() {
        return expandKeyRDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return expandKeyRDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.yk_ctrl_adapter_expand, null);
        }
        TextView keyBtn = convertView.findViewById(R.id.key_btn);
        String keyName = expandKeyRDatas.get(position).getName();
        keyBtn.setText(keyName);
        return convertView;
    }

}
