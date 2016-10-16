package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;

import java.awt.*;

/**
 * Created by Suqing on 10/3/16.
 */
public class WallCell extends Cell {

    public WallCell(Coordinate coordinate) {
        super(Utility.WALL_COLOR, coordinate);
    }

}
