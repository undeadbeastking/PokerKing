package view;

import Utils.PlayerPU;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Agatha of Wood Beyond on 7/4/2014.
 */
public class PlayerPanel extends JPanel {

    private JLabel icon = new JLabel();
    private JLabel username = new JLabel();
    private JLabel status = new JLabel();
    private JLabel bet = new JLabel();

    public PlayerPanel(int p, String username) {
        //Read player image into JLabel icon
        ImageIcon imageIcon = null;
        Image img = PlayerPU.randomIcon();

        if (img != null) {
            imageIcon = new ImageIcon(img);
            icon.setIcon(imageIcon);
        }

        //Customize player panel
        this.setLayout(null);
        this.setBorder(PlayerPU.PanelBorder);
        this.setBackground(PlayerPU.Transparent_background);

        //Main player is number 7 (Count from 1)
        if (p == 7) {
            //Set bounds for Main player
            icon.setBounds(PlayerPU.icon_x, PlayerPU.icon_y,
                    PlayerPU.icon_w, PlayerPU.icon_h);

            this.username.setBounds(PlayerPU.username_x, PlayerPU.username_y,
                    PlayerPU.username7_w, PlayerPU.username7_h);

            bet.setBounds(PlayerPU.playerBet_x, PlayerPU.playerBet_y,
                    PlayerPU.playerBet_w, PlayerPU.playerBet_h);

            //Customize player statistics
            this.username.setText(username);
            this.username.setForeground(PlayerPU.username7_Color);
            this.username.setFont(PlayerPU.username7_font);
            //Default bet
            bet.setText("Bet: $" + 5000000);
            bet.setForeground(PlayerPU.playerBet_color);
            bet.setFont(PlayerPU.playerBet_font);

        } else {
            //Set bounds for opponent players
            icon.setBounds(PlayerPU.icon_x, PlayerPU.icon_y,
                    PlayerPU.icon_w, PlayerPU.icon_h);

            this.username.setBounds(PlayerPU.username_x, PlayerPU.username_y,
                    PlayerPU.username_w, PlayerPU.username_h);

            bet.setBounds(PlayerPU.bet_x, PlayerPU.bet_y,
                    PlayerPU.bet_w, PlayerPU.bet_h);

            //Customize player statistics
            this.username.setText(username);
            this.username.setForeground(PlayerPU.username_Color);
            this.username.setFont(PlayerPU.username_font);
            bet.setText("Bet: $" + 5000000);
            bet.setForeground(PlayerPU.bet_Color);
            bet.setFont(PlayerPU.bet_font);
        }

        //Add component
        this.add(icon);
        this.add(this.username);
        this.add(bet);

    }

    public void newMainPlayer(String newName) {
        //Change username
        username.setText(newName);

        //Load new player icon
        ImageIcon imageIcon = null;
        Image img = PlayerPU.randomIcon();

        if (img != null) {
            imageIcon = new ImageIcon(img);
            icon.setIcon(imageIcon);
        }
    }
}
