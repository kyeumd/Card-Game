package com.company.Card;

public class Card {

    private String shape = null;
    private int number = 0;

    public Card(){

    }

    public Card(String shape ,int number){
        this.shape = shape;
        this.number = number;
    }

    public String getShape() {
        return this.shape;
    }

    public int getNumber() {
        return this.number;
    }

}

