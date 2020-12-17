package com.example.cardgame.fragments;

import com.example.cardgame.Records.TopTen;

public interface CallBack_RecordList {
    public TopTen loadTopTenData();
    public void focusRecordOnMap(int place);

}
