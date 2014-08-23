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

        //Handling Bets and stuffs
        for (int i = 0; i < playersCom.size(); i++) {
            playersCom.get(i).write(State.CurrentTurn);
            for (int j = 0; j < playersCom.size(); j++) {
                if (j != i) {
                    playersCom.get(j).write(State.NotYourTurn);
                    playersCom.get(j).write(i);
                }
            }
            Object fromClient = playersCom.get(i).read();
            for (int j = 0; j < playersCom.size(); j++) {
                if (j != i) {
                    playersCom.get(j).write(fromClient);
                }
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

}
