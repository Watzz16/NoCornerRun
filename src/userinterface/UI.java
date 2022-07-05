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
    public int commandNum = 0;
    public final int maxCommands = 4;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gameFont = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2) {
        g2.setFont(gameFont);

        switch(gamePanel.gameState) {
            case MAINMENU -> {
                drawMenuScreen(g2);
            }
            case GAMEOVER -> {
                drawGameOverScreen(g2);
            }
            case RUNNING -> {
                drawScore(g2);
                drawAvailableFireCharges(g2);
                drawGems(g2);
            }
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
        g2.drawString(text, (gamePanel.maxScreenCol-6)* gamePanel.tileSize, gamePanel.tileSize);
        BufferedImage fireChargeImage = null;
        try {
            fireChargeImage = ImageIO.read(getClass().getResourceAsStream("/sprites/Particles/fireball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(fireChargeImage, (gamePanel.maxScreenCol-6)* gamePanel.tileSize + 10, 16, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    private void drawGems(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20f));
        String text = gamePanel.currentlyCollectedGems + "x";
        g2.drawString(text, (gamePanel.maxScreenCol-4)* gamePanel.tileSize - 24, gamePanel.tileSize);
        BufferedImage gemImage = null;
        try {
            gemImage = ImageIO.read(getClass().getResourceAsStream("/sprites/Items/gemBlue.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(gemImage, (gamePanel.maxScreenCol-4)* gamePanel.tileSize - 10, 16, gamePanel.tileSize, gamePanel.tileSize, null);
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

    private void drawMenuScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 0));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        //title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
        String text = "No Corner Run";
        int x = getTextCenterX(g2, text);
        int y = gamePanel.tileSize * 2;

        //shadow
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);

        //actual title
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //login components
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));

        text = "username";
        Rectangle rect = new Rectangle(250,gamePanel.tileSize);
        x = getRectCenterX(rect.width);
        y = gamePanel.tileSize * (gamePanel.maxScreenRow - 8);
        g2.setColor(Color.blue);
        g2.fillRect(x, y, rect.width, rect.height);
        g2.setColor(Color.white);
        g2.drawString(text, x, y-5);
        if(commandNum == 0) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
            g2.drawString(">", x - gamePanel.tileSize, y+rect.height/2+12);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));

        text = "password";
        rect = new Rectangle(250,gamePanel.tileSize);
        x = getRectCenterX(rect.width);
        y = gamePanel.tileSize * (gamePanel.maxScreenRow - 6);
        g2.setColor(Color.blue);
        g2.fillRect(x, y, rect.width, rect.height);
        g2.setColor(Color.white);
        g2.drawString(text, x, y-5);
        if(commandNum == 1) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
            g2.drawString(">", x - gamePanel.tileSize, y+rect.height/2+12);
        }

        //menu buttons
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));

        text = "LOGIN";
        x = getTextCenterX(g2, text);
        y = gamePanel.tileSize * (gamePanel.maxScreenRow - 4);
        g2.drawString(text, x, y);
        if(commandNum == 2) {
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

        text = "PLAY";
        x = getTextCenterX(g2, text);
        y = gamePanel.tileSize * (gamePanel.maxScreenRow - 2);
        g2.drawString(text, x, y);
        if(commandNum == 3) {
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

        text = "QUIT";
        x = getTextCenterX(g2, text);
        y = gamePanel.tileSize * (gamePanel.maxScreenRow - 1);
        g2.drawString(text, x, y);
        if(commandNum == 4) {
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

    }

    private int getTextCenterX(Graphics2D g2, String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gamePanel.screenWidth / 2 - length / 2;
        return x;
    }

    private int getRectCenterX(int width) {
        int x = gamePanel.screenWidth / 2 - width / 2;
        return x;
    }

}
