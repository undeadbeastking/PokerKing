package view;

import Custom.CustBut;

import javax.swing.*;

/**
 * Created by Agatha of Wood Beyond on 6/30/2014.
 */

public class LoginPanel extends JPanel {

    //Panel components
    private JLabel intro = new JLabel("Welcome to Poker King");
    //Input Fields
    private JTextField usernameField = new JTextField("Username...");
    private JPasswordField passwordField = new JPasswordField("Password...");
    //Input validation Message
    private JLabel loginApproval = new JLabel();
    //Buttons
    private CustBut signIn = new CustBut("Sign in");
    private CustBut signUp = new CustBut("Sign me up");


    public LoginPanel() {
        //Customize Login Panel
        setLayout(null);
        this.setBackground(Utils.backGroundColor);

        //Set bounds for components
        intro.setBounds(170, 70, 290, 30);
        usernameField.setBounds(200, 130, 200, 30);
        passwordField.setBounds(200, 180, 200, 30);
        signIn.setBounds(225, 240, 150, 30);
        signUp.setBounds(40, 315, 120, 30);
        loginApproval.setBounds(375, 312, 190, 30);

        //Add components
        this.add(intro);
        this.add(usernameField);
        this.add(passwordField);
        this.add(signIn);
        this.add(signUp);
        this.add(loginApproval);

        //Customize Introduction label
        intro.setFont(Utils.loginPanelIntroLabelFont);
        intro.setForeground(Utils.loginPanelIntroLabelColor);

        //Initialize all the fields for first use
        reverseInitializedState();
    }

    private void reverseInitializedState() {
        //Customize usernameJTexfield first look
        usernameField.setBorder(Utils.JTextFieldColorBorder);
        usernameField.setFont(Utils.hintFont);
        usernameField.setForeground(Utils.hintColor);

        //Customize passwordField first look
        passwordField.setBorder(Utils.JTextFieldColorBorder);
        passwordField.setEchoChar((char) 0);
        passwordField.setFont(Utils.hintFont);
        passwordField.setForeground(Utils.hintColor);

        //Customize login validation Label
        loginApproval.setFont(Utils.loginFailFont);
        loginApproval.setForeground(Utils.loginFailColor);
    }

    public void loginPanelRefresh() {
        usernameField.setText("Username...");
        passwordField.setText("Password...");
        loginApproval.setText("");
        signUp.setFont(Utils.hyperTextButtonFontNormal);
        reverseInitializedState();
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

    public JLabel getLoginApproval() {
        return loginApproval;
    }
}
