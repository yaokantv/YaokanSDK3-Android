package com.yaokantv.sdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RcListActivity extends BaseActivity  {
    ListView listView;
    CommonAdapter<RemoteCtrl> adapter;
    List<RemoteCtrl> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rc_list);
        initToolbar(R.string.rc_list);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteCtrl> list = Yaokan.instance().getRcList();
                mList.clear();
                if (list != null && list.size() > 0) {
                    mList.addAll(list);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void initView() {
        listView = findViewById(R.id.lv);
        adapter = new CommonAdapter<RemoteCtrl>(this, mList, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder helper, RemoteCtrl item, int position) {
                helper.setText(android.R.id.text1, item.getName());
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RcListActivity.this, RcActivity.class);
                intent.putExtra(RcActivity.RID, mList.get(position).getRid());
                startActivity(intent);
            }
        });
    }

}
