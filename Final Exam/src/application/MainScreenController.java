package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {
	
	//Instantiate inventories
	public static Inventory partInventory = new Inventory();	
	public static Inventory productInventory = new Inventory();
	
	//Selected table items
	public static Part selectedPartData;
	public static Product selectedProductData;
	
	

	
	//Connect table views//
	
	//part table
	@FXML private TableView<Part> partTableView;
	@FXML private TableColumn<Part, Integer> partIDColumn;
	@FXML private TableColumn<Part, String> partNameColumn;
	@FXML private TableColumn<Part, Integer> partInventoryLevelColumn;
	@FXML private TableColumn<Part, Double> partPriceColumn;
	
	
	//product table
	@FXML private TableView<Product> productTableView;
	@FXML private TableColumn<Product, Integer> productIDColumn;
	@FXML private TableColumn<Product, String> productNameColumn;
	@FXML private TableColumn<Product, Integer> productInventoryLevelColumn;
	@FXML private TableColumn<Product, Double> productPriceColumn;
	
	
	
	
	//Exit button
	@FXML private Button exitButton;
	
	@FXML
	private void handleExitButtonAction(ActionEvent event) {
		Stage stage = (Stage) exitButton.getScene().getWindow();
	    stage.close();
	}
	
	
	//Parts section//
	
	//Search parts field
	@FXML private TextField searchPartsTextField;
	
	//Search parts button
	
	@FXML
	private void handleSearchPartsButtonAction(ActionEvent event) {
		if (searchPartsTextField.getText() == null || searchPartsTextField.getText().trim().isEmpty()) {
			partTableView.getItems().setAll(partInventory.getAllParts());
		} else {
			partTableView.getItems().setAll(partInventory.lookupPart(Integer.parseInt(searchPartsTextField.getText())));
		}
	}
	
	//Add part button
	@FXML private Button addPartButton;
	
	@FXML
	private void handleAddPartsButtonAction(ActionEvent event) throws IOException {
		Parent root;
		Stage stage = (Stage) addPartButton.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Modify part button
	@FXML private Button modifyPartButton;
	
	@FXML
	private void handleModifyPartsButtonAction(ActionEvent event) throws IOException {
		selectedPartData = partTableView.getSelectionModel().getSelectedItem();
		
		Stage stage;
		Parent root;
		stage = (Stage) modifyPartButton.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("ModifyPartScreen.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Delete part button
	@FXML private Button deletePartButton;
	
	@FXML private void handleDeletePartsButtonAction(ActionEvent event) {
		Alert confirmPartDelete = new Alert(AlertType.CONFIRMATION);
		confirmPartDelete.setTitle("Confirm Delete");
		confirmPartDelete.setHeaderText("Delete part?");
		confirmPartDelete.setContentText("This part will be deleted");
		Optional<ButtonType> partResult = confirmPartDelete.showAndWait();
		if (partResult.get() == ButtonType.OK) {
			partInventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
			partTableView.getItems().remove(partTableView.getSelectionModel().getSelectedIndex());
		}
	}
	
	
	//Products section
	
	//Search products field
	@FXML private TextField searchProductsTextField;
	
	//Search products button
	@FXML private void handleSearchProductsButtonAction(ActionEvent event) {
		if (searchProductsTextField.getText() == null || searchProductsTextField.getText().trim().isEmpty()) {
			productTableView.getItems().setAll(productInventory.getAllProducts());
		} else {
			productTableView.getItems().setAll(productInventory.lookupProduct(Integer.parseInt(searchProductsTextField.getText())));
		}
	}
	
	//Add product button
	@FXML private Button addProductButton;
	
	@FXML
	private void handleAddProductsButtonAction(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) addProductButton.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Modify product button
	@FXML private Button modifyProductButton;
	
	@FXML
	private void handleModifyProductsButtonAction(ActionEvent event) throws IOException {
		selectedProductData = productTableView.getSelectionModel().getSelectedItem();
		
		Stage stage;
		Parent root;
		stage = (Stage) modifyProductButton.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("ModifyProductScreen.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Delete product button
	@FXML private Button deleteProductButton;
	
	@FXML 
	private void handleDeleteProductsButtonAction(ActionEvent event) {
		if (productTableView.getSelectionModel().getSelectedItem().getAssociatedParts().isEmpty()) {
			Alert confirmProductDelete = new Alert(AlertType.CONFIRMATION);
			confirmProductDelete.setTitle("Confirm Delete");
			confirmProductDelete.setHeaderText("Delete product?");
			confirmProductDelete.setContentText("This product will be deleted");
			Optional<ButtonType> productResult = confirmProductDelete.showAndWait();
			if (productResult.get() == ButtonType.OK) {
				productInventory.removeProduct(productTableView.getSelectionModel().getSelectedIndex());
				productTableView.getItems().remove(productTableView.getSelectionModel().getSelectedIndex());
			}
			
		} else {
			Alert nonEmptyProduct = new Alert(AlertType.ERROR);
			nonEmptyProduct.setTitle("Non-Empty Product Error");
			nonEmptyProduct.setHeaderText("Remove assiciated parts");
			nonEmptyProduct.setContentText("You cannot delete a product with parts still assigned to it. Remove the assigned parts from the product and try again.");
			nonEmptyProduct.showAndWait();
		}
	}
	
	
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Show part data
		partIDColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("partID"));
		partNameColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
		partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("inStock"));
		partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));
		partTableView.getItems().setAll(MainScreenController.partInventory.getAllParts());
		
		//Show product data
		productIDColumn.setCellValueFactory(new PropertyValueFactory<Product,Integer>("productID"));
		productNameColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
		productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Product,Integer>("inStock"));
		productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product,Double>("price"));
		productTableView.getItems().setAll(MainScreenController.productInventory.getAllProducts());
	}
}
