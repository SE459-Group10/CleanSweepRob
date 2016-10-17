package edu.depaul.cdm.se459.ui;

import edu.depaul.cdm.se459.model.Coordinate;
import edu.depaul.cdm.se459.model.Utility;

import java.awt.*;

/**
 * Created by Suqing on 10/3/16.
 */

/**
 * A subclass of the class Cell that contains the coordinates of the cell and
 * its floor type depending on the floor type the color is changing the cell can
 * be dirt or clean
 */
public class FloorCell extends Cell {

	/**
	 * floorType: 1: bare floor 2: low pile 3: high pile
	 */
	private int floorType;
	private Coordinate coordinate;
	private int dirtAmount;

	public FloorCell(Coordinate coordinate, int floorType) {
		// super(Color.WHITE, coordinate);
		super();
		this.coordinate = coordinate;
		this.floorType = floorType;
		switch (floorType) {
		case 1:
			this.setBackground(Utility.BARE_FLOOR_COLOR);
			break;
		case 2:
			this.setBackground(Utility.LOW_PILE_COLOR);
			break;
		case 3:
			this.setBackground(Utility.HIGH_PILE_COLOR);
			break;
		default:
			this.setBackground(Color.WHITE);
			this.setText("Invalid input");
		}
		this.setOpaque(true);
	}

	public int getFloorType() {
		return floorType;
	}

	public void setFloorType(int floorType) {
		this.floorType = floorType;
	}

	public int getDirtAmount() {
		return dirtAmount;
	}

	public void setDirtAmount(int dirtAmount) {
		this.dirtAmount = dirtAmount;
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		this.setHorizontalAlignment(CENTER);
	}
}
