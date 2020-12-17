package com.example.cardgame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cardgame.utils.BaseActivity;
import com.example.cardgame.R;
import com.example.cardgame.utils.MyScreenUtils;

public class PreGameActivity extends BaseActivity {
    private EditText pre_ETXT_leftName,pre_ETXT_rightName;
    private ImageView pre_IMG_rightPokemon,pre_IMG_leftPokemon;

    private String leftPokemon ;
    private String rightPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyScreenUtils.hideSystemUI(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);    //keeps notification bar away after using the keyboard.
        setContentView(R.layout.activity_pre_game);

        initViews();
        initButtonListeners();
        glideEverything();
    }

    public void pickLeftPokemon(View view){     //set as OnClick
        ImageView img = (ImageView)view;

        Glide.with(this).load(img.getDrawable()).into(pre_IMG_leftPokemon);
        leftPokemon = img.getTag().toString();
    }

    public void pickRightPokemon(View view){    //set as OnClick
        ImageView img = (ImageView)view;

        Glide.with(this).load(img.getDrawable()).into(pre_IMG_rightPokemon);
        rightPokemon = img.getTag().toString();
    }

    private void initViews(){
        pre_IMG_rightPokemon    = findViewById(R.id.pre_IMG_rightPokemon);
        pre_IMG_leftPokemon     = findViewById(R.id.pre_IMG_leftPokemon);
        pre_ETXT_rightName      = findViewById(R.id.pre_ETXT_rightName);
        pre_ETXT_leftName       = findViewById(R.id.pre_ETXT_leftName);
    }

    private void initButtonListeners() {
        Button pre_BTN_start = findViewById(R.id.pre_BTN_start);
        Button pre_BTN_return = findViewById(R.id.pre_BTN_return);

        pre_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButtonSound();
                openGameActivity();
            }
        });
        pre_BTN_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButtonSound();
                openMenu();
            }
        });
    }

    private void openGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);

        intent.putExtra("leftName",pre_ETXT_leftName.getText().toString());         //pass names from textEdits
        intent.putExtra("rightName",pre_ETXT_rightName.getText().toString());
        intent.putExtra("leftPokemon",leftPokemon);
        intent.putExtra("rightPokemon",rightPokemon);

        startActivity(intent);
        finish();
    }

    private void glideEverything(){
        leftPokemon = "bulbasaur" ;     //set default pics
        rightPokemon = "squirtle" ;
        Glide.with(this).load(R.drawable.bulbasaur).into(pre_IMG_rightPokemon);
        Glide.with(this).load(R.drawable.squirtle).into(pre_IMG_leftPokemon);

        Glide.with(this).load(R.drawable.bulbasaur).into((ImageView)findViewById(R.id.pre_IMG_leftBulbasaur));
        Glide.with(this).load(R.drawable.bulbasaur).into((ImageView)findViewById(R.id.pre_IMG_rightBulbasaur));

        Glide.with(this).load(R.drawable.charmander).into((ImageView)findViewById(R.id.pre_IMG_leftCharmander));
        Glide.with(this).load(R.drawable.charmander).into((ImageView)findViewById(R.id.pre_IMG_rightCharmander));

        Glide.with(this).load(R.drawable.pikachu).into((ImageView)findViewById(R.id.pre_IMG_leftPikachu));
        Glide.with(this).load(R.drawable.pikachu).into((ImageView)findViewById(R.id.pre_IMG_rightPikachu));

        Glide.with(this).load(R.drawable.squirtle).into((ImageView)findViewById(R.id.pre_IMG_leftSquirtle));
        Glide.with(this).load(R.drawable.squirtle).into((ImageView)findViewById(R.id.pre_IMG_rightSquirtle));

        Glide.with(this).load(R.drawable.pokemon_logo).into((ImageView)findViewById(R.id.pre_IMG_logo));
    }
}