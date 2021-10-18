package com.company.Card;

import com.company.Const.ConstPoker;

import java.util.ArrayList;

public class CardManagement {

    public ArrayList<Card> makeDeck(){

        //Return Card List.
        ArrayList<Card> arrListReturnDeck = new ArrayList<Card>();

        //Deck Create.
        for(int i = 1 ; i < ConstPoker.DECK_TOTAL_NUMBER+1 ; i++){
            arrListReturnDeck.add(new Card(ConstPoker.SHAPE_HEART,i));
            arrListReturnDeck.add(new Card(ConstPoker.SHAPE_CLOVER,i));
            arrListReturnDeck.add(new Card(ConstPoker.SHAPE_DIAMOND,i));
            arrListReturnDeck.add(new Card(ConstPoker.SHAPE_SPADE,i));
        }
        return arrListReturnDeck;
    }

    public void shuffleCard(ArrayList<Card> arrListCard){
        //Shuffle Card.
        for(int i = 0 ; i < arrListCard.size()*3 ; i++){
            int x = (int)(Math.random() * (arrListCard.size()));
            int y = (int)(Math.random() * (arrListCard.size()));

            Card temp = arrListCard.get(x);
            arrListCard.set(x,arrListCard.get(y));
            arrListCard.set(y,temp);
        }

    }

    public void printDeck(ArrayList<Card> arrListDeck){
        int i = 0 ;
        for(Card card : arrListDeck){
            System.out.print(card.getShape() + card.getNumber() + "//");
            i++;
            if(i == 10){

                System.out.println();
                i = 0;
            }
        }
        System.out.println();
    }

}
