package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.model.CellStatus;
import edu.depaul.cdm.se459.ui.Cell;
import edu.depaul.cdm.se459.ui.FloorCell;
import edu.depaul.cdm.se459.ui.MainFrame;
import edu.depaul.cdm.se459.ui.StationCell;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Eric on 10/31/2016.
 */
public class SurfaceDetectionTest {


    @Test
    public void testBareFloor(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testbarefloor.txt").getFile());
            MainFrame main = new MainFrame(file);
            Cell[][] cells = main.getCells();   // will return each cell elements
            CellStatus[][] unvisitedFloorCells = main.getCellStatuses();
            StationCell startStation = main.getStartStationCell();
            int initialCapacity = 50;
            int initialBattery=100;
            int proposedX =  startStation.getCoordinate().getX() ;
            int proposedY =  startStation.getCoordinate().getY();
            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    main.getFloorLayoutRows(), main.getFloorLayoutColumns(), initialCapacity, initialBattery);

            sweepMachine.move(unvisitedFloorCells);

            Cell currentCell = sweepMachine.getCurrentPositionCell();
            int actualFloorTypeCode = sweepMachine.detectSurface( (FloorCell) currentCell);
            int proposedFloorTypeCode = 1; //1 is a bare floor

            System.out.println(""+ actualFloorTypeCode );


            assertEquals(proposedFloorTypeCode, actualFloorTypeCode);

        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testLowPileFloor(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testlowpiletest.txt").getFile());
            MainFrame main = new MainFrame(file);
            Cell[][] cells = main.getCells();   // will return each cell elements
            CellStatus[][] unvisitedFloorCells = main.getCellStatuses();
            StationCell startStation = main.getStartStationCell();
            int initialCapacity = 50;
            int initialBattery=100;
            int proposedX =  startStation.getCoordinate().getX() ;
            int proposedY =  startStation.getCoordinate().getY();
            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    main.getFloorLayoutRows(), main.getFloorLayoutColumns(), initialCapacity, initialBattery);

            sweepMachine.move(unvisitedFloorCells);

            Cell currentCell = sweepMachine.getCurrentPositionCell();
            int actualFloorTypeCode = sweepMachine.detectSurface( (FloorCell) currentCell);
            int proposedFloorTypeCode = 2; //2 is a low-pile carpet floor

            System.out.println(""+ actualFloorTypeCode );


            assertEquals(proposedFloorTypeCode, actualFloorTypeCode);

        } catch (IOException e) {
            assertTrue(false);
        }
    }


    @Test
    public void testHighPileFloor(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testhighpiletest.txt").getFile());
            MainFrame main = new MainFrame(file);
            Cell[][] cells = main.getCells();   // will return each cell elements
            CellStatus[][] unvisitedFloorCells = main.getCellStatuses();
            StationCell startStation = main.getStartStationCell();
            int initialCapacity = 50;
            int initialBattery=100;
            int proposedX =  startStation.getCoordinate().getX() ;
            int proposedY =  startStation.getCoordinate().getY();
            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    main.getFloorLayoutRows(), main.getFloorLayoutColumns(), initialCapacity,  initialBattery);

            sweepMachine.move(unvisitedFloorCells);

            Cell currentCell = sweepMachine.getCurrentPositionCell();
            int actualFloorTypeCode = sweepMachine.detectSurface( (FloorCell) currentCell);
            int proposedFloorTypeCode = 3; //3 is a high-pile floor

            System.out.println(""+ actualFloorTypeCode );


            assertEquals(proposedFloorTypeCode, actualFloorTypeCode);

        } catch (IOException e) {
            assertTrue(false);
        }
    }













}
