package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background {

    private BufferedImage purpleWorld;

    public Background() {
        loadBackgroundImage();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(purpleWorld, -200, -250, null);
    }

    private void loadBackgroundImage() {
        try {
            purpleWorld = ImageIO.read(getClass().getResourceAsStream("/sprites/Backgrounds/Purple_world.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
