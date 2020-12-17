package com.example.cardgame.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cardgame.fragments.CallBack_MapLocation;
import com.example.cardgame.fragments.Fragment_Map;
import com.example.cardgame.fragments.Fragment_Record_List;
import com.example.cardgame.utils.BaseActivity;
import com.example.cardgame.R;
import com.example.cardgame.Records.Record;
import com.example.cardgame.utils.MyScreenUtils;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class TopTenActivity extends BaseActivity implements CallBack_MapLocation {
    private Fragment_Map fragment_map;
    private Fragment_Record_List fragment_record_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyScreenUtils.hideSystemUI(this);
        setContentView(R.layout.activity_top_ten);

        initButtonListeners();
        getLatLngData();

        loadFragmentMap();
        loadFragmentScore();
    }

    private void loadFragmentMap(){
        fragment_map = new Fragment_Map();
        fragment_map.setCallBack_mapLocation(this);
        getSupportFragmentManager().beginTransaction().add(R.id.topTen_LAY_map,fragment_map).commit();
    }

    private void loadFragmentScore(){
        fragment_record_list = new Fragment_Record_List();
        fragment_record_list.setCallBack_recordList(this);
        getSupportFragmentManager().beginTransaction().add(R.id.topTen_LAY_records,fragment_record_list).commit();
    }

    @Override
    public void focusRecordOnMap(int pos){
        fragment_map.focusRecord(pos);
    }

    private void initButtonListeners() {
        Button top_BTN_back = findViewById(R.id.top_BTN_back);

        top_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButtonSound();
                openMenu();
            }
        });
    }

    @Override
    public LatLng[] getLatLngData() {
        ArrayList<Record> records = loadTopTenData().getList();

        LatLng latLng[] = new LatLng[records.size()];

        for(int i=0 ; i<records.size();i++){
            latLng[i] = new LatLng(records.get(i).getLatitude(),records.get(i).getLongitude());
        }
        return latLng;
    }


}