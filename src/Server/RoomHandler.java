package Server;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Agatha Wood Beyond on 8/16/2014.
 */
public class RoomHandler implements Runnable {

    //All username of a room
    private int numberOfPlayersPerRoom;
    private ArrayList<String> allUsernames;

    //Communication of each player in a Room
    private ArrayList<PlayerCommunicator> playerComs;

    //Game
    //Cards
    private Deck deck;
    private LinkedList<Hand> hands = new LinkedList<Hand>();
    private ShowHand showHand;

    //Pot and Bets
    private int pot = 150;
    private int currentRaise = 100;
    private ArrayList<Integer> allPlayerCash;
    private int[] allBets;

    //Check if all the players have All In status
    private boolean[] isAllIn;

    public RoomHandler(int numberOfPlayersInARoom) {
        this.numberOfPlayersPerRoom = numberOfPlayersInARoom;
        playerComs = new ArrayList<PlayerCommunicator>();
        allUsernames = new ArrayList<String>();
        isAllIn = new boolean[numberOfPlayersPerRoom];
        deck = new Deck();
    }

    public void addPlayer(PlayerCommunicator p, String username) {
        playerComs.add(p);
        allUsernames.add(username);
    }

    @Override
    public void run() {
        //If possible, a game ends then start another one with these same players
        startAGame();

        //Close connection of every player in the room
        disconnect();
    }

    public void startAGame(){
        initNewGame();

        //Handling Bets and Logic
        processingBets();

        //Test closing game
        endGame();
    }

    public void initNewGame() {

        allPlayerCash = new ArrayList<Integer>();
        int testMoney = 600;
        for (int i = 0; i < numberOfPlayersPerRoom; i++) {

            //Subtract the money of small blind and big blind hosts first
            if(i == numberOfPlayersPerRoom-2){
                allPlayerCash.add(testMoney - 50);
            } else if(i == numberOfPlayersPerRoom-1){
                allPlayerCash.add(testMoney - 100);
            } else {
                allPlayerCash.add(testMoney);
            }
            testMoney-=100;
        }

        //Init each player Bet
        allBets = new int[numberOfPlayersPerRoom];
        allBets[numberOfPlayersPerRoom-2] = 50;//Small Blind
        allBets[numberOfPlayersPerRoom-1] = 100;//Big Blind

        //Send Client's Hand and identities of all players, money
        for (PlayerCommunicator playerCom : playerComs) {
            playerCom.write(State.StartGame);

            Object fromClient = playerCom.read();
            if (fromClient instanceof State) {

                State s = (State) fromClient;
                if (s == State.SendCard) {
                    sendCards(playerCom);
                }

                s = (State) playerCom.read();
                if (s == State.SendPlayers) {
                    playerCom.write(allUsernames);
                }

                s = (State) playerCom.read();
                if(s == State.SendMoney){
                    playerCom.write(allPlayerCash);
                }
            }
        }
    }

    public void sendCards(PlayerCommunicator p) {
        Hand hand = new Hand(deck);
        //Send hand
        p.write(hand.getCards());
        //username and hand have similar index
        hands.add(hand);
    }

    public void processingBets() {

        //Loop through 4 Betting States
        for (int j = 0; j < 4; j++) {

            //if raiseLoop == (number of players - 1) -> everyone chooses Call || All IN || Fold
            //There is a Raise then raiseLoop starts again
            int raiseLoop = 0;
            int playerIndex = 0;

            //Notify all the players about each Bet State
            for (PlayerCommunicator com : playerComs) {
                switch (j) {
                    case 0:
                        com.write(BetState.FirstBet);
                        break;
                    case 1:
                        com.write(BetState.SecondBet);
                        break;
                    case 2:
                        com.write(BetState.ThirdBet);
                        break;
                    case 3:
                        com.write(BetState.FourBet);
                        break;

                    default:
                        System.out.println("Unknown Bet State.");
                }
            }

            //Skip betting
            boolean skip = check_allFold_or_allAllIn();
            if(skip) {
                //Send Stop signal to clients, tell them that a winner has been found
                for (int i = 0; i < playerComs.size(); i++) {
                    playerComs.get(i).write("Skip");
                }
            }

            //There is still another player who tries to raise, SKip if all Fold or all All IN
            while (raiseLoop != (playerComs.size()) && !skip) {

                if(check_allFold_or_allAllIn()) break;

                if(allUsernames.get(playerIndex) == null && isAllIn[playerIndex]){
                    raiseLoop++;
                }

                //If a username is not null then his turn is sent and the server will listen to the response of his socket
                if (allUsernames.get(playerIndex) != null && !isAllIn[playerIndex]) {
                    //send whos turn
                    System.out.println("Send a player's turn.");
                    sendTurn(playerIndex);

                    //receive response from that player socket
                    System.out.println("Waiting for response from that player");
                    Object responseFromAClient = playerComs.get(playerIndex).read();
                    String responseToOthers = "Cannot detect";

                    if (responseFromAClient instanceof Integer) {
                        System.out.println(responseFromAClient);
                        int moneyToAdd_FromPlayer = (Integer) responseFromAClient;

                        if (moneyToAdd_FromPlayer == -1) {
                            raiseLoop++;
                            responseToOthers = "Fold $" + allBets[playerIndex] + "/" + pot + "/" + allPlayerCash.get(playerIndex);

                            //Fold and All in - Special Pass
                            if (responseToOthers.startsWith("Fold")) {
                                allUsernames.set(playerIndex, null);
                            }

                        //Check
                        } else if (moneyToAdd_FromPlayer == 0) {
                            raiseLoop++;
                            responseToOthers = "Check: $" + allBets[playerIndex] + "/" + pot + "/" + allPlayerCash.get(playerIndex);

                        //All in
                        } else if( ((moneyToAdd_FromPlayer + allBets[playerIndex]) < currentRaise) ||
                                //Player has nothing left after subtracting moneyToAdd
                                ((allPlayerCash.get(playerIndex) - moneyToAdd_FromPlayer) == 0)){

                            isAllIn[playerIndex] = true;
                            raiseLoop++;

                            //Increase pot
                            pot += moneyToAdd_FromPlayer;

                            //But decrease in that player money
                            allPlayerCash.set(playerIndex, allPlayerCash.get(playerIndex) - moneyToAdd_FromPlayer);

                            //Update the current Bet of that player
                            allBets[playerIndex] = moneyToAdd_FromPlayer + allBets[playerIndex];

                            responseToOthers = "All in: $" + allBets[playerIndex] + "/" + pot + "/" + allPlayerCash.get(playerIndex);

                            //if All in is bigger than currentRaise -> set it to currentRaise
                            if(allBets[playerIndex] > currentRaise){
                                currentRaise = allBets[playerIndex];
                                responseToOthers = "Big All In: $" + allBets[playerIndex] + "/" + pot + "/" + allPlayerCash.get(playerIndex);
                                raiseLoop = 0;//Bigger than current Raise then count as Raise
                            }

                        //Raise
                        } else if ((moneyToAdd_FromPlayer + allBets[playerIndex]) > currentRaise) {
                            raiseLoop = 0;

                            //Update the new Raise
                            currentRaise = moneyToAdd_FromPlayer + allBets[playerIndex];

                            //Increase pot
                            pot += moneyToAdd_FromPlayer;

                            //But decrease in that player money
                            allPlayerCash.set(playerIndex, allPlayerCash.get(playerIndex) - moneyToAdd_FromPlayer);

                            //Update the current Bet of that player
                            allBets[playerIndex] = currentRaise;

                            responseToOthers = "Raise: $" + currentRaise + "/" + pot + "/" + allPlayerCash.get(playerIndex);

                        //Call
                        } else if ((moneyToAdd_FromPlayer + allBets[playerIndex]) == currentRaise) {
                            raiseLoop++;

                            //Increase pot
                            pot += moneyToAdd_FromPlayer;

                            //But decrease in that player money
                            allPlayerCash.set(playerIndex, allPlayerCash.get(playerIndex) - moneyToAdd_FromPlayer);

                            //Update the current Bet of that player
                            allBets[playerIndex] = currentRaise;

                            responseToOthers = "Call: $" + currentRaise + "/" + pot + "/" + allPlayerCash.get(playerIndex);

                        }

                        //send that response to everyone
                        for (int y = 0; y < playerComs.size(); y++) {
                            System.out.println("Writing " + responseToOthers + " to everyone.");
                            playerComs.get(y).write(responseToOthers);
                        }
                    }
                }

                playerIndex++;
                //Restart index to The first Player
                if (playerIndex == playerComs.size()) {
                    playerIndex = 0;
                }
            }

            if(!skip){
                //The final turn has been processed, send end turn to every player
                for (int x = 0; x < playerComs.size(); x++) {
                    playerComs.get(x).write(BetState.EndState);
                }
            }

        }

        //ShowDown
        for (PlayerCommunicator com : playerComs) {
            com.write(BetState.ShowDown);
        }
    }

    public void sendTurn(int currentTurnIndex) {
        System.out.println("Current turn: " + allUsernames.get(currentTurnIndex));
        //All player will receive the username of the current player's turn
        for (int j = 0; j < allUsernames.size(); j++) {
            playerComs.get(j).write(allUsernames.get(currentTurnIndex));
        }
    }

    public boolean check_allFold_or_allAllIn() {
        int count = 0;
        boolean result = false;
        for (int i = 0; i < allUsernames.size(); i++) {
            if (allUsernames.get(i) == null) {
                count++;
            }
        }
        System.out.println("Number of folded players" + count);

        int count1 = 0;
        for (int i = 0; i < isAllIn.length; i++) {
            if(isAllIn[i])    count1++;
        }

        if (count == playerComs.size() - 1 || count1 == playerComs.size()) {
            result = true;
        }

        //Fold and All at the same time
        if(count + count1 == playerComs.size()){
            result = true;
        }

        return result;
    }

    public void endGame() {
        for (PlayerCommunicator p : playerComs) {
            //Send first hand info
            p.write(State.EndGame);
        }
    }

    public void disconnect() {
        for (PlayerCommunicator p : playerComs) {
            p.close();
        }
    }

    public ArrayList<String> getAllUsernames() {
        return allUsernames;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }
}
