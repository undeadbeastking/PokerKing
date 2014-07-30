package Server;


import java.util.LinkedList;

/**
 * Created by Dester on 7/26/2014.
 */
public class ShowHand {

    private LinkedList<Hand> hands, winnerList;
    private int pot, finalMoney;

    public ShowHand(LinkedList<Hand> hands, int pot) {
        this.hands = hands;
        this.pot = pot;
        findWinners();
    }

    public int getFinalMoney() {
        return finalMoney;
    }

    public LinkedList<Hand> getWinnerList() {
        return winnerList;
    }

//    public void setPot(int pot){
//
//    }

    public void findWinners() {

        LinkedList<Hand> tieList = new LinkedList<Hand>();

        //find the strongest combination among all of the hands
        int strongestCom = findMaxInList(hands, 0);

        //find how many time strongest combination appear
        for (int i = 0; i < hands.size(); i++) {
            if (hands.get(i).getValues()[0] == strongestCom) {
                tieList.add(hands.get(i));
            }
        }
        if (tieList.size() > 1) {
            winnerList = handleTie(tieList, strongestCom);
            System.out.println("tie appear");
            splitPot();
        } else {
            winnerList = addToResult(hands, strongestCom, 0);
            System.out.println("no tie");
            finalMoney = pot;
        }

    }

    public void splitPot() {
        int numberOfWinner = winnerList.size();
        finalMoney = pot / numberOfWinner;

    }


    private LinkedList<Hand> handleTie(LinkedList<Hand> tieList, int strongestCom) {

        LinkedList<Hand> result = new LinkedList<Hand>();

        switch (strongestCom) {
            case 1:  //--------------------high card-------------------
                System.out.println("High card");
                int highCard_1 = findMaxInList(tieList, 1);
                int tieHighCard_1 = checkTie(tieList, highCard_1, 1);

                if (tieHighCard_1 > 1) {

                    int highCard_2 = findMaxInList(tieList, 2);
                    int tieHighCard_2 = checkTie(tieList, highCard_2, 2);

                    if (tieHighCard_2 > 1) {

                        int highCard_3 = findMaxInList(tieList, 3);
                        int tieHighCard_3 = checkTie(tieList, highCard_3, 3);

                        if (tieHighCard_3 > 1) {

                            int highCard_4 = findMaxInList(tieList, 4);
                            int tieHighCard_4 = checkTie(tieList, highCard_4, 4);

                            if (tieHighCard_4 > 1) {

                                int highCard_5 = findMaxInList(tieList, 5);
                                result = addToResult(tieList, highCard_5, 5);

                            } else {
                                result = addToResult(tieList, highCard_4, 4);
                            }

                        } else {
                            result = addToResult(tieList, highCard_3, 3);
                        }

                    } else {
                        result = addToResult(tieList, highCard_2, 2);
                    }

                } else {
                    result = addToResult(tieList, highCard_1, 1);
                }
                break;

            case 2: //--------------------one pair-------------------
                System.out.println("One pair");
                int onePair_1 = findMaxInList(tieList, 1);
                int tieOnePair_1 = checkTie(tieList, onePair_1, 1);

                if (tieOnePair_1 > 1) {

                    int onePair_2 = findMaxInList(tieList, 2);
                    int tieOnePair_2 = checkTie(tieList, onePair_2, 2);

                    if (tieOnePair_2 > 1) {

                        int onePair_3 = findMaxInList(tieList, 3);
                        int tieOnePair_3 = checkTie(tieList, onePair_3, 3);

                        if (tieOnePair_3 > 1) {

                            int onePair_4 = findMaxInList(tieList, 4);
                            result = addToResult(tieList, onePair_4, 4);

                        } else {
                            result = addToResult(tieList, onePair_3, 3);
                        }

                    } else {
                        result = addToResult(tieList, onePair_2, 2);
                    }

                } else {
                    result = addToResult(tieList, onePair_1, 1);
                }
                break;

            case 3: //--------------------two pair-------------------
                System.out.println("Two Pair");
                int twoPair_1 = findMaxInList(tieList, 1);
                int tieTwoPair_1 = checkTie(tieList, twoPair_1, 1);

                if (tieTwoPair_1 > 1) {

                    int twoPair_2 = findMaxInList(tieList, 2);
                    int tieTwoPair_2 = checkTie(tieList, twoPair_2, 2);

                    if (tieTwoPair_2 > 1) {

                        int twoPair_3 = findMaxInList(tieList, 3);
                        result = addToResult(tieList, twoPair_3, 3);

                    } else {
                        result = addToResult(tieList, twoPair_2, 2);
                    }
                } else {
                    result = addToResult(tieList, twoPair_1, 1);
                }
                break;

            case 4: //--------------------3 of the kind-------------------
                System.out.println("Three of the kind");
                int threeOTK_1 = findMaxInList(tieList, 1);
                int tieTheeOTK_1 = checkTie(tieList, threeOTK_1, 1);

                if (tieTheeOTK_1 > 1) {

                    int threeOTK_2 = findMaxInList(tieList, 2);
                    int tieThreeOTK_2 = checkTie(tieList, threeOTK_2, 2);

                    if (tieThreeOTK_2 > 1) {

                        int threeOTK_3 = findMaxInList(tieList, 3);
                        result = addToResult(tieList, threeOTK_3, 3);

                    } else {

                        result = addToResult(tieList, threeOTK_2, 2);

                    }

                } else {

                    result = addToResult(tieList, threeOTK_1, 1);

                }

                break;

            case 5: //--------------------straight-------------------
                System.out.println("Straight");
                int straight = findMaxInList(tieList, 1);
                result = addToResult(tieList, straight, 1);

                break;
            case 6: //--------------------flush-------------------
                System.out.println("Flush");
                int flush_1 = findMaxInList(tieList, 1);
                int tieFlush_1 = checkTie(tieList, flush_1, 1);

                if (tieFlush_1 > 1) {

                    int flush_2 = findMaxInList(tieList, 2);
                    int tieFlush_2 = checkTie(tieList, flush_2, 2);

                    if (tieFlush_2 > 1) {

                        int flush_3 = findMaxInList(tieList, 3);
                        int tieFlush_3 = checkTie(tieList, flush_3, 3);

                        if (tieFlush_3 > 1) {

                            int flush_4 = findMaxInList(tieList, 4);
                            int tieFlush_4 = checkTie(tieList, flush_4, 4);

                            if (tieFlush_4 > 1) {

                                int flush_5 = findMaxInList(tieList, 5);
                                result = addToResult(tieList, flush_5, 5);

                            } else {
                                result = addToResult(tieList, flush_4, 4);
                            }

                        } else {
                            result = addToResult(tieList, flush_3, 3);
                        }

                    } else {
                        result = addToResult(tieList, flush_2, 2);
                    }

                } else {
                    result = addToResult(tieList, flush_1, 1);
                }

                break;
            case 7: //--------------------full house-------------------
                System.out.println("Full House");
                int bigGroup = findMaxInList(tieList, 1);
                int tieBigGroup = checkTie(tieList, bigGroup, 1);

                if (tieBigGroup > 1) {

                    int smallGroup = findMaxInList(tieList, 2);
                    result = addToResult(tieList, smallGroup, 2);

                } else {
                    result = addToResult(tieList, bigGroup, 1);
                }
                break;

            case 8: //--------------------4 of the kind-------------------
                System.out.println("Four of the kind");
                int fourOTK_1 = findMaxInList(tieList, 1);
                int tie4OTK_1 = checkTie(tieList, fourOTK_1, 1);

                if (tie4OTK_1 > 1) {

                    int fourOTK_2 = findMaxInList(tieList, 2);
                    result = addToResult(tieList, fourOTK_2, 2);

                } else {
                    result = addToResult(tieList, fourOTK_1, 1);
                }

                break;

            case 9: //--------------------straight flush-------------------
                System.out.println("Straight flush");
                int straightFlush = findMaxInList(tieList, 1);
                result = addToResult(tieList, straightFlush, 1);
                break;

        }
        return result;
    }

    public int findMaxInList(LinkedList<Hand> list, int position) {

        int max = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValues()[position] > max) {
                max = list.get(i).getValues()[position];
            }
        }
        return max;
    }

    public LinkedList<Hand> addToResult(LinkedList<Hand> list, int max, int position) {

        LinkedList<Hand> result = new LinkedList<Hand>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValues()[position] == max) {
                result.add(list.get(i));
            }
        }

        return result;
    }

    public int checkTie(LinkedList<Hand> tieList, int max, int position) {

        int numberOfTie = 0;

        for (int i = 0; i < tieList.size(); i++) {
            if (tieList.get(i).getValues()[position] == max) {
                numberOfTie++;
            }
        }

        return numberOfTie;
    }


}
