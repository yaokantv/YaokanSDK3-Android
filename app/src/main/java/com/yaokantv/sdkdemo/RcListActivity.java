package com.yaokantv.sdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RcListActivity extends BaseActivity {
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
        adapter = new CommonAdapter<RemoteCtrl>(this, mList, R.layout.lv_rc_item) {
            @Override
            public void convert(ViewHolder helper, RemoteCtrl item, int position) {
                helper.setText(R.id.text1, item.getName());
                helper.setText(R.id.text2, item.getMac());
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RcListActivity.this, RcActivity.class);
                intent.putExtra(RcActivity.UUID, mList.get(position).getUuid());
                startActivity(intent);
            }
        });
        toolbar.inflateMenu(R.menu.rc_list_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_all:
                        Yaokan.instance().deleteAllRcDevice();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

}
