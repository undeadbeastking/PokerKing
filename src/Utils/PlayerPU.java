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
    public static final Border TurnBorder = new LineBorder(Color.green, 2, false);
    public static final Color Transparent_background = new Color(0, 0, 0, 125);

    //Player panels' bounds
    //Resolutions
    public static final int width = 200;
    public static final int height = 140;

    //Locations
    //Upper 4 panels
    //1 & 4
    public static final int panel1_x = 50;
    public static final int panel4_x = 820;
    public static final int panel1_4_y = 70;//Combine boud
    //2 & 3
    public static final int panel2_x = 310;
    public static final int panel3_x = 560;
    public static final int panel2_3_y = 40;//Combine Bound
    //Right panel - 5 And Left Panel - 9
    public static final int panel5_x = 840;
    public static final int panel9_x = 30;
    public static final int panel5_9_y = 230;//Combine bound
    //Lower 3 panels
    //6 & 8
    public static final int panel6_x = 820;
    public static final int panel8_x = 50;
    public static final int panel6_8_y = 395;
    //7 - Center Panel
    public static final int panel7_x = 405;
    public static final int panel7_y = 415;

    //Username , Status, Bet - x, width, height
    public static final int label_x = 5;//Combine bound
    public static final int label_w = 300;//Combine bound
    public static final int label_h = 20;//Combine bound
    public static final int username_y = 3;
    public static final int status_y = 105;
    public static final int bet_y = 118;

    //CArds
    public static final String pathPrefix = "cards/";
    //cards bounds
    public static final int card_y = 50;//Combine bound
    public static final int card_w = 30;//Combine bound
    public static final int card_h = 30;//Combine bound
    public static final int card1_x = 120;
    public static final int card2_x = 145;

    //Color & Style
    public static final Font label_font = new Font("Consolas", Font.BOLD, 13);//Combine
    public static final Color label_Color = Color.green;//Combine
    //Username
    public static final Color username_Color = Color.GREEN;
    public static final Color pUsername_Color = Color.YELLOW;

    //Player icon
    //Bounds
    public static final int icon_x = 2;
    public static final int icon_y = 24;
    public static final int icon_w = 80;
    public static final int icon_h = 80;
    //Paths (images)
    public static final String Ahri_icon = "players/Ahri.png";
    public static final String Ashe_icon = "players/Ashe.png";
    public static final String Fate_icon = "players/Fate.png";
    public static final String Fizz_icon = "players/Fizz.png";
    public static final String Irelia_icon = "players/Irelia.png";
    public static final String Lux_icon = "players/Lux.png";
    public static final String Momohime_icon = "players/Momohime.png";
    public static final String Nami_icon = "players/Nami.png";
    public static final String Octopus_icon = "players/Octopus.png";
    public static final String Panda_icon = "players/Panda.png";
    public static final String Rabbit_icon = "players/Rabbit.png";
    public static final String Riven_icon = "players/Riven.png";
    public static final String Robot_icon = "players/Robot.png";
    public static final String Scooby_icon = "players/Scooby.png";
    public static final String Shaggy_icon = "players/Shaggy.png";
    public static final String Shishio_icon = "players/Shishio.png";
    public static final String Sivir_icon = "players/Sivir.png";
    public static final String Sona_icon = "players/Sona.png";
    public static final String Vi_icon = "players/Vi.png";

    public static Image randomIcon() {
        Random rand = new Random();
        int min = 1, max = 19;
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
                path = Ashe_icon;
                break;

            case 8:
                path = Fate_icon;
                break;

            case 9:
                path = Fizz_icon;
                break;

            case 10:
                path = Irelia_icon;
                break;

            case 11:
                path = Lux_icon;
                break;

            case 12:
                path = Nami_icon;
                break;

            case 13:
                path = Octopus_icon;
                break;

            case 14:
                path = Rabbit_icon;
                break;

            case 15:
                path = Riven_icon;
                break;

            case 16:
                path = Robot_icon;
                break;

            case 17:
                path = Scooby_icon;
                break;

            case 18:
                path = Shaggy_icon;
                break;

            case 19:
                path = Sona_icon;
                break;

            default:
                path = Sona_icon;
                break;
        }
        img = ImageGetter.getInstance().getImage(path);

        return img;
    }
}
