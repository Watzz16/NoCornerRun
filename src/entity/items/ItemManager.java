package entity.items;

import entity.Entity;
import entity.enemies.Enemy;
import main.GamePanel;
import tile.Tile;
import tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ItemManager {

    private List<Gem> gemList = new ArrayList<>();
    private double gemSecondCounter = 0;
    private int gemSpawnInterval = 10; //every 10 seconds spawn a gem with a chance of
    private double gemSpawnChance = 0.1; //10%

    private GamePanel gamePanel;
    private TileManager tileManager;

    public ItemManager(GamePanel gamePanel, TileManager tileManager) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
    }

    public void draw(Graphics2D g2) {
        //draw gems
        for(Iterator<Gem> iterator = gemList.iterator(); iterator.hasNext();) {
            Gem gem = iterator.next();
            gem.draw(g2);
        }
    }

    public void updateItems() {
        this.updateGemGenerator();
        this.updateGems();
    }

    private void updateGemGenerator() {
        this.gemSecondCounter += 1.0/gamePanel.FPS;

        if(gemSecondCounter >= gemSpawnInterval) {
            double rng = Math.random();
            if(rng <= gemSpawnChance) {
                System.out.println("Spawned Gem!");
                int randomLaneIndex = ThreadLocalRandom.current().nextInt(0, tileManager.getLanes().length);
                int randomXoffset = ThreadLocalRandom.current().nextInt(0, gamePanel.tileSize * 3);
                gemList.add(new Gem(gamePanel, tileManager, randomLaneIndex, (gamePanel.maxScreenCol*gamePanel.tileSize) + randomXoffset, 0));
            }

            gemSecondCounter = 0;
        }
    }

    private void updateGems() {
        for(Iterator<Gem> iterator = gemList.iterator(); iterator.hasNext();) {
            Gem gem = iterator.next();
            gem.update();
            if((gem.getX() + gamePanel.tileSize) <= 0) {
                iterator.remove();
            }
        }
    }

    public void resetItems() {
        this.gemList.clear();
    }

}
