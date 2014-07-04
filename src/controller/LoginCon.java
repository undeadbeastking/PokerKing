package controller;

import model.Data;
import view.MainFrame;
import view.PlayerPanel;
import view.Utils;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Agatha of Wood Beyond on 7/2/2014.
 */
public class LoginCon {

    private MainFrame mainFrame;

    public LoginCon(MainFrame f) {
        this.mainFrame = f;

        //Add FocusListener for Input Fields
        mainFrame.getLoginPanel().getUsernameField().addFocusListener(new JFieldListener());
        mainFrame.getLoginPanel().getPasswordField().addFocusListener(new JFieldListener());

        //Add MouseListener to Signup button
        mainFrame.getLoginPanel().getSignUp().addMouseListener(new SignUpListener());

        //Add ActionListener to Signin button
        mainFrame.getLoginPanel().getSignIn().addActionListener(new SigninListener());
    }

    private class SigninListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //Extract inputs
            String username = mainFrame.getLoginPanel().getUsernameField().getText();
            String password = String.valueOf(mainFrame.getLoginPanel().getPasswordField().getPassword());
            boolean byPass = false;

            //Validate username and password
            for (int i = 0; i < Data.getAccounts().size(); i++) {
                String comparedUsername = Data.getAccounts().get(i).getUsername();
                String comparedPassword = String.valueOf(Data.getAccounts().get(i).getPassword());

                if(username.equals(comparedUsername) && password.equals(comparedPassword)){
                    byPass = true;
                    break;
                }
            }

            //Log in successfully?
            if(byPass){
                //Remove text of Login panel's fields
                mainFrame.getLoginPanel().loginPanelRefresh();
                //Replace Login Panel with SignUp Panel
                mainFrame.remove(mainFrame.getLoginPanel());

                if(mainFrame.getGamePanel() == null){
                    mainFrame.initialize_GamePanel(username);

                    /*
                    Switch the panel of the current player only
                    No need for set up the whole Game Panel again
                     */
                } else {
                    //get current player panel
                    PlayerPanel[] current = mainFrame.getGamePanel().getPlayersP();
                    current[6].changeCurrentPlayer(username);
                }

                mainFrame.add(mainFrame.getGamePanel());
                //Set suitable size for the frame and relocate it to center
                mainFrame.setSize(Utils.GamePanel_width, Utils.GamePanel_height);
                mainFrame.setLocationRelativeTo(null);
                //Notify MainFrame
                mainFrame.validate();
                mainFrame.repaint();

            } else {
                mainFrame.getLoginPanel().getLoginApproval().setText("*Wrong username or password");
            }
        }
    }

    private class JFieldListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            JTextField usernameRef;
            JPasswordField passwordRef;

            //usernameField
            if (mainFrame.getLoginPanel().getUsernameField() == e.getSource()) {
                usernameRef = mainFrame.getLoginPanel().getUsernameField();
                if (usernameRef.getText().equals("Username...")) {
                    usernameRef.setText("");
                }
                usernameRef.setForeground(Utils.inputColor);
                usernameRef.setFont(Utils.inputFont);
            }

            //passwordField
            if (mainFrame.getLoginPanel().getPasswordField() == e.getSource()) {
                passwordRef = mainFrame.getLoginPanel().getPasswordField();
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
            if (mainFrame.getLoginPanel().getUsernameField() == e.getSource()) {
                usernameRef = mainFrame.getLoginPanel().getUsernameField();
                if (usernameRef.getText().equals("")) {
                    usernameRef.setFont(Utils.hintFont);
                    usernameRef.setForeground(Utils.hintColor);
                    usernameRef.setText("Username...");
                }
            }

            //passwordField
            if (mainFrame.getLoginPanel().getPasswordField() == e.getSource()) {
                passwordRef = mainFrame.getLoginPanel().getPasswordField();
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
            //Remove text of Login panel's fields
            mainFrame.getLoginPanel().loginPanelRefresh();
            //Replace Login Panel with SignUp Panel
            mainFrame.remove(mainFrame.getLoginPanel());
            mainFrame.add(mainFrame.getSignUpPanel());
            //Set suitable size for the frame and relocate it to center
            mainFrame.setSize(Utils.SignUpPanel_width, Utils.SignUpPanel_height);
            mainFrame.setLocationRelativeTo(null);
            //Notify MainFrame
            mainFrame.validate();
            mainFrame.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            mainFrame.getLoginPanel().getSignUp().setFont(Utils.hyperTextButtonFontEnter);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            mainFrame.getLoginPanel().getSignUp().setFont(Utils.hyperTextButtonFontNormal);
        }
    }
}
