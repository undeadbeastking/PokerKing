package view;

import CustomUI.CustBut;
import Utils.SignUpPU;
import Utils.Utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Agatha of Wood Beyond on 7/3/2014.
 */
public class SignUpPanel extends JPanel {

    private JLabel introLabel = new JLabel("Register Form");
    //Input fields
    private JTextField usernameField = new JTextField("Username...");
    private JPasswordField passwordField = new JPasswordField("Password...");
    //Validation fields
    private JLabel usernameVal = new JLabel();
    private JLabel passwordVal = new JLabel();
    //Buttons
    private CustBut backButton = new CustBut("back");
    private CustBut registerButton = new CustBut("Register");

    public SignUpPanel() {
        //Customize SignUp Panel
        setLayout(null);
        this.setBackground(Utils.backGroundColor);

        //Set Bounds
        introLabel.setBounds(110, 60, 180, 30);
        backButton.setBounds(320, 20, 50, 50);
        registerButton.setBounds(140, 240, 110, 30);
        //Vertical Gap for textfields: 55
        usernameField.setBounds(105, 120, 180, 25);
        passwordField.setBounds(105, 175, 180, 25);
        //Vertical Gap for valfields: 55
        usernameVal.setBounds(110, 140, 250, 30);
        passwordVal.setBounds(110, 195, 250, 30);

        //Add components
        this.add(introLabel);
        this.add(backButton);
        this.add(registerButton);
        //Input fields
        this.add(usernameField);
        this.add(passwordField);
        //Validation fields
        this.add(usernameVal);
        this.add(passwordVal);

        //Customize introduction label
        introLabel.setFont(SignUpPU.introLFont);
        introLabel.setForeground(SignUpPU.introLColor);

        //Set border for fields
        usernameField.setBorder(Utils.JFColorBorder);
        passwordField.setBorder(Utils.JFColorBorder);

        //First init || refershPanel
        refreshPanel();
    }

    public void refreshPanel() {
        //Refresh input fields
        usernameField.setText("Username...");
        passwordField.setText("Password...");
        passwordField.setEchoChar((char) 0);//Review password
        //Refresh Validation messages
        usernameVal.setText("");
        passwordVal.setText("");

        //Customize Input fields
        defaultSetUp(usernameField);
        defaultSetUp(passwordField);
        //Customize validation fields
        defaultSetUp(usernameVal);
        defaultSetUp(passwordVal);
    }

    private void defaultSetUp(Component input) {
        JTextField textField;
        JPasswordField passField;
        JLabel labelField;

        if (input instanceof JTextField) {
            textField = (JTextField) input;//local cast
            textField.setFont(Utils.hintFont);
            textField.setForeground(Utils.hintColor);

        } else if (input instanceof JPasswordField) {
            passField = (JPasswordField) input;//local cast
            passField.setFont(Utils.hintFont);
            passField.setForeground(Utils.hintColor);

        } else if (input instanceof JLabel) {
            labelField = (JLabel) input;//local cast
            labelField.setFont(Utils.hintFont);
            labelField.setForeground(Utils.hintColor);

        }
    }

    public JLabel getPasswordVal() {
        return passwordVal;
    }

    public JLabel getUsernameVal() {
        return usernameVal;
    }

    public JPasswordField getPasswordF() {
        return passwordField;
    }

    public JTextField getUsernameF() {
        return usernameField;
    }

    public CustBut getRegistBut() {
        return registerButton;
    }

    public CustBut getBackBut() {
        return backButton;
    }
}
