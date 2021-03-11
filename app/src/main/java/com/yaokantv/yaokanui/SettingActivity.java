package com.yaokantv.yaokanui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokanui.dlg.RenameDialog;
import com.yaokantv.yaokanui.utils.ControlUtils;
import com.yaokantv.yaokanui.utils.YKAppManager;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    View delete, rename;
    RemoteCtrl uiRc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiRc = Yaokan.instance().getRcDataByUUID(getIntent().getStringExtra("uuid"));
        setContentView(R.layout.activity_setting);
        setMTitle(R.string.setting, TITLE_LOCATION_CENTER);
    }

    @Override
    protected void initView() {
        delete = findViewById(R.id.rl_delete);
        delete.setOnClickListener(this);
        rename = findViewById(R.id.rl_update);
        rename.setOnClickListener(this);
        findViewById(R.id.rl_study).setOnClickListener(this);
        if (uiRc != null && (uiRc.getBe_rc_type() == 7 ||
                uiRc.getBe_rc_type() == 45 || uiRc.getBe_rc_type() == 46)
                || ControlUtils.isGfskControl(uiRc.getBid())) {
            findViewById(R.id.rl_study).setVisibility(View.GONE);
        }
    }

    @Override
    public void onReceiveMsg(MsgType msgType, YkMessage ykMessage) {

    }

    @Override
    protected void reload() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_delete) {
            final String id = uiRc.getUuid();
            if (!TextUtils.isEmpty(id)) {
                DlgUtils.createDefDlg(activity, "", getString(R.string.is_delete_rc), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Yaokan.instance().isDeviceOnline(uiRc.getMac())) {
                            //删除设备端和手机内的的遥控器
                            Yaokan.instance().deleteDeviceRc(Yaokan.instance().getDid(uiRc.getMac()), uiRc.isRf() ? uiRc.getStudyId() : uiRc.getRid());
                            Yaokan.instance().deleteRcByUUID(uiRc.getUuid());
                            finish();
                        } else {
                            //如果不在线则只能删除手机数据库中的遥控器
                            Yaokan.instance().deleteRcByUUID(uiRc.getUuid());
                            finish();
                        }
                        YKAppManager.getAppManager().finishActivity(RcActivity.class);
                        finish();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        } else if (v.getId() == R.id.rl_update) {
            RenameDialog renameDialog = new RenameDialog(activity, uiRc);
            renameDialog.show();
        } else if (v.getId() == R.id.rl_study) {
            Intent intent = new Intent(this, RcActivity.class);
            setResult(100, intent);
            finish();
        } else if (v.getId() == R.id.rl_room_msg) {
            Intent intent = new Intent(this, RoomMsgActivity.class);
            intent.putExtra("uuid", uiRc.getUuid());
            startActivity(intent);
        }
    }
}
