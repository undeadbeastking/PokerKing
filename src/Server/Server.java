package Server;

import model.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Server {

    public static Deck deck;
    private static final int PORT = 9001;

    //Set of all usernames
    private static ArrayList<String> usernames = new ArrayList<String>();
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    public static int numberOfPlayers = 0;

    /**
     * The appplication main method, which just listens on a port and
     * spawns handler threads.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);
        //Load users data
        Data.loadAccounts();
        deck = new Deck();

        try {
            while (true) {
                new Handler(listener.accept()).start();
            }

        } finally {
            listener.close();
            //Save accounts
            Data.saveAccounts();
        }
    }

    /**
     * A handler thread class.  Handlers are spawned from the listening
     * loop and are responsible for a dealing with a single client
     * and broadcasting its messages.
     */
    private static class Handler extends Thread {

        private String account;
        private String username, pass;
        //Server connection
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {

            try {
                // Create character streams for the socket.
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    out.println("SubmitAccount");
                    account = in.readLine();

                    synchronized (usernames) {
                        boolean byPass = false;
                        //Splitting
                        StringTokenizer tokenizer = new StringTokenizer(account, ",");
                        username = tokenizer.nextToken();
                        pass = tokenizer.nextToken();
                        //Validate username and password
                        for (int i = 0; i < Data.getAccounts().size(); i++) {
                            String dataUsername = Data.getAccounts().get(i).getUsername();
                            String dataPassword = String.valueOf(Data.getAccounts().get(i).getPassword());

                            if (username.equals(dataUsername) && pass.equals(dataPassword)) {
                                byPass = true;
                                break;
                            }
                        }
                        if(usernames.contains(username)){
                            byPass = false;
                            System.out.println("SOmeone is using this account.");
                        }
                        if (byPass) {
                            usernames.add(username);
                            writers.add(out);//Remember socket address
                            System.out.println("A new user just signed in: " + username);
                            numberOfPlayers++;
                            break;

                        } else {
                            out.println("FailLogin");
                            out.println("SubmitAccount");
                        }
                    }
                }

                out.println("Accepted");

                //Enough 3 players then init GamePanel for all
                if(numberOfPlayers == 3){
                    //Send usernames
                    for (PrintWriter writer: writers) {
                        String users = "";
                        for (int i = 0; i < usernames.size(); i++) {
                            if(i < usernames.size() - 1){
                                users = users + usernames.get(i) + ",";
                            } else {
                                users = users + usernames.get(i);
                                users = users + ",end";
                            }
                        }
                        System.out.println(users);
                        writer.println("AllUsers " + users);
                    }

                    //Send cards
                    for (PrintWriter writer: writers) {
                        Card[] cards = deck.newHand().getCards();
                        String beSent = cards[0].toString() + "-" + cards[1].toString();
                        writer.println("Cards " + beSent);
                    }

                    for (PrintWriter writer : writers) {
                        writer.println("Playing");
                    }

                }


                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + account + ": " + input);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);

            } finally {
                if (username != null) {
                    //remove a username if logged out
                    usernames.remove(username);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    //Remove socket
                    socket.close();

                } catch (IOException e) {
                }
            }
        }
    }
}