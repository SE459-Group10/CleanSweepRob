package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.CellStatus;
import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static edu.depaul.cdm.se459.model.CellStatus.*;

/**
 * Created by Suqing on 10/2/16.
 */

/**
 * A class for graphics for the Floor Plan
 * It reads the file that is located in the resources and gives the number of col/row which created our cell
 * depending on the kind of cell we add it to the panel
 * each cell has some dirt amount which is random
 */
public class MainFrame extends JFrame {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;
    private static final String APP_NAME = "Clean Sweep Machine";
    private Cell[][] cells;
    private CellStatus[][] cellStatuses;
    private StationCell startStationCell;
    private JButton startBtn;
    private JButton stopBtn;
    private ArrayList<FloorCell> floorCells = new ArrayList<FloorCell>();

    public MainFrame(File file) throws IOException {
        super(APP_NAME);
        constructAppWindow(file);
    }

    private void constructAppWindow(File file) throws IOException {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);    // position not in top left corner, but in center of screen
        processFloorPlan(file);
        setVisible(true);
    }

    private void processFloorPlan(File file) throws IOException {
        Scanner s = null;
        try {
            s = new Scanner(file);
            int cols = 0;
            int rows = 0;

            if(s.hasNextInt()) {
                cols = s.nextInt();
            } else throw new IOException("Not able to get number of columns from file");
            if(s.hasNextInt()) {
                rows = s.nextInt();
            } else throw new IOException("Not able to get number of rows from file");

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(0, cols));

            cells = new Cell[rows][cols];
            cellStatuses = new CellStatus[rows][cols];

            for(int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (s.hasNextInt()) {
                        int num = s.nextInt();
                        cellStatuses[i][j] = OBSTACLE;
                        switch (num) {
                            case 0:
                                WallCell wallCell = new WallCell(new Coordinate(i, j));
                                cells[i][j] = wallCell;
                                panel.add(wallCell);
                                break;
                            case 4:
                                DoorCell doorCellOpen = new DoorCell(new Coordinate(i, j), true);
                                cells[i][j] = doorCellOpen;
                                panel.add(doorCellOpen);
                                cellStatuses[i][j] = UNVISITEDFLOORCELL;
                                break;
                            case 7:
                                DoorCell doorCell = new DoorCell(new Coordinate(i, j), false);
                                cells[i][j] = doorCell;
                                panel.add(doorCell);
                                break;
                            case 5:
                                StairCell stairCell = new StairCell(new Coordinate(i, j));
                                cells[i][j] = stairCell;
                                panel.add(stairCell);

                                break;
                            case 6:
                                StationCell stationCell = new StationCell(new Coordinate(i, j));
                                cells[i][j] = stationCell;
                                if(startStationCell == null) {
                                    // initialize the start station cell of the floor plan
                                    startStationCell = stationCell;
                                }
                                // set start station as visited cell
                                cellStatuses[i][j] = VISITEDFLOORCELL;
                                panel.add(stationCell);
                                break;
                            default:
                                FloorCell floorCell = new FloorCell(new Coordinate(i, j), num);
                                floorCells.add(floorCell);

//                                Random generator = new Random();
//                                int randomDirtAmount = generator.nextInt(5);          // give a random dirt amount from 0 to 4
//                                floorCell.setDirtAmount(randomDirtAmount);
//
//                                floorCell.setText(randomDirtAmount+"");
                                cells[i][j] = floorCell;
                                cellStatuses[i][j] = UNVISITEDFLOORCELL;
                                panel.add(floorCell);
                        }
                    } else throw new IOException("File data is does not match rows and columns");
                }
            }

            add(panel, BorderLayout.CENTER);

//            add(overlay, BorderLayout.CENTER);

            // add info panel about views
            addInfoPanel();
            addControlBtnPanel();
        } catch (IOException e) {
            throw e;
        } finally {
            if(s != null) {
                s.close();
            }
        }
    }

    public void resetDirtToFloor() {
        for(FloorCell floorCell: floorCells) {
            Random generator = new Random();
            int randomDirtAmount = generator.nextInt(5);          // give a random dirt amount from 0 to 4
            floorCell.setDirtAmount(randomDirtAmount);

            floorCell.setText(randomDirtAmount+"");
            floorCell.setVisited(false);
            floorCell.updateUI();
        }
        for(int i = 0; i < cellStatuses.length; i++) {
            for(int j = 0; j < cellStatuses[0].length; j++) {
                if(cellStatuses[i][j] == VISITEDFLOORCELL) {
                    cellStatuses[i][j] = UNVISITEDFLOORCELL;
                }
            }
        }
    }

    public CellStatus[][] getCellStatuses() {
        return cellStatuses;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public int getFloorLayoutColumns() {
        return cells.length;
    }

    public int getFloorLayoutRows() {
        return cells[0].length;
    }

    public StationCell getStartStationCell() {
        return startStationCell;
    }

    public void setStartStationCell(StationCell startStationCell) {
        this.startStationCell = startStationCell;
    }

    private void addControlBtnPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        startBtn = new JButton("Start");
        stopBtn = new JButton("Reset Dirt");

        panel.add(startBtn);
        panel.add(stopBtn);

        add(panel, BorderLayout.NORTH);
    }


    private void addInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 9));

        JLabel label = new JLabel("Sweep Machine");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Utility.SWEEP_MACHINE_COLOR);
        panel.add(label);

        // wall label
        JLabel wallLabel = new JLabel("Wall");
        wallLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wallLabel.setOpaque(true);
        wallLabel.setBackground(Utility.WALL_COLOR);
        wallLabel.setForeground(Color.WHITE);
        panel.add(wallLabel);

        JLabel bareLabel = new FloorCell(new Coordinate(0, 0), 1);
        bareLabel.setText("Bare Floor");
        bareLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(bareLabel);

        JLabel lowPileFloorLabel = new FloorCell(new Coordinate(0, 0), 2);
        lowPileFloorLabel.setText("Low Pile Floor");
        lowPileFloorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lowPileFloorLabel);

        JLabel highPileFloorLabel = new FloorCell(new Coordinate(0, 0), 3);
        highPileFloorLabel.setText("High Pile Floor");
        highPileFloorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(highPileFloorLabel);

        JLabel openDoorLabel = new DoorCell(new Coordinate(0, 0), true);
        openDoorLabel.setBackground(Utility.OPEN_DOOR_COLOR);
        openDoorLabel.setText("Open Door");
        openDoorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(openDoorLabel);
        
        JLabel closedDoorLabel = new DoorCell(new Coordinate(0, 0), false);
        closedDoorLabel.setText("Closed Door");
        closedDoorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(closedDoorLabel);

        JLabel stairLabel = new StairCell(new Coordinate(0, 0));
        stairLabel.setText("Stair");
        stairLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(stairLabel);

        JLabel chargingStationLabel = new StationCell(new Coordinate(0, 0));
        chargingStationLabel.setText("Charging Station");
        chargingStationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(chargingStationLabel);

        add(panel, BorderLayout.SOUTH);
    }

    public JButton getStartBtn() {
        return startBtn;
    }

    public JButton getStopBtn() {
        return stopBtn;
    }

}
