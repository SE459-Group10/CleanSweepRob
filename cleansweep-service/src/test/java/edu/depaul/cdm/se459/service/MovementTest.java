package edu.depaul.cdm.se459.service;


        import static org.junit.Assert.*;

        import edu.depaul.cdm.se459.model.CellStatus;
        import org.junit.Test;
        import java.io.File;
        import java.io.IOException;
        import  edu.depaul.cdm.se459.ui.*;

/**
 * Created by Eric on 10/18/2016.
 *
 *
 */


public class MovementTest {


    @Test
    public void testMoveEast(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testmoveright.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            Cell[][] cells = mainFrame.getCells();   // will return each cell elements
            CellStatus[][] unvisitedFloorCells = mainFrame.getCellStatuses();
            StationCell startStation = mainFrame.getStartStationCell();
            int initialCapacity = 50;
            int initialBattery=100;
            int proposedX =  startStation.getCoordinate().getX() + 1 ;
            int proposedY =  startStation.getCoordinate().getY();
            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    mainFrame.getFloorLayoutRows(), mainFrame.getFloorLayoutColumns(), initialCapacity, initialBattery);
            sweepMachine.move(unvisitedFloorCells);
            Cell actualPosition = sweepMachine.getCurrentPositionCell();

            int actualX = actualPosition.getCoordinate().getX();
            int actualY = actualPosition.getCoordinate().getY();

            assertEquals(proposedX, actualX);
            assertEquals(proposedY, actualY);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testMoveWest(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testmoveleft.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            Cell[][] cells = mainFrame.getCells();   // will return each cell elements
            CellStatus[][] unvisitedFloorCells = mainFrame.getCellStatuses();
            StationCell startStation = mainFrame.getStartStationCell();
            int initialCapacity = 50;
            int initialBattery=100;
            int proposedX =  startStation.getCoordinate().getX() - 1 ;
            int proposedY =  startStation.getCoordinate().getY();
            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    mainFrame.getFloorLayoutRows(), mainFrame.getFloorLayoutColumns(), initialCapacity, initialBattery);
            sweepMachine.move(unvisitedFloorCells);
            Cell actualPosition = sweepMachine.getCurrentPositionCell();

            int actualX = actualPosition.getCoordinate().getX();
            int actualY = actualPosition.getCoordinate().getY();

            assertEquals(proposedX, actualX);
            assertEquals(proposedY, actualY);


        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testMoveNorth(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testmoveforward.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            Cell[][] cells = mainFrame.getCells();   // will return each cell elements
            CellStatus[][] unvisitedFloorCells = mainFrame.getCellStatuses();
            StationCell startStation = mainFrame.getStartStationCell();
            int initialCapacity = 50;
            int initialBattery=100;
            int proposedX =  startStation.getCoordinate().getX() ;
            int proposedY =  startStation.getCoordinate().getY() -1;
            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    mainFrame.getFloorLayoutRows(), mainFrame.getFloorLayoutColumns(), initialCapacity, initialBattery);
            sweepMachine.move(unvisitedFloorCells);
            Cell actualPosition = sweepMachine.getCurrentPositionCell();

            int actualX = actualPosition.getCoordinate().getX();
            int actualY = actualPosition.getCoordinate().getY();

            assertEquals(proposedX, actualX);
            assertEquals(proposedY, actualY);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testMoveSouth(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testmovebackward.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            Cell[][] cells = mainFrame.getCells();   // will return each cell elements
            CellStatus[][] unvisitedFloorCells = mainFrame.getCellStatuses();
            StationCell startStation = mainFrame.getStartStationCell();
            int initialCapacity = 50;
            int initialBattery=100;
            int proposedX =  startStation.getCoordinate().getX();
            int proposedY =  startStation.getCoordinate().getY() + 1;
            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    mainFrame.getFloorLayoutRows(), mainFrame.getFloorLayoutColumns(), initialCapacity, initialBattery);
            sweepMachine.move(unvisitedFloorCells);
            Cell actualPosition = sweepMachine.getCurrentPositionCell();

            int actualX = actualPosition.getCoordinate().getX();
            int actualY = actualPosition.getCoordinate().getY();

            assertEquals(proposedX, actualX);
            assertEquals(proposedY, actualY);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

}
