package view;

import CustomUI.CustBut;
import Utils.LoginPU;
import Utils.Utils;

import javax.swing.*;

/**
 * Created by Agatha of Wood Beyond on 6/30/2014.
 */

public class LoginPanel extends JPanel {

    private MainFrame f;
    //Panel components
    private JLabel introLabel = new JLabel("Welcome to Poker King");
    //Input Fields
    private JTextField usernameF = new JTextField();
    private JPasswordField passwordF = new JPasswordField();
    //Input validation Message
    private JLabel errorMess = new JLabel();
    //Buttons
    private CustBut signInButton = new CustBut("Sign in");
    private CustBut signUpButton = new CustBut("Sign me up");

    public LoginPanel(MainFrame f) {

        //Customize Login Panel
        setLayout(null);
        this.setBackground(Utils.backGroundColor);
        this.f = f;

        //Set bounds for components
        introLabel.setBounds(LoginPU.introLabel_x, LoginPU.introLabel_y, LoginPU.introLabel_w, LoginPU.introLabel_h);
        usernameF.setBounds(LoginPU.inputField_x, LoginPU.usernameF_y, LoginPU.inputField_w, LoginPU.inputField_h);
        passwordF.setBounds(LoginPU.inputField_x, LoginPU.passwordF_y, LoginPU.inputField_w, LoginPU.inputField_h);
        signInButton.setBounds(LoginPU.signIn_x, LoginPU.signIn_y, LoginPU.button_w, LoginPU.button_h);
        signUpButton.setBounds(LoginPU.signUp_x, LoginPU.signUp_y, LoginPU.button_w, LoginPU.button_h);
        errorMess.setBounds(LoginPU.error_x, LoginPU.error_y, LoginPU.error_w, LoginPU.error_h);

        //Add components
        this.add(introLabel);
        this.add(usernameF);
        this.add(passwordF);
        this.add(signInButton);
        this.add(signUpButton);
        this.add(errorMess);

        //Customize Introduction label
        introLabel.setFont(LoginPU.IntroFont);
        introLabel.setForeground(LoginPU.IntroColor);

        //Set border for JTextFields
        usernameF.setBorder(Utils.JFColorBorder);
        passwordF.setBorder(Utils.JFColorBorder);

        //Customize login validation mess
        errorMess.setFont(LoginPU.loginFailFont);
        errorMess.setForeground(LoginPU.loginFailColor);

        //Initialize all the fields for first use
        refreshPanel();
    }

    public void loadWaitingUI(){
        errorMess.setText("*Waiting for other players");
        //Prevent UI Interaction while waiting for other Players
        signUpButton.setEnabled(false);
        signInButton.setEnabled(false);
        usernameF.setEnabled(false);
        passwordF.setEnabled(false);
    }

    public void refreshPanel() {
        //Clean error message
        errorMess.setText("");

        //Set signup button Unfocus State
        signUpButton.setFont(Utils.hyperButUnfocusState);

        //Enable buttons
        signUpButton.setEnabled(true);
        signInButton.setEnabled(true);
        usernameF.setEnabled(true);
        passwordF.setEnabled(true);

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

    public CustBut getSignInButton() {
        return signInButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public JLabel getErrorMess() {
        return errorMess;
    }

}
