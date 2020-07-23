package com.yaokantv.yaokanui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TypeListDialog extends Dialog {
    Context context;
    ListView listView;
    OnStringSelectedListener listener;
    CommonAdapter<String> adapter;
    List<String> list;

    public TypeListDialog(@NonNull Context context, List<String> data, OnStringSelectedListener listener) {
        this(context, R.style.dialog);
        this.context = context;
        this.listener = listener;
        list = new ArrayList<>();
        list.addAll(data);
    }

    public TypeListDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_type_list);
        listView = findViewById(R.id.dlg_remote_lv);
        adapter = new CommonAdapter<String>(context, list, R.layout.item_type_list) {
            @Override
            public void convert(ViewHolder helper, String item, int position) {
                helper.setText(R.id.item_name, item);
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onSelected(list.get(position));
                }
                dismiss();
            }
        });
    }


    public interface OnStringSelectedListener {
        void onSelected(String s);
    }
}