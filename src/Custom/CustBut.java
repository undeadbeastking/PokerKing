package Custom;

import view.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Agatha of Wood Beyond on 6/30/2014.
 */
public class CustBut extends JButton {

    //Buttons with no texts: Back
    public CustBut() {
        //Set button icon
        try {
            Image img = ImageIO.read(getClass().getResource(Utils.backButtonImage));
            this.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
            System.out.println("Unable to set button images");
        }
        //Remove button's default background
        this.setContentAreaFilled(false);
    }

    //Buttons with texts
    public CustBut(String name) {
        super(name);

        //'Sign me up' Hyperlink button
        if (name.equals("Sign me up")) {
            //Remove button's highlight when clicked
            this.setFocusPainted(false);
            this.setMargin(new Insets(0, 0, 0, 0));
            //Remove button's background and border
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            //Customize button text
            this.setFont(Utils.hyperTextButtonFontNormal);
            this.setForeground(Utils.hyperTextButtonColor);

            //Normal image button
        } else {
            //Set button icon
            try {
                Image img = ImageIO.read(getClass().getResource(Utils.normalButtonImage));
                this.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
                System.out.println("Unable to set button images");
            }

            //Customize button text
            this.setFont(Utils.normalButtonFont);
            this.setHorizontalTextPosition(SwingConstants.CENTER);
        }
    }
}
