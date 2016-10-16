package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;

import java.awt.*;

/**
 * Created by Suqing on 10/14/16.
 */
public class DoorCell extends Cell {

    private boolean isOpen;
    private Coordinate coordinate;

    public DoorCell(Coordinate coordinate, boolean isOpen) {
        super();
        this.isOpen = isOpen;
        this.coordinate = coordinate;
        this.setOpaque(true);
        this.setBackground(Utility.OPEN_DOOR_COLOR);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
