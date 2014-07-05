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

    private MainFrame f;

    public SignUpCon(MainFrame frame) {
        this.f = frame;

        //Add FocusListener to fields
        this.f.getSignUpPanel().getUsernameField().addFocusListener(new JFieldListener());
        this.f.getSignUpPanel().getPasswordField().addFocusListener(new JFieldListener());

        //Add ActionListener for Back button
        this.f.getSignUpPanel().getBack().addActionListener(new BackButtonListener());

        //Add ActionListener for Register button
        this.f.getSignUpPanel().getRegister().addActionListener(new RegisterListener());
    }

    private class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Casting to local variables
            JTextField usernameRef = f.getSignUpPanel().getUsernameField();
            JPasswordField passwordRef = f.getSignUpPanel().getPasswordField();
            JLabel usernameValRef = f.getSignUpPanel().getUsernameVal();
            JLabel passwordValRef = f.getSignUpPanel().getPasswordVal();
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
        f.getSignUpPanel().signUpPanelRefresh();
        //Replace Signup Panel with Login Panel
        f.remove(f.getSignUpPanel());
        f.add(f.getLoginPanel());
        //Set suitable size for the frame and relocate it to center
        f.setSize(Utils.loginPanel_width, Utils.loginPanel_height);
        f.setLocationRelativeTo(null);
        //Refresh the MainFrame
        f.validate();
        f.repaint();
    }

    private class JFieldListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            JTextField usernameRef;
            JPasswordField passwordRef;

            //usernameField
            if (f.getSignUpPanel().getUsernameField() == e.getSource()) {
                usernameRef = f.getSignUpPanel().getUsernameField();
                if (usernameRef.getText().equals("Username...")) {
                    usernameRef.setText("");
                }
                usernameRef.setForeground(Utils.inputColor);
                usernameRef.setFont(Utils.inputFont);
            }

            //passwordField
            if (f.getSignUpPanel().getPasswordField() == e.getSource()) {
                passwordRef = f.getSignUpPanel().getPasswordField();
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
            if (f.getSignUpPanel().getUsernameField() == e.getSource()) {
                usernameRef = f.getSignUpPanel().getUsernameField();
                if (usernameRef.getText().equals("")) {
                    usernameRef.setFont(Utils.hintFont);
                    usernameRef.setForeground(Utils.hintColor);
                    usernameRef.setText("Username...");
                }
            }

            //passwordField
            if (f.getSignUpPanel().getPasswordField() == e.getSource()) {
                passwordRef = f.getSignUpPanel().getPasswordField();
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
