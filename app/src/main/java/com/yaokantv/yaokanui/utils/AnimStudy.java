package com.yaokantv.yaokanui.utils;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.utils.Logger;
import com.yaokantv.yaokansdk.utils.Utility;

public class AnimStudy {
    private Drawable oldDrawable;

    private boolean codeStudying = false;

    private View currentView;

    private AnimationDrawable anim;

    private String currResStr;

    private Activity ctx;
    public static final int STUDY_START = 0;
    public static final int STUDY_SUCCESS = 1;
    public static final int STUDY_FAIL = 2;

    public AnimStudy(Activity ctx) {
        this.ctx = ctx;
    }

    int i;

    public void startAnim(View view) {
        try {
            if (!codeStudying) {
                currentView = view;
                currResStr = (String) view.getTag();
                if (view instanceof ImageButton) {
                    oldDrawable = view.getBackground();
                    if (StringUtils.DRA_CAMERA.equals(currResStr)) {
                        ((ImageButton) view).setImageDrawable(null);
                    }
                } else if (view instanceof ImageView) {
                    oldDrawable = ((ImageView) view).getDrawable();
                } else {
                    oldDrawable = view.getBackground();
                }
                Logger.e("currResStr:" + currResStr);
                anim = new AnimationDrawable();
                anim.setOneShot(false);
                anim.addFrame(ctx.getResources().getDrawable(ResourceManager.getIdByName(ResourceManager.drawable, getSrc(currResStr)[0])), 300);
                anim.addFrame(ctx.getResources().getDrawable(ResourceManager.getIdByName(ResourceManager.drawable, getSrc(currResStr)[1])), 300);
                view.setBackground(anim);
                anim.start();
                codeStudying = true;
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

    public String[] getSrc(String name) {
        String[] arr = new String[2];
        switch (name) {
            case StringUtils.DRA_BTN_CIRCLE:
                arr[0] = "shape_circle_white";
                arr[1] = "shape_circle_blue";
                break;
            case StringUtils.DRA_BTN_NUM:
                arr[0] = "shape_cir_white_25";
                arr[1] = "shape_cir_main_25";
                break;
            case StringUtils.DRA_BG_MATCHING:
                arr[0] = "shape_matching_btn";
                arr[1] = "shape_matching_btn_press";
                break;
            case StringUtils.DRA_SQUARE:
                arr[0] = "yk_ctrl_unselected_app_square";
                arr[1] = "yk_ctrl_selected_app_square";
                break;
            case StringUtils.DRA_PLAY:
                arr[0] = "shape_cir_white_30";
                arr[1] = "shape_cir_main_30";
                break;
            case StringUtils.DRA_CAMERA:
                arr[0] = "ib_camera";
                arr[1] = "ib_camera_press";
                break;
        }
        return arr;
    }

    public void stopAnim() {
        if (!Utility.isEmpty(currentView)) {
            anim.stop();
            if (currentView instanceof ImageButton) {
                currentView.setBackground(oldDrawable);
                if (StringUtils.DRA_CAMERA.equals(currResStr)) {
                    ((ImageButton) currentView).setImageResource(R.drawable.btn_camera);
                }
            } else if (currentView instanceof ImageView) {
                ((ImageView) currentView).setImageDrawable(oldDrawable);
            } else {
                currentView.setBackground(oldDrawable);
            }
            codeStudying = false;
        }
    }

    StudyListener listener;

    public void setListener(StudyListener listener) {
        this.listener = listener;
    }

    public interface StudyListener {
        void onFail();
    }

}
