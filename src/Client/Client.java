package Client;


import view.GamePanel;
import view.MainFrame;
import view.PlayerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client extends JFrame {
    static MainFrame ms;

    public static void main(String[] args) {


        // Login

        // Game Panel

        ms = new MainFrame();
        new Client();
//         this = new Client();
//        frame.setVisible(true);
//        frame.setSize(600, 400);
//        frame.setLocation(650, 0);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public Client() {
        try {

            final Socket socket = new Socket("localhost", 19999);
            System.out.println("One Player connected");

            final DataInputStream fromServer = new DataInputStream(socket.getInputStream());
            final DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());


            ms.initGamePanel(ms.getLoginPanel().getUsernameF().getText());

            // Read first 2 cards from Server
            String firstCard = fromServer.readUTF();
            String secondCard = fromServer.readUTF();



            PlayerPanel[] players = ms.getGamePanel().getPlayersP();
            players[0].setCards(firstCard, secondCard);

//            // receive 3 Community Cards
//            String firstCC = fromServer.readUTF();
//            String secondCC = fromServer.readUTF();
//            String thirdCC = fromServer.readUTF();
//
//            String fourthCC = fromServer.readUTF();
//            String fifthCC = fromServer.readUTF();
//
//            String combinedMessage =
//                    "Your Role: " + urRole + "\n"
//                            + "Your firstCard: " + firstCard + "\n"
//                            + "Your secondCard: " + secondCard + "\n"
//                            + "1: " + firstCC + "\n"
//                            + "2: " + secondCC + "\n"
//                            + "3: " + thirdCC + "\n"
//                            + "4: " + fourthCC + "\n"
//                            + "5: " + fifthCC + "\n";
//
//
//            area.append(combinedMessage);


            // These 2 buttons will be Call, Check, Raise or Fold
            // I'm working on the Betting,
            // so there is nothing to do with these button at the moment.
//            btnSubmit.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
////                    try {
////                    } catch (IOException ex) {
////                        ex.printStackTrace();
////                    }
//                }
//            });
//
//            btnClose.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
////                    try {
////                        dos.writeUTF("Quit");
////                    } catch (IOException ex) {
////                        ex.printStackTrace();
////                    }
//                }
//            });

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String findYourRole(int role) {

        String urRole;

        if (role == 0) {

            return urRole = "Dealer";
        } else if (role == 1) {
            return urRole = "Small Bind";
        } else if (role == 2) {
            return urRole = "Big Binde";
        } else {
            return urRole = "Normal";
        }
    }
}
