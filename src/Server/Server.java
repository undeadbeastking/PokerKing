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
    public static Deck deck;

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

                                Hand hand = deck.newHand();

                                Card[] card = hand.getCards();
                                String card_1 = card[0].toString();
                                String card_2 = card[1].toString();

                                toClient.writeUTF(card_1);
                                toClient.writeUTF(card_2);

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