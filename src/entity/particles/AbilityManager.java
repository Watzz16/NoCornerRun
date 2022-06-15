package entity.particles;

import main.GamePanel;
import tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AbilityManager {

    private List<FireCharge> fireCharges = new ArrayList<>();

    private GamePanel gamePanel;
    private TileManager tileManager;

    public AbilityManager(GamePanel gamePanel, TileManager tileManager) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
    }

    public void updateFireCharges() {
        for(Iterator<FireCharge> iterator = fireCharges.iterator(); iterator.hasNext();) {
            FireCharge fireCharge = iterator.next();
            fireCharge.update();
            if((fireCharge.getX()) >= gamePanel.screenWidth) { //fire charge left the screen
                iterator.remove();
            }
        }
    }

    public void drawFireCharges(Graphics2D g2) {
        for(Iterator<FireCharge> iterator = fireCharges.iterator(); iterator.hasNext();) {
            FireCharge fireCharge = iterator.next();
            fireCharge.draw(g2);
        }
    }

    public List<FireCharge> getFireCharges() {
        return fireCharges;
    }

}
