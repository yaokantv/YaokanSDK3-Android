package com.yaokantv.sdkdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    ProgressDialog dialog;
    ProgressDialog progressDialog;
    static final String TAG = "YaokanSDK";
    protected String[] rcItem = {"删除"};
    protected String[] rcListItem = {"查询小苹果内的遥控器"};
    Activity activity;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        dialog = new ProgressDialog(this);
        progressDialog = new ProgressDialog(this);
        AppManager.getAppManager().addActivity(activity);

    }

    void log(String s) {
        Log.e(TAG + activity.getClass().getName(), s);
    }

    void initToolbar(int id) {
        initToolbar(getString(id));
    }

    void initToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected void toast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    String getShowText(String s) {
        String text = "";
        switch (s) {
            case "cold":
                text = "制冷";
                break;
            case "hot":
                text = "制热";
                break;
            case "auto":
                text = "自动";
                break;
            case "dry":
                text = "抽湿";
                break;
            case "wind":
                text = "送风";
                break;
            case "0":
                text = "风速自动";
                break;
            case "1":
                text = "风速1";
                break;
            case "2":
                text = "风速2";
                break;
            case "3":
                text = "风速3";
                break;
            case "verticalOn":
            case "verOn":
                text = "上下扫风开";
                break;
            case "verticalOff":
            case "verOff":
                text = "上下扫风关";
                break;
            case "horizontalOn":
            case "horOn":
                text = "左右扫风开";
                break;
            case "horizontalOff":
            case "horOff":
                text = "左右扫风关";
                break;
        }
        return text;
    }

    protected void setViewStatus(View v, boolean isEnable) {
        if (v != null) {
            v.setVisibility(isEnable ? View.VISIBLE : View.INVISIBLE);
            if (v instanceof Button) {

            } else if (v instanceof TextView) {
                if (!isEnable) {
                    ((TextView) v).setText("--");
                }
            }
        }
    }

    protected void showDlg() {
        if (dialog != null && !dialog.isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.setMessage(getString( R.string.loading));
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

    protected void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
        }
    }

    protected void listAlear(String[] item, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择");
        builder.setItems(item, listener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
