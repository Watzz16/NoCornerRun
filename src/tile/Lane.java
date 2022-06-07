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

    public Lane(int yPosition, GamePanel gamePanel) {
        this.yPosition = yPosition;
        this.gamePanel = gamePanel;
        loadTileImages();
    }

    private void loadTileImages() {
        tiles = new Tile[gamePanel.maxScreenCol+1];

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
        for(Tile tile : tiles) {
            tile.setX(tile.getX() - laneSpeed);

            if( (tile.getX() + gamePanel.tileSize) <= 0) {
                tile.setX(gamePanel.maxScreenCol * gamePanel.tileSize);
            }
        }
    }

    public void draw(Graphics2D g2) {
        for(Tile tile : tiles) {
            g2.drawImage(tile.texture, tile.getX(), tile.getY(), gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

    public int getLaneYPosition() {
        return this.yPosition;
    }

}
