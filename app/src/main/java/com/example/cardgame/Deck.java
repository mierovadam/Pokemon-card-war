package com.example.cardgame;

import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private Card card;
    private ArrayList<Card> deck;
    private int cardsLeft = 0;

    public Deck() {
        this.deck = new ArrayList<Card>();
        createDeck();
    }


    private void createDeck(){
        for (Suit suit : Suit.values()) {
            for (int i=1 ; i<=13 ; i++) {       //card value from 1 to 13
                deck.add( new Card(i, suit));
                cardsLeft++;
            }
        }
        Collections.shuffle(deck);
    }

    public Card dealCard() {
        Card card = null;
        try{
            card = deck.remove(0);
        } catch(Exception e) {
            Log.i("dealError","com.example.cardgame.Deck is empty , cant pull/remove card.");
        }
        cardsLeft--;
        return card;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }

}