package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Lane {

    private int yPosition;
    private Tile[] tiles;
    private GamePanel gamePanel;
    private int laneSpeed = 3;

    private Tile endingTile = null;

    public Lane(int yPosition, GamePanel gamePanel) {
        this.yPosition = yPosition;
        this.gamePanel = gamePanel;
        loadTileImages();
    }

    private void loadTileImages() {
        tiles = new Tile[gamePanel.maxScreenCol+2];
        endingTile = tiles[gamePanel.maxScreenCol-1];

        BufferedImage grassSprite;
        try {
            grassSprite = ImageIO.read(getClass().getResourceAsStream("/sprites/Ground/Grass/grass.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile(grassSprite, i * gamePanel.tileSize, yPosition);
        }
    }

    public void update() {
        for(int i = 0; i < tiles.length; i++) {
            tiles[i].setX(tiles[i].getX() - laneSpeed);

            if( (tiles[i].getX() + gamePanel.tileSize) <= 0) {
                tiles[i].setX((gamePanel.maxScreenCol+1) * gamePanel.tileSize + tiles[i].getX()); //adding x coordinate in case current x is less than 0
                endingTile = tiles[i];
            }
        }
    }

    public void draw(Graphics2D g2) {
        for(Tile tile : tiles) {
            g2.drawImage(tile.texture, tile.getX(), tile.getY(), gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

    public int getXPosOfRightmostTile() {
        return endingTile.getX();
    }

    public int getLaneYPosition() {
        return this.yPosition;
    }

    public int getLaneSpeed() {
        return this.laneSpeed;
    }

    public void setLaneSpeed(int laneSpeed) {
        this.laneSpeed = laneSpeed;
    }

}
