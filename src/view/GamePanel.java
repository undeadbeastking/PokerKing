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
        playersP[0].setBounds(PlayerPU.opponent1_x, PlayerPU.opponent1_y,
                PlayerPU.opponent_w, PlayerPU.opponent_h);
        playersP[1].setBounds(PlayerPU.opponent2_x, PlayerPU.opponent2_y,
                PlayerPU.opponent_w, PlayerPU.opponent_h);
        playersP[2].setBounds(PlayerPU.opponent3_x, PlayerPU.opponent3_y,
                PlayerPU.opponent_w, PlayerPU.opponent_h);
        playersP[3].setBounds(PlayerPU.opponent4_x, PlayerPU.opponent4_y,
                PlayerPU.opponent_w, PlayerPU.opponent_h);
        playersP[4].setBounds(PlayerPU.opponent5_x, PlayerPU.opponent5_y,
                PlayerPU.opponent_w, PlayerPU.opponent_h);
        playersP[5].setBounds(PlayerPU.opponent6_x, PlayerPU.opponent6_y,
                PlayerPU.opponent_w, PlayerPU.opponent_h);

        playersP[6].setBounds(PlayerPU.player_x, PlayerPU.player_y,
                PlayerPU.player_w, PlayerPU.player_h);

        playersP[7].setBounds(PlayerPU.opponent8_x, PlayerPU.opponent8_y,
                PlayerPU.opponent_w, PlayerPU.opponent_h);
        playersP[8].setBounds(PlayerPU.opponent9_x, PlayerPU.opponent9_y,
                PlayerPU.opponent_w, PlayerPU.opponent_h);

        back.setBounds(GamePU.backBut_x, GamePU.backBut_y,
                GamePU.backBut_w, GamePU.backBut_h);

        //Add components
        for (int j = 0; j < maxPlayersNum; j++) {
            this.add(playersP[j]);
        }
        this.add(back);
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
