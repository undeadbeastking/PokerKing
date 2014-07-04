package view;

import Custom.CustBut;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Agatha of Wood Beyond on 7/3/2014.
 */
public class SignUpPanel extends JPanel {

    private JLabel intro = new JLabel("Register Form");
    //Input fields
    private JTextField usernameField = new JTextField("Username...");
    private JPasswordField passwordField = new JPasswordField("Password...");
    //Validation fields
    private JLabel usernameVal = new JLabel();
    private JLabel passwordVal = new JLabel();
    //Buttons
    private CustBut back = new CustBut();
    private CustBut register = new CustBut("Register");

    public SignUpPanel() {
        //Customize SignUp Panel
        setLayout(null);
        this.setBackground(Utils.backGroundColor);

        //Set Bounds
        intro.setBounds(110, 60, 180, 30);
        back.setBounds(320, 20, 50, 50);
        register.setBounds(140, 240, 110, 30);
        //Vertical Gap for textfields: 55
        usernameField.setBounds(105, 120, 180, 25);
        passwordField.setBounds(105, 175, 180, 25);
        //Vertical Gap for valfields: 55
        usernameVal.setBounds(110, 140, 250, 30);
        passwordVal.setBounds(110, 195, 250, 30);

        //Add components
        this.add(intro);
        this.add(back);
        this.add(register);
        //Input fields
        this.add(usernameField);
        this.add(passwordField);
        //Validation fields
        this.add(usernameVal);
        this.add(passwordVal);

        //Customize introduction label
        intro.setFont(Utils.signUpPanelIntroLabelFont);
        intro.setForeground(Utils.signUpPanelIntroLabelColor);

        //Customize input and validation fields for first use
        reverseInitializedState();
    }

    public void reverseInitializedState() {
        //Customize Input fields
        defaultSetUp(usernameField);
        //Review password hint
        passwordField.setEchoChar((char) 0);
        defaultSetUp(passwordField);

        //Customize validation fields
        defaultSetUp(usernameVal);
        defaultSetUp(passwordVal);
    }

    public void signUpPanelRefresh() {
        //Set initialized texts for fields
        usernameField.setText("Username...");
        passwordField.setText("Password...");
        usernameVal.setText("");
        passwordVal.setText("");
        //Refresh to initialized state
        reverseInitializedState();
    }

    private void defaultSetUp(Component input) {
        JTextField textField;
        JPasswordField passField;
        JLabel labelField;

        if (input instanceof JTextField) {
            textField = (JTextField) input;
            textField.setBorder(Utils.JTextFieldColorBorder);
            textField.setFont(Utils.hintFont);
            textField.setForeground(Utils.hintColor);

        } else if (input instanceof JPasswordField) {
            passField = (JPasswordField) input;
            passField.setBorder(Utils.JTextFieldColorBorder);
            passField.setFont(Utils.hintFont);
            passField.setForeground(Utils.hintColor);

        } else if (input instanceof JLabel) {
            labelField = (JLabel) input;
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

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public CustBut getRegister() {
        return register;
    }

    public CustBut getBack() {
        return back;
    }
}
