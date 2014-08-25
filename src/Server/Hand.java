package Server;

/**
 * Created by Agatha of Wood Beyond
 * <p/>
 * A Poker game Tutorial reference:
 * 5 card-stud
 * http://www.dreamincode.net/forums/topic/116864-how-to-make-a-poker-game-in-java/
 */

public class Hand {

    private int id;
    private Card[] cards;
    private int[] values;
    private Card[] bestOfAHand;

    /*
    Every hand will have 5 same community cards and 2 random cards from the created deck

    Array 'values' - first index is the best available combination of a hand like pair, strightFlush,...
    other 5 indexes are chosen ranks from high to low
     */

    public Hand(Deck d) {
        /*
        Initialize Player hand
         */
        id = d.generateID();//Id of a specific Hand

        cards = new Card[7];
        bestOfAHand = new Card[5];//For display Purpose
        values = new int[6];//first index is the strongest combination of a hand

        //2 cards on Hand
        cards[0] = d.drawFromDeck();
        cards[1] = d.drawFromDeck();
        //5 cards from table (Community cards)
        Card[] commuCards = d.getCommuCards();
        for (int i = 2; i < 7; i++) {
            cards[i] = commuCards[i - 2];
        }

        //Evaluate best 5 of 7
        handEvaluate();
    }

    //Test Evaluate Constructor
    public Hand(Card one, Card two, Card three, Card four, Card five, Card six, Card seven) {
        /*
        Initialize Player hand
         */
        cards = new Card[7];
        values = new int[6];//first index is the strongest combination of a hand

        cards[0] = one;
        cards[1] = two;
        cards[2] = three;
        cards[3] = four;
        cards[4] = five;
        cards[5] = six;
        cards[6] = seven;

        //Evaluate best 5 of 7
        handEvaluate();
    }

    public int getId() {
        return id;
    }

    private void handEvaluate() {

        /*
        2 hole cards on our hand have the same Suit -> Flush and SF possibility
         */
        if (cards[0].getSuit() == cards[1].getSuit()) {
            int rank[] = new int[14];
            int suitOfFlush = cards[0].getSuit();
            //of 7 cards, those with the suit of FLush will go into this array
            for (int i = 0; i < 7; i++) {
                if (suitOfFlush == cards[i].getSuit())
                    rank[cards[i].getRank()]++;
            }
            /*
            Check StraightFlush
            Break Tie: Store the Highest card in values[1]
             */

            //Royal StraightFlush
            if (rank[10] == 1 && rank[11] == 1 && rank[12] == 1 && rank[13] == 1 && rank[1] == 1 &&
                    //Check if 2 hole cards are from these 5
                    ((cards[0].getRank() == 1 || (10 <= cards[0].getRank() && (cards[0].getRank() <= 13))) &&
                            (cards[1].getRank() == 1 || (10 <= cards[1].getRank() && (cards[1].getRank() <= 13)))
                    )) {
                values[0] = 9;//9 = StraightFlush
                values[1] = 14;//Ace as High card

                //Store best result of a hand
                bestOfAHand[0] = new Card(suitOfFlush, 1);
                int startFromKing = 13;
                for (int i = 0; i < 4; i++) {
                    bestOfAHand[i] = new Card(suitOfFlush, startFromKing);
                    startFromKing--;
                }

            } else {
                /*
                Normal Straight Flush
                Ace can belong to 'Royal StraightFlush' or 'Lowest StraightFlush'
                 */
                for (int i = 13; i >= 5; i--) {
                    if (rank[i] == 1 && rank[i - 1] == 1 && rank[i - 2] == 1 && rank[i - 3] == 1 && rank[i - 4] == 1 &&
                            //Check if 2 hole cards are from these 5
                            (i >= cards[0].getRank() && (cards[0].getRank() >= i - 4)) &&
                            (i >= cards[1].getRank() && (cards[1].getRank() >= i - 4))) {

                        values[0] = 9;//9 = StraightFlush
                        values[1] = i;//Highest card

                        //Store best result of a hand
                        int startFromKing = 13;
                        for (int j = 0; j < 5; j++) {
                            bestOfAHand[j] = new Card(suitOfFlush, startFromKing);
                            startFromKing--;
                        }
                        break;
                    }
                }
            }

            /*
            Not Straight Flush -> Checking Flush
             */
            if (values[0] != 9) {
                int cardsWithFlushSuit = 0;
                for (int i = 13; i > 0; i--) {
                    if (rank[i] == 1) {
                        cardsWithFlushSuit++;
                    }
                    //at least 5 cards with same suit to make a Flush
                    if (cardsWithFlushSuit >= 5) {
                        values[0] = 6;
                        /*
                        Break tie: Only need the highest hole card of a hand

                        All Flush hands of a game must have the same Suit. Because Stricly at least 3 Commu cards have same suit with 2
                            hole cards from a handto form a Flush. That makes the remain 2 Commu cards insignificant! Since
                            not enough commu cards left to form another FLush with different suit so other hands with Flush
                            end up belong to that Suit also.

                        Break tie Equation:
                        Community cards (Can have >= 0 cards) > Highest hole card of hand 1
                            > Community cards (Can have >= 0 cards) > Highest hole card of hand 2 > ... > Highest hole card of hand 3
                         */
                        //One of the Hole card is an Ace-> set Highest hole card Energy to 14 instead of its rank (1)
                        if (cards[0].getRank() == 1 || cards[1].getRank() == 1) {
                            values[1] = 14;
                        } else {
                            //Normal rank from 13 to 2
                            values[1] = cards[0].getRank() > cards[1].getRank() ? cards[0].getRank() : cards[1].getRank();
                        }
                        break;
                    }
                }
                //Store best result of a hand
                if (values[0] == 6) {
                    bestOfAHand[0] = cards[0];
                    bestOfAHand[1] = cards[1];
                    //Avoid repicking the hole cards
                    int hole1Rank = cards[0].getRank();
                    int hole2Rank = cards[1].getRank();
                    //2 slots occupied, then point the index to slot 3
                    int index = 2;
                    //Got an Ace but it is not a hole card, then 100% it will be included
                    if ((hole1Rank != 1 && hole2Rank != 1) && rank[1] == 1) {
                        bestOfAHand[index] = new Card(suitOfFlush, 1);
                        index++;
                    }
                    for (int i = 13; i > 1; i--) {
                        /*
                        Ignore 2 hole cards, loop from top rank to lower rank to find available Commu cards
                         */
                        if ((i != hole1Rank && i != hole2Rank) && rank[i] == 1) {
                            bestOfAHand[index] = new Card(suitOfFlush, i);
                            index++;
                        }
                        //get enough Commu cards then ignore the remaining with lower Rank
                        if (index > 4) {
                            break;
                        }
                    }
                }
            }
        }

        /*
        StraightFlush fails then find other combinations but still keep result of Flush
         */
        if (values[0] != 9) {
            int rank[];
            //Count cards of each rank but this time without Suit condition
            rank = new int[14];
            for (int i = 0; i < 7; i++) {
                rank[cards[i].getRank()]++;
            }

            /*
            Find 2 ranks which have the most cards
            Quantity matters (4 of a kind is valuable! even if it has a rank of 2)
             */
            int bigQuan = 1, smallerQuan = 1;//Rank with big quantity, rank with smaller quantity
            int bigQuanRank = 0, smallerQuanRank = 0;
            for (int i = 13; i > 0; i--) {
                if (rank[i] > bigQuan) {
                    if (bigQuan != 1) {
                        //Pass the old rank with less cards to smallerQuan
                        smallerQuan = bigQuan;
                        smallerQuanRank = bigQuanRank;
                    }
                    //Set to the rank with more cards
                    bigQuan = rank[i];
                    bigQuanRank = i;

                } else if (rank[i] > smallerQuan) {
                    smallerQuan = rank[i];
                    smallerQuanRank = i;
                }
            }

            /*
            Check four of a kind
            Big group has 4 cards but at least a hole card is in there.
            Else cannot get 4 of them out and still get 2 hole cards out because it makes 6
             */
            if (bigQuan == 4 && (cards[0].getRank() == bigQuanRank || cards[1].getRank() == bigQuanRank)) {
                values[0] = 8;
                /*
                Break tie: Just need to know the Energy of the 4. Because of the involve of at least one hole card
			    in there makes it inaccessible to other players (Careful Ace!)
                 */
                values[1] = bigQuanRank == 1 ? 14 : bigQuanRank;

                /*
                Store best result of a hand
                 */
                for (int i = 1; i <= 4; i++) {
                    bestOfAHand[i - 1] = new Card(i, bigQuanRank);
                }
                /*
                Get the fifth card
                1 of the hole card is not from group 4, this hole card will be the fifth
                 */
                if (cards[0].getRank() != cards[1].getRank()) {
                    bestOfAHand[4] = bigQuanRank == cards[0].getRank() ? cards[1] : cards[0];
                } else {
                    //Fifth card from Community cards
                    if (bigQuanRank != 1 && rank[1] > 0) {
                        //Know the card is available but we still need to know the suit of at least one Ace
                        for (int i = 2; i <= 6; i++) {
                            if (cards[i].getRank() == 1) {
                                bestOfAHand[4] = cards[i];
                                break;
                            }
                        }
                    } else {
                        for (int i = 13; i > 1; i++) {
                            if (bigQuanRank != i && rank[i] > 0) {
                                //Get the suit of a card at that rank from Community
                                for (int j = 2; j <= 6; j++) {
                                    if (cards[j].getRank() == i) {
                                        bestOfAHand[4] = cards[j];
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }

                /*
                Activate checking Full House only if we have a group of 3 and at least another group >= 2
                 */
            } else if (bigQuan == 3 && smallerQuan >= 2) {
                /*
                bigQuan:3 - smallerQuan:3 - 1 stray card
                Hole 1 & 2 must belong to any group of 3. Else, We only got 3 of a kind
                 */
                if (bigQuan == 3 && smallerQuan == 3) {
                    if ((cards[0].getRank() == bigQuanRank || cards[0].getRank() == smallerQuanRank)
                            && (cards[1].getRank() == bigQuanRank || cards[1].getRank() == smallerQuanRank)) {

                        values[0] = 7;//is Full house now
                        //Get energy of the Triple and the Pair
                        /*
                        bigQuanRank || smallerQuanRank at 1 then Energy will be 14 else there is no group 3 of Ace at all
                        => bigQuanRank got normal rank, use it normally
                         */
                        values[1] = bigQuanRank == 1 ? 14 : smallerQuanRank == 1 ? 14 : bigQuanRank;
                        /*
                        smallerQuanRank is 1 then use bigQuan else in normal cases, we use smallerQuanRank
                         */
                        values[2] = smallerQuanRank == 1 ? bigQuanRank : smallerQuanRank;//get energy of Pair

                        //Get display
                        int index = 0;
                        for (int j = 0; j < 2; j++) {
                            int cardWithThisRank;
                            if (j == 0) {
                                cardWithThisRank = bigQuanRank == 1 ? 14 : smallerQuanRank == 1 ? 14 : bigQuanRank;
                            } else {
                                cardWithThisRank = smallerQuanRank == 1 ? bigQuanRank : smallerQuanRank;
                            }

                            for (int i = 0; i < 7; i++) {
                                if (cards[i].getRank() == cardWithThisRank) {
                                    bestOfAHand[index] = cards[i];
                                    index++;
                                }
                                if (cardWithThisRank == bigQuanRank && index > 2) {
                                    break;
                                } else if (cardWithThisRank == smallerQuanRank && index > 4) {
                                    break;
                                }
                            }
                        }
                    }
                /*
                bigQuan:3 - smallerQuan:2
                Possible combinations: 3 - 2 - 2, 3 - 2 - 2 stray cards
                */
                } else if (bigQuan == 3 && smallerQuanRank == 2) {
                    //Both holes in the group 3
                    if (cards[0].getRank() == bigQuanRank && cards[1].getRank() == bigQuanRank) {
                        values[0] = 7;
                        values[1] = bigQuanRank == 1 ? 14 : bigQuanRank;
                        //No Ace pair then even if we got 1 - 2 pairs, they will have normal ranks from 13 -> 2
                        //=> smallerGroup will got the highest pair
                        values[2] = rank[1] == 2 ? 14 : smallerQuanRank;

                        //Get display
                        int index = 0;
                        //3 cards from big group
                        for (int i = 0; i < 7; i++) {
                            if (bigQuanRank == cards[i].getRank()) {
                                bestOfAHand[index] = cards[i];
                                index++;
                                if (index > 3) break;
                            }
                        }
                        //Loop & get Highest Pair
                        int highestPairRank = rank[1] == 2 ? 1 : smallerQuanRank;
                        for (int i = 0; i < 7; i++) {
                            if (highestPairRank == cards[i].getRank()) {
                                bestOfAHand[index] = cards[i];
                                index++;
                                if (index > 4) break;
                            }
                        }
                        //1 of the hole card is in the group of 3 -> the remaining hole must belong to a pair
                    } else if (cards[0].getRank() == bigQuanRank || cards[1].getRank() == bigQuanRank) {
                        int pairHoleCardRank = cards[0].getRank() == bigQuanRank ? cards[1].getRank() : cards[0].getRank();
                        //the other hole card must belong to a pair
                        if (rank[pairHoleCardRank] == 2) {
                            values[0] = 7;
                            values[1] = bigQuanRank == 1 ? 14 : bigQuanRank;
                            values[2] = pairHoleCardRank == 1 ? 14 : pairHoleCardRank;

                            //Get display
                            int index = 0;
                            //3 cards from big group
                            for (int i = 0; i < 7; i++) {
                                if (bigQuanRank == cards[i].getRank()) {
                                    bestOfAHand[index] = cards[i];
                                    index++;
                                    if (index > 3) break;
                                }
                            }
                            //Get the pair which contains the other hole card
                            for (int i = 0; i < 7; i++) {
                                if (pairHoleCardRank == cards[i].getRank()) {
                                    bestOfAHand[index] = cards[i];
                                    index++;
                                    if (index > 4) break;
                                }
                            }
                        }
                        //Both hole cards is a pair
                    } else if (cards[0].getRank() != bigQuanRank && cards[1].getRank() != bigQuanRank) {
                        if (cards[0].getRank() == cards[1].getRank()) {
                            values[0] = 7;
                            values[1] = bigQuanRank == 1 ? 14 : bigQuanRank;
                            values[2] = cards[0].getRank() == 1 ? 14 : cards[0].getRank();

                            //Get Display
                            int index = 0;
                            //3 cards from big group
                            for (int i = 0; i < 7; i++) {
                                if (bigQuanRank == cards[i].getRank()) {
                                    bestOfAHand[index] = cards[i];
                                    index++;
                                    if (index > 3) break;
                                }
                            }
                            bestOfAHand[3] = cards[0];
                            bestOfAHand[4] = cards[1];
                        }
                    }
                }
                /*
                Check if Flush has been found above
                 */
            } else if (values[0] == 6) {
                //Do nothing

                /*
                Check Straight
                 */
            } else {
                /*
                Check Straight
                At least one card of each rank on a row to form a Straight
                2 hole cards with same rank then we cannot summon a Straight since it will make a combination of 6

                Break Tie: Card with highest rank
                 */
                if (cards[0].getRank() != cards[1].getRank()) {
                    //The case with Ace
                    if (rank[1] >= 1 && rank[13] >= 1 && rank[12] >= 1 && rank[11] >= 1 && rank[10] >= 1 &&
                            //2 hole cards must be in this range
                            (cards[0].getRank() == 1 || (10 <= cards[0].getRank() && cards[0].getRank() <= 13)) &&
                            (cards[1].getRank() == 1 || (10 <= cards[1].getRank() && cards[1].getRank() <= 13))) {

                        //Get Energy
                        values[0] = 5;
                        values[1] = 14;

                        //Get display cards
                        bestOfAHand[0] = cards[0];
                        bestOfAHand[1] = cards[1];
                        int index = 2;
                        //Both hole cards are not Ace. Then put Ace in there
                        if(cards[0].getRank() != 1 && cards[1].getRank() != 1){
                            for (int i = 2; i < 7; i++) {//ignore 2 hole cards
                                if(cards[i].getRank() == 1){
                                    bestOfAHand[index] = cards[i];
                                    index++;
                                    break;
                                }
                            }
                        }
                        for (int i = 13; i >= 10; i--) {
                            //Skip cards which have the same rank with 2 hole cards (Already added)
                            if(cards[0].getRank() != i && cards[1].getRank() != i){
                                for (int j = 2; j < 7; j++) {//ignore 2 hole cards
                                    if(cards[j].getRank() == i){
                                        bestOfAHand[index] = cards[j];
                                        index++;
                                        break;
                                    }
                                }
                            }
                            if(index > 4)   break;
                        }

                    } else {
                        for (int i = 13; i >= 5; i--) {
                            if (rank[i] >= 1 && rank[i - 1] >= 1 && rank[i - 2] >= 1 && rank[i - 3] >= 1 && rank[i - 4] >= 1 &&
                                    //2 hole cards must be in this range
                                    (i-4 <= cards[0].getRank() && cards[0].getRank() <= i) &&
                                    (i-4 <= cards[1].getRank() && cards[1].getRank() <= i)) {

                                values[0] = 5;
                                values[1] = i;

                                //Get display cards
                                bestOfAHand[0] = cards[0];
                                bestOfAHand[1] = cards[1];
                                int index = 2;
                                /*
                                At this state we know the range of Straight is from Rank i-4 -> i
                                 */
                                for (int j = i; j >= i-4; j--) {
                                    //Skip cards which have the same rank with 2 hole cards (Already added)
                                    if(cards[0].getRank() != j && cards[1].getRank() != j){
                                        for (int x = 2; x < 7; x++) {//ignore 2 hole cards
                                            if(cards[x].getRank() == j){
                                                bestOfAHand[index] = cards[x];
                                                index++;
                                                break;
                                            }
                                        }
                                    }
                                    if(index > 4)   break;
                                }

                                break;
                            }
                        }
                    }
                }

                //No Straight - Proceed to other cases
                if (values[0] != 5) {
                    /*
                    Check 3 of a kind

                    The 3 can be on the table only, on our hand we got 2 high cards which means will be used
                    to break tie with other 3 of a kind hands
                     */
                    if (bigQuan == 3) {
                        values[0] = 4;
                        //Get Energy
                        values[1] = bigQuanRank == 1 ? 14 : bigQuanRank;
                        if(cards[0].getRank() != bigQuanRank && cards[1].getRank() != bigQuanRank){
                            int biggerRank = cards[0].getRank() > cards[1].getRank() ? cards[0].getRank() : cards[1].getRank();
                            values[2] = cards[0].getRank() == 1 ? 14 : cards[1].getRank() == 1 ? 14 : biggerRank;

                        }

                        /*
                        Check 2 pairs
                         */
                    } else if (bigQuan == 2 && smallerQuan == 2) {
                        values[0] = 3;
                        /*
                        There is no big group or small group when we get 2 pairs
                        but the first pair will always go to bigGroup and the second go to smallGroup

                        But the end can be a pair of Ace which is the first strongest Pair
                         */
                        if (smallerQuanRank == 1) {
                            //Pair of Ace
                            values[1] = 14;
                            //Get the second pair
                            values[2] = bigQuanRank;//13 -> 2
                            //get the High card
                            for (int i = 13; i >= 2; i--) {
                                if (rank[i] == 1) {
                                    values[3] = i;
                                    break;
                                }
                            }

                        } else {
                            //No pair of Ace
                            values[1] = bigQuanRank;
                            values[2] = smallerQuanRank;
                            //FInal card
                            if (rank[1] == 1) {
                                //There is an Ace
                                values[3] = 14;
                            } else {
                                //No Ace - Find Normal
                                for (int i = 13; i >= 2; i--) {
                                    if (rank[i] == 1) {
                                        values[3] = i;
                                        break;
                                    }
                                }
                            }
                        }
                        /*
                        Check 1 pair
                         */
                    } else if (bigQuan == 2) {
                        values[0] = 2;
                        //Rank of Pair
                        if (bigQuanRank == 1) {
                            values[1] = 14;
                        } else {
                            values[1] = bigQuanRank;
                        }

                        //Get 3 cards
                        int index = 2;
                        //There is an Ace???
                        if (rank[1] == 1) {
                            values[index] = 14;
                            index++;
                        }
                        for (int i = 13; i >= 2; i--) {
                            if (rank[i] == 1) {
                                values[index] = i;
                                index++;
                            }
                            // Get enough 3
                            if (index > 4) {
                                break;
                            }
                        }

                        /*
                        High card
                         */
                    } else {
                        values[0] = 1;
                        int index = 1;
                        //1 Ace
                        if (rank[1] == 1) {
                            values[index] = 14;
                            index++;
                        }
                        //Remaining
                        for (int i = 13; i >= 2; i--) {
                            if (rank[i] == 1) {
                                values[index] = i;
                                index++;
                            }
                            if (index > 5) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void display() {
        for (int i = 0; i < 7; i++) {
            System.out.print(cards[i] + ", ");
        }
        System.out.println();

        switch (values[0]) {
            case 9:
                System.out.println("Straight FLush: " + values[1]);
                break;
            case 8:
                System.out.println("Four of a kind: " + values[1] + " " + values[2]);
                break;
            case 7:
                System.out.println("Full House: " + values[1] + " " + values[2]);
                break;
            case 6:
                System.out.println("Flush: " + values[1] + " " + values[2] + " " + values[3] + " " + values[4] + " " + values[5]);
                break;
            case 5:
                System.out.println("Straight: " + values[1]);
                break;
            case 4:
                System.out.println("3 of a kind: " + values[1] + " " + values[2] + " " + values[3]);
                break;
            case 3:
                System.out.println("2 pairs: " + values[1] + " " + values[2] + " " + values[3]);
                break;
            case 2:
                System.out.println("1 pair: " + values[1] + " " + values[2] + " " + values[3] + " " + values[4]);
                break;
            case 1:
                System.out.println("High Card: " + values[1] + " " + values[2] + " " + values[3] + " " + values[4] + " " + values[5]);
                break;

            default:
                System.out.println("Unrecognized Hand.");
        }
        System.out.println();
    }

    //    public Card[] getCards() {
//        return cards;
//    }
    public String getCards() {
        String myCards = cards[0].toString() + "-" + cards[1].toString();
        String commuCards = cards[2].toString() + "-" + cards[3].toString() + "-" + cards[4].toString() + "-" + cards[5].toString() + "-" + cards[6].toString();
        String allCards = myCards + "/" + commuCards;
        return allCards;
    }

    public int[] getValues() {
        return values;
    }
}
