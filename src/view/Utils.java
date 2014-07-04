package view;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Agatha of Wood Beyond on 7/2/2014.
 */
public class Utils {

    //Background color
    public static final Color backGroundColor = new Color(137, 207, 240);

    //JTextField Colorful border
    public static final Border JTextFieldColorBorder = new LineBorder(Color.GREEN, 1, false);
    //JTextField Error border
    public static final Border JTextFieldErrorBorder = new LineBorder(Color.RED, 1, false);
    //JTextField input HINT color and font
    public static final Font hintFont = new Font("Consolas", Font.ITALIC, 14);
    public static final Color hintColor = Color.darkGray;
    //JTextField input font
    public static final Font inputFont = new Font("Consolas", Font.PLAIN, 14);
    public static final Color inputColor = Color.BLACK;

    //Button Utilities
    //Normal button Font & image path
    public static final Font normalButtonFont = new Font("Consolas", Font.BOLD, 15);
    public static final String normalButtonImage = "images/Button blue.png";
    //HyperText Button Font & Color
    public static final Font hyperTextButtonFontNormal = new Font("Consolas", Font.PLAIN, 15);
    public static final Font hyperTextButtonFontEnter = new Font("Consolas", Font.BOLD, 15);
    public static final Color hyperTextButtonColor = Color.BLUE;
    //Back button image path
    public static final String backButtonImage = "images/Back.png";

    //LoginPanel Utilities
    //Resolutions
    public static final int loginPanel_width = 600;
    public static final int loginPanel_height = 400;
    //Introduction label customize
    public static final Font loginPanelIntroLabelFont = new Font("Consolas", Font.BOLD, 23);
    public static final Color loginPanelIntroLabelColor = Color.BLUE;
    //Login Label warning
    public static final Font loginFailFont = new Font("Calibri", Font.BOLD, 14);
    public static final Color loginFailColor = Color.red;

    //SignUpPanel Utilities
    //Resolutions
    public static final int SignUpPanel_width = 400;
    public static final int SignUpPanel_height = 340;
    //Introduction label customize
    public static final Font signUpPanelIntroLabelFont = new Font("Consolas", Font.BOLD, 23);
    public static final Color signUpPanelIntroLabelColor = Color.blue;

    //GamePanel Utilities
    //Resolution
    public static final int GamePanel_width = 1080;
    public static final int GamePanel_height = 720;
    public static final String gameBackground = "images/Background.png";

    //Player Panels Utilities
    public static final Border PlayerPanel_border = new LineBorder(Color.red, 2, false);
    public static final Color Transparent_background = new Color(0,0,0,125);
    //Resolutions
    public static final int OpponentPanel_width = 210;
    public static final int OpponentPanel_height = 180;
    public static final int MainPlayerPanel_width = 270;
    public static final int MainPlayerPanel_height = 240;
    //Player panels' locations
    //Upper 4 panels
    public static final int OpponentPanel1_x = 50;
    public static final int OpponentPanel1_y = 70;
    public static final int OpponentPanel2_x = 310;
    public static final int OpponentPanel2_y = 40;
    public static final int OpponentPanel3_x = 560;
    public static final int OpponentPanel3_y = 40;
    public static final int OpponentPanel4_x = 820;
    public static final int OpponentPanel4_y = 70;
    //Right panel
    public static final int OpponentPanel5_x = 840;
    public static final int OpponentPanel5_y = 260;
    //Lower 3 panels
    public static final int OpponentPanel6_x = 820;
    public static final int OpponentPanel6_y = 460;
    public static final int MainPlayerPanel_7_x = 405;
    public static final int MainPlayerPanel_7_y = 450;
    public static final int OpponentPanel8_x = 50;
    public static final int OpponentPanel8_y = 460;
    //Left Panel
    public static final int OpponentPanel9_x = 30;
    public static final int OpponentPanel9_y = 260;

    //Player panels name
    //Opponent
    public static final int OpponentName_x = 25;
    public static final int OpponentName_y = 8;
    public static final int OpponentName_width = 180;
    public static final int OpponentName_height = 20;
    public static final Color OpponnentName_Color = Color.WHITE;
    public static final Font OpponentName_font = new Font("Consolas", Font.BOLD, 14);
    //Main Player
    public static final int PlayerName_x = 25;
    public static final int PlayerName_y = 6;
    public static final int PlayerName_width = 220;
    public static final int PlayerName_height = 30;
    public static final Color PlayerName_Color = Color.WHITE;
    public static final Font PlayerName_font = new Font("Consolas", Font.BOLD, 16);

    //Player panels cash
    //Opponent
    public static final int OpponentCash_x = 24;
    public static final int OpponentCash_y = 152;
    public static final int OpponentCash_width = 260;
    public static final int OpponentCash_height = 18;
    public static final Color OpponnentCash_Color = Color.green;
    public static final Font OpponentCash_font = new Font("Consolas", Font.BOLD, 18);
    //Main Player
    public static final int PlayerCash_x = 24;
    public static final int PlayerCash_y = 209;
    public static final int PlayerCash_width = 260;
    public static final int PlayerCash_height = 20;
    public static final Color PlayerCash_Color = Color.green;
    public static final Font PlayerCash_font = new Font("Consolas", Font.BOLD, 21);

    //Player panels icons
    //Main Player
    public static final int PlayerIcon_x = 2;
    public static final int PlayerIcon_y = 34;
    public static final int PlayerIcon_width = 160;
    public static final int PlayerIcon_height = 170;
    //Paths
    public static final String Agatha_icon = "images/MainPlayer/Agatha.png";
    public static final String Ahri_icon = "images/MainPlayer/Ahri.png";
    public static final String Momohime_icon = "images/MainPlayer/Momohime.png";
    public static final String Panda_icon = "images/MainPlayer/Panda.png";
    public static final String Shishio_icon = "images/MainPlayer/Shishio.png";
    public static final String Sivir_icon = "images/MainPlayer/Sivir.png";
    public static final String Vi_icon = "images/MainPlayer/Vi.png";
    //Opponent
    public static final int OpponentIcon_x = 2;
    public static final int OpponentIcon_y = 34;
    public static final int OpponentIcon_width = 110;
    public static final int OpponentIcon_height = 110;
    //Paths
    public static final String Alien_icon = "images/Opponent/Alien.png";
    public static final String Annie_icon = "images/Opponent/Annie.png";
    public static final String Ashe_icon = "images/Opponent/Ashe.png";
    public static final String Blackbeard_icon = "images/Opponent/Blackbeard.png";
    public static final String Fate_icon = "images/Opponent/Fate.png";
    public static final String Fizz_icon = "images/Opponent/Fizz.png";
    public static final String Irelia_icon = "images/Opponent/Irelia.png";
    public static final String Lux_icon = "images/Opponent/Lux.png";
    public static final String Nami_icon = "images/Opponent/Nami.png";
    public static final String Octopus_icon = "images/Opponent/Octopus.png";
    public static final String Rabbit_icon = "images/Opponent/Rabbit.png";
    public static final String Riven_icon = "images/Opponent/Riven.png";
    public static final String Robot_icon = "images/Opponent/Robot.png";
    public static final String Scooby_icon = "images/Opponent/Scooby.png";
    public static final String Shaggy_icon = "images/Opponent/Shaggy.png";
    public static final String Sona_icon = "images/Opponent/Sona.png";

    public static String random_mainPlayer_icon(){
        Random rand = new Random();
        int min = 1, max = 6;
        int randomNum = rand.nextInt((max - min) + 1) + min;

        String path;
        switch(randomNum){
            case 1:
                path = Ahri_icon;
                break;

            case 2:
                path = Momohime_icon;
                break;

            case 3:
                path = Panda_icon;
                break;

            case 4:
                path = Shishio_icon;
                break;

            case 5:
                path = Sivir_icon;
                break;

            case 6:
                path = Vi_icon;
                break;

            default:
                path = Agatha_icon;
                break;
        }

        return path;
    }

}
