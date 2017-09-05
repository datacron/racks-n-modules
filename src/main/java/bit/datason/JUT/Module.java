package bit.datason.JUT;

public class Module extends Device implements Rackable {

	private int uSize;

	public Module(String name, int uSize) {
		this.setName(name);
		this.uSize = uSize;
	}

	public int getUSize() {
		return uSize;
	}

	@Override
	public String toString() {
		return "Module " + this.getName() +  ", " + uSize + "U";
	}
	
	
	

		
}
