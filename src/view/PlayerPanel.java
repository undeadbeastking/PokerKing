package view;

import Utils.PlayerPU;
import images.ImageGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
    private MainFrame f;
    //Players info
    private ArrayList<String> allPlayers;

    public PlayerPanel(int p, MainFrame frame) {
        //Customize player panel
        this.setLayout(null);
        this.setBorder(PlayerPU.PanelBorder);
        this.setBackground(PlayerPU.Transparent_background);
        this.f = frame;

        //Read player image into JLabel icon
        Image img = PlayerPU.randomIcon();
        ImageIcon imageIcon = null;
        if (img != null) {
            imageIcon = new ImageIcon(img);
            icon.setIcon(imageIcon);
        }

        //Bounds
        icon.setBounds(PlayerPU.icon_x, PlayerPU.icon_y,
                PlayerPU.icon_w, PlayerPU.icon_h);
        username.setBounds(PlayerPU.label_x, PlayerPU.username_y,
                PlayerPU.label_w, PlayerPU.label_h);
        status.setBounds(PlayerPU.label_x, PlayerPU.status_y,
                PlayerPU.label_w, PlayerPU.label_h);
        bet.setBounds(PlayerPU.label_x, PlayerPU.bet_y,
                PlayerPU.label_w, PlayerPU.label_h);
        card1.setBounds(PlayerPU.card1_x, PlayerPU.card_y,
                PlayerPU.card_w, PlayerPU.card_h);
        card2.setBounds(PlayerPU.card2_x, PlayerPU.card_y,
                PlayerPU.card_w, PlayerPU.card_h);

        //Customize displays

        username.setFont(PlayerPU.label_font);
        bet.setText("Bet: $" + 5000000);
        bet.setFont(PlayerPU.label_font);
        status.setFont(PlayerPU.label_font);
        status.setForeground(PlayerPU.label_Color);
        bet.setForeground(PlayerPU.label_Color);

        allPlayers = f.getAllUsers();
        if (allPlayers.get(p).equals(f.getClientUser())) {
            username.setText(f.getClientUser());
            //Customize player statistics
            username.setForeground(PlayerPU.pUsername_Color);
            setCards(true);

        } else {
            username.setText(allPlayers.get(p));
            //Customize player statistics
            username.setForeground(PlayerPU.username_Color);
            setCards(false);

        }

        //Add component
        this.add(icon);
        this.add(username);
        this.add(bet);
        this.add(status);
        this.add(card1);
        this.add(card2);
    }

    //Set hole cards
    public void setCards(boolean thisPlayer) {

        String path1 = PlayerPU.pathPrefix + "0.png";
        String path2 = PlayerPU.pathPrefix + "0.png";

        if (thisPlayer) {
            StringTokenizer tokenizer = new StringTokenizer(f.getCards(), "-");
            path1 = PlayerPU.pathPrefix + tokenizer.nextToken() + ".png";
            path2 = PlayerPU.pathPrefix + tokenizer.nextToken() + ".png";
        }

        Image img1 = ImageGetter.getInstance().getImage(path1);
        Image img2 = ImageGetter.getInstance().getImage(path2);


        //Drop images
        Image cardI1 = createImage(new FilteredImageSource(img1.getSource(),
                new CropImageFilter(1, 1, 18, 32)));
        Image cardI2 = createImage(new FilteredImageSource(img2.getSource(),
                new CropImageFilter(1, 1, 18, 32)));

        if (img1 != null && img2 != null) {
            ImageIcon imageIcon = new ImageIcon(cardI1);
            card1.setIcon(imageIcon);
            imageIcon = new ImageIcon(cardI2);
            card2.setIcon(imageIcon);
        }
    }
}
