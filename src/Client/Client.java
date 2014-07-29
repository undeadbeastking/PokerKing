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

    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private String[] cardNames = new String[2];

    public Client() {
        try {
            final Socket socket = new Socket("localhost", 19999);
            System.out.println("One Player connected");

            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());

            // Read first 2 cards from Server
            String firstCard = fromServer.readUTF();
            String secondCard = fromServer.readUTF();

            cardNames[0] = firstCard;
            cardNames[1] = secondCard;
            System.out.println(firstCard + " " + secondCard);

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String[] getHoleCards() {
        return cardNames;
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
