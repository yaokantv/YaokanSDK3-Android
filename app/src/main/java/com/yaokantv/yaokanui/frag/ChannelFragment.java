package com.yaokantv.yaokanui.frag;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


import com.yaokantv.yaokansdk.model.RemoteCtrl;

import java.util.ArrayList;
import java.util.List;

public class ChannelFragment extends BaseRcFragment {
    @Override
    public void refreshRcData(RemoteCtrl rc) {

    }

    @Override
    public void setKeyBackground() {

    }
//    GridView vg;
//    CommonAdapter<ChannelInfo> adapter;
//    List<ChannelInfo> infos = new ArrayList<>();
//    int type = 5;
//    String localId;
//    RcActivity activity;
//
//    public static ChannelFragment getInstance(int type, String localId) {
//        ChannelFragment sf = new ChannelFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(Co.TAG, type);
//        bundle.putString(Co.LOCAL_ID, localId);
//        sf.setArguments(bundle);
//        return sf;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (type == 0 || type == 9) {
//            refreshList();
//        }
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.frg_channel, null);
//        vg = v.findViewById(R.id.gv);
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            type = bundle.getInt(Co.TAG, 5);
//            localId = bundle.getString(Co.LOCAL_ID);
//        }
//        activity = (RcActivity) getActivity();
//        adapter = new CommonAdapter<ChannelInfo>(getContext(), infos, R.layout.channel_program_gridview) {
//            @Override
//            public void convert(ViewHolder helper, final ChannelInfo item, int position) {
//                helper.setText(R.id.program_delegate_title_item, item.getName());
//                helper.setText(R.id.program_delegate_num_item, item.getControl() + "");
//                if (!TextUtils.isEmpty(item.getIcon())) {
//                    helper.setImageURl(R.id.program_delegate_item, item.getIcon());
//                } else {
//                    helper.setImageResource(R.id.program_delegate_item, R.mipmap.ch_default);
//                }
//                helper.setOnLongClickListener(R.id.item_ll_bg, new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        if (DBUtils.isLikeChannel(item)) {
//                            DlgUtils.createDefDlg(getActivity(), "", getString(R.string.is_remove_from_like), new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    int i = DBUtils.remoteTag(item);
//                                    if (type == 9 && i > 0) {
//                                        infos.remove(item);
//                                    }
//                                    dialog.dismiss();
//                                }
//                            }, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            }, true);
//                        } else {
//                            DlgUtils.createDefDlg(getActivity(), "", getString(R.string.add_to_like), new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    DBUtils.addToChannel(item, "9");
//                                    dialog.dismiss();
//                                }
//                            }, true);
//                        }
//                        return true;
//                    }
//                });
//                helper.setOnclickListener(R.id.item_ll_bg, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!TCP.IS_BLE_CONNECT) {
//                            showAppleOffline();
//                            return;
//                        }
//                        ch = getString(R.string.sending_code) + item.getName() + " ";
//                        activity.showDlg(ch);
//                        Logger.e(Co.TAG, item.toString());
//                        DBUtils.addToChannel(item, "0");
//                        char[] sp = String.valueOf(item.getControl()).toCharArray();
//                        sendNum(sp, handler);
//                    }
//                });
//            }
//        };
//        vg.setAdapter(adapter);
//        refreshList();
//        return v;
//    }
//
//    String ch = "";
//
//    private int sendNum(char[] sp, Handler handler) {
//        Object codes[] = new Object[sp.length + 1];
//        int i = 0;
//        for (char c : sp) {
//            codes[i] = DBUtils.getCode(c, localId);
////            Logger.e(Co.TAG, codes[i]);
//            i++;
//        }
//        codes[i] = "finish";
//        i = 0;
//        for (Object mCode : codes) {
//            if (mCode != null) {
//                try {
//                    Message message = handler.obtainMessage();
//                    if ("finish".equals(mCode)) {
//                        message.obj = new Integer(1);
//                        handler.sendMessageDelayed(message, i * DBUtils.getInterval());
//                    } else {
//                        message.obj = mCode;
//                        message.what = Integer.valueOf(String.valueOf(sp[i]));
//                        handler.sendMessageDelayed(message, i * DBUtils.getInterval());
//                    }
//                    i++;
//                } catch (Exception e) {
//                    Logger.e(Co.TAG, "sendNum:" + e.getMessage());
//                }
//            }
//        }
//        return i;
//    }
//
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg != null && msg.obj != null && msg.obj instanceof RcData) {
//                activity.sendCmd((RcData) msg.obj);
//                ch += msg.what + "";
//                activity.setDlgTips(ch);
//            }
//            if (msg != null && msg.obj != null && msg.obj instanceof Integer) {
//                activity.closeDlg();
//            }
//        }
//    };
//
//    private void refreshList() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<ChannelInfo> list = DBUtils.getChannelInfoByTag(type);
//                infos.clear();
//                infos.addAll(list);
//                if (getActivity() != null) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapter.notifyDataSetChanged();
//                        }
//                    });
//                }
//            }
//        }).start();
//    }

}
