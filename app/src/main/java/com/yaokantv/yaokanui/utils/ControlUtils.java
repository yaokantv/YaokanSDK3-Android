package com.yaokantv.yaokanui.utils;


import com.yaokantv.yaokanui.frag.BaseRcFragment;
import com.yaokantv.yaokanui.frag.RcAirFragment;
import com.yaokantv.yaokanui.frag.RcAudioFragment;
import com.yaokantv.yaokanui.frag.RcBoxFragment;
import com.yaokantv.yaokanui.frag.RcCameraFragment;
import com.yaokantv.yaokanui.frag.RcCurtainFragment;
import com.yaokantv.yaokanui.frag.RcDVDFragment;
import com.yaokantv.yaokanui.frag.RcFanFragment;
import com.yaokantv.yaokanui.frag.RcFanLightFragment;
import com.yaokantv.yaokanui.frag.RcFanRfFragment;
import com.yaokantv.yaokanui.frag.RcHangerFragment;
import com.yaokantv.yaokanui.frag.RcHeaterFragment;
import com.yaokantv.yaokanui.frag.RcLiangbaFragment;
import com.yaokantv.yaokanui.frag.RcLightFragment;
import com.yaokantv.yaokanui.frag.RcProjectorFragment;
import com.yaokantv.yaokanui.frag.RcPurifierFragment;
import com.yaokantv.yaokanui.frag.RcStbFragment;
import com.yaokantv.yaokanui.frag.RcSweeperFragment;
import com.yaokantv.yaokanui.frag.RcSwitchFragment;
import com.yaokantv.yaokanui.frag.RcTvFragment;

public class ControlUtils {
    public static BaseRcFragment getControlFragment(int type) {
        BaseRcFragment sf;
        switch (type) {
            case 1:
            case 11:
                sf = new RcStbFragment();
                break;
            case 2:
                sf = new RcTvFragment();
                break;
            case 3:
                sf = new RcDVDFragment();
                break;
            case 5:
                sf = new RcProjectorFragment();
                break;
            case 6:
                sf = new RcFanFragment();
                break;
            case 7:
                sf = new RcAirFragment();
                break;
            case 8:
                sf = new RcLightFragment();
                break;
            case 10:
                sf = new RcBoxFragment();
                break;
            case 12:
                sf = new RcSweeperFragment();
                break;
            case 13:
                sf = new RcAudioFragment();
                break;
            case 14:
                sf = new RcCameraFragment();
                break;
            case 15:
                sf = new RcPurifierFragment();
                break;
            case 23:
                sf = new RcCurtainFragment();
                break;
            case 24:
                sf = new RcHangerFragment();
                break;
            case 21:
            case 22:
            case 25:
                sf = new RcSwitchFragment();
                break;
            case 40:
                sf = new RcHeaterFragment();
                break;
            case 38:
                sf = new RcFanLightFragment();
                break;
            case 41:
                sf = new RcLiangbaFragment();
                break;
            case 42:
                sf = new RcFanRfFragment();
                break;
            default:
                sf = new RcStbFragment();
                break;
        }
        return sf;
    }
}
