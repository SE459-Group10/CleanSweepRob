package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.model.CellStatus;
import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.ui.*;

import java.util.*;

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
    private Cell[][] cells;
    private Cell nextCell = null;
    private boolean isRunning = true;

    public ControlSystem(SweepMachine sweepMachine, CellStatus[][] cellStatuses) {
        this.sweepMachine = sweepMachine;
        this.cellStatuses = cellStatuses;
        stationCells = new ArrayList<StationCell>();
        this.height = cellStatuses.length;
        this.width = cellStatuses[0].length;
        this.pathGrid = new int[height][width];
        // init pathGrid values
        resetPathGrid();
        cells = sweepMachine.getLayoutCells();
    }

    @Override
    public void run() {
        super.run();
        try {
            while(isRunning) {

                // if not able to move, currently it means the sweep machine is full
                // step 1: direct to charging station, use cellStatuses
                // step 2: empty itself
                // step 3: go back to previous cell position
//                    previousPositionCell = sweepMachine.getCurrentPositionCell();

                if(!sweepMachine.move(cellStatuses)) {  // not able to move

                    // sweepMachine reaches capacity
                    if(sweepMachine.getDirtCapacity() == 0)
                    {   // this block is handling sweepMachine has reached its capacity
                        Cell currentPositionCell = sweepMachine.getCurrentPositionCell();
                        StationCell baseStation = stationCells.get(0);
                        // return to charging station
                        ArrayList<Coordinate> path = getShortestPath(currentPositionCell, baseStation, true);
                        for (int i = 0; i < path.size(); i++) {
                            Coordinate nextCoordinate = path.get(i);
                            sweepMachine.makeMovement(cells[nextCoordinate.getY()][nextCoordinate.getX()]);
                            Thread.sleep(100);
                        }
                        // show empty me message
                        sweepMachine.showEmptyMeDialog();

                        // back to previous position
                        for (int i = path.size() - 1; i >= 0; i--) {
                            Coordinate nextCoordinate = path.get(i);
                            sweepMachine.makeMovement(cells[nextCoordinate.getY()][nextCoordinate.getX()]);
                            Thread.sleep(100);
                        }
                    }
                    else if(sweepMachine.getBatteryLevel() < 3.0)
                    {   // this block is handling sweepMachine has reached its capacity
                        Cell currentPositionCell = sweepMachine.getCurrentPositionCell();
                        StationCell baseStation = stationCells.get(0);
                        // return to charging station
                        ArrayList<Coordinate> path = getShortestPath(currentPositionCell, baseStation, true);
                        for (int i = 0; i < path.size(); i++) {
                            Coordinate nextCoordinate = path.get(i);
                            sweepMachine.makeMovement(cells[nextCoordinate.getY()][nextCoordinate.getX()]);
                            Thread.sleep(100);
                        }
                        // show empty me message
                        sweepMachine.showRechargeDialog();

                        // back to previous position
                        for (int i = path.size() - 1; i >= 0; i--) {
                            Coordinate nextCoordinate = path.get(i);
                            sweepMachine.makeMovement(cells[nextCoordinate.getY()][nextCoordinate.getX()]);
                            Thread.sleep(100);
                        }
                    }
                    else {
                        Cell currentCell = sweepMachine.getCurrentPositionCell();
                        int col = currentCell.getCoordinate().getX();
                        int row = currentCell.getCoordinate().getY();
                        Cell cell = getClosestUnvisitedCell(row, col);

                        if(cell == null) {
                            System.out.println("Sweep Machine stopped cleaning...");
                            StationCell baseStation = stationCells.get(0);
                            ArrayList<Coordinate> path = getShortestPath(currentCell, baseStation, true);
                            for (int i = 0; i < path.size(); i++) {
                                Coordinate nextCoordinate = path.get(i);
                                sweepMachine.makeMovement(cells[nextCoordinate.getY()][nextCoordinate.getX()]);
                                Thread.sleep(100);
                            }
                            sweepMachine.showFinishedCleaningDialog();
                            stopRunning();
                        } else {
                            ArrayList<Coordinate> path = getShortestPath(currentCell, cell, false);
                            for (int i = 0; i < path.size(); i++) {
                                Coordinate nextCoordinate = path.get(i);
                                cellStatuses[nextCoordinate.getY()][nextCoordinate.getX()] = CellStatus.VISITEDFLOORCELL;
                                sweepMachine.makeMovement(cells[nextCoordinate.getY()][nextCoordinate.getX()]);
                                Thread.sleep(100);
                            }
                            //System.out.println(cell.getCoordinate().toString());
                        }
                    }

                }

                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Coordinate> getShortestPath(Cell currentCell, Cell destCell, boolean visitedOnly) {
        ArrayList<Coordinate> res = new ArrayList<Coordinate>();
        int col = currentCell.getCoordinate().getX();
        int row = currentCell.getCoordinate().getY();
        bfs(row, col, visitedOnly);
        Coordinate destCoordinate = destCell.getCoordinate();
        int desCol = destCoordinate.getX();
        int desRow = destCoordinate.getY();
        res.add(new Coordinate(desRow, desCol));
        int destWeight = pathGrid[desRow][desCol];
        while(destWeight != 0) {
            // check surrounding weight if is less by 1
            if(pathGrid[desRow-1][desCol] == destWeight-1) {
                res.add(0, new Coordinate(desRow-1, desCol));
                desRow--;
            } else if(pathGrid[desRow][desCol-1] == destWeight -1) {
                res.add(0, new Coordinate(desRow, desCol-1));
                desCol--;
            } else if(pathGrid[desRow+1][desCol] == destWeight-1) {
                res.add(0, new Coordinate(desRow+1, desCol));
                desRow++;
            } else if(pathGrid[desRow][desCol+1] == destWeight-1) {
                res.add(0, new Coordinate(desRow, desCol+1));
                desCol++;
            }
            destWeight--;
        }
        for(Coordinate c: res) {
            System.out.println(c);
        }
        // reset grid for next time use
        resetPathGrid();
        return res;
    }

    private void bfs(int currentRow, int currentCol, boolean visistedOnly) {
        Cell[][] cells = sweepMachine.getLayoutCells();
        Queue<Cell> queue = new LinkedList<Cell>();
        queue.add(cells[currentRow][currentCol]);
        int weight = 0;
        pathGrid[currentRow][currentCol] = weight;
        while(!queue.isEmpty()) {       // while queue not empty
            Cell cell = queue.remove();
            // check cell's surrounding cells
            Coordinate coordinate = cell.getCoordinate();
            int col = coordinate.getX();
            int row = coordinate.getY();

            checkSurroundingCellWithinVisited(cells, row, col, row-1, col, queue, visistedOnly);
            checkSurroundingCellWithinVisited(cells, row, col, row, col-1, queue, visistedOnly);
            checkSurroundingCellWithinVisited(cells, row, col, row+1, col, queue, visistedOnly);
            checkSurroundingCellWithinVisited(cells, row, col, row, col+1, queue, visistedOnly);
        }
    }

    private Cell getClosestUnvisitedCell(int currentRow, int currentCol) {
        nextCell = null;    // reset next cell
        Cell[][] cells = sweepMachine.getLayoutCells();
        Queue<Cell> queue = new LinkedList<Cell>();
        HashSet<Cell> set = new HashSet<Cell>();
        queue.add(cells[currentRow][currentCol]);
        while(!queue.isEmpty()) {
            Cell cell = queue.remove();
            Coordinate coordinate = cell.getCoordinate();
            int col = coordinate.getX();
            int row = coordinate.getY();

            if(nextCell == null) {
                checkSurroundingCell(cells, row, col, row-1, col, queue, set);
            } else return nextCell;

            if(nextCell == null) {
                checkSurroundingCell(cells, row, col, row, col-1, queue, set);
            } else return nextCell;

            if(nextCell == null) {
                checkSurroundingCell(cells, row, col, row+1, col, queue, set);
            } else return nextCell;

            if(nextCell == null) {
                checkSurroundingCell(cells, row, col, row, col+1, queue, set);
            } else return nextCell;
        }

        return null;
    }


    private void checkSurroundingCell(Cell[][] cells, int curRow, int curCol, int nextRow, int nextCol, Queue<Cell> queue, HashSet<Cell> set) {
        if(((cells[nextRow][nextCol] instanceof FloorCell) || (cells[nextRow][nextCol] instanceof StationCell) || (cells[nextRow][nextCol] instanceof DoorCell)) &&
                cellStatuses[nextRow][nextCol].equals(CellStatus.VISITEDFLOORCELL)) {
            if(!set.contains(cells[nextRow][nextCol])) {
                set.add(cells[nextRow][nextCol]);
                queue.add(cells[nextRow][nextCol]);
            }
        } else if(((cells[nextRow][nextCol] instanceof FloorCell) || (cells[nextRow][nextCol] instanceof StationCell) || (cells[nextRow][nextCol] instanceof DoorCell)) &&
                cellStatuses[nextRow][nextCol].equals(CellStatus.UNVISITEDFLOORCELL)) {
            nextCell = cells[nextRow][nextCol];
        }
    }

    private void checkSurroundingCellWithinVisited(Cell[][] cells, int curRow, int curCol, int nextRow, int nextCol, Queue<Cell> queue, boolean visitedOnly) {
        if(((cells[nextRow][nextCol] instanceof FloorCell) || (cells[nextRow][nextCol] instanceof StationCell) || (cells[nextRow][nextCol] instanceof DoorCell))
                && pathGrid[nextRow][nextCol] > pathGrid[curRow][curCol]) {
            if(visitedOnly) {
                if(cellStatuses[nextRow][nextCol].equals(CellStatus.VISITEDFLOORCELL)) {
                    pathGrid[nextRow][nextCol] = pathGrid[curRow][curCol] + 1;
                    queue.add(cells[nextRow][nextCol]);
                }
            } else {
                pathGrid[nextRow][nextCol] = pathGrid[curRow][curCol] + 1;
                queue.add(cells[nextRow][nextCol]);
            }
        }
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

    private void resetPathGrid() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                pathGrid[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    private void stopRunning() {
        this.isRunning = false;
    }


}
