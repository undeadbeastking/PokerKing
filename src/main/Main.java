package main;

import view.MainInterface;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        MainInterface mainInter = new MainInterface();
        mainInter.setSize(1024,600);
        mainInter.setTitle("Poker King - The Game");
        mainInter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainInter.setResizable(false);
        mainInter.setVisible(true);
    }
}
