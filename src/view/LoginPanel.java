package view;

import Custom.CustBut;
import javafx.scene.control.Hyperlink;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Agatha of Wood Beyond on 6/30/2014.
 */

public class LoginPanel extends JPanel {

    //Panel components
    private JLabel intro = new JLabel("Welcome to Poker King");
    private JTextField usernameField = new JTextField("Username...");
    private JPasswordField passwordField = new JPasswordField ("Password...");
    private CustBut signIn = new CustBut("Sign in");
    private JButton signUp = new JButton("Sign me up");

    public LoginPanel(){
        //Customize Login Panel
        setLayout(null);
        this.setSize(600, 400);
        this.setBackground(new Color(137,207,240));

        //Set bounds for components
        intro.setBounds(185, 70, 250, 30);
        usernameField.setBounds(200, 130, 200, 30);
        passwordField.setBounds(200, 180, 200, 30);
        signIn.setBounds(225,240,150,30);
        signUp.setBounds(40,315,120,30);

        //Add components
        this.add(intro);
        this.add(usernameField);
        this.add(passwordField);
        this.add(signIn);
        this.add(signUp);

        //Customize Introduction label
        intro.setFont(new Font("Calibri", Font.BOLD, 25));
        intro.setForeground(Color.BLUE);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public CustBut getSignIn() {
        return signIn;
    }

    public JButton getSignUp() {
        return signUp;
    }
}
