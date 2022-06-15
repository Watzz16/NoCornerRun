package entity.items;

import main.GamePanel;
import tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GemManager {

    private List<Gem> gemList = new ArrayList<>();
    private double gemSecondCounter = 0;
    private int gemSpawnInterval = 10; //every 10 seconds spawn a gem with a chance of
    private double gemSpawnChance = 1; //10%

    private GamePanel gamePanel;
    private TileManager tileManager;

    public GemManager(GamePanel gamePanel, TileManager tileManager) {
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
                gemList.add(new Gem(gamePanel, tileManager, randomLaneIndex, xSpawnPos, 0));
            }

            gemSecondCounter = 0;
        }
    }

    public void updateGems() {
        for(Iterator<Gem> iterator = gemList.iterator(); iterator.hasNext();) {
            Gem gem = iterator.next();
            gem.update();
            if((gem.getX() + gamePanel.tileSize) <= 0) {
                iterator.remove();
            }
        }
    }

    public void drawGems(Graphics2D g2) {
        for(Iterator<Gem> iterator = gemList.iterator(); iterator.hasNext();) {
            Gem gem = iterator.next();
            gem.draw(g2);
        }
    }

    public List<Gem> getGemList() {
        return gemList;
    }

}
