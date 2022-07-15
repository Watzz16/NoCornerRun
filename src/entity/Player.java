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

    BufferedImage[] walkImages;
    PlayerState playerState = PlayerState.WALK;
    TileManager tileManager;

    private int spriteAnimCounter = 0;
    private int walkAnimSprite = 1;
    private int walkAnimFrameDuration = 6;
    private int numberOfWalkSprites = 4;
    private final int maxHealth = 1;
    private int health = 1;
    private int currentFireChargeCount = 1;
    private final int maxFireChargeCount = 3;

    int lane = 1;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, TileManager tileManager, RequestService requestService) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.tileManager = tileManager;
        this.requestService = requestService;

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
        walkImages = new BufferedImage[numberOfWalkSprites];
        try {
            walkImages[0] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/red/1.png"));
            walkImages[1] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/red/2.png"));
            walkImages[2] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/red/3.png"));
            walkImages[3] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/red/1.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
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
        this.y = currentLanePositionY-(walkImages[0].getHeight()/2 - 12);
    }

    public void draw(Graphics2D g2) {
        BufferedImage currentImage = null;
        switch(playerState) {
            case IDLE, DEAD, JUMP -> currentImage = walkImages[0];
            case WALK -> {
                switch (walkAnimSprite) {
                    case 1 -> currentImage = walkImages[0];
                    case 2 -> currentImage = walkImages[1];
                    case 3 -> currentImage = walkImages[2];
                    case 4 -> currentImage = walkImages[3];
                }
            }
        }

        //g2.drawImage(currentImage, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
        g2.drawImage(currentImage, x, y, currentImage.getWidth()/2 - 12, currentImage.getHeight()/2 - 12, null);
    }

    private void walkAnimation() {

        if(spriteAnimCounter > walkAnimFrameDuration) {
            if(walkAnimSprite < numberOfWalkSprites) {
                walkAnimSprite++;
            } else {
                walkAnimSprite = 1;
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

    public BufferedImage[] getWalkSprites() {
        return this.walkImages;
    }
}
