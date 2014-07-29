package view;

import Utils.PlayerPU;
import images.ImageGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

/**
 * Created by Agatha of Wood Beyond on 7/4/2014.
 */
public class PlayerPanel extends JPanel {

    private JLabel icon = new JLabel();
    private JLabel username = new JLabel();
    private JLabel status = new JLabel("Status: Raise");
    private JLabel bet = new JLabel();
    //Cards
    private JLabel card1 = new JLabel();
    private JLabel card2 = new JLabel();

    public PlayerPanel(int p, String username) {
        //Read player image into JLabel icon
        Image img = PlayerPU.randomIcon();
        ImageIcon imageIcon = null;

        if (img != null) {
            imageIcon = new ImageIcon(img);
            icon.setIcon(imageIcon);
        }

        //Read cards images
        img = ImageGetter.getInstance().getImage("cards/h1.png");
        //Drop images
        Image cardI1 = createImage(new FilteredImageSource(img.getSource(),
                new CropImageFilter(1, 1, 18, 32)));
        if(img != null){
            imageIcon = new ImageIcon(cardI1);
            card1.setIcon(imageIcon);
        }

        //Customize player panel
        this.setLayout(null);
        this.setBorder(PlayerPU.PanelBorder);
        this.setBackground(PlayerPU.Transparent_background);

        //Same components
        //Bounds
        icon.setBounds(PlayerPU.icon_x, PlayerPU.icon_y,
                PlayerPU.icon_w, PlayerPU.icon_h);
        this.username.setBounds(PlayerPU.label_x, PlayerPU.username_y,
                PlayerPU.label_w, PlayerPU.label_h);
        status.setBounds(PlayerPU.label_x, PlayerPU.status_y,
                PlayerPU.label_w, PlayerPU.label_h);
        bet.setBounds(PlayerPU.label_x, PlayerPU.bet_y,
                PlayerPU.label_w, PlayerPU.label_h);
        card1.setBounds(100, 20, 50, 50);
        //Customize displays
        this.username.setText(username);
        this.username.setFont(PlayerPU.label_font);
        bet.setText("Bet: $" + 5000000);
        bet.setFont(PlayerPU.label_font);
        status.setFont(PlayerPU.label_font);

        if (p == 7) {
            //Customize player statistics
            this.username.setForeground(PlayerPU.pUsername_Color);
            status.setForeground(PlayerPU.label_Color);
            bet.setForeground(PlayerPU.label_Color);

        } else {
            //Customize player statistics
            this.username.setForeground(PlayerPU.username_Color);
            status.setForeground(PlayerPU.label_Color);
            bet.setForeground(PlayerPU.label_Color);

        }

        //Add component
        this.add(icon);
        this.add(this.username);
        this.add(bet);
        this.add(status);
        this.add(card1);
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
