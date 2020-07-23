package com.yaokantv.yaokanui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.RemoteCtrl;
import com.yaokantv.yaokansdk.model.Room;
import com.yaokantv.yaokansdk.model.WhereBean;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.CommonAdapter;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokansdk.utils.ViewHolder;
import com.yaokantv.yaokanui.widget.TypeListDialog;

import java.util.ArrayList;
import java.util.List;

public class RoomMsgActivity extends BaseActivity {
    TextView tvAlice;
    GridView gridView;
    CommonAdapter<Room> adapter;
    List<Room> rooms = new ArrayList<>();
    String alice, place;
    RemoteCtrl remoteCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_msg);
        setMTitle(R.string.set_room_msg, TITLE_LOCATION_CENTER);
    }

    Room curRoom;

    @Override
    protected void initView() {
        remoteCtrl = Yaokan.instance().getRcDataByUUID(getIntent().getStringExtra("uuid"));
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
                whereBean.setName(remoteCtrl.getBe_rc_type() + "", alice);
                whereBean.setRoom(place);
                Yaokan.instance().setRoomMsg(Yaokan.instance().getDid(remoteCtrl.getMac()), whereBean);
            }
        });
        tvAlice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aliceList != null) {
                    showTypeList(findViewById(R.id.tv_tag), activity, aliceList, new TypeListDialog.OnStringSelectedListener() {
                        @Override
                        public void onSelected(String s) {
                            tvAlice.setText(s);
                            alice = s;
                        }
                    });
                }
            }
        });
        adapter = new CommonAdapter<Room>(activity, rooms, R.layout.yk_ctrl_adapter_expand) {
            @Override
            public void convert(ViewHolder helper, Room item, int position) {
                helper.setText(R.id.key_btn, item.getName());
                helper.setOnclickListener(R.id.key_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (Room room : rooms) {
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
        Yaokan.instance().placeList();
        Yaokan.instance().aliceList(remoteCtrl.getBe_rc_type());
    }

    List<String> aliceList;

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
                    case PlaceList:
                        if (ykMessage.getData() != null) {
                            rooms.addAll((List<Room>) ykMessage.getData());
                            if (!TextUtils.isEmpty(place)) {
                                for (Room room : rooms) {
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
                    case AliceList:
                        if (ykMessage.getData() != null) {
                            aliceList = (List<String>) ykMessage.getData();
                            if (TextUtils.isEmpty(alice) || getIntent().getBooleanExtra("create", false)) {
                                alice = aliceList.get(0);
                                tvAlice.setText(aliceList.get(0));
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
