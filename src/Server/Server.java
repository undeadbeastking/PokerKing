package Server;


import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends JFrame {
   static Deck deck;

    public static void main(String[] args) throws IOException {

        deck = new Deck();
        // Note: Display the IP of the server.


        Server server = new Server();
        server.setVisible(true);
        server.setSize(600, 400);
        server.run();
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void run() throws IOException {


        // Initialize the UI
        final JTextArea area = new JTextArea();
        this.add(new JScrollPane(area));

        try {


            // Initialize the Server Socket
            // Server socket
            ServerSocket server = new ServerSocket(19999);


            // Keep Listening to the Client
            while (true) {


                for (int i = 0; i < 3; i++) {


                    // If there is a client connect to the Server at the port 19999
                    final Socket client = server.accept();
                    System.out.println("Client " + i + " connected");


                    // Create the THREAD FOR EACH PLAYER
                    new Thread(new Runnable() {

                        public void run() {

                            try {


                                DataInputStream fromClient = new DataInputStream(client.getInputStream());
                                DataOutputStream toClient = new DataOutputStream(client.getOutputStream());


                                // Initialized the round
//                                Round round = Round.Opening;

                                ArrayList<Integer> playerID = new ArrayList<Integer>();

                                ArrayList<String> communityCard = get5Cards();


                                // Send ID - !!! need to improve
                                toClient.writeInt(randInt());

                                // Send Role - !!! need to improve
                                toClient.writeInt(randInt());

                                // SEND 2 CARD TO EACH PLAYER CONNECTED
                                // SEND FIRST CARD

                                Hand hand = new Hand(deck);

                                // SEND FIRST CARD
                                Card [] card = hand.getCards();
                                String card_1 = card[0].toString();
                                String card_2 = card[1].toString();
//                                toClient.writeInt(randCards());
                                    toClient.writeUTF(card_1);
                                // SEND SECOND CARD
//                                toClient.writeInt(randCards());
                                toClient.writeUTF(card_2);


//                                // SEND 3 COMMUNITY CARDS
//                                toClient.writeUTF(communityCard.get(0));
////                                toClient.writeUTF(communityCard.get                         toClient.writeUTF(communityCard.get(2));
//
//
////
//                                // HANDLE BETTING
//
//                                // SEND THE TURN CARD


//                                toClient.writeUTF(communityCard.get(3));
//
//                                // HANDLE BETTING
//
//                                // SEND THE FLOP
//                                toClient.writeUTF(communityCard.get(4));

                                // HANDLE BETTING


//                                switch (round) {
//
//                                    case Opening:
//
//                                        // Send ID
//                                        toClient.writeInt(randInt());
//
//                                        // Send Role - !!! need to improve
//                                        toClient.writeInt(randInt());
//
//                                        // SEND 2 CARD TO EACH PLAYER CONNECTED
//                                        // SEND FIRST CARD
//                                        toClient.writeInt(randCards());
//
//                                        // SEND SECOND CARD
//                                        toClient.writeInt(randCards());
//
//
//                                        // HANDLE BETTING
//
//                                    case Flop:
//
//                                        // SEND 3 COMMUNITY CARDS
//                                        toClient.writeUTF(communityCard.get(0));
//                                        toClient.writeUTF(communityCard.get(1));
//                                        toClient.writeUTF(communityCard.get(2));
//
//                                        // HANDLE BETTING
//
//                                    case Turn:
//
//                                        // SEND THE TURN CARD
//                                        toClient.writeUTF(communityCard.get(3));
//
//                                        // HANDLE BETTING
//
//                                    case River:
//
//                                        // SEND THE FLOP
//                                        toClient.writeUTF(communityCard.get(4));
//
//                                        // HANDLE BETTING
//
//                                }

                                // IN ORDER TO SEND COMMUNITY CARD
                                // WE NEED ARRAYLIST

                                // KAKA THAT IS VERY FUNNY
                                // IF YOU WANT TO
                                // SEND TO EVERYBODY MAKE IT STATIC
                                // SEND TO SPECIFIC PLAYER MAKE IN RANDOM

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }).start();

                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int randInt() {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((4 - 1) + 1) + 1;

        return randomNum;
    }

    public int randCards() {

        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((14 - 1) + 1) + 1;

        return randomNum;
    }


    public ArrayList<String> get5Cards() {
        ArrayList<String> communityCard = new ArrayList<String>();

        for (int i = 0; i < 5; i++) {

            communityCard.add("Ace spade");
        }

        return communityCard;

    }

    public void sendRole() {
    }
}