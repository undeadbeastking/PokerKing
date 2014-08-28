package Server;

import model.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 * Created by Agatha Wood Beyond on 8/16/2014.
 */
public class RoomHandler implements Runnable {

    //All users of a room
    private ArrayList<String> usernames;

    //Server connection
    private ArrayList<PlayerCommunicator> playersCom;
    private int numberOfPlayers;

    //Game
    private Deck deck;
    private LinkedList<Hand> hands = new LinkedList<Hand>();
    private ShowHand showHand;
    private int pot;

    public RoomHandler(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        playersCom = new ArrayList<PlayerCommunicator>();
        usernames = new ArrayList<String>();
        deck = new Deck();
    }

    public void addPlayer(PlayerCommunicator p, String username) {
        playersCom.add(p);
        usernames.add(username);
    }

    @Override
    public void run() {
        System.out.println("Wrong.");
        for (PlayerCommunicator p : playersCom) {
            //Send first hand info
            p.write(State.StartGame);
            Object fromClient = p.read();
            if (fromClient instanceof State) {
                State s = (State) fromClient;
                if (s == State.SendCard) {
                    sendCards(p);
                }
                s = (State) p.read();
                if (s == State.SendPlayers) {
                    p.write(usernames);
                }
            }
        }

        //Handling Bets and stuffs
        for (int i = 0; i < playersCom.size(); i++) {
            if (!allAreFold()) {
                if (usernames.get(i) != null) {
                    //send whos turn
                    sendTurn(i);
                    //receive response from that player
                    handleReponse(i);

                }
                if (i == (playersCom.size() - 1)) {
                    i = -1;
                }
            } else {
                break;
            }
        }

        //Test closing game
        for (PlayerCommunicator p : playersCom) {
            //Send first hand info
            p.write(State.EndGame);
        }

        //Close connection of every player in the room
        for (PlayerCommunicator p : playersCom) {
            p.close();
        }
    }

    public void sendCards(PlayerCommunicator p) {

        Hand hand = new Hand(deck);
        p.write(hand.getCards());
        hands.add(hand);
    }

    public boolean allAreFold() {
        int count = 0;
        boolean allAreFold = false;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i) == null) {
                count++;
            }
        }
        System.out.println("number of folded players" + count);
        if (count == playersCom.size() - 1) {
            allAreFold = true;
            for (int k = 0; k < playersCom.size(); k++) {
                playersCom.get(k).write("Stop");
            }
        }
        return allAreFold;
    }

    public void sendTurn(int i) {
        System.out.println("Current turn: " + usernames.get(i));
        for (int j = 0; j < usernames.size(); j++) {
            playersCom.get(j).write(usernames.get(i));

        }
    }

    public void handleReponse(int i) {
        Object fromClient = playersCom.get(i).read();

        if (fromClient != null) {
            //send that response to everyone
            for (int k = 0; k < playersCom.size(); k++) {
                playersCom.get(k).write(fromClient);
            }
        }
        //if a player fold, this username will be null
        if (fromClient.equals("Fold")) {
            usernames.set(i, null);
        }
    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }
}
