package view;

import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Agatha of Wood Beyond on 6/29/2014.
 */
public class MainFrame extends JFrame {

    private Login login = new Login();

    private static Font DimFont = new Font("Consolas", Font.ITALIC, 14);
    private static Font myFont = new Font("Consolas", Font.PLAIN, 14);

    public MainFrame(){
        //Customize MainFrame for login Panel
        this.setSize(600, 400);
        this.setTitle("Poker King - The Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);//Set center
        this.setVisible(true);

        //Add login Panel
        this.add(login);
    }

    public static Font getDimFont() {
        return DimFont;
    }

    public static Font getMyFont() {
        return myFont;
    }
}
