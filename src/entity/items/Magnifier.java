package entity.items;

import entity.HitboxType;
import main.GamePanel;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Magnifier extends Item {

    private int floatAnimFrameCounter = 0;
    private int floatAnimUpdateNeededFrames = 5;
    private int floatAnimOffset = 0;
    private final int floatAnimDeviationFromZero = 3;
    private boolean floatAnimUp = true;

    public Magnifier(GamePanel gamePanel, TileManager tileManager, int laneIndex, int startXposition, int speed) {
        super(gamePanel, tileManager, laneIndex, startXposition, speed);
        this.hitboxType = HitboxType.CIRCLE;
    }

    @Override
    protected void loadSprite() {
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream("/sprites/Items/magnifier.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        this.x -= tileManager.getLanes()[laneIndex].getLaneSpeed();
        int currentLanePositionY = tileManager.getLanes()[laneIndex].getLaneYPosition();
        this.y = currentLanePositionY - gamePanel.tileSize + floatAnimOffset;

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
