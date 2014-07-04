package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Agatha of Wood Beyond on 7/4/2014.
 */
public class PlayerPanel extends JPanel {

    private JLabel icon = new JLabel();
    private JLabel playerName = new JLabel();
    private JLabel cash = new JLabel();

    public PlayerPanel(int p, String username, int deposit) {
        //Read player image into JLabel icon
        ImageIcon imageIcon = null;
        Image img = null;
        try {
            switch (p) {
                case 1:
                    img = ImageIO.read(getClass().getResource(Utils.Nami_icon));
                    break;

                case 2:
                    img = ImageIO.read(getClass().getResource(Utils.Octopus_icon));
                    break;

                case 3:
                    img = ImageIO.read(getClass().getResource(Utils.Rabbit_icon));
                    break;

                case 4:
                    img = ImageIO.read(getClass().getResource(Utils.Riven_icon));
                    break;

                case 5:
                    img = ImageIO.read(getClass().getResource(Utils.Robot_icon));
                    break;

                case 6:
                    img = ImageIO.read(getClass().getResource(Utils.Scooby_icon));
                    break;

                case 7:
                    String randon_image_path = Utils.random_mainPlayer_icon();
                    img = ImageIO.read(getClass().getResource(randon_image_path));
                    break;

                case 8:
                    img = ImageIO.read(getClass().getResource(Utils.Sona_icon));
                    break;

                case 9:
                    img = ImageIO.read(getClass().getResource(Utils.Lux_icon));
                    break;

                default:
                    System.out.println("No suitable case.");
                    break;
            }

            if (img != null) {
                imageIcon = new ImageIcon(img);
                icon.setIcon(imageIcon);
            }

        } catch (IOException ex) {
            System.out.println("Unable to set player image");
        }

        //Customize player panel
        this.setLayout(null);
        this.setBorder(Utils.PlayerPanel_border);
        this.setBackground(Utils.Transparent_background);

        //Main player is number 7 (Count from 1)
        if (p == 7) {
            //Set bounds for Main player
            icon.setBounds(Utils.PlayerIcon_x, Utils.PlayerIcon_y,
                    Utils.PlayerIcon_width, Utils.PlayerIcon_height);

            playerName.setBounds(Utils.PlayerName_x, Utils.PlayerName_y,
                    Utils.PlayerName_width, Utils.PlayerName_height);

            cash.setBounds(Utils.PlayerCash_x, Utils.PlayerCash_y,
                    Utils.PlayerCash_width, Utils.PlayerCash_height);

            //Customize player statistics
            playerName.setText(username);
            playerName.setForeground(Utils.PlayerName_Color);
            playerName.setFont(Utils.PlayerName_font);
            cash.setText("$" + deposit);
            cash.setForeground(Utils.PlayerCash_Color);
            cash.setFont(Utils.PlayerCash_font);

        } else {
            //Set bounds for opponent players
            icon.setBounds(Utils.OpponentIcon_x, Utils.OpponentIcon_y,
                    Utils.OpponentIcon_width, Utils.OpponentIcon_height);

            playerName.setBounds(Utils.OpponentName_x, Utils.OpponentName_y,
                    Utils.OpponentName_width, Utils.OpponentName_height);

            cash.setBounds(Utils.OpponentCash_x, Utils.OpponentCash_y,
                    Utils.OpponentCash_width, Utils.OpponentCash_height);

            //Customize player statistics
            playerName.setText(username);
            playerName.setForeground(Utils.OpponnentName_Color);
            playerName.setFont(Utils.OpponentName_font);
            cash.setText("$" + deposit);
            cash.setForeground(Utils.OpponnentCash_Color);
            cash.setFont(Utils.OpponentCash_font);
        }

        //Add component
        this.add(icon);
        this.add(playerName);
        this.add(cash);

    }
}
