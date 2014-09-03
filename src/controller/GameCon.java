package controller;

import view.MainFrame;
import view.PlayerPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

/**
 * Created by Agatha of Wood Beyond on 7/4/2014.
 */
public class GameCon {

    private MainFrame f;
    private int increaseDecreaseUnit = 10;

    public GameCon(MainFrame frame) {
        this.f = frame;

        //Set ActionListener for back button
        this.f.getGamePanel().getFoldBut().addActionListener(new FoldButtonListener());
        this.f.getGamePanel().getCallBut().addActionListener(new CallButtonListener());
        this.f.getGamePanel().getRaiseBut().addActionListener(new RaiseButtonListener());

        //Increase and decrease Money
        this.f.getGamePanel().getDecreaseMonButton().addActionListener(new DecreaseMoneyListener());
        this.f.getGamePanel().getIncreaseMonButton().addActionListener(new IncreaseMoneyListener());
    }

    private class FoldButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            f.getServer().write(-1);
        }
    }

    private class CallButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            f.getServer().write(f.getMoneyToAdd());
        }
    }

    private class RaiseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            f.getServer().write(f.getMoneyToAdd());
        }
    }

    private class IncreaseMoneyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            PlayerPanel myPlayerPanel = f.getGamePanel().getPlayerPanels().get(f.getMyIndex());

            //Still enough money then raise
            if (f.getMyMoney() >= increaseDecreaseUnit) {
                //Update Data
                f.setMyBet(f.getMyBet() + increaseDecreaseUnit);
                f.setMoneyToAdd(f.getMoneyToAdd() + increaseDecreaseUnit);
                f.setMyMoney(f.getMyMoney() - increaseDecreaseUnit);

                //Update UI
                f.getGamePanel().getBetAmountLabel().setText("$" + f.getMyBet());
                myPlayerPanel.getRemainCash().setText("Cash: $" + f.getMyMoney());

                //After subtracting, if playerMoney is 0 -> Switch to All in Button
                if(f.getMyMoney() == 0){
                    f.getGamePanel().getRaiseBut().setText("All In");
                }
            }

            //Decide to raise then cannot Call
            f.getGamePanel().getCallBut().setEnabled(false);
            f.getGamePanel().getRaiseBut().setEnabled(true);
        }
    }

    private class DecreaseMoneyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PlayerPanel myPlayerPanel = f.getGamePanel().getPlayerPanels().get(f.getMyIndex());

            //if Raise button is all in -> set back to Raise
            f.getGamePanel().getRaiseBut().setText("Raise");

            //Decrease until it equals to current highest Raise
            if (f.getMyBet() >= f.getCurrentHighestBet() + increaseDecreaseUnit) {
                //Update Data
                f.setMyBet(f.getMyBet() - increaseDecreaseUnit);
                f.setMoneyToAdd(f.getMoneyToAdd() - increaseDecreaseUnit);
                f.setMyMoney(f.getMyMoney() + increaseDecreaseUnit);

                //Update UI
                f.getGamePanel().getBetAmountLabel().setText("$" + f.getMyBet());
                myPlayerPanel.getRemainCash().setText("Cash: $" + f.getMyMoney());

                //After subtracting, my Bet == CurrentHighestBet, disable buttons
                if(f.getMyBet() == f.getCurrentHighestBet()){
                    //Decide to call then cannot Raise
                    f.getGamePanel().getRaiseBut().setEnabled(false);
                    f.getGamePanel().getCallBut().setEnabled(true);
                }
            }
        }
    }
}
