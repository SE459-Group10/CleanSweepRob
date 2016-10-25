package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.model.CellStatus;
import edu.depaul.cdm.se459.ui.Cell;
import edu.depaul.cdm.se459.ui.FloorCell;
import edu.depaul.cdm.se459.ui.MainFrame;
import edu.depaul.cdm.se459.ui.StationCell;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Eric on 10/24/2016.
 */
public class CapacityFullNotificationStoppedTest {


    @Test
    public void testInitiallyFullNotification(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testdirtyroom.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            Cell[][] cells = mainFrame.getCells();   // will return each cell elements
            StationCell startStation = mainFrame.getStartStationCell();
            int initialCapacity = 0 ;
            CellStatus[][] cellStatuses = mainFrame.getCellStatuses();

            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    mainFrame.getFloorLayoutRows(), mainFrame.getFloorLayoutColumns(), initialCapacity);

            sweepMachine.move(cellStatuses);
            System.out.println("Starting Notification Test");
            assertTrue(sweepMachine.capacityFullNotification());

        } catch (IOException e) {
            assertTrue(false);
        }
    }


    @Test
    public void testFullStoppedNotification(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            Cell[][] cells = mainFrame.getCells();   // will return each cell elements
            StationCell startStation = mainFrame.getStartStationCell();
            int initialCapacity = 50 ;
            CellStatus[][] cellStatuses = mainFrame.getCellStatuses();

            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    mainFrame.getFloorLayoutRows(), mainFrame.getFloorLayoutColumns(), initialCapacity);


            System.out.println("Starting Full Stopped Test");
            ControlSystem controlSystem = new ControlSystem(sweepMachine, cellStatuses);
            controlSystem.start();

            if(sweepMachine.capacityFullNotification() == true){
            assertTrue(sweepMachine.getDirtCapacity() == 0);
            }

        } catch (IOException e) {
            assertTrue(false);
        }
    }










}
