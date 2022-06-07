package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage texture;
    public boolean collision;
    private int x, y;

    public Tile(BufferedImage texture, int x, int y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
