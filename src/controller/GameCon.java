package controller;

import view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

/**
 * Created by Agatha of Wood Beyond on 7/4/2014.
 */
public class GameCon {

    private MainFrame f;

    public GameCon(MainFrame frame){
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
            System.out.println("Raise");
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

            //Still enough money then raise
            if(bet < f.getMyMoney()){
                f.getGamePanel().getBetAmountLabel().setText("$"+(bet+10));

            } else {
                //Raise equal or higher than My Money means All IN
                bet = f.getMyMoney();
                f.getGamePanel().getBetAmountLabel().setText("$"+bet);
            }

            //Decide to raise then cannot Call
            f.getGamePanel().getCallBut().setEnabled(false);
        }
    }

    private class DecreaseMoneyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringTokenizer tokenizer = new StringTokenizer(f.getGamePanel().getBetAmountLabel().getText(), "$");
            int bet = Integer.valueOf(tokenizer.nextToken());

            //Decrease until it equals to current highest Raise
            if(bet > f.getCurrentHighestBet()){
                f.getGamePanel().getBetAmountLabel().setText("$"+(bet-10));
            } else {
                f.getGamePanel().getBetAmountLabel().setText("$"+f.getCurrentHighestBet());
                //Decide to call then cannot Raise
                f.getGamePanel().getRaiseBut().setEnabled(false);
            }
        }
    }
}
