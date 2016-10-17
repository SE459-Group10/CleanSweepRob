package edu.depaul.cdm.se459.service;

/**
 * Created by Suqing on 10/17/16.
 */
public class ControlSystem extends Thread {

    private SweepMachine sweepMachine;

    public ControlSystem(SweepMachine sweepMachine) {
        this.sweepMachine = sweepMachine;
    }

    @Override
    public void run() {
        super.run();
        try {
            while(sweepMachine.move()) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
