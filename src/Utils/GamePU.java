package Utils;

import java.awt.*;

/*
 * Created by Agatha Wood Beyond on 7/20/2014.
 */
public class GamePU {

    //GamePanel Utilities
    //Resolution
    public static final int width = 1080;
    public static final int height = 720;
    public static final String backGround = "backgrounds/Background.png";

    //Bet Round Label
    public static final int betRound_x = 467;
    public static final int betRound_y = 205;
    public static final int betRound_w = 150;
    public static final int betRound_h = 40;
    public static final Font betRoundFont = new Font("Consolas", Font.BOLD, 21);

    //Fold - Call - Raise buttons -> fcr buttons
    public static final int fcr_y = 570;//Combine bound
    public static final int fcr_w = 160;//Combine bound
    public static final int fcr_h = 40;//Combine bound
    public static  final int fold_x = 250;
    public static  final int call_x = 450;
    public static  final int raise_x = 650;

    //Community cards
    public static final int CommuCard_w = 70;//Combine bound
    public static final int CommuCard_h = 100;//Combine bound
    public static final int CommuCard_y = 320;//Combine bound
    public static final int CommuCard_x = 260;

    //Pot Label
    public static final int pot_blind_w = 170;//Combine bound
    public static final int pot_blind_h = 30;//Combine bound
    public static final int pot_x = 20;
    public static final int pot_y = 640;
    public static final Font potFont = new Font("Consolas", Font.BOLD, 21);

    //Small blind and Big blind Bound
    public static final int blind_x = 900;//Combine bound
    public static final int smallBlind_y = 620;
    public static final int bigBlind_y = 650;
    public static final Font blindFont = new Font("Consolas", Font.BOLD, 16);

    //Increase, decrease $ buttons
    public static final String increase_Mon_Pic = "buttons/increase.png";
    public static final String decrease_Mon_Pic = "buttons/decrease.png";

    public static final int betGear_w = 50;//Combine bound
    public static final int betGear_h = 40;//Combine bound
    public static final int betGear_y = 630;//Combine bound
    public static final int betDecrease_x = 405;
    public static final int betIncrease_x = 605;
    //Label field display Bet amount
    public static final int betAmount_x = 482;
    public static final int betAmount_w = 100;
    public static final int betAmount_h = 40;
}
