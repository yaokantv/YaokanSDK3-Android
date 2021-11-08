package com.yaokantv.yaokanui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.MatchingData;
import com.yaokantv.yaokansdk.model.Operators;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;

import java.util.List;

public class NewMatchActivity extends BaseActivity implements View.OnClickListener {
    int index = 0;
    List<MatchingData> dataList;
    MatchingData mMatch;

    private int tid;
    private int bid;
    private Operators operator;
    private TextView tvName, tvNum;
    private ImageButton ibPre, ibTest, ibNext;
    private Button btnNext;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);
        setMTitle(R.string.title_step_match, TITLE_LOCATION_CENTER);
        tid = getIntent().getIntExtra(Config.S_TID, 0);
        bid = getIntent().getIntExtra(Config.S_BID, 0);
        name = Config.curBName;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            operator = bundle.getParcelable(Config.S_OPE);
            if (operator != null) {
                name = operator.getName();
            }
        }
        reload();
        if (tid == 1 && ibTest != null) {
            ibTest.setImageResource(R.drawable.bg_vol_test);
        }
    }

    @Override
    protected void initView() {
        tvName = findViewById(R.id.tv_device_name);
        tvNum = findViewById(R.id.tv_number);
        ibPre = findViewById(R.id.ib_pre);
        ibTest = findViewById(R.id.ib_test);
        ibNext = findViewById(R.id.ib_next);
        btnNext = findViewById(R.id.finish_btn);


        ibPre.setOnClickListener(this);
        ibTest.setOnClickListener(this);
        ibNext.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    private void match() {
        if (mMatch != null) {
            Yaokan.instance().sendCmd(Config.DID, mMatch.getRid(), mMatch.getCmd(), tid, null, null);
        }
    }

    private void setMatchMsg() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                findViewById(R.id.ll_match1).setVisibility(View.VISIBLE);
//                findViewById(R.id.btn_second).setVisibility(View.VISIBLE);
                mMatch = dataList.get(index);
                String msg = name + " " + Config.curTName + " " + (index + 1);
                tvNum.setText((index + 1) + "/" + dataList.size());
                tvName.setText(msg);
            }
        });
    }

    @Override
    public void onReceiveMsg(MsgType msgType, YkMessage ykMessage) {
        switch (msgType) {
            case Matching:
                if (ykMessage != null && ykMessage.getData() != null && ykMessage.getData() instanceof List) {
                    dataList = (List) ykMessage.getData();
                    if (dataList != null && dataList.size() > 0) {
                        setMatchMsg();
                    }
                }
                dismiss();
                break;
        }
    }

    @Override
    protected void reload() {
        showDlg();
        Yaokan.instance().getMatchingResult(tid, bid);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ib_pre) {
            index--;
            if (index < 0) {
                index = dataList.size() - 1;
            }
            setMatchMsg();
            match();
        } else if (v.getId() == R.id.ib_test) {
            match();
        } else if (v.getId() == R.id.ib_next) {
            index++;
            if (index >= dataList.size()) {
                index = 0;
            }
            setMatchMsg();
            match();
        } else if (v.getId() == R.id.finish_btn) {
            if (mMatch != null) {
                Intent intent = new Intent(this, RcActivity.class);
                intent.putExtra(Config.ACTIVITY_TYPE, Config.TYPE_MATCHING);
                intent.putExtra("create", true);
                intent.putExtra(Config.S_TID, tid);
                intent.putExtra(Config.S_BID, mMatch.getBid());
                intent.putExtra(Config.S_GID, mMatch.getGid());
                startActivity(intent);
            }
        }
    }
}
