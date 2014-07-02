package Custom;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Agatha of Wood Beyond on 6/30/2014.
 */
public class CustBut extends JButton {
    private Font font = new Font("Consolas", Font.BOLD, 15);

    public CustBut(String name) {
        super(name);

        //Set button icon
        try {
            Image img = ImageIO.read(getClass().getResource("images/Button blue.png"));
            this.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
            System.out.println("Unable to set button images");
        }

        //Set button text
        this.setFont(font);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
    }

}
