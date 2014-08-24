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
        this.f.getGamePanel().getBackBut().addActionListener(new BackButtonListener());
        this.f.getGamePanel().getFoldBut().addActionListener(new FoldButtonListener());
        this.f.getGamePanel().getCallBut().addActionListener(new CallButtonListener());
        this.f.getGamePanel().getRaiseBut().addActionListener(new RaiseButtonListener());
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
            f.getServer().write("Fold");
            f.getGamePanel().setYourTurn(false);
        }

    }

    private class CallButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Call");
            f.getServer().write("Call");
            f.getGamePanel().setYourTurn(false);
        }

    }

    private class RaiseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Raise");
            f.getServer().write("Raise");
            f.getGamePanel().setYourTurn(false);
        }

    }
}
