package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.model.CellStatus;
import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;
import edu.depaul.cdm.se459.ui.Cell;
import edu.depaul.cdm.se459.ui.FloorCell;
import edu.depaul.cdm.se459.ui.StationCell;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
	public boolean move(CellStatus[][] cellStatuses) {
            if(currentPositionCell instanceof FloorCell) {
                FloorCell currentFloorCell = (FloorCell) currentPositionCell;
				// set current position cell as visited
				int x = currentFloorCell.getCoordinate().getX();
				int y = currentFloorCell.getCoordinate().getY();
				if(!cellStatuses[y][x].equals(CellStatus.VISITEDFLOORCELL)) {
					cellStatuses[y][x] = CellStatus.VISITEDFLOORCELL;
				}
				// detect dirt at current position
                if (detectDirt(currentFloorCell)){
                    if(!removeDirt(currentFloorCell))
                    	return false;
                } else {	// if no dirt at current position, try to make movement
					FloorCell destinationCell = detectSurrounding();
					if(destinationCell != null)
                    	makeMovement(destinationCell);
					else return false;
                }
            } else { // it's not floor cell, then it could only be Station cell, move forward
				FloorCell destinationCell = detectSurrounding();
				if(destinationCell != null)
                	makeMovement(destinationCell);
				else return false;
            }
			return true;
	}

	//detects dirt, takes in a Floor Cell, checks its dirtAmount and returns true or false if the cell is dirty.
	public boolean detectDirt(FloorCell currentCell){
		int dirtAmount = currentCell.getDirtAmount();
			if (dirtAmount > 0 ){
			System.out.println(dirtAmount + " dirt present");
				return true;
			} else {
				System.out.println("No dirt");
			return false;
			}
	}

	private boolean removeDirt(FloorCell currentCell) {
        if(dirtCapacity > 0) {
            int dirtAmount = currentCell.getDirtAmount();
            int remainingDirt = dirtAmount - 1; // remaining dirt decrease 1
            dirtCapacity--;  // capacity decrease 1
            System.out.println("Current Capacity: " + dirtCapacity);
            currentCell.setDirtAmount(remainingDirt);
            currentCell.setText(remainingDirt + "");
			return true;
        } else {    // sweep machine has full
            return capacityFullNotification();
        }
    }

    public void showEmptyMeDialog() {
		JOptionPane.showMessageDialog(null, "Sweep Machine capacity is full");
		System.out.println("Movement stopped because of full capacity");
	}

	public boolean capacityFullNotification(){
		if (dirtCapacity == 0 ) {
			showEmptyMeDialog();
			return false;
		} return true;
	}

	public void makeMovement(Cell destinationCell) {
		currentPositionCell.setBackground(preColor);		// change current position cell's background to it's original state
		currentPositionCell = destinationCell;				// make movement
		if(destinationCell instanceof FloorCell)
			((FloorCell)destinationCell).setVisited(true);
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

	public Cell[][] getLayoutCells() {
		return layoutCells;
	}

	public int getDirtCapacity() {
		return dirtCapacity;
	}
}