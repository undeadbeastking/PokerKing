package controller;

import Utils.SignUpPU;
import Utils.Utils;
import model.Account;
import view.LoginPanel;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Agatha of Wood Beyond on 7/2/2014.
 */
public class LoginCon {

    private MainFrame f;

    public LoginCon(MainFrame frame) {
        this.f = frame;

        //Cast to local variable
        LoginPanel local = f.getLoginPanel();

        //Add FocusListener for Input Fields
        local.getUsernameF().addFocusListener(new JFieldFocusListener());
        local.getPasswordF().addFocusListener(new JFieldFocusListener());

        //Add MouseListener to Signup button
        local.getSignUpButton().addMouseListener(new SignUpListener());

        //Add ActionListener to Signin button anf fields (Enter event)
        local.getSignInButton().addActionListener(new LoginListener());
        local.getUsernameF().addActionListener(new LoginListener());
        local.getPasswordF().addActionListener(new LoginListener());
    }

    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Local cast
            LoginPanel local = f.getLoginPanel();

            //Extract inputs from UI
            String username = local.getUsernameF().getText();
            String password = String.valueOf(local.getPasswordF().getPassword());

            //Pack account and send to server
            Account temp = new Account(username, password);
            System.out.println(temp);
            f.setMe(temp);
            f.getServer().write(temp);
            System.out.println("Sent account info to server");
        }
    }

    private class JFieldFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            JTextField usernameRef;
            JPasswordField passwordRef;

            //usernameField
            if (f.getLoginPanel().getUsernameF() == e.getSource()) {
                usernameRef = f.getLoginPanel().getUsernameF();
                if (usernameRef.getText().equals("Username...")) {
                    usernameRef.setText("");
                }
                usernameRef.setForeground(Utils.inputColor);
                usernameRef.setFont(Utils.inputFont);
            }

            //passwordField
            if (f.getLoginPanel().getPasswordF() == e.getSource()) {
                passwordRef = f.getLoginPanel().getPasswordF();
                String pass = String.valueOf(passwordRef.getPassword());
                if (pass.equals("Password...")) {
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
            if (f.getLoginPanel().getUsernameF() == e.getSource()) {
                usernameRef = f.getLoginPanel().getUsernameF();
                if (usernameRef.getText().equals("")) {
                    usernameRef.setFont(Utils.hintFont);
                    usernameRef.setForeground(Utils.hintColor);
                    usernameRef.setText("Username...");
                }
            }

            //passwordField
            if (f.getLoginPanel().getPasswordF() == e.getSource()) {
                passwordRef = f.getLoginPanel().getPasswordF();
                if (passwordRef.getPassword().length == 0) {
                    passwordRef.setEchoChar((char) 0);
                    passwordRef.setFont(Utils.hintFont);
                    passwordRef.setForeground(Utils.hintColor);
                    passwordRef.setText("Password...");
                }
            }
        }
    }

    private class SignUpListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            //Refresh LoginPanel for next return
            f.getLoginPanel().refreshPanel();
            //Replace Login Panel with SignUp Panel
            f.remove(f.getLoginPanel());
            f.add(f.getSignUpPanel());
            //Set suitable size for the frame and relocate it to center
            f.setSize(SignUpPU.width, SignUpPU.height);
            f.setLocationRelativeTo(null);
            //Notify MainFrame
            f.validate();
            f.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            f.getLoginPanel().getSignUpButton().setFont(Utils.hyperButFocusState);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            f.getLoginPanel().getSignUpButton().setFont(Utils.hyperButUnfocusState);
        }
    }
}
