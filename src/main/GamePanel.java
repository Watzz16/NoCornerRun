package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    //screen settings
    final int originalTileSize = 16; //how many pixels is one tile? 16x16
    final int scale = 3; //how large do we want to scale one tile? 3x -> 48x48 pixel

    final int tileSize = originalTileSize * scale; //48x48 pixel
    final int maxScreenCol = 16; //how many tiles in width? Columns
    final int maxScreenRow = 12; //how many tiles in height? Rows
    final int screenWidth = tileSize * maxScreenCol; //calculate the pixel width of the window
    final int screenHeight = tileSize * maxScreenRow; //calculate the pixel height of the window

    //this is the constructor. It's a method that is called once a new object of that class is created. It constructs the object
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }


}
