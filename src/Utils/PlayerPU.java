package Utils;

import images.ImageGetter;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Random;

/**
 * Created by Agatha Wood Beyond on 7/21/2014.
 */
public class PlayerPU {

    //PlayerPanel Utilities
    public static final Border PanelBorder = new LineBorder(Color.red, 2, false);
    public static final Color Transparent_background = new Color(0, 0, 0, 125);

    //Player panels' bounds
    //Resolutions
    public static final int opponent_w = 210;
    public static final int opponent_h = 180;
    public static final int player_w = 270;
    public static final int player_h = 240;
    //Locations
    //Upper 4 panels
    //1
    public static final int opponent1_x = 50;
    public static final int opponent1_y = 70;
    //2
    public static final int opponent2_x = 310;
    public static final int opponent2_y = 40;
    //3
    public static final int opponent3_x = 560;
    public static final int opponent3_y = 40;
    //4
    public static final int opponent4_x = 820;
    public static final int opponent4_y = 70;
    //Right panel - 5
    public static final int opponent5_x = 840;
    public static final int opponent5_y = 260;
    //Lower 3 panels
    //6
    public static final int opponent6_x = 820;
    public static final int opponent6_y = 460;
    //7 - Main Player
    public static final int player_x = 405;
    public static final int player_y = 450;
    //8
    public static final int opponent8_x = 50;
    public static final int opponent8_y = 460;
    //Left Panel - 9
    public static final int opponent9_x = 30;
    public static final int opponent9_y = 260;

    //Username bounds
    public static final int username_x = 25;//Combine bound
    public static final int username_y = 8;//Combine bound
    //width & height
    public static final int username_w = 180;
    public static final int username_h = 20;
    public static final int username7_w = 220;
    public static final int username7_h = 30;
    //Color & Style
    public static final Color username_Color = Color.GREEN;
    public static final Font username_font = new Font("Consolas", Font.BOLD, 14);
    public static final Color username7_Color = Color.YELLOW;
    public static final Font username7_font = new Font("Consolas", Font.BOLD, 18);

    //Bet
    //Opponent
    public static final int bet_x = 24;
    public static final int bet_y = 152;
    public static final int bet_w = 260;
    public static final int bet_h = 18;
    public static final Color bet_Color = Color.green;
    public static final Font bet_font = new Font("Consolas", Font.BOLD, 13);
    //Main Player
    public static final int playerBet_x = 24;
    public static final int playerBet_y = 209;
    public static final int playerBet_w = 280;
    public static final int playerBet_h = 20;
    public static final Color playerBet_color = Color.RED;
    public static final Font playerBet_font = new Font("Consolas", Font.BOLD, 21);

    //Player icon
    //Bounds
    public static final int icon_x = 2;
    public static final int icon_y = 36;
    public static final int icon_w = 80;
    public static final int icon_h = 80;
    //Paths (Big images)
    public static final String Agatha_icon = "MainPlayer/Agatha.png";
    public static final String Ahri_icon = "MainPlayer/Ahri.png";
    public static final String Momohime_icon = "MainPlayer/Momohime.png";
    public static final String Panda_icon = "MainPlayer/Panda.png";
    public static final String Shishio_icon = "MainPlayer/Shishio.png";
    public static final String Sivir_icon = "MainPlayer/Sivir.png";
    public static final String Vi_icon = "MainPlayer/Vi.png";
    //Paths
    public static final String Alien_icon = "Opponent/Alien.png";
    public static final String Annie_icon = "Opponent/Annie.png";
    public static final String Ashe_icon = "Opponent/Ashe.png";
    public static final String Blackbeard_icon = "Opponent/Blackbeard.png";
    public static final String Fate_icon = "Opponent/Fate.png";
    public static final String Fizz_icon = "Opponent/Fizz.png";
    public static final String Irelia_icon = "Opponent/Irelia.png";
    public static final String Lux_icon = "Opponent/Lux.png";
    public static final String Nami_icon = "Opponent/Nami.png";
    public static final String Octopus_icon = "Opponent/Octopus.png";
    public static final String Rabbit_icon = "Opponent/Rabbit.png";
    public static final String Riven_icon = "Opponent/Riven.png";
    public static final String Robot_icon = "Opponent/Robot.png";
    public static final String Scooby_icon = "Opponent/Scooby.png";
    public static final String Shaggy_icon = "Opponent/Shaggy.png";
    public static final String Sona_icon = "Opponent/Sona.png";

    public static Image randomIcon() {
        Random rand = new Random();
        int min = 1, max = 22;
        int randomNum = rand.nextInt((max - min) + 1) + min;

        String path;
        Image img;
        switch (randomNum) {
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

            case 7:
                path = Alien_icon;
                break;

            case 8:
                path = Annie_icon;
                break;

            case 9:
                path = Ashe_icon;
                break;

            case 10:
                path = Blackbeard_icon;
                break;

            case 11:
                path = Fate_icon;
                break;

            case 12:
                path = Fizz_icon;
                break;

            case 13:
                path = Irelia_icon;
                break;

            case 14:
                path = Lux_icon;
                break;

            case 15:
                path = Nami_icon;
                break;

            case 16:
                path = Octopus_icon;
                break;

            case 17:
                path = Rabbit_icon;
                break;

            case 18:
                path = Riven_icon;
                break;

            case 19:
                path = Robot_icon;
                break;

            case 20:
                path = Scooby_icon;
                break;

            case 21:
                path = Shaggy_icon;
                break;

            case 22:
                path = Sona_icon;
                break;

            default:
                path = Agatha_icon;
                break;
        }

        img = ImageGetter.getInstance().getImage(path);

        return img;
    }
}
