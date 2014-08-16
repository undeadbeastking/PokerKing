package view;

import Server.*;
import Utils.GamePU;
import Utils.LoginPU;
import controller.GameCon;
import controller.LoginCon;
import controller.SignUpCon;
import model.Account;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
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

    public MainFrame() {
        //Start COnnection
        try {
            initSocket();

        } catch (IOException e) {
            System.out.println("Cannot to server");
        }

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
    public void run(){

    }

    //Receive and process stuffs from server
    public void processSignalFromServer() {
    }

    //Client send username to server
    public void processUser(String username, String password) {

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


}
