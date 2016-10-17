package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Suqing on 10/3/16.
 */

/**
 * A Cell class that contains the color and the coordinate of each cell
 */
public class Cell extends JLabel {

	private Coordinate coordinate;
	private Color backgroundColor;

	public Cell() {
	}
	public Cell(Color color, Coordinate coordinate) {
		super();
		this.coordinate = coordinate;
		this.backgroundColor = color;
		this.setOpaque(true);
		this.setBackground(color);
	}

	public Cell(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
