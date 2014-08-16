package Server;

import model.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

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

    public GameHandler() {
        this.usernames = Server.getUsernames();
    }

    public void addPlayer(PlayerCommunicator p){
        playersCom.add(p);
    }

    @Override
    public void run() {
        for(PlayerCommunicator p : playersCom) {
            //Send first hand info
            p.write(State.StartGame);
        }

        //Handling Bets and stuffs
    }
}
