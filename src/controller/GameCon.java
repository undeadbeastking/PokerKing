package controller;

import view.MainFrame;
import view.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Agatha of Wood Beyond on 7/4/2014.
 */
public class GameCon {

    private MainFrame mainFrame;

    public GameCon(MainFrame f){
        this.mainFrame = f;

        //Set ActionListener for back button
        this.mainFrame.getGamePanel().getBack().addActionListener(new BackButtonListener());
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Replace Game Panel with Login Panel
            mainFrame.remove(mainFrame.getGamePanel());
            mainFrame.add(mainFrame.getLoginPanel());
            //Set suitable size for the frame and relocate it to center
            mainFrame.setSize(Utils.loginPanel_width, Utils.loginPanel_height);
            mainFrame.setLocationRelativeTo(null);
            //Refresh the mainFrame
            mainFrame.validate();
            mainFrame.repaint();
        }
    }
}
