package com.yaokantv.yaokanui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.yaokantv.sdkdemo.App;
import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.Contants;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.AliPList;
import com.yaokantv.yaokansdk.model.Alias;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.SelectItem;
import com.yaokantv.yaokansdk.model.WhereBean;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.ViewHolder;
import com.yaokantv.yaokanui.widget.TypeListDialogV2;

import java.util.ArrayList;
import java.util.List;

public class RoomMsgActivity extends BaseActivity {
    TextView tvAlice;
    GridView gridView;
    CommonAdapter<AliPList> adapter;
    List<AliPList> rooms = new ArrayList<>();
    String alice, place;
    RemoteCtrl remoteCtrl;
    String voiceMode = Contants.VOICE_MODE_QY;
    private Alias mAlias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_msg);
        setMTitle(R.string.set_room_msg, TITLE_LOCATION_CENTER);
    }

    AliPList curRoom;
    boolean isCreate = false;

    @Override
    protected void initView() {
        isCreate = getIntent().getBooleanExtra("create", false);
        remoteCtrl = Yaokan.instance().getRcDataByUUID(getIntent().getStringExtra("uuid"));
        getVoiceMode();
        alice = remoteCtrl.getName();
        place = remoteCtrl.getPlace();
        tvAlice = findViewById(R.id.tv_alice);
        tvAlice.setText(alice);
        gridView = findViewById(R.id.gv);
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("正在设置房间信息...");
                dialog.show();
                WhereBean whereBean = new WhereBean();
                whereBean.setDevice_type(remoteCtrl.getBe_rc_type() + "");
                whereBean.setRid("1".endsWith(remoteCtrl.getRf()) ? remoteCtrl.getStudyId() : remoteCtrl.getRid());
                whereBean.setName(mAlias != null ? mAlias.getId() : ((Alias) aliceList.get(0)).getId());
                whereBean.setRoom(curRoom.getDownloadId());
                Yaokan.instance().setRoomMsg(Yaokan.instance().getDid(remoteCtrl.getMac()), whereBean);
            }
        });
        tvAlice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aliceList != null) {
                    showTypeList(findViewById(R.id.tv_tag), activity, aliceList, new TypeListDialogV2.OnStringSelectedListener() {
                        @Override
                        public void onSelected(SelectItem s) {
                            Alias alias = (Alias) s;
                            tvAlice.setText(alias.getName());
                            mAlias = alias;
                            alice = mAlias.getDisplay();
                        }
                    }, alice);
                }
            }
        });
        adapter = new CommonAdapter<AliPList>(activity, rooms, R.layout.yk_ctrl_adapter_expand) {
            @Override
            public void convert(ViewHolder helper, AliPList item, int position) {
                helper.setText(R.id.key_btn, item.getName());
                helper.setOnclickListener(R.id.key_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (AliPList room : rooms) {
                            room.setSelected(false);
                        }
                        curRoom = rooms.get(position);
                        curRoom.setSelected(true);
                        place = curRoom.getName();
                        adapter.notifyDataSetChanged();
                    }
                });
                helper.setBgResource(R.id.key_btn, item.isSelected() ? R.drawable.shape_cir_main_20 : R.drawable.shape_cir_white_20);
            }
        };
        gridView.setAdapter(adapter);
        Yaokan.instance().placeListV2(voiceMode);
        Yaokan.instance().aliceListV2(remoteCtrl.getBe_rc_type(), voiceMode);
    }

    private void getVoiceMode() {
        if (!TextUtils.isEmpty(Hawk.get(Contants.VOICE_VERSION + App.curMac, ""))) {
            voiceMode = Hawk.get(Contants.VOICE_VERSION + App.curMac, "");
        }
    }

    List<Alias> aliceList;

    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (msgType) {
                    case RoomUpdate:
                        dismiss();
                        remoteCtrl.setPlace(place);
                        remoteCtrl.setName(alice);
                        //更新到数据库
                        Yaokan.instance().updateRc(remoteCtrl);
                        DlgUtils.createDefDlg(activity, "", "设置成功", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        break;
                    case PlaceListV2://房间
                        if (ykMessage.getData() != null) {
                            List<AliPList> list = (List<AliPList>) ykMessage.getData();
                            rooms.addAll(list);
                            if (!TextUtils.isEmpty(place)) {
                                for (AliPList room : rooms) {
                                    if (place.equals(room.getName())) {
                                        curRoom = room;
                                        curRoom.setSelected(true);
                                    }
                                }
                            } else {
                                curRoom = rooms.get(0);
                                curRoom.setSelected(true);
                                place = curRoom.getName();
                            }
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case AliceListV2://别名
                        if (ykMessage.getData() != null) {
                            aliceList = (List<Alias>) ykMessage.getData();
                            for (Alias alias : aliceList) {
                                String s = alias.getName();
                                if (!TextUtils.isEmpty(s) && s.equals(alice)) {
                                    mAlias = alias;
                                }
                            }
                            if (isCreate) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAlias = aliceList.get(0);
                                        tvAlice.setText(mAlias.getName());
                                        alice = mAlias.getName();
                                    }
                                });
                            }
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void reload() {

    }
}
