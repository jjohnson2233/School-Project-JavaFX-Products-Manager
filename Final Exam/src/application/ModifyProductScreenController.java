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

public class ModifyProductScreenController implements Initializable {
	
	//Text Fields
	@FXML private TextField id;
	@FXML private TextField name;
	@FXML private TextField inv;
	@FXML private TextField price; 
	@FXML private TextField max;
	@FXML private TextField min;
	@FXML private TextField search;
	
	//Unassigned parts table
	@FXML private TableView<Part> unassignedPartsTable;
	@FXML private TableColumn<Part, Integer> unassignedPartIDColumn;
	@FXML private TableColumn<Part, String> unassignedPartNameColumn;
	@FXML private TableColumn<Part, Integer> unassignedPartInventoryLevelColumn;
	@FXML private TableColumn<Part, Double> unassignedPartPriceColumn;
	
	//Assigned parts table
	@FXML private TableView<Part> assignedPartsTable;
	@FXML private TableColumn<Part, Integer> assignedPartIDColumn;
	@FXML private TableColumn<Part, String> assignedPartNameColumn;
	@FXML private TableColumn<Part, Integer> assignedPartInventoryLevelColumn;
	@FXML private TableColumn<Part, Double> assignedPartPriceColumn;
	
	
	//Search button
	@FXML
	private void handleSearchButtonAction(ActionEvent event) {
		if (search.getText() == null || search.getText().trim().isEmpty()) {
			unassignedPartsTable.getItems().setAll(MainScreenController.partInventory.getAllParts());
		} else {
			unassignedPartsTable.getItems().setAll(MainScreenController.partInventory.lookupPart(Integer.parseInt(search.getText())));
		}
	}
	
	//Add button
	@FXML
	private void handleAddButtonAction(ActionEvent event) {
		//add the selected part to the assigned parts list
		MainScreenController.selectedProductData.addAssociatedPart(MainScreenController.partInventory.lookupPart(unassignedPartsTable.getSelectionModel().getSelectedItem().getPartID()));
		//delete the part from the unassigned list
		MainScreenController.partInventory.deletePart(unassignedPartsTable.getSelectionModel().getSelectedItem());
		//Refresh the table views
		initialize(null, null);
	}
	
	//Delete button
	@FXML
	private void handleDeleteButtonAction(ActionEvent event) {
		MainScreenController.partInventory.addPart(assignedPartsTable.getSelectionModel().getSelectedItem());
		Part selectedPart = assignedPartsTable.getSelectionModel().getSelectedItem();
		MainScreenController.selectedProductData.removeAssociatedPart(selectedPart.getPartID());
		initialize(null,null);
	}
	
	//Save button
	@FXML private Button saveButton;
	
	@FXML
	private void handleSaveButtonAction(ActionEvent event) throws IOException {
		double partsPriceSum = 0;
		for (Part part : MainScreenController.selectedProductData.getAssociatedParts()) {
			partsPriceSum += part.getPrice();
		}
		if (Double.parseDouble(price.getText()) >= partsPriceSum && Integer.parseInt(inv.getText()) <= Integer.parseInt(max.getText()) && 
				Integer.parseInt(inv.getText()) >= Integer.parseInt(min.getText()) && 
				Integer.parseInt(max.getText()) >= Integer.parseInt(min.getText())) {
			try {
				//Modify the properties of the product
				MainScreenController.selectedProductData.setProductID(Integer.parseInt(id.getText()));
				MainScreenController.selectedProductData.setName(name.getText());
				MainScreenController.selectedProductData.setInStock(Integer.parseInt(inv.getText()));
				MainScreenController.selectedProductData.setPrice(Double.parseDouble(price.getText()));
				MainScreenController.selectedProductData.setMax(Integer.parseInt(max.getText()));
				MainScreenController.selectedProductData.setMin(Integer.parseInt(min.getText()));
				
				//Go back to main screen
				Stage stage;
				Parent root;
				stage = (Stage) saveButton.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				Alert emptyFieldError = new Alert(AlertType.ERROR);
				emptyFieldError.setTitle("Empty Fields Error");
				emptyFieldError.setHeaderText("All fields must be filled");
				emptyFieldError.setContentText("One or more fields are empty. Please correct the fields and try again.");
				emptyFieldError.showAndWait();
			}
		} else if  (Double.parseDouble(price.getText()) < partsPriceSum) {
			Alert priceError = new Alert(AlertType.ERROR);
			priceError.setTitle("Price Error");
			priceError.setHeaderText("Price cannot be less than the sum of associated parts");
			priceError.setContentText("Make the price of the product greater than or equal to the total price of the parts assigned to it and try again.");
			priceError.showAndWait();
			
		} else if (Integer.parseInt(inv.getText()) < Integer.parseInt(min.getText())) {
			Alert outOfRangeError = new Alert(AlertType.ERROR);
			outOfRangeError.setTitle("Inventory out of range");
			outOfRangeError.setHeaderText("Inventory lower than minimum");
			outOfRangeError.setContentText("'inv' needs to be greater than or eqaul to 'min'");
			outOfRangeError.showAndWait();
		} else if (Integer.parseInt(inv.getText()) > Integer.parseInt(max.getText())) {
			Alert outOfRangeError = new Alert(AlertType.ERROR);
			outOfRangeError.setTitle("Inventory out of range");
			outOfRangeError.setHeaderText("Inventory higher than maximum");
			outOfRangeError.setContentText("'inv' needs to be less than or eqaul to 'max'");
			outOfRangeError.showAndWait();
		}
	}
	
	//Cancel button
	@FXML public Button modifyProductCancelButton;
	
	@FXML
	private void handleCancelButtonAction(ActionEvent event) throws IOException {
		Alert confirmCancel = new Alert(AlertType.CONFIRMATION);
		confirmCancel.setTitle("Confirm Cancel");
		confirmCancel.setHeaderText("Go back to home screen?");
		confirmCancel.setContentText("This data for this part will be deleted and you will be taken back to the home screen");
		Optional<ButtonType> result = confirmCancel.showAndWait();
		if (result.get() == ButtonType.OK) {
			Stage stage;
			Parent root;
			stage = (Stage) modifyProductCancelButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Fill properties text fields
		id.setText(String.valueOf(MainScreenController.selectedProductData.getProductID()));
		name.setText(MainScreenController.selectedProductData.getName());
		price.setText(String.valueOf(MainScreenController.selectedProductData.getPrice()));
		inv.setText(String.valueOf(MainScreenController.selectedProductData.getInStock()));
		max.setText(String.valueOf(MainScreenController.selectedProductData.getMax()));
		min.setText(String.valueOf(MainScreenController.selectedProductData.getMin()));
	
		//Show unassigned parts
		unassignedPartIDColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("partID"));
		unassignedPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
		unassignedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("inStock"));
		unassignedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));
		unassignedPartsTable.getItems().setAll(MainScreenController.partInventory.getAllParts());
		
		//Show assigned parts
		assignedPartIDColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("partID"));
		assignedPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
		assignedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("inStock"));
		assignedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));
		assignedPartsTable.getItems().setAll(MainScreenController.selectedProductData.getAssociatedParts());
	}
}