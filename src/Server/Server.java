package Server;

import model.Account;
import model.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends JFrame implements Runnable {

    private static final int PORT = 9000;
    private static Thread serverThread = null;
    private static ServerSocket server = null;
    private int roomNumber = 1;//Count how many rooms the server is controlling
    public static int numberOfPlayersInARoom = 3;
    private static ArrayList<String> inUsedUsernames = new ArrayList<String>();
    private static String IP;
    static AutoObtainIP autoObtainIP = new AutoObtainIP();

    public static void main(String[] args) {
        //Load users data
        Data.loadAccounts();

        if (initServerSocket()) {
            serverThread = new Thread(new Server());
            serverThread.start();

        } else {
            System.out.println("Fail to establish a server Connection.\nExit Program.");
        }
    }

    public static boolean initServerSocket() {
        try {
            server = new ServerSocket(PORT);

            //Print out server ip
            try {
                System.out.println("Server is running. The IP is: \n" + Inet4Address.getLocalHost().getHostAddress() + "\n\n");
                IP = Inet4Address.getLocalHost().getHostAddress();
                autoObtainIP.delete(autoObtainIP.obtainIP());
                autoObtainIP.create(IP);

            } catch (Exception e) {
                System.out.println("Can not find server ip!");
            }

        } catch (IOException e) {
            System.out.println("Cannot init ServerSocket.");
            return false;
        }
        return true;
    }

    public Server() {
        //Server UI
        setTitle("Server");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Saving accounts before exiting the program
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //Save accounts
                System.out.println("\n\nTerminate the program.");
                System.out.println("Saving accounts successfully.");
                Data.saveAccounts();
                try {
                    autoObtainIP.delete(autoObtainIP.obtainIP());
                } catch (Exception e1) {
                    System.out.println("Cannot delete server.");
                }
            }
        });

        //JLabel info
        JLabel serverState = new JLabel("<html>Port: " + PORT +
                "<br>Server IP: " + IP +
                "<br>Number of players allowed in a Room: " + numberOfPlayersInARoom +
                "<br>Server is running." + "</html>", SwingConstants.CENTER);
        serverState.setOpaque(true);
        serverState.setBackground(Color.YELLOW);
        add(serverState);

        //Repack the JLabel, reSet the Frame resolution and Position
        pack();
        setSize(270, 130);
        setLocationRelativeTo(null);//Set center
        setVisible(true);
    }

    @Override
    public synchronized void run() {

        while (true) {
            RoomHandler roomHandler = new RoomHandler(numberOfPlayersInARoom);
            for (int j = 0; j < numberOfPlayersInARoom; j++) {

                Socket socket = null;
                try {
                    socket = server.accept();
                    System.out.println("Player " + (j + 1) + " of Room " + roomNumber + " established the connection successfully.");
                    PlayerCommunicator playerCom = new PlayerCommunicator(
                            socket,
                            new ObjectInputStream(socket.getInputStream()),
                            new ObjectOutputStream(socket.getOutputStream())
                    );
                    /*
                    Avoid listening to one Player and fail to process messages from others
                    Throw out a thread Validating for that player, Move on receive connect info from the others
                     */
                    new Thread(new WaitingForValidation(roomHandler, playerCom, (j + 1), roomNumber)).start();

                } catch (IOException e) {
                    System.out.println("Fail to connect to client socket.");
                    break;
                }
            }
            System.out.println("\n\nEnough players, Create another Room.");
            roomNumber++;
            System.out.println("~~~~~~~~~~~~~~~//////~~~~~~~~~~~~~~\n\n");
        }
    }

    public class WaitingForValidation implements Runnable {

        private RoomHandler roomHandler;
        private PlayerCommunicator playerCom;
        private int playerNum;
        private int roomNum;

        public WaitingForValidation(RoomHandler r, PlayerCommunicator p, int playerNum, int roomNum) {
            this.roomHandler = r;
            this.playerCom = p;
            this.playerNum = playerNum;
            this.roomNum = roomNum;
        }

        @Override
        public void run() {
            //Waiting for account obj and send back validation result
            while (true) {
                System.out.println("Waiting for login info... from " + "Player " + playerNum + " of Room " + roomNum);
                Object obj;
                try {
                    obj = playerCom.read();

                } catch (RuntimeException e) {
                    /*
                    A player fails to login and disconnect from the game -> this roomNumber will listen
                    to a new player connection instead
                     */
                    System.out.println("Player " + playerNum + " in Room " + roomNum + " has disconnected.");
                    Socket socket = null;
                    try {
                        socket = server.accept();
                        System.out.println("Player " + playerNum + " of Room " + roomNum + " established the connection successfully.");
                        PlayerCommunicator player = new PlayerCommunicator(
                                socket,
                                new ObjectInputStream(socket.getInputStream()),
                                new ObjectOutputStream(socket.getOutputStream())
                        );
                        new Thread(new WaitingForValidation(roomHandler, player, playerNum, roomNum)).start();

                    } catch (IOException e1) {
                        System.out.println("Room cannot find another player.");
                    }

                    //Finish creating a new validation thread for the new player. Stop this thread
                    break;
                }

                //Validation
                boolean rightAccount = false;

                if (obj instanceof Account) {
                    Account acc = (Account) obj;
                    String username = acc.getUsername();
                    String pass = acc.getPassword();

                    //Get account from the database and compare
                    for (int i = 0; i < Data.getAccounts().size(); i++) {
                        String dataUsername = Data.getAccounts().get(i).getUsername();
                        String dataPassword = String.valueOf(Data.getAccounts().get(i).getPassword());

                        if (username.equals(dataUsername) && pass.equals(dataPassword)) {
                            rightAccount = true;
                            break;
                        }
                    }
                    /*
                    Get the room info and see if a user with this account has already entered
                    this room then prompt the current user to enter another account
                     */
                    if (inUsedUsernames.contains(username)) {
                        rightAccount = false;
                        System.out.println("Someone is using this account!!!");
                    }

                    //Send validation result back to Client
                    if (rightAccount) {
                        System.out.println(username + " login successfully.");
                        roomHandler.addPlayer(playerCom, username);
                        inUsedUsernames.add(username);
                        playerCom.write(State.Waiting);
                        break;

                    } else {
                        playerCom.write(State.WrongAccount);
                        System.out.println("Wrong account.");
                    }
                }
            }
            /*
            Start the game if everyone logs in successfully
            The final thread will invoke this thread
             */
            if (roomHandler.getAllUsernames().size() == numberOfPlayersInARoom) {
                new Thread(roomHandler).start();
            }
        }
    }
}