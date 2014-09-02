package controller;

import view.MainFrame;
import Utils.LoginPU;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            f.getServer().write("Call");
        }
    }

    private class RaiseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Raise");
            f.getServer().write("Raise");
        }
    }
}
