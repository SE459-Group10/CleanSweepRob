package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;

import java.awt.*;

/**
 * Created by Suqing on 10/14/16.
 */

/**
 * A subclass of the class Cell that check if a charging station exist in the cell
 * contains the coordinates of Charging Station and their color
  */
public class StationCell extends Cell {
    private Coordinate coordinate;
    private boolean isStartStation;

    public StationCell(Coordinate coordinate) {
//        super();
//        this.coordinate = coordinate;
//        this.setOpaque(true);
//        this.setBackground(Utility.CHARGING_STATION_COLOR);
        super(Utility.CHARGING_STATION_COLOR, coordinate);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public boolean isStartStation() {
        return isStartStation;
    }

    public void setStartStation(boolean startStation) {
        isStartStation = startStation;
    }
}
