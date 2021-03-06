package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.model.CellStatus;
import edu.depaul.cdm.se459.ui.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Suqing on 10/2/16.
 */
public class AppMain {

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("file/floorplan.txt").getFile());
                //MainFrame main = null;
				try {
					final MainFrame main = new MainFrame(file);

                    Cell[][] cells = main.getCells();   // will return each cell elements

                    JButton startBtn = main.getStartBtn();
                    JButton resetBtn = main.getStopBtn();


                    startBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            Cell[][] cells = main.getCells();   // will return each cell elements
                            CellStatus[][] cellStatuses = main.getCellStatuses();
                            StationCell startStation = main.getStartStationCell();
                            int initialCapacity = SweepMachine.DIRT_CAPACITY;
                            int initialBattery=SweepMachine.MAX_BATTERY_CAPACITY;

                            SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                                    main.getFloorLayoutRows(), main.getFloorLayoutColumns(), initialCapacity,initialBattery);
                            ControlSystem controlSystem = new ControlSystem(sweepMachine, cellStatuses);
                            controlSystem.addStationCell(startStation);  // add start station
                            controlSystem.start();
                            //startControlSystemPressed();
                        }
                    });

                    resetBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            main.resetDirtToFloor();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }


//                sweepMachine.move();
//                sweepMachine.detectSurrounding(Direction.North);
            }
        });
    }
}
