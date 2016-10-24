package edu.depaul.cdm.se459.model;

/**
 * Created by Suqing on 10/23/16.
 */
public enum CellStatus {
    VISITEDFLOORCELL(100), UNVISITEDFLOORCELL(200), OBSTACLE(300);

    private final int id;
    CellStatus(int id) {this.id = id;}

    public int getValue() {
        return this.id;
    }

    @Override
    public String toString() {
        switch (this) {
            case VISITEDFLOORCELL: return "Visited";
            case UNVISITEDFLOORCELL: return "Unvisited";
            case OBSTACLE: return "Obstacle";
            default: return "Null";
        }
    }
}
