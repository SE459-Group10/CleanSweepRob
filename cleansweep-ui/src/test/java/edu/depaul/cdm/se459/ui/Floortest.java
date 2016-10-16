package edu.depaul.cdm.se459.ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;


/**
 * Unit test for testing the floor plan
 */
public class Floortest {

	@Test
	public void testFloorPlanWithValidDataFile(){
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("file/floorplan.txt").getFile());
			MainFrame mainFrame = new MainFrame(file);
			assertTrue(true);
		} catch (IOException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testFloorPlanWithEmptyDataFile(){
		try {
			ClassLoader classLoader1 = getClass().getClassLoader();
			File file = new File(classLoader1.getResource("file/floorplan-test-0.txt").getFile());
			MainFrame mainFrame = new MainFrame(file);
			assertTrue(false);
		} catch (IOException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testFloorPlanWithNotEnoughDataFile(){
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("file/floorplan-test-1.txt").getFile());
			MainFrame mainFrame = new MainFrame(file);
			assertTrue(false);
		} catch (IOException e) {
			assertTrue(true);
		}
	}
}
