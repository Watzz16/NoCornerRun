package main;

import entity.Entity;
import entity.Player;
import entity.enemies.Enemy;
import entity.enemies.EnemyManager;

import java.util.List;

public class CollisionChecker {

    private GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkPlayerCollisionWithEnemies(Player player, EnemyManager enemyManager) {
        int tileWidthHeight = gamePanel.tileSize;

        for(Enemy enemy : enemyManager.getEnemyList()) {
            if(player.getX() <= enemy.getX() + tileWidthHeight && player.getX() + tileWidthHeight >= enemy.getX() && player.getY() <= enemy.getY() + tileWidthHeight && tileWidthHeight + player.getY() >= enemy.getY()) {
                player.setHealth(player.getHealth() - enemy.getDamage());
            }
        }
    }

}
