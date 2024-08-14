package application;

import java.time.LocalDate;
import java.util.Calendar;
import java.sql.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateStockHandler {
	
	@FXML Label itemCode;
	@FXML Label itemName;
	@FXML TextField qty;
	@FXML TextField price;
	@FXML DatePicker pDate;
	@FXML DatePicker eDate;	
	@FXML TextField	dept;
	
	private EventHandlerDispatcher dispatcherObj;
	private Stage dialogStage;
	
	public void setSelectedConsumables(EventHandlerDispatcher dispatcherObj, Stage dialogStage, Consumables c) {
		setCommonValues((Item) c);
		
		eDate.setValue(fromDateToLocalDate(c.getExpiryDate()));
		
		this.dispatcherObj = dispatcherObj;
		this.dialogStage = dialogStage;
	}
	
	public void setSelectedFixedItem(EventHandlerDispatcher dispatcherObj, Stage dialogStage, FixedItem fi) {
		setCommonValues((Item) fi);

		dept.setText(fi.getDepartment());
		
		this.dispatcherObj = dispatcherObj;
		this.dialogStage = dialogStage;
	}
	
	private void setCommonValues(Item i) {
		qty.requestFocus();
		
		itemCode.setText(i.getItemNo());
		itemName.setText(i.getItemName());
		qty.setText(String.valueOf(i.getQty()));
		price.setText(String.valueOf(i.getPrice()));		
		pDate.setValue(fromDateToLocalDate(i.getPurchaseDate()));		
	}

	public void conUpdateStock() {			
		DBController dbc = new DBController();
		EventHandlerHelper helperObj = new EventHandlerHelper();

		Consumables c = new Consumables(helperObj.returnItemIntCode(itemCode.getText()), itemName.getText(), Integer.parseInt(qty.getText()), Double.parseDouble(price.getText()), fromLocalDateToDate(pDate.getValue()), fromLocalDateToDate(eDate.getValue()));

		if(dbc.updateRecord((Item) c)) {
			dispatcherObj.handleConUpdate(c);
			helperObj.showAlert(Alert.AlertType.INFORMATION, "Success", "Item updated successfully.", "");			
		} else
			helperObj.showAlert(Alert.AlertType.ERROR, "Error", "Update operation failed.", "Error occurred while updating the item. Please contact IT team.");
		
		dialogStage.close();
	}
	
	public void fiUpdateStock() {
		DBController dbc = new DBController();
		EventHandlerHelper helperObj = new EventHandlerHelper();
		
		FixedItem fi = new FixedItem(helperObj.returnItemIntCode(itemCode.getText()), itemName.getText(), Integer.parseInt(qty.getText()), Double.parseDouble(price.getText()), fromLocalDateToDate(pDate.getValue()), dept.getText());

		if(dbc.updateRecord((Item) fi)) {
			dispatcherObj.handleFIUpdate(fi);
			helperObj.showAlert(Alert.AlertType.INFORMATION, "Success", "Item updated successfully.", "");			
		} else
			helperObj.showAlert(Alert.AlertType.ERROR, "Error", "Update operation failed.", "Error occurred while updating the item. Please contact IT team.");
		
		dialogStage.close();
	}
	
	private LocalDate fromDateToLocalDate(Date d){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);			
		
		return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
	}
	
	private Date fromLocalDateToDate(LocalDate ld) {
		return Date.valueOf(ld);
	}
}