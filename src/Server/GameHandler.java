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
public class GameHandler implements Runnable {

    //accounts
    private ArrayList<String> usernames;
    private String account;
    private String username, pass;
    //Server connection
    private ArrayList<PlayerCommunicator> playersCom;
    private BufferedReader in;
    private PrintWriter out;
    private int numberOfPlayers, pot;
    private ShowHand showHand;
    private Deck deck;
    private LinkedList<Hand> hands = new LinkedList<Hand>();


    public GameHandler(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.usernames = Server.getUsernames();
        playersCom = new ArrayList<PlayerCommunicator>();
        deck = new Deck();

    }

    public void addPlayer(PlayerCommunicator p) {
        playersCom.add(p);
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    @Override
    public void run() {
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

        //Send role
//        int role = 0;
//        while(role < playersCom.size() ){
//            playersCom.get(role).write("D");
//            playersCom.get(role+1).write("SB");
//            playersCom.get(role+2).write("BB");
//            for (int j = role+3; j < playersCom.size(); j++) {
//                playersCom.get(j).write("Com");
//            }
//        }

        //Handling Bets and stuffs
        for (int i = 0; i < playersCom.size(); i++) {
            if (usernames.get(i) != null) {
                //send whos turn
                sendTurn(i);
                //receive response from that player
                handleReponse(i);

            }
            if (i == 3) {
                i = -1;
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
        if (count == usernames.size() - 1) {
            allAreFold = true;
        }

        return allAreFold;
    }

    public void sendTurn(int i){
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

}
