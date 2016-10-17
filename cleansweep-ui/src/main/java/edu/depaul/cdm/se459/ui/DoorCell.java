package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;

import java.awt.*;

/**
 * Created by Suqing on 10/14/16.
 */

/**
 * A subclass of the class Cell that contains the coordinates of a door
 * existence and if a door is open or closed and the door color
 */
public class DoorCell extends Cell {

	private boolean isOpen;
	private Coordinate coordinate;

	public DoorCell(Coordinate coordinate, boolean isOpen) {
		super(Utility.OPEN_DOOR_COLOR, coordinate);
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean open) {
		isOpen = open;
	}

}
