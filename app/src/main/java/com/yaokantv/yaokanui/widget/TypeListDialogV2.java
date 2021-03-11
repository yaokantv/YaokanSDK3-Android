package com.yaokantv.yaokanui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.Alias;
import com.yaokantv.yaokansdk.model.SelectItem;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TypeListDialogV2<T extends SelectItem> extends Dialog {
    Context context;
    ListView listView;
    OnStringSelectedListener listener;
    CommonAdapter<T> adapter;
    List<T> list;
    String name;

    public TypeListDialogV2(@NonNull Context context, List<T> data, OnStringSelectedListener listener, String name) {
        this(context, data, listener);
        this.name = name;
    }

    public TypeListDialogV2(@NonNull Context context, List<T> data, OnStringSelectedListener listener) {
        this(context, R.style.dialog);
        this.context = context;
        this.listener = listener;
        list = new ArrayList<>();
        list.addAll(data);
    }

    public TypeListDialogV2(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_type_list);
        listView = findViewById(R.id.dlg_remote_lv);
        adapter = new CommonAdapter<T>(context, list, R.layout.item_type_list) {
            @Override
            public void convert(ViewHolder helper, T item, int position) {
                helper.setText(R.id.item_name, item.getDisplay());
                ImageView iv = helper.getView(R.id.iv);
                if (item.getDisplay().equals(name)) {
                    iv.setBackgroundResource(R.mipmap.item_t_select);
                } else {
                    iv.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                }
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
        void onSelected(SelectItem s);
    }
}
