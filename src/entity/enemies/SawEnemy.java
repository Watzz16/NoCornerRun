package entity.enemies;

import main.GamePanel;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SawEnemy extends Enemy {

    private static final int walkAnimFrameDuration = 10;

    public SawEnemy(TileManager tileManager, GamePanel gamePanel, int laneIndex, int startXposition, int speed) {
        super(walkAnimFrameDuration, tileManager, gamePanel, laneIndex);
        this.x = startXposition;
        this.speed = speed;
        this.damage = 5;
    }

    @Override
    void loadImages() {
        try {
            this.imageWalk1 = ImageIO.read(getClass().getResourceAsStream("/sprites/Enemies/saw.png"));
            this.imageWalk2 = ImageIO.read(getClass().getResourceAsStream("/sprites/Enemies/saw_move.png"));
            this.imageDead = ImageIO.read(getClass().getResourceAsStream("/sprites/Enemies/saw_dead.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void draw(Graphics2D g2) {
        BufferedImage currentImage = null;
        switch(this.enemyState) {
            case DEAD -> currentImage = this.imageDead;
            case ALIVE -> {
                if(walkAnimSprite == 1) {
                    currentImage = this.imageWalk1;
                } else {
                    currentImage = this.imageWalk2;
                }
            }
        }

        g2.drawImage(currentImage, this.x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    //relative to lane speed
    private void move() {
        this.x -= (tileManager.getLanes()[0].getLaneSpeed() + this.speed);
        int currentLanePositionY = tileManager.getLanes()[this.laneIndex].getLaneYPosition();
        this.y = currentLanePositionY-gamePanel.tileSize;
    }

    @Override
    protected void update() {
        this.walkAnimation();
        this.move();
    }

}
