package entity;

import java.awt.*;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int speed;
    protected HitboxType hitboxType;
    protected int hitboxReduceOffset = 0;

    protected abstract void draw(Graphics2D g2);
    protected abstract void update();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public HitboxType getHitboxType() {
        return hitboxType;
    }

    public void setHitboxType(HitboxType hitboxType) {
        this.hitboxType = hitboxType;
    }

    public int getHitboxReduceOffset() {
        return hitboxReduceOffset;
    }

    public void setHitboxReduceOffset(int hitboxReduceOffset) {
        this.hitboxReduceOffset = hitboxReduceOffset;
    }
}
