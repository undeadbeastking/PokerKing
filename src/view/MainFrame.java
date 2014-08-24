package view;

import Server.PlayerCommunicator;
import Server.State;
import Utils.GamePU;
import Utils.LoginPU;
import controller.GameCon;
import controller.LoginCon;
import controller.SignUpCon;
import model.Account;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Agatha of Wood Beyond on 6/29/2014.
 */
public class MainFrame extends JFrame implements Runnable {

    //All panels
    private LoginPanel loginPanel = new LoginPanel(this);
    private SignUpPanel signUpPanel = new SignUpPanel();
    private GamePanel gamePanel = null;//Need a user to initialize

    //Controllers
    private LoginCon loginCon;
    private SignUpCon signUpCon;
    private GameCon gameCon;

    //CLient user
    private static final int PORT = 9000;
    private String serverAddress = "localhost";
    private PlayerCommunicator server;
    private Account me;
    private String myCards, commuCards;
    private ArrayList<String> usernames = new ArrayList<String>();

    public MainFrame() {
        //Customize MainFrame for loginPanel
        this.setSize(LoginPU.width, LoginPU.height);
        this.setTitle("Poker King - The Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);//Set center
        this.setVisible(true);

        //Add loginPanel as default
        this.add(loginPanel);

        //Initialize controllers and listeners for all panels' components
        loginCon = new LoginCon(this);
        signUpCon = new SignUpCon(this);

        //Add Windows listener - On Exit - save files
        this.addWindowListener(new OnExit());

        //Start Connection
        try {
            initSocket();

        } catch (IOException e) {
            System.out.println("Cannot connect to server");
        }
    }

    private void initSocket() throws IOException {

        //Make connection and initialize streams
        Socket socket = new Socket(serverAddress, PORT);
        ObjectOutputStream oos = new ObjectOutputStream(
                socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(
                socket.getInputStream());

        server = new PlayerCommunicator(socket, ois, oos);
    }

    private class OnExit extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent windowEvent) {
        }
    }

    public static void main(String[] args) {
        MainFrame m = new MainFrame();
        new Thread(m).start();
    }

    @Override
    public void run() {
        //Process signals from Server
        Object fromServer = server.read();
        while (fromServer != null) {
            if (fromServer instanceof State) {
                State s = (State) fromServer;
                if (s == State.WrongAccount) {
                    loginPanel.getErrorMess().setText("*Wrong account");
                } else if (s == State.Waiting) {
                    loginPanel.loadWaiting();

                } else if (s == State.StartGame) {
                    takeCard();
                    takePlayers();
                    initGamePanel();
                    System.out.println("Pop up GamePanel");
                    while (true){
                        checkTurn();
                        listenResponse();
                    }

                } else if (s == State.EndGame) {

                    System.out.println("End game. No longer reading signal from server.");
                    break;
                }
            }
            System.out.println("Read signal...");
            fromServer = server.read();
        }
        System.out.println("No longer reading signal from the server.");
    }

    /*
    Because the need of switching account, GamePanel and GameCon need to
    be initialized later so it can receive the logged in user
     */
    public void initGamePanel() {

        //Refresh LoginPanel for next Login
        loginPanel.refreshPanel();
        //Replace Login Panel with Game Panel
        this.remove(loginPanel);

        if (this.getGamePanel() == null) {
            //Initialized GamePanel & Game Controller
            gamePanel = new GamePanel(this);
            gameCon = new GameCon(this);
//            gamePanel.setTurn(myTurn);
        }

        this.add(this.getGamePanel());
        //Set suitable size for the frame and relocate it to center
        this.setSize(GamePU.width, GamePU.height);
        this.setLocationRelativeTo(null);
        //Notify MainFrame
        this.validate();
        this.repaint();
    }

    public void takeCard() {
        server.write(State.SendCard);
        Object fromServer = server.read();
        if (fromServer != null) {
            String cards = fromServer.toString();
            StringTokenizer tokenizer = new StringTokenizer(cards, "/");
            myCards = tokenizer.nextToken();
            commuCards = tokenizer.nextToken();
            System.out.println(cards);
        } else {
            System.out.println("Can not take cards from server");
        }
    }

    public void takePlayers() {
        server.write(State.SendPlayers);
        Object fromServer = server.read();
        if (fromServer instanceof ArrayList) {
            usernames = (ArrayList<String>) fromServer;
        } else {
            System.out.println("Can not take players from server");
        }
    }

    public void checkTurn() {
        boolean myTurn = false;
        String name = "";
        Object fromServer = server.read();
        if (fromServer instanceof String){
            name = fromServer.toString();
            if (me.getUsername().equals(name)){
                System.out.println("This is: " + fromServer + " turn!");
                myTurn = true;
            }
        }
        gamePanel.setTurn(myTurn, name);
    }

    public void listenResponse (){
        Object fromServer = server.read();
        if (fromServer instanceof String){
            System.out.println(fromServer + " Receive a respose from a player");
        }
    }



    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public SignUpPanel getSignUpPanel() {
        return signUpPanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public PlayerCommunicator getServer() {
        return server;
    }

    public String getCommuCards() {
        return commuCards;
    }

    public String getMyCards() {
        return myCards;
    }

    public ArrayList<String> getAllUsers() {
        return usernames;
    }

    public void setMe(Account a) {
        me = a;
    }

    public Account getMe() {
        return me;
    }
}
