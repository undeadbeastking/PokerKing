package view;

import Utils.LoginPU;
import controller.GameCon;
import controller.LoginCon;
import controller.SignUpCon;
import model.Data;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Agatha of Wood Beyond on 6/29/2014.
 */
public class MainFrame extends JFrame {

    //Read & Write instances
    private BufferedReader in;
    private PrintWriter out;

    //All panels
    private LoginPanel loginPanel = new LoginPanel();
    private SignUpPanel signUpPanel = new SignUpPanel();
    private GamePanel gamePanel = null;//Need a user to initialize

    //Controllers
    private LoginCon loginCon;
    private SignUpCon signUpCon;
    private GameCon gameCon;

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

        //Load data to Arraylist accounts
        Data.loadAccounts();

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
            Data.saveAccounts();
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
                if (line.startsWith("NAMEACCEPTED")) {
                    loginPanel.loadWaitingUI();
                    break;

                } else if (line.startsWith("MESSAGE")) {
                }

            } catch (IOException i) {
                System.out.println("Fail to process client.");
            }
        }
    }

    //Client send username to server
    public void processUser(String username, String pasword) {
        // Process all messages from server, according to the protocol.
        try {
            String line = in.readLine();
            if (line.startsWith("SUBMITNAME")) {
                out.println(username);
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
    public void initGamePanel(String username) {

        //Initialized GamePanel & Game Controller
        gamePanel = new GamePanel(username);
        gameCon = new GameCon(this);
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
}
