package edu.depaul.cdm.se459.model;

/**
 * Created by Athina on 10/12/16.
 */
/**
 * 
 * This class will contain what a cell will have
 * its point depending on x,y,
 * the surface type that a cell can be
 * if its clean or not
 * if the cell has an obstacle , its open or not
 * if the cell its a charging station
 */

public class Cells {

	private Coordinate coordinatePoint;
	private int surface;
	private int isdirty;

	public Cells(int x, int y) {
		coordinatePoint = new Coordinate(x, y);
	}

	public void setPoint(Coordinate coordinatePoint) {
		this.coordinatePoint = coordinatePoint;
	}

	public Coordinate getPoint() {
		return coordinatePoint;
	}

	// get x,y 

	public int getX() {

		return coordinatePoint.getX();
	}

	public int getY() {

		return this.coordinatePoint.getY();

	}
	/**
	 * we could have an enum for surface
	 */
	//we will add the surface
	public void setSurface(int surface) {
        this.surface = surface;
    }
	public int getSurface() {
    	return surface;
    }
	//movement
	/**
	 * we could have an enum for direction
	 */
	//set and the paths
	
	//we have to set also dirt detection
    public void setDirt(int isdirty) throws Exception {

        if (isdirty < 0) {
        	throw new Exception("The cell is clean");
        }

        this.isdirty = isdirty;
    }
	
	 public int getDirt() {
	      return isdirty;
	    }
	 
	//obstacles

}
