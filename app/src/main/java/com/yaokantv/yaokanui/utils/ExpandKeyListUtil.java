package com.yaokantv.yaokanui.utils;

import com.yaokantv.yaokanui.key.AirMiniDataKey;
import com.yaokantv.yaokanui.key.AirPurifierRemoteControlDataKey;
import com.yaokantv.yaokanui.key.BoxRemoteControlDataKey;
import com.yaokantv.yaokanui.key.CtrlContants;
import com.yaokantv.yaokanui.key.CurtainRFDataKey;
import com.yaokantv.yaokanui.key.DVDRemoteControlDataKey;
import com.yaokantv.yaokanui.key.FannerRemoteControlDataKey;
import com.yaokantv.yaokanui.key.HangerRFDataKey;
import com.yaokantv.yaokanui.key.JackRFDataKey;
import com.yaokantv.yaokanui.key.LiangbaRFDataKey;
import com.yaokantv.yaokanui.key.LightRemoteControlDataKey;
import com.yaokantv.yaokanui.key.ProjectorRemoteControlDataKey;
import com.yaokantv.yaokanui.key.STBRemoteControlDataKey;
import com.yaokantv.yaokanui.key.SweeperRemoteControlDataKey;
import com.yaokantv.yaokanui.key.TVRemoteControlDataKey;
import com.yaokantv.yaokanui.key.WaterHeaterRemoteControlDataKey;

import java.util.ArrayList;
import java.util.List;

public class ExpandKeyListUtil {
    /**
     * 机顶盒
     */
    private List<String> mSTBList;
    /**
     * 电视机
     */
    private List<String> mTVList;
    private List<String> mLightList;
    private List<String> mBoxList;
    private List<String> mAudioList;
    /**
     * 扫地机
     */
    private List<String> mSweperList;
    /**
     * 风扇
     */
    private List<String> mFanList;
    /**
     * 净化器
     */
    private List<String> mAirPurifierList;
    /**
     * 投影仪
     */
    private List<String> mProList;
    /**
     * 投影仪
     */
    private List<String> mDVDList;
    private List<String> mWaterHeaterList;
    private List<String> mRfList;
    private List<String> mLbList;
    private List<String> mAirList;

    public ExpandKeyListUtil(int deviceType) {
        initExpandKeyList(deviceType);
    }

    private void initSTBExpandKeyList() {
        mSTBList = new ArrayList<>();

        mSTBList.add(STBRemoteControlDataKey.ZERO.getKey());
        mSTBList.add(STBRemoteControlDataKey.ONE.getKey());
        mSTBList.add(STBRemoteControlDataKey.TWO.getKey());
        mSTBList.add(STBRemoteControlDataKey.THREE.getKey());
        mSTBList.add(STBRemoteControlDataKey.FOUR.getKey());
        mSTBList.add(STBRemoteControlDataKey.FIVE.getKey());
        mSTBList.add(STBRemoteControlDataKey.SIX.getKey());
        mSTBList.add(STBRemoteControlDataKey.SEVEN.getKey());
        mSTBList.add(STBRemoteControlDataKey.EIGHT.getKey());
        mSTBList.add(STBRemoteControlDataKey.NINE.getKey());

        mSTBList.add(STBRemoteControlDataKey.OK.getKey());
        mSTBList.add(STBRemoteControlDataKey.UP.getKey());
        mSTBList.add(STBRemoteControlDataKey.DOWN.getKey());
        mSTBList.add(STBRemoteControlDataKey.LEFT.getKey());
        mSTBList.add(STBRemoteControlDataKey.RIGHT.getKey());


        mSTBList.add(STBRemoteControlDataKey.MUTE.getKey());
        mSTBList.add(STBRemoteControlDataKey.BACK.getKey());
        mSTBList.add(STBRemoteControlDataKey.MENU.getKey());
        mSTBList.add(STBRemoteControlDataKey.BOOT.getKey());
        mSTBList.add(STBRemoteControlDataKey.POWER.getKey());
        mSTBList.add(STBRemoteControlDataKey.TVPOWER.getKey());
        mSTBList.add(STBRemoteControlDataKey.CHANNEL_ADD.getKey());
        mSTBList.add(STBRemoteControlDataKey.CHANNEL_SUB.getKey());
        mSTBList.add(STBRemoteControlDataKey.VOLUME_ADD.getKey());
        mSTBList.add(STBRemoteControlDataKey.VOLUME_SUB.getKey());
        mSTBList.add(STBRemoteControlDataKey.SIGNAL.getKey());
        mSTBList.add(STBRemoteControlDataKey.PLAY.getKey());
        mSTBList.add(STBRemoteControlDataKey.FF.getKey());
        mSTBList.add(STBRemoteControlDataKey.REW.getKey());
        mSTBList.add(STBRemoteControlDataKey.STAR.getKey());
        mSTBList.add(STBRemoteControlDataKey.POUND.getKey());
        mSTBList.add(STBRemoteControlDataKey.EXIT.getKey());
//		mSTBList.add(STBRemoteControlDataKey.INFO.getKey());


//		mSTBList.add(STBRemoteControlDataKey.EXIT.getKey());
//		mSTBList.add(STBRemoteControlDataKey.GUIDE.getKey());
//		mSTBList.add(STBRemoteControlDataKey.EPG.getKey());
//		mSTBList.add(STBRemoteControlDataKey.SERVICE.getKey());
//		mSTBList.add(STBRemoteControlDataKey.TRACK.getKey());
//
//
//		mSTBList.add(STBRemoteControlDataKey.VOD.getKey());
//		mSTBList.add(STBRemoteControlDataKey.PRE.getKey());
//		mSTBList.add(STBRemoteControlDataKey.NEXT.getKey());
//		mSTBList.add(STBRemoteControlDataKey.FAVOURITE.getKey());
//		mSTBList.add(STBRemoteControlDataKey.INTERACTIVE.getKey());
//		mSTBList.add(STBRemoteControlDataKey.RED.getKey());
//		mSTBList.add(STBRemoteControlDataKey.GREEN.getKey());
//
//		mSTBList.add(STBRemoteControlDataKey.YELLOW.getKey());
//		mSTBList.add(STBRemoteControlDataKey.BLUE.getKey());
//		mSTBList.add(STBRemoteControlDataKey.INPUTMETHOD.getKey());
//		mSTBList.add(STBRemoteControlDataKey.LOOPPLAY.getKey());
//		mSTBList.add(STBRemoteControlDataKey.RECALL.getKey());
//		mSTBList.add(STBRemoteControlDataKey.LIVE.getKey());
//		mSTBList.add(STBRemoteControlDataKey.SET.getKey());
//		mSTBList.add(STBRemoteControlDataKey.LOCATION.getKey());
//
//		mSTBList.add(STBRemoteControlDataKey.F1.getKey());
//		mSTBList.add(STBRemoteControlDataKey.F2.getKey());
//		mSTBList.add(STBRemoteControlDataKey.F3.getKey());
//		mSTBList.add(STBRemoteControlDataKey.F4.getKey());
    }

    private void initTVExpandKeyList() {
        mTVList = new ArrayList<>();

        mTVList.add(TVRemoteControlDataKey.ONE.getKey());
        mTVList.add(TVRemoteControlDataKey.TWO.getKey());
        mTVList.add(TVRemoteControlDataKey.THREE.getKey());
        mTVList.add(TVRemoteControlDataKey.FOUR.getKey());
        mTVList.add(TVRemoteControlDataKey.FIVE.getKey());
        mTVList.add(TVRemoteControlDataKey.SIX.getKey());
        mTVList.add(TVRemoteControlDataKey.SEVEN.getKey());
        mTVList.add(TVRemoteControlDataKey.EIGHT.getKey());
        mTVList.add(TVRemoteControlDataKey.NINE.getKey());
        mTVList.add(TVRemoteControlDataKey.ZERO.getKey());
        mTVList.add(TVRemoteControlDataKey.VOLUME_ADD.getKey());
        mTVList.add(TVRemoteControlDataKey.VOLUME_SUB.getKey());
        mTVList.add(TVRemoteControlDataKey.CHANNEL_ADD.getKey());
        mTVList.add(TVRemoteControlDataKey.CHANNEL_SUB.getKey());
        mTVList.add(TVRemoteControlDataKey.BACK.getKey());
        mTVList.add(TVRemoteControlDataKey.INFO.getKey());
        mTVList.add(TVRemoteControlDataKey.MUTE.getKey());
        mTVList.add(TVRemoteControlDataKey.SIGNAL.getKey());
        mTVList.add(TVRemoteControlDataKey.POWER.getKey());
        mTVList.add(TVRemoteControlDataKey.UP.getKey());
        mTVList.add(TVRemoteControlDataKey.DOWN.getKey());
        mTVList.add(TVRemoteControlDataKey.LEFT.getKey());
        mTVList.add(TVRemoteControlDataKey.RIGHT.getKey());
        mTVList.add(TVRemoteControlDataKey.OK.getKey());
        mTVList.add(TVRemoteControlDataKey.MENU.getKey());
        mTVList.add(TVRemoteControlDataKey.EXIT.getKey());
        mTVList.add(TVRemoteControlDataKey.DIGIT.getKey());
//
//		mTVList.add(TVRemoteControlDataKey.REW.getKey());
//		mTVList.add(TVRemoteControlDataKey.PLAY.getKey());
//		mTVList.add(TVRemoteControlDataKey.FF.getKey());
//		mTVList.add(TVRemoteControlDataKey.PRE.getKey());
//		mTVList.add(TVRemoteControlDataKey.STOP.getKey());
//		mTVList.add(TVRemoteControlDataKey.NEXT.getKey());
//		mTVList.add(TVRemoteControlDataKey.PAUSE.getKey());
//
//		mTVList.add(TVRemoteControlDataKey.CHILDLOCK.getKey());
//		mTVList.add(TVRemoteControlDataKey.BROWSE.getKey());
//		mTVList.add(TVRemoteControlDataKey.TVSYS.getKey());
//		mTVList.add(TVRemoteControlDataKey.TVSWAP.getKey());
//		mTVList.add(TVRemoteControlDataKey.TIMER.getKey());
//		mTVList.add(TVRemoteControlDataKey.SOUND.getKey());
//		mTVList.add(TVRemoteControlDataKey.NICAM.getKey());
//
//		mTVList.add(TVRemoteControlDataKey.SPIN.getKey());
//		mTVList.add(TVRemoteControlDataKey.AUDIOMODEL.getKey());
//		mTVList.add(TVRemoteControlDataKey.SLEEP.getKey());
//		mTVList.add(TVRemoteControlDataKey.NICAM.getKey());
//		mTVList.add(TVRemoteControlDataKey.INFO.getKey());
//		mTVList.add(TVRemoteControlDataKey.IMAGEMODEL.getKey());
//		mTVList.add(TVRemoteControlDataKey.FAVOURITE.getKey());
//
//		mTVList.add(TVRemoteControlDataKey.SCREENSHOT.getKey());
//		mTVList.add(TVRemoteControlDataKey.DEL.getKey());
        mTVList.add(TVRemoteControlDataKey.BOOT.getKey());
    }

    private void initSweperExpandKeyList() {
        mSweperList = new ArrayList<>();
        mSweperList.add(SweeperRemoteControlDataKey.POWER.getKey());
        mSweperList.add(SweeperRemoteControlDataKey.UP.getKey());
        mSweperList.add(SweeperRemoteControlDataKey.DOWN.getKey());
        mSweperList.add(SweeperRemoteControlDataKey.LEFT.getKey());
        mSweperList.add(SweeperRemoteControlDataKey.RIGHT.getKey());
        mSweperList.add(SweeperRemoteControlDataKey.PAUSE.getKey());
        mSweperList.add(SweeperRemoteControlDataKey.CHARGE.getKey());
    }
    private void initAirMiniExpandKeyList() {
        mAirList = new ArrayList<>();
        mAirList.add(AirMiniDataKey.POWER.getKey());
        mAirList.add(AirMiniDataKey.TEMP_UP.getKey());
        mAirList.add(AirMiniDataKey.TEMP_DOWN.getKey());
        mAirList.add(AirMiniDataKey.FANSPEED.getKey());
        mAirList.add(AirMiniDataKey.MODE.getKey());
        mAirList.add(AirMiniDataKey.LRWIND.getKey());
        mAirList.add(AirMiniDataKey.UDWIND.getKey());
    }
    private void initBoxExpandKeyList() {
        mBoxList = new ArrayList<>();
        mBoxList.add(BoxRemoteControlDataKey.POWER.getKey());
        mBoxList.add(BoxRemoteControlDataKey.UP.getKey());
        mBoxList.add(BoxRemoteControlDataKey.DOWN.getKey());
        mBoxList.add(BoxRemoteControlDataKey.LEFT.getKey());
        mBoxList.add(BoxRemoteControlDataKey.RIGHT.getKey());
        mBoxList.add(BoxRemoteControlDataKey.SIGNAL.getKey());
        mBoxList.add(BoxRemoteControlDataKey.OK.getKey());
        mBoxList.add(BoxRemoteControlDataKey.TVPOWER.getKey());
        mBoxList.add(BoxRemoteControlDataKey.VOLUME_ADD.getKey());
        mBoxList.add(BoxRemoteControlDataKey.VOLUME_SUB.getKey());
        mBoxList.add(BoxRemoteControlDataKey.BOOT.getKey());
        mBoxList.add(BoxRemoteControlDataKey.BACK.getKey());
        mBoxList.add(BoxRemoteControlDataKey.MENU.getKey());
        mBoxList.add(BoxRemoteControlDataKey.MUTE.getKey());
    }

    private void initAudioExpandKeyList() {
        mAudioList = new ArrayList<>();
        mAudioList.add(DVDRemoteControlDataKey.POWER.getKey());
        mAudioList.add(DVDRemoteControlDataKey.SWICTH.getKey());
        mAudioList.add(DVDRemoteControlDataKey.STOP.getKey());
        mAudioList.add(DVDRemoteControlDataKey.UP.getKey());
        mAudioList.add(DVDRemoteControlDataKey.DOWN.getKey());
        mAudioList.add(DVDRemoteControlDataKey.LEFT.getKey());
        mAudioList.add(DVDRemoteControlDataKey.RIGHT.getKey());
        mAudioList.add(DVDRemoteControlDataKey.SIGNAL.getKey());
        mAudioList.add(DVDRemoteControlDataKey.OK.getKey());
        mAudioList.add(DVDRemoteControlDataKey.VOLUME_ADD.getKey());
        mAudioList.add(DVDRemoteControlDataKey.VOLUME_SUB.getKey());
        mAudioList.add(DVDRemoteControlDataKey.BACK.getKey());
        mAudioList.add(DVDRemoteControlDataKey.MENU.getKey());
        mAudioList.add(DVDRemoteControlDataKey.MUTE.getKey());
        mAudioList.add(DVDRemoteControlDataKey.PRE.getKey());
        mAudioList.add(DVDRemoteControlDataKey.PLAY.getKey());
        mAudioList.add(DVDRemoteControlDataKey.FF.getKey());
    }

    private void initFanExpandKeyList() {
        mFanList = new ArrayList<>();
        mFanList.add(FannerRemoteControlDataKey.POWER.getKey());
//		mFanList.add(FannerRemoteControlDataKey.POWEROFF.getKey());
//		mFanList.add(FannerRemoteControlDataKey.FANSPEED.getKey());
//		mFanList.add(FannerRemoteControlDataKey.OSCILLATION.getKey());
//		mFanList.add(FannerRemoteControlDataKey.TIMER.getKey());
//		mFanList.add(FannerRemoteControlDataKey.MODE.getKey());
    }

    private void initAirPurifierExpandKeyList() {
        mAirPurifierList = new ArrayList<>();
        mAirPurifierList.add(AirPurifierRemoteControlDataKey.FANSPEED.getKey());
        mAirPurifierList.add(AirPurifierRemoteControlDataKey.SLEEP.getKey());
        mAirPurifierList.add(AirPurifierRemoteControlDataKey.STERILIZE.getKey());
//        mAirPurifierList.add(AirPurifierRemoteControlDataKey.ANION.getKey());

        mAirPurifierList.add(AirPurifierRemoteControlDataKey.POWER.getKey());
        mAirPurifierList.add(AirPurifierRemoteControlDataKey.TIMER.getKey());
    }

    private void initProExpandKeyList() {
        mProList = new ArrayList<>();
        mProList.add(ProjectorRemoteControlDataKey.POWER.getKey());
        mProList.add(ProjectorRemoteControlDataKey.POWEROFF.getKey());

        mProList.add(ProjectorRemoteControlDataKey.SIGNAL.getKey());
        mProList.add(ProjectorRemoteControlDataKey.FOCUS_ADD.getKey());
        mProList.add(ProjectorRemoteControlDataKey.FOCUS_SUB.getKey());
        mProList.add(ProjectorRemoteControlDataKey.MENU.getKey());
        mProList.add(ProjectorRemoteControlDataKey.OK.getKey());

        mProList.add(ProjectorRemoteControlDataKey.UP.getKey());
        mProList.add(ProjectorRemoteControlDataKey.DOWN.getKey());
        mProList.add(ProjectorRemoteControlDataKey.LEFT.getKey());
        mProList.add(ProjectorRemoteControlDataKey.RIGHT.getKey());

        mProList.add(ProjectorRemoteControlDataKey.EXIT.getKey());
        mProList.add(ProjectorRemoteControlDataKey.VOL_ADD.getKey());
        mProList.add(ProjectorRemoteControlDataKey.VOL_SUB.getKey());
        mProList.add(ProjectorRemoteControlDataKey.MUTE.getKey());
        mProList.add(ProjectorRemoteControlDataKey.LIGHTNESS.getKey());
        mProList.add(ProjectorRemoteControlDataKey.BOOT.getKey());


        //mProList.add(ProjectorRemoteControlDataKey.PAUSE.getKey());
        //mProList.add(ProjectorRemoteControlDataKey.PRE.getKey());
        //mProList.add(ProjectorRemoteControlDataKey.NEXT.getKey());
        //mProList.add(ProjectorRemoteControlDataKey.KEYSTONE_ADD.getKey());
        //mProList.add(ProjectorRemoteControlDataKey.KEYSTONE_SUB.getKey());
    }

    private void initWaterHeaterExpandKeyList() {
        mWaterHeaterList = new ArrayList<>();
        mWaterHeaterList.add(WaterHeaterRemoteControlDataKey.POWER.getKey());
        mWaterHeaterList.add(WaterHeaterRemoteControlDataKey.TEMP_UP.getKey());
        mWaterHeaterList.add(WaterHeaterRemoteControlDataKey.TEMP_DOWN.getKey());
    }

    private void initLightExpandKeyList() {
        mLightList = new ArrayList<>();
        mLightList.add(LightRemoteControlDataKey.POWER.getKey());
        mLightList.add(LightRemoteControlDataKey.POWEROFF.getKey());
    }

    private void initRfExpandKeyList() {
        mRfList = new ArrayList<>();
        mRfList.add(JackRFDataKey.POWER.getKey());
        mRfList.add(JackRFDataKey.ON.getKey());
        mRfList.add(JackRFDataKey.OFF.getKey());
        mRfList.add(CurtainRFDataKey.OPEN.getKey());
        mRfList.add(CurtainRFDataKey.CLOSE.getKey());
        mRfList.add(CurtainRFDataKey.PAUSE.getKey());
        mRfList.add(HangerRFDataKey.RISE.getKey());
        mRfList.add(HangerRFDataKey.DROP.getKey());
        mRfList.add(HangerRFDataKey.STOP.getKey());
        mRfList.add(JackRFDataKey.H1.getKey());
        mRfList.add(JackRFDataKey.H2.getKey());
        mRfList.add(JackRFDataKey.H4.getKey());
        mRfList.add(JackRFDataKey.H8.getKey());
        mRfList.add(JackRFDataKey.LOW.getKey());
        mRfList.add(JackRFDataKey.MID.getKey());
        mRfList.add(JackRFDataKey.HIGH.getKey());
        mRfList.add(JackRFDataKey.LIGHT.getKey());
    }

    private void initLbExpandKeyList() {
        mLbList = new ArrayList<>();
        mLbList.add(LiangbaRFDataKey.PTWIND.getKey());
        mLbList.add(LiangbaRFDataKey.LOW.getKey());
        mLbList.add(LiangbaRFDataKey.MID.getKey());
        mLbList.add(LiangbaRFDataKey.HIGH.getKey());
        mLbList.add(LiangbaRFDataKey.TIME_DN.getKey());
        mLbList.add(LiangbaRFDataKey.TIME_UP.getKey());
        mLbList.add(LiangbaRFDataKey.LIGHT.getKey());
        mLbList.add(LiangbaRFDataKey.POWER.getKey());
        mLbList.add(LiangbaRFDataKey.SWING.getKey());
    }

    private void initDVDExpandKeyList() {
        mDVDList = new ArrayList<>();
        mDVDList.add(DVDRemoteControlDataKey.POWER.getKey());
        mDVDList.add(DVDRemoteControlDataKey.TVPOWER.getKey());
        mDVDList.add(DVDRemoteControlDataKey.SIGNAL.getKey());

        mDVDList.add(DVDRemoteControlDataKey.UP.getKey());
        mDVDList.add(DVDRemoteControlDataKey.DOWN.getKey());
        mDVDList.add(DVDRemoteControlDataKey.LEFT.getKey());
        mDVDList.add(DVDRemoteControlDataKey.RIGHT.getKey());
        mDVDList.add(DVDRemoteControlDataKey.OK.getKey());


        mDVDList.add(DVDRemoteControlDataKey.VOLUME_ADD.getKey());
        mDVDList.add(DVDRemoteControlDataKey.VOLUME_SUB.getKey());

        mDVDList.add(DVDRemoteControlDataKey.SWICTH.getKey());
        mDVDList.add(DVDRemoteControlDataKey.MUTE.getKey());

        mDVDList.add(DVDRemoteControlDataKey.MENU.getKey());
        mDVDList.add(DVDRemoteControlDataKey.BACK.getKey());
        mDVDList.add(DVDRemoteControlDataKey.BOOT.getKey());

        mDVDList.add(DVDRemoteControlDataKey.STOP.getKey());
        mDVDList.add(DVDRemoteControlDataKey.PLAY.getKey());
        mDVDList.add(DVDRemoteControlDataKey.PAUSE.getKey());

        mDVDList.add(DVDRemoteControlDataKey.REW.getKey());
        mDVDList.add(DVDRemoteControlDataKey.PRE.getKey());
        mDVDList.add(DVDRemoteControlDataKey.FF.getKey());
        mDVDList.add(DVDRemoteControlDataKey.NEXT.getKey());
    }

    private void initExpandKeyList(int deviceType) {
        switch (deviceType) {
            case CtrlContants.RemoteControlType.STB:
            case CtrlContants.RemoteControlType.IPTV:
            case CtrlContants.RemoteControlType.SATV:
                initSTBExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.TV:
                initTVExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.BOX:
                initBoxExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.AUDIO:
                initAudioExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.LIGHT:
                initLightExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.SWEEPER:
                initSweperExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.FANNER:
            case CtrlContants.RemoteControlType.FAN_RF:
                initFanExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.AIRPURIFIER:
                initAirPurifierExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.PROJECTOR:
                initProExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.DVD:
                initDVDExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.WATER_HEATER:
                initWaterHeaterExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.JACK_RF:
            case CtrlContants.RemoteControlType.SWITCH_RF:
            case CtrlContants.RemoteControlType.CURTAIN_RF:
            case CtrlContants.RemoteControlType.HANDGER_RF:
            case CtrlContants.RemoteControlType.LIGHT_CTRL:
            case CtrlContants.RemoteControlType.FAN_LIGHT:
                initRfExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.LIANGE_BA:
                initLbExpandKeyList();
                break;
            case CtrlContants.RemoteControlType.AIR_MINI:
                initAirMiniExpandKeyList();
                break;
        }

    }

    public List<String> getmLightList() {
        return mLightList;
    }

    public List<String> getmBoxList() {
        return mBoxList;
    }

    public List<String> getmAudioList() {
        return mAudioList;
    }

    public List<String> getmSTBList() {
        return mSTBList;
    }

    public List<String> getmTVList() {
        return mTVList;
    }

    public List<String> getmSweperList() {
        return mSweperList;
    }

    public List<String> getmFanList() {
        return mFanList;
    }

    public List<String> getmAirPurifierList() {
        return mAirPurifierList;
    }

    public List<String> getmProList() {
        return mProList;
    }

    public List<String> getmDVDList() {
        return mDVDList;
    }

    public List<String> getmWaterHeaterList() {
        return mWaterHeaterList;
    }

    public List<String> getmRfList() {
        return mRfList;
    }

    public List<String> getmLiangbaList() {
        return mLbList;
    }

    public List<String> getAirMiniList() {
        return mAirList;
    }
}
