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
    private JLabel status = new JLabel();
    private JLabel cash = new JLabel();
    private JLabel bet = new JLabel("Bet: $" + 0);
    //Cards
    private JLabel card1 = new JLabel();
    private JLabel card2 = new JLabel();
    private MainFrame f;
    //Players info
    private ArrayList<String> usernames;

    public PlayerPanel(int orderInArray, MainFrame frame) {
        //Customize player panel
        this.setLayout(null);
        this.setOpaque(false);
        this.f = frame;

        //Read player image into JLabel icon
        Image img = PlayerPU.randomIcon();
        ImageIcon imageIcon = null;
        if (img != null) {
            imageIcon = new ImageIcon(img);
            icon.setIcon(imageIcon);
        }

        //Bounds
        icon.setBounds(PlayerPU.icon_x, PlayerPU.icon_y, PlayerPU.icon_w, PlayerPU.icon_h);
        username.setBounds(PlayerPU.label_x, PlayerPU.username_y, PlayerPU.label_w, PlayerPU.label_h);
        status.setBounds(PlayerPU.label_x, PlayerPU.status_y, PlayerPU.label_w, PlayerPU.label_h);
        cash.setBounds(PlayerPU.label_x, PlayerPU.cash_y, PlayerPU.label_w, PlayerPU.label_h);
        card1.setBounds(PlayerPU.card1_x, PlayerPU.card_y, PlayerPU.card_w, PlayerPU.card_h);
        card2.setBounds(PlayerPU.card2_x, PlayerPU.card_y, PlayerPU.card_w, PlayerPU.card_h);
        bet.setBounds(PlayerPU.bet_x, PlayerPU.bet_y, PlayerPU.label_w, PlayerPU.label_h);

        //Set Player Panel Bound
        setPanelBound(orderInArray);

        //Customize displays
        username.setFont(PlayerPU.label_font);
        cash.setText("Cash: $" + 1000);
        cash.setFont(PlayerPU.label_font);
        cash.setForeground(PlayerPU.label_Color);
        status.setFont(PlayerPU.label_font);
        status.setForeground(PlayerPU.label_Color);
        bet.setFont(PlayerPU.label_font);
        bet.setForeground(PlayerPU.label_Color);

        usernames = f.getUsernames();
        //If the current username == Client username then Highlight his or her Panel
        if (usernames.get(orderInArray).equals(f.getMyAccount().getUsername())) {
            username.setText(f.getMyAccount().getUsername());
            //Customize player statistics
            username.setForeground(PlayerPU.pUsername_Color);
            setHoleCards(true);

        //For other users, Face down the hole cards
        } else {
            username.setText(usernames.get(orderInArray));
            //Customize player statistics
            username.setForeground(PlayerPU.username_Color);
            setHoleCards(false);

        }

        //Add component
        this.add(icon);
        this.add(username);
        this.add(cash);
        this.add(status);
        this.add(card1);
        this.add(card2);
        this.add(bet);
    }

    //Set PlayerPanel Bound & Resolution
    private void setPanelBound(int index){
        switch(index){
            case 0:
                this.setBounds(PlayerPU.panel1_x, PlayerPU.panel1_4_y,
                        PlayerPU.width, PlayerPU.height);
                break;

            case 1:
                this.setBounds(PlayerPU.panel2_x, PlayerPU.panel2_3_y,
                        PlayerPU.width, PlayerPU.height);
                break;

            case 2:
                this.setBounds(PlayerPU.panel3_x, PlayerPU.panel2_3_y,
                        PlayerPU.width, PlayerPU.height);
                break;

            case 3:
                this.setBounds(PlayerPU.panel4_x, PlayerPU.panel1_4_y,
                        PlayerPU.width, PlayerPU.height);
                break;

            case 4:
                this.setBounds(PlayerPU.panel5_x, PlayerPU.panel5_9_y,
                        PlayerPU.width, PlayerPU.height);
                break;

            case 5:
                this.setBounds(PlayerPU.panel6_x, PlayerPU.panel6_8_y,
                        PlayerPU.width, PlayerPU.height);
                break;

            case 6:
                this.setBounds(PlayerPU.panel7_x, PlayerPU.panel7_y,
                        PlayerPU.width, PlayerPU.height);
                break;

            case 7:
                this.setBounds(PlayerPU.panel8_x, PlayerPU.panel6_8_y,
                        PlayerPU.width, PlayerPU.height);
                break;

            case 8:
                this.setBounds(PlayerPU.panel9_x, PlayerPU.panel5_9_y,
                        PlayerPU.width, PlayerPU.height);
                break;

            default:
                System.out.println("Cannot find the Panel to set bound.");
        }
    }

    //Set hole cards
    public void setHoleCards(boolean thisPlayer) {

        String path1 = PlayerPU.pathPrefix + "0.png";
        String path2 = PlayerPU.pathPrefix + "0.png";

        if (thisPlayer) {
            StringTokenizer tokenizer = new StringTokenizer(f.getMyCards(), "-");
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

    public void highlightMyTurn() {
        this.setBorder(PlayerPU.TurnBorder);
    }

    public void highlightOtherTurn() {
        this.setBorder(PlayerPU.PanelBorder);
    }

    public String getUsername() {
        return username.getText();
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }
}
