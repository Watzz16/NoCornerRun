package userinterface;

import main.GamePanel;
import main.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {
    GamePanel gamePanel;
    Font gameFont;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gameFont = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2) {
        g2.setFont(gameFont);

        if(gamePanel.gameState == GameState.GAMEOVER) drawGameOverScreen(g2);
        if(gamePanel.gameState == GameState.RUNNING) {
            drawScore(g2);
            drawAvailableFireCharges(g2);
        }
    }

    private void drawScore(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20f));
        String text = "Score: " + (int) gamePanel.score;
        g2.drawString(text, (gamePanel.maxScreenCol-3)* gamePanel.tileSize, gamePanel.tileSize);
    }

    private void drawAvailableFireCharges(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20f));
        String text = gamePanel.player.getCurrentFireChargeCount() + "x";
        g2.drawString(text, (gamePanel.maxScreenCol-5)* gamePanel.tileSize, gamePanel.tileSize);
        BufferedImage fireChargeImage = null;
        try {
            fireChargeImage = ImageIO.read(getClass().getResourceAsStream("/sprites/Particles/fireball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage(fireChargeImage, (gamePanel.maxScreenCol-5)* gamePanel.tileSize + 10, 16, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    private void drawGameOverScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        //shadow
        g2.setColor(Color.BLACK);
        String text = "Game Over";
        g2.drawString(text, getTextCenterX(g2, text), gamePanel.tileSize*4);

        //main game over text
        g2.setColor(Color.white);
        g2.drawString(text, getTextCenterX(g2, text)-4, gamePanel.tileSize*4-4);

        //final score
        g2.setFont(g2.getFont().deriveFont(30f));
        text = "Your score: " + (int) gamePanel.score;
        g2.drawString(text, getTextCenterX(g2, text), gamePanel.tileSize*6);

        //retry option
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Press Enter to retry";
        g2.drawString(text, getTextCenterX(g2, text), gamePanel.tileSize*10);
    }

    private int getTextCenterX(Graphics2D g2, String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gamePanel.screenWidth / 2 - length / 2;
        return x;
    }

}
