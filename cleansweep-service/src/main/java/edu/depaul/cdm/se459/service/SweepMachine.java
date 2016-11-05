package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.model.CellStatus;
import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;
import edu.depaul.cdm.se459.ui.Cell;
import edu.depaul.cdm.se459.ui.DoorCell;
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
 * machine, depending on the class Cell coordinate. 
 * Detects the surrounding objects and make the movement into the next Cell 
 * Detects if a Cell is dirty and cleans the Cell
 * Notify when capacity is full
 */
public class SweepMachine {

	private Cell currentPositionCell;// home Cell
	private Cell[][] layoutCells;// Cell[rows][cols]
	private int layoutRows;
	private int layoutCols;
	private Color preColor;
	private int dirtCapacity;
	private double CurrentBatteryLevel;
	public static final int DIRT_CAPACITY = 50;
	public static final int MAX_BATTERY_CAPACITY = 100;

	public SweepMachine(Cell currentPositionCell, Cell[][] layoutCells, int layoutCols, int layoutRows, int dirtCapacity,double CurrentBatteryLevel) {
		this.currentPositionCell = currentPositionCell;
		this.layoutCells = layoutCells;
		this.layoutRows = layoutRows;
		this.layoutCols = layoutCols;
		this.preColor = currentPositionCell.getBackgroundColor();
		this.dirtCapacity = dirtCapacity;
		this.CurrentBatteryLevel=CurrentBatteryLevel;
		currentPositionCell.setBackground(Utility.SWEEP_MACHINE_COLOR);
	}

	public boolean move(CellStatus[][] cellStatuses) {
            if(currentPositionCell instanceof FloorCell) {
                FloorCell currentFloorCell = (FloorCell) currentPositionCell;
				// set current position cell as visited
				int x = currentFloorCell.getCoordinate().getX();
				int y = currentFloorCell.getCoordinate().getY();
				if(!cellStatuses[y][x].equals(CellStatus.VISITEDFLOORCELL)) {
					cellStatuses[y][x] = CellStatus.VISITEDFLOORCELL;
				}
				detectSurface(currentFloorCell);//depending on that the battery consumption is changed
				// detect dirt at current position
                if (detectDirt(currentFloorCell)){
                    if(!removeDirt(currentFloorCell))
                    	return false;
                } else {	// if no dirt at current position, try to make movement
					FloorCell destinationCell = detectSurrounding(cellStatuses);
					if(destinationCell != null){
						//3 is the max average cost/when reach it return to base 
						if(CurrentBatteryLevel > 3.0) {
						 this.CurrentBatteryLevel=this.CurrentBatteryLevel-calculateBatteryReduction(currentFloorCell,destinationCell);//remain battery
						 System.out.println( " the remaining battery is"+ CurrentBatteryLevel);
                    	makeMovement(destinationCell);//call the method to make the movement
						}
						else return false;
					}else return false;
                }
            } else { // it's not floor cell, then it could only be Station cell, move forward
				FloorCell destinationCell = detectSurrounding(cellStatuses);
				if(destinationCell != null)
                	makeMovement(destinationCell);//call the method to make the movement 
				else return false;
            }
			return true;
	}
	
	//connects with the battery consumption
	//should return the power consumption 
public int detectSurface(FloorCell currentCell){

	int surface=currentCell.getFloorType();
	if (surface==1){
		System.out.println( " bare floor surface");//battery consumption is one unit
		//reduce battery by one
		return 1;
	}else if (surface==2){
		System.out.println(" low pile surface ");//battery consumption is two unit
		//reduce battery by two
		return 2;
	}else{//reduce battery by three
		System.out.println(" high pile surface");//battery consumption is three unit
		return 3;
	}
}
	//reduce battery method based on different surfaces
//(location A + location B) / 2
public double calculateBatteryReduction(FloorCell currentCell, FloorCell destinationCell) {//floor cell
    double remainBattery = 0;
    double CurrentSurface, DestinationSurface;
    	CurrentSurface = (double) currentCell.getFloorType();//surface of current cell
    	DestinationSurface = (double) destinationCell.getFloorType();//surface of destination cell
    	remainBattery = (CurrentSurface + DestinationSurface) / 2;
    	System.out.println(" Battery reduction is"+remainBattery+"%");
    return remainBattery; 
}


	//detects dirt, takes in a Floor Cell, checks its dirtAmount and returns true or false if the cell is dirty.
	public boolean detectDirt(FloorCell currentCell){
		int dirtAmount = currentCell.getDirtAmount();
		int cellType = currentCell.getFloorType();
			if (dirtAmount > 0 ){
			System.out.println(dirtAmount + " dirt present");
				System.out.println(cellType + "");
				return true;
			} else {
				System.out.println("No dirt");
				System.out.println(cellType + "");
			return false;
			}
	}
	
//removes the dirt from the Floor Cell
	public boolean removeDirt(FloorCell currentCell) {
        if(dirtCapacity > 0) {
            int dirtAmount = currentCell.getDirtAmount();
            int remainingDirt = dirtAmount - 1; // remaining dirt decrease 1
            dirtCapacity--;  // capacity decrease 1
            System.out.println("Current Capacity: " + dirtCapacity);
            currentCell.setDirtAmount(remainingDirt);
            currentCell.setText(remainingDirt + "");
			return true;
        } else {    // sweep machine has full
			return false;	// return to charging station
            //return capacityFullNotification(); //call the method to show the message
        }
    }
	 //for dirt capacity
    public void showEmptyMeDialog() {
		JOptionPane.showMessageDialog(null, "Sweep Machine capacity is full");
		emptyDirtCapacity();
		System.out.println("Movement stopped because of full capacity");
	}
    //for battery
    public void showRechargeDialog() {
  		JOptionPane.showMessageDialog(null, "Sweep Machine Battery is empty");
  		RechargeBatteryCapacity();
  		System.out.println("Return to charging station");
  	}
  //for dirt capacity
	public boolean capacityFullNotification(){
		if (dirtCapacity == 0 ) {
			showEmptyMeDialog();
			return false;
		} return true;
	}
	
	//make the movement in the UI
	public void makeMovement(Cell destinationCell) {
		currentPositionCell.setBackground(preColor);		// change current position cell's background to it's original state
		currentPositionCell = destinationCell;				// make movement
		if(destinationCell instanceof FloorCell)
			((FloorCell)destinationCell).setVisited(true);
		preColor = destinationCell.getBackgroundColor();	// change previous color to the destination cell's original color
		currentPositionCell.setBackground(Utility.SWEEP_MACHINE_COLOR);	// change destination cell's background to sweep machine
	}


	
	private FloorCell detectSurrounding(CellStatus[][] cellStatuses) {
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
			else if(checkingCell instanceof DoorCell){// check if it's a DoorCell instance
				DoorCell doorCell=(DoorCell)checkingCell;
				if(doorCell.isOpen()){
					int x = doorCell.getCoordinate().getX();
					int y = doorCell.getCoordinate().getY();
					if(!cellStatuses[y][x].equals(CellStatus.VISITEDFLOORCELL)) {
						cellStatuses[y][x] = CellStatus.VISITEDFLOORCELL;
					}
					 checkingCell = layoutCells[currentPositionCell.getCoordinate().getY() - 2][currentPositionCell.getCoordinate().getX()];
	                    FloorCell floorCell = (FloorCell) checkingCell;    // cast Cell to FloorCell to get if it's visited
	                    if (!floorCell.isVisited()) {               // check if it's been visited or not
	                        System.out.println("Open path on north...");
	                        return floorCell;
	                   }
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
			else if(checkingCell instanceof DoorCell){
				DoorCell doorCell=(DoorCell)checkingCell;
				if(doorCell.isOpen()){
					int x = doorCell.getCoordinate().getX();
					int y = doorCell.getCoordinate().getY();
					if(!cellStatuses[y][x].equals(CellStatus.VISITEDFLOORCELL)) {
						cellStatuses[y][x] = CellStatus.VISITEDFLOORCELL;
					}
					checkingCell = layoutCells[currentPositionCell.getCoordinate().getY() + 2][currentPositionCell.getCoordinate().getX()];
                    FloorCell floorCell = (FloorCell) checkingCell;    // cast Cell to FloorCell to get if it's visited
                    if (!floorCell.isVisited()) {               // check if it's been visited or not
                       System.out.println("Open path on south...");
                        return floorCell;
                   }
				
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
			else if(checkingCell instanceof DoorCell){
				DoorCell doorCell=(DoorCell)checkingCell;
				if(doorCell.isOpen()){
					int x = doorCell.getCoordinate().getX();
					int y = doorCell.getCoordinate().getY();
					if(!cellStatuses[y][x].equals(CellStatus.VISITEDFLOORCELL)) {
						cellStatuses[y][x] = CellStatus.VISITEDFLOORCELL;
					}
					checkingCell = layoutCells[currentPositionCell.getCoordinate().getY()][currentPositionCell.getCoordinate().getX() + 2];
                    FloorCell floorCell = (FloorCell) checkingCell;    // cast Cell to FloorCell to get if it's visited
                  if (!floorCell.isVisited()) {               // check if it's been visited or not
                       System.out.println("Open path on east...");
                        return floorCell;
                  }
				
				}
		}
	}

		// check west side cell
		if(currentPositionCell.getCoordinate().getX() + 1 < layoutCols) {		// check if out of bound on west side
			checkingCell = layoutCells[currentPositionCell.getCoordinate().getY()][currentPositionCell.getCoordinate().getX() - 1];
			if (checkingCell instanceof FloorCell) {
				FloorCell floorCell = (FloorCell) checkingCell;    // cast Cell to FloorCell to get if it's visited
				if (!floorCell.isVisited()) {
					System.out.println("Open path on west...");
					return floorCell;
				}
			}
			else if(checkingCell instanceof DoorCell){
				DoorCell doorCell=(DoorCell)checkingCell;
				if(doorCell.isOpen()){
					int x = doorCell.getCoordinate().getX();
					int y = doorCell.getCoordinate().getY();
					if(!cellStatuses[y][x].equals(CellStatus.VISITEDFLOORCELL)) {
						cellStatuses[y][x] = CellStatus.VISITEDFLOORCELL;
					}
					 checkingCell = layoutCells[currentPositionCell.getCoordinate().getY()][currentPositionCell.getCoordinate().getX() - 2];
	                    FloorCell floorCell = (FloorCell) checkingCell;    // cast Cell to FloorCell to get if it's visited
	                   if (!floorCell.isVisited()) {               // check if it's been visited or not
	                        System.out.println("Open path on west...");
	                        return floorCell;
	                   }
			
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
	/**
	 * Get the battery current level
	 */
	public double getBatteryLevel() {
		return CurrentBatteryLevel;
	}
	public void RechargeBatteryCapacity() {
		this.CurrentBatteryLevel = MAX_BATTERY_CAPACITY ;
	}
	public void emptyDirtCapacity() {
		this.dirtCapacity = DIRT_CAPACITY;
	}
}