package CustomUI;

import Utils.Utils;
import images.ImageGetter;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Agatha of Wood Beyond on 6/30/2014.
 */
public class CustBut extends JButton {

    public CustBut(String name) {

        //'Sign me up' Hyperlink button
        if (name.equals("Sign me up")) {
            //Remove button's highlight when clicked
            this.setFocusPainted(false);
            this.setMargin(new Insets(0, 0, 0, 0));
            //Remove button's background and border
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            //Customize button text
            this.setText(name);
            this.setFont(Utils.hyperButUnfocusState);
            this.setForeground(Utils.hyperButColor);

            //Back button
        } else if (name.equals("back")) {
            //Set button icon
            Image img = ImageGetter.getInstance().getImage(Utils.backButImage);
            this.setIcon(new ImageIcon(img));
            //Remove button's default background
            this.setContentAreaFilled(false);

            //Normal Button
        } else {
            //Set button icon
            Image img = ImageGetter.getInstance().getImage(Utils.normalButImage);
            this.setIcon(new ImageIcon(img));
            //Customize button text
            this.setText(name);
            this.setFont(Utils.normalButFont);
            this.setHorizontalTextPosition(SwingConstants.CENTER);
        }
    }
}
