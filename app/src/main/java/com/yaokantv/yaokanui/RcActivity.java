package com.yaokantv.yaokanui;

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
import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.MatchingData;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokanui.bean.UiRc;
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
    BaseRcFragment frgRc ;
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
                Bundle bundle = new Bundle();
                bundle.putParcelable(Config.S_GID, UiRc.transRc(rc));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

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
                    showcontrol.setVisibility(View.GONE);
                    actType = Config.TYPE_RC;
                    Config.IS_MATCH = false;
                    frgRc.saveRc();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(uuid)) {
            rc = Yaokan.instance().getRcDataByUUID(uuid);
            setMTitle(rc.getName(), TITLE_LOCATION_LEFT);
        }
    }

    public int getActivityType() {
        return actType;
    }

    @Override
    protected void reload() {
        if (actType == Config.TYPE_RC_RF_MATCH_STUDY) {
            showDlg();
            findViewById(R.id.rl_study).setVisibility(View.VISIBLE);
            findViewById(R.id.rl_match).setVisibility(View.GONE);
            findViewById(R.id.study_finish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YKAppManager.getAppManager().finishActivities(BrandListActivity.class, SelectDeviceActivity.class);
                    showcontrol.setVisibility(View.GONE);
                    showSetting(true);
                    actType = Config.TYPE_RC;
                    Config.IS_MATCH = false;
                    frgRc.saveRc();
                }
            });
            Yaokan.instance().getRfMatchingResult(Config.MAC, rcTid, getIntent().getIntExtra(Config.S_BID, 0));
            setMTitle(Config.curBName + Config.curTName, TITLE_LOCATION_LEFT);
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
                                setMTitle(rc.getName(), TITLE_LOCATION_LEFT);
                                frgRc.refreshRcData(rc);
                            }
                        }
                    });
                }
            }, 500);
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
            Yaokan.instance().saveRc(rc);
            showSetting(true);
            setMTitle(rc.getName(), TITLE_LOCATION_LEFT);
            YKAppManager.getAppManager().finishActivities(SelectProviderActivity.class,
                    NewMatchActivity.class, SelectDeviceActivity.class, BrandListActivity.class);
            showcontrol.setVisibility(View.GONE);
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
                dismiss();
                break;
        }
    }

    private void setRcData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismiss();
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
