package images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Agatha Wood Beyond on 7/20/2014.
 */
public class ImageGetter {

    private static ImageGetter instance = null;

    public static ImageGetter getInstance(){
        if(instance == null){
            instance = new ImageGetter();
        }
        return instance;
    }

    public Image getImage(String path){
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            return img;

        } catch (IOException ex) {
            System.out.println("Unable to get the image.");
        }
        return null;
    }
}
