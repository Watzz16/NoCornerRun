package entity.items;

import main.GamePanel;
import tile.TileManager;

import java.awt.*;

public class ItemManager {

    private GamePanel gamePanel;
    private TileManager tileManager;

    private MagnifierManager magnifierManager;
    private ItemCrateManager itemCrateManager;

    public ItemManager(GamePanel gamePanel, TileManager tileManager) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;

        this.magnifierManager = new MagnifierManager(gamePanel, tileManager);
        this.itemCrateManager = new ItemCrateManager(gamePanel, tileManager);
    }

    public void draw(Graphics2D g2) {
        magnifierManager.drawGems(g2);
        itemCrateManager.drawItemCrates(g2);
    }

    public void updateItems() {
        magnifierManager.updateGemGenerator();
        magnifierManager.updateGems();

        itemCrateManager.updateItemCrateGenerator();
        itemCrateManager.updateItemsCrates();
    }

    public void resetItems() {
        magnifierManager.getGemList().clear();
        itemCrateManager.getItemCrateList().clear();
    }

    public MagnifierManager getGemManager() {
        return magnifierManager;
    }

    public ItemCrateManager getItemCrateManager() {
        return itemCrateManager;
    }
}
