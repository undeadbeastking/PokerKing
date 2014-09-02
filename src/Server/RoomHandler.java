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
    private ArrayList<Integer> allPlayerMoney;

    public RoomHandler(int numberOfPlayersInARoom) {
        this.numberOfPlayersPerRoom = numberOfPlayersInARoom;
        playerComs = new ArrayList<PlayerCommunicator>();
        allUsernames = new ArrayList<String>();
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

        allPlayerMoney = new ArrayList<Integer>();
        int testMoney = 600;
        for (int i = 0; i < numberOfPlayersPerRoom; i++) {

            //Subtract the money of small blind and big blind hosts first
            if(i == numberOfPlayersPerRoom-2){
                testMoney-=50;
            } else if(i == numberOfPlayersPerRoom-1){
                testMoney-=100;
            }

            allPlayerMoney.add(testMoney);
            testMoney-=100;
        }

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
                    playerCom.write(allPlayerMoney);
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

            int dealerPosition = 0;
            int playerIndex = dealerPosition;

            //There is still a raise then keep looping around the table
            int endOfARaise = 0;

            //There is still another player who tries to raise
            while (endOfARaise != (playerComs.size()-1)) {

                //Restart index to The first Player
                if (playerIndex == playerComs.size()) {
                    playerIndex = 0;
                }

                //If a username is not null then his turn is sent and the server will listen to the response of his socket
                if (allUsernames.get(playerIndex) != null) {
                    //send whos turn
                    System.out.println("Send a player's turn.");
                    sendTurn(playerIndex);

                    //receive response from that player socket
                    System.out.println("Waiting for response from that player");
                    Object responseFromAClient = playerComs.get(playerIndex).read();
                    String responseToOthers = "Cannot detect";

                    if (responseFromAClient instanceof Integer) {
                        System.out.println(responseFromAClient);
                        int moneyFromPlayer = (Integer) responseFromAClient;

                        if (moneyFromPlayer > currentRaise) {
                            currentRaise = moneyFromPlayer;
                            endOfARaise = 0;
                            responseToOthers = "Raise $" + moneyFromPlayer;

                        } else if (moneyFromPlayer == currentRaise) {
                            endOfARaise++;
                            responseToOthers = "Call $" + moneyFromPlayer;
                            System.out.println("Number of players who match Raise: " + endOfARaise);

                        } else if (moneyFromPlayer == -1) {
                            endOfARaise++;
                            responseToOthers = "Fold $";

                        }
                        //send that response to everyone
                        for (int y = 0; y < playerComs.size(); y++) {
                            System.out.println("Writing " + responseToOthers + " to everyone.");
                            playerComs.get(y).write(responseToOthers);
                        }
                    }
                    //if a player fold, this username will be null
                    if (responseToOthers.startsWith("Fold")) {
                        allUsernames.set(playerIndex, null);
                    }
                }

                playerIndex++;

                if (endOfARaise == playerComs.size()) {
                    break;
                }
            }

            //The final turn has been processed, send end turn to every player
            for (int x = 0; x < playerComs.size(); x++) {
                playerComs.get(x).write(BetState.EndState);
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

    public boolean onlyOneDoesNotFold() {
        int count = 0;
        boolean result = false;
        for (int i = 0; i < allUsernames.size(); i++) {
            if (allUsernames.get(i) == null) {
                count++;
            }
        }
        System.out.println("Number of folded players" + count);
        if (count == playerComs.size() - 1) {
            result = true;
            //Send Stop signal to clients, tell them that a winner has been found
            for (int i = 0; i < playerComs.size(); i++) {
                playerComs.get(i).write("Stop");
            }
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
