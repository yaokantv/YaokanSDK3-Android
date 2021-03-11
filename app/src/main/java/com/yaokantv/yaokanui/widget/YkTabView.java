package com.yaokantv.yaokanui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yaokantv.sdkdemo.R;

import java.util.ArrayList;

public class YkTabView extends LinearLayout implements View.OnClickListener {
    ArrayList<TextView> views = new ArrayList<>();
    Context context;
    OnTabClickListener onTabClickListener;

    public YkTabView(Context context) {
        this(context, null);
    }

    public YkTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        if (child instanceof TextView) {
            int index = getChildCount();
            child.setTag(index);
            if(index==1){
                ((TextView) child).setTextColor(context.getResources().getColor(R.color.white));
                ((TextView) child).setBackgroundResource(R.drawable.shape_yk_tab_selected);
            }else{
                ((TextView) child).setTextColor(context.getResources().getColor(R.color.tab_indicator_textview));
                ((TextView) child).setBackgroundResource(R.drawable.shape_yk_tab_unselected);
            }
            child.setOnClickListener(this);
            views.add((TextView) child);
        }
    }

    @Override
    public void onClick(View v) {
        for (TextView textView : views) {
            textView.setTextColor(context.getResources().getColor(R.color.tab_indicator_textview));
            textView.setBackgroundResource(R.drawable.shape_yk_tab_unselected);
            if (((Integer) textView.getTag()) == ((Integer) v.getTag())) {
                textView.setTextColor(context.getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.shape_yk_tab_selected);
            }
        }
        if (onTabClickListener != null) {
            onTabClickListener.onTabClick(v, (Integer) v.getTag());
        }
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    public interface OnTabClickListener {
        void onTabClick(View v, int position);
    }

}
