package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.ui.Cell;
import edu.depaul.cdm.se459.ui.MainFrame;
import edu.depaul.cdm.se459.ui.StationCell;
import edu.depaul.cdm.se459.ui.SweepMachine;

import javax.swing.*;

/**
 * Created by Suqing on 10/2/16.
 */
public class AppMain {

    public static void main(String[] args) {
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
                MainFrame main = new MainFrame();
                Cell[][] cells = main.getCells();   // will return each cell elements
                StationCell startStation = main.getStartStationCell();
                SweepMachine sweepMachine = new SweepMachine(startStation, cells,
                        main.getFloorLayoutRows(), main.getFloorLayoutColumns());
                sweepMachine.detectSurrounding();
            }
        });
    }
}
