package entity.items;

import entity.Entity;
import entity.HitboxType;
import main.GamePanel;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Item extends Entity {

    protected BufferedImage sprite;
    protected GamePanel gamePanel;
    protected TileManager tileManager;
    protected int laneIndex;

    public Item(GamePanel gamePanel, TileManager tileManager, int laneIndex, int startXposition, int speed) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        this.x = startXposition;
        this.speed = speed;
        this.laneIndex = laneIndex;
        this.hitboxType = HitboxType.CIRCLE;
        loadSprite();
    }

    protected abstract void loadSprite();

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(sprite, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    @Override
    public void update() {
        this.x -= tileManager.getLanes()[laneIndex].getLaneSpeed();
        int currentLanePositionY = tileManager.getLanes()[laneIndex].getLaneYPosition();
        this.y = currentLanePositionY-gamePanel.tileSize;
    }
}
