package com.company.Player;

import com.company.Card.Card;

import java.util.ArrayList;
import java.util.Map;

public class Player {

    private String name = null;
    private ArrayList<Card> handleCard = null;
    private int money = 0;
    private Map<String,String> cardRate = null;

    public Player(){

    }
    public Player(String name, int baseMoney){
        this.name = name;
        this.money = baseMoney;
        handleCard = new ArrayList<Card>();
    }

    public void receiveCard(ArrayList<Card> receiveCardList) {
        this.handleCard.addAll(receiveCardList);
    }

    public void showHandleCard() {
        for(Card card : this.handleCard){
            System.out.print(card.getShape()+ " " +  card.getNumber() + " // ");
        }
        System.out.println();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getHandleCard() {
        return handleCard;
    }

    public void setHandleCard(ArrayList<Card> handleCard) {
        this.handleCard = handleCard;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Map<String, String> getCardRate() {
        return cardRate;
    }

    public void setCardRate(Map<String, String> cardRate) {
        this.cardRate = cardRate;
    }
}
