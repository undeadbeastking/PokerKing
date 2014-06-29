package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Agatha Wood Beyond on 6/29/2014.
 */
public class LoginInter extends JFrame {
    private JLabel title = new JLabel("Welcome to Poker King");

    public LoginInter(){
        setLayout(null);

        title.setBounds(185,70,250,20);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setForeground(Color.BLUE);

        add(title);
    }
}
