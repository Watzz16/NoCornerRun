package entity;

import main.GamePanel;
import main.GameState;
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
    PlayerState playerState = PlayerState.WALK;
    TileManager tileManager;

    private int spriteAnimCounter = 0;
    private int walkAnimSprite = 1;
    private int walkAnimFrameDuration = 7;
    private static final int maxHealth = 1;
    private int health = 1;

    int lane = 1;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, TileManager tileManager) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.tileManager = tileManager;

        this.setDefaultValues();
        this.hitboxType = HitboxType.CIRCLE;
        this.hitboxReduceOffset = gamePanel.tileSize / 4; //reduce hitbox by 12 pixel
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
            imageWalk1 = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/Own/player1.png"));
            imageWalk2 = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/Own/player2.png"));
            imageJump = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/Own/player2.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        moveLanes();
        updatePlayerState();
        checkPlayerHealth();
        walkAnimation();

    }

    private void moveLanes() {
        if(keyHandler.upKeyPressed) {
            if(lane < tileManager.getLanes().length-1) lane++;
            keyHandler.upKeyPressed = false;
        }

        if(keyHandler.downKeyPressed) {
            if(lane > 0) lane--;
            keyHandler.downKeyPressed = false;
        }

        updatePosition();
    }

    private void updatePlayerState() {
        //Switch player state to render correct animation / player sprite
        if(!keyHandler.canPressUpKey || !keyHandler.canPressDownKey) {
            playerState = PlayerState.JUMP;
        } else {
            playerState = PlayerState.WALK;
        }
    }

    private void updatePosition() {
        int currentLanePositionY = tileManager.getLanes()[lane].getLaneYPosition();
        this.y = currentLanePositionY-gamePanel.tileSize;
    }

    public void draw(Graphics2D g2) {
        BufferedImage currentImage = null;
        switch(playerState) {
            case IDLE -> currentImage = imageIdle;
            case JUMP -> currentImage = imageJump;
            case WALK -> {
                if(walkAnimSprite == 1) {
                    currentImage = imageWalk1;
                } else {
                    currentImage = imageWalk2;
                }
            }
        }

        g2.drawImage(currentImage, x, y, currentImage.getWidth()/8, currentImage.getHeight()/8, null);
    }

    private void walkAnimation() {
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

    private void checkPlayerHealth() {
        if(this.health <= 0) { //player died
            gamePanel.gameState = GameState.GAMEOVER;
        }
    }

    public void resetPlayer() {
        this.health = 1;
        this.lane = 1;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
