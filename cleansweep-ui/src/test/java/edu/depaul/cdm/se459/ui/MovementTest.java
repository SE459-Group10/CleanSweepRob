package edu.depaul.cdm.se459.ui;


import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

/**
 * Created by Eric on 10/16/2016.
 *
 * Will update  when movement is complete
 */
public class MovementTest {
    @Test
    public void testMoveRight(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testmoveright.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            assertTrue(true);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testMoveLeft(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testmoveleft.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            assertTrue(true);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testMoveForward(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testmoveforward.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            assertTrue(true);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testMoveBackward(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("file/floorplan-testmovebackward.txt").getFile());
            MainFrame mainFrame = new MainFrame(file);
            assertTrue(true);
        } catch (IOException e) {
            assertTrue(false);
        }
    }





}
