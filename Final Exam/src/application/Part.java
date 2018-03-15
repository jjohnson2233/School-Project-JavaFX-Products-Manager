package application;

public abstract class Part {
	private int partID;
	private String name;
	private double price;
	private int inStock;
	private int min;
	private int max;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getPrice() {
		return this.price;
	}
	
	public void setInStock(int inStock) {
		this.inStock = inStock;
	}
	
	public int getInStock() {
		return this.inStock;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
	
	public int getMin() {
		return this.min;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public int getMax() {
		return this.max;
	}

	public void setPartID(int id) {
		this.partID = id;
	}

	public int getPartID() {
		return this.partID;
	}
}
