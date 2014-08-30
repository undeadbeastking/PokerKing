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
}
