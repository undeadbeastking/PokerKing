package controller;

import model.Data;
import model.Validator;
import view.MainFrame;
import view.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Agatha of Wood Beyond on 7/3/2014.
 */
public class SignUpCon {

    private MainFrame mainFrame;

    public SignUpCon(MainFrame f) {
        this.mainFrame = f;

        //Add FocusListener to fields
        mainFrame.getSignUpPanel().getUsernameField().addFocusListener(new JFieldListener());
        mainFrame.getSignUpPanel().getPasswordField().addFocusListener(new JFieldListener());

        //Add ActionListener for Back button
        mainFrame.getSignUpPanel().getBack().addActionListener(new BackButtonListener());

        //Add ActionListener for Register button
        mainFrame.getSignUpPanel().getRegister().addActionListener(new RegisterListener());
    }

    private class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Casting to local variables
            JTextField usernameRef = mainFrame.getSignUpPanel().getUsernameField();
            JPasswordField passwordRef = mainFrame.getSignUpPanel().getPasswordField();
            JLabel usernameValRef = mainFrame.getSignUpPanel().getUsernameVal();
            JLabel passwordValRef = mainFrame.getSignUpPanel().getPasswordVal();
            boolean qualified = true;

            //Extract inputs from TextFields
            String username = usernameRef.getText();
            String password = String.valueOf(passwordRef.getPassword());

            //Validate username
            if (Validator.isEmpty(username)) {
                qualified = false;
                usernameRef.setBorder(Utils.JTextFieldErrorBorder);
                usernameValRef.setText(Validator.emptyMess);

            } else if (Validator.containSpace(username)) {
                qualified = false;
                usernameRef.setBorder(Utils.JTextFieldErrorBorder);
                usernameValRef.setText(Validator.spaceMess);

            } else if (Validator.usernameExist(username)) {
                qualified = false;
                usernameRef.setBorder(Utils.JTextFieldErrorBorder);
                usernameValRef.setText(Validator.unavailableMess);

            } else if (Validator.notLongEnough(username)) {
                qualified = false;
                usernameRef.setBorder(Utils.JTextFieldErrorBorder);
                usernameValRef.setText(Validator.lengthMess);

            } else {
                usernameRef.setBorder(Utils.JTextFieldColorBorder);
                usernameValRef.setText("");

            }

            //Validate password
            if (Validator.isEmpty(password)) {
                qualified = false;
                passwordRef.setBorder(Utils.JTextFieldErrorBorder);
                passwordValRef.setText(Validator.emptyMess);

            } else if (Validator.containSpace(password)) {
                qualified = false;
                passwordRef.setBorder(Utils.JTextFieldErrorBorder);
                passwordValRef.setText(Validator.spaceMess);

            } else if (Validator.notLongEnough(password)) {
                qualified = false;
                passwordRef.setBorder(Utils.JTextFieldErrorBorder);
                passwordValRef.setText(Validator.lengthMess);

            } else {
                passwordRef.setBorder(Utils.JTextFieldColorBorder);
                passwordValRef.setText("");

            }

            if (qualified) {
                Data.add(username, password);
                //Show success message
                JOptionPane.showMessageDialog(null, "Register successfully");
                //Return to login
                backToLoginPanel();
            }
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            backToLoginPanel();
        }
    }

    private void backToLoginPanel() {
        //Refresh JTextfield and validation field
        mainFrame.getSignUpPanel().signUpPanelRefresh();
        //Replace Signup Panel with Login Panel
        mainFrame.remove(mainFrame.getSignUpPanel());
        mainFrame.add(mainFrame.getLoginPanel());
        //Set suitable size for the frame and relocate it to center
        mainFrame.setSize(Utils.loginPanel_width, Utils.loginPanel_height);
        mainFrame.setLocationRelativeTo(null);
        //Refresh the mainFrame
        mainFrame.validate();
        mainFrame.repaint();
    }

    private class JFieldListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            JTextField usernameRef;
            JPasswordField passwordRef;

            //usernameField
            if (mainFrame.getSignUpPanel().getUsernameField() == e.getSource()) {
                usernameRef = mainFrame.getSignUpPanel().getUsernameField();
                if (usernameRef.getText().equals("Username...")) {
                    usernameRef.setText("");
                }
                usernameRef.setForeground(Utils.inputColor);
                usernameRef.setFont(Utils.inputFont);
            }

            //passwordField
            if (mainFrame.getSignUpPanel().getPasswordField() == e.getSource()) {
                passwordRef = mainFrame.getSignUpPanel().getPasswordField();
                String password = String.valueOf(passwordRef.getPassword());
                if (password.equals("Password...")) {
                    passwordRef.setText("");
                }
                passwordRef.setForeground(Utils.inputColor);
                passwordRef.setEchoChar('*');
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField usernameRef;
            JPasswordField passwordRef;

            //usernameField
            if (mainFrame.getSignUpPanel().getUsernameField() == e.getSource()) {
                usernameRef = mainFrame.getSignUpPanel().getUsernameField();
                if (usernameRef.getText().equals("")) {
                    usernameRef.setFont(Utils.hintFont);
                    usernameRef.setForeground(Utils.hintColor);
                    usernameRef.setText("Username...");
                }
            }

            //passwordField
            if (mainFrame.getSignUpPanel().getPasswordField() == e.getSource()) {
                passwordRef = mainFrame.getSignUpPanel().getPasswordField();
                if (passwordRef.getPassword().length == 0) {
                    passwordRef.setEchoChar((char) 0);
                    passwordRef.setFont(Utils.hintFont);
                    passwordRef.setForeground(Utils.hintColor);
                    passwordRef.setText("Password...");
                }
            }
        }
    }
}
