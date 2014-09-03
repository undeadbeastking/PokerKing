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

    private MainFrame f;
    //Background image
    private Image backGround;

    //4 - 9 player mini panels
    private ArrayList<PlayerPanel> playerPanels;

    //This label tells which state of Bet round players are in
    private CustBut betRoundLabel = new CustBut("Start a game");

    //Pot, small blind, big blind
    private JLabel potLabel = new JLabel("Pot: $" + 150);
    private JLabel smallBlind = new JLabel("Small Blind: $50");
    private JLabel bigBlind = new JLabel("Big Blind: $100");

    //5 Community cards
    private JLabel ComCard1 = new JLabel();
    private JLabel ComCard2 = new JLabel();
    private JLabel ComCard3 = new JLabel();
    private JLabel ComCard4 = new JLabel();
    private JLabel ComCard5 = new JLabel();

    //Bet Function buttons
    private CustBut foldBut = new CustBut("Fold");
    private CustBut callBut = new CustBut("Call");
    private CustBut raiseBut = new CustBut("Raise");

    //Money Betting UI
    private CustBut decreaseMonButton = new CustBut("decrease");
    private CustBut increaseMonButton = new CustBut("increase");
    private JLabel betAmountLabel = new JLabel("$100");

    public GamePanel(MainFrame frame) {
        //Customize Game Panel
        this.f = frame;
        this.backGround = ImageGetter.getInstance().getImage(GamePU.backGround);
        setLayout(null);

        //Get community cards from server and put them into Labels, set Invisible -> Waiting to be called
        setCommunityCards();

        //Create all players panels
        playerPanels = new ArrayList<PlayerPanel>();

        //Local cast username list
        ArrayList<String> localAllUsernames;
        localAllUsernames = f.getAllUsernames();
        for (int j = 0; j < localAllUsernames.size(); j++) {
            playerPanels.add(new PlayerPanel(j, f));
        }

        //Set bounds for components
        foldBut.setBounds(GamePU.fold_x, GamePU.fcr_y, GamePU.fcr_w, GamePU.fcr_h);
        callBut.setBounds(GamePU.call_x, GamePU.fcr_y, GamePU.fcr_w, GamePU.fcr_h);
        raiseBut.setBounds(GamePU.raise_x, GamePU.fcr_y, GamePU.fcr_w, GamePU.fcr_h);
        decreaseMonButton.setBounds(GamePU.betDecrease_x, GamePU.betGear_y, GamePU.betGear_w, GamePU.betGear_h);
        increaseMonButton.setBounds(GamePU.betIncrease_x, GamePU.betGear_y, GamePU.betGear_w, GamePU.betGear_h);
        betAmountLabel.setBounds(GamePU.betAmount_x, GamePU.betGear_y, GamePU.betAmount_w, GamePU.betAmount_h);

        //Community cards bound
        ComCard1.setBounds(GamePU.CommuCard_x, GamePU.CommuCard_y, GamePU.CommuCard_w, GamePU.CommuCard_h);
        ComCard2.setBounds(GamePU.CommuCard_x + 120, GamePU.CommuCard_y, GamePU.CommuCard_w, GamePU.CommuCard_h);
        ComCard3.setBounds(GamePU.CommuCard_x + 240, GamePU.CommuCard_y, GamePU.CommuCard_w, GamePU.CommuCard_h);
        ComCard4.setBounds(GamePU.CommuCard_x + 360, GamePU.CommuCard_y, GamePU.CommuCard_w, GamePU.CommuCard_h);
        ComCard5.setBounds(GamePU.CommuCard_x + 480, GamePU.CommuCard_y, GamePU.CommuCard_w, GamePU.CommuCard_h);

        //Custom Bet round info label and set Bound
        betRoundLabel.setBounds(GamePU.betRound_x, GamePU.betRound_y, GamePU.betRound_w, GamePU.betRound_h);
        betRoundLabel.setFont(GamePU.betRoundFont);

        //Pot, small blind, big blind Label Font, Color and Bound
        //Font
        potLabel.setFont(GamePU.potFont);
        smallBlind.setFont(GamePU.blindFont);
        bigBlind.setFont(GamePU.blindFont);
        //Bound
        potLabel.setBounds(GamePU.pot_x, GamePU.pot_y, GamePU.pot_blind_w, GamePU.pot_blind_h);
        smallBlind.setBounds(GamePU.blind_x, GamePU.smallBlind_y, GamePU.pot_blind_w, GamePU.pot_blind_h);
        bigBlind.setBounds(GamePU.blind_x, GamePU.bigBlind_y, GamePU.pot_blind_w, GamePU.pot_blind_h);
        //Set font color
        potLabel.setForeground(Color.GREEN);
        smallBlind.setForeground(Color.GREEN);
        bigBlind.setForeground(Color.GREEN);

        //Bet amount Custom UI
        betAmountLabel.setBackground(Color.BLACK);
        betAmountLabel.setForeground(Color.WHITE);
        betAmountLabel.setOpaque(true);
        betAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Add components
        //PlayerPanels
        for (int j = 0; j < playerPanels.size(); j++) {
            this.add(playerPanels.get(j));
        }

        this.add(foldBut);
        this.add(callBut);
        this.add(raiseBut);

        this.add(ComCard1);
        this.add(ComCard2);
        this.add(ComCard3);
        this.add(ComCard4);
        this.add(ComCard5);

        this.add(betRoundLabel);

        this.add(potLabel);
        this.add(smallBlind);
        this.add(bigBlind);

        this.add(increaseMonButton);
        this.add(decreaseMonButton);
        this.add(betAmountLabel);

        //Disable buttons
        callBut.setEnabled(false);
        foldBut.setEnabled(false);
        raiseBut.setEnabled(false);
        increaseMonButton.setEnabled(false);
        decreaseMonButton.setEnabled(false);
    }

    public void setTurn(boolean isMyTurn, String currentTurnUsername) {
        if (isMyTurn) {
            foldBut.setEnabled(true);

            //Calculate if the player have enough money?
            int amountToAdd = f.getCurrentHighestBet() - f.getMyBet();
            int moneyRemain = f.getMyMoney() - amountToAdd;

            //Reset button to Call
            callBut.setText("Call");

            //Check
            if(amountToAdd == 0){
                increaseMonButton.setEnabled(true);
                decreaseMonButton.setEnabled(true);
                callBut.setEnabled(true);
                raiseBut.setEnabled(false);

                //Set button to check
                callBut.setText("Check");

                f.setMoneyToAdd(0);

            } else {
                //Can only All in or Fold
                if(moneyRemain <= 0){
                    increaseMonButton.setEnabled(false);
                    decreaseMonButton.setEnabled(false);
                    callBut.setEnabled(false);

                    //All in button
                    raiseBut.setEnabled(true);
                    raiseBut.setText("All in");

                    //Set All in: Old bet and the remaining cash
                    f.setMyBet(f.getMyBet() + f.getMyMoney());
                    f.setMoneyToAdd(f.getMyMoney());

                    //No cash left
                    f.setMyMoney(0);

                    //Set them to UI
                    f.getGamePanel().getPlayerPanels().get(f.getMyIndex()).getRemainCash().setText("Cash: $" + f.getMyMoney());
                    f.getGamePanel().getBetAmountLabel().setText("$" + f.getMyBet());

                //Call or Raise
                } else {
                    increaseMonButton.setEnabled(true);
                    decreaseMonButton.setEnabled(true);
                    callBut.setEnabled(true);
                    raiseBut.setEnabled(false);

                    //Add to old bet amount
                    f.setMyBet(f.getMyBet() + amountToAdd);
                    f.setMoneyToAdd(amountToAdd);

                    //myMoney - amountToAdd
                    f.setMyMoney(moneyRemain);

                    //Set them to UI
                    f.getGamePanel().getPlayerPanels().get(f.getMyIndex()).getRemainCash().setText("Cash: $" + f.getMyMoney());
                    f.getGamePanel().getBetAmountLabel().setText("$" + f.getMyBet());
                }
            }

        //Not my Turn
        } else {
            callBut.setEnabled(false);
            foldBut.setEnabled(false);
            raiseBut.setEnabled(false);
            increaseMonButton.setEnabled(false);
            decreaseMonButton.setEnabled(false);
        }

        //Highlight the current turn panel, Others will have red highlight as waiting Status
        for (int i = 0; i < playerPanels.size(); i++) {
            if (currentTurnUsername.equals(playerPanels.get(i).getUsername())) {
                playerPanels.get(i).highlightCurrentPlayerTurn();
            } else {
                playerPanels.get(i).waitingStatus();
            }
        }
    }

    public void processResponseFromOtherPlayer(String name, String response) {

        StringTokenizer tokenizer = new StringTokenizer(response, "/");

        String status = tokenizer.nextToken();
        String pot = tokenizer.nextToken();
        String playerMoney = tokenizer.nextToken();

        //Set that player status, money
        for (int i = 0; i < playerPanels.size(); i++) {
            if (name.equals(playerPanels.get(i).getUsername())) {
                playerPanels.get(i).setStatus(status);
                playerPanels.get(i).getRemainCash().setText("Cash: $" + playerMoney);
            }
        }

        //Set Pot
        potLabel.setText("Pot: $" + pot);

        //Set current highestRaise
        if(response.startsWith("Raise")) {
            StringTokenizer bet = new StringTokenizer(status, "$");
            bet.nextToken();//ignore String "Raise"

            f.setCurrentHighestBet(Integer.valueOf(bet.nextToken()));
        }
    }

    public void setCommunityCards() {

        String path1, path2, path3, path4, path5;

        StringTokenizer tokenizer = new StringTokenizer(f.getCommuCards(), "-");
        path1 = PlayerPU.cardsPathPrefix + tokenizer.nextToken() + ".png";
        path2 = PlayerPU.cardsPathPrefix + tokenizer.nextToken() + ".png";
        path3 = PlayerPU.cardsPathPrefix + tokenizer.nextToken() + ".png";
        path4 = PlayerPU.cardsPathPrefix + tokenizer.nextToken() + ".png";
        path5 = PlayerPU.cardsPathPrefix + tokenizer.nextToken() + ".png";

        Image img1 = ImageGetter.getInstance().getImage(path1);
        Image img2 = ImageGetter.getInstance().getImage(path2);
        Image img3 = ImageGetter.getInstance().getImage(path3);
        Image img4 = ImageGetter.getInstance().getImage(path4);
        Image img5 = ImageGetter.getInstance().getImage(path5);

        ImageIcon imageIcon;
        if (img1 != null && img2 != null && img3 != null && img4 != null && img5 != null) {
            imageIcon = new ImageIcon(img1);
            ComCard1.setIcon(imageIcon);
            imageIcon = new ImageIcon(img2);
            ComCard2.setIcon(imageIcon);
            imageIcon = new ImageIcon(img3);
            ComCard3.setIcon(imageIcon);
            imageIcon = new ImageIcon(img4);
            ComCard4.setIcon(imageIcon);
            imageIcon = new ImageIcon(img5);
            ComCard5.setIcon(imageIcon);
        }

        //Hide all 5 Community cards, waiting for the right bet round to Display
        ComCard1.setVisible(false);
        ComCard2.setVisible(false);
        ComCard3.setVisible(false);
        ComCard4.setVisible(false);
        ComCard5.setVisible(false);
    }

    //Draw panel image
    public void paintComponent(Graphics g) {
        g.drawImage(backGround, 0, 0, null);
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
        return ComCard1;
    }

    public JLabel getComCard2() {
        return ComCard2;
    }

    public JLabel getComCard3() {
        return ComCard3;
    }

    public JLabel getComCard4() {
        return ComCard4;
    }

    public JLabel getComCard5() {
        return ComCard5;
    }

    public JLabel getBetAmountLabel() {
        return betAmountLabel;
    }

    public CustBut getIncreaseMonButton() {
        return increaseMonButton;
    }

    public CustBut getDecreaseMonButton() {
        return decreaseMonButton;
    }

    public JLabel getPotLabel() {
        return potLabel;
    }

}
