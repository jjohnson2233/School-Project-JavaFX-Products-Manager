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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ModifyPartScreenController implements Initializable {
	
	//Text Fields
	@FXML private TextField id;
	@FXML private TextField name;
	@FXML private TextField price; 
	@FXML private TextField inv;
	@FXML private TextField max;
	@FXML private TextField min;
	@FXML private TextField optionalTextField;
	//Last text field label
	@FXML private Label optionalLabel;
	
	//Temporary variables
	public static Inhouse inhouseData;
	public static Outsourced outsourcedData;

	
	//Save button
	@FXML public Button saveButton;
	
	@FXML
	private void handleSaveButtonAction(ActionEvent event) throws IOException {
		if (Integer.parseInt(inv.getText()) <= Integer.parseInt(max.getText()) && Integer.parseInt(inv.getText()) >= Integer.parseInt(min.getText())) {
			System.out.println("Didn't catch");
			//Update the part with inputed data
			MainScreenController.selectedPartData.setName(name.getText());
			MainScreenController.selectedPartData.setPrice(Double.parseDouble(price.getText()));
			MainScreenController.selectedPartData.setInStock(Integer.parseInt(inv.getText()));
			MainScreenController.selectedPartData.setMax(Integer.parseInt(max.getText()));
			MainScreenController.selectedPartData.setMin(Integer.parseInt(min.getText()));
			
			//Determine if inhouse or outsourced
			if(outsourcedRadioButton.isSelected()) {
				if (MainScreenController.selectedPartData instanceof Outsourced) {
					outsourcedData = (Outsourced) MainScreenController.selectedPartData;
					outsourcedData.setCompanyName(optionalLabel.getText());
				} else {
					outsourcedData = new Outsourced(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(inv.getText()),
							Integer.parseInt(max.getText()), Integer.parseInt(min.getText()));
					outsourcedData.setCompanyName(optionalTextField.getText());
					MainScreenController.partInventory.deletePart(MainScreenController.selectedPartData);
					MainScreenController.partInventory.addPart(outsourcedData);
				}
			} else {
				if (MainScreenController.selectedPartData instanceof Inhouse) {
					inhouseData = (Inhouse) MainScreenController.selectedPartData;
				} else {
					inhouseData = new Inhouse(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(inv.getText()), Integer.parseInt(max.getText()),
							Integer.parseInt(min.getText()));
					inhouseData.setMachineID(Integer.parseInt(optionalTextField.getText()));
					MainScreenController.partInventory.deletePart(MainScreenController.selectedPartData);
					MainScreenController.partInventory.addPart(inhouseData);
				}
			}
			
			//Update the part in the inventory
			MainScreenController.partInventory.updatePart(Integer.parseInt(id.getText()));
			
			//Go back to the main screen
			Stage stage;
			Parent root;
			stage = (Stage) modifyPartCancelButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		//Display out of range error messages
		} else if (Integer.parseInt(min.getText()) > Integer.parseInt(max.getText())) {
			Alert impossibleRange = new Alert(AlertType.ERROR);
			impossibleRange.setTitle("Impossible Inventory Range");
			impossibleRange.setHeaderText("minimum higher than maximum");
			impossibleRange.setContentText("'min' needs to be less than or eqaul to 'max'");
			impossibleRange.showAndWait();
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
	@FXML public Button modifyPartCancelButton;
	
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
			stage = (Stage) modifyPartCancelButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	
	//Radio button group
	@FXML final ToggleGroup group = new ToggleGroup();
	
	//In-house radio button
	@FXML public RadioButton inhouseRadioButton;
	
	//Outsourced radio button
	@FXML public RadioButton outsourcedRadioButton;

	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		id.setText(String.valueOf(MainScreenController.selectedPartData.getPartID()));
		name.setText(MainScreenController.selectedPartData.getName());
		price.setText(String.valueOf(MainScreenController.selectedPartData.getPrice()));
		inv.setText(String.valueOf(MainScreenController.selectedPartData.getInStock()));
		max.setText(String.valueOf(MainScreenController.selectedPartData.getMax()));
		min.setText(String.valueOf(MainScreenController.selectedPartData.getMin()));
		inhouseRadioButton.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
			if (isSelected) {
				optionalLabel.setText("Machine ID");
			}
		});
		outsourcedRadioButton.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
			if (isSelected) {
				optionalLabel.setText("Company Name");
			}
		});
		
		if (MainScreenController.selectedPartData instanceof Outsourced)  {
			Outsourced outsourcedData = (Outsourced) MainScreenController.selectedPartData;
			outsourcedRadioButton.setSelected(true);
			optionalTextField.setText(outsourcedData.getCompanyName());
			
		} else {
			Inhouse inhouseData = (Inhouse) MainScreenController.selectedPartData;
			inhouseRadioButton.setSelected(true);
			optionalTextField.setText(String.valueOf(inhouseData.getMachineID()));
		}
	}

}
