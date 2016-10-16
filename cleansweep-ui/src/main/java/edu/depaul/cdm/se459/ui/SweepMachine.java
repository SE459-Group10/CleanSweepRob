package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;

import java.awt.*;

/**
 * Created by Suqing on 10/15/16.
 */
public class SweepMachine {

    private Cell currentPositionCell;
    //  Cell[rows][cols]
    private Cell[][] layoutCells;
    private Coordinate currentPositionCoordinate;
    private int layoutRows;
    private int layoutCols;

    public SweepMachine(Cell currentPositionCell, Cell[][] layoutCells, int layoutRows, int layoutCols) {
        this.currentPositionCell = currentPositionCell;
        this.layoutCells = layoutCells;
        this.layoutRows = layoutRows;
        this.layoutCols = layoutCols;
        this.currentPositionCoordinate = currentPositionCell.getCoordinate();
        currentPositionCell.setBackground(Utility.SWEEP_MACHINE_COLOR);
    }

    // TODO: Sweep Machine movements based on currentPositionCoordinate
    // TODO: need to call detectSurrounding() to get the destination cell to move
    // TODO: detectSurrounding() will return null if there is no movable cell, then should return false
    public boolean move() {
        return false;
    }


    // TODO: Sweep Machine surrounding object detection based on currentPositionCoordinate
    // TODO: should return null if there is no movable surrounding cell
    public Cell detectSurrounding() {

        // TODO: this is just a sample way to access current cell's surroundings, and check its instance
        Cell nextCell = layoutCells[currentPositionCoordinate.getY()-1][currentPositionCoordinate.getX()];
        if(nextCell instanceof FloorCell)
            nextCell.setBackground(Utility.SWEEP_MACHINE_COLOR);

        return null;
    }

}