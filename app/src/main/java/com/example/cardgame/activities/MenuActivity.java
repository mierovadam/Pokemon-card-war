package com.example.cardgame.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cardgame.utils.BaseActivity;
import com.example.cardgame.R;
import com.example.cardgame.utils.MyScreenUtils;

public class MenuActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyScreenUtils.hideSystemUI(this);
        setContentView(R.layout.activity_menu);

        initButtonListeners();
        glideEverything();

    }

    private void glideEverything() {
        Glide.with(this).load(R.drawable.pokemon_logo).into((ImageView)findViewById(R.id.menu_IMG_logo));
    }

    private void initButtonListeners() {
        Button menu_BTN_startGame = findViewById(R.id.menu_BTN_startGame);
        Button menu_BTN_topTen = findViewById(R.id.menu_BTN_topTen);

        menu_BTN_startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButtonSound();
                openMenuActivity();
            }
        });

        menu_BTN_topTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButtonSound();
                openTopTenActivity();
            }
        });
    }


}