package entity.enemies;

import entity.HitboxType;
import main.GamePanel;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SquidEnemy extends Enemy {

    private static final int walkAnimFrameDuration = 10;
    private int floatAnimFrameCounter = 0;
    private int floatAnimUpdateNeededFrames = 5;
    private int floatAnimOffset = 0;
    private final int floatAnimStaticOffset = 3;
    private final int floatAnimDeviationFromZero = 4;
    private boolean floatAnimUp = true;

    public SquidEnemy(TileManager tileManager, GamePanel gamePanel, int laneIndex, int startXposition, int speed) {
        super(walkAnimFrameDuration, tileManager, gamePanel, laneIndex, 4);
        this.x = startXposition;
        this.speed = speed;
        this.damage = 5;
        this.hitboxType = HitboxType.CIRCLE;
        this.hitboxReduceOffset = gamePanel.tileSize / 4; //reduce hitbox by 8 pixel
    }

    @Override
    void loadImages() {
        try {
            this.walkImages[0] = ImageIO.read(getClass().getResourceAsStream("/sprites/Enemies/squid/squid1.png"));
            this.walkImages[1] = ImageIO.read(getClass().getResourceAsStream("/sprites/Enemies/squid/squid2.png"));
            this.walkImages[2] = ImageIO.read(getClass().getResourceAsStream("/sprites/Enemies/squid/squid3.png"));
            this.walkImages[3] = ImageIO.read(getClass().getResourceAsStream("/sprites/Enemies/squid/squid2.png"));
            this.imageDead = ImageIO.read(getClass().getResourceAsStream("/sprites/Enemies/squid/squid1.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage currentImage = null;
        switch(this.enemyState) {
            case DEAD -> currentImage = this.imageDead;
            case ALIVE -> {
                currentImage = this.walkImages[walkAnimSprite];
            }
        }

        g2.drawImage(currentImage, x, y, currentImage.getWidth()/12, currentImage.getHeight()/12, null);
    }

    //relative to lane speed
    private void move() {
        this.x -= (tileManager.getLanes()[0].getLaneSpeed() + this.speed);
        int currentLanePositionY = tileManager.getLanes()[this.laneIndex].getLaneYPosition();
        this.y = currentLanePositionY-walkImages[0].getHeight()/12 + floatAnimOffset - floatAnimStaticOffset;
    }

    @Override
    public void update() {
        this.walkAnimation();
        this.move();

        if(floatAnimFrameCounter >= floatAnimUpdateNeededFrames) {
            this.updateFloatAnim();
            floatAnimFrameCounter = 0;
        }
        floatAnimFrameCounter++;
    }

    private void updateFloatAnim() {
        if(floatAnimOffset == floatAnimDeviationFromZero) {
            floatAnimUp = false;
        } else if(floatAnimOffset == -floatAnimDeviationFromZero) {
            floatAnimUp = true;
        }

        if(floatAnimUp) {
            floatAnimOffset++;
        } else {
            floatAnimOffset--;
        }
    }


}
