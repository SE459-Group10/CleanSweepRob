package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.ui.Cell;
import edu.depaul.cdm.se459.ui.FloorCell;
import edu.depaul.cdm.se459.ui.MainFrame;
import edu.depaul.cdm.se459.ui.StationCell;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Eric on 10/20/2016.
 */
public class DirtDetectionTest {


    @Test
    public void testDirtyRoom(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testdirtyroom.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            Cell[][] cells = mainFrame.getCells();   // will return each cell elements
            StationCell startStation = mainFrame.getStartStationCell();
            int initialCapacity = 50;

            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    mainFrame.getFloorLayoutRows(), mainFrame.getFloorLayoutColumns(), initialCapacity);

            System.out.println("Starting Dirty Test");
            assertTrue(innerTestDirty(sweepMachine, startStation));

        } catch (IOException e) {
            assertTrue(false);
        }
    }




    public boolean innerTestDirty(SweepMachine sweepMachine, StationCell startStation) {
        sweepMachine.move();
        while (sweepMachine.getCurrentPositionCell() != startStation) {
            sweepMachine.move();
            if (sweepMachine.getCurrentPositionCell().getText() != "0") {
                if (sweepMachine.detectDirt((FloorCell) sweepMachine.getCurrentPositionCell()) == true)
                    return true;
            }
            else {
                if (sweepMachine.detectDirt((FloorCell) sweepMachine.getCurrentPositionCell()) == true)
                    return true;
            }

        }
        return false;
    }



}
