package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;

import java.awt.*;

/**
 * Created by Suqing on 10/15/16.
 */

/**
 * A class for the vacuum cleaner that determines the current position of the
 * machine, depending on the class Cell coordinate. Also detects the surrounding
 * objects
 */
public class SweepMachine {

	private Cell currentPositionCell;// home Cell
	// Cell[rows][cols]
	private Cell[][] layoutCells;
	private Coordinate currentPositionCoordinate;
	private int layoutRows;
	private int layoutCols;
	private Cell nextCell;
	private Direction obstacleDirection;

	public SweepMachine(Cell currentPositionCell, Cell[][] layoutCells, int layoutRows, int layoutCols) {
		this.currentPositionCell = currentPositionCell;
		this.layoutCells = layoutCells;
		this.layoutRows = layoutRows;
		this.layoutCols = layoutCols;
		this.currentPositionCoordinate = currentPositionCell.getCoordinate();
		currentPositionCell.setBackground(Utility.SWEEP_MACHINE_COLOR);

		// add visited and not visited cells
		// battery life
	}

	// TODO: Sweep Machine movements based on currentPositionCoordinate
	// TODO: need to call detectSurrounding() to get the destination cell to
	// move
	// TODO: detectSurrounding() will return null if there is no movable cell,
	// then should return false
	public boolean move() {

		Cell destinationCell = detectSurrounding(null);
		if (destinationCell.equals(null)) {
			return false;
		} else {

			return true;
		}
	}

	// TODO: Sweep Machine surrounding object detection based on
	// currentPositionCoordinate
	// TODO: should return null if there is no movable surrounding cell
	public Cell detectSurrounding(Direction obstacleDirection) {
		// TODO: this is just a sample way to access current cell's
		// surroundings, and check its instance
		if (obstacleDirection == Direction.North) {
			Cell NorthCell = layoutCells[currentPositionCoordinate.getY() - 1][currentPositionCoordinate.getX()];
			nextCell = NorthCell;
		} else if (obstacleDirection == Direction.South) {
			Cell nextCellSouth = layoutCells[currentPositionCoordinate.getY() + 1][currentPositionCoordinate.getX()];
			nextCell = nextCellSouth;
		} else if (obstacleDirection == Direction.West) {
			Cell nextCellWest = layoutCells[currentPositionCoordinate.getY()][currentPositionCoordinate.getX() - 1];
			nextCell = nextCellWest;
		} else if (obstacleDirection == Direction.East) {
			Cell nextCellEast = layoutCells[currentPositionCoordinate.getY()][currentPositionCoordinate.getX() + 1];
			nextCell = nextCellEast;
		}
		return nextCell;
		// Cell nextCell =
		// layoutCells[currentPositionCoordinate.getY()-1][currentPositionCoordinate.getX()];
		// if(nextCell instanceof FloorCell)
		// nextCell.setBackground(Utility.SWEEP_MACHINE_COLOR);
		// return null;
	}
}