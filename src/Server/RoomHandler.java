package Server;

import java.util.*;

/**
 * Created by Agatha Wood Beyond on 8/16/2014.
 */
public class RoomHandler implements Runnable {

    //All username of a room
    private int numberOfPlayersInARoom;
    private ArrayList<String> allUsernames;

    //Communication of each player in a Room
    private ArrayList<PlayerCommunicator> playerComs;

    //Game
    private Deck deck;
    private LinkedList<Hand> hands = new LinkedList<Hand>();
    private ShowHand showHand;
    private int pot = 150;

    public RoomHandler(int numberOfPlayersInARoom) {
        this.numberOfPlayersInARoom = numberOfPlayersInARoom;
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
        initNewGame();

        //Handling Bets and stuffs
        processingBets();

        //Test closing game
        endGame();

        //Close connection of every player in the room
        disconnect();
    }

    public void initNewGame(){
        for (PlayerCommunicator playerCom : playerComs) {
            //Send Client's Hand and identities of all players
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
            }
        }
    }

    public void sendCards(PlayerCommunicator p) {
        Hand hand = new Hand(deck);
        p.write(hand.getCards());
        //Each username will have a similar index with his or her Hand
        hands.add(hand);
    }

    public void processingBets(){
        //Loop 4 Betting States
        for (int j = 0; j < 4; j++) {
            //Notify all the players about bet states
            for (PlayerCommunicator com : playerComs){
                switch(j){
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

            //Bet starts with first one posts small blind, second posts big blind, third one will have the official turn
            int i = 0;
            if(j == 0){
                i = 2;
            }

            //Need a while here and will loop if there is still a RAISE
            for (; i < playerComs.size(); i++) {
                //if there is only one player left in the room who does not fold then he is the Winner
                if (!onlyOneDoesNotFold()) {
                    //If a username is not null then his turn is sent and the server will listen to the response of his socket
                    if (allUsernames.get(i) != null) {
                        //send whos turn
                        sendTurn(i);
                        //receive response from that player socket
                        handleReponse(i);
                    }
                } else {
                    break;

                }

                //The final turn has been processed, send end turn to every player
                if(i == playerComs.size()-1){
                    for (int x = 0; x < playerComs.size(); x++) {
                        playerComs.get(x).write(BetState.EndState);
                    }
                }
            }
        }

        //ShowDown
        for (PlayerCommunicator com : playerComs){
            com.write(BetState.ShowDown);
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

    public void sendTurn(int i) {
        System.out.println("Current turn: " + allUsernames.get(i));
        //All player will receive the username of the current player's turn
        for (int j = 0; j < allUsernames.size(); j++) {
            playerComs.get(j).write(allUsernames.get(i));
        }
    }

    public void handleReponse(int i) {
        Object fromClient = playerComs.get(i).read();

        if (fromClient != null) {
            //send that response to everyone
            for (int j = 0; j < playerComs.size(); j++) {
                playerComs.get(j).write(fromClient);
            }
        }
        //if a player fold, this username will be null
        if (fromClient.equals("Fold")) {
            allUsernames.set(i, null);
        }
    }

    public void endGame(){
        for (PlayerCommunicator p : playerComs) {
            //Send first hand info
            p.write(State.EndGame);
        }
    }

    public void disconnect(){
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
