package Utils;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by Agatha Wood Beyond on 7/19/2014.
 */
public class Utils {

    //Background color
    public static final Color backGroundColor = new Color(137, 207, 240);

    //General JextField Customize
    //JTextField Colorful border
    public static final Border JFColorBorder = new LineBorder(Color.GREEN, 1, false);
    //JTextField input HINT color and font
    public static final Font hintFont = new Font("Consolas", Font.ITALIC, 14);
    public static final Color hintColor = Color.darkGray;
    //JTextField input color and font
    public static final Font inputFont = new Font("Consolas", Font.PLAIN, 14);
    public static final Color inputColor = Color.BLACK;

    //General Button Utilities
    //Normal button Font & image path
    public static final Font normalButFont = new Font("Consolas", Font.BOLD, 15);
    public static final String normalButImage = "buttons/Button blue.png";
    //Back button image path
    public static final String backButImage = "buttons/Back.png";
    //HyperText Button Font & Color
    public static final Font hyperButUnfocusState = new Font("Consolas", Font.PLAIN, 15);
    public static final Font hyperButFocusState = new Font("Consolas", Font.BOLD, 15);
    public static final Color hyperButColor = Color.BLUE;

}
