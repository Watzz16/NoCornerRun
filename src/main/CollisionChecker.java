package main;

import entity.Entity;
import entity.HitboxType;
import entity.Player;
import entity.enemies.Enemy;
import entity.enemies.EnemyManager;
import entity.items.Magnifier;
import entity.items.ItemCrate;
import entity.items.ItemManager;
import entity.particles.AbilityManager;
import entity.particles.FireCharge;

import java.util.Iterator;

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

    public void checkPlayerCollisionWithItems(Player player, ItemManager itemManager) {
        for(Iterator<Magnifier> iterator = itemManager.getGemManager().getGemList().iterator(); iterator.hasNext();) {
            Magnifier magnifier = iterator.next();
            if(checkCollisionBetweenEntities(player, magnifier)) {
                iterator.remove();
                gamePanel.currentlyCollectedGems++;
            }
        }
        for(Iterator<ItemCrate> iterator = itemManager.getItemCrateManager().getItemCrateList().iterator(); iterator.hasNext();) {
            ItemCrate itemCrate = iterator.next();
            if(checkCollisionBetweenEntities(player, itemCrate) && player.getCurrentFireChargeCount() < player.getMaxFireChargeCount()) {
                iterator.remove();
                if(player.getCurrentFireChargeCount() < player.getMaxFireChargeCount()) {
                    player.setCurrentFireChargeCount(player.getCurrentFireChargeCount()+1);
                }
            }
        }
    }

    //O(n^2) time complexity
    public void checkEnemyCollisionWithParticles(EnemyManager enemyManager, AbilityManager abilityManager) {
        for(Iterator<FireCharge> fireChargeIterator = abilityManager.getFireCharges().iterator(); fireChargeIterator.hasNext();) {
            FireCharge fireCharge = fireChargeIterator.next();

            for(Iterator<Enemy> enemyIterator = enemyManager.getEnemyList().iterator(); enemyIterator.hasNext();) {
                Enemy enemy = enemyIterator.next();

                if(checkCollisionBetweenEntities(fireCharge, enemy)) {
                    fireChargeIterator.remove();
                    enemyIterator.remove();
                    break;
                }
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
