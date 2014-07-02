package view;

import controller.LoginCon;

import javax.swing.*;

/**
 * Created by Agatha of Wood Beyond on 6/29/2014.
 */
public class MainFrame extends JFrame {

    //All panels
    private LoginPanel loginPanel = new LoginPanel();

    //Controllers
    private LoginCon loginCon;

    public MainFrame(){
        //Customize MainFrame for loginPanel
        this.setSize(600, 400);
        this.setTitle("Poker King - The Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);//Set center
        this.setVisible(true);

        //Add loginPanel Panel
        this.add(loginPanel);

        //Initialize controllers and listeners for all panels' components
        loginCon = new LoginCon(this);
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }
}
