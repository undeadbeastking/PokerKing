package controller;

import view.MainFrame;
import view.Utils;

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
        this.f.getGamePanel().getBack().addActionListener(new BackButtonListener());
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Replace Game Panel with Login Panel
            f.remove(f.getGamePanel());
            f.add(f.getLoginPanel());
            //Set suitable size for the frame and relocate it to center
            f.setSize(Utils.loginPanel_width, Utils.loginPanel_height);
            f.setLocationRelativeTo(null);
            //Refresh the MainFrame
            f.validate();
            f.repaint();
        }
    }
}
