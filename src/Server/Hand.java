package Server;



/**
 * Created by Agatha of Wood Beyond
 * <p/>
 * A Poker game Tutorial reference:
 * 5 card-stud
 * http://www.dreamincode.net/forums/topic/116864-how-to-make-a-poker-game-in-java/
 */

public class Hand {

    private Card[] cards;
    private int[] values;
    private int id;


    // DEFAULT CONSTRUCTOR
    public Hand(){}
    /*
    Every hand will have 5 same community cards and 2 random cards from the created deck

    Array 'values' - first index is the best available combination of a hand like pair, strightFlush,...
    other 5 indexes are chosen ranks from high to low
     */

    public Hand(Deck d) {
        /*
        Initialize Player hand
         */

        cards = new Card[7];
        values = new int[6];//first index is the strongest combination of a hand
        for (int i = 0; i < 6; i++) {
            values[i] = 0;
        }
        //2 cards on Hand
        cards[0] = d.drawFromDeck();
        cards[1] = d.drawFromDeck();
        //5 cards from table (Community cards)
        Card[] commuCards = d.getCommuCards();
        for (int i = 2; i < 7; i++) {
            cards[i] = commuCards[i - 2];
        }
        id = d.generateID();
        //Evaluate best 5 of 7
        handEvaluate();
    }

    //Test Evaluate Constructor
    public Hand(Card one, Card two, Card three, Card four, Card five, Card six, Card seven) {

        /*
        Initialize Player hand
         */

        cards = new Card[7];
        cards[0] = one;
        cards[1] = two;
        cards[2] = three;
        cards[3] = four;
        cards[4] = five;
        cards[5] = six;
        cards[6] = seven;

        values = new int[6];//first index is the strongest combination of a hand
        for (int i = 0; i < 6; i++) {
            values[i] = 0;
        }

        //Evaluate best 5 of 7
        handEvaluate();
    }

    public int getId(){
        return id;
    }

    private void handEvaluate() {

        /*
        Find the Suit with most cards
         */
        int flushSuit = -1, curSuit, cardCount = 1;
        for (int i = 0; i < 7; i++) {
            curSuit = cards[i].getSuit();
            cardCount = 1;

            for (int j = 0; j < 7; j++) {
                if (i != j && curSuit == cards[j].getSuit()) {
                    cardCount++;
                }
            }
            /*
            Check Flush
            Of 7 cards can only have 1 suit with 5 - 7 cards
             */
            if (cardCount >= 5) {
                flushSuit = curSuit;
                break;
            }
        }

        /*
        Check StraightFlush
         */
        int rankSF[] = new int[14];

        if (flushSuit != -1) {
            /*
            Count cards of each rank with the suit of Flush
            Ignore index 0 (Since there is No rank 0)
             */
            for (int i = 0; i < 14; i++) {
                rankSF[i] = 0;
            }
            for (int i = 0; i < 7; i++) {
                //Only take cards with Flush suit
                if (cards[i].getSuit() == flushSuit) {
                    rankSF[cards[i].getRank()]++;
                }
            }

            /*
            Check StraightFlush

            Break Tie:
            Only need to record the lead card
             */
            //Royal StraightFlush - The unbeatable
            if (rankSF[10] == 1 && rankSF[11] == 1 && rankSF[12] == 1 && rankSF[13] == 1 && rankSF[1] == 1) {
                values[0] = 9;//9 = StraightFlush
                values[1] = 14;//Ace as High card

            } else {
                /*
                Ace is funny because it can be the strongest (Royal StaightFlush) or the weakest (Low SF)
                 */
                for (int i = 13; i >= 5; i--) {
                    if (rankSF[i] == 1 && rankSF[i - 1] == 1 && rankSF[i - 2] == 1 && rankSF[i - 3] == 1 && rankSF[i - 4] == 1) {
                        values[0] = 9;//9 = StraightFlush
                        values[1] = i;//Highest Rank
                        break;
                    }
                }
            }

            /*
            Not StraightFlush then can only be FLUSH
            Just a premature set. If we encounter Four of a kind then we can override Flush later
             */
            if (values[0] != 9) {
                values[0] = 6;
                /*
                Get Highest cards to break tie

                3   5    6   7   8
                3   4    5   7   8

                Not right in Table play case when best hand is table-flush

                Break Tie:
                All hands end up with FLush will belong to same Suit
                => need to record the ranks of all 5 cards from Flush-Hand
                 */

                int handCount = 1;//1 -> 5 - card order on a player hand

                //Inspect Ace first so there is no need to include rank 1 in the next Loop
                if (rankSF[1] == 1) {
                    values[handCount] = 14;
                    //First card on player hand has been chosen as Ace
                    handCount++;
                }
                //Get 5 || remaining 4 cards
                for (int i = 13; i >= 2; i--) {
                    if (rankSF[i] == 1) {
                        values[handCount] = i;
                        handCount++;
                        //Hand is full then stop
                        if (handCount > 5) break;
                    }
                }
            }
        }

        /*
        StraightFlush fails then find other combinations
         */
        int ranks[];
        if (values[0] != 9) {
            /*
            Eliminate StraightFlush but Flush can still be there
             */

            //Count cards of each rank but this time withour suit condition
            ranks = new int[14];
            for (int i = 0; i < 14; i++) {
                ranks[i] = 0;
            }
            for (int i = 0; i < 7; i++) {
                ranks[cards[i].getRank()]++;
            }

            /*
            Check Straight
            Take 5 out of 7 to form a Straight which means that the remain 2 cards
            can be on the same rank with any of the chosen 5

            -> At least one card of each rank on a row to form a Straight

            Break Tie:
            Only need the leader (CArd with highest rank)
             */
            //The case with Ace
            if (ranks[1] >= 1 && ranks[13] >= 1 && ranks[12] >= 1 && ranks[11] >= 1 && ranks[10] >= 1) {
                values[0] = 5;
                //Set leader to Ace
                values[1] = 14;

            } else {
                for (int i = 13; i >= 5; i--) {
                    if (ranks[i] >= 1 && ranks[i - 1] >= 1 && ranks[i - 2] >= 1 && ranks[i - 3] >= 1 && ranks[i - 4] >= 1) {
                        values[0] = 5;
                        values[1] = i;
                        //Stop at the sight of Straight combo with trongest leader card
                        break;
                    }
                }
            }

            /*
            Find 2 ranks which have the most cards
            Quatity matters, rank does not matter
             */
            int bigGroup = 1, smallGroup = 1;
            int bigGroupRank = 0, smallGroupRank = 0;
            for (int i = 13; i >= 1; i--) {
                if (ranks[i] > bigGroup) {
                    if (bigGroup != 1) {
                        //Pass the old group to smallGroup
                        smallGroup = bigGroup;
                        smallGroupRank = bigGroupRank;
                    }
                    //Set to the rank with more cards
                    bigGroupRank = i;
                    bigGroup = ranks[i];

                } else if (ranks[i] > smallGroup) {
                    smallGroupRank = i;
                    smallGroup = ranks[i];
                }
            }

            /*
            Check four of a kind
             */
            if (bigGroup == 4) {
                values[0] = 8;
                //Rank of the 4
                if (bigGroupRank == 1) {
                    values[1] = 14;//Ace
                } else {
                    values[1] = bigGroupRank;
                }
                //The Final card with highest rank and not the same rank with four of a kind
                if (bigGroupRank != 1 && ranks[1] >= 1) {
                    values[2] = 14;
                } else {
                    for (int i = 13; i >= 2; i--) {
                        if (i != bigGroupRank && ranks[i] >= 1) {
                            values[2] = i;
                            break;
                        }
                    }
                }

                /*
                Check Full House

                Weird cases:
                bigGroup = 3, smallGroup = 3
                 8 8 6 6 5
                 Hole: 8 6

                 Hand: 8 8 8 6 6 (Full House)
                 */
            } else if (bigGroup == 3 && smallGroup >= 2) {
                values[0] = 7;
                /*
                Rank of triplet
                 */
                if (smallGroup == 3 && smallGroupRank == 1) {
                    /*
                    Special case
                    Big group: 3 rank 7, small group: 3 Ace => Should pick Ace for Triplet despite small group
                     */
                    values[1] = 14;
                    values[2] = bigGroupRank;

                } else {
                    //Normal cases
                    if (bigGroupRank == 1) {
                        values[1] = 14;
                    } else {
                        values[1] = bigGroupRank;
                    }
                    /*
                    Rank of pair
                    */
                    if (smallGroupRank == 1) {
                        values[2] = 14;
                    } else {
                        values[2] = smallGroupRank;
                    }
                }

                /*
                Check if Flush has been found above
                 */
            } else if (values[0] == 6) {
                //Do nothing

                /*
                Check if Straight has been found above
                 */
            } else if (values[0] == 5) {
                //Do Nothing

                /*
                Check 3 of a kind
                 */
            } else if (bigGroup == 3) {
                values[0] = 4;
                if (bigGroupRank == 1) {
                    values[1] = 14;//Triple Ace
                } else {
                    values[1] = bigGroupRank;
                }
                //Get 2 highest cards
                int index = 2;
                //Not Ace then get Ace as first High Card
                if (bigGroupRank != 1 && ranks[1] == 1) {
                    values[index] = 14;
                    index++;
                }
                //Normal cards
                for (int i = 13; i >= 2; i--) {
                    if (bigGroupRank != i && ranks[i] == 1) {
                        values[index] = i;
                        index++;
                    }
                    //Got 2 high cards then break
                    if (index > 3) {
                        break;
                    }
                }

                /*
                Check 2 pairs
                 */
            } else if (bigGroup == 2 && smallGroup == 2) {
                values[0] = 3;
                /*
                There is no big group or small group when we get 2 pairs
                but the first pair will always go to bigGroup and the second go to smallGroup

                But the end can be a pair of Ace which is the first strongest Pair
                 */
                if (smallGroupRank == 1) {
                    //Pair of Ace
                    values[1] = 14;
                    //Get the second pair
                    values[2] = bigGroupRank;//13 -> 2
                    //get the High card
                    for (int i = 13; i >= 2; i--) {
                        if (ranks[i] == 1) {
                            values[3] = i;
                            break;
                        }
                    }

                } else {
                    //No pair of Ace
                    values[1] = bigGroupRank;
                    values[2] = smallGroupRank;
                    //FInal card
                    if (ranks[1] == 1) {
                        //There is an Ace
                        values[3] = 14;
                    } else {
                        //No Ace - Find Normal
                        for (int i = 13; i >= 2; i--) {
                            if (ranks[i] == 1) {
                                values[3] = i;
                                break;
                            }
                        }
                    }
                }
                /*
                CHeck 1 pair
                 */
            } else if (bigGroup == 2) {
                values[0] = 2;
                //Rank of Pair
                if (bigGroupRank == 1) {
                    values[1] = 14;
                } else {
                    values[1] = bigGroupRank;
                }

                //Get 3 cards
                int index = 2;
                //There is an Ace???
                if(ranks[1] == 1){
                    values[index] = 14;
                    index++;
                }
                for (int i = 13; i >= 2; i--) {
                    if (ranks[i] == 1) {
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
                if (ranks[1] == 1) {
                    values[index] = 14;
                    index++;
                }
                //Remaining
                for (int i = 13; i >= 2; i--) {
                    if (ranks[i] == 1) {
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

    public void display(){
        for (int i = 0; i < 7; i++) {
            System.out.print(cards[i] + ", ");
        }
        System.out.println();

        switch(values[0]){
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

    public Card[] getCards() {
        return cards;
    }
    public int[] getValues(){ return values;}
}
