package Server;

import java.util.LinkedList;

/**
 * Created by Dester on 9/3/2014.
 */
public class TestShowHand {

    public static void main(String[] args) {
        LinkedList<Hand> hands = new LinkedList<Hand>();
        for (int i = 0; i < 20; i++) {
            Deck deck = new Deck();
            Hand hand = new Hand(deck);
            hands.add(hand);
        }
        ShowHand showHand = new ShowHand(hands, 1000);
        int numberOfWinner = showHand.getWinnerList().size();
        System.out.println("Number of winners: " + numberOfWinner);
        if (numberOfWinner > 1) {
            for (int i = 0; i < numberOfWinner; i++) {
                showHand.getWinnerList().get(i).display();
            }
        } else {
            showHand.getWinnerList().get(0).display();
        }
    }
}
