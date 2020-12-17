package com.example.cardgame.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cardgame.utils.BaseActivity;
import com.example.cardgame.R;
import com.example.cardgame.cardStuff.Card;
import com.example.cardgame.cardStuff.Deck;
import com.example.cardgame.utils.MyScreenUtils;

public class GameActivity extends BaseActivity {
    private TextView main_LBL_leftScore;
    private TextView main_LBL_rightScore;
    private ImageView main_IMG_leftCard,main_IMG_rightCard,main_IMG_leftPlayer,main_IMG_rightPlayer;
    private ProgressBar progressBar;
    private Deck deck;

    private static final int DELAY = 300;

    private int barProgress = 0;
    private int leftScore   = 0;
    private int rightScore  = 0;
    private int winnerScore = 0;
    private Card leftCard;
    private Card rightCard;
    private String winnerName;
    private String winnerPokemon;
    private String leftName,rightName;
    private String leftPokemon ;
    private String rightPokemon ;
    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyScreenUtils.hideSystemUI(this);
        setContentView(R.layout.activity_game);

        playOpeningSong();

        getInfoFromPreGameActivity();               //getIntents
        initViews();
        initButtonListeners();
        glideEverything();

        deck = new Deck();
    }

    private void playOpeningSong() {
        mp = MediaPlayer.create(this, R.raw.background_sound);
        mp.start();
    }

    private void glideEverything() {
        Glide.with(this).load(R.drawable.pokemon_back_card).into(main_IMG_leftCard);
        Glide.with(this).load(R.drawable.pokemon_back_card).into(main_IMG_rightCard);

        int idLeftPokemon = getResources().getIdentifier("com.example.cardgame:drawable/" + leftPokemon, null, null);
        Glide.with(this).load(idLeftPokemon).into(main_IMG_leftPlayer);

        int idRightPokemon = getResources().getIdentifier("com.example.cardgame:drawable/" + rightPokemon, null, null);
        Glide.with(this).load(idRightPokemon).into(main_IMG_rightPlayer);

        Glide.with(this).load(R.drawable.pokemon_logo).into((ImageView)findViewById(R.id.main_IMG_logo));
    }

    private void getInfoFromPreGameActivity() {
        leftName = getIntent().getStringExtra("leftName");
        rightName = getIntent().getStringExtra("rightName");
        leftPokemon = getIntent().getStringExtra("leftPokemon");
        rightPokemon = getIntent().getStringExtra("rightPokemon");
    }

    private void initViews(){
        main_IMG_leftCard    = findViewById(R.id.main_IMG_leftCard);
        main_IMG_leftPlayer  = findViewById(R.id.main_IMG_leftPlayer);
        main_LBL_leftScore   = findViewById(R.id.main_LBL_leftScore);
        TextView main_LBL_leftName = findViewById(R.id.main_LBL_leftName);
        main_LBL_leftName.setText(leftName);

        main_IMG_rightPlayer = findViewById(R.id.main_IMG_rightPlayer);
        main_IMG_rightCard   = findViewById(R.id.main_IMG_rightCard);
        main_LBL_rightScore  = findViewById(R.id.main_LBL_rightScore);
        TextView main_LBL_rightName = findViewById(R.id.main_LBL_rightName);
        main_LBL_rightName.setText(rightName);

        progressBar = findViewById(R.id.progressBar) ;
        progressBar.setProgress(barProgress);       //initialized at 0
    }

    private void initButtonListeners(){
        Button main_BTN_next        = findViewById(R.id.main_BTN_next);
        main_BTN_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                playButtonSound();
                start();
                main_BTN_next.setEnabled(false);
            }
        });
    }

    private void newRound(){
        leftCard  = deck.dealCard();       //pull a new card every round
        rightCard = deck.dealCard();

        barProgress+=1;
        progressBar.setProgress(barProgress);

        setImageCard(leftCard,main_IMG_leftCard);
        setImageCard(rightCard,main_IMG_rightCard);

        if(leftCard.getValue() > rightCard.getValue()) {        //check which card has a higher value
            leftScore++;
            main_LBL_leftScore.setText(""+leftScore);
        }else if(leftCard.getValue() < rightCard.getValue()) {
            rightScore++;
            main_LBL_rightScore.setText(""+rightScore);
        }

        if (deck.getCardsLeft() == 0) {
            if(leftScore == rightScore){
                winnerName = "Draw";
            }else if(leftScore>rightScore){
                winnerName = leftName;
                winnerPokemon = leftPokemon;
                winnerScore = leftScore;
            } else {
                winnerName = rightName;
                winnerPokemon = rightPokemon;
                winnerScore = rightScore;
            }
            openWinnerActivity();
        }
    }

    private void openWinnerActivity() {
        Intent intent = new Intent(this, WinnerActivity.class);
        intent.putExtra("winnerName",winnerName);
        intent.putExtra("winnerPokemon",winnerPokemon);
        intent.putExtra("winnerScore",winnerScore);

        intent.putExtra("leftPokemon",leftPokemon);     //in case the player wants to restart with same names and pokemons from the winner activity
        intent.putExtra("rightPokemon",rightPokemon);
        intent.putExtra("leftName",leftName);
        intent.putExtra("rightName",rightName);

        startActivity(intent);
        finish();
    }

    private void setImageCard(Card card, ImageView img){
        String cardName = ""+card.getSuit().toString() +card.getValue();
        cardName = cardName.toLowerCase();

        int id = getResources().getIdentifier("com.example.cardgame:drawable/" + cardName, null, null);
        Glide.with(this).load(id).into(img);
    }

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        public void run() {
            handler.postDelayed(runnable, DELAY);
            if(deck.getCardsLeft()!=0)
                newRound();
            else
                stop();
        }
    };

    private void start() {
        handler.postDelayed(runnable, DELAY);
    }

    private void stop() {
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onStop() {
        mp.pause();
        super.onStop();
    }
    @Override
    public void onResume() {
        if(!mp.isPlaying())
            mp.start();
        super.onResume();
    }
    @Override
    public void onDestroy() {
        mp.release();
        mp = null;
        super.onDestroy();
    }


}