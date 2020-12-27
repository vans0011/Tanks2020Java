package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ResourceLoader {
    public static final String PATH = "C:\\Users\\Vans\\IdeaProjects\\ServerClient\\Server\\Res/" ;

    public static BufferedImage loadImage(String fileName){

        BufferedImage image = null;
        System.out.println(PATH + fileName);
        try {
            image = ImageIO.read(new File(PATH + fileName));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

}
