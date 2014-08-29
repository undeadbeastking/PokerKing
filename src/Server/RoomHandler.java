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
    private int pot;

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
        for (int i = 0; i < playerComs.size(); i++) {
            if (!everyoneFold()) {
                if (allUsernames.get(i) != null) {
                    //send whos turn
                    sendTurn(i);
                    //receive response from that player socket
                    handleReponse(i);

                }
                if (i == (playerComs.size() - 1)) {
                    i = -1;
                }
            } else {
                break;
            }
        }
    }

    public boolean everyoneFold() {
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
            for (int i = 0; i < playerComs.size(); i++) {
                playerComs.get(i).write("Stop");
            }
        }
        return result;
    }

    public void sendTurn(int i) {
        System.out.println("Current turn: " + allUsernames.get(i));
        for (int j = 0; j < allUsernames.size(); j++) {
            playerComs.get(j).write(allUsernames.get(i));
        }
    }

    public void handleReponse(int i) {
        Object fromClient = playerComs.get(i).read();

        if (fromClient != null) {
            //send that response to everyone
            for (int k = 0; k < playerComs.size(); k++) {
                playerComs.get(k).write(fromClient);
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
