package com.yaokantv.yaokanui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jaeger.library.StatusBarUtil;
import com.yaokantv.sdkdemo.App;
import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.Contants;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.DeviceResult;
import com.yaokantv.yaokansdk.model.MatchingData;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.Logger;
import com.yaokantv.yaokanui.frag.BaseRcFragment;
import com.yaokantv.yaokanui.key.TabEntity;
import com.yaokantv.yaokanui.utils.ControlUtils;
import com.yaokantv.yaokanui.utils.ViewFindUtils;
import com.yaokantv.yaokanui.utils.YKAppManager;
import com.yaokantv.yaokanui.widget.TabLayout.CommonTabLayout;
import com.yaokantv.yaokanui.widget.TabLayout.listener.CustomTabEntity;
import com.yaokantv.yaokanui.widget.TabLayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class RcActivity extends BaseActivity implements View.OnClickListener {
    boolean isMatching = false;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.mipmap.tab_rc, R.mipmap.tab_stb};
    private int[] mIconSelectIds = {
            R.mipmap.tab_rc_select, R.mipmap.tab_stb_select};
    private String[] mTitles = {"遥控器"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;
    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout_2;
    BaseRcFragment frgRc;
    RelativeLayout showcontrol;
    TextView tvTips;
    RemoteCtrl rc;
    Button btnOk;
    int index = 0;
    int actType;
    List<MatchingData> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actType = getIntent().getIntExtra(Config.ACTIVITY_TYPE, 4);
        rcTid = getIntent().getIntExtra(Config.S_TID, -1);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("uuid"))) {
            uuid = getIntent().getStringExtra("uuid");
            rc = Yaokan.instance().getRcDataByUUID(uuid);
            if (rc != null) {
                rcTid = rc.getBe_rc_type();
            }
        }
        setContentView(R.layout.activity_rc_ui);
        reload();
        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SettingActivity.class);
                intent.putExtra("uuid", rc.getUuid());
                startActivityForResult(intent, 0);
            }
        });
    }

    boolean isRfStudy = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && data != null) {
            actType = Config.TYPE_RC_STUDY;
            showcontrol.setVisibility(View.VISIBLE);
            findViewById(R.id.rl_study).setVisibility(View.VISIBLE);
            findViewById(R.id.rl_match).setVisibility(View.GONE);
            findViewById(R.id.study_finish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check();
                }
            });
        }
    }

    private void studyFinish() {
        showSetting(true);
        showcontrol.setVisibility(View.GONE);
        actType = Config.TYPE_RC;
        Config.IS_MATCH = false;
        frgRc.saveRc();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(uuid)) {
            rc = Yaokan.instance().getRcDataByUUID(uuid);
            setRcTitle();
        } else if (rc != null && !TextUtils.isEmpty(rc.getUuid())) {
            rc = Yaokan.instance().getRcDataByUUID(rc.getUuid());
            setRcTitle();
        } else {
            setMTitle(Config.curBName + Config.curTName, TITLE_LOCATION_LEFT);
        }
    }

    private void setRcTitle() {
        if (rc == null) {
            return;
        }
        if (!TextUtils.isEmpty(rc.getPlace())) {
            setMTitle(rc.getPlace() + rc.getName(), TITLE_LOCATION_LEFT);
        } else {
            setMTitle(rc.getName(), TITLE_LOCATION_LEFT);
        }
    }

    public int getActivityType() {
        return actType;
    }

    boolean isRfDownload = false;

    @Override
    protected void reload() {
        if (actType == Config.TYPE_RC_RF_MATCH_STUDY) {
            showDlg();
            findViewById(R.id.rl_study).setVisibility(View.VISIBLE);
            findViewById(R.id.rl_match).setVisibility(View.GONE);
            findViewById(R.id.study_finish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check();
                }
            });
            if (isMpeDevice()) {
                Yaokan.instance().getRfMatchingResult(Config.MAC, rcTid, getIntent().getIntExtra(Config.S_BID, 0), getIntent().getStringExtra(Config.S_TAG));
            } else {
                Yaokan.instance().getRfMatchingResult(Config.MAC, rcTid, getIntent().getIntExtra(Config.S_BID, 0));
            }
            setRcTitle();
        } else if (actType == Config.TYPE_MATCHING) {
            showDlg();
            Yaokan.instance().getMatchingResult(rcTid, getIntent().getIntExtra(Config.S_BID, 0), getIntent().getIntExtra(Config.S_GID, 0));
        } else if (!TextUtils.isEmpty(getIntent().getStringExtra("uuid"))) {
            Config.IS_MATCH = false;
            showDlg();
            showSetting(true);
            if (actType == Config.TYPE_RC_STUDY) {
                findViewById(R.id.rl_study).setVisibility(View.VISIBLE);
                findViewById(R.id.rl_match).setVisibility(View.GONE);
            } else {
                showcontrol.setVisibility(View.GONE);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                            if (rc != null) {
                                setRcTitle();
                                frgRc.refreshRcData(rc);
                            }
                        }
                    });
                }
            }, 500);
        }
    }

    private boolean isMpeDevice() {
        return rcTid == 45 || rcTid == 46;
    }

    private void check() {
        YKAppManager.getAppManager().finishActivities(BrandListActivity.class, SelectDeviceActivity.class);
        if (rc.isRf()) {
            String mac = rc.getMac();
            if (TextUtils.isEmpty(mac)) {
                mac = App.curMac;
            }
            if (rc.getBe_rc_type() != 45 && rc.getBe_rc_type() != 46 && Yaokan.instance().isNeedDownloadDevice(mac)) {
                isRfStudy = true;
                dialog.setMessage(getString(R.string.download_to_big_apple));
                dialog.show();
                if (rc.getId() == 0) {
                    isRfDownload = true;
                    frgRc.saveRc();
                } else {
                    Yaokan.instance().downloadCodeToDevice(App.curDid, rc.getStudyId(), rc.getBe_rc_type());
                }
            } else if (rc.getBe_rc_type() == 45 || rc.getBe_rc_type() == 46) {
                isRfDownload = false;
                frgRc.saveRc();
            } else {
                studyFinish();
            }
        } else {
            studyFinish();
        }
    }

    @Override
    protected void initView() {
        setTopColor(rcTid);
        tvTips = findViewById(R.id.tv_tips);
        showcontrol = findViewById(R.id.showcontrol);
        btnOk = findViewById(R.id.btn_ok);
        frgRc = ControlUtils.getControlFragment(rcTid);
        mFragments.add(frgRc);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mDecorView = getWindow().getDecorView();
        mViewPager = ViewFindUtils.find(mDecorView, R.id.vp_2);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabLayout_2 = ViewFindUtils.find(mDecorView, R.id.tl_2);
        tl_2();
        setBtnTypeface((Button) findViewById(R.id.btn_pre_t), "\ue608");
        setBtnTypeface((Button) findViewById(R.id.btn_next_t), "\ue609");
//        if ("1".equals(rc.getBe_rc_type())) {
//
//        } else {
        mTabLayout_2.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setTransparent(this);
    }

    private void tl_2() {
        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (!isMatching)
                    mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_pre_t) {
            index--;
            if (index < 0) {
                index = dataList.size() - 1;
            }
            setMatchMsg();
        } else if (v.getId() == R.id.btn_next_t) {
            index++;
            if (index >= dataList.size()) {
                index = 0;
            }
            setMatchMsg();
        } else if (v.getId() == R.id.btn_ok) {
            rc.setName(Config.curBName + Config.curTName + " " + rc.getRmodel());
            rc.setMac(Config.MAC);
            if (rc.getBe_rc_type() == 1 && Config.operators != null) {//机顶盒有运营商数据
                rc.setProvider(Config.operators.getJson());
                Config.operators = null;
            }
            //设备列表中必须有此设备
            if (Yaokan.instance().isNeedToDownload(rc) && Yaokan.instance().isNeedDownloadDevice(rc.getMac())) {
                dialog.setMessage(getString(R.string.download_to_big_apple));
                dialog.show();
                if (!TextUtils.isEmpty(rc.getRid())) {
                    Yaokan.instance().downloadCodeToDevice(App.curDid, rc.getRid(), rc.getBe_rc_type());
                } else if (!TextUtils.isEmpty(rc.getStudyId())) {
                    Yaokan.instance().downloadCodeToDevice(App.curDid, rc.getStudyId(), rc.getBe_rc_type());
                }
            } else {
                saveTo();
            }
        }
    }

    private void saveTo() {
        boolean isSetRoom = false;
        if (rc.getId() == 0) {
            isSetRoom = true;
        }
        Yaokan.instance().saveRc(rc);
        showSetting(true);
        setRcTitle();
        YKAppManager.getAppManager().finishActivities(SelectProviderActivity.class,
                NewMatchActivity.class, SelectDeviceActivity.class, BrandListActivity.class);
        showcontrol.setVisibility(View.GONE);
        if (isSetRoom) {
            Intent intent = new Intent(activity, RoomMsgActivity.class);
            intent.putExtra("uuid", rc.getUuid());
            intent.putExtra("create", true);
            startActivity(intent);
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    public void onReceiveMsg(MsgType msgType, YkMessage ykMessage) {
        switch (msgType) {
            case SecondMatching:
                if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof List) {
                    dataList = (List) ykMessage.getData();
                    if (dataList != null && dataList.size() > 0) {
                        setMatchMsg();
                    }
                } else {
                    dismiss();
                }
                break;
            case RemoteInfo:
                if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof RemoteCtrl) {
                    rc = (RemoteCtrl) ykMessage.getData();
                    setRcData();
                }
                break;
            case DeviceOffline:
                toast(R.string.device_offline);
                break;
            case Other:
                finish();
                break;
            case RfUploadSuccess:
                rc = (RemoteCtrl) ykMessage.getData();
                if (isRfDownload) {
                    Yaokan.instance().downloadCodeToDevice(App.curDid, rc.getStudyId(), rc.getBe_rc_type());
                } else {
                    dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (rc.getBe_rc_type() == 45 || rc.getBe_rc_type() == 46) {
                                boolean b = getIntent().getBooleanExtra("create", false);
                                if (b) {
                                    Intent intent = new Intent(activity, RoomMsgActivity.class);
                                    intent.putExtra("uuid", rc.getUuid());
                                    intent.putExtra("create", true);
                                    startActivity(intent);
                                }
                            }
                            studyFinish();
                        }
                    });
                }
                break;
            case DownloadCode:
                if (!isFinishing()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final DeviceResult result = (DeviceResult) ykMessage.getData();
                            Logger.e("DownloadCode" + result.toString());
                            if (result != null && !TextUtils.isEmpty(App.curMac) && App.curMac.equals(result.getMac())) {
                                String msg = "";
                                switch (result.getCode()) {
                                    case Contants.YK_DOWNLOAD_CODE_RESULT_START_FAIL:
                                        dismiss();
                                        msg = "开启下载遥控器失败";
                                        break;
                                    case Contants.YK_DOWNLOAD_CODE_RESULT_START://开始下载遥控器
                                        Logger.e("开始下载遥控器");
                                        showDlg(120, "正在下载码库到设备...", new OnDownloadTimerOutListener() {
                                            @Override
                                            public void onTimeOut() {
                                                if (isFinishing()) {
                                                    return;
                                                }
                                                dismiss();
                                                DlgUtils.createDefDlg(activity, "下载超时");
                                            }
                                        });
                                        break;
                                    case Contants.YK_DOWNLOAD_CODE_RESULT_SUC://下载遥控器成功
                                        Logger.e("下载遥控器成功");
                                        dismiss();
                                        DlgUtils.createDefDlg(activity, "", "下载成功", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                timerCancel();
                                                if (isRfStudy) {
                                                    boolean b = getIntent().getBooleanExtra("create", false);
                                                    if (b || isRfDownload) {
                                                        isRfDownload = false;
                                                        Intent intent = new Intent(activity, RoomMsgActivity.class);
                                                        intent.putExtra("uuid", rc.getUuid());
                                                        intent.putExtra("create", true);
                                                        startActivity(intent);
                                                    }
                                                    studyFinish();
                                                } else {
                                                    saveTo();
                                                }
                                            }
                                        }, false);

                                        break;
                                    case Contants.YK_DOWNLOAD_CODE_RESULT_FAIL:
                                        msg = "下载遥控器失败";
                                        break;
                                    case Contants.YK_DOWNLOAD_CODE_RESULT_EXIST:
                                        msg = "遥控器已存在设备中";
                                        break;
                                    case Contants.YK_DOWNLOAD_CODE_RESULT_AIR_MAX:
                                        msg = "空调遥控器达到极限";
                                        break;
                                    case Contants.YK_DOWNLOAD_CODE_RESULT_IR_MAX:
                                        msg = "非空调遥控器达到极限";
                                        break;
                                    case Contants.YK_DOWNLOAD_CODE_RESULT_RF_MAX:
                                        msg = "射频遥控器达到极限";
                                        break;
                                    case Contants.YK_DOWNLOAD_CODE_RESULT_DOOR_MAX:
                                        msg = "门铃遥控器达到极限";
                                        break;
                                }
                                if (!TextUtils.isEmpty(msg)) {
                                    dismiss();
                                    DlgUtils.createDefDlg(activity, "", msg, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }, false);
                                }
                            }
                        }
                    });
                }
                break;
        }
    }

    private void setRcData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismiss();
                if (isMpeDevice()) {
                    setStudyTips(R.string.click_finish);
                }
                Config.IS_MATCH = true;
                if (rc != null && rc.getRcCmd() != null && 7 != rc.getBe_rc_type() && rc.getRcCmd().size() > 0) {
                    frgRc.refreshRcData(rc);
                } else if (rc != null && 7 == rc.getBe_rc_type() && rc.getAirCmd() != null) {
                    frgRc.refreshRcData(rc);
                }
            }
        });
    }

    MatchingData mMatch;

    public void setStudyTips(int id) {
        tvTips.setText(id);
    }

    private void setMatchMsg() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDlg();
                mMatch = dataList.get(index);
                String msg = Config.curBName + Config.curTName + (index + 1) + "/" + dataList.size();
                setMTitle(msg, TITLE_LOCATION_CENTER);
                match();
            }
        });
    }

    private void match() {
        if (mMatch != null) {
            Yaokan.instance().remoteInfo(mMatch.getRid(), rcTid);
        } else {
            dismiss();
        }

    }
}
