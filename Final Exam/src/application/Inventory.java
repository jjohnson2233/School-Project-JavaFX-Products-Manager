package application;

import java.util.ArrayList;

public class Inventory {
	private ArrayList<Product> products = new ArrayList<Product>();
	private ArrayList<Part> allParts = new ArrayList<Part>();
	
	
	public void addProduct(Product product) {
		products.add(product);
	}
	
	public boolean removeProduct(int productIndex) {
		return products.remove(productIndex) != null;
	}
	
	public Product lookupProduct(int searchText) {
		Product searchResult = null;
		for (Product product : products) {
			if (product.getProductID() == searchText) {
				searchResult = product;
			}
		}
		return searchResult;
	}
	
	public void updateProduct(int id) {
		
	}
	
	public void addPart(Part part) {
		allParts.add(part);
	}
	
	public boolean deletePart(Part part) {
		return allParts.remove(part);
	}
	
	public Part lookupPart(int searchText) {
		Part searchResult = null;
		for (Part part : allParts) {
			if (part.getPartID() == searchText) {
				searchResult = part;
			}
		}
		return searchResult;
	}
	
	public void updatePart(int id) {
		for (Part part : allParts) {
			if (part.getPartID() == id) {
				try {
					part = ModifyPartScreenController.inhouseData;
				} catch (Exception e) {
					part = ModifyPartScreenController.outsourcedData;
				}
			}
		}
	}
	
	public ArrayList<Part> getAllParts() {
		ArrayList<Part> list = new ArrayList<Part>();
		for (Part part : allParts) {
			list.add(part);
		}
		return list;
	}
	
	public ArrayList<Product> getAllProducts() {
		ArrayList<Product> list = new ArrayList<Product>();
		for (Product product : products) {
			list.add(product);
		}
		return list;
	}
}