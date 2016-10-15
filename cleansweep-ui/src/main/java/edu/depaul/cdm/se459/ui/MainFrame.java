package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Suqing on 10/2/16.
 */
public class MainFrame extends JFrame {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;
    private static final String APP_NAME = "Clean Sweep Machine";
    private Cell[][] cells;

    public MainFrame() {
        super(APP_NAME);
        constructAppWindow();
    }

    private void constructAppWindow() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);    // position not in top left corner, but in center of screen
        processFloorPlan();
        setVisible(true);
    }

    private void processFloorPlan() {
        Scanner s = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan.txt").getFile());
            s = new Scanner(file);
            int cols = 0;
            int rows = 0;

            if(s.hasNextInt()) {
                cols = s.nextInt();
            } else return;
            if(s.hasNextInt()) {
                rows = s.nextInt();
            } else return;

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(rows, cols));

            cells = new Cell[cols][rows];

            for(int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    if (s.hasNextInt()) {
                        int num = s.nextInt();
                        switch (num) {
                            case 0:
                                WallCell wallCell = new WallCell(new Coordinate(i, j));
                                cells[i][j] = wallCell;
                                panel.add(wallCell);
                                break;
                            case 4:
                                DoorCell doorCell = new DoorCell(new Coordinate(i, j), true);
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
                                panel.add(stationCell);
                                break;
                            default:
                                FloorCell floorCell = new FloorCell(new Coordinate(i, j), num);
                                Random generator = new Random();
                                int randomDirtAmount = generator.nextInt(10);          // give a random dirt amount from 0 to 9
                                floorCell.setDirtAmount(randomDirtAmount);
                                System.out.println(floorCell.getDirtAmount());
                                floorCell.setText(randomDirtAmount+"");
                                cells[i][j] = floorCell;
                                panel.add(floorCell);
                        }
                    }
                }
            }

            add(panel, BorderLayout.CENTER);

            // add info panel about views
            addInfoPanel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(s != null) {
                s.close();
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    private void addInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 7));

        // wall label
        JLabel wallLabel = new JLabel("Wall");
        wallLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wallLabel.setOpaque(true);
        wallLabel.setBackground(Color.BLACK);
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
        openDoorLabel.setText("Open Door");
        openDoorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(openDoorLabel);

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

}
