package view;

import CustomUI.CustBut;
import images.ImageGetter;
import Utils.GamePU;
import Utils.PlayerPU;

import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Agatha of Wood Beyond on 7/4/2014.
 */
public class GamePanel extends JPanel {

    private Image backGround;
    private int maxPlayersNum = 9;
    private PlayerPanel[] playersP;
    private CustBut back = new CustBut("back");
    //Function buttons
    private CustBut foldBut = new CustBut("Fold");
    private CustBut callBut = new CustBut("Call");
    private CustBut raiseBut = new CustBut("Raise");
    private MainFrame f;
    private ArrayList<String> allPlayers;
    private JLabel card1 = new JLabel();
    private JLabel card2 = new JLabel();
    private JLabel card3 = new JLabel();
    private JLabel card4 = new JLabel();
    private JLabel card5 = new JLabel();


    public GamePanel(MainFrame frame) {
        //Customize Game Panel
        this.backGround = ImageGetter.getInstance().getImage(GamePU.backGround);
        setLayout(null);
        this.f = frame;
        setCards();
        //Create all players panels - real player will be added
        playersP = new PlayerPanel[maxPlayersNum];
        allPlayers = f.getAllUsers();
        for (int j = 0; j < allPlayers.size(); j++) {
            playersP[j] = new PlayerPanel(j, f);
        }

        //Set bounds for player panels
        playersP[0].setBounds(PlayerPU.panel1_x, PlayerPU.panel1_4_y,
                PlayerPU.width, PlayerPU.height);
        playersP[1].setBounds(PlayerPU.panel2_x, PlayerPU.panel2_3_y,
                PlayerPU.width, PlayerPU.height);
        playersP[2].setBounds(PlayerPU.panel3_x, PlayerPU.panel2_3_y,
                PlayerPU.width, PlayerPU.height);
        playersP[3].setBounds(PlayerPU.panel4_x, PlayerPU.panel1_4_y,
                PlayerPU.width, PlayerPU.height);

//        playersP[4].setBounds(PlayerPU.panel5_x, PlayerPU.panel5_9_y,
//                PlayerPU.width, PlayerPU.height);
//        playersP[8].setBounds(PlayerPU.panel9_x, PlayerPU.panel5_9_y,
//                PlayerPU.width, PlayerPU.height);
//
//        playersP[5].setBounds(PlayerPU.panel6_x, PlayerPU.panel6_8_y,
//                PlayerPU.width, PlayerPU.height);
//        playersP[7].setBounds(PlayerPU.panel8_x, PlayerPU.panel6_8_y,
//                PlayerPU.width, PlayerPU.height);
//        //Center player
//        playersP[6].setBounds(PlayerPU.panel7_x, PlayerPU.panel7_y,
//                PlayerPU.width, PlayerPU.height);
        //Other components
        back.setBounds(GamePU.backBut_x, GamePU.backBut_y,
                GamePU.backBut_w, GamePU.backBut_h);
        foldBut.setBounds(GamePU.fold_x, GamePU.fcr_y,
                GamePU.fcr_w, GamePU.fcr_h);
        callBut.setBounds(GamePU.call_x, GamePU.fcr_y,
                GamePU.fcr_w, GamePU.fcr_h);
        raiseBut.setBounds(GamePU.raise_x, GamePU.fcr_y,
                GamePU.fcr_w, GamePU.fcr_h);
        card1.setBounds(GamePU.card_x, GamePU.card_y,
                GamePU.card_w, GamePU.card_h);
        card2.setBounds(GamePU.card_x + 120, GamePU.card_y,
                GamePU.card_w, GamePU.card_h);
        card3.setBounds(GamePU.card_x + 240, GamePU.card_y,
                GamePU.card_w, GamePU.card_h);
        card4.setBounds(GamePU.card_x + 360, GamePU.card_y,
                GamePU.card_w, GamePU.card_h);
        card5.setBounds(GamePU.card_x + 480, GamePU.card_y,
                GamePU.card_w, GamePU.card_h);


        //Add components
        for (int j = 0; j < allPlayers.size(); j++) {
            this.add(playersP[j]);
        }

//        callBut.setEnabled(false);
//        foldBut.setEnabled(false);
//        raiseBut.setEnabled(false);

        this.add(back);
        this.add(foldBut);
        this.add(callBut);
        this.add(raiseBut);
        this.add(card1);
        this.add(card2);
        this.add(card3);
        this.add(card4);
        this.add(card5);

    }

    //Draw panel image
    public void paintComponent(Graphics g) {
        g.drawImage(backGround, 0, 0, null);
    }

    public CustBut getBackBut() {
        return back;
    }

    public CustBut getFoldBut() {
        return foldBut;
    }

    public CustBut getCallBut() {
        return callBut;
    }

    public CustBut getRaiseBut() {
        return raiseBut;
    }

    public void setYourTurn(boolean myTurn){
        if (myTurn){
            callBut.setEnabled(true);
            foldBut.setEnabled(true);
            raiseBut.setEnabled(true);
        } else {
            callBut.setEnabled(false);
            foldBut.setEnabled(false);
            raiseBut.setEnabled(false);
        }
    }

    public PlayerPanel[] getPlayersP() {
        return playersP;
    }

    public void setCards() {

        String path1, path2, path3, path4, path5;


        StringTokenizer tokenizer = new StringTokenizer(f.getCommuCards(), "-");
        path1 = PlayerPU.pathPrefix + tokenizer.nextToken() + ".png";
        path2 = PlayerPU.pathPrefix + tokenizer.nextToken() + ".png";
        path3 = PlayerPU.pathPrefix + tokenizer.nextToken() + ".png";
        path4 = PlayerPU.pathPrefix + tokenizer.nextToken() + ".png";
        path5 = PlayerPU.pathPrefix + tokenizer.nextToken() + ".png";


        Image img1 = ImageGetter.getInstance().getImage(path1);
        Image img2 = ImageGetter.getInstance().getImage(path2);
        Image img3 = ImageGetter.getInstance().getImage(path3);
        Image img4 = ImageGetter.getInstance().getImage(path4);
        Image img5 = ImageGetter.getInstance().getImage(path5);


        if (img1 != null && img2 != null && img3 != null && img4 != null && img5 != null) {
            ImageIcon imageIcon;
            imageIcon = new ImageIcon(img1);
            card1.setIcon(imageIcon);
            imageIcon = new ImageIcon(img2);
            card2.setIcon(imageIcon);
            imageIcon = new ImageIcon(img3);
            card3.setIcon(imageIcon);
            imageIcon = new ImageIcon(img4);
            card4.setIcon(imageIcon);
            imageIcon = new ImageIcon(img5);
            card5.setIcon(imageIcon);
        }
    }
}
