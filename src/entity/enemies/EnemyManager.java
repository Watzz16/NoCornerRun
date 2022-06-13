package entity.enemies;

import main.GamePanel;
import tile.Tile;
import tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyManager {

    private List<Enemy> enemyList = new ArrayList<>();
    private GamePanel gamePanel;
    private TileManager tileManager;
    private int concurrentEnemyCount = 3;
    private int minSpeed = 0;
    private int maxSpeed = 3;

    public EnemyManager(GamePanel gamePanel, TileManager tileManager) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
    }

    public void draw(Graphics2D g2) {
        for(Iterator<Enemy> iterator = enemyList.iterator(); iterator.hasNext();) {
            Enemy enemy = iterator.next();
            enemy.draw(g2);
        }
    }

    public void update() {
        for(Iterator<Enemy> iterator = enemyList.iterator(); iterator.hasNext();) {
            Enemy enemy = iterator.next();
            enemy.update();
            if((enemy.getX() + gamePanel.tileSize) <= 0) {
                iterator.remove();
            }
        }
        generateRandomEnemies();
    }

    private void generateRandomEnemies() {
        if(enemyList.size() < concurrentEnemyCount) {
            int randomLaneIndex = ThreadLocalRandom.current().nextInt(0, tileManager.getLanes().length);
            int randomSpeed = ThreadLocalRandom.current().nextInt(minSpeed, maxSpeed+1); //inclusive of max -> add 1
            int randomXoffset = ThreadLocalRandom.current().nextInt(0, gamePanel.tileSize * 3); //maximum of 3 tiles of random offset for x spawn coordinate
            enemyList.add(new SawEnemy(tileManager, gamePanel, randomLaneIndex, (gamePanel.maxScreenCol*gamePanel.tileSize) + randomXoffset, randomSpeed));
        }
    }

    public int getConcurrentEnemyCount() {
        return concurrentEnemyCount;
    }

    public void setConcurrentEnemyCount(int concurrentEnemyCount) {
        this.concurrentEnemyCount = concurrentEnemyCount;
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(int minSpeed) {
        this.minSpeed = minSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public void resetEnemies() {
        this.enemyList.clear();
    }

}
