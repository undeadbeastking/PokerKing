package view;

import Server.Server;
import Utils.GamePU;
import Utils.LoginPU;
import controller.GameCon;
import controller.LoginCon;
import controller.SignUpCon;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Agatha of Wood Beyond on 6/29/2014.
 */
public class MainFrame extends JFrame {

    //Read & Write instances
    private BufferedReader in;
    private PrintWriter out;

    //All panels
    private LoginPanel loginPanel = new LoginPanel(this);
    private SignUpPanel signUpPanel = new SignUpPanel();
    private GamePanel gamePanel = null;//Need a user to initialize

    //Controllers
    private LoginCon loginCon;
    private SignUpCon signUpCon;
    private GameCon gameCon;

    //CLient user
    private String clientUser = "";
    private String cards = "";
    private ArrayList<String> allUsers = new ArrayList<String>();

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

        try {
            initSocket();

        } catch (IOException e) {
            System.out.println("Cannot connect to server.");
        }

    }

    private class OnExit extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent windowEvent) {
        }
    }

    private void initSocket() throws IOException {

        //Make connection and initialize streams
        Socket socket = new Socket("localhost", 9001);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()
        ));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    //Receive and process stuffs from server
    public void processSignalFromServer(){
        while (true) {
            try {
                String line = in.readLine();
                if (line.startsWith("FailLogin")) {
                    loginPanel.getErrorMess().setText("*Wrong Username or Password");
                    break;

                } else if (line.startsWith("Accepted")) {
                    loginPanel.loadWaitingUI();
                    break;

                } else if (line.startsWith("AllUsers")) {
                    String signal = line.substring(9);
                    System.out.println(signal);
                    StringTokenizer tokenizer = new StringTokenizer(signal, ",");

                    String userExtract;
                    while(!(userExtract = tokenizer.nextToken()).equals("end")){
                        allUsers.add(userExtract);
                    }
                    for (int i = 0; i < allUsers.size(); i++) {
                        System.out.println(allUsers.get(i));
                    }

                } else if (line.startsWith("Cards")) {
                    String signal = line.substring(6);
                    cards = signal;
                    System.out.println(cards);

                } else if(line.startsWith("Playing")){
                    initGamePanel();
                    break;
                }

            } catch (IOException i) {
                System.out.println("Fail to process client.");
            }
        }
    }

    //Client send username to server
    public void processUser(String username, String password) {
        // Process all messages from server, according to the protocol.
        try {
            String line = in.readLine();
            if (line.startsWith("SubmitAccount")) {
                out.println(username + "," + password);
                //Unique username
                clientUser = username;
            }
            processSignalFromServer();

        } catch (IOException i) {
            System.out.println("Fail to process client.");
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

        if (this.getGamePanel() == null) {
            //Initialized GamePanel & Game Controller
            gamePanel = new GamePanel(this);
            gameCon = new GameCon(this);
        }

        this.add(this.getGamePanel());
        //Set suitable size for the frame and relocate it to center
        this.setSize(GamePU.width, GamePU.height);
        this.setLocationRelativeTo(null);
        //Notify MainFrame
        this.validate();
        this.repaint();
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

    public String getCards() {
        return cards;
    }

    public ArrayList<String> getAllUsers() {
        return allUsers;
    }

    public String getClientUser() {
        return clientUser;
    }
}
