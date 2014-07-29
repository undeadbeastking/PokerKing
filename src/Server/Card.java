package Server;

/**
 * Created by Agatha of Wood Beyond
 *
 * A Poker game Tutorial reference:
 * 5 card-stud
 * http://www.dreamincode.net/forums/topic/116864-how-to-make-a-poker-game-in-java/
 */

public class Card {

    //suit: 1 - 4, rank: 1 - 13
    private int suit, rank;

    //Suit & Rank as String array
    private static String[] suits = {"clubs", "diamonds", "hearts", "spades"};
    private static String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "Jack", "Queen", "King"};

    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    //Return a card's rank as String
    public static String rankAsString(int x) {
        //x: 1 -> 13 but array first element is 0
        if (x < 1 || x > 13) {
            return "Unavailable rank.";
        }
        return ranks[x - 1];
    }

    //A card object full info
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
