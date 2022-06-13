package entity.items;

import entity.Entity;
import entity.HitboxType;
import main.GamePanel;
import tile.Tile;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Gem extends Entity {

    private BufferedImage sprite;
    private GamePanel gamePanel;
    private TileManager tileManager;
    private int laneIndex;

    public Gem(GamePanel gamePanel, TileManager tileManager, int laneIndex, int startXposition, int speed) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        this.x = startXposition;
        this.speed = speed;
        this.laneIndex = laneIndex;
        this.hitboxType = HitboxType.CIRCLE;
        loadSprite();
    }

    private void loadSprite() {
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream("/sprites/Items/gemBlue.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(sprite, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    @Override
    public void update() {
        this.x -= tileManager.getLanes()[0].getLaneSpeed();
        int currentLanePositionY = tileManager.getLanes()[this.laneIndex].getLaneYPosition();
        this.y = currentLanePositionY-gamePanel.tileSize;
    }
}
