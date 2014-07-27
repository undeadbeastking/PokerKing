package view;

import CustomUI.CustBut;
import images.ImageGetter;
import Utils.GamePU;
import Utils.PlayerPU;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Agatha of Wood Beyond on 7/4/2014.
 */
public class GamePanel extends JPanel {

    private Image backGround;
    private int maxPlayersNum = 9;
    private PlayerPanel[] playersP;
    private CustBut back = new CustBut("back");
    //Function buttons
    private CustBut foldBut = new CustBut("Fold");
    private CustBut callBut= new CustBut("Call");
    private CustBut raiseBut = new CustBut("Raise");

    public GamePanel(String playerName) {
        //Customize Game Panel
        this.backGround = ImageGetter.getInstance().getImage(GamePU.backGround);
        setLayout(null);

        //Create all players panels - real player will be added
        playersP = new PlayerPanel[maxPlayersNum];
        for (int j = 0; j < maxPlayersNum; j++) {
            //Number 7 is always the current Player
            if(j == 6){
                playersP[j] = new PlayerPanel(j+1, playerName);

            } else{
                playersP[j] = new PlayerPanel(j+1, "Anonymous");
            }
        }

        //Set bounds for player panels
        playersP[0].setBounds(PlayerPU.panel1_x, PlayerPU.panel1_4_y,
                PlayerPU.width, PlayerPU.height);
        playersP[3].setBounds(PlayerPU.panel4_x, PlayerPU.panel1_4_y,
                PlayerPU.width, PlayerPU.height);

        playersP[1].setBounds(PlayerPU.panel2_x, PlayerPU.panel2_3_y,
                PlayerPU.width, PlayerPU.height);
        playersP[2].setBounds(PlayerPU.panel3_x, PlayerPU.panel2_3_y,
                PlayerPU.width, PlayerPU.height);

        playersP[4].setBounds(PlayerPU.panel5_x, PlayerPU.panel5_9_y,
                PlayerPU.width, PlayerPU.height);
        playersP[8].setBounds(PlayerPU.panel9_x, PlayerPU.panel5_9_y,
                PlayerPU.width, PlayerPU.height);

        playersP[5].setBounds(PlayerPU.panel6_x, PlayerPU.panel6_8_y,
                PlayerPU.width, PlayerPU.height);
        playersP[7].setBounds(PlayerPU.panel8_x, PlayerPU.panel6_8_y,
                PlayerPU.width, PlayerPU.height);
        //Center player
        playersP[6].setBounds(PlayerPU.panel7_x, PlayerPU.panel7_y,
                PlayerPU.width, PlayerPU.height);
        //Other components
        back.setBounds(GamePU.backBut_x, GamePU.backBut_y,
                GamePU.backBut_w, GamePU.backBut_h);
        foldBut.setBounds(GamePU.fold_x, GamePU.fcr_y,
                GamePU.fcr_w, GamePU.fcr_h);
        callBut.setBounds(GamePU.call_x, GamePU.fcr_y,
                GamePU.fcr_w, GamePU.fcr_h);
        raiseBut.setBounds(GamePU.raise_x, GamePU.fcr_y,
                GamePU.fcr_w, GamePU.fcr_h);

        //Add components
        for (int j = 0; j < maxPlayersNum; j++) {
            this.add(playersP[j]);
        }
        this.add(back);
        this.add(foldBut);
        this.add(callBut);
        this.add(raiseBut);
}

    //Draw panel image
    public void paintComponent(Graphics g) {
        g.drawImage(backGround, 0, 0, null);
    }

    public CustBut getBackBut() {
        return back;
    }

    public PlayerPanel[] getPlayersP() {
        return playersP;
    }
}
