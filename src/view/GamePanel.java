package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Agatha of Wood Beyond on 7/4/2014.
 */
public class GamePanel extends JPanel {

    private Image backGroundImage;
    private int defaultPlayersNum = 9;
    private PlayerPanel[] playersP;

    public GamePanel(Image i, String playerName) {
        //Customize Game Panel
        this.backGroundImage = i;
        setLayout(null);

        //Create all players panels - real player will be added
        playersP = new PlayerPanel[defaultPlayersNum];
        for (int j = 0; j < defaultPlayersNum; j++) {
            //Number 7 is always the current Player
            if(j == 6){
                playersP[j] = new PlayerPanel(j+1, playerName, 500000);

            } else{
                playersP[j] = new PlayerPanel(j+1, "Kissing Girl", 500000);
            }
        }

        //Set bounds for player panels
        playersP[0].setBounds(Utils.OpponentPanel1_x, Utils.OpponentPanel1_y, Utils.OpponentPanel_width, Utils.OpponentPanel_height);
        playersP[1].setBounds(Utils.OpponentPanel2_x, Utils.OpponentPanel2_y, Utils.OpponentPanel_width, Utils.OpponentPanel_height);
        playersP[2].setBounds(Utils.OpponentPanel3_x, Utils.OpponentPanel3_y, Utils.OpponentPanel_width, Utils.OpponentPanel_height);
        playersP[3].setBounds(Utils.OpponentPanel4_x, Utils.OpponentPanel4_y, Utils.OpponentPanel_width, Utils.OpponentPanel_height);
        playersP[4].setBounds(Utils.OpponentPanel5_x, Utils.OpponentPanel5_y, Utils.OpponentPanel_width, Utils.OpponentPanel_height);
        playersP[5].setBounds(Utils.OpponentPanel6_x, Utils.OpponentPanel6_y, Utils.OpponentPanel_width, Utils.OpponentPanel_height);
        playersP[6].setBounds(Utils.MainPlayerPanel_7_x, Utils.MainPlayerPanel_7_y, Utils.MainPlayerPanel_width, Utils.MainPlayerPanel_height);
        playersP[7].setBounds(Utils.OpponentPanel8_x, Utils.OpponentPanel8_y, Utils.OpponentPanel_width, Utils.OpponentPanel_height);
        playersP[8].setBounds(Utils.OpponentPanel9_x, Utils.OpponentPanel9_y, Utils.OpponentPanel_width, Utils.OpponentPanel_height);

        //Add real player panel
        for (int j = 0; j < defaultPlayersNum; j++) {
            this.add(playersP[j]);
        }
    }

    //Draw panel image
    public void paintComponent(Graphics g) {
        g.drawImage(backGroundImage, 0, 0, null);
    }
}
