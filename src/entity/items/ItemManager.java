package entity.items;

import main.GamePanel;
import tile.TileManager;

import java.awt.*;

public class ItemManager {

    private GamePanel gamePanel;
    private TileManager tileManager;

    private GemManager gemManager;
    private ItemCrateManager itemCrateManager;

    public ItemManager(GamePanel gamePanel, TileManager tileManager) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;

        this.gemManager = new GemManager(gamePanel, tileManager);
        this.itemCrateManager = new ItemCrateManager(gamePanel, tileManager);
    }

    public void draw(Graphics2D g2) {
        gemManager.drawGems(g2);
        itemCrateManager.drawItemCrates(g2);
    }

    public void updateItems() {
        gemManager.updateGemGenerator();
        gemManager.updateGems();

        itemCrateManager.updateItemCrateGenerator();
        itemCrateManager.updateItemsCrates();
    }

    public void resetItems() {
        gemManager.getGemList().clear();
        itemCrateManager.getItemCrateList().clear();
    }

    public GemManager getGemManager() {
        return gemManager;
    }

    public ItemCrateManager getItemCrateManager() {
        return itemCrateManager;
    }
}
