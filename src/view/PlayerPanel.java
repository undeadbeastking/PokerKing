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

    private MainFrame f;

    //Player avatar
    private JLabel icon = new JLabel();

    private JLabel username = new JLabel();
    private JLabel status = new JLabel();
    private JLabel remainCash = new JLabel();

    //Hole Cards
    private JLabel holeCard1 = new JLabel();
    private JLabel holeCard2 = new JLabel();

    public PlayerPanel(int index, MainFrame frame) {
        //Customize player panel
        this.f = frame;
        this.setLayout(null);
        this.setOpaque(false);//Transparent background

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
        remainCash.setBounds(PlayerPU.label_x, PlayerPU.cash_y, PlayerPU.label_w, PlayerPU.label_h);
        holeCard1.setBounds(PlayerPU.holeCard1_x, PlayerPU.holeCard_y, PlayerPU.holeCard_w, PlayerPU.holeCard_h);
        holeCard2.setBounds(PlayerPU.holeCard2_x, PlayerPU.holeCard_y, PlayerPU.holeCard_w, PlayerPU.holeCard_h);

        //Set Player Panel Bound
        setPlayerPanelBound(index);

        //Customize displays
        username.setFont(PlayerPU.label_font);
        remainCash.setFont(PlayerPU.label_font);
        remainCash.setForeground(PlayerPU.label_Color);
        status.setFont(PlayerPU.label_font);
        status.setForeground(PlayerPU.label_Color);

        //Local cast username list from MainFrame
        ArrayList<String> usernames = f.getAllUsernames();

        //The player behind the final player will pay the small blind
        if(index == usernames.size()-2){
            status.setText("Small Blind: $50");

        //The final player hosts the big blind
        } else if(index == usernames.size()-1) {
            status.setText("Big Blind: $100");
        }

        //Set text for username label
        username.setText(usernames.get(index));

        //Set text for player's cash
        //First one will be expected to used $100 to match a Call
        if(index == 0){
            remainCash.setText("Cash: $" + (f.getAllMoney().get(index) - f.getCurrentHighestBet()));
        } else {
            remainCash.setText("Cash: $" + f.getAllMoney().get(index));
        }

        //If the current username == Client username then Highlight his username
        if (usernames.get(index).equals(f.getMyAccount().getUsername())) {
            //Highlight yellow username
            username.setForeground(PlayerPU.myUsername_Color);

            //Display hole cards for the client
            setHoleCards(true);

            //Remember this player Money, other just for display
            f.setMyMoney(f.getAllMoney().get(index));

            //Set my index
            f.setMyIndex(index);

        //For opponents
        } else {
            //Green Border
            username.setForeground(PlayerPU.otherUsername_Color);

            //Hide hole cards of other players
            setHoleCards(false);

        }

        //Add component
        this.add(icon);
        this.add(username);
        this.add(remainCash);
        this.add(status);
        this.add(holeCard1);
        this.add(holeCard2);
    }

    //Set PlayerPanel Bound & Resolution
    private void setPlayerPanelBound(int index){
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
                System.out.println("Cannot find the PlayerPanel to set bound.");
        }
    }

    //Set hole cards
    public void setHoleCards(boolean isClient) {

        //Default face down hole cards
        String path1 = PlayerPU.cardsPathPrefix + "0.png";
        String path2 = PlayerPU.cardsPathPrefix + "0.png";

        //is client then set the right hole cards
        if (isClient) {
            StringTokenizer tokenizer = new StringTokenizer(f.getMyHoleCards(), "-");
            path1 = PlayerPU.cardsPathPrefix + tokenizer.nextToken() + ".png";
            path2 = PlayerPU.cardsPathPrefix + tokenizer.nextToken() + ".png";
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
            holeCard1.setIcon(imageIcon);
            imageIcon = new ImageIcon(cardI2);
            holeCard2.setIcon(imageIcon);
        }
    }

    public void highlightCurrentPlayerTurn() {
        this.setBorder(PlayerPU.YourTurnBorder);
    }

    public void waitingStatus() {
        this.setBorder(PlayerPU.DisableBorder);
    }

    public String getUsername() {
        return username.getText();
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }

    public JLabel getRemainCash() {
        return remainCash;
    }
}
