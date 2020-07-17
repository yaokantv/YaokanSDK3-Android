package com.yaokantv.yaokanui.dlg;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokanui.bean.UiRc;

public class RenameDialog extends Dialog {
    Context context;
    UiRc uiRc;
    EditText et;
    Button cancel, ok;

    public RenameDialog(Context context, UiRc uiRc) {
        super(context, R.style.ykk_dialog);
        this.context = context;
        this.uiRc = uiRc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_rename);
        et = findViewById(R.id.et_name);
        et.setText(uiRc.getName());
        cancel = findViewById(R.id.cancel);
        ok = findViewById(R.id.ok);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                String name = et.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    return;
                }
                if (name.length() > 15) {
                    return;
                }
                RemoteCtrl ctrl = Yaokan.instance().getRcDataByUUID(uiRc.getUuid());
                if (ctrl != null) {
                    ctrl.setName(name);
                    Yaokan.instance().updateRc(ctrl);
                }
            }
        });
    }

}
