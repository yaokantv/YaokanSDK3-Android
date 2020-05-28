package com.yaokantv.sdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.Area;
import com.yaokantv.yaokansdk.model.ListType;
import com.yaokantv.yaokansdk.model.Operators;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SelectProviderActivity extends BaseActivity implements View.OnClickListener, YaokanSDKListener {
    private ListView lvProvince, lvCity, lvProvider;
    private CommonAdapter<Area> provinceAdapter, cityAdapter;
    private CommonAdapter<Operators> providerAdapter;
    private List<Area> cityList = new ArrayList<>();
    private List<Area> provinceList = new ArrayList<>();
    private List<Operators> providerList = new ArrayList<>();
    private int proIndex, cityIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_provider);
        initToolbar(R.string.tv_provider);
        Yaokan.instance().addSdkListener(this);
        initView();
        showDlg();
        loadProvinceList();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Yaokan.instance().removeSdkListener(this);
    }

    private void initView() {
        lvProvince = findViewById(R.id.lv_province);
        lvCity = findViewById(R.id.lv_city);
        lvProvider = findViewById(R.id.lv_provider);
        provinceAdapter = new CommonAdapter<Area>(this, provinceList, R.layout.item_text_lv) {
            @Override
            public void convert(ViewHolder helper, Area item, int position) {
                helper.setText(R.id.tv, item.getName());
                TextView textView = helper.getView(R.id.tv);
                if (position == proIndex) {
                    textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    textView.setSingleLine(true);
                    textView.setSelected(true);
                    textView.setFocusable(true);
                    textView.setFocusableInTouchMode(true);
                    helper.setBgResource(R.id.ll_item_bg, R.color.main_ctrl_btn_color);
                    textView.setTextColor(getResources().getColor(R.color.white));
                } else {
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                    textView.setSelected(false);
                    textView.setFocusable(false);
                    textView.setFocusableInTouchMode(false);
                    helper.setBgResource(R.id.ll_item_bg, R.color.transparent);
                    textView.setTextColor(getResources().getColor(R.color.black));
                }
            }
        };
        cityAdapter = new CommonAdapter<Area>(this, cityList, R.layout.item_text_lv) {
            @Override
            public void convert(ViewHolder helper, Area item, int position) {
                helper.setText(R.id.tv, item.getName());
                TextView textView = helper.getView(R.id.tv);
                if (position == cityIndex) {
                    textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    textView.setSingleLine(true);
                    textView.setSelected(true);
                    textView.setFocusable(true);
                    textView.setFocusableInTouchMode(true);
                    helper.setBgResource(R.id.ll_item_bg, R.color.main_ctrl_btn_color);
                    textView.setTextColor(getResources().getColor(R.color.white));
                } else {
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                    textView.setSelected(false);
                    textView.setFocusable(false);
                    textView.setFocusableInTouchMode(false);
                    helper.setBgResource(R.id.ll_item_bg, R.color.transparent);
                    textView.setTextColor(getResources().getColor(R.color.black));
                }
            }
        };
        providerAdapter = new CommonAdapter<Operators>(this, providerList, R.layout.item_text_lv) {
            @Override
            public void convert(ViewHolder helper, Operators item, int position) {
                helper.setText(R.id.tv, item.getName());
                TextView textView = helper.getView(R.id.tv);
                if (position == mPosition) {
                    textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    textView.setSingleLine(true);
                    textView.setSelected(true);
                    textView.setFocusable(true);
                    textView.setFocusableInTouchMode(true);
                    helper.setBgResource(R.id.ll_item_bg, R.color.main_ctrl_btn_color);
                    textView.setTextColor(getResources().getColor(R.color.white));
                } else {
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                    textView.setSelected(false);
                    textView.setFocusable(false);
                    textView.setFocusableInTouchMode(false);
                    helper.setBgResource(R.id.ll_item_bg, R.color.transparent);
                    textView.setTextColor(getResources().getColor(R.color.black));
                }
            }
        };
        lvProvince.setAdapter(provinceAdapter);
        lvCity.setAdapter(cityAdapter);
        lvProvider.setAdapter(providerAdapter);

        lvProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (provinceList.size() > position) {
                    proIndex = position;
                    cityIndex = 0;
                    mPosition = 0;
                    notifyF(ListType.Province);
                    showDlg();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Area area = provinceList.get(position);
                            loadCityList(area);
                        }
                    }).start();
                }
            }
        });
        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (cityList.size() > position) {
                    cityIndex = position;
                    mPosition = 0;
                    notifyF(ListType.City);
                    showDlg();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Area area = cityList.get(position);
                            loadProvider(area);
                        }
                    }).start();
                }
            }
        });
        lvProvider.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                notifyF(ListType.Provider);
            }
        });
    }

    int mPosition = 0;

    private void loadProvinceList() {
        Yaokan.instance().getArea(0);
    }

    private void loadCityList(Area mArea) {
        Yaokan.instance().getArea(mArea.getAreaId());
    }

    private void loadProvider(Area mArea) {
        Yaokan.instance().getProvidersByAreaId(mArea.getAreaId());
    }

    void notifyF(final ListType i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (i) {
                    case Non:
                        dismiss();
                        break;
                    case Province:
                        provinceAdapter.notifyDataSetChanged();
                        if (proIndex != 0) {
                            lvProvince.setSelection(proIndex);
                            lvProvince.smoothScrollToPosition(proIndex);
                        }
                        break;
                    case City:
                        cityAdapter.notifyDataSetChanged();
                        if (cityIndex != 0) {
                            lvCity.setSelection(cityIndex);
                            lvCity.smoothScrollToPosition(cityIndex);
                        }
                        break;
                    case Provider:
                        providerAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (providerList.size() > mPosition) {
                    Operators operators = providerList.get(mPosition);
                    App.curBName = operators.getName();
                    App.curTid = 1;
                    App.curBid = operators.getBid();
                    App.operators = operators;
                    startActivity(new Intent(this, MatchActivity.class));
                }

                break;
        }
    }

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        switch (msgType) {
            case Province:
                if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof List) {
                    List<Area> areaResult = (List) ykMessage.getData();
                    provinceList.clear();
                    provinceList.addAll(areaResult);
                    notifyF(ListType.Province);
                    loadCityList(areaResult.get(0));
                } else {
                    notifyF(ListType.Non);
                }
                break;
            case City:
                if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof List) {
                    List<Area> areaResult = (List) ykMessage.getData();
                    cityList.clear();
                    cityList.addAll(areaResult);
                    notifyF(ListType.City);
                    loadProvider(areaResult.get(0));
                } else {
                    notifyF(ListType.Non);
                }
                break;
            case Provider:
                if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof List) {
                    List<Operators> areaResult = (List) ykMessage.getData();
                    providerList.clear();
                    providerList.addAll(areaResult);
                    notifyF(ListType.Provider);
                }
                notifyF(ListType.Non);
                break;
        }
    }
}
