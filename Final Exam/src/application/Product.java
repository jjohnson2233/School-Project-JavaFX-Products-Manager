package application;

import java.util.ArrayList;

public class Product {
	private ArrayList<Part> associatedParts = new ArrayList<Part>();
	private int productID;
	private String name;
	private Double price;
	private int inStock;
	private int min;
	private int max;
	
	public Product(int productID, String name, Double price, int inStock, int min, int max) {
		this.productID = productID;
		this.name = name;
		this.price = price;
		this.inStock = inStock;
		this.min = min;
		this.max = max;
	}
	
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
	
	public void addAssociatedPart(Part part) {
		associatedParts.add(part);
	}
	
	public boolean removeAssociatedPart(int id) {
		boolean result = false;
		Part trashPart = null;
		for (Part part : associatedParts) {
			if (part.getPartID() == id) {
				result = true;
				trashPart = part;
			}
		}
		if (result) {
			associatedParts.remove(trashPart);
		}
		return result;
	}
	
	public Part lookupAssociatedPart(int partIndex) {
		return associatedParts.get(partIndex);
	}
	
	public ArrayList<Part> getAssociatedParts() {
		return associatedParts;
	}
	
	public void setProductID(int id) {
		this.productID = id;
	}
	
	public int getProductID() {
		return this.productID;
	}
}
