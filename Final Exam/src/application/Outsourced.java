package application;

public class Outsourced extends Part {
	
	public Outsourced(int partID, String name, Double price, int inStock, int max, int min) {
		this.setPartID(partID);
		this.setName(name);
		this.setPrice(price);
		this.setInStock(inStock);
		this.setMin(min);
		this.setMax(max);
	}

	private String companyName;
	
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String name) {
		companyName = name;
	}
}
