package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Suqing on 10/3/16.
 */
public class Cell extends JLabel {

    private Coordinate coordinate;
    private Color backgroundColor;

    public Cell(){}

    public Cell(Color color, Coordinate coordinate){
        super();
        this.coordinate = coordinate;
        this.backgroundColor = color;
        this.setOpaque(true);
        this.setBackground(color);
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
