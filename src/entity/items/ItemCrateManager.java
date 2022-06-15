package entity.items;

import main.GamePanel;
import tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ItemCrateManager {

    private List<ItemCrate> itemCrateList = new ArrayList<>();
    private double itemCrateSecondCounter = 0;
    private int itemCrateSpawnInterval = 5; //every 10 seconds spawn an itemCrate with a chance of
    private double itemCrateSpawnChance = 0.4; //40%

    private GamePanel gamePanel;
    private TileManager tileManager;

    public ItemCrateManager(GamePanel gamePanel, TileManager tileManager) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
    }

    public void updateItemCrateGenerator() {
        this.itemCrateSecondCounter += 1.0/gamePanel.FPS;

        if(itemCrateSecondCounter >= itemCrateSpawnInterval) {
            double rng = Math.random();
            if(rng <= itemCrateSpawnChance) {
                int randomLaneIndex = ThreadLocalRandom.current().nextInt(0, tileManager.getLanes().length);
                int xSpawnPos = tileManager.getLanes()[randomLaneIndex].getXPosOfRightmostTile() + gamePanel.tileSize + 3; //can't wrap my head around why the items are not centered
                itemCrateList.add(new ItemCrate(gamePanel, tileManager, randomLaneIndex, xSpawnPos, 0));
            }

            itemCrateSecondCounter = 0;
        }
    }

    public void updateItemsCrates() {
        for(Iterator<ItemCrate> iterator = itemCrateList.iterator(); iterator.hasNext();) {
            ItemCrate itemCrate = iterator.next();
            itemCrate.update();
            if((itemCrate.getX() + gamePanel.tileSize) <= 0) {
                iterator.remove();
            }
        }
    }

    public void drawItemCrates(Graphics2D g2) {
        for(Iterator<ItemCrate> iterator = itemCrateList.iterator(); iterator.hasNext();) {
            ItemCrate itemCrate = iterator.next();
            itemCrate.draw(g2);
        }
    }

    public List<ItemCrate> getItemCrateList() {
        return itemCrateList;
    }

}
