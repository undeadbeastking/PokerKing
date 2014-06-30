package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Agatha of Wood Beyond on 6/30/2014.
 */

public class Login extends JPanel {

    private JLabel intro = new JLabel("Welcome to Poker King");
    private JTextField username = new JTextField("Username...");
    private JPasswordField  password = new JPasswordField ("Password...");

    public Login(){
        //Customize Login Panel
        setLayout(null);
        this.setSize(600, 400);
        this.setBackground(new Color(137,207,240));

        //Set bounds for components
        intro.setBounds(185, 70, 250, 30);
        username.setBounds(200,130,200,30);
        password.setBounds(200,180,200,30);

        //Add components
        this.add(intro);
        this.add(username);
        this.add(password);

        //Customize Introduction label
        intro.setFont(new Font("Comic sans ms", Font.BOLD, 20));
        intro.setForeground(Color.BLUE);

        //Customize username box
        username.setFont(MainFrame.getDimFont());
        username.setForeground(Color.LIGHT_GRAY);

        username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(username.getText().equals("Username...")){
                    username.setText("");
                }
                username.setForeground(Color.BLACK);
                username.setFont(MainFrame.getMyFont());
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(username.getText().equals("")){
                    username.setFont(MainFrame.getDimFont());
                    username.setForeground(Color.LIGHT_GRAY);
                    username.setText("Username...");
                }
            }
        });

        //Customize password box
        password.setEchoChar((char)0);
        password.setFont(MainFrame.getDimFont());
        password.setForeground(Color.LIGHT_GRAY);

        password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String tempInput = String.valueOf(password.getPassword());
                if(tempInput.equals("Password...")){
                    password.setText("");
                }
                password.setForeground(Color.BLACK);
                password.setEchoChar('*');
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(password.getPassword().length == 0){
                    password.setEchoChar((char)0);
                    password.setFont(MainFrame.getDimFont());
                    password.setForeground(Color.LIGHT_GRAY);
                    password.setText("Password...");
                }
            }
        });

    }
}
