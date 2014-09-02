package Utils;

import java.awt.*;

/**
 * Created by Agatha Wood Beyond on 7/19/2014.
 */
public class LoginPU {

    //LoginPanel Utilities
    //Resolutions
    public static final int width = 600;
    public static final int height = 400;

    //Introduction label customize
    public static final Font IntroFont = new Font("Consolas", Font.BOLD, 23);
    public static final Color IntroColor = Color.BLUE;

    //Login Label warning
    public static final Font loginFailFont = new Font("Calibri", Font.BOLD, 14);
    public static final Color loginFailColor = Color.red;

    //Bounds
    //Intro label
    public static final int introLabel_x = 170;
    public static final int introLabel_y = 70;
    public static final int introLabel_w = 290;
    public static final int introLabel_h = 30;

    //Username & Password
    public static final int inputField_x = 200;//Combine bound
    public static final int inputField_w = 200;//Combine bound
    public static final int inputField_h =  30;//Combine bound
    public static final int usernameF_y = 130;
    public static final int passwordF_y = 180;

    //Sign in & Sign up buttons
    public static final int button_w = 150;//Combine bound
    public static final int button_h = 30;//Combine bound

    public static final int signIn_x = 225;
    public static final int signIn_y = 240;

    public static final int signUp_x = 40;
    public static final int signUp_y = 315;

    //Login Message
    public static final int error_x = 390;
    public static final int error_y = 330;
    public static final int error_w = 190;
    public static final int error_h = 30;
}
