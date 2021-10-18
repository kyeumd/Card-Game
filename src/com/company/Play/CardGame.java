package com.company.Play;

import com.company.Card.Card;
import com.company.Card.CardManagement;
import com.company.Card.Deck;
import com.company.Const.ConstPoker;
import com.company.Const.ConstQuestion;
import com.company.Player.Player;

import java.util.*;

public class CardGame {

    public static void main(String[] args) {

        ArrayList<Player> arrListPlayer = new ArrayList<Player>(); //플레이어 리스트

        CardManagement cardMange = new CardManagement();
        Deck deck = new Deck();

        //플레이어 입력
        arrListPlayer = makePlayer();

        //카드 셋팅
        gameSetting(cardMange,deck);

        //게임 플레이
        gamePlaying(cardMange,deck,arrListPlayer);


    }

    private static ArrayList<Player> makePlayer() {

        //플레이어 수
        int intPlayerCnt;
        //배팅 진행 후 남은 플레이어
        ArrayList<Player> arrListReturnPlayer = new ArrayList<Player>();

        Scanner scan = new Scanner(System.in);
        intPlayerCnt = Integer.parseInt(inputScan("int","플레이 할 사람의 수를 입력하시오."));

//        intPlayerCnt = 2;
        //admin 모드
        if(intPlayerCnt == 0 ) {
            Player p1 = new Player("admin",0);
            ArrayList<Card> listCard = new ArrayList<Card>();
            for(int i = 0 ; i < ConstPoker.SEVEN_POKER ; i++){
                int iCardnum = Integer.valueOf(inputScan("int", "카드 번호 입력"));
                String strShape = inputScan("str", "카드 모양 입력");
                switch (strShape){
                    case "S" : strShape = ConstPoker.SHAPE_SPADE;
                        break;
                    case "D" : strShape = ConstPoker.SHAPE_DIAMOND;
                        break;
                    case "H" : strShape = ConstPoker.SHAPE_HEART;
                        break;
                    case "C" : strShape = ConstPoker.SHAPE_CLOVER;
                        break;
                }
                listCard.add(new Card(strShape,iCardnum));
            }
            p1.receiveCard(listCard);
            arrListReturnPlayer.add(p1);
        }
        System.out.println("플레이 할 사람의 이름을 입력하시오.");

        for (int i = 0; i < intPlayerCnt; i++) {
            String name = inputScan("str");
//            String name = "Player " + (i+1);
            Player player = new Player(name,ConstPoker.BASE_MONEY);
            System.out.println(player.getName());
            player.setName(name);
            arrListReturnPlayer.add(player);
        }//end for
        System.out.print("--------- Player list : ");
        for (Player a : arrListReturnPlayer) {
            System.out.print(a.getName()+ " ");
        }//end for
        System.out.println(" ---------");
        return arrListReturnPlayer;
    }

    private static void gameSetting(CardManagement cardManage, Deck deck){

        System.out.println("--------- Deck Creat ---------");
        deck.setArrListDeck(cardManage.makeDeck());
        cardManage.printDeck(deck.getArrListDeck());

        System.out.println("--------- Deck Shuffle ---------");
        cardManage.shuffleCard(deck.getArrListDeck());
        cardManage.printDeck(deck.getArrListDeck());
    }

    private static void gamePlaying(CardManagement cardManage, Deck deck , ArrayList<Player> arrListPlayer) {
        ArrayList<Player> arrListRemainPlayer = new ArrayList<Player>();
        arrListRemainPlayer.addAll(arrListPlayer);
        System.out.println("arrListRemain" + arrListRemainPlayer.get(0).getName());
        int iTotalMoney = 0;
        //base bet -- todo

        // First 3 card deal.
        dealCard(deck,arrListRemainPlayer,3);

        //show one card -- todo

        // Second 1 card deal.
        dealCard(deck,arrListRemainPlayer,1);

        // First betting
//        iTotalMoney += betting(arrListRemainPlayer);

        System.out.println("-------First Betting Total Money : " + iTotalMoney);

        // Third 1 card deal.
        dealCard(deck,arrListRemainPlayer,1);

        // Second betting
//        iTotalMoney += betting(arrListRemainPlayer);

        System.out.println("-------Second Betting Total Money : " + iTotalMoney);

        // Fourth 1 card deal.
        dealCard(deck,arrListRemainPlayer,1);

        // Third betting
//        iTotalMoney += betting(arrListRemainPlayer);

        System.out.println("-------Third Betting Total Money : " + iTotalMoney);

        // Last Hidden 1 card deal.
        dealCard(deck,arrListRemainPlayer,1);

        // Last betting
//        iTotalMoney += betting(arrListRemainPlayer);

        System.out.println("-------Last Betting Total Money : " + iTotalMoney);

        // Choose Winner
        checkWinner(arrListRemainPlayer);
    }

    private static void dealCard(Deck deck , ArrayList<Player> arrListPlayer, int iCnt){
        System.out.println("--------- Card deal -----------");
//        for(Player p : arrListPlayer){
//            p.receiveCard(deck.deal(iCnt));
//            System.out.print(p.getName() + " Handle Card : ");
//            p.showHandleCard();
//        }//end for
    }

    private static int betting(ArrayList<Player> arrRemainPlayer){

        System.out.println("--------- Money Bet ---------");
        int iTotalBetMoney = 0;
        for(Player p : arrRemainPlayer){
            System.out.print(p.getName() + " Handle Card : ");
            p.showHandleCard();

            String strBetOrDie = "";
            while(true){
                strBetOrDie = inputScan("str",p.getName() + " Bet(B) or Die(D) ");
                if(ConstQuestion.BET.equals(strBetOrDie)){
                    break;
                }else if(ConstQuestion.DIE.equals(strBetOrDie)){
                    arrRemainPlayer.remove(p);
                    break;
                }
                else{
                    System.out.println("Wrong input. please retry");
                }
            }
            if(ConstQuestion.BET.equals(strBetOrDie)){
                while(true){
                    int iBetMoney = Integer.parseInt(inputScan("int",p.getName() + " Bet Money "));
                    if(iBetMoney > p.getMoney()){
                        System.out.println("You have " + p.getMoney()+ "$ only");
                    }else{
                        iTotalBetMoney += iBetMoney;
                        p.setMoney(p.getMoney()-iBetMoney);

                        break;
                    }
                }
            }

        }//end for
        //todo-- rush bet //now bet once time

        return iTotalBetMoney;
    }

    private static Player checkWinner(ArrayList<Player> arrRemainPlayer){
        Player pWinner = null;
        System.out.println("------- Check Winner -------");


        for(Player player : arrRemainPlayer){
            //카드 정렬
            player.setHandleCard(sortCard(player.getHandleCard()));
            //카드 등급 지정
            player.setCardRate(cardRate(player.getHandleCard()));
        }

        for(Player player : arrRemainPlayer){
            // 첫플레이어는 우선 Winner에 등록
            if(pWinner ==null) pWinner = player;
            else{
                
                int iPlayerRate = Integer.valueOf(player.getCardRate().get("carRate")) ;
                int iWinnerRate = Integer.valueOf(pWinner.getCardRate().get("carRate")) ;
                //카드 Rate 낮을수록 높은패
                if(iPlayerRate < iWinnerRate){
                    //Winner 변경
                    pWinner = player;
                }
                //Rate 같을경우
                else if(iPlayerRate == iWinnerRate){
                    //Flush 로 같은 경우 모양으로 우선 판단
                    if(iPlayerRate == 5){
                        int iPshape = checkShapeRate(player.getCardRate().get("topCardShape"));
                        int iWshape = checkShapeRate(pWinner.getCardRate().get("topCardShape"));
                        if(iPshape > iWshape){
                            pWinner = player;
                        }
                        //모양도 같을경우 가장 높은 수로 판단
                        else if(iPshape == iWshape){
                            if(Integer.valueOf(player.getCardRate().get("topCardNum")) > Integer.valueOf(pWinner.getCardRate().get("topCardNum"))){
                                pWinner = player;
                            }
                        }
                    }else{
                        int iPnum = Integer.valueOf(player.getCardRate().get("topCardNum"));
                        int iWnum = Integer.valueOf(pWinner.getCardRate().get("topCardNum"));
                        //숫자로 판단
                        if(iPnum > iWnum){
                            pWinner = player;
                        }
                        //숫자가 같을 경우 모양으로 판단
                        else if(iPnum == iWnum){
                            if(checkShapeRate(player.getCardRate().get("topCardShape")) > checkShapeRate(pWinner.getCardRate().get("topCardShape"))){
                                pWinner = player;
                            }
                        }
                    }
                }
            }

        }
        return pWinner;
    }

    private static Map<String,String> cardRate(ArrayList<Card> arrCard){
        Map<String,String> listResultCard = new HashMap<String,String>();
        int iContinueNum = 0;
        ArrayList<Card> arrPointCard = new ArrayList<Card>();
        Map<Card,Integer> mapPairCard = new HashMap<Card,Integer>();

        //check Straight
        iContinueNum = checkStraight(arrCard,arrPointCard);
        System.out.println("iContinueNum(Final) : " + iContinueNum);

        //is Straight
        if(iContinueNum >=4){
            System.out.println("Straight");
            listResultCard.put("cardRate", "7");

            String strCShape = checkShape(arrPointCard);
            if(!("".equals(strCShape)) && null != strCShape){
                System.out.println("shape : " +strCShape);
                ArrayList<Card> arrSameCard = new ArrayList<Card>();
                for(Card c : arrPointCard){
                    if(c.getShape() == strCShape) arrSameCard.add(c);
                }
                arrPointCard = new ArrayList<Card>();
                int iRSTF = checkStraight(arrSameCard, arrPointCard);
                //is Straight Flush
                if(iRSTF >= 4){
                    listResultCard.put("cardRate", "1");
                    listResultCard.put("topCardNum", String.valueOf(arrPointCard.get(arrPointCard.size()-1).getNumber()));
                    listResultCard.put("topCardShape", arrPointCard.get(arrPointCard.size()-1).getShape());


                }
            }
        }
        //no straight
        else{
            //check Pair
            String strCardRate = checkPair(arrCard, mapPairCard);
            listResultCard.put("cardRate", strCardRate);

            //가장 높은카드 선정을 위한 로직
            int iTempSame = 0;
            int iTempCardNum = 0;
            for(Card card : mapPairCard.keySet()){
                if(mapPairCard.get(card) > iTempSame ){
                    iTempSame = mapPairCard.get(card);
                    iTempCardNum = (Integer)card.getNumber();
                    listResultCard.put("topCardNum", String.valueOf(card.getNumber()));
                    listResultCard.put("topCardShape", String.valueOf(card.getShape()));
                }
                else if(mapPairCard.get(card) == iTempSame){
                    if( (Integer)card.getNumber() > iTempCardNum){
                        listResultCard.put("topCardNum", String.valueOf(card.getNumber()));
                        listResultCard.put("topCardShape", String.valueOf(card.getShape()));
                    }
                }
            }
            //Flush Check
            String strFShape = checkShape(arrCard);
            if(strFShape != ""){
                if(Integer.valueOf(listResultCard.get("cardRate")) > 5 ){
                    listResultCard.put("cardRate", "5");
                    for(Card card : arrCard){
                        if(card.getShape() == strFShape ){
                            listResultCard.put("topCardNum", String.valueOf(card.getNumber()));
                            listResultCard.put("topCardShape", String.valueOf(card.getShape()));
                        }
                    }

                }
            }
        }
        System.out.println(listResultCard.get("topCardNum") + " "+ listResultCard.get("topCardShape")  + " // "+ printCardRate(listResultCard.get("cardRate")));
        return listResultCard;
    }

    private static String printCardRate(String cardRate){
        String strResult = null;
        switch (cardRate){
            case "1" : strResult = "Royal STF";
                break;
            case "2" : strResult = "Straight Flush";
                break;
            case "3" : strResult = "Four Card";
                break;
            case "4" : strResult = "Full House";
                break;
            case "5" : strResult = "Flush";
                break;
            case "6" : strResult = "Mountain";
                break;
            case "7" : strResult = "Straight";
                break;
            case "8" : strResult = "Tripple";
                break;
            case "9" : strResult = "Two Pair";
                break;
            case "10" : strResult = "One Pair";
                break;
            case "11" : strResult = "TOP";
                break;
        }

        return strResult;
    }

    private static int checkStraight(ArrayList<Card> arrCard,ArrayList<Card> arrPointCard){
        System.out.println("------- Check Straight -------");

        int iContinueNum = 0;

        for(int j = 1 ; j < arrCard.size() ; j++) {
            //첫 장은 항상 queue 에 넣는다.
            if(arrPointCard.size() == 0 ){
                arrPointCard.add(arrCard.get(j-1));
            }else{
                //둘 째장 부터 같은숫자 제외 연속되는지 확인
                if((arrPointCard.get(arrPointCard.size()-1).getNumber() != arrCard.get(j-1).getNumber()) &&
                        (arrPointCard.get(arrPointCard.size()-1).getShape() != arrCard.get(j-1).getShape())){

                        arrPointCard.add(arrCard.get(j-1));
                }
            }
            if ((arrCard.get(j - 1).getNumber() + 1) == arrCard.get(j).getNumber()) {
                iContinueNum++;
                arrPointCard.add(arrCard.get(j));
                System.out.println("iContinueNum(Con) : " + iContinueNum);
            }
            else if(arrCard.get(j - 1).getNumber()== arrCard.get(j).getNumber() ){
                arrPointCard.add(arrCard.get(j));
                System.out.println("iContinueNum(Con) : " + iContinueNum);
            }
            else {
                iContinueNum = 0;
                arrPointCard.clear();
                System.out.println("iContinueNum(Stop) : " + iContinueNum);
            }
            System.out.println("Straight Card List : ");
            for(Card c : arrPointCard){
                System.out.print(c.getNumber() + c.getShape() + " ");
            }
            System.out.println();
        }
        return iContinueNum;
    }

    private static String checkPair(ArrayList<Card> arrCard, Map<Card,Integer> mapPairCard){
        String strRate = "0";

        int iCheckPair = 0;
        List<Integer> iCheckSame = new ArrayList<Integer>();
        //check Pair
        for(int i = arrCard.size()-1 ; i >= 0 ; i--){
            int itempPair = 0;
            boolean ifSame = false;
            //같은 모양이 이미 Pair에 들어가있는지 확인
            for(Integer iCheck : iCheckSame){
                if(arrCard.get(i).getNumber() == iCheck) ifSame = true;
            }
            if(!ifSame){
                for(int j = i-1 ; j >= 0 ; j--){

                    if(arrCard.get(i).getNumber() == arrCard.get(j).getNumber()){
                        mapPairCard.put(arrCard.get(i),++itempPair);
                        iCheckSame.add(arrCard.get(i).getNumber());
                    }
                    if(iCheckPair < itempPair) iCheckPair = itempPair;
                }
            }

        }
        System.out.println("Pair Count : " + iCheckPair);
        for(Card cd  : mapPairCard.keySet()){
            System.out.println("pair Card => " + cd.getNumber() + cd.getShape() + " : " + mapPairCard.get(cd));
        }

        //같은카드 4장
        if(iCheckPair == 3){
            strRate = "3"; //Four Card
        }
        //같은카드 3장
        else if(iCheckPair == 2){
            strRate = "8";
            //같은카드 3장 + 2장
            if(mapPairCard.size() > 1){
                strRate = "4";
            }

        }
        //같은카드 2장
        else if(iCheckPair ==1){
            strRate = "10";
            //같은카드 2장 + 2장
            if(mapPairCard.size() >1){
                strRate = "9";
            }
        }
        //같은카드 없음
        else if(iCheckPair == 0){
            strRate = "11";
        }

        System.out.println("result : " + strRate);
        return strRate;
    }

    private static String checkShape(ArrayList<Card> arrCard){
        System.out.println("------- Check Shape -------");
        Map<String, Integer> mapShapeCnt = new HashMap<String,Integer>();
        mapShapeCnt.put(ConstPoker.SHAPE_SPADE,0);
        mapShapeCnt.put(ConstPoker.SHAPE_DIAMOND,0);
        mapShapeCnt.put(ConstPoker.SHAPE_HEART,0);
        mapShapeCnt.put(ConstPoker.SHAPE_CLOVER,0);
        for(Card card : arrCard){
            switch (card.getShape()){
                case ConstPoker.SHAPE_SPADE: mapShapeCnt.put(ConstPoker.SHAPE_SPADE, mapShapeCnt.get(ConstPoker.SHAPE_SPADE) +1);
                    break;
                case ConstPoker.SHAPE_DIAMOND: mapShapeCnt.put(ConstPoker.SHAPE_DIAMOND, mapShapeCnt.get(ConstPoker.SHAPE_DIAMOND) +1);
                    break;
                case ConstPoker.SHAPE_HEART: mapShapeCnt.put(ConstPoker.SHAPE_HEART, mapShapeCnt.get(ConstPoker.SHAPE_HEART) +1);
                    break;
                case ConstPoker.SHAPE_CLOVER: mapShapeCnt.put(ConstPoker.SHAPE_CLOVER, mapShapeCnt.get(ConstPoker.SHAPE_CLOVER) +1);
                    break;
                default :
                    break;
            }

        }
        for(String key : mapShapeCnt.keySet()){

            if(mapShapeCnt.get(key) >= 5){
                System.out.println("------- shape 5 up--------");
                return key;
            }
        }
        return "";
    }

    private static ArrayList<Card> sortCard(ArrayList<Card> arrCard){
        System.out.println("------- Sort card -------");
        for(int i = 0 ; i < arrCard.size(); i++){
            for(int j = 0 ; j < arrCard.size()- (i+1); j++){
                //낮은 수 부터 앞으로 나오도록 정렬
                if(arrCard.get(j).getNumber() > arrCard.get(j+1).getNumber()){
                    Card tempCard = arrCard.get(j);
                    arrCard.set(j,arrCard.get(j+1));
                    arrCard.set(j+1,tempCard);
                }
                //숫자가 같을 시 C/H/D/S 순으로 정렬
                else if(arrCard.get(j).getNumber() == arrCard.get(j+1).getNumber()){
                    int iJrate01 = 0;
                    int iJrate02 = 0;
                    iJrate01 = checkShapeRate(arrCard.get(j).getShape());
                    iJrate02 = checkShapeRate(arrCard.get(j+1).getShape());
                    if(iJrate01 > iJrate02){
                        Card tempCard = arrCard.get(j);
                        arrCard.set(j,arrCard.get(j+1));
                        arrCard.set(j+1,tempCard);
                    }
                }
            }
        }
        for(Card card : arrCard ){
            System.out.print(card.getShape() + " "+card.getNumber() + " ");
        }
        System.out.println();
        return arrCard;
    }

    private static int checkShapeRate(String strShape){
        int iReturnRate = 0 ;
        switch(strShape){
            case ConstPoker.SHAPE_SPADE: iReturnRate = 4;
                break;
            case ConstPoker.SHAPE_DIAMOND: iReturnRate = 3;
                break;
            case ConstPoker.SHAPE_HEART: iReturnRate = 2;
                break;
            case ConstPoker.SHAPE_CLOVER: iReturnRate = 1;
                break;
            default :
                break;
        }

        return iReturnRate;
    }
    private static String inputScan( String strVType){
        String strReturn = null;
        Scanner scan = new Scanner(System.in);

        if("int".equals(strVType)){
            strReturn = String.valueOf(scan.nextInt());
        }
        else{
            strReturn = scan.next();
        }
        return strReturn;
    }

    private static String inputScan( String strVType, String comments){
        Scanner scan = new Scanner(System.in);
        if(!("".equals(comments)) && null != comments){
            System.out.println(comments);
        }
        return inputScan(strVType);
    }



}
