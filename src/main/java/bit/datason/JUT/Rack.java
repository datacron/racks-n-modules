package bit.datason.JUT;

public class Rack {
	
	private String name;
	private Rackable[] uSpace;

	private int maxUnits;
	private int unitsOccupied;
	private int nModules;
	
	public Rack(String name, int maxUnits) {
		this.name = name;
		this.maxUnits = maxUnits;
		this.uSpace = new Rackable[maxUnits];
		this.unitsOccupied = 0;
		this.nModules = 0;
	}

	public boolean mount(Rackable module) {
		// Check for U space
		if (module.getUSize() > maxUnits-unitsOccupied) {
			return false;  // not enough U space
		} else {
			// Proceed
			int result = findSpace(module.getUSize());
			if (result != -1) {
				mount(module, result);
				return true;
			}
		}
		return false;
	}

	public boolean mount(Rackable module, int position) {
		// arguments validation (valid > 0, module size acceptable for pos)
		if (position <= 0 || position + module.getUSize()-1 > maxUnits)
			return false;
		// space validation (target Us null)
		for (int i = position-1; i < position + module.getUSize()-1; i++) {
			if (uSpace[i] != null)	// TODO Failure trace (IOR)
				return false;
		} 
		if ( (position + module.getUSize() <= maxUnits+1) && position>0) {
			// true; mount module
			for (int i = position-1; i<position-1 + module.getUSize(); i++) {
				uSpace[i] = module;
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
			if (uSpace[i] == module) {
				for (int j = i; j < i + module.getUSize(); j++) {
					uSpace[j] = null;
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
	public int findSpace(int uSize) {
		int cSpace = 0;
		for (int i = 0; i < maxUnits; i++) {
			if (uSpace[i] ==  null) {
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
	
	public Rackable[] getUSpace() {
		return uSpace;
	}
	
	public int getnModules() {
		return nModules;
	}
	
	
	
}
