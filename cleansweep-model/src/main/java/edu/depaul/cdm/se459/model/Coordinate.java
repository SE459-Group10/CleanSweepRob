package edu.depaul.cdm.se459.model;

/**
 * Created by Suqing on 10/3/16.
 */
public class Coordinate {
    private int x, y;

    public Coordinate() {}

    public Coordinate(int y, int x) {
        this.x = x;
        this.y = y;
    }

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
}
