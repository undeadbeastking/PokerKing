package view;

import CustomUI.CustBut;
import images.ImageGetter;
import Utils.GamePU;
import Utils.PlayerPU;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Agatha of Wood Beyond on 7/4/2014.
 */
public class GamePanel extends JPanel {

    private ArrayList<PlayerPanel> playerPanels;

    private MainFrame f;
    private Image backGround;
    private CustBut betRoundLabel = new CustBut("Start a game");

    private JLabel potLabel = new JLabel("Pot: $" + 150);
    private JLabel smallBlind = new JLabel("Small Blind: $50");
    private JLabel bigBlind = new JLabel("Big Blind: $100");

    //5 Community cards
    private JLabel comCard1 = new JLabel();
    private JLabel comCard2 = new JLabel();
    private JLabel comCard3 = new JLabel();
    private JLabel comCard4 = new JLabel();
    private JLabel comCard5 = new JLabel();

    //Function buttons
    private CustBut foldBut = new CustBut("Fold");
    private CustBut callBut = new CustBut("Call");
    private CustBut raiseBut = new CustBut("Raise");

    //Money Betting UI
    private CustBut decreaseMonBut = new CustBut("decrease");
    private CustBut increaseMonBut = new CustBut("increase");
    private JLabel betAmountLabel = new JLabel("$100");

    public GamePanel(MainFrame frame) {

        this.f = frame;
        setLayout(null);

        //Customize Game Panel
        this.backGround = ImageGetter.getInstance().getImage(GamePU.backGround);

        setCommunityCards();

        //Create all players panels - real player will be added
        playerPanels = new ArrayList<PlayerPanel>();

        //Get username list from MainFrame
        ArrayList<String> usernames = f.getUsernames();
        for (int j = 0; j < usernames.size(); j++) {
            playerPanels.add(new PlayerPanel(j, f));
        }

        //Set bounds for components - fcr = Fall Call Raise
        foldBut.setBounds(GamePU.fold_x, GamePU.fcr_y, GamePU.fcr_w, GamePU.fcr_h);
        callBut.setBounds(GamePU.call_x, GamePU.fcr_y, GamePU.fcr_w, GamePU.fcr_h);
        raiseBut.setBounds(GamePU.raise_x, GamePU.fcr_y, GamePU.fcr_w, GamePU.fcr_h);
        decreaseMonBut.setBounds(GamePU.betDecrease_x, GamePU.betGear_y, GamePU.betGear_w, GamePU.betGear_h);
        increaseMonBut.setBounds(GamePU.betIncrease_x, GamePU.betGear_y, GamePU.betGear_w, GamePU.betGear_h);
        betAmountLabel.setBounds(GamePU.betAmount_x, GamePU.betGear_y, GamePU.betAmount_w, GamePU.betAmount_h);

        //Community cards
        comCard1.setBounds(GamePU.comCard_x, GamePU.comCard_y, GamePU.comCard_w, GamePU.comCard_h);
        comCard2.setBounds(GamePU.comCard_x + 120, GamePU.comCard_y, GamePU.comCard_w, GamePU.comCard_h);
        comCard3.setBounds(GamePU.comCard_x + 240, GamePU.comCard_y, GamePU.comCard_w, GamePU.comCard_h);
        comCard4.setBounds(GamePU.comCard_x + 360, GamePU.comCard_y, GamePU.comCard_w, GamePU.comCard_h);
        comCard5.setBounds(GamePU.comCard_x + 480, GamePU.comCard_y, GamePU.comCard_w, GamePU.comCard_h);

        //Custom Bet round info and set Bound
        betRoundLabel.setBounds(GamePU.betRoundLabel_x, GamePU.betRoundLabel_y, GamePU.betRoundLabel_w, GamePU.betRoundLabel_h);
        betRoundLabel.setFont(GamePU.betRoundLabelFont);

        //Pot Label Font, Color and Bound
        potLabel.setFont(GamePU.potFont);
        smallBlind.setFont(GamePU.blindFont);
        bigBlind.setFont(GamePU.blindFont);
        potLabel.setBounds(GamePU.pot_x, GamePU.pot_y, GamePU.pot_blind_w, GamePU.pot_blind_h);
        smallBlind.setBounds(GamePU.blind_x, GamePU.smallBlind_y, GamePU.pot_blind_w, GamePU.pot_blind_h);
        bigBlind.setBounds(GamePU.blind_x, GamePU.bigBlind_y, GamePU.pot_blind_w, GamePU.pot_blind_h);
        potLabel.setForeground(Color.GREEN);
        smallBlind.setForeground(Color.GREEN);
        bigBlind.setForeground(Color.GREEN);

        //Bet amount
        betAmountLabel.setBackground(Color.white);
        betAmountLabel.setForeground(Color.BLACK);
        betAmountLabel.setOpaque(true);
        betAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Add components - PlayerPanels
        for (int j = 0; j < playerPanels.size(); j++) {
            this.add(playerPanels.get(j));
        }
//        this.add(back);
        this.add(foldBut);
        this.add(callBut);
        this.add(raiseBut);
        this.add(comCard1);
        this.add(comCard2);
        this.add(comCard3);
        this.add(comCard4);
        this.add(comCard5);
        this.add(betRoundLabel);
        this.add(potLabel);
        this.add(increaseMonBut);
        this.add(decreaseMonBut);
        this.add(betAmountLabel);
        this.add(smallBlind);
        this.add(bigBlind);

        //Disable buttons
        callBut.setEnabled(false);
        foldBut.setEnabled(false);
        raiseBut.setEnabled(false);
    }

    //Draw panel image
    public void paintComponent(Graphics g) {
        g.drawImage(backGround, 0, 0, null);
    }

    public void setTurn(boolean isMyTurn, String currentTurnUsername) {
        if (isMyTurn) {
            callBut.setEnabled(true);
            foldBut.setEnabled(true);
            raiseBut.setEnabled(true);
        } else {
            callBut.setEnabled(false);
            foldBut.setEnabled(false);
            raiseBut.setEnabled(false);
        }

        for (int i = 0; i < playerPanels.size(); i++) {
            if (currentTurnUsername.equals(playerPanels.get(i).getUsername())) {
                playerPanels.get(i).highlightMyTurn();
            } else {
                playerPanels.get(i).highlightOtherTurn();
            }
        }
    }

    public void processResponse(String name, String response) {
        for (int i = 0; i < playerPanels.size(); i++) {
            if (name.equals(playerPanels.get(i).getUsername())) {
                playerPanels.get(i).setStatus(response);
            }
        }
    }

    public void setCommunityCards() {

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

        ImageIcon imageIcon;
        if (img1 != null && img2 != null && img3 != null && img4 != null && img5 != null) {

            imageIcon = new ImageIcon(img1);
            comCard1.setIcon(imageIcon);
            imageIcon = new ImageIcon(img2);
            comCard2.setIcon(imageIcon);
            imageIcon = new ImageIcon(img3);
            comCard3.setIcon(imageIcon);
            imageIcon = new ImageIcon(img4);
            comCard4.setIcon(imageIcon);
            imageIcon = new ImageIcon(img5);
            comCard5.setIcon(imageIcon);
        }

        //Hide all 5 Community cards
        comCard1.setVisible(false);
        comCard2.setVisible(false);
        comCard3.setVisible(false);
        comCard4.setVisible(false);
        comCard5.setVisible(false);
    }

    public ArrayList<PlayerPanel> getPlayerPanels() {
        return playerPanels;
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

    public CustBut getBetRoundLabel() {
        return betRoundLabel;
    }

    public JLabel getComCard1() {
        return comCard1;
    }

    public JLabel getComCard2() {
        return comCard2;
    }

    public JLabel getComCard3() {
        return comCard3;
    }

    public JLabel getComCard4() {
        return comCard4;
    }

    public JLabel getComCard5() {
        return comCard5;
    }
}
