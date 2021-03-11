package com.yaokantv.yaokanui.utils;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

public class YKAppManager {
    // Activity栈
    private static Stack<Activity> activityStack;
    // 单例模式
    private static YKAppManager instance;

    private YKAppManager() {
    }

    /**
     * 单一实例
     */
    public static YKAppManager getAppManager() {
        if (instance == null) {
            instance = new YKAppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity mActivity) {
        if (mActivity != null) {
            Iterator<Activity> iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (activity.getClass().equals(mActivity.getClass())) {
                    if (activity != null) {
                        activity.finish();
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)) {
                if (activity != null) {
                    activity.finish();
                    iterator.remove();
                }
            }
        }
    }

    public boolean hasClass(Class<?> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)) {
                if (activity != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void finishActivities(Class<?>... clss) {
        for (Class<?> cls : clss) {
            Iterator<Activity> iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (activity.getClass().equals(cls)) {
                    try {
                        if (activity != null) {
                            activity.finish();
                            iterator.remove();
                        }
                    }catch (Exception e){
                    }
                }
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

}
