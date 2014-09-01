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

    //Back button
    public static final int backBut_x = 1010;
    public static final int backBut_y = 10;
    public static final int backBut_w = 50;
    public static final int backBut_h = 50;

    //Bet Round Label
    public static final int betRound_x = 467;
    public static final int betRound_y = 205;
    public static final int betRound_w = 150;
    public static final int betRound_h = 40;
    public static final Font betRoundFont = new Font("Consolas", Font.BOLD, 21);

    //Fold - Call - Raise - fcr
    public static final int fcr_y = 570;//Combine bound
    public static final int fcr_w = 160;//Combine bound
    public static final int fcr_h = 40;//Combine bound
    public static  final int fold_x = 250;
    public static  final int call_x = 450;
    public static  final int raise_x = 650;

    //Community cards
    public static final int card_w = 70;//Combine bound
    public static final int card_h = 100;//Combine bound
    public static final int card_x = 260;
    public static final int card_y = 320;

    //Pot Label
    public static final int pot_x = 20;
    public static final int pot_y = 640;
    public static final int pot_w = 170;//Combine bound
    public static final int pot_h = 30;//Combine bound
    public static final Font potFont = new Font("Consolas", Font.BOLD, 21);

    //Small blind and Big blind Bound
    public static final int blind_x = 900;//Combine bound
    public static final int smallBlind_y = 620;
    public static final int bigBlind_y = 650;
    public static final Font blindFont = new Font("Consolas", Font.BOLD, 16);

    //Increase, decrease $ buttons
    public static final String increase_Mon = "buttons/increase.png";
    public static final String decrease_Mon = "buttons/decrease.png";
    public static final int bet_y = 630;//Combine bound
    public static final int bet_w = 50;//Combine bound
    public static final int bet_h = 40;//Combine bound
    public static final int betDecrease_x = 405;
    public static final int betIncrease_x = 605;
    public static final int betAmount_x = 482;
    public static final int betAmount_w = 100;
    public static final int betAmount_h = 40;
}
