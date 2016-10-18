package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;

import java.awt.*;

/**
 * Created by Suqing on 10/14/16.
 */

/**
 * A subclass of the class Cell that contains the coordinates of Stairs and
 * their color
 */
public class StairCell extends Cell {

	private Coordinate coordinate;

	public StairCell(Coordinate coordinate) {
		super(Utility.STAIRS_COLOR, coordinate);
	}



}
