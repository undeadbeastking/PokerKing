package controller;

import view.MainFrame;
import Utils.LoginPU;

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
        this.f.getGamePanel().getBackBut().addActionListener(new BackButtonListener());
        this.f.getGamePanel().getFoldBut().addActionListener(new FoldButtonListener());
        this.f.getGamePanel().getCallBut().addActionListener(new CallButtonListener());
        this.f.getGamePanel().getRaiseBut().addActionListener(new RaiseButtonListener());

        //Increase and decrease Money
        this.f.getGamePanel().getDecreaseMon().addActionListener(new DecreaseMoneyListener());
        this.f.getGamePanel().getIncreaseMon().addActionListener(new IncreaseMoneyListener());
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Replace Game Panel with Login Panel
            f.remove(f.getGamePanel());
            f.add(f.getLoginPanel());
            //Set suitable size for the frame and relocate it to center
            f.setSize(LoginPU.width, LoginPU.height);
            f.setLocationRelativeTo(null);
            //Refresh the MainFrame
            f.validate();
            f.repaint();
        }
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
            StringTokenizer tokenizer = new StringTokenizer(f.getGamePanel().getBetAmount().getText(), "$");
            int bet = Integer.valueOf(tokenizer.nextToken());
            f.getServer().write(bet);
        }
    }

    private class RaiseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Raise");
            StringTokenizer tokenizer = new StringTokenizer(f.getGamePanel().getBetAmount().getText(), "$");
            int bet = Integer.valueOf(tokenizer.nextToken());
            f.getServer().write(bet);
        }
    }

    private class IncreaseMoneyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringTokenizer tokenizer = new StringTokenizer(f.getGamePanel().getBetAmount().getText(), "$");
            int bet = Integer.valueOf(tokenizer.nextToken());
            if(bet < f.getMyMoney()){
                f.getGamePanel().getBetAmount().setText("$"+(bet+10));
            }
        }
    }

    private class DecreaseMoneyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringTokenizer tokenizer = new StringTokenizer(f.getGamePanel().getBetAmount().getText(), "$");
            int bet = Integer.valueOf(tokenizer.nextToken());
            if(bet > f.getCurrentHighestBet()){
                f.getGamePanel().getBetAmount().setText("$"+(bet-10));

            } else {
                f.getGamePanel().getBetAmount().setText("$"+f.getCurrentHighestBet());
            }
        }
    }
}
