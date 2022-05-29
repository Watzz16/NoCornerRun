package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;


public class TileManager {
    GamePanel GP;
    Tile[] Tiles;

    public TileManager(GamePanel GP){
        this.GP = GP;
        Tiles = new Tile[10];
        getTileImage();
    }
    public void getTileImage (){
        Tiles[0] = new Tile();
        try {
            Tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/sprites/Ground/Grass/grass.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
    public void draw(Graphics2D g2){
        g2.drawImage(Tiles[0].image,0,500,null);
    }

}
