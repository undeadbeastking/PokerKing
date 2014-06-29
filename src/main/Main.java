package main;

import view.LoginInter;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        LoginInter logInter = new LoginInter();
        //Customize Login Frame
        logInter.setSize(600, 400);
        logInter.setTitle("Poker King - The Game");
        logInter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInter.setResizable(false);
        logInter.setLocationRelativeTo(null);//Set center
        logInter.setVisible(true);

    }
}
