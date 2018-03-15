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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddPartScreenController implements Initializable {
	
	//Text Fields
	@FXML private TextField id;
	@FXML private TextField name;
	@FXML private TextField price; 
	@FXML private TextField inv;
	@FXML private TextField max;
	@FXML private TextField min;
	@FXML private TextField optionalTextField;
	//last field label
	@FXML private Label optionalLabel;
	
	//Save button
	@FXML public Button saveButton;
	
	@FXML
	private void handleSaveButtonAction(ActionEvent event) throws IOException {
		//checks to make sure that the inventory is in range
		if (Integer.parseInt(inv.getText()) <= Integer.parseInt(max.getText()) && Integer.parseInt(inv.getText()) >= Integer.parseInt(min.getText()) && 
				Integer.parseInt(max.getText()) >= Integer.parseInt(min.getText())) {
			if (outsourcedRadioButton.isSelected()) {
				Outsourced outsourcedPart = new Outsourced(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(inv.getText()), Integer.parseInt(max.getText()),
						Integer.parseInt(min.getText()));
				outsourcedPart.setCompanyName(optionalTextField.getText());
				MainScreenController.partInventory.addPart(outsourcedPart);
			} else {
				Inhouse inhousePart = new Inhouse(Integer.parseInt(id.getText()), name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(inv.getText()), Integer.parseInt(max.getText()), 
						Integer.parseInt(min.getText()));
				inhousePart.setMachineID(Integer.parseInt(optionalTextField.getText()));
				MainScreenController.partInventory.addPart(inhousePart);
			}
			
			//Return to the main screen
			Stage stage;
			Parent root;
			stage = (Stage) partCancelButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
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
	@FXML public Button partCancelButton;
	
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
			stage = (Stage) partCancelButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	
	//Radio button group
	@FXML final ToggleGroup group = new ToggleGroup();
	
	//In-House radio button
	@FXML private RadioButton inHouseRadioButton;
	
	
	//Outsourced radio button
	@FXML private RadioButton outsourcedRadioButton;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		inHouseRadioButton.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
			if (isSelected) {
				optionalLabel.setText("Machine ID");
			}
		});
		
		outsourcedRadioButton.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
			if (isSelected) {
				optionalLabel.setText("Company Name");
			}
		});
	}

}