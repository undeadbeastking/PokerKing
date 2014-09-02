package controller;

import Utils.LoginPU;
import Utils.Utils;
import model.Data;
import model.Validator;
import view.MainFrame;
import view.SignUpPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Agatha of Wood Beyond on 7/3/2014.
 */
public class SignUpCon {

    private MainFrame f;

    public SignUpCon(MainFrame frame) {
        this.f = frame;

        //local variable
        SignUpPanel local = f.getSignUpPanel();

        //Add FocusListener to fields
        local.getUsernameF().addFocusListener(new JFieldFocusListener());
        local.getPasswordF().addFocusListener(new JFieldFocusListener());

        //Add ActionListener for buttons
        local.getBackBut().addActionListener(new ActionListener());
        local.getRegistBut().addActionListener(new ActionListener());
    }

    private class ActionListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Local SignUpPanel
            SignUpPanel local = f.getSignUpPanel();

            //Register button ActionListener
            if (e.getSource() == local.getRegistBut()) {
                //Casting to local variables
                JTextField usernameRef = local.getUsernameF();
                JPasswordField passwordRef = local.getPasswordF();
                JLabel usernameValRef = local.getUsernameVal();
                JLabel passwordValRef = local.getPasswordVal();

                boolean isQualified = true;

                //Extract inputs from TextFields
                String username = usernameRef.getText();
                String password = String.valueOf(passwordRef.getPassword());

                //Validate username
                if (Validator.isEmpty(username)) {
                    isQualified = false;
                    usernameValRef.setText(Validator.emptyMess);

                } else if (Validator.containSpace(username)) {
                    isQualified = false;
                    usernameValRef.setText(Validator.spaceMess);

                } else if (Validator.usernameExist(username)) {
                    isQualified = false;
                    usernameValRef.setText(Validator.unavailableMess);

                } else if (Validator.notLongEnough(username)) {
                    isQualified = false;
                    usernameValRef.setText(Validator.lengthMess);

                } else {
                    //Everything is fine then set empty to Error Field
                    usernameValRef.setText("");
                }

                //Validate password
                if (Validator.isEmpty(password)) {
                    isQualified = false;
                    passwordValRef.setText(Validator.emptyMess);

                } else if (Validator.containSpace(password)) {
                    isQualified = false;
                    passwordValRef.setText(Validator.spaceMess);

                } else if (Validator.notLongEnough(password)) {
                    isQualified = false;
                    passwordValRef.setText(Validator.lengthMess);

                } else {
                    //Everything is fine then set empty to Error Field
                    passwordValRef.setText("");
                }

                if (isQualified) {
                    Data.addAccount(username, password);

                    //Show success message
                    JOptionPane.showMessageDialog(null, "Register successfully");

                    //Return to login Panel
                    toLoginP();
                }
            }

            //Back Button listener
            if (e.getSource() == local.getBackBut()) {
                toLoginP();
            }
        }
    }

    private void toLoginP() {
        //Refresh JTextfield and validation field
        f.getSignUpPanel().refreshPanel();
        //Replace Signup Panel with Login Panel
        f.remove(f.getSignUpPanel());
        f.add(f.getLoginPanel());
        //Set suitable size for the frame and relocate it to center
        f.setSize(LoginPU.width, LoginPU.height);
        f.setLocationRelativeTo(null);
        //Refresh the MainFrame
        f.validate();
        f.repaint();
    }

    private class JFieldFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            JTextField usernameRef;
            JPasswordField passwordRef;

            //usernameField
            if (f.getSignUpPanel().getUsernameF() == e.getSource()) {

                usernameRef = f.getSignUpPanel().getUsernameF();

                if (usernameRef.getText().equals("Username...")) {
                    usernameRef.setText("");
                }
                usernameRef.setForeground(Utils.inputColor);
                usernameRef.setFont(Utils.inputFont);
            }

            //passwordField
            if (f.getSignUpPanel().getPasswordF() == e.getSource()) {

                passwordRef = f.getSignUpPanel().getPasswordF();
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
            if (f.getSignUpPanel().getUsernameF() == e.getSource()) {
                usernameRef = f.getSignUpPanel().getUsernameF();

                if (usernameRef.getText().equals("")) {
                    usernameRef.setFont(Utils.hintFont);
                    usernameRef.setForeground(Utils.hintColor);
                    usernameRef.setText("Username...");
                }
            }

            //passwordField
            if (f.getSignUpPanel().getPasswordF() == e.getSource()) {
                passwordRef = f.getSignUpPanel().getPasswordF();

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
