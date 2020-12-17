package com.example.cardgame.activities;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cardgame.utils.BaseActivity;
import com.example.cardgame.R;
import com.example.cardgame.Records.Record;
import com.example.cardgame.Records.TopTen;
import com.example.cardgame.utils.MySP;
import com.example.cardgame.utils.MyScreenUtils;

import com.google.gson.Gson;

public class WinnerActivity extends BaseActivity {

    private TextView winner_LBL_name;
    private ImageView winner_IMG_winner;

    private TopTen topTen;
    private String winnerName;
    private String winnerPokemon;
    private int winnerScore;
    private Location winnerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyScreenUtils.hideSystemUI(this);
        setContentView(R.layout.activity_winner);

        getIntents();
        initViews();
        initButtonListeners();

        if(declareWinner() != false) {              //if != draw , get location and enter json file.
            winnerLocation = getWinnerLocation();
            topTen = loadTopTenData();
            saveNewTopTen();
        }
    }

    //get the latest location the phone remembers.
    private Location getWinnerLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(WinnerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            long GPSLocationTime = 0;
            if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

            long NetLocationTime = 0;

            if (null != locationNet) {
                NetLocationTime = locationNet.getTime();
            }
            if ( 0 < GPSLocationTime - NetLocationTime ) {
                return locationGPS;
            }        else {
                return locationNet;
            }
        }else {
            ActivityCompat.requestPermissions(WinnerActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            getWinnerLocation();    //get location again after granting permission
        }
        return null;
    }


    private void saveNewTopTen() {
        Record record = new Record(winnerName,winnerPokemon,winnerScore,winnerLocation.getLongitude(),winnerLocation.getLatitude());

        if(topTen.enterNewScore(record)) {  //Re-save only if the record enters into top ten.
            MySP.init(this);
            Gson gson = new Gson();

            Toast.makeText(this, "Record entered into Top10 !", Toast.LENGTH_SHORT).show();

            String json = gson.toJson(topTen.getList());
            MySP.getInstance().putString(SP_RECORDS_KEY, json);
        }
    }

    private boolean declareWinner(){
        if(winnerName.equals("Draw")) {
            winner_LBL_name.setText("We have a draw :(");
            Glide.with(this).load(R.drawable.magicarp).into(winner_IMG_winner);
            return false;
        }
        winner_LBL_name.setText("The winner is " + winnerName + " !");
        int id = getResources().getIdentifier("com.example.cardgame:drawable/" + winnerPokemon, null, null);
        Glide.with(this).load(id).into(winner_IMG_winner);

        return true;
    }

    private void initViews(){
        winner_LBL_name           = findViewById(R.id.winner_LBL_name);
        winner_IMG_winner         = findViewById(R.id.winner_IMG_winner);
    }

    private void initButtonListeners(){
        Button winner_BTN_menu    = findViewById(R.id.winner_BTN_menu);
        Button winner_BTN_restart = findViewById(R.id.winner_BTN_restart);

        winner_BTN_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButtonSound();
                restart();
            }
        });
        winner_BTN_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButtonSound();
                openMenu();
            }
        });
    }

    private void getIntents(){
        winnerName    = getIntent().getStringExtra("winnerName");
        winnerPokemon = getIntent().getStringExtra("winnerPokemon");
        winnerScore   = getIntent().getIntExtra("winnerScore",0);
    }

    private void restart(){
        Intent intent = new Intent(this, GameActivity.class);

        //restart game with same names and pokemons
        intent.putExtra("leftName",getIntent().getStringExtra("leftName"));
        intent.putExtra("rightName",getIntent().getStringExtra("rightName"));
        intent.putExtra("leftPokemon",getIntent().getStringExtra("leftPokemon"));
        intent.putExtra("rightPokemon",getIntent().getStringExtra("rightPokemon"));

        startActivity(intent);
        finish();
    }

}