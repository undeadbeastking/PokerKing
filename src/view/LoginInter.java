package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Agatha Wood Beyond on 6/29/2014.
 */
public class LoginInter extends JFrame {
    private JLabel title = new JLabel("Welcome to Poker King");

    public LoginInter(){
        //Customize Login Frame
        this.setSize(600, 400);
        this.setTitle("Poker King - The Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);//Set center
        this.setVisible(true);

        setLayout(null);
        title.setBounds(185,70,250,20);

        add(title);

        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setForeground(Color.BLUE);

    }
}
