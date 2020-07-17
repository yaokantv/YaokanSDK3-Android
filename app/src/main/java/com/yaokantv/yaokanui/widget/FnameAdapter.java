package com.yaokantv.yaokanui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.model.Results;

import java.util.List;

public class FnameAdapter extends BaseAdapter implements SectionIndexer {

    private Context context;

    private LayoutInflater inflater;
    private List<Results> fnames;


    public FnameAdapter(Context mContext) {
        this.context = mContext;
        inflater = LayoutInflater.from(context);
    }

    public FnameAdapter(Context mContext, List<Results> resultsts) {
        this.context = mContext;
        inflater = LayoutInflater.from(context);
        fnames = resultsts;
    }

    public void setFnames(List<Results> fnames) {
        this.fnames = fnames;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fnames.size();
    }

    @Override
    public Object getItem(int position) {
        return fnames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HodlerView hodler = new HodlerView();
        Results results = fnames.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.yk_ctrl_fname_listitem, null);
            hodler.showName = convertView.findViewById(R.id.tv_showname);
            hodler.tvLetter = convertView.findViewById(R.id.catalog);
            hodler.vCC = convertView.findViewById(R.id.v_cc);
            hodler.vCCD = convertView.findViewById(R.id.v_ccd);
            convertView.setTag(hodler);
        } else {
            hodler = (HodlerView) convertView.getTag();
        }
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            hodler.tvLetter.setVisibility(View.VISIBLE);
            hodler.vCC.setVisibility(View.VISIBLE);
            hodler.vCCD.setVisibility(View.VISIBLE);
            if (results.getSortLetters().equals("@")) {
                hodler.tvLetter.setText("");
            } else {
                hodler.tvLetter.setText(results.getSortLetters());
            }
        } else {
            hodler.tvLetter.setVisibility(View.GONE);
            hodler.vCC.setVisibility(View.GONE);
            hodler.vCCD.setVisibility(View.GONE);
        }
        hodler.showName.setText(results.getName().toString().replace("@", ""));
        hodler.showName.setTag(results);
        return convertView;
    }

    class HodlerView {
        TextView showName;
        TextView tvLetter;
        View vCC;
        View vCCD;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = fnames.get(i).getSortLetters();
            if (TextUtils.isEmpty(sortStr)) {
                return -1;
            }
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        if (fnames == null || fnames.size() == 0 || TextUtils.isEmpty(fnames.get(position).getSortLetters())) {
            return 'A';
        }
        return fnames.get(position).getSortLetters().charAt(0);
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
