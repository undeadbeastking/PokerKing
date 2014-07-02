package controller;

import view.MainFrame;
import view.Utils;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Agatha of Wood Beyond on 7/2/2014.
 */
public class LoginCon {
    private MainFrame mainFrame;

    public LoginCon(MainFrame mainF){
        this.mainFrame = mainF;

        //Hint for usernameJTexfield and add Focuslisner
        mainFrame.getLoginPanel().getUsernameField().setBorder(Utils.getColorBorder());
        mainFrame.getLoginPanel().getUsernameField().setFont(Utils.getDimFont());
        mainFrame.getLoginPanel().getUsernameField().setForeground(Color.LIGHT_GRAY);
            //Add FocusListener
        mainFrame.getLoginPanel().getUsernameField().addFocusListener(new usernameListen());

        //Hint for passwordfield and add Focuslisner
        mainFrame.getLoginPanel().getPasswordField().setBorder(Utils.getColorBorder());
        mainFrame.getLoginPanel().getPasswordField().setEchoChar((char) 0);
        mainFrame.getLoginPanel().getPasswordField().setFont(Utils.getDimFont());
        mainFrame.getLoginPanel().getPasswordField().setForeground(Color.LIGHT_GRAY);
            //Add FocusListener
        mainFrame.getLoginPanel().getPasswordField().addFocusListener(new passwordListen());

        //Customize signUp button
            //Remove button's border
        mainFrame.getLoginPanel().getSignUp().setFocusPainted(false);
        mainFrame.getLoginPanel().getSignUp().setMargin(new Insets(0, 0, 0, 0));
            //Remove button's inside color and border
        mainFrame.getLoginPanel().getSignUp().setContentAreaFilled(false);
        mainFrame.getLoginPanel().getSignUp().setBorderPainted(false);
            //Customize button text
        mainFrame.getLoginPanel().getSignUp().setFont(new Font("Consolas", Font.PLAIN, 15));
        mainFrame.getLoginPanel().getSignUp().setForeground(Color.BLUE);
            //Add MouseListener
        mainFrame.getLoginPanel().getSignUp().addMouseListener(new SignUpListen());
    }

    private class usernameListen implements FocusListener{
        @Override
        public void focusGained(FocusEvent e) {
            if (mainFrame.getLoginPanel().getUsernameField().getText().equals("Username...")) {
                mainFrame.getLoginPanel().getUsernameField().setText("");
            }
            mainFrame.getLoginPanel().getUsernameField().setForeground(Color.BLACK);
            mainFrame.getLoginPanel().getUsernameField().setFont(Utils.getMyFont());
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (mainFrame.getLoginPanel().getUsernameField().getText().equals("")) {
                mainFrame.getLoginPanel().getUsernameField().setFont(Utils.getDimFont());
                mainFrame.getLoginPanel().getUsernameField().setForeground(Color.LIGHT_GRAY);
                mainFrame.getLoginPanel().getUsernameField().setText("Username...");
            }
        }
    }

    private class passwordListen implements FocusListener{
        @Override
        public void focusGained(FocusEvent e) {
            String tempInput = String.valueOf(mainFrame.getLoginPanel().getPasswordField().getPassword());
            if (tempInput.equals("Password...")) {
                mainFrame.getLoginPanel().getPasswordField().setText("");
            }
            mainFrame.getLoginPanel().getPasswordField().setForeground(Color.BLACK);
            mainFrame.getLoginPanel().getPasswordField().setEchoChar('*');
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (mainFrame.getLoginPanel().getPasswordField().getPassword().length == 0) {
                mainFrame.getLoginPanel().getPasswordField().setEchoChar((char) 0);
                mainFrame.getLoginPanel().getPasswordField().setFont(Utils.getDimFont());
                mainFrame.getLoginPanel().getPasswordField().setForeground(Color.LIGHT_GRAY);
                mainFrame.getLoginPanel().getPasswordField().setText("Password...");
            }
        }
    }

    private class SignUpListen implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            mainFrame.getLoginPanel().getSignUp().setFont(new Font("Consolas", Font.BOLD, 15));
            mainFrame.getLoginPanel().getSignUp().setForeground(Color.BLUE);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            mainFrame.getLoginPanel().getSignUp().setFont(new Font("Consolas", Font.PLAIN, 15));
            mainFrame.getLoginPanel().getSignUp().setForeground(Color.BLUE);
        }
    }
}
