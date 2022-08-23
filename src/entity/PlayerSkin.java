package entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class PlayerSkin {

    protected int id;
    protected BufferedImage[] walkSprites;
    protected int numberOfWalkSprites = 4;
    protected int walkAnimFrameDuration = 6;

    protected int cost;

    public PlayerSkin(int id, int numberOfWalkSprites, int walkAnimFrameDuration) {
        this.id = id;
        this.numberOfWalkSprites = numberOfWalkSprites;
        this.walkAnimFrameDuration = walkAnimFrameDuration;
        this.loadSprites();
    }

    protected abstract void loadSprites();

    public BufferedImage[] getWalkSprites() {
        return this.walkSprites;
    }

    public static final PlayerSkin RED = new PlayerSkin(0,4, 6) {
        @Override
        protected void loadSprites() {
            walkSprites = new BufferedImage[numberOfWalkSprites];
            try {
                walkSprites[0] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/red/1.png"));
                walkSprites[1] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/red/2.png"));
                walkSprites[2] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/red/3.png"));
                walkSprites[3] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/red/1.png"));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static final PlayerSkin BLUE = new PlayerSkin(1, 4, 6) {
        @Override
        protected void loadSprites() {
            walkSprites = new BufferedImage[numberOfWalkSprites];
            try {
                walkSprites[0] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/blue/1.png"));
                walkSprites[1] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/blue/2.png"));
                walkSprites[2] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/blue/3.png"));
                walkSprites[3] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/blue/1.png"));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static final PlayerSkin GREEN = new PlayerSkin(2, 4, 6) {
        @Override
        protected void loadSprites() {
            walkSprites = new BufferedImage[numberOfWalkSprites];
            try {
                walkSprites[0] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/green/1.png"));
                walkSprites[1] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/green/2.png"));
                walkSprites[2] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/green/3.png"));
                walkSprites[3] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/green/1.png"));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static final PlayerSkin SILVER = new PlayerSkin(3, 4, 6) {
        @Override
        protected void loadSprites() {
            walkSprites = new BufferedImage[numberOfWalkSprites];
            try {
                walkSprites[0] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/silver/1.png"));
                walkSprites[1] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/silver/2.png"));
                walkSprites[2] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/silver/3.png"));
                walkSprites[3] = ImageIO.read(getClass().getResourceAsStream("/sprites/Players/skins/silver/1.png"));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static PlayerSkin[] getSkinWheel() {
        return new PlayerSkin[]{RED, BLUE, GREEN, SILVER};
    }

    public int getNumberOfWalkSprites() {
        return numberOfWalkSprites;
    }

    public int getWalkAnimFrameDuration() {
        return walkAnimFrameDuration;
    }

    public int getId() { return id; }
}
