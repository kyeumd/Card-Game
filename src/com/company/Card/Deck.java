package com.company.Card;

import java.util.ArrayList;

public class Deck {

    private ArrayList<Card> arrListDeck;

    public Deck(){

    }


    public ArrayList<Card> deal(int iCntCard){
        ArrayList<Card> arrDealCard = new ArrayList<Card>();
        for(int i = 0 ; i < iCntCard ; i++){
            Card card = arrListDeck.remove(0);
            arrDealCard.add(card);
        }

        return arrDealCard;
    }

    public ArrayList<Card> getArrListDeck() {
        return arrListDeck;
    }

    public void setArrListDeck(ArrayList<Card> arrListDeck) {
        this.arrListDeck = arrListDeck;
    }
}
