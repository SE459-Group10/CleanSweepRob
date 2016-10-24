package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.model.CellStatus;
import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.ui.Cell;
import edu.depaul.cdm.se459.ui.StationCell;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Suqing on 10/17/16.
 */
public class ControlSystem extends Thread {

    private SweepMachine sweepMachine;
    private CellStatus[][] cellStatuses;
    private ArrayList<StationCell> stationCells;
    private Cell previousPositionCell;
    private int[][] pathGrid;
    private final static int TRIED = 2;
    private final static int PATH = 3;
    private int height;
    private int width;
    private Cell destinationCell;

    public ControlSystem(SweepMachine sweepMachine, CellStatus[][] cellStatuses) {
        this.sweepMachine = sweepMachine;
        this.cellStatuses = cellStatuses;
        stationCells = new ArrayList<StationCell>();
        this.height = cellStatuses.length;
        this.width = cellStatuses[0].length;
        this.pathGrid = new int[height][width];
    }

    @Override
    public void run() {
        super.run();
        try {
            while(true) {

                // if not able to move, currently it means the sweep machine is full
                // step 1: direct to charging station, use cellStatuses
                // step 2: empty itself
                // step 3: go back to previous cell position
//                    previousPositionCell = sweepMachine.getCurrentPositionCell();

                if(!sweepMachine.move(cellStatuses)) {
                    //moveToDestination(sweepMachine, stationCells.get(0));
                    break;
                }
                    //printStatus();
//                    while (moveToDestination(sweepMachine, stationCells.get(0))) {
////                        Thread.sleep(1000);
//                    }
                    //moveToDestination(sweepMachine, stationCells.get(0));
//                    // TODO show empty me message
//                    System.out.println("Empty me");
//                    while(moveToDestination(cellStatuses, sweepMachine, previousPositionCell)) {
//                        Thread.sleep(1000);
//                    }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean moveToDestination(SweepMachine sweepMachine, Cell destinationCell) {
        Cell currentPositionCell = sweepMachine.getCurrentPositionCell();
        this.destinationCell = destinationCell;
        System.out.println("Destination is: " + destinationCell.getCoordinate().getX() + ", " + destinationCell.getCoordinate().getY());
        findShortestPath(currentPositionCell);
        printGrid();
        return true;
    }

    private boolean findShortestPath(Cell currentPositionCell) {
        int col = currentPositionCell.getCoordinate().getX();
        int row = currentPositionCell.getCoordinate().getY();
        return traverse(row, col);
    }

    private boolean traverse(int i, int j) {
        if(!isValidPosition(i, j)) return false;

        if(isDestination(i, j)) {
            pathGrid[i][j] = PATH;
            return true;
        } else {
            pathGrid[i][j] = TRIED;
        }

        // North check
        if(traverse(i - 1, j)) {
            pathGrid[i-1][j] = PATH;
            return true;
        }
        // East check
        if(traverse(i, j+1)) {
            pathGrid[i][j+1] = PATH;
            return true;
        }
        // South check
        if(traverse(i+1, j)) {
            pathGrid[i+1][j] = PATH;
            return true;
        }
        // West check
        if(traverse(i, j-1)) {
            pathGrid[i][j-1] = PATH;
            return true;
        }

        return false;
    }

    private boolean isValidPosition(int i, int j) {
        if(inRange(i, j) && isOpen(i, j) && !isTried(i, j)) {
            return true;
        }
        return false;
    }

    private boolean isDestination(int i, int j) {
        Coordinate coordinate = destinationCell.getCoordinate();
        int dHeight = coordinate.getX();
        int dWidth = coordinate.getY();
        System.out.println("i = " + i + ", j = " + j + ", height = " + dWidth + ", width = " + dHeight);
        return i == dWidth && j == dHeight;
    }

    private boolean isOpen(int i, int j) {
        return cellStatuses[i][j].equals(CellStatus.VISITEDFLOORCELL);
    }

    private boolean isTried(int i, int j) {
        return pathGrid[i][j] == TRIED;
    }

    private boolean inRange(int i, int j) {
        return i >= 0 && i < height && j >= 0 && j < width;
    }

    public ArrayList<StationCell> getStationCells() {
        return stationCells;
    }

    public void setStationCells(ArrayList<StationCell> stationCells) {
        this.stationCells = stationCells;
    }

    public void addStationCell(StationCell stationCell) {
        stationCells.add(stationCell);
    }

    private void printStatus() {
        int m = cellStatuses[0].length;
        int n = cellStatuses.length;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                System.out.print(cellStatuses[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void printGrid() {
        String s = "";
        for (int[] row : pathGrid) {
            s += Arrays.toString(row) + "\n";
        }

        System.out.print(s);
    }


}
