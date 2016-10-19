package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;
import edu.depaul.cdm.se459.ui.Cell;
import edu.depaul.cdm.se459.ui.Direction;
import edu.depaul.cdm.se459.ui.FloorCell;

import javax.swing.*;
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
	private int dirtCapacity;

	public SweepMachine(Cell currentPositionCell, Cell[][] layoutCells, int layoutCols, int layoutRows, int dirtCapacity) {
		this.currentPositionCell = currentPositionCell;
		this.layoutCells = layoutCells;
		this.layoutRows = layoutRows;
		this.layoutCols = layoutCols;
		this.preColor = currentPositionCell.getBackgroundColor();
		this.dirtCapacity = dirtCapacity;
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
            if(currentPositionCell instanceof FloorCell) {
                FloorCell currentFloorCell = (FloorCell) currentPositionCell;
                if (detectDirt(currentFloorCell)){
                    removeDirt(currentFloorCell);
                    if(capacityFullNotification()){
                        JOptionPane.showMessageDialog(null, "Sweep Machine capacity is full");
                        System.out.println("Movement stopped because of full capacity");
                        return false;
                    }
                } else {
                    makeMovement(destinationCell);
                }
            } else { // it's not floor cell, then it could only be Station cell, move forward
                makeMovement(destinationCell);
            }
			return true;
		}
	}

	//detects dirt, takes in a Floor Cell, checks its dirtAmount and returns true or false if the cell is dirty.
	private boolean detectDirt(FloorCell currentCell){
		int dirtAmount = currentCell.getDirtAmount();
			if (dirtAmount > 0 ){
			System.out.println(dirtAmount + " dirt present");
				return true;
			} else {
				System.out.println("No dirt");
			return false;
			}
	}

	private void removeDirt(FloorCell currentCell) {
        if(dirtCapacity > 0) {
            int dirtAmount = currentCell.getDirtAmount();
            int remainingDirt = dirtAmount - 1; // remaining dirt decrease 1
            dirtCapacity--;  // capacity decrease 1
            System.out.println("Current Capacity: " + dirtCapacity);
            currentCell.setDirtAmount(remainingDirt);
            currentCell.setText(remainingDirt + "");
        } else {    // sweep machine has full
            capacityFullNotification();
        }
    }

	private boolean capacityFullNotification(){
		if (dirtCapacity == 0 ) {
			System.out.println("Empty Me");
			System.out.println("Dirt Capacity Full");
			return true;
		} return false;
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

	public Cell getCurrentPositionCell() {
		return currentPositionCell;
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