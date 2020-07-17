package com.yaokantv.yaokanui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;
import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.callback.YaokanSDKListener;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokanui.utils.ToastUtils;
import com.yaokantv.yaokanui.utils.YKAppManager;

public abstract class BaseActivity extends AppCompatActivity implements YaokanSDKListener {
    protected static final int TITLE_LOCATION_LEFT = 0;
    protected static final int TITLE_LOCATION_CENTER = 1;
    ProgressDialog dialog;
    ProgressDialog progressDialog;
    static final String TAG = "YaokanSDK";
    protected String[] rcItem = {"删除"};
    protected String[] rcListItem = {"查询小苹果内的遥控器"};
    Activity activity;
    protected int rcTid;
    protected String uuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Yaokan.instance().addSdkListener(this);
        activity = this;
        dialog = new ProgressDialog(this);
        progressDialog = new ProgressDialog(this);
        YKAppManager.getAppManager().addActivity(activity);
    }


    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Yaokan.instance().removeSdkListener(this);
        YKAppManager.getAppManager().finishActivity(activity);
    }

    protected void setTopColor(int type) {
        View view = findViewById(R.id.top);
        if (view == null) {
            return;
        }
        if (type == 6 || type == 15 || type == 40 || type == 14 || type == 8 || type == 7 || type == 21
                || type == 22 || type == 24 || type == 25 || type == 23 || type == 38 || type == 41) {
            view.setBackgroundResource(R.color.top_gray_deep);
//            StatusBarUtil.setTranslucent(this);
            StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.top_gray_deep));
        }
    }

    protected void showSetting(boolean isShow) {
        View v = findViewById(R.id.setting);
        if (v != null) {
            v.setVisibility(isShow ? (Config.IS_SHOW_SETTING ? View.VISIBLE : View.GONE) : View.GONE);
        }
    }

    protected void showSetting(int res,View.OnClickListener listener) {
        showSetting(true);
        ImageView v = findViewById(R.id.setting);
        if (v != null) {
            if(res!=0){
                v.setImageResource(res);
            }
            v.setOnClickListener(listener);
        }
    }

    protected void toast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToastFree(activity, s);
            }
        });
    }

    protected void toast(int s) {
        toast(getString(s));
    }

    public abstract void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage);

    protected void setBtnTypeface(Button view, String t) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        view.setTypeface(typeface);
        view.setText(t);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
        Button button = findViewById(R.id.btn_reload);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reload();
                }
            });
        }
        View back = findViewById(R.id.back);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        initView();
    }

    protected void hideBack() {
        View back = findViewById(R.id.back);
        if (back != null) {
            back.setVisibility(View.GONE);
        }
    }

    protected void showContent(boolean b) {
        View content = findViewById(R.id.rl_content);
        View empty = findViewById(R.id.empty);
        if (content != null) {
            content.setVisibility(b ? View.VISIBLE : View.GONE);
        }
        if (empty != null) {
            empty.setVisibility(!b ? View.VISIBLE : View.GONE);
        }
    }

    protected abstract void reload();

    public void setMTitle(String name, int location) {
        if (findViewById(R.id.tv_top_left) != null) {
            findViewById(R.id.tv_top_left).setVisibility(View.GONE);
        }
        if (findViewById(R.id.tv_top_center) != null) {
            findViewById(R.id.tv_top_center).setVisibility(View.GONE);
        }
        switch (location) {
            case 0:
                if (findViewById(R.id.tv_top_left) != null) {
                    ((TextView) findViewById(R.id.tv_top_left)).setText(name);
                    findViewById(R.id.tv_top_left).setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                if (findViewById(R.id.tv_top_center) != null) {
                    ((TextView) findViewById(R.id.tv_top_center)).setText(name);
                    findViewById(R.id.tv_top_center).setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    protected void setMTitle(int name, int location) {
        setMTitle(getString(name), location);
    }

    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

    public void showDlg() {
        if (dialog != null && !dialog.isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.setMessage(getString(com.yaokantv.yaokansdk.R.string.loading));
                    dialog.show();
                }
            });
        }
    }

    protected void showForceDlg(final String s) {
        if (dialog != null && !dialog.isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.setMessage(s);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            });
        }
    }

    protected void showProDlg(final String s, final int p) {
        if (progressDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.setCancelable(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setMax(100);
                    progressDialog.setProgress(p);
                    progressDialog.setMessage(s);
                    if (!progressDialog.isShowing()) {
                        progressDialog.show();
                    }
                }
            });
        }
    }

    protected void dismissPro() {
        if (progressDialog != null && progressDialog.isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
        }
    }

    protected void showDlg(final String s) {
        if (dialog != null && !dialog.isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.setMessage(s);
                    dialog.setCancelable(true);
                    dialog.show();
                }
            });
        }
    }

    protected void showDlg(final String s, final DialogInterface.OnCancelListener listener) {
        if (dialog != null && !dialog.isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.setMessage(s);
                    dialog.setOnCancelListener(listener);
                    dialog.setCancelable(true);
                    dialog.show();
                }
            });
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
        }
    }
}
