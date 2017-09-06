package bit.datason.JUT;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestRack {
	
	private Rack rackA;
	private Rackable module1;
	private Rackable module2;
	
	@Before
	public void setup() {
		rackA = new Rack("Rack A", 10);
		module1 = new Module("JUnit Module A", 2);
		module2 = new Module("JUnit Module B", 4);
	}
	
	@After
	public void tearDown() {
		rackA = null;
	}
	
	public void printUContent(Rack rack) {
		int c = 0;
		for (Rackable module : rack.getUSpace()) {
			c++;		
			System.out.println(c + ": " + module);
		}
	}

	@Test
	public void testMount_shouldOccupyZeroOnePosInArrayAndTwoNull() {
		rackA.mount(module1, 1);
		assertTrue(rackA.getUSpace()[0] == module1 
				&& rackA.getUSpace()[1] == module1
				&& rackA.getUSpace()[2] == null);
	}
	
	@Test
	public void testMount_positionValidation_negativePosScenario() {
		assertFalse(rackA.mount(module1,-1));
	}
	
	@Test
	public void testMount_positionValidation_11tooBigPos_shouldReturnFalse() {
		assertFalse(rackA.mount(module2, 11));
	}

	@Test
	public void testMount_positionValidation_moduleShouldNotFitAtPos8_returnFalse() {
		assertFalse(rackA.mount(module2, 8));
	}
	
	@Test
	public void testUnmount_moduleDoesNotExist_shouldReturnFalse() {
		assertFalse(rackA.unmount(module2));
	}
	
	@Test
	public void testUnmount_moduleExists_scenarioA() {
		rackA.mount(module1, 1);
		assertTrue(rackA.unmount(module1));
	}
	
	@Test
	public void testUnmount_moduleExists_scenarioA_assertArrayPos0To1AreNull() {
		rackA.mount(module1, 1);
		rackA.unmount(module1);
		assertTrue(rackA.getUSpace()[0] == null && rackA.getUSpace()[1] == null);
	}
	
	@Test
	public void testUnmount_moduleExists_scenarioB() {
		rackA.mount(module2, 7);
		assertTrue(rackA.unmount(module2));
	}
	
	@Test
	public void testUnmount_moduleExists_scenarioB_assertArrayPos6To9Null() {
		rackA.mount(module2, 7);
		rackA.unmount(module2);
		boolean result = true;
		for (int i = 6; i < 10; i++) {
			if (rackA.getUSpace()[i] != null) {
				result = false;
				break;
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void testGetUnitsOccupied() {
		rackA.mount(module1, 1);  // should take 2U
		rackA.mount(module2, 4);  // should take 4U
		assertTrue(module1.getUSize() + module2.getUSize() == rackA.getUnitsOccupied());		
	}
	
	@Test
	public void testFindSpace_ScenarioA() {
		rackA.mount(module1, 2);  // Occupies 2-3
		rackA.mount(module1, 5);  // Occupies 5-6
		rackA.mount(new Module("EQP-WA", 2), 7); // Occupies 7-8
		assertEquals(9, rackA.findSpace(2));  // Available slot for this U size should be 9
	}
	
	@Test 
	public void testFindSpace_ScenarioB() {
		rackA.mount(module2, 1);  					// occupies 1-4
		rackA.mount(new Module("JUnit 4U", 4), 7);	// occupies 7-10
		assertEquals(5, rackA.findSpace(module1.getUSize()));
	}
	
	@Ignore
	public void testMountImplicit() {
		rackA.mount(module1, 2);  // Occupies 2-3
		rackA.mount(module1, 5);  // Occupies 5-6
		rackA.mount(new Module("EQP-WA", 2), 7); // Occupies 7-8
		rackA.mount(new Module("JUnit 2U", 2));
	}

}
