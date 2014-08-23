package Server;

import model.Account;
import model.Data;

import java.io.*;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Server implements Runnable {

    private static final int PORT = 9000;

    //Set of all usernames
    private static ArrayList<String> usernames = new ArrayList<String>();
    public static int numberOfPlayers = 4;

    public static void main(String[] args) throws Exception {
        //Load users data
        Data.loadAccounts();

//        System.out.println("Server is running.");
        Server s = new Server();
        new Thread(s).start();
    }

    public Server() {
        //print out server ip
        try {
            System.out.println("Server is running. The IP is: \n"
                    + Inet4Address.getLocalHost().getHostAddress());
        } catch (Exception e) {
            System.out.println("Can not find server ip!");
        }

        //Server UI here
    }

    @Override
    public void run() {
        ServerSocket server = null;

        try {
            server = new ServerSocket(PORT);

            while (true) {
                GameHandler g = new GameHandler(numberOfPlayers);
                for (int j = 0; j < numberOfPlayers; j++) {

                    Socket socket = server.accept();
                    PlayerCommunicator player = new PlayerCommunicator(
                            socket,
                            new ObjectInputStream(socket.getInputStream()),
                            new ObjectOutputStream(socket.getOutputStream())
                    );

                    System.out.println("Player " + (j + 1) + " is trying to log in.");
                    //Waiting for account obj and send back validation result
                    while (true) {
                        System.out.println("Waiting for account info...");
                        Object o = player.read();
                        boolean rightAccount = false;
                        String acceptUsername = "";

                        if (o instanceof Account) {
                            Account acc = (Account) o;
                            String username = acc.getUsername();
                            String pass = acc.getPassword();
                            System.out.println(username + " login successfully");

                            //Right username and password?
                            for (int i = 0; i < Data.getAccounts().size(); i++) {
                                String dataUsername = Data.getAccounts().get(i).getUsername();
                                String dataPassword = String.valueOf(Data.getAccounts().get(i).getPassword());

                                if (username.equals(dataUsername) && pass.equals(dataPassword)) {
                                    rightAccount = true;
                                    acceptUsername = username;
                                    break;
                                }
                            }
                            //Account is currently in used
                            if (usernames.contains(username)) {
                                rightAccount = false;
                                System.out.println("Someone is using this account.");
                            }

                        }

                        if (rightAccount) {
                            g.addPlayer(player);
                            usernames.add(acceptUsername);
                            player.write(State.Waiting);
                            player.write("cards from server");
                            break;

                        } else {
                            player.write(State.WrongAccount);
                            System.out.println("Wrong account.");
                        }
                    }
                }
                System.out.println("Enough players, begin a game.");
                //Enough players then we start a thread handling for that room
                System.out.println("~~~~~~~~~~~~~~~//////~~~~~~~~~~~~~~");


                new Thread(g).start();
            }

        } catch (Exception ex) {
            System.out.println("A player has disconnected.");
        } finally {
            try {
                server.close();
                System.out.println("Stop server.");

            } catch (IOException i) {
                System.out.println("Cannot close server socket.");
            }

            //Save accounts
            Data.saveAccounts();
        }
    }

    public static ArrayList<String> getUsernames() {
        return usernames;
    }
}