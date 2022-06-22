package entity.items;

import main.GamePanel;
import tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MagnifierManager {

    private List<Magnifier> magnifierList = new ArrayList<>();
    private double gemSecondCounter = 0;
    private int gemSpawnInterval = 10; //every 10 seconds spawn a gem with a chance of
    private double gemSpawnChance = 1; //10%

    private GamePanel gamePanel;
    private TileManager tileManager;

    public MagnifierManager(GamePanel gamePanel, TileManager tileManager) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
    }

    public void updateGemGenerator() {
        this.gemSecondCounter += 1.0/gamePanel.FPS;

        if(gemSecondCounter >= gemSpawnInterval) {
            double rng = Math.random();
            if(rng <= gemSpawnChance) {
                int randomLaneIndex = ThreadLocalRandom.current().nextInt(0, tileManager.getLanes().length);
                int xSpawnPos = tileManager.getLanes()[randomLaneIndex].getXPosOfRightmostTile() + gamePanel.tileSize + 3;
                magnifierList.add(new Magnifier(gamePanel, tileManager, randomLaneIndex, xSpawnPos, 0));
            }

            gemSecondCounter = 0;
        }
    }

    public void updateGems() {
        for(Iterator<Magnifier> iterator = magnifierList.iterator(); iterator.hasNext();) {
            Magnifier magnifier = iterator.next();
            magnifier.update();
            if((magnifier.getX() + gamePanel.tileSize) <= 0) {
                iterator.remove();
            }
        }
    }

    public void drawGems(Graphics2D g2) {
        for(Iterator<Magnifier> iterator = magnifierList.iterator(); iterator.hasNext();) {
            Magnifier magnifier = iterator.next();
            magnifier.draw(g2);
        }
    }

    public List<Magnifier> getGemList() {
        return magnifierList;
    }

}
