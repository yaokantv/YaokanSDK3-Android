package com.yaokantv.yaokanui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yaokantv.sdkdemo.App;
import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.RfModels;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.ViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoadListActivity extends BaseActivity {
    private int tid, bid;
    List<RfModels> rfModels = new ArrayList<>();
    CommonAdapter<RfModels> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_list);
    }

    @Override
    protected void initView() {
        Yaokan.instance().deviceInfo(App.curDid);
        listView = findViewById(R.id.lv);
        tid = getIntent().getIntExtra("tid", 0);
        bid = getIntent().getIntExtra("bid", 0);
        Yaokan.instance().getGfskModels(tid, bid);
        adapter = new CommonAdapter<RfModels>(this, rfModels, R.layout.yk_ctrl_fname_listitem) {
            @Override
            public void convert(ViewHolder helper, RfModels item, int position) {
                helper.setVisibility(R.id.v_cc, View.GONE);
                helper.setVisibility(R.id.catalog, View.GONE);
                helper.setText(R.id.tv_showname, item.getModelName());
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RoadListActivity.this, SelectGfskModeActivity.class);
                intent.putExtra("road", rfModels.get(position).getModelId());
                intent.putExtra("tid", tid);
                intent.putExtra("bid", bid);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onReceiveMsg(final MsgType msgType,final YkMessage ykMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (msgType){
                    case RfModels:
                        rfModels.addAll((Collection<? extends RfModels>) ykMessage.getData());
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }

    @Override
    protected void reload() {

    }
}