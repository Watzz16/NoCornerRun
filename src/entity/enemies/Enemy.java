package entity.enemies;

import entity.Entity;
import main.GamePanel;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Enemy extends Entity {

    protected int health;
    protected BufferedImage imageWalk1, imageWalk2, imageDead;
    private int spriteAnimCounter = 0;
    protected int walkAnimSprite = 1;
    protected int walkAnimFrameDuration;
    protected EnemyState enemyState = EnemyState.ALIVE;
    protected TileManager tileManager;
    protected GamePanel gamePanel;
    protected int laneIndex;

    public Enemy(int walkAnimFrameDuration, TileManager tileManager, GamePanel gamePanel, int laneIndex) {
        super();
        this.tileManager = tileManager;
        this.walkAnimFrameDuration = walkAnimFrameDuration;
        this.gamePanel = gamePanel;
        this.laneIndex = laneIndex;
        this.loadImages();
    }

    abstract void loadImages();

    @Override
    protected void draw(Graphics2D g2) { }

    @Override
    protected void update() { }

    protected void walkAnimation() {
        if(spriteAnimCounter > walkAnimFrameDuration) {
            if(walkAnimSprite == 1) {
                walkAnimSprite = 2;
            } else {
                walkAnimSprite = 1;
            }
            spriteAnimCounter = 0;
        }

        spriteAnimCounter++;
    }

}
