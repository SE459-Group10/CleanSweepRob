package edu.depaul.cdm.se459.model;

/**
 * Created by Eric on 10/16/2016.
 */
public class ControlSystem {

    private Coordinate location;
    private int batteryLife;
    private int dirtCapacity;

    public ControlSystem(Coordinate location, int batteryLife, int dirtCapacity){
        this.location = location;
        this.batteryLife  = batteryLife;
        this.dirtCapacity = dirtCapacity;
    }

    public Coordinate getLocation(){
        return location;
    }


    public int getBatteryLife(){
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife){
        this.batteryLife = batteryLife;
    }

    public int getDirtCapacity(){
        return dirtCapacity;
    }

    public void setDirtCapacity(int dirtCapacity){
        this.dirtCapacity = dirtCapacity;
    }


    public Coordinate moveLeft(Coordinate location){
        int x = location.getX();
        int y = location.getY();
         x  =  x - 1;
        Coordinate returnLeftLocation = new Coordinate(x,y);
        return returnLeftLocation;
    }

    public Coordinate moveRight(Coordinate location){
        int x = location.getX();
        int y = location.getY();
        x  =  x + 1;
        Coordinate returnRightLocation = new Coordinate(x,y);
        return returnRightLocation;

    }

    public Coordinate moveForward(Coordinate location){
        int x = location.getX();
        int y = location.getY();
        y  =  y - 1;
        Coordinate returnForwardLocation = new Coordinate(x,y);
        return returnForwardLocation;
    }

    public Coordinate moveBackward(Coordinate location){
        int x = location.getX();
        int y = location.getY();
        y  =  y + 1;
        Coordinate returnBackLocation = new Coordinate(x,y);
        return returnBackLocation;
    }

    //Remove Dirt Method

    public void removeDirt(int dirtAmount){
        int remainingCapacity = getDirtCapacity() - dirtAmount;
        setDirtCapacity(remainingCapacity);
    }



}
