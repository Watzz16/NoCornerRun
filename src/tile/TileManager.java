package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;


public class TileManager {
    GamePanel gamePanel;
    Lane[] lanes;

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        buildLanes();
    }

    private void buildLanes() {
        lanes = new Lane[3];
        lanes[0] = new Lane(530, gamePanel);
        lanes[1] = new Lane(330, gamePanel);
        lanes[2] = new Lane(130, gamePanel);
    }

    public void draw(Graphics2D g2){
        for(Lane lane : lanes) lane.draw(g2);
    }

    public void update() {
        for(Lane lane : lanes) lane.update();
    }

    public Lane[] getLanes() {
        return lanes;
    }

}
