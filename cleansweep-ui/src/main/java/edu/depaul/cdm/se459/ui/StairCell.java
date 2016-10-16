package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;

import java.awt.*;

/**
 * Created by Suqing on 10/14/16.
 */
public class StairCell extends Cell {

    private Coordinate coordinate;

    public StairCell(Coordinate coordinate) {
        super();
        this.coordinate = coordinate;
        this.setOpaque(true);
        this.setBackground(Utility.STAIRS_COLOR);
    }

}
