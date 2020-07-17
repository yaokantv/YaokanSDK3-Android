package com.yaokantv.yaokanui.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yaokantv.sdkdemo.R;

public class ToastUtils {
    public static Toast showToastFree(Context ctx, String str) {
        Toast toast = Toast.makeText(ctx, str, Toast.LENGTH_SHORT);
        LinearLayout toastView = (LinearLayout)
                LayoutInflater.from(ctx).inflate(R.layout.toast_hor_view, null);
        TextView tv = toastView.findViewById(R.id.toast_tv);
        tv.setText(str);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setView(toastView);
        toast.show();
        return toast;
    }
}
