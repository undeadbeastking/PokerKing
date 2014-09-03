package Server;

/**
 * Created by Agatha of Wood Beyond
 * <p/>
 * A Poker game Tutorial reference:
 * 5 card-stud
 * http://www.dreamincode.net/forums/topic/116864-how-to-make-a-poker-game-in-java/
 */

public class Card {

    //Suit & Rank as String array
    private static String[] suits = {"clubs", "diamonds", "hearts", "spades"};
    private static String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "Jack", "Queen", "King"};
    //suit: 1 - 4, rank: 1 - 13
    private int suit, rank;

    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    //Return card's full info
    public String CardName() {
        return ranks[rank] + " of " + suits[suit] + ".";
    }

    //Fast access to card's info
    @Override
    public String toString() {
        return suit + "," + rank;
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }
}
