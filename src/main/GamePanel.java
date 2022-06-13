package main;

import entity.Player;
import entity.enemies.EnemyManager;
import entity.items.ItemManager;
import sound.Sound;
import tile.LevelManager;
import tile.TileManager;
import userinterface.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {

    //screen settings
    final int originalTileSize = 16; //how many pixels is one tile? 16x16
    final int scale = 3; //how large do we want to scale one tile? 3x -> 48x48 pixel

    public final int tileSize = originalTileSize * scale; //48x48 pixel
    public final int maxScreenCol = 16; //how many tiles in width? Columns
    public final int maxScreenRow = 12; //how many tiles in height? Rows
    public final int screenWidth = tileSize * maxScreenCol; //calculate the pixel width of the window
    public final int screenHeight = tileSize * maxScreenRow; //calculate the pixel height of the window

    public double score = 0;

    public final int FPS = 60;

    KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;

    Background background = new Background();
    TileManager tileManager = new TileManager(this);
    Player player = new Player(this, this.keyHandler, tileManager);

    EnemyManager enemyManager = new EnemyManager(this, tileManager);
    ItemManager itemManager = new ItemManager(this, tileManager);

    LevelManager levelManager = new LevelManager(this, tileManager, enemyManager, itemManager);

    public UI ui = new UI(this);

    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public GameState gameState = GameState.RUNNING;

    Sound sound = new Sound();

    //this is the constructor. It's a method that is called once a new object of that class is created. It constructs the object
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        this.playMusic();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawIntervalNanoseconds = 1000000000/FPS; //1 second divided by the number of frames we want per second. Here: 1/60
        double delta = 0;
        long previousTime = System.nanoTime();
        long currentTime;

        //calculate the fps we reached
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            //this calculates the time delta between the previous update and the current one
            //by using the drawIntervalNanoseconds, we determine how many frames we missed drawing
            delta += (currentTime - previousTime) / drawIntervalNanoseconds;
            timer += (currentTime - previousTime);

            previousTime = currentTime;

            //if we missed drawing frames, do that now for one frame and reduce delta by one
            if(delta > 0) {
                update(); //update information like player position
                repaint(); //redraw screen with updated information
                delta--; //reduce delta until we have a delta of 0 (no missed frames)
                drawCount++; //we draw one frame in here
            }

            //calculate the number of frames we have drawn in one second
            if(timer >= 1000000000) { //we reached one second
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {
        if(gameState == GameState.GAMEOVER) return;

        player.update();
        tileManager.update();
        enemyManager.update();
        itemManager.updateItems();
        collisionChecker.checkPlayerCollisionWithEnemies(player, enemyManager);
        levelManager.update();

        score += 1.0/FPS; //update score relative to fps
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        background.draw(g2);
        tileManager.draw(g2);
        itemManager.draw(g2);

        enemyManager.draw(g2);
        player.draw(g2);

        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic() {
        sound.setFile(0);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSoundEffect(int index) {
        sound.setFile(index);
        sound.play();
    }

}
