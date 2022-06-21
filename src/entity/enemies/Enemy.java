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
    protected BufferedImage imageDead;
    protected BufferedImage[] walkImages;
    private int spriteAnimCounter = 0;
    protected int walkAnimSprite = 1;
    protected int walkAnimFrameDuration;
    protected EnemyState enemyState = EnemyState.ALIVE;
    protected TileManager tileManager;
    protected GamePanel gamePanel;
    protected int laneIndex;
    protected int damage;
    private int numberOfWalkImages = 0;

    public Enemy(int walkAnimFrameDuration, TileManager tileManager, GamePanel gamePanel, int laneIndex, int numberOfWalkImages) {
        super();
        this.tileManager = tileManager;
        this.walkAnimFrameDuration = walkAnimFrameDuration;
        this.gamePanel = gamePanel;
        this.laneIndex = laneIndex;
        this.numberOfWalkImages = numberOfWalkImages;
        this.walkImages = new BufferedImage[numberOfWalkImages];
        this.loadImages();
    }

    abstract void loadImages();

    @Override
    public void draw(Graphics2D g2) { }

    @Override
    public void update() { }

    protected void walkAnimation() {
        if(spriteAnimCounter > walkAnimFrameDuration) {
            if(walkAnimSprite < numberOfWalkImages-1) {
                walkAnimSprite++;
            } else {
                walkAnimSprite = 0;
            }

            spriteAnimCounter = 0;
        }

        spriteAnimCounter++;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
