package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;
import edu.depaul.cdm.se459.ui.Cell;
import edu.depaul.cdm.se459.ui.Direction;
import edu.depaul.cdm.se459.ui.FloorCell;

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
	private int layoutRows;
	private int layoutCols;
	private Cell nextCell;
	private Direction obstacleDirection;
	private Color preColor;

	public SweepMachine(Cell currentPositionCell, Cell[][] layoutCells, int layoutCols, int layoutRows) {
		this.currentPositionCell = currentPositionCell;
		this.layoutCells = layoutCells;
		this.layoutRows = layoutRows;
		this.layoutCols = layoutCols;
		this.preColor = currentPositionCell.getBackgroundColor();
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

		FloorCell destinationCell = detectSurrounding();
		if (destinationCell == null) {			// there is no open path surrounding current cell
			System.out.println("Movement stopped...");
			return false;
		} else {				// there is an open path on on side
			makeMovement(destinationCell);
			return true;
		}
	}

	private void makeMovement(FloorCell destinationCell) {
		currentPositionCell.setBackground(preColor);		// change current position cell's background to it's original state
		currentPositionCell = destinationCell;				// make movement
		destinationCell.setVisited(true);
		preColor = destinationCell.getBackgroundColor();	// change previous color to the destination cell's original color
		currentPositionCell.setBackground(Utility.SWEEP_MACHINE_COLOR);	// change destination cell's background to sweep machine
	}

	private FloorCell detectSurrounding() {
		Cell checkingCell = null;
		// check north side cell
		if(currentPositionCell.getCoordinate().getY() - 1 >= 0) {		// check if out of bound on north side
			checkingCell = layoutCells[currentPositionCell.getCoordinate().getY() - 1][currentPositionCell.getCoordinate().getX()];
			if (checkingCell instanceof FloorCell) {		// check if it's a FloorCell instance
				FloorCell floorCell = (FloorCell) checkingCell;    // cast Cell to FloorCell to get if it's visited
				if (!floorCell.isVisited()) {				// check if it's been visited or not
					System.out.println("Open path on north...");
					return floorCell;
				}
			}
		}
		// check south side cell
		if(currentPositionCell.getCoordinate().getY() + 1 < layoutRows) {		// check if out of bound on south side
			checkingCell = layoutCells[currentPositionCell.getCoordinate().getY() + 1][currentPositionCell.getCoordinate().getX()];
			if (checkingCell instanceof FloorCell) {
				FloorCell floorCell = (FloorCell) checkingCell;    // cast Cell to FloorCell to get if it's visited
				if (!floorCell.isVisited()) {
					System.out.println("Open path on south...");
					return floorCell;
				}
			}
		}

		// check east side cell
		if(currentPositionCell.getCoordinate().getX() - 1 >= 0) {		// check if out of bound on east side
			checkingCell = layoutCells[currentPositionCell.getCoordinate().getY()][currentPositionCell.getCoordinate().getX() + 1];
			if (checkingCell instanceof FloorCell) {
				FloorCell floorCell = (FloorCell) checkingCell;    // cast Cell to FloorCell to get if it's visited
				if (!floorCell.isVisited()) {
					System.out.println("Open path on east...");
					return floorCell;
				}
			}
		}

		// check north side cell
		if(currentPositionCell.getCoordinate().getX() + 1 < layoutCols) {		// check if out of bound on west side
			checkingCell = layoutCells[currentPositionCell.getCoordinate().getY()][currentPositionCell.getCoordinate().getX() - 1];
			if (checkingCell instanceof FloorCell) {
				FloorCell floorCell = (FloorCell) checkingCell;    // cast Cell to FloorCell to get if it's visited
				if (!floorCell.isVisited()) {
					System.out.println("Open path on west...");
					return floorCell;
				}
			}
		}

		return null;
	}

	// TODO: Sweep Machine surrounding object detection based on
	// currentPositionCoordinate
	// TODO: should return null if there is no movable surrounding cell
	public Cell detectSurrounding(Direction obstacleDirection) {
		// TODO: this is just a sample way to access current cell's
		// surroundings, and check its instance
		if (obstacleDirection == Direction.North) {
			Cell NorthCell = layoutCells[currentPositionCell.getCoordinate().getY() - 1][currentPositionCell.getCoordinate().getX()];
			nextCell = NorthCell;
		} else if (obstacleDirection == Direction.South) {
			Cell nextCellSouth = layoutCells[currentPositionCell.getCoordinate().getY() + 1][currentPositionCell.getCoordinate().getX()];
			nextCell = nextCellSouth;
		} else if (obstacleDirection == Direction.West) {
			Cell nextCellWest = layoutCells[currentPositionCell.getCoordinate().getY()][currentPositionCell.getCoordinate().getX() - 1];
			nextCell = nextCellWest;
		} else if (obstacleDirection == Direction.East) {
			Cell nextCellEast = layoutCells[currentPositionCell.getCoordinate().getY()][currentPositionCell.getCoordinate().getX() + 1];
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