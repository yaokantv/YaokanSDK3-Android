package com.yaokantv.yaokanui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yaokantv.sdkdemo.App;
import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
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
        setMTitle(R.string.rc_list, TITLE_LOCATION_CENTER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reload();
    }

    @Override
    protected void initView() {
        listView = findViewById(R.id.lv);
        adapter = new CommonAdapter<RemoteCtrl>(this, mList, R.layout.lv_rc_item) {
            @Override
            public void convert(ViewHolder helper, RemoteCtrl item, int position) {
                helper.setText(R.id.text1, item.getPlace() + item.getName());
                helper.setText(R.id.text2, item.getMac());
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String s = Yaokan.instance().exportRcString(mList.get(position).getUuid());
//                String d = Yaokan.instance().exportDeviceListStringFromDB();
                Intent intent = new Intent(RcListActivity.this, RcActivity.class);
                intent.putExtra("uuid", mList.get(position).getUuid());
                startActivity(intent);
            }
        });
        showSetting(R.mipmap.add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SelectDeviceActivity.class);
                intent.putExtra("isRf", "1".equals(App.curRf));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onReceiveMsg(MsgType msgType, YkMessage ykMessage) {

    }

    @Override
    protected void reload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteCtrl> list = Yaokan.instance().getRcList(App.curMac);
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

}
