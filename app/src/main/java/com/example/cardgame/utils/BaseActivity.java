package com.example.cardgame.utils;

import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardgame.R;
import com.example.cardgame.Records.Record;
import com.example.cardgame.Records.TopTen;
import com.example.cardgame.activities.MenuActivity;
import com.example.cardgame.activities.PreGameActivity;
import com.example.cardgame.activities.TopTenActivity;
import com.example.cardgame.fragments.CallBack_RecordList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity implements CallBack_RecordList {

    public final static String SP_RECORDS_KEY = "sharedPreferencesFile";
    private MediaPlayer mp;

    public void openMenu() {
        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);
        finish();
    }

    public void playButtonSound(){
        mp = MediaPlayer.create(this, R.raw.click_sound);
        mp.start();
    }

    public void openTopTenActivity() {
        Intent intent = new Intent(this, TopTenActivity.class);
        startActivity(intent);
        finish();
    }

    public void openMenuActivity() {
        Intent intent = new Intent(this, PreGameActivity.class);
        startActivity(intent);
        finish();
    }

    public TopTen loadTopTenData() {
        Gson gson = new Gson();
        String json = MySP.getInstance().getString(SP_RECORDS_KEY, null);
        Type type = new TypeToken<ArrayList<Record>>() {}.getType();
        ArrayList<Record> recordList = gson.fromJson(json, type);

        if (recordList == null)
            recordList = new ArrayList<Record>();

        return new TopTen(recordList);
    }

    @Override
    public void focusRecordOnMap(int place) { }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            MyScreenUtils.hideSystemUI(this);
        }
    }

    protected boolean isDoubleBackPressToClose = true;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (isDoubleBackPressToClose) {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            }
            else {
                Toast.makeText(this, "Tap back button again to exit", Toast.LENGTH_SHORT).show();
            }

            mBackPressed = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStop() {
        if(mp != null) {
            mp.release();
            mp = null;
        }
        super.onStop();
    }
}
