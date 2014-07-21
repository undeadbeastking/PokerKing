package view;

import CustomUI.CustBut;
import Utils.LoginPU;
import Utils.Utils;

import javax.swing.*;

/**
 * Created by Agatha of Wood Beyond on 6/30/2014.
 */

public class LoginPanel extends JPanel {

    //Panel components
    private JLabel intro = new JLabel("Welcome to Poker King");
    //Input Fields
    private JTextField usernameF = new JTextField();
    private JPasswordField passwordF = new JPasswordField();
    //Input validation Message
    private JLabel errorMess = new JLabel();
    //Buttons
    private CustBut signIn = new CustBut("Sign in");
    private CustBut signUp = new CustBut("Sign me up");


    public LoginPanel() {
        //Customize Login Panel
        setLayout(null);
        this.setBackground(Utils.backGroundColor);

        //Set bounds for components
        intro.setBounds(LoginPU.intro_x, LoginPU.intro_y,
                LoginPU.intro_w, LoginPU.intro_h);
        usernameF.setBounds(LoginPU.field_x, LoginPU.usernameF_y,
                LoginPU.field_w, LoginPU.field_h);
        passwordF.setBounds(LoginPU.field_x, LoginPU.passwordF_y,
                LoginPU.field_w, LoginPU.field_h);
        signIn.setBounds(LoginPU.signIn_x, LoginPU.signIn_y,
                LoginPU.but_w, LoginPU.but_h);
        signUp.setBounds(LoginPU.signUp_x, LoginPU.signUp_y,
                LoginPU.but_w, LoginPU.but_h);
        errorMess.setBounds(LoginPU.error_x, LoginPU.error_y,
                LoginPU.error_w, LoginPU.error_h);

        //Add components
        this.add(intro);
        this.add(usernameF);
        this.add(passwordF);
        this.add(signIn);
        this.add(signUp);
        this.add(errorMess);

        //Customize Introduction label
        intro.setFont(LoginPU.IntroFont);
        intro.setForeground(LoginPU.IntroColor);

        //Set border for JTextFields
        usernameF.setBorder(Utils.JFColorBorder);
        passwordF.setBorder(Utils.JFColorBorder);

        //Customize login validation mess
        errorMess.setFont(LoginPU.loginFailFont);
        errorMess.setForeground(LoginPU.loginFailColor);

        //Initialize all the fields for first use
        refreshPanel();
    }

    public void refreshPanel() {

        //Clean error message
        errorMess.setText("");

        //Set signup button Unfocus State
        signUp.setFont(Utils.hyperButUnfocusState);

        //Set input hints
        usernameF.setText("Username...");
        usernameF.setFont(Utils.hintFont);
        usernameF.setForeground(Utils.hintColor);

        passwordF.setEchoChar((char) 0);//review hint in password
        passwordF.setText("Password...");
        passwordF.setFont(Utils.hintFont);
        passwordF.setForeground(Utils.hintColor);

    }

    public JTextField getUsernameF() {
        return usernameF;
    }

    public JPasswordField getPasswordF() {
        return passwordF;
    }

    public CustBut getSignIn() {
        return signIn;
    }

    public JButton getSignUp() {
        return signUp;
    }

    public JLabel getErrorMess() {
        return errorMess;
    }
}
