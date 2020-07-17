package com.yaokantv.yaokanui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

public class YkButton extends AppCompatButton {

    //按下时响应对应点的坐标
    private int mLastMotionX, mLastMotionY;

    //是否移动了
    private boolean isMoved;

    //移动的阈值
    private static final int TOUCH_MOVE_SLOP = 20;

    //长按时间
    private static final int  PRESS_LONG_TIMEOUT= 1500;

    //开始时间
    private long startTime = 0  ;

    //弹起时间
    private long endTime = 0 ;

    private String keyTag;

    public String getKeyTag() {
        return keyTag;
    }

    public void setKeyTag(String keyTag) {
        this.keyTag = keyTag;
    }

    /**
     * 长按事件线程
     */
    private Runnable mLongPressRunnable = new Runnable() {

        @Override
        public void run() {
            performLongClick();
            setPressed(false);
            isMoved = true;
        }
    };

    public YkButton(Context context) {
        super(context);
    }

    public YkButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(!isEnabled()){
            return true;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                mLastMotionX = x;
                mLastMotionY = y;
                isMoved = false;
                setPressed(true);
                postDelayed(mLongPressRunnable, PRESS_LONG_TIMEOUT);
                break;
            case MotionEvent.ACTION_MOVE:
//			endTime = System.currentTimeMillis();
                if(isMoved) break;
                if(Math.abs(mLastMotionX-x) > TOUCH_MOVE_SLOP || Math.abs(mLastMotionY-y) > TOUCH_MOVE_SLOP) {
                    //移动超过阈值，则表示移动了
                    isMoved = true;
                    removeCallbacks(mLongPressRunnable);
                }else{
                    //如果按下超过固定时间，响应长按事件
				/*if(endTime-startTime>=PRESS_LONG_TIMEOUT){
					performLongClick();
					setPressed(false);
					isMoved = true;
				}*/
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                setPressed(false);
                removeCallbacks(mLongPressRunnable);
                break;
            case MotionEvent.ACTION_UP:
                endTime = System.currentTimeMillis();
                setPressed(false);
                //如果没有到长按时间，响应点击事件
                if(!isMoved && endTime-startTime<PRESS_LONG_TIMEOUT){
                    performClick();
                }
                removeCallbacks(mLongPressRunnable);
                break;
        }
        return true ;
    }
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }
}
