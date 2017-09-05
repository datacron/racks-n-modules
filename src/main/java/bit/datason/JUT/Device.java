package bit.datason.JUT;

public abstract class Device {
	
	private String name;
	private boolean isOn = false;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void turnOn() {
		isOn = true;
	}
	
	public void turnOff() {
		isOn = false;
	}

}
