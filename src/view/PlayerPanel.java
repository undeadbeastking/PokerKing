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
        this.setOpaque(false);

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
        setPanelBound(index);

        //Customize displays
        username.setFont(PlayerPU.label_font);
        remainCash.setText("Cash: $" + 1000);
        remainCash.setFont(PlayerPU.label_font);
        remainCash.setForeground(PlayerPU.label_Color);
        status.setFont(PlayerPU.label_font);
        status.setForeground(PlayerPU.label_Color);

        //Get usernames list from MainFrame
        ArrayList<String> usernames = f.getUsernames();

        //Set Text username
        username.setText(usernames.get(index));

        if (usernames.get(index).equals(f.getMyAccount().getUsername())) {

            //Highlight my username
            username.setForeground(PlayerPU.myUsername_Color);

            //Display my hole cards
            setHoleCards(true);

        } else {

            //Normal color username
            username.setForeground(PlayerPU.otherUsername_Color);

            //Face down hole cards
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

    //Set PlayerPanel Bound & Resolution based on index
    private void setPanelBound(int index){
        switch(index){
            case 0:
                this.setBounds(PlayerPU.panel1_x, PlayerPU.panel1_4_y, PlayerPU.width, PlayerPU.height);
                break;

            case 1:
                this.setBounds(PlayerPU.panel2_x, PlayerPU.panel2_3_y, PlayerPU.width, PlayerPU.height);
                break;

            case 2:
                this.setBounds(PlayerPU.panel3_x, PlayerPU.panel2_3_y, PlayerPU.width, PlayerPU.height);
                break;

            case 3:
                this.setBounds(PlayerPU.panel4_x, PlayerPU.panel1_4_y, PlayerPU.width, PlayerPU.height);
                break;

            case 4:
                this.setBounds(PlayerPU.panel5_x, PlayerPU.panel5_9_y, PlayerPU.width, PlayerPU.height);
                break;

            case 5:
                this.setBounds(PlayerPU.panel6_x, PlayerPU.panel6_8_y, PlayerPU.width, PlayerPU.height);
                break;

            case 6:
                this.setBounds(PlayerPU.panel7_x, PlayerPU.panel7_y, PlayerPU.width, PlayerPU.height);
                break;

            case 7:
                this.setBounds(PlayerPU.panel8_x, PlayerPU.panel6_8_y, PlayerPU.width, PlayerPU.height);
                break;

            case 8:
                this.setBounds(PlayerPU.panel9_x, PlayerPU.panel5_9_y, PlayerPU.width, PlayerPU.height);
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
            StringTokenizer tokenizer = new StringTokenizer(f.getMyHoleCards(), "-");
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
            holeCard1.setIcon(imageIcon);
            imageIcon = new ImageIcon(cardI2);
            holeCard2.setIcon(imageIcon);
        }
    }

    public void highlightMyTurn() {
        this.setBorder(PlayerPU.MyTurnBorder);
    }

    public void highlightOtherTurn() {
        this.setBorder(PlayerPU.NotMyTurnBorder);
    }

    public String getUsername() {
        return username.getText();
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }
}
