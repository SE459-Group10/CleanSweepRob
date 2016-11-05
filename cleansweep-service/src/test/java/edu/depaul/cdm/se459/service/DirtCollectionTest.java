
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
/**
 * Created by Eric on 10/24/2016.
 */
public class DirtCollectionTest {


    @Test
    public void dirtCollectionTest(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testdirtyroom.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            Cell[][] cells = mainFrame.getCells();   // will return each cell elements
            StationCell startStation = mainFrame.getStartStationCell();
            int initialCapacity = 50 ;
            int initialBattery=100;
            CellStatus[][] cellStatuses = mainFrame.getCellStatuses();
            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    mainFrame.getFloorLayoutRows(), mainFrame.getFloorLayoutColumns(), initialCapacity, initialBattery);




            sweepMachine.move(cellStatuses);

            if(sweepMachine.detectDirt((FloorCell) sweepMachine.getCurrentPositionCell())){
                assertTrue(sweepMachine.removeDirt((FloorCell) sweepMachine.getCurrentPositionCell()));
            }else {
                sweepMachine.move(cellStatuses);
                if(sweepMachine.detectDirt((FloorCell) sweepMachine.getCurrentPositionCell())) {
                    assertTrue(sweepMachine.removeDirt((FloorCell) sweepMachine.getCurrentPositionCell()));
                }
            }



        } catch (IOException e) {
            assertTrue(false);
        }
    }
}
