package bit.datason.JUT;

import java.util.ArrayList;

public class Rack {
	
	private String name;
	private Rackable[] shelves;

	private int maxUnits;
	private int unitsOccupied;
	private int nModules;
	
	public Rack(String name, int maxUnits) {
		this.name = name;
		this.maxUnits = maxUnits;
		this.shelves = new Rackable[maxUnits];
		this.unitsOccupied = 0;
		this.nModules = 0;
	}
	
	public boolean mount(Rackable module) {
		// Check if module fits in rack in the first place
		if (module.getUSize() > maxUnits-unitsOccupied) {
			System.out.println("Doesn't fit");
			return false;  // doesn't fit anyway
		} else {
			// Mount on the appropriate slot(s)
			int result = findSlot(module.getUSize());
			if (result != -1) {
				mount(module, result);
				return true;
			}
		}
		return false;
	}

	public boolean mount(Rackable module, int slot) {
		// Validate arguments
		if ( (slot + module.getUSize() <= maxUnits+1) && slot>0) {
			// true; mount module
			for (int i = slot-1; i<slot-1 + module.getUSize(); i++) {
				shelves[i] = module;
				unitsOccupied++;
			}
			nModules++;
			return true;
		} else {
			return false;
		}
	}

	public boolean unmount(Rackable module) {
		for (int i = 0; i < maxUnits; i++) {	// TODO fix useless iteration in the end
			if (shelves[i] == module) {
				for (int j = i; j < i + module.getUSize(); j++) {
					shelves[j] = null;
					unitsOccupied--;
				}
				nModules--;
				return true;
			}
		}
		return false;
	}

	public int getUnitsOccupied() {
		return unitsOccupied;
	}
	
	// Returns -1 if no consecutive slots of module size are found
	// otherwise it returns the first available U position 
	public int findSlot(int uSize) {
		int cSpace = 0;
		for (int i = 0; i < maxUnits; i++) {
			if (shelves[i] ==  null) {
				cSpace++;
				if (cSpace == uSize) {
					return i;
				}
			} else {
				cSpace = 0;
			}
		}
		return -1;
	}
	
	public Rackable[] getShelves() {
		return shelves;
	}
	
	
	
}
