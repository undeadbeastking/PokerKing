package Server;

import model.Account;
import model.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame implements Runnable {

    private static final int PORT = 9000;
    private static Thread serverThread = null;
    private static ServerSocket server = null;
    private int room = 1;//Count how many rooms the server is controlling
    public static int numberOfPlayersInARoom = 4;

    public static void main(String[] args) throws Exception {
        //Load users data
        Data.loadAccounts();

        Server s = new Server();

        if(server != null){
            serverThread = new Thread(s);
            serverThread.start();
        }
    }

    public Server() {
        //print out server ip
        try {
            System.out.println("Server is running. The IP is: \n" + Inet4Address.getLocalHost().getHostAddress());

        } catch (Exception e) {
            System.out.println("Can not find server ip!");
        }

        //Create ServerSocket
        try {
            server = new ServerSocket(PORT);

        } catch (IOException e) {
            System.out.println("Cannot create ServerSocket.");
            return;
        }

        //Server UI
        setSize(200, 100);
        setTitle("Server");
        setResizable(false);
        setLocationRelativeTo(null);//Set center
        setVisible(true);

        //Diconnect Button
        JButton disconnectBut = new JButton("Disconnect from Server");
        disconnectBut.setHorizontalAlignment(SwingConstants.CENTER);
        add(disconnectBut);
        disconnectBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    server.close();
                    System.out.println("Server stopped.");

                } catch (IOException i) {
                    System.out.println("Cannot close server socket.");

                } finally {
                    //Save accounts
                    System.out.println("Saving accounts successfully.");
                    Data.saveAccounts();

                    //Stop receiving connection from clients
                    serverThread.interrupt();
                }
            }
        });
    }

    @Override
    public void run() {

        while (!Thread.interrupted()) {
            RoomHandler g = new RoomHandler(numberOfPlayersInARoom);
            for (int j = 0; j < numberOfPlayersInARoom; j++) {
                if(Thread.interrupted()){
                    System.out.println("Stop receiving client connection requests.");
                    break;
                }

                Socket socket = null;
                try {
                    socket = server.accept();
                    PlayerCommunicator player = new PlayerCommunicator(
                            socket,
                            new ObjectInputStream(socket.getInputStream()),
                            new ObjectOutputStream(socket.getOutputStream())
                    );
                    //Avoid the situation when a player is Taking over the attention of the server
                    //and other players must wait for this player to login successfully first to be able to login next
                    new Thread(new WaitingForValidation(g, player, (j + 1), room)).start();

                } catch (IOException e) {
                    System.out.println("Fail to connect to client socket.");
                }
            }
            System.out.println("Enough players, Create another Room.");
            //Enough players then we start a thread handling for that room
            System.out.println("~~~~~~~~~~~~~~~//////~~~~~~~~~~~~~~");
        }
    }

    public class WaitingForValidation implements Runnable {

        private RoomHandler room;
        private PlayerCommunicator playerCom;
        private int playerNum;
        private int roomNum;

        public WaitingForValidation(RoomHandler g, PlayerCommunicator p, int playerNum, int roomNum) {
            this.room = g;
            this.playerCom = p;
            this.playerNum = playerNum;
            this.roomNum = roomNum;
        }

        @Override
        public void run() {
            //Waiting for account obj and send back validation result
            while (!Thread.interrupted()) {
                System.out.println("Waiting for login info..." + "Player " + playerNum + " of Room: " + roomNum);
                Object o;
                try {
                    o = playerCom.read();

                } catch (RuntimeException e) {
                    //A player fails to login and disconnect from the game -> this room will listen
                    //to a new player connection instead
                    System.out.println("Player " + playerNum + "in Room " + roomNum + " has disconnected.");
                    Socket socket = null;
                    try {
                        socket = server.accept();
                        PlayerCommunicator player = new PlayerCommunicator(
                                socket,
                                new ObjectInputStream(socket.getInputStream()),
                                new ObjectOutputStream(socket.getOutputStream())
                        );
                        new Thread(new WaitingForValidation(room, player, playerNum, roomNum)).start();

                    } catch (IOException e1) {
                        System.out.println("Room cannot find another player");
                    }

                    break;
                }
                boolean rightAccount = false;

                if (o instanceof Account) {
                    Account acc = (Account) o;
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
                    //Account is currently in used
                    if (room.getUsernames().contains(username)) {
                        rightAccount = false;
                        System.out.println("Someone is using this account.");
                    }
                    //Send validation result back to Client
                    if (rightAccount) {
                        System.out.println(username + " login successfully");
                        room.addPlayer(playerCom, username);
                        playerCom.write(State.Waiting);
                        break;

                    } else {
                        playerCom.write(State.WrongAccount);
                        System.out.println("Wrong account.");
                    }
                }
            }

            //Start the game if everyone logs in successfully
            if (room.getUsernames().size() == numberOfPlayersInARoom) {
                new Thread(room).start();
            }
        }
    }
}