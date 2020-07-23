package com.yaokantv.yaokanui.utils;

import android.content.Context;

import com.yaokantv.yaokansdk.utils.Logger;

public class ResourceManager {
    private static String TAG = ResourceManager.class.getName();

    //布局
    public static String layout = "layout";

    //图片
    public static String drawable = "drawable";

    //控件id
    public static String id = "id";

    //文字
    public static String string = "string";

    //颜色
    public static String color = "color";


    //数组
    public static String array = "array";

    //动画
    public static String anim = "anim";

    //风格
    public static String style = "style";


    public static String raw = "raw";

    public static String dimen = "dimen";




    @SuppressWarnings("rawtypes")
    public static  final int getIdByName(Context context, String className, String name) {
        String packageName = context.getPackageName();
        Class<?> r = null;
        int id = 0;
        try {
            r = Class.forName(packageName + ".R");
            Class[] classes = r.getClasses();
            Class desireClass = null;

            for (int i = 0; i < classes.length; ++i) {
                if (classes[i].getName().split("\\$")[1].equals(className)) {
                    desireClass = classes[i];
                    break;
                }
            }

            if (desireClass != null)
                id = desireClass.getField(name).getInt(desireClass);
        } catch (ClassNotFoundException e) {
            Logger.e(TAG, "e:" + e.getMessage());
        } catch (IllegalArgumentException e) {
            Logger.e(TAG, "e:" + e.getMessage());
        } catch (SecurityException e) {
            Logger.e(TAG, "e:" + e.getMessage());
        } catch (IllegalAccessException e) {
            Logger.e(TAG, "e:" + e.getMessage());
        } catch (NoSuchFieldException e) {
            Logger.e(TAG, "e:" + e.getMessage());
        }

        return id;
    }

}
