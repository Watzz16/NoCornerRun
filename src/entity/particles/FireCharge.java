package entity.particles;

import entity.Entity;
import entity.HitboxType;
import main.GamePanel;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FireCharge extends Entity {

    private BufferedImage sprite;
    private GamePanel gamePanel;
    private TileManager tileManager;

    private final int speed = 5;

    public FireCharge(GamePanel gamePanel, TileManager tileManager, int spawnXPos, int spawnYPos) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;

        this.x = spawnXPos;
        this.y = spawnYPos;

        this.hitboxType = HitboxType.CIRCLE;
        this.hitboxReduceOffset = 24;

        this.loadSprite();
    }

    private void loadSprite() {
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream("/sprites/Particles/fireball.png"));
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
        this.x += this.speed;
    }
}
