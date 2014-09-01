package view;

import Server.BetState;
import Server.AutoObtainIP;
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
    private GamePanel gamePanel = null;//Need a user before initializing

    //Controllers
    private LoginCon loginCon;
    private SignUpCon signUpCon;
    private GameCon gameCon;

    //CLient user
    private static final int PORT = 9000;
    private String serverAddress = "localhost";
    private PlayerCommunicator server;
    private Account me;
    private String myCards, commuCards, currentTurnUsername;
    private ArrayList<Integer> money;
    private int myMoney;
    private int currentHighestBet = 100;
    private ArrayList<String> usernames;
    private boolean winnerFound = false;
     AutoObtainIP autoObtainIP = new AutoObtainIP();

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
//        try {
//            serverAddress = autoObtainIP.obtainIP();
//        } catch (Exception e) {
//            System.out.println("Can't obtain");
//        }

        Socket socket = new Socket(serverAddress, PORT);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

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
                    //Receive necessary info before pop up the Game
                    receiveCards();
                    receiveAllPlayerNames();
                    receivePlayersMoney();

                    //Pop Up GamePanel
                    System.out.println("Pop up GamePanel");
                    initGamePanel();

                    for (int i = 0; i < 5; i++) {
                        //Break when fold happens
                        if (winnerFound) break;

                        //Read the Bet State
                        Object betState = server.read();
                        if (betState instanceof BetState) {
                            BetState e = (BetState) betState;

                            if (e == BetState.FirstBet) {
                                gamePanel.getBetRound().setText("Pre-Flop");

                            } else if (e == BetState.SecondBet) {
                                gamePanel.getBetRound().setText("The Flop");
                                gamePanel.getCard1().setVisible(true);
                                gamePanel.getCard2().setVisible(true);
                                gamePanel.getCard3().setVisible(true);

                            } else if (e == BetState.ThirdBet) {
                                gamePanel.getBetRound().setText("The Turn");
                                gamePanel.getCard4().setVisible(true);

                            } else if (e == BetState.FourBet) {
                                gamePanel.getBetRound().setText("The River");
                                gamePanel.getCard5().setVisible(true);

                            } else if(e == BetState.FourBet.ShowDown){
                                gamePanel.getBetRound().setText("ShowDown");

                            }
                        }

                        //i = 4 means it is ShowDown so no Bet Processing
                        if(i!=4){
                            System.out.println("Process a bet State...");
                            processABetState();
                        }
                    }

                } else if (s == State.EndGame) {
                    System.out.println("End game. No longer reading signal from server.");
                    break;
                }
            }
            System.out.println("Read signal...");
            fromServer = server.read();
        }
    }

    public void receiveCards() {
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

    public void receiveAllPlayerNames() {
        server.write(State.SendPlayers);
        Object fromServer = server.read();
        if (fromServer instanceof ArrayList) {
            usernames = (ArrayList<String>) fromServer;
        } else {
            System.out.println("Can not take players from server");
        }
    }

    public void receivePlayersMoney(){
        server.write(State.SendMoney);
        Object fromServer = server.read();
        if (fromServer instanceof ArrayList) {
            money = (ArrayList<Integer>) fromServer;
            System.out.println("Receive Money.");

        } else {
            System.out.println("Can not take players from server");
        }
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

        //Initialized new GamePanel & Game Controller whenever login in
        gamePanel = new GamePanel(this);
        gameCon = new GameCon(this);
        this.add(gamePanel);

        //Set suitable size for the frame and relocate it to center
        this.setSize(GamePU.width, GamePU.height);
        this.setLocationRelativeTo(null);
        //Notify MainFrame
        this.validate();
        this.repaint();
    }

    public void processABetState() {
        while (true) {
            System.out.println("Enter Processing Bet State.");
            boolean isMyTurn = false;//false as default
            Object fromServer = server.read();

            if(fromServer instanceof BetState){
                BetState s = (BetState) fromServer;
                if(s == BetState.EndState){
                    break;
                }
            }

            if (fromServer instanceof String) {
                //Find a winner before the game ends
                if (fromServer.toString().equals("Stop")) {
                    winnerFound = true;
                    System.out.println("Stop game");

                } else if(fromServer instanceof String) {
                    currentTurnUsername = fromServer.toString();
                    if (me.getUsername().equals(currentTurnUsername)) {
                        isMyTurn = true;
                    }
                    System.out.println("This is: " + fromServer + " turn!");
                    gamePanel.setTurn(isMyTurn, currentTurnUsername);
                }
            }

            System.out.println("Turn received, now listen to response.");
            listenResponse();
            if(winnerFound)   break;
        }
    }

    public void listenResponse() {
        if (!winnerFound) {
            Object fromServer = server.read();
            System.out.println("Received from the others: " + fromServer);
            if (fromServer instanceof String) {
                gamePanel.processResponse(currentTurnUsername, fromServer.toString());
            }
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

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    public void setMe(Account a) {
        me = a;
    }

    public Account getMyAccount() {
        return me;
    }

    public ArrayList<Integer> getMoney() {
        return money;
    }

    public int getCurrentHighestBet() {
        return currentHighestBet;
    }

    public void setCurrentHighestBet(int value) {
        currentHighestBet = value;
    }

    public int getMyMoney() {
        return myMoney;
    }

    public void setMyMoney(int m) {
        myMoney = m;
    }
}
