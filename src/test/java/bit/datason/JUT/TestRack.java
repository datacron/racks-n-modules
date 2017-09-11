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
	public void testMount_shouldOccupy0_1PosInArrayAnd2Null() {
		rackA.mount(module1, 1);
		assertTrue(rackA.getUSpace()[0] == module1 
				&& rackA.getUSpace()[1] == module1
				&& rackA.getUSpace()[2] == null);
	}
	
	@Test
	public void testMount_shouldReturnFalse_whenUSpaceOccupied_at1_2() {
		rackA.mount(module1, 1);
		Rackable mod2 = new Module("Channel Strip", 2);
		assertFalse(rackA.mount(mod2, 1));
	}
	
	@Test
	public void testMount_shouldReturnFalse_whenUSpaceOccupied_at5_8() {
		rackA.mount(module2, 5);
		Rackable mod2 = new Module("Behringer X32 Rack", 4);
		assertFalse(rackA.mount(mod2, 5));
	}
	
	@Test
	public void testMount_shouldReturnFalse_whenUSpaceOccupied_at6_9_and_placed_at_3() {
		rackA.mount(module2, 6);
		Rackable mod2 = new Module("Behringer X32 Rack", 4);
		assertFalse(rackA.mount(mod2, 3));
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
		//assertTrue(rackA.unmount(module2));
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
		rackA.mount(new Module("EQP-WA", 2), 7); // Occupies pos 7-8 (6-7 in array)
		assertEquals(9, rackA.findSpace(2));  // Available pos for this U size should be 9
	}
	
	@Test 
	public void testFindSpace_ScenarioB() {
		rackA.mount(module2, 1);  					// occupies 1-4 (0-3 in array)
		rackA.mount(new Module("JUnit 4U", 4), 7);	// occupies 7-10 (6-9 in array)
		assertEquals(5, rackA.findSpace(module1.getUSize()));
	}
	
	
	@Test
	public void testMount_ScenarioA_should_mount_last_module_on_pos_9_10() {
		rackA.mount(module1, 2);  // Occupies 2-3
		rackA.mount(new Module("ChannelStrip", 2), 5);  // Occupies 5-6
		rackA.mount(new Module("EQP-WA", 2), 7); // Occupies 7-8 (6-7 in array)
		rackA.mount(new Module("ValveEQ", 2));  // should mount on 9-10 (8-9 in array)
		printUContent(rackA);
		assertTrue(rackA.getUSpace()[8] != null && rackA.getUSpace()[9] != null);
	}
	
	@Test
	public void testMount_ScenarioA_should_mount_last_module_on_pos_6_7() {
		rackA.mount(module1, 1);  // Occupies 1-2 (0-1 in array)
		rackA.mount(new Module("EQP-WA", 2), 4); // Occupies 4-5 (3-4 in array)
		rackA.mount(new Module("JUnit 2U", 2));  // should mount on 6-7 (5-6 array-wise)
		printUContent(rackA);
		assertTrue(rackA.getUSpace()[5] != null && rackA.getUSpace()[6] != null);
	}

	 

}
