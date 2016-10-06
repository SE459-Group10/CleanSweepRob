package edu.depaul.cdm.se459.ui;

import com.sun.xml.internal.bind.v2.runtime.Coordinator;
import edu.depaul.cdm.se459.model.Coordinate;

import javax.swing.*;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Suqing on 10/2/16.
 */
public class MainFrame extends JFrame {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;
    private static final String APP_NAME = "Clean Sweep Machine";

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

            while(s.hasNextInt()) {
                int i = s.nextInt();
                // 0: wall
                // else: any other type of floor
                switch (i) {
                    case 0: panel.add(new WallCell(new Coordinate(0, 0)));
                        break;
                    default: panel.add(new FloorCell(new Coordinate(0, 0), i));
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

        JLabel openDoorLabel = new FloorCell(new Coordinate(0, 0), 4);
        openDoorLabel.setText("Open Door");
        openDoorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(openDoorLabel);

        JLabel stairLabel = new FloorCell(new Coordinate(0, 0), 5);
        stairLabel.setText("Stair");
        stairLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(stairLabel);

        JLabel chargingStationLabel = new FloorCell(new Coordinate(0, 0), 6);
        chargingStationLabel.setText("Charging Station");
        chargingStationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(chargingStationLabel);

        add(panel, BorderLayout.SOUTH);
    }

}
