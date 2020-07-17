package com.yaokantv.yaokanui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.Brand;
import com.yaokantv.yaokansdk.model.BrandResult;
import com.yaokantv.yaokansdk.model.Results;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokanui.widget.CharacterParser;
import com.yaokantv.yaokanui.widget.FnameAdapter;
import com.yaokantv.yaokanui.widget.PinyinComparator;
import com.yaokantv.yaokanui.widget.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BrandListActivity extends BaseActivity {
    private ListView fnamesLv;
    private List<Results> fnames = new ArrayList<>();
    private FnameAdapter mFnameAdapter;
    private SideBar sideBar;
    private int tid = 0;
    private boolean isRf = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list);
        setMTitle(R.string.title_select_brand, TITLE_LOCATION_CENTER);
        tid = getIntent().getIntExtra(Config.S_TID, 0);
        isRf = getIntent().getBooleanExtra(Config.S_IS_RF, false);
        reload();
    }

    @Override
    protected void initView() {
        fnamesLv = findViewById(R.id.fname_list);
        sideBar = findViewById(R.id.sidrbar);
    }

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        if (msgType == MsgType.Brands) {
            PinyinComparator pinyinComparator = new PinyinComparator();
            List<Results> list = new ArrayList<>();
            BrandResult brandResult = (BrandResult) ykMessage.getData();
            for (Brand brand : brandResult.getResult()) {
                list.add(new Results(brand.getName(), brand.getBid()));
            }
            list = filledData(list);
            fnames.addAll(list);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                    mFnameAdapter = new FnameAdapter(BrandListActivity.this, fnames);
                    fnamesLv.setAdapter(mFnameAdapter);
                    sideBar.setVisibility(View.VISIBLE);
                    setOnTouch();
                }
            });
        }
    }

    private void setOnTouch() {
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = mFnameAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    fnamesLv.setSelection(position);
                }
            }
        });
        fnamesLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Results results = fnames.get(position);
                if (results != null) {
                    Config.curBName = results.getName();
                    if (tid == 7||isRf) {
                        Intent intent = new Intent(activity, RcActivity.class);
                        intent.putExtra(Config.ACTIVITY_TYPE, Config.TYPE_MATCHING);
                        intent.putExtra(Config.S_TID, tid);
                        intent.putExtra(Config.S_BID, results.getBid());
                        intent.putExtra(Config.S_GID, 0);
                        intent.putExtra(Config.S_IS_RF, true);
                        if(isRf){
                            intent.putExtra(Config.ACTIVITY_TYPE, Config.TYPE_RC_RF_MATCH_STUDY);
                        }
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(activity, NewMatchActivity.class);
                        intent.putExtra(Config.S_BID, results.getBid());
                        intent.putExtra(Config.S_TID, tid);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public List<Results> filledData(List<Results> listResult) {
        CharacterParser characterParser = new CharacterParser();
        List<Results> mSortList = new ArrayList<Results>();
        for (int i = 0; i < listResult.size(); i++) {
            Results sortModel = listResult.get(i);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(sortModel.getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
                sortModel.setCp(sortString.toUpperCase()+pinyin);
            } else if (sortString.equals("@")) {
                sortModel.setSortLetters("@");
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        // 对每个字母分类的数据按名称排序
        Collections.sort(mSortList, new Comparator<Results>() {
            @Override
            public int compare(Results r1, Results r2) {
                if(!TextUtils.isEmpty(r1.getCp())&&!TextUtils.isEmpty(r2.getCp())){
                    return r1.getCp().compareTo(r2.getCp());
                }else{
                    return r1.getName().compareTo(r2.getName());
                }
            }
        });
        return mSortList;
    }

    @Override
    protected void reload() {
        showDlg();
        Yaokan.instance().getBrandsByType(tid);
    }

}
