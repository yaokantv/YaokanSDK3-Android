package com.yaokantv.yaokanui.widget;

import com.yaokantv.yaokansdk.model.Results;

import java.util.Comparator;

public class PinyinComparator implements Comparator<Results> {

    public int compare(Results o1, Results o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

}