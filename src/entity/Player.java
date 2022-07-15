package entity;

import entity.particles.FireCharge;
import main.GamePanel;
import main.GameState;
import main.KeyHandler;
import services.PlayerStats;
import services.RequestService;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    RequestService requestService;

    PlayerState playerState = PlayerState.WALK;
    TileManager tileManager;

    private int spriteAnimCounter = 0;
    private int walkAnimSprite = 0;
    private final int maxHealth = 1;
    private int health = 1;
    private int currentFireChargeCount = 1;
    private final int maxFireChargeCount = 3;

    private PlayerSkin skin = PlayerSkin.RED;

    int lane = 1;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, TileManager tileManager, RequestService requestService) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.tileManager = tileManager;
        this.requestService = requestService;

        this.setDefaultValues();
        this.hitboxType = HitboxType.CIRCLE;
        this.hitboxReduceOffset = gamePanel.tileSize / 4; //reduce hitbox by 12 pixel
    }

    public void setDefaultValues() {
        this.x = 100;
        this.y = 300;
        this.speed = 4;
        this.lane = 0;
    }

    public void update() {
        moveLanes();
        updatePlayerState();
        checkPlayerHealth();
        walkAnimation();
        shootFireCharges();
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

    private void shootFireCharges() {
        if(currentFireChargeCount > 0 && keyHandler.rightKeyPressed) {
            gamePanel.abilityManager.getFireCharges().add(new FireCharge(gamePanel, tileManager, this.x, this.y + 12));
            currentFireChargeCount--;
            keyHandler.rightKeyPressed = false;
        }
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
        this.y = currentLanePositionY-(skin.getWalkSprites()[0].getHeight()/2 - 12);
    }

    public void draw(Graphics2D g2) {
        BufferedImage currentImage = null;
        switch(playerState) {
            case IDLE, DEAD, JUMP -> currentImage = skin.getWalkSprites()[0];
            case WALK -> {
                currentImage = skin.getWalkSprites()[walkAnimSprite];
            }
        }

        //g2.drawImage(currentImage, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
        g2.drawImage(currentImage, x, y, currentImage.getWidth()/2 - 12, currentImage.getHeight()/2 - 12, null);
    }

    private void walkAnimation() {

        if(spriteAnimCounter > skin.walkAnimFrameDuration) {
            if(walkAnimSprite < skin.numberOfWalkSprites-1) {
                walkAnimSprite++;
            } else {
                walkAnimSprite = 0;
            }

            spriteAnimCounter = 0;
        }

        spriteAnimCounter++;
    }

    private void checkPlayerHealth() {
        if(this.health <= 0) { //player died
            playerState = PlayerState.DEAD;
            gamePanel.ui.commandNum = 0;
            gamePanel.ui.minCommandNum = 0;
            gamePanel.ui.maxCommandNum = 2;
            gamePanel.gameState = GameState.GAMEOVER;
            gamePanel.stopMusic();

            if(requestService.isLoggedIn()) {
                System.out.println("UPDATING PLAYER STATS");
                requestService.updatePlayerStats((int) Math.max(gamePanel.score, requestService.getLoggedinPlayer().getHighscore()), gamePanel.currentlyCollectedGems);
            }
        }
    }

    public void resetPlayer() {
        this.health = 1;
        this.lane = 1;
        this.currentFireChargeCount = 1;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getCurrentFireChargeCount() {
        return currentFireChargeCount;
    }

    public void setCurrentFireChargeCount(int currentFireChargeCount) {
        this.currentFireChargeCount = currentFireChargeCount;
    }

    public int getMaxFireChargeCount() {
        return maxFireChargeCount;
    }

    public PlayerSkin getSkin() {
        return skin;
    }

    public void setSkin(PlayerSkin skin) {
        this.skin = skin;
    }

}
