package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    BufferedImage imageIdle, imageWalk1, imageWalk2, imageJump;
    PlayerState playerState = PlayerState.IDLE;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        this.setDefaultValues();
        loadPlayerImages();
    }

    public void setDefaultValues() {
        this.x = 100;
        this.y = 500;
        this.speed = 4;
    }

    public void loadPlayerImages() {
        try {
            imageIdle = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/VariableSizes/Yellow/alienYellow_stand.png"));
            imageWalk1 = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/VariableSizes/Yellow/alienYellow_walk1.png"));
            imageWalk2 = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/VariableSizes/Yellow/alienYellow_walk2.png"));
            imageJump = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/VariableSizes/Yellow/alienYellow_jump.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyHandler.UpKeyPressed) {
            y -= speed;
            playerState = PlayerState.JUMP;
        } else {
            playerState = PlayerState.IDLE;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage currentImage = null;
        switch(playerState) {
            case IDLE -> currentImage = imageIdle;
            case JUMP -> currentImage = imageJump;
        }

        g2.drawImage(currentImage, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }

}
