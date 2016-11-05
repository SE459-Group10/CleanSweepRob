package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.model.CellStatus;
import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.ui.Cell;
import edu.depaul.cdm.se459.ui.FloorCell;
import edu.depaul.cdm.se459.ui.MainFrame;
import edu.depaul.cdm.se459.ui.StationCell;
import org.junit.Test;

import java.io.File;
import java.io.IOException;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Eric on 10/31/2016.
 */
public class VisitedUnvisitedCellsTest {


    @Test
    public void testVisitedCells(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testmoveright.txt").getFile());
            MainFrame main = new MainFrame(file);
            Cell[][] cells = main.getCells();   // will return each cell elements
            CellStatus[][] unvisitedFloorCells = main.getCellStatuses();
            StationCell startStation = main.getStartStationCell();
            int initialCapacity = 50;
            int initialBattery=100;
            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    main.getFloorLayoutRows(), main.getFloorLayoutColumns(), initialCapacity, initialBattery);

            sweepMachine.move(unvisitedFloorCells);
            Cell  visitedPosition = sweepMachine.getCurrentPositionCell();
            FloorCell floorCellPosition = (FloorCell) visitedPosition;

            assertTrue(floorCellPosition.isVisited());

        } catch (IOException e) {
            assertTrue(false);
        }
    }



    @Test
    public void testUnvisitedCells(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testcleanroom.txt").getFile());
            MainFrame main = new MainFrame(file);
            Cell[][] cells = main.getCells();   // will return each cell elements
            CellStatus[][] unvisitedFloorCells = main.getCellStatuses();
            StationCell startStation = main.getStartStationCell();
            int initialCapacity = 50;
            int initialBattery=100;
            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    main.getFloorLayoutRows(), main.getFloorLayoutColumns(), initialCapacity ,initialBattery);

            sweepMachine.move(unvisitedFloorCells);

            sweepMachine.move(unvisitedFloorCells);

            Cell  visitedPosition = sweepMachine.getCurrentPositionCell();
            FloorCell floorCellPosition = (FloorCell) visitedPosition;
           int unvisitedX =  visitedPosition.getCoordinate().getX();
            int unvisitedY =  visitedPosition.getCoordinate().getY();

            System.out.println(unvisitedX + " " + unvisitedY);

            assertTrue(floorCellPosition.isVisited());
            Cell unvisitedFloorCell = null;

            Cell[][] layoutCells =  sweepMachine.getLayoutCells();

            unvisitedFloorCell = layoutCells[sweepMachine.getCurrentPositionCell().getCoordinate().getY()][sweepMachine.getCurrentPositionCell().getCoordinate().getX() + 1];

            FloorCell floorCell = (FloorCell) unvisitedFloorCell;    // cast Cell to FloorCell to get if it's visited
            assertFalse(floorCell.isVisited());




        } catch (IOException e) {
            assertTrue(false);
        }
    }



}
