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

    //Server connection Components
    private static final int PORT = 9000;
    private static ServerSocket server = null;
    private static Thread serverThread = null;
    //Server IP
    private static String IP;
    static AutoObtainIP autoObtainIP = new AutoObtainIP();

    //Count how many rooms the server is controlling
    private int roomNumber = 1;
    public final static int numberOfPlayersPerRoom = 4;

    //Usernames that are inused
    private static ArrayList<String> inUsedUsernames = new ArrayList<String>();

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
                System.out.println("Server is running. The IP is: \n" + Inet4Address.getLocalHost().getHostAddress() + "\n");
                IP = Inet4Address.getLocalHost().getHostAddress();
                autoObtainIP.delete(autoObtainIP.obtainIP());
                autoObtainIP.create(IP);

            } catch (Exception e) {
                System.out.println("Can not find server ip!");
            }

        } catch (IOException e) {
            System.out.println("Cannot connect to ServerSocket.");
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

                //Clean server IP
                try {
                    autoObtainIP.delete(IP);
                } catch (Exception e1) {
                    System.out.println("Cannot delete server IP.");
                }
            }
        });

        //JLabel info and custom UI
        JLabel serverState = new JLabel("<html>" +
                "Port: " + PORT +
                "<br>Server IP: " + IP +
                "<br>Number of players per Room: " + numberOfPlayersPerRoom +
                "<br>Server is running." + "</html>", SwingConstants.CENTER);
        serverState.setOpaque(true);
        serverState.setBackground(Color.YELLOW);
        add(serverState);

        //Repack the JLabel, Set the Frame resolution and Position
        pack();
        setSize(270, 130);
        setLocationRelativeTo(null);//Set center
        setVisible(true);
    }

    @Override
    public synchronized void run() {

        while (true) {

            //A RoomHandler will receive a specific number of player sockets then a new RoomHandler will be created
            RoomHandler roomHandler = new RoomHandler(numberOfPlayersPerRoom);
            for (int j = 0; j < numberOfPlayersPerRoom; j++) {

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
                    System.out.println("Fail to connect to a client socket.");
                    break;
                }
            }

            System.out.println("\nEnough players, Create another Room.");
            System.out.println("~~~~~~~~~~~~~~~//////~~~~~~~~~~~~~~\n");
            roomNumber++;
        }
    }

    public class WaitingForValidation implements Runnable {

        private RoomHandler roomHandler;
        private PlayerCommunicator playerCom;
        private int playerIndex;
        private int roomNum;

        public WaitingForValidation(RoomHandler r, PlayerCommunicator p, int playerIndex, int roomNum) {
            this.roomHandler = r;
            this.playerCom = p;
            this.playerIndex = playerIndex;
            this.roomNum = roomNum;
        }

        @Override
        public void run() {
            //Waiting for account obj and send back validation result
            while (true) {
                System.out.println("Waiting for login info... from " + "Player " + playerIndex + " of Room " + roomNum);
                Object obj;
                try {
                    obj = playerCom.read();

                } catch (RuntimeException e) {
                    /*
                    A player fails to login and disconnect from the game -> this roomNumber will automatically listen
                    to a new player connection instead
                     */
                    System.out.println("Player " + playerIndex + " in Room " + roomNum + " has disconnected.");

                    Socket socket = null;
                    try {
                        socket = server.accept();
                        System.out.println("Another Player " + playerIndex + " of Room " + roomNum + " established the connection successfully.");
                        PlayerCommunicator player = new PlayerCommunicator(
                                socket,
                                new ObjectInputStream(socket.getInputStream()),
                                new ObjectOutputStream(socket.getOutputStream())
                        );
                        new Thread(new WaitingForValidation(roomHandler, player, playerIndex, roomNum)).start();

                    } catch (IOException e1) {
                        System.out.println("Room cannot find another player.");
                    }

                    //Finish creating a new validation thread for the new player. Stop this thread
                    break;
                }

                //Validation process
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
                    See if this account is in used
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
                        //Enter waiting State
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
            if (roomHandler.getAllUsernames().size() == numberOfPlayersPerRoom) {
                new Thread(roomHandler).start();
            }
        }
    }
}