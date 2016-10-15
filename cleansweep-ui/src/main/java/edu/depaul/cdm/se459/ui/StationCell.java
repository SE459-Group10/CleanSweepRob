package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;

import java.awt.*;

/**
 * Created by Suqing on 10/14/16.
 */
public class StationCell extends Cell {

    private final static Color CHARGING_STATION_COLOR = Color.GREEN;
    private Coordinate coordinate;

    public StationCell(Coordinate coordinate) {
        super();
        this.setOpaque(true);
        this.setBackground(CHARGING_STATION_COLOR);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
