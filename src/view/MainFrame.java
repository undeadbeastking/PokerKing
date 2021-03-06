package view;

import Server.AutoObtainIP;
import Server.BetState;
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

    //Client
    //Connection components
    private static final int PORT = 9000;
    private String serverAddress = "localhost";
    private PlayerCommunicator server;
    private AutoObtainIP autoObtainIP = new AutoObtainIP();

    //All usernames in the room
    private ArrayList<String> allUsernames;

    //This client account
    private Account me;
    private int myIndex;

    //Cards
    private String myHoleCards, commuCards, currentTurnUsername;

    private ArrayList<Integer> allMoney;
    private int myMoney;
    private int moneyToAdd;
    private int myBet = 0;
    private int currentHighestBet = 100;

    public MainFrame() {

//        try{
//            if (autoObtainIP.obtainIP().length() > 1) {
//                serverAddress = autoObtainIP.obtainIP();
//                System.out.println("Connected to " + serverAddress);
//            }else {
//                System.out.println("Cant obtain IP");
//            }
//        }catch (Exception e){
//            System.out.println("Cant connect to server");
//        }

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

        //Connect to server
        try {
            initSocket();

        } catch (IOException e) {
            System.out.println("Cannot connect to server");
        }
    }

    private void initSocket() throws IOException {

        Socket socket = new Socket(serverAddress, PORT);

        //Creepy error - oos must be init first before ois
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        server = new PlayerCommunicator(socket, ois, oos);
    }

    @Override
    public void run() {
        //Process signals from Server
        Object fromServer = server.read();//Read the first Signal
        System.out.println("Read signal....");
        while (fromServer != null) {

            if (fromServer instanceof State) {
                State s = (State) fromServer;
                if (s == State.WrongAccount) {
                    loginPanel.getErrorMess().setText("*Wrong account");

                } else if (s == State.Waiting) {
                    loginPanel.loadWaitingUI();

                } else if (s == State.StartGame) {
                    //Receive necessary info before Init the GamePanel
                    receiveCards();
                    receiveAllUsernames();
                    receiveAllPlayerMoney();

                    //Init and Pop Up GamePanel
                    initGamePanel();
                    System.out.println("Pop up GamePanel");

                    //Process 4 BET states
                    for (int i = 0; i < 4; i++) {

                        //Read the Bet State
                        Object betState = server.read();
                        if (betState instanceof BetState) {
                            BetState e = (BetState) betState;

                            if (e == BetState.FirstBet) {
                                gamePanel.getBetRoundLabel().setText("Pre-Flop");

                            } else if (e == BetState.SecondBet) {
                                gamePanel.getBetRoundLabel().setText("The Flop");
                                gamePanel.getComCard1().setVisible(true);
                                gamePanel.getComCard2().setVisible(true);
                                gamePanel.getComCard3().setVisible(true);

                            } else if (e == BetState.ThirdBet) {
                                gamePanel.getBetRoundLabel().setText("The Turn");
                                gamePanel.getComCard4().setVisible(true);

                            } else if (e == BetState.FourBet) {
                                gamePanel.getBetRoundLabel().setText("The River");
                                gamePanel.getComCard5().setVisible(true);

                            }
                        }

                        System.out.println("Process a bet State...");
                        processABetState();
                    }

                    Object betState = server.read();
                    if (betState instanceof BetState) {
                        BetState e = (BetState) betState;

                        if (e == BetState.ShowDown) {
                            gamePanel.getBetRoundLabel().setText("ShowDown");
                            nhanTien();
                            showWinner();
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
            myHoleCards = tokenizer.nextToken();
            commuCards = tokenizer.nextToken();

        } else {
            System.out.println("Cannot take cards from server");
        }
    }

    public void receiveAllUsernames() {
        server.write(State.SendPlayers);
        Object fromServer = server.read();
        if (fromServer instanceof ArrayList) {
            allUsernames = (ArrayList<String>) fromServer;
        } else {
            System.out.println("Cannot take usernames from server");
        }
    }

    public void receiveAllPlayerMoney() {
        server.write(State.SendMoney);
        Object fromServer = server.read();
        if (fromServer instanceof ArrayList) {
            allMoney = (ArrayList<Integer>) fromServer;

        } else {
            System.out.println("Cannot take allMoney from server");
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
            boolean isMyTurn = false;
            Object fromServer = server.read();

            if (fromServer instanceof String) {

                //Find a winner before the game ends
                if (fromServer.toString().equals("Skip betting")) {
                    break;

                } else {
                    currentTurnUsername = fromServer.toString();
                    if (me.getUsername().equals(currentTurnUsername)) {
                        isMyTurn = true;
                    }

                    System.out.println("This is: " + fromServer + " turn!");
                    gamePanel.setTurn(isMyTurn, currentTurnUsername);

                    System.out.println("Turn received, now listen to response.");
                    listenResponse();
                }

            } else if (fromServer instanceof BetState) {
                BetState s = (BetState) fromServer;
                if (s == BetState.EndState) {
                    break;
                }
            }
        }
    }

    public void listenResponse() {
        Object fromServer = server.read();
        System.out.println("Received from the others: " + fromServer);

        if (fromServer instanceof String) {
            gamePanel.processResponseFromOtherPlayer(currentTurnUsername, fromServer.toString());
        }
    }

    private void showWinner(){
        Object fromServer = server.read();
        if (fromServer instanceof String && fromServer != null){
            String winnerUsername = (String) fromServer;
            System.out.println(winnerUsername);
            StringTokenizer tokenizer = new StringTokenizer(winnerUsername, ",");
            String display = tokenizer.nextToken();
            while (tokenizer.hasMoreTokens()){
                display = display + tokenizer.nextToken()  + " ,";
            }
            JOptionPane.showMessageDialog(null, "Our winner tonight, lady and gentlemen: " + display);
        }
    }

    private void nhanTien(){
        System.out.println("bat dau chia tien");
        Object fromServer = server.read();
        if (fromServer instanceof ArrayList) {
            allMoney = (ArrayList<Integer>) fromServer;
            for (int i = 0; i < allMoney.size(); i ++){
                System.out.println("Tien oi la tien: " + allMoney.get(i));
            }
        } else {
            System.out.println("Cannot take allMoney from server");
        }
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

    public String getMyHoleCards() {
        return myHoleCards;
    }

    public ArrayList<String> getAllUsernames() {
        return allUsernames;
    }

    public void setMe(Account a) {
        me = a;
    }

    public Account getMyAccount() {
        return me;
    }

    public ArrayList<Integer> getAllMoney() {
        return allMoney;
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

    public int getMyIndex() {
        return myIndex;
    }

    public int getMyBet() {
        return myBet;
    }

    public void setMyBet(int myBet) {
        this.myBet = myBet;
    }

    public void setMyIndex(int myIndex) {
        this.myIndex = myIndex;
    }

    public int getMoneyToAdd() {
        return moneyToAdd;
    }

    public void setMoneyToAdd(int moneyToAdd) {
        this.moneyToAdd = moneyToAdd;
    }
}
