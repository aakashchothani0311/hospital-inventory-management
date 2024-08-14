package application;

import java.sql.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddDialogHandler {
	
	@FXML TextField iName;
	@FXML TextField qty;
	@FXML TextField price;
	@FXML DatePicker purDate;
	@FXML DatePicker expDate;
	@FXML TextField dept;
	
	@FXML CheckBox isConsumable;
	@FXML GridPane consumableGrid;
	@FXML GridPane fixedItemGrid;

	private EventHandlerDispatcher dispatcherObj;
	private Stage dialogStage;
	
	public void init(EventHandlerDispatcher dispatcherObj, Stage dialogStage) {
		this.dispatcherObj = dispatcherObj;
		this.dialogStage = dialogStage;		
	}
	
	public void handleIsConsumableCBox(){
		consumableGrid.setVisible(isConsumable.isSelected());
		fixedItemGrid.setVisible(!isConsumable.isSelected());	
	}
	
	public void handleAdd() {
		DBController dbc = new DBController();
		EventHandlerHelper helperObj = new EventHandlerHelper();
		
		Item i;
		if(isConsumable.isSelected())
			i = new Consumables(0, iName.getText(), Integer.parseInt(qty.getText()), Double.parseDouble(price.getText()), Date.valueOf(purDate.getValue()), Date.valueOf(expDate.getValue()));
		else
			i = new FixedItem(0, iName.getText(), Integer.parseInt(qty.getText()), Double.parseDouble(price.getText()), Date.valueOf(purDate.getValue()), dept.getText());		
		
		int result = dbc.insertRecord(i);
		
		if(result != -1) {
			i.setItemNo(result);
			if(i.getIsConsumable())
				dispatcherObj.handleConInsert((Consumables) i);
			else
				dispatcherObj.handleFIInsert((FixedItem) i);
				
			helperObj.showAlert(Alert.AlertType.INFORMATION, "Success", "Item inserted successfully.", "");			
		} else
			helperObj.showAlert(Alert.AlertType.ERROR, "Error", "Insert operation failed.", "Error occurred while inserting the item. Please contact IT team.");
		
		dialogStage.close();
	}
}