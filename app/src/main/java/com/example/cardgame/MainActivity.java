package com.example.cardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int leftScore = 0;
    private int rightScore = 0;
    private TextView main_LBL_leftName,main_LBL_rightName,main_LBL_leftScore,main_LBL_rightScore;
    private ImageView main_IMG_leftCard,main_IMG_rightCard;
    private Button main_BTN_next;
    private Deck deck;
    private Card leftCard,rightCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        main_LBL_leftScore =    findViewById(R.id.main_LBL_leftScore);
        main_LBL_rightScore =   findViewById(R.id.main_LBL_rightScore);
        main_LBL_leftName =     findViewById(R.id.main_LBL_leftName);
        main_LBL_rightName =    findViewById(R.id.main_LBL_rightName);
        main_IMG_leftCard =     findViewById(R.id.main_IMG_leftCard);
        main_IMG_rightCard =    findViewById(R.id.main_IMG_rightCard);
        main_BTN_next =         findViewById(R.id.main_BTN_next);

        deck = new Deck();

        main_BTN_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deck.getCardsLeft()!=0){
                    newRound();
                }
            }
        });


    }

    private void newRound(){
        leftCard = deck.dealCard();
        rightCard = deck.dealCard();

        setImageCard(leftCard,main_IMG_leftCard);
        setImageCard(rightCard,main_IMG_rightCard);

        if(leftCard.getValue() > rightCard.getValue()) {
            leftScore++;
            main_LBL_leftScore.setText(""+leftScore);
        }else if(leftCard.getValue() < rightCard.getValue()) {
            rightScore++;
            main_LBL_rightScore.setText(""+rightScore);
        } else if(leftCard.getValue() == rightCard.getValue()) {
            //draw add indicator
        }
    }

    private void setImageCard(Card card, ImageView img){
        String cardName = ""+card.getSuit().toString() +card.getValue();
        cardName = cardName.toLowerCase();

        int id = getResources().getIdentifier("com.example.cardgame:drawable/" + cardName, null, null);
        img.setImageResource(id);
    }


}