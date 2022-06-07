package entity;

import main.GamePanel;
import main.KeyHandler;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    BufferedImage imageIdle, imageWalk1, imageWalk2, imageJump;
    PlayerState playerState = PlayerState.IDLE;
    TileManager tileManager;

    int lane = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, TileManager tileManager) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.tileManager = tileManager;

        this.setDefaultValues();
        loadPlayerImages();
    }

    public void setDefaultValues() {
        this.x = 100;
        this.y = 300;
        this.speed = 4;
        this.lane = 0;
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
        if(keyHandler.upKeyPressed) {
            if(lane < tileManager.getLanes().length-1) lane++;
            keyHandler.upKeyPressed = false;
        }

        if(keyHandler.downKeyPressed) {
            if(lane > 0) lane--;
            keyHandler.downKeyPressed = false;
        }

        if(!keyHandler.canPressUpKey || !keyHandler.canPressDownKey) {
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

        int currentLanePositionY = tileManager.getLanes()[lane].getLaneYPosition();

        g2.drawImage(currentImage, x, currentLanePositionY-gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
    }

}
