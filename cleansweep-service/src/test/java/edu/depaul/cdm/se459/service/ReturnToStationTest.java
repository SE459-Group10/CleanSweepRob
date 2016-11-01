package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.model.CellStatus;
import edu.depaul.cdm.se459.ui.Cell;
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
public class ReturnToStationTest {



    @Test
    public void testReturnToStation(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testreturntostation.txt").getFile());
            MainFrame main = new MainFrame(file);
            Cell[][] cells = main.getCells();   // will return each cell elements
            CellStatus[][] unvisitedFloorCells = main.getCellStatuses();
            StationCell startStation = main.getStartStationCell();
            int initialCapacity = 10;

            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                    main.getFloorLayoutRows(), main.getFloorLayoutColumns(), initialCapacity);
            ControlSystem controlSystem = new ControlSystem(sweepMachine, unvisitedFloorCells);

            Cell stationPosition = sweepMachine.getCurrentPositionCell();
            controlSystem.start();

            Cell actualPosition = sweepMachine.getCurrentPositionCell();

            if(actualPosition instanceof  StationCell){
                assert( actualPosition == stationPosition);
            }



        } catch (IOException e) {
            assertTrue(false);
        }
    }


}
