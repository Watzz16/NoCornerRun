package entity.items;

import entity.HitboxType;
import main.GamePanel;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ItemCrate extends Item {

    public ItemCrate(GamePanel gamePanel, TileManager tileManager, int laneIndex, int startXposition, int speed) {
        super(gamePanel, tileManager, laneIndex, startXposition, speed);
        this.hitboxType = HitboxType.RECTANGLE;
    }

    @Override
    protected void loadSprite() {
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream("/sprites/Tiles/crate.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


}
