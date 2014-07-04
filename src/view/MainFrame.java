package view;

import controller.GameCon;
import controller.LoginCon;
import controller.SignUpCon;
import model.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created by Agatha of Wood Beyond on 6/29/2014.
 */
public class MainFrame extends JFrame {

    //All panels
    private LoginPanel loginPanel = new LoginPanel();
    private SignUpPanel signUpPanel = new SignUpPanel();
    private GamePanel gamePanel = null;

    //Controllers
    private LoginCon loginCon;
    private SignUpCon signUpCon;
    private GameCon gameCon;

    public MainFrame() {
        //Customize MainFrame for loginPanel
        this.setSize(Utils.loginPanel_width, Utils.loginPanel_height);
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
        Data.loadData();
        //Add Windows listener - On Exit - save files
        this.addWindowListener(new OnExit());
    }

    private class OnExit extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent windowEvent) {
            Data.saveData();
        }
    }

    /*
    Because the need of switching account, GamePanel and GameCon need to
    be initialized later
     */
    public void initialize_GamePanel(String username){

        //Initialized GamePanel & Set Background
        Image img;
        try {
            img = ImageIO.read(getClass().getResource(Utils.gameBackground));
            gamePanel = new GamePanel(img, username);

        } catch (IOException ex) {
            System.out.println("Unable to set background images");
        }

        /*
        Initialize Controller for Game Panel - add listeners after the
        Game Panel has been initialized
         */
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
