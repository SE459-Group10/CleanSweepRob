package edu.depaul.cdm.se459.ui;

import javax.swing.*;

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
        setVisible(true);
    }

}
