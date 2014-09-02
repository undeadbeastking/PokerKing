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
    private int moneyRaiseAndFall = 10;

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
            System.out.println("Fold");
            f.getServer().write(-1);
        }
    }

    private class CallButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Call");
            StringTokenizer tokenizer = new StringTokenizer(f.getGamePanel().getBetAmountLabel().getText(), "$");
            int currentBetAmount = Integer.valueOf(tokenizer.nextToken());
            f.getServer().write(currentBetAmount);
        }
    }

    private class RaiseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Raise or All in");
            StringTokenizer tokenizer = new StringTokenizer(f.getGamePanel().getBetAmountLabel().getText(), "$");
            int bet = Integer.valueOf(tokenizer.nextToken());
            f.getServer().write(bet);
        }
    }

    private class IncreaseMoneyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringTokenizer tokenizer = new StringTokenizer(f.getGamePanel().getBetAmountLabel().getText(), "$");
            int bet = Integer.valueOf(tokenizer.nextToken());
            PlayerPanel myPlayerPanel = f.getGamePanel().getPlayerPanels().get(f.getMyIndex());

            //Still enough money then raise
            if (f.getMyMoney() >= moneyRaiseAndFall) {
                f.getGamePanel().getBetAmountLabel().setText("$" + (bet + moneyRaiseAndFall));

                //My cash decrease
                f.setMyMoney(f.getMyMoney() - moneyRaiseAndFall);
                myPlayerPanel.getRemainCash().setText("Cash: $" + f.getMyMoney());

                //Switch to All in Button
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
            StringTokenizer tokenizer = new StringTokenizer(f.getGamePanel().getBetAmountLabel().getText(), "$");
            int bet = Integer.valueOf(tokenizer.nextToken());
            PlayerPanel myPlayerPanel = f.getGamePanel().getPlayerPanels().get(f.getMyIndex());

            //Decrease until it equals to current highest Raise
            if (bet >= f.getCurrentHighestBet() + moneyRaiseAndFall) {
                f.getGamePanel().getBetAmountLabel().setText("$" + (bet - moneyRaiseAndFall));

                //My cash increase
                f.setMyMoney(f.getMyMoney() + moneyRaiseAndFall);
                myPlayerPanel.getRemainCash().setText("Cash: $" + f.getMyMoney());

                if(bet == f.getCurrentHighestBet() + moneyRaiseAndFall){
                    //Decide to call then cannot Raise
                    f.getGamePanel().getRaiseBut().setEnabled(false);
                    f.getGamePanel().getCallBut().setEnabled(true);
                }

                //If all in status then change back to Raise
                f.getGamePanel().getRaiseBut().setText("Raise");
            }
        }
    }
}
