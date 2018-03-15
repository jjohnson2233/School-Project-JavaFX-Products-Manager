package application;

public class Inhouse extends Part {
	
	public Inhouse(int partID, String name, Double price, int inStock, int max, int min) {
		this.setPartID(partID);
		this.setName(name);
		this.setPrice(price);
		this.setInStock(inStock);
		this.setMin(min);
		this.setMax(max);
	}

	private int machineID;
	
	
	public void setMachineID(int id) {
		machineID = id;
	}
	
	public int getMachineID() {
		return machineID;
	}
}
