package main;

import entity.Entity;
import entity.HitboxType;
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
        for(Enemy enemy : enemyManager.getEnemyList()) {
            if(checkCollisionBetweenEntities(player, enemy)) {
                player.setHealth(player.getHealth() - enemy.getDamage());
            }
        }
    }

    private boolean checkCollisionBetweenEntities(Entity entity1, Entity entity2) {
        if(entity1.getHitboxType() == HitboxType.CIRCLE || entity1.getHitboxType() == HitboxType.CIRCLE) {
            return checkCircleHitboxCollision(entity1, entity2);
        } else {
            return checkRectangleHitboxCollision(entity1, entity2);
        }
    }

    private boolean checkRectangleHitboxCollision(Entity entity1, Entity entity2) {
        int hitboxWidthHeight1 = gamePanel.tileSize - entity1.getHitboxReduceOffset();
        int hitboxWidthHeight2 = gamePanel.tileSize - entity2.getHitboxReduceOffset();

        if(entity1.getX() <= entity2.getX() + hitboxWidthHeight2 && entity1.getX() + hitboxWidthHeight1 >= entity2.getX() && entity1.getY() <= entity2.getY() + hitboxWidthHeight2 && hitboxWidthHeight1 + entity1.getY() >= entity2.getY()) {
            return true;
        }
        return false;
    }

    private boolean checkCircleHitboxCollision(Entity entity1, Entity entity2) {
        int hitboxWidthHeight1 = gamePanel.tileSize - entity1.getHitboxReduceOffset();
        int hitboxWidthHeight2 = gamePanel.tileSize - entity2.getHitboxReduceOffset();

        double dx = (entity1.getX() + hitboxWidthHeight1/2) - (entity2.getX() + hitboxWidthHeight2/2);
        double dy = (entity1.getY() + hitboxWidthHeight1/2) - (entity2.getY() + hitboxWidthHeight2/2);
        double distance = Math.sqrt(dx * dx + dy * dy);

        if(distance < hitboxWidthHeight1/2 + hitboxWidthHeight2/2) {
            return true;
        } else {
            return false;
        }
    }

}
