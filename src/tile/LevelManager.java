package tile;

import entity.enemies.EnemyManager;
import entity.items.ItemManager;
import main.GamePanel;

public class LevelManager {

    private GamePanel gamePanel;
    private TileManager tileManager;
    private EnemyManager enemyManager;
    private ItemManager itemManager;

    private int level = 1;

    public LevelManager(GamePanel gamePanel, TileManager tileManager, EnemyManager enemyManager, ItemManager itemManager) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        this.enemyManager = enemyManager;
        this.itemManager = itemManager;
    }

    public void update() {
        if((int) gamePanel.score == 0) {
            setToLevel1();
        } else if((int) gamePanel.score == 30) {
            setToLevel2();
        } else if((int) gamePanel.score == 50) {
            setToLevel3();
        } else if((int) gamePanel.score == 70) {
            setToLevel4();
        } else if((int) gamePanel.score == 100) {
            setToLevel5();
        }
    }

    private void setToLevel1() {
        for(Lane lane : tileManager.getLanes()) lane.setLaneSpeed(3); //3
        enemyManager.setConcurrentEnemyCount(3); //3
        enemyManager.setConcurrentSquidCount(0); //0
        enemyManager.setMinSpeed(0); //0
        enemyManager.setMaxSpeed(0); //ß
    }

    private void setToLevel2() {
        for(Lane lane : tileManager.getLanes()) lane.setLaneSpeed(4);
        enemyManager.setConcurrentEnemyCount(3);
        enemyManager.setConcurrentSquidCount(1);
        enemyManager.setMinSpeed(0);
        enemyManager.setMaxSpeed(1);
    }

    private void setToLevel3() {
        for(Lane lane : tileManager.getLanes()) lane.setLaneSpeed(5);
        enemyManager.setConcurrentEnemyCount(4);
        enemyManager.setConcurrentSquidCount(2);
        enemyManager.setMinSpeed(0);
        enemyManager.setMaxSpeed(2);
    }

    private void setToLevel4() {
        for(Lane lane : tileManager.getLanes()) lane.setLaneSpeed(7);
        enemyManager.setConcurrentEnemyCount(3);
        enemyManager.setConcurrentSquidCount(3);
        enemyManager.setMinSpeed(1);
        enemyManager.setMaxSpeed(4);
    }

    private void setToLevel5() {
        for(Lane lane : tileManager.getLanes()) lane.setLaneSpeed(8);
        enemyManager.setConcurrentEnemyCount(4);
        enemyManager.setConcurrentSquidCount(4);
        enemyManager.setMinSpeed(3);
        enemyManager.setMaxSpeed(5);
    }


}
