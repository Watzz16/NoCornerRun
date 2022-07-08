package userinterface;

import main.GamePanel;
import main.GameState;
import services.RequestService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    private GamePanel gamePanel;
    private RequestService requestService;
    private Font gameFont;
    public String menuUsername = "";
    public String menuPassword = "";
    public int commandNum = 0;
    public int minCommandNum = 0;
    public int maxCommandNum = 3;

    //walk animation of player skin
    private int spriteAnimCounter = 0;
    private int walkAnimSprite = 1;
    private int walkAnimFrameDuration = 6;

    public UI(GamePanel gamePanel, RequestService requestService) {
        this.gamePanel = gamePanel;
        this.requestService = requestService;

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf");
            gameFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //this.commandNum = gamePanel.gameState == GameState.LOGINREGISTER ? 0: 4;
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
                drawKnowledge(g2);
            }
            case LOGINREGISTER -> {
                drawLoginRegisterScreen(g2);
            }
        }
    }

    private void drawScore(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20f));
        String text = "Score:" + (int) gamePanel.score;
        g2.drawString(text, (gamePanel.maxScreenCol-4)* gamePanel.tileSize, gamePanel.tileSize);
    }

    private void drawAvailableFireCharges(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20f));
        String text = gamePanel.player.getCurrentFireChargeCount() + "x";
        g2.drawString(text, (gamePanel.maxScreenCol-8)* gamePanel.tileSize, gamePanel.tileSize);
        BufferedImage fireChargeImage = null;
        try {
            fireChargeImage = ImageIO.read(getClass().getResourceAsStream("/sprites/Particles/fireball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(fireChargeImage, (gamePanel.maxScreenCol-8)* gamePanel.tileSize + 30, 12, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    private void drawKnowledge(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20f));
        String text = gamePanel.currentlyCollectedGems + "x";
        g2.drawString(text, (gamePanel.maxScreenCol-6) * gamePanel.tileSize - 24, gamePanel.tileSize);
        BufferedImage gemImage = null;
        try {
            gemImage = ImageIO.read(getClass().getResourceAsStream("/sprites/Items/magnifier.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(gemImage, (gamePanel.maxScreenCol-6)* gamePanel.tileSize + 30, 16, gamePanel.tileSize-12, gamePanel.tileSize-12, null);
    }

    private void drawGameOverScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72f));

        //shadow
        g2.setColor(Color.BLACK);
        String text = "Game Over";
        g2.drawString(text, getTextCenterX(g2, text), gamePanel.tileSize*4);

        //main game over text
        g2.setColor(Color.white);
        g2.drawString(text, getTextCenterX(g2, text)-4, gamePanel.tileSize*4-4);

        //final score
        g2.setFont(g2.getFont().deriveFont(24f));
        text = "Your score: " + (int) gamePanel.score;
        g2.drawString(text, getTextCenterX(g2, text), gamePanel.tileSize*6);

        //menu buttons
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));

        text = "RETRY";
        int x = getTextCenterX(g2, text);
        int y = gamePanel.tileSize * (gamePanel.maxScreenRow - 3);
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

        text = "MENU";
        x = getTextCenterX(g2, text);
        y = gamePanel.tileSize * (gamePanel.maxScreenRow - 2);
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

        text = "QUIT";
        x = getTextCenterX(g2, text);
        y = gamePanel.tileSize * (gamePanel.maxScreenRow - 1);
        g2.drawString(text, x, y);
        if(commandNum == 2) {
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

    }

    private void drawMenuScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 0));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        //title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
        String text = "No Corner Run";
        int x = getTextCenterX(g2, text);
        int y = gamePanel.tileSize * 2;

        //shadow
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);

        //actual title
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        drawLoggedInPlayerChar(g2);

        //menu buttons
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));

        text = "PLAY";
        x = getTextCenterX(g2, text);
        y = gamePanel.tileSize * (gamePanel.maxScreenRow - 2);
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

        text = "QUIT";
        x = getTextCenterX(g2, text);
        y = gamePanel.tileSize * (gamePanel.maxScreenRow - 1);
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

    }

    private void drawLoginRegisterScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 0));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        //title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
        String text = "No Corner Run";
        int x = getTextCenterX(g2, text);
        int y = gamePanel.tileSize * 2;

        //shadow
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);

        //actual title
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));

        //login components
        drawLoginRegisterComponents(g2);

        text = "LOGIN";
        x = getTextCenterX(g2, text);
        y = gamePanel.tileSize * (gamePanel.maxScreenRow - 2);
        g2.drawString(text, x, y);
        if(commandNum == 2) {
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

        text = "REGISTER";
        x = getTextCenterX(g2, text);
        y = gamePanel.tileSize * (gamePanel.maxScreenRow - 1);
        g2.drawString(text, x, y);
        if(commandNum == 3) {
            g2.drawString(">", x-gamePanel.tileSize, y);
        }


    }

    private void drawLoginRegisterComponents(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));

        String text = "username";
        Rectangle rect = new Rectangle(250,gamePanel.tileSize);
        int x = getRectCenterX(rect.width);
        int y = gamePanel.tileSize * (gamePanel.maxScreenRow - 8);
        g2.setColor(Color.blue);
        g2.fillRect(x, y, rect.width, rect.height);
        g2.setColor(Color.white);
        g2.drawString(text, x, y-5);
        g2.drawString(menuUsername, x+5, y+rect.height/2+12);
        if(commandNum == 0) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
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
        g2.drawString(menuPassword, x+5, y+rect.height/2+12);
        if(commandNum == 1) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
            g2.drawString(">", x - gamePanel.tileSize, y+rect.height/2+12);
        }
    }

    private void drawLoggedInPlayerChar(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));

        String text = "Logged in as " + requestService.getLoggedinPlayer().getPlayername();
        int x = getTextCenterX(g2, text);
        int y = gamePanel.tileSize * (gamePanel.maxScreenRow - 8);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        text = "Highscore: " + requestService.getLoggedinPlayer().getHighscore();
        x = getTextCenterX(g2, text);
        y = gamePanel.tileSize * (gamePanel.maxScreenRow - 7);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        this.drawPlayerSkin(g2);

    }

    private void drawPlayerSkin(Graphics2D g2) {
        BufferedImage currentImage = null;
        switch (walkAnimSprite) {
            case 1 -> currentImage = gamePanel.player.getWalkSprites()[0];
            case 2 -> currentImage = gamePanel.player.getWalkSprites()[1];
            case 3 -> currentImage = gamePanel.player.getWalkSprites()[2];
            case 4 -> currentImage = gamePanel.player.getWalkSprites()[3];
        }

        int x = getRectCenterX(currentImage.getWidth()/5);
        int y = gamePanel.tileSize * (gamePanel.maxScreenRow - 6) - 18;
        g2.drawImage(currentImage, x, y, currentImage.getWidth()/5, currentImage.getHeight()/5, null);
    }

    public void updateMenuWalkAnimation() {

        if(spriteAnimCounter > walkAnimFrameDuration) {
            if(walkAnimSprite < gamePanel.player.getWalkSprites().length) {
                walkAnimSprite++;
            } else {
                walkAnimSprite = 1;
            }

            spriteAnimCounter = 0;
        }

        spriteAnimCounter++;
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
