package com.yaokantv.yaokanui.widget;

public interface OnCrollerChangeListener {
    void onProgressChanged(Croller croller, int progress);

    void onStartTrackingTouch(Croller croller);

    void onStopTrackingTouch(Croller croller);
}
