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

    private int maxPlayersNum = 9;
    private ArrayList<String> usernames;
    private ArrayList<PlayerPanel> playerPanels;

    private MainFrame f;
    private Image backGround;
    private CustBut betRound = new CustBut("Start a game");
    private CustBut back = new CustBut("back");
    private JLabel potLabel = new JLabel("Pot: $" + 150);
    private JLabel smallBlind = new JLabel("Small Blind: 50$");
    private JLabel bigBlind = new JLabel("Big Blind: 100$");

    //5 Community cards
    private JLabel card1 = new JLabel();
    private JLabel card2 = new JLabel();
    private JLabel card3 = new JLabel();
    private JLabel card4 = new JLabel();
    private JLabel card5 = new JLabel();

    //Function buttons
    private CustBut foldBut = new CustBut("Fold");
    private CustBut callBut = new CustBut("Call");
    private CustBut raiseBut = new CustBut("Raise");

    //Money Betting UI
    private CustBut decreaseMon = new CustBut("decrease");
    private CustBut increaseMon = new CustBut("increase");
    private JLabel betAmount = new JLabel("$100");

    public GamePanel(MainFrame frame) {
        //Customize Game Panel
        this.backGround = ImageGetter.getInstance().getImage(GamePU.backGround);
        this.f = frame;
        setLayout(null);
        setCommunityCards();

        //Create all players panels - real player will be added
        playerPanels = new ArrayList<PlayerPanel>();
        usernames = f.getUsernames();
        for (int j = 0; j < usernames.size(); j++) {
            playerPanels.add(new PlayerPanel(j, f));
        }

        //Set bounds for components - fcr = Fall Call Raise
        back.setBounds(GamePU.backBut_x, GamePU.backBut_y, GamePU.backBut_w, GamePU.backBut_h);
        foldBut.setBounds(GamePU.fold_x, GamePU.fcr_y, GamePU.fcr_w, GamePU.fcr_h);
        callBut.setBounds(GamePU.call_x, GamePU.fcr_y, GamePU.fcr_w, GamePU.fcr_h);
        raiseBut.setBounds(GamePU.raise_x, GamePU.fcr_y, GamePU.fcr_w, GamePU.fcr_h);
        decreaseMon.setBounds(GamePU.betDecrease_x, GamePU.bet_y, GamePU.bet_w, GamePU.bet_h);
        increaseMon.setBounds(GamePU.betIncrease_x, GamePU.bet_y, GamePU.bet_w, GamePU.bet_h);
        betAmount.setBounds(GamePU.betAmount_x, GamePU.bet_y, GamePU.betAmount_w, GamePU.betAmount_h);

        //Community cards
        card1.setBounds(GamePU.card_x, GamePU.card_y, GamePU.card_w, GamePU.card_h);
        card2.setBounds(GamePU.card_x + 120, GamePU.card_y, GamePU.card_w, GamePU.card_h);
        card3.setBounds(GamePU.card_x + 240, GamePU.card_y, GamePU.card_w, GamePU.card_h);
        card4.setBounds(GamePU.card_x + 360, GamePU.card_y, GamePU.card_w, GamePU.card_h);
        card5.setBounds(GamePU.card_x + 480, GamePU.card_y, GamePU.card_w, GamePU.card_h);

        //Custom Bet round info and set Bound
        betRound.setBounds(GamePU.betRound_x, GamePU.betRound_y, GamePU.betRound_w, GamePU.betRound_h);
        betRound.setFont(GamePU.betRoundFont);

        //Pot Label Font, Color and Bound
        potLabel.setFont(GamePU.potFont);
        smallBlind.setFont(GamePU.blindFont);
        bigBlind.setFont(GamePU.blindFont);
        potLabel.setBounds(GamePU.pot_x, GamePU.pot_y, GamePU.pot_w, GamePU.pot_h);
        smallBlind.setBounds(GamePU.blind_x, GamePU.smallBlind_y, GamePU.pot_w, GamePU.pot_h);
        bigBlind.setBounds(GamePU.blind_x, GamePU.bigBlind_y, GamePU.pot_w, GamePU.pot_h);
        potLabel.setForeground(Color.GREEN);
        smallBlind.setForeground(Color.GREEN);
        bigBlind.setForeground(Color.GREEN);

        //Bet amount
        betAmount.setBackground(Color.white);
        betAmount.setForeground(Color.BLACK);
        betAmount.setOpaque(true);
        betAmount.setHorizontalAlignment(SwingConstants.CENTER);

        //Add components - PlayerPanels
        for (int j = 0; j < playerPanels.size(); j++) {
            this.add(playerPanels.get(j));
        }
        this.add(back);
        this.add(foldBut);
        this.add(callBut);
        this.add(raiseBut);
        this.add(card1);
        this.add(card2);
        this.add(card3);
        this.add(card4);
        this.add(card5);
        this.add(betRound);
        this.add(potLabel);
        this.add(increaseMon);
        this.add(decreaseMon);
        this.add(betAmount);
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

        //Set current highestRaise
        if(response.startsWith("Raise")) {
            StringTokenizer bet = new StringTokenizer(response, "$");
            bet.nextToken();
            f.setCurrentHighestBet(Integer.valueOf(bet.nextToken()));
            System.out.println(f.getCurrentHighestBet());
        }

        betAmount.setText("$" + f.getCurrentHighestBet());
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

        //Hide all 5 Community cards
        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card4.setVisible(false);
        card5.setVisible(false);
    }

    public ArrayList<PlayerPanel> getPlayerPanels() {
        return playerPanels;
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

    public CustBut getBetRound() {
        return betRound;
    }

    public JLabel getCard1() {
        return card1;
    }

    public JLabel getCard2() {
        return card2;
    }

    public JLabel getCard3() {
        return card3;
    }

    public JLabel getCard4() {
        return card4;
    }

    public JLabel getCard5() {
        return card5;
    }

    public JLabel getBetAmount() {
        return betAmount;
    }

    public CustBut getIncreaseMon() {
        return increaseMon;
    }

    public CustBut getDecreaseMon() {
        return decreaseMon;
    }

    public JLabel getPotLabel() {
        return potLabel;
    }
}
