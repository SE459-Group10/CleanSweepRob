package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.ui.Cell;
import edu.depaul.cdm.se459.ui.FloorCell;
import edu.depaul.cdm.se459.ui.MainFrame;
import edu.depaul.cdm.se459.ui.StationCell;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Gwiz on 10/21/2016.
 */
public class ObstacleDetectionTest {

    @Test
    public void testDirtyRoom() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testdirtyroom.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            Cell[][] cells = mainFrame.getCells();   // will return each cell elements

            StationCell startStation = mainFrame.getStartStationCell();
            int initialCapacity = 50;

            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    mainFrame.getFloorLayoutRows(), mainFrame.getFloorLayoutColumns(), initialCapacity);




           //assertTrue();

        } catch (IOException e) {
            assertTrue(false);
        }
    }



}
