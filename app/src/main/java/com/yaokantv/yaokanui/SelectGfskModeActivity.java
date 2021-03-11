package com.yaokantv.yaokanui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.yaokantv.sdkdemo.App;
import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.Contants;
import com.yaokantv.yaokansdk.manager.WANManager;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.BaseR;
import com.yaokantv.yaokansdk.model.GfskMacResult;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.ProgressDialogUtils;
import com.yaokantv.yaokansdk.utils.Utility;
import com.yaokantv.yaokanui.widget.YkTabView;

public class SelectGfskModeActivity extends BaseActivity {

    String subMac, code;
    int road, tid, bid;
    GfskMacResult gfskMacResult;
    YkTabView ykTabView;
    public static final String REBIND = "rebind";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gfsk_mode);
        setMTitle("设备配码", TITLE_LOCATION_CENTER);
    }

    @Override
    protected void initView() {
        ykTabView = findViewById(R.id.yk_tab);
        addTab(R.string.key_gfsk_match);
        addTab(R.string.start_gfsk_match);
        ykTabView.setOnTabClickListener(new YkTabView.OnTabClickListener() {
            @Override
            public void onTabClick(View v, int position) {
                switch (position) {
                    case 1:
                        findViewById(R.id.tv_key_match_hint).setVisibility(View.VISIBLE);
                        findViewById(R.id.rl_start_match).setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        findViewById(R.id.tv_key_match_hint).setVisibility(View.INVISIBLE);
                        findViewById(R.id.rl_start_match).setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        road = getIntent().getIntExtra("road", 0);
        tid = getIntent().getIntExtra("tid", 0);
        bid = getIntent().getIntExtra("bid", 0);

        getCode();
        String rm = Hawk.get(Contants.S_RECEIVE_MODE + App.curMac, "");
        if (!TextUtils.isEmpty(rm)) {
            ((TextView) findViewById(R.id.tv_receive_mode)).setText("当前网关接收协议是：" + Utility.getRfModeName(rm));
        }
        findViewById(R.id.rl_match).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gfskMacResult != null) {
                    Yaokan.instance().publishDown(App.curDid,gfskMacResult.getBindCode());
                }
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectGfskModeActivity.this, GfskMatchActivity.class);
                intent.putExtra("road", road);
                intent.putExtra("tid", tid);
                intent.putExtra("bid", bid);
                Bundle bundle = new Bundle();
                bundle.putParcelable("p", gfskMacResult);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void reload() {

    }

    public void addTab(int txId) {
        TextView textView = new TextView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        textView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        textView.setText(txId);
        ykTabView.addView(textView, layoutParams);
    }

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        switch (msgType) {
            case ProduceMacResult:
                if (ykMessage == null || ykMessage.getData() == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DlgUtils.createDefDlg(SelectGfskModeActivity.this, "", "获取配码信息失败，请重新获取", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getCode();
                                }
                            }, false);
                        }
                    });
                } else {
                    gfskMacResult = (GfskMacResult) ykMessage.getData();
                    subMac = gfskMacResult.getMac();
                    String t = Integer.toHexString(tid);
                    if (t.length() == 1) {
                        t = "0" + t;
                    }
                    code = Contants.YK_PRO + "15FFAA010100" + App.curMac + subMac + "300" + road + "00" + t;
                }
                break;
        }
    }


    private void getCode() {
        Yaokan.instance().getProduceMac(App.curMac, tid, road);
    }
}