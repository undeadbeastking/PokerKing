package Server;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Agatha of Wood Beyond
 * <p/>
 * A Poker game Tutorial reference:
 * 5 card-stud
 * http://www.dreamincode.net/forums/topic/116864-how-to-make-a-poker-game-in-java/
 */

public class Deck {
    private int id = 0;

    //Deck cards
    private ArrayList<Card> cards;

    //Community cards
    private Card[] commuCards;

    public Deck() {
        cards = new ArrayList<Card>();

        //Create a Poker deck with arranged order
        //4 types of suit
        for (int i = 1; i <= 4; i++) {
            //For each suit, create 13 cards
            for (int j = 1; j <= 13; j++) {
                cards.add(new Card(i, j));
            }
        }

        /*
        Shuffling the cards by switching positions of 100 pairs
        */
        int position1, position2;
        Random generator = new Random();
        Card temp;
        for (int i = 0; i < 100; i++) {
            //Random any 2 cards from the deck
            position1 = generator.nextInt(cards.size() - 1);
            position2 = generator.nextInt(cards.size() - 1);

            //Switch 2 chosen cards' positions
            temp = cards.get(position1);
            cards.set(position1, cards.get(position2));
            cards.set(position2, temp);
        }

        //Burn 3 cards
        for (int i = 0; i < 3; i++) {
            this.drawFromDeck();
        }

        //Get community cards from the created deck
        commuCards = new Card[5];
        for (int i = 0; i < 5; i++) {
            commuCards[i] = this.drawFromDeck();
        }
    }

    public int generateID() {
        return id++;
    }

    public Card[] getCommuCards() {
        return commuCards;
    }

    //Draw the top card from a deck
    public Card drawFromDeck() {
        return cards.remove(0);
    }

    //How many cards the deck still has
    public int getTotalCards() {
        return cards.size();
    }
}
