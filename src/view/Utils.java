package view;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by Agatha of Wood Beyond on 7/2/2014.
 */
public class Utils {

    //My fonts
    private static Font dimFont = new Font("Consolas", Font.ITALIC, 14);
    private static Font myFont = new Font("Consolas", Font.PLAIN, 14);

    //LoginPanel Utilities
    private static Border colorBorder = new LineBorder(Color.GREEN, 1, false);

    public static Font getMyFont() {
        return myFont;
    }

    public static Font getDimFont() {
        return dimFont;
    }

    public static Border getColorBorder() {
        return colorBorder;
    }
}
